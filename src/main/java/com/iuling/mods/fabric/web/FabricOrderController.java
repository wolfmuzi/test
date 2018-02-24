/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

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
import com.iuling.mods.fabric.entity.FabricOrder;
import com.iuling.mods.fabric.service.FabricOrderService;

/**
 * 色卡调布Controller
 * @author 潘俞再
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricOrder")
public class FabricOrderController extends BaseController {

	@Autowired
	private FabricOrderService fabricOrderService;
	
	@ModelAttribute
	public FabricOrder get(@RequestParam(required=false) String id) {
		FabricOrder entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricOrderService.get(id);
		}
		if (entity == null){
			entity = new FabricOrder();
		}
		return entity;
	}
	
	/**
	 * 调布信息列表页面
	 */
	@RequiresPermissions("fabric:fabricOrder:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricOrderList";
	}
	
		/**
	 * 调布信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricOrder fabricOrder, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricOrder> page = fabricOrderService.findPage(new Page<FabricOrder>(request, response), fabricOrder); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑调布信息表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricOrder:view","fabric:fabricOrder:add","fabric:fabricOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricOrder fabricOrder, Model model) {
		model.addAttribute("fabricOrder", fabricOrder);
		return "mods/fabric/fabricOrderForm";
	}

	/**
	 * 保存调布信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricOrder:add","fabric:fabricOrder:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricOrder fabricOrder, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricOrder)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricOrderService.save(fabricOrder);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存调布信息成功");
		return j;
	}
	
	/**
	 * 删除调布信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricOrder fabricOrder, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricOrderService.delete(fabricOrder);
		j.setMsg("删除调布信息成功");
		return j;
	}
	
	/**
	 * 批量删除调布信息
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricOrderService.delete(fabricOrderService.get(id));
		}
		j.setMsg("删除调布信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricOrder:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricOrder fabricOrder, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "调布信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricOrder> page = fabricOrderService.findPage(new Page<FabricOrder>(request, response, -1), fabricOrder);
    		new ExportExcel("调布信息", FabricOrder.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出调布信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricOrder:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricOrder> list = ei.getDataList(FabricOrder.class);
			for (FabricOrder fabricOrder : list){
				try{
					fabricOrderService.save(fabricOrder);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条调布信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条调布信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入调布信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricOrder/?repage";
    }
	
	/**
	 * 下载导入调布信息数据模板
	 */
	@RequiresPermissions("fabric:fabricOrder:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "调布信息数据导入模板.xlsx";
    		List<FabricOrder> list = Lists.newArrayList(); 
    		new ExportExcel("调布信息数据", FabricOrder.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricOrder/?repage";
    }

}