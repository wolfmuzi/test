/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.entity.FabricInventoryItme;
import com.iuling.mods.fabric.service.FabricInventoryItmeService;
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
import com.iuling.mods.fabric.entity.FabricInventory;
import com.iuling.mods.fabric.service.FabricInventoryService;

/**
 * 色卡盘点Controller
 * @author 潘俞再
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricInventory")
public class FabricInventoryController extends BaseController {

	@Autowired
	private FabricInventoryService fabricInventoryService;

	@Autowired
	private FabricInventoryItmeService fabricInventoryItmeService;
	
	@ModelAttribute
	public FabricInventory get(@RequestParam(required=false) String id) {
		FabricInventory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricInventoryService.get(id);
		}
		if (entity == null){
			entity = new FabricInventory();
		}
		return entity;
	}
	
	/**
	 * 盘点记录列表页面
	 */
	@RequiresPermissions("fabric:fabricInventory:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricInventoryList";
	}
	
		/**
	 * 盘点记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricInventory fabricInventory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricInventory> page = fabricInventoryService.findPage(new Page<FabricInventory>(request, response), fabricInventory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑盘点记录表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricInventory:view","fabric:fabricInventory:add","fabric:fabricInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricInventory fabricInventory, Model model) {
		model.addAttribute("fabricInventory", fabricInventory);
		return "mods/fabric/fabricInventoryForm";
	}

	/**
	 * 保存盘点记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricInventory:add","fabric:fabricInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricInventory fabricInventory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricInventory)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricInventoryService.save(fabricInventory);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存盘点记录成功");
		return j;
	}


	/**
	 * 更改处理状态
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricInventory:add","fabric:fabricInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "statusHandle")
	public AjaxJson statusHandle(FabricInventory fabricInventory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		FabricInventory f = fabricInventoryService.getByCode(fabricInventory);
		if (fabricInventory.getCode() == null || f == null){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		FabricInventoryItme fabricInventoryItme = new FabricInventoryItme();
		fabricInventoryItme.setCode(fabricInventory.getCode());
		fabricInventoryItme.setType(2);
		fabricInventoryItme.setStatus(1);
		List<Fabric> list = fabricInventoryItmeService.findByParam(fabricInventoryItme);
		if(list.size() > 0){//如果盘盈还有未上架的
			j.setSuccess(false);
			j.setMsg("盘盈的面料没有全部上架！");
			return j;
		}
		f.setStatus(2);
		fabricInventoryService.save(f);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("处理成功");
		return j;
	}
	
	/**
	 * 删除盘点记录
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricInventory fabricInventory, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricInventoryService.delete(fabricInventory);
		j.setMsg("删除盘点记录成功");
		return j;
	}
	
	/**
	 * 批量删除盘点记录
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricInventoryService.delete(fabricInventoryService.get(id));
		}
		j.setMsg("删除盘点记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricInventory fabricInventory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "盘点记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricInventory> page = fabricInventoryService.findPage(new Page<FabricInventory>(request, response, -1), fabricInventory);
    		new ExportExcel("盘点记录", FabricInventory.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出盘点记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricInventory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricInventory> list = ei.getDataList(FabricInventory.class);
			for (FabricInventory fabricInventory : list){
				try{
					fabricInventoryService.save(fabricInventory);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条盘点记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条盘点记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入盘点记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricInventory/?repage";
    }
	
	/**
	 * 下载导入盘点记录数据模板
	 */
	@RequiresPermissions("fabric:fabricInventory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "盘点记录数据导入模板.xlsx";
    		List<FabricInventory> list = Lists.newArrayList(); 
    		new ExportExcel("盘点记录数据", FabricInventory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricInventory/?repage";
    }

}