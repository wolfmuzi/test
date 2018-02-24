/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.service.FabricStockService;

/**
 * 面料色卡库存信息Controller
 * @author 潘俞再
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricStock")
public class FabricStockController extends BaseController {

	@Autowired
	private FabricStockService fabricStockService;
	
	@ModelAttribute
	public FabricStock get(@RequestParam(required=false) String id) {
		FabricStock entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricStockService.get(id);
		}
		if (entity == null){
			entity = new FabricStock();
		}
		return entity;
	}
	
	/**
	 * 面料列表页面
	 */
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricStockList";
	}
	
		/**
	 * 面料列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricStock fabricStock, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricStock> page = fabricStockService.findPage(new Page<FabricStock>(request, response), fabricStock); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑面料表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricStock:view","fabric:fabricStock:add","fabric:fabricStock:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricStock fabricStock, Model model) {
		model.addAttribute("fabricStock", fabricStock);
		return "mods/fabric/fabricStockForm";
	}

	/**
	 * 保存面料
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricStock:add","fabric:fabricStock:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricStock fabricStock, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricStock)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricStockService.save(fabricStock);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存面料成功");
		return j;
	}
	
	/**
	 * 删除面料
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricStock fabricStock, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricStockService.delete(fabricStock);
		j.setMsg("删除面料成功");
		return j;
	}
	
	/**
	 * 批量删除面料
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricStockService.delete(fabricStockService.get(id));
		}
		j.setMsg("删除面料成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricStock:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricStock fabricStock, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "面料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricStock> page = fabricStockService.findPage(new Page<FabricStock>(request, response, -1), fabricStock);
    		new ExportExcel("面料", FabricStock.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出面料记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricStock:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricStock> list = ei.getDataList(FabricStock.class);
			for (FabricStock fabricStock : list){
				try{
					fabricStockService.save(fabricStock);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条面料记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条面料记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入面料失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricStock/?repage";
    }
	
	/**
	 * 下载导入面料数据模板
	 */
	@RequiresPermissions("fabric:fabricStock:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "面料数据导入模板.xlsx";
    		List<FabricStock> list = Lists.newArrayList(); 
    		new ExportExcel("面料数据", FabricStock.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricStock/?repage";
    }

}