/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.web;

import com.google.common.collect.Lists;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.Equipment;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.service.EquipmentService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Map;

/**
 * 设备管理Controller
 * @author 宋小雄
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/base/equipment")
public class EquipmentController extends BaseController {

	@Autowired
	private EquipmentService equipmentService;
	
	@ModelAttribute
	public Equipment get(@RequestParam(required=false) String id) {
		Equipment entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = equipmentService.get(id);
		}
		if (entity == null){
			entity = new Equipment();
		}
		return entity;
	}
	
	/**
	 * 设备信息列表页面
	 */
	@RequiresPermissions("base:equipment:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/equipmentList";
	}
	
		/**
	 * 设备信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Equipment equipment, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Equipment> page = equipmentService.findPage(new Page<Equipment>(request, response), equipment); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设备信息表单页面
	 */
	@RequiresPermissions(value={"base:equipment:view","base:equipment:add","base:equipment:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Equipment equipment, Model model) {
		model.addAttribute("equipment", equipment);
		return "mods/base/equipmentForm";
	}

	/**
	 * 保存设备信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:equipment:add","base:equipment:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Equipment equipment, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, equipment)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 判断条码是否重复
		 */
		Equipment s = new Equipment();
		s.setNumber(equipment.getNumber());
		List<Equipment> list = equipmentService.findList(s);
		if(list.size() > 0 && !list.get(0).getId().equals(equipment.getId())){
			j.setSuccess(false);
			j.setMsg("设备编码已存在！");
			return j;
		}
		equipmentService.save(equipment);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存设备信息成功");
		return j;
	}
	
	/**
	 * 删除设备信息
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Equipment equipment, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		equipmentService.delete(equipment);
		j.setMsg("删除设备信息成功");
		return j;
	}
	
	/**
	 * 批量删除设备信息
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			equipmentService.delete(equipmentService.get(id));
		}
		j.setMsg("删除设备信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:equipment:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Equipment equipment, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "设备信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Equipment> page = equipmentService.findPage(new Page<Equipment>(request, response, -1), equipment);
    		new ExportExcel("设备信息", Equipment.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出设备信息记录失败！失败信息："+e.getMessage());
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
			int chong = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Equipment> list = ei.getDataList(Equipment.class);
			for (Equipment equipment : list){
				try{
					Equipment f = new Equipment();
					f.setNumber(equipment.getNumber());
					List<Equipment> temp = equipmentService.findList(f);
					if (temp.size() > 0) {//如果有重复
						chong++;
						continue;
					}
					equipmentService.save(equipment);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设备信息记录。");
			}
			String msg = "";
			if(chong > 0){
				msg = chong+"条记录重复条码未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条设备信息记录"+failureMsg+" , "+msg);

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入设备信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/equipment/?repage";
    }
	
	/**
	 * 下载导入设备信息数据模板
	 */
	@RequiresPermissions("base:equipment:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "设备信息数据导入模板.xlsx";
    		List<Equipment> list = Lists.newArrayList(); 
    		new ExportExcel("设备信息数据", Equipment.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/equipment/?repage";
    }

}