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

import com.iuling.mods.fabric.entity.FabricShelves;
import com.iuling.mods.fabric.service.FabricShelvesService;
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
import com.iuling.mods.fabric.entity.FabricShelvesItem;
import com.iuling.mods.fabric.service.FabricShelvesItemService;

/**
 * 面料上架详情Controller
 * @author 潘俞再
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricShelvesItem")
public class FabricShelvesItemController extends BaseController {

	@Autowired
	private FabricShelvesItemService fabricShelvesItemService;

	@Autowired
	private FabricShelvesService fabricShelvesService;

	@ModelAttribute
	public FabricShelvesItem get(@RequestParam(required=false) String id) {
		FabricShelvesItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricShelvesItemService.get(id);
		}
		if (entity == null){
			entity = new FabricShelvesItem();
		}
		return entity;
	}
	
	/**
	 * 上架详情列表页面
	 */
	@RequiresPermissions("fabric:fabricShelves:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricShelvesItemList";
	}


	/**
	 * 上架详情列表页面
	 */
	@RequiresPermissions("fabric:fabricShelves:list")
	@RequestMapping(value = {"toDetail/{id}", "GET"})
	public String toDetail(@PathVariable String id, HttpServletRequest request)
	{

		FabricShelves param = new FabricShelves();
		param.setId(id);
		FabricShelves fabricShelves = fabricShelvesService.get(param);
		request.setAttribute("fabricShelves",fabricShelves);
		return "mods/fabric/fabricShelvesItemList";
	}


		/**
	 * 上架详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricShelvesItem fabricShelvesItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricShelvesItem> page = fabricShelvesItemService.findPage(new Page<FabricShelvesItem>(request, response), fabricShelvesItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑上架详情表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricShelves:view","fabric:fabricShelves:add","fabric:fabricShelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricShelvesItem fabricShelvesItem, Model model) {
		model.addAttribute("fabricShelvesItem", fabricShelvesItem);
		return "mods/fabric/fabricShelvesItemForm";
	}

	/**
	 * 保存上架详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricShelves:add","fabric:fabricShelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricShelvesItem fabricShelvesItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricShelvesItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricShelvesItemService.save(fabricShelvesItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存上架详情成功");
		return j;
	}
	
	/**
	 * 删除上架详情
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricShelvesItem fabricShelvesItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricShelvesItemService.delete(fabricShelvesItem);
		j.setMsg("删除上架详情成功");
		return j;
	}
	
	/**
	 * 批量删除上架详情
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricShelvesItemService.delete(fabricShelvesItemService.get(id));
		}
		j.setMsg("删除上架详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricShelves:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricShelvesItem fabricShelvesItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "上架详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricShelvesItem> page = fabricShelvesItemService.findPage(new Page<FabricShelvesItem>(request, response, -1), fabricShelvesItem);
    		new ExportExcel("上架详情", FabricShelvesItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出上架详情记录失败！失败信息："+e.getMessage());
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
			List<FabricShelvesItem> list = ei.getDataList(FabricShelvesItem.class);
			for (FabricShelvesItem fabricShelvesItem : list){
				try{
					fabricShelvesItemService.save(fabricShelvesItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条上架详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条上架详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入上架详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricShelvesItem/?repage";
    }
	
	/**
	 * 下载导入上架详情数据模板
	 */
	@RequiresPermissions("fabric:fabricShelves:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "上架详情数据导入模板.xlsx";
    		List<FabricShelvesItem> list = Lists.newArrayList(); 
    		new ExportExcel("上架详情数据", FabricShelvesItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricShelvesItem/?repage";
    }

}