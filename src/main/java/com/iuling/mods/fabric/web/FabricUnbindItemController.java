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
import org.springframework.web.bind.annotation.*;
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
import com.iuling.mods.fabric.entity.FabricUnbindItem;
import com.iuling.mods.fabric.service.FabricUnbindItemService;

/**
 * 面料解绑详情Controller
 * @author 潘俞再
 * @version 2017-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricUnbindItem")
public class FabricUnbindItemController extends BaseController {

	@Autowired
	private FabricUnbindItemService fabricUnbindItemService;
	
	@ModelAttribute
	public FabricUnbindItem get(@RequestParam(required=false) String id) {
		FabricUnbindItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricUnbindItemService.get(id);
		}
		if (entity == null){
			entity = new FabricUnbindItem();
		}
		return entity;
	}
	
	/**
	 * 面料解绑详情列表页面
	 */
	@RequiresPermissions("fabric:fabricUnbind:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricUnbindItemList";
	}


	/**
	 * 详情页面跳转
	 */
	@RequiresPermissions("fabric:fabricUnbind:list")
	@RequestMapping(value = {"toDetail/{id}", "GET"})
	public String toDetail(@PathVariable String id, HttpServletRequest request) {

		request.setAttribute("id",id);

		return "mods/fabric/fabricUnbindItemList";
	}
		/**
	 * 面料解绑详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricUnbindItem fabricUnbindItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricUnbindItem> page = fabricUnbindItemService.findPage(new Page<FabricUnbindItem>(request, response), fabricUnbindItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑面料解绑详情表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricUnbind:view","fabric:fabricUnbind:add","fabric:fabricUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricUnbindItem fabricUnbindItem, Model model) {
		model.addAttribute("fabricUnbindItem", fabricUnbindItem);
		return "mods/fabric/fabricUnbindItemForm";
	}

	/**
	 * 保存面料解绑详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricUnbind:add","fabric:fabricUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricUnbindItem fabricUnbindItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricUnbindItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricUnbindItemService.save(fabricUnbindItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存面料解绑详情成功");
		return j;
	}
	
	/**
	 * 删除面料解绑详情
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricUnbindItem fabricUnbindItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricUnbindItemService.delete(fabricUnbindItem);
		j.setMsg("删除面料解绑详情成功");
		return j;
	}
	
	/**
	 * 批量删除面料解绑详情
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricUnbindItemService.delete(fabricUnbindItemService.get(id));
		}
		j.setMsg("删除面料解绑详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricUnbindItem fabricUnbindItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "面料解绑详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricUnbindItem> page = fabricUnbindItemService.findPage(new Page<FabricUnbindItem>(request, response, -1), fabricUnbindItem);
    		new ExportExcel("面料解绑详情", FabricUnbindItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出面料解绑详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricUnbind:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricUnbindItem> list = ei.getDataList(FabricUnbindItem.class);
			for (FabricUnbindItem fabricUnbindItem : list){
				try{
					fabricUnbindItemService.save(fabricUnbindItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条面料解绑详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条面料解绑详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入面料解绑详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricUnbindItem/?repage";
    }
	
	/**
	 * 下载导入面料解绑详情数据模板
	 */
	@RequiresPermissions("fabric:fabricUnbind:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "面料解绑详情数据导入模板.xlsx";
    		List<FabricUnbindItem> list = Lists.newArrayList(); 
    		new ExportExcel("面料解绑详情数据", FabricUnbindItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricUnbindItem/?repage";
    }

}