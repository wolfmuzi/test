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
import com.iuling.mods.fabric.entity.FabricShelves;
import com.iuling.mods.fabric.service.FabricShelvesService;

/**
 * 上架信息Controller
 * @author 潘俞再
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricShelves")
public class FabricShelvesController extends BaseController {

	@Autowired
	private FabricShelvesService fabricShelvesService;
	
	@ModelAttribute
	public FabricShelves get(@RequestParam(required=false) String id) {
		FabricShelves entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricShelvesService.get(id);
		}
		if (entity == null){
			entity = new FabricShelves();
		}
		return entity;
	}
	
	/**
	 * 上架信息列表页面
	 */
	@RequiresPermissions("fabric:fabricShelves:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricShelvesList";
	}
	
		/**
	 * 上架信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricShelves fabricShelves, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricShelves> page = fabricShelvesService.findPage(new Page<FabricShelves>(request, response), fabricShelves); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑上架信息表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricShelves:view","fabric:fabricShelves:add","fabric:fabricShelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricShelves fabricShelves, Model model) {
		model.addAttribute("fabricShelves", fabricShelves);
		return "mods/fabric/fabricShelvesForm";
	}

	/**
	 * 保存上架信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricShelves:add","fabric:fabricShelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricShelves fabricShelves, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricShelves)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricShelvesService.save(fabricShelves);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存上架信息成功");
		return j;
	}
	
	/**
	 * 删除上架信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricShelves fabricShelves, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricShelvesService.delete(fabricShelves);
		j.setMsg("删除上架信息成功");
		return j;
	}
	
	/**
	 * 批量删除上架信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricShelvesService.delete(fabricShelvesService.get(id));
		}
		j.setMsg("删除上架信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricShelves fabricShelves, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "上架信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricShelves> page = fabricShelvesService.findPage(new Page<FabricShelves>(request, response, -1), fabricShelves);
    		new ExportExcel("上架信息", FabricShelves.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出上架信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricShelves:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricShelves> list = ei.getDataList(FabricShelves.class);
			for (FabricShelves fabricShelves : list){
				try{
					fabricShelvesService.save(fabricShelves);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条上架信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条上架信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入上架信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricShelves/?repage";
    }
	
	/**
	 * 下载导入上架信息数据模板
	 */
	@RequiresPermissions("fabric:fabricShelves:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "上架信息数据导入模板.xlsx";
    		List<FabricShelves> list = Lists.newArrayList(); 
    		new ExportExcel("上架信息数据", FabricShelves.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricShelves/?repage";
    }

}