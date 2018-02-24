/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.iuling.mods.base.entity.Equipment;
import com.iuling.mods.base.service.EquipmentService;
import com.iuling.mods.fabric.entity.FabricShelves;
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
import com.iuling.mods.base.entity.EquipmentUse;
import com.iuling.mods.base.service.EquipmentUseService;

/**
 * 设备使用记录Controller
 * @author 宋小雄
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/base/equipmentUse")
public class EquipmentUseController extends BaseController {

	@Autowired
	private EquipmentUseService equipmentUseService;

	@Autowired
	private EquipmentService equipmentService;
	
	@ModelAttribute
	public EquipmentUse get(@RequestParam(required=false) String id) {
		EquipmentUse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = equipmentUseService.get(id);
		}
		if (entity == null){
			entity = new EquipmentUse();
		}
		return entity;
	}


	/**
	 * 设备使用详情列表页面
	 */
	@RequiresPermissions("base:equipment:list")
	@RequestMapping(value = {"toDetail/{id}", "GET"})
	public String toDetail(@PathVariable String id, HttpServletRequest request)
	{

		FabricShelves param = new FabricShelves();
		param.setId(id);

		request.setAttribute("id",id);
		return "mods/base/equipmentUseList";
	}


	/**
	 * 设备使用记录列表页面
	 */
	@RequiresPermissions("base:equipment:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/equipmentUseList";
	}
	
		/**
	 * 设备使用记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(EquipmentUse equipmentUse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<EquipmentUse> page = equipmentUseService.findPage(new Page<EquipmentUse>(request, response), equipmentUse); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备使用记录表单页面
	 */
	@RequiresPermissions(value={"base:equipment:view","base:equipment:add","base:equipment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(EquipmentUse equipmentUse, Model model) {
		model.addAttribute("equipmentUse", equipmentUse);
		return "mods/base/equipmentUseForm";
	}

	/**
	 * 保存设备使用记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:equipment:add","base:equipment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(EquipmentUse equipmentUse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, equipmentUse)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		/**
		 * 判断设备是不是使用中
		 */
		Equipment equipment = equipmentService.get(equipmentUse.getEquipment().getId());
;		if(equipment != null && "1".equals(equipment.getStatus()) ){
			j.setSuccess(false);
			j.setMsg("该设备使用中！");
			return j;


		}
		equipment.setStatus("1");
		equipmentService.save(equipment);

		equipmentUseService.save(equipmentUse);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存设备使用记录成功");
		return j;
	}
	
	/**
	 * 删除设备使用记录
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(EquipmentUse equipmentUse, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		equipmentUseService.delete(equipmentUse);
		j.setMsg("删除设备使用记录成功");
		return j;
	}
	
	/**
	 * 批量删除设备使用记录
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			equipmentUseService.delete(equipmentUseService.get(id));
		}
		j.setMsg("删除设备使用记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(EquipmentUse equipmentUse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备使用记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<EquipmentUse> page = equipmentUseService.findPage(new Page<EquipmentUse>(request, response, -1), equipmentUse);
    		new ExportExcel("设备使用记录", EquipmentUse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备使用记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:equipment:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<EquipmentUse> list = ei.getDataList(EquipmentUse.class);
			for (EquipmentUse equipmentUse : list){
				try{
					equipmentUseService.save(equipmentUse);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备使用记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条设备使用记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入设备使用记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/equipmentUse/?repage";
    }
	
	/**
	 * 下载导入设备使用记录数据模板
	 */
	@RequiresPermissions("base:equipment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "设备使用记录数据导入模板.xlsx";
    		List<EquipmentUse> list = Lists.newArrayList(); 
    		new ExportExcel("设备使用记录数据", EquipmentUse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/equipmentUse/?repage";
    }

}