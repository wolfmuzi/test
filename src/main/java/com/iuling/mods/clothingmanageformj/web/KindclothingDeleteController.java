/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.web;

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
import com.iuling.mods.clothingmanageformj.entity.KindclothingDelete;
import com.iuling.mods.clothingmanageformj.service.KindclothingDeleteService;

/**
 * 样衣销毁记录Controller
 * @author 彭成
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingDelete")
public class KindclothingDeleteController extends BaseController {

	@Autowired
	private KindclothingDeleteService kindclothingDeleteService;
	
	@ModelAttribute
	public KindclothingDelete get(@RequestParam(required=false) String id) {
		KindclothingDelete entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingDeleteService.get(id);
		}
		if (entity == null){
			entity = new KindclothingDelete();
		}
		return entity;
	}
	
	/**
	 * 销毁记录列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingDelete:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingDeleteList";
	}
	
		/**
	 * 销毁记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingDelete:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingDelete kindclothingDelete, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingDelete> page = kindclothingDeleteService.findPage(new Page<KindclothingDelete>(request, response), kindclothingDelete); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑销毁记录表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingDelete:view","kindclothing:kindclothingDelete:add","kindclothing:kindclothingDelete:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingDelete kindclothingDelete, Model model) {
		model.addAttribute("kindclothingDelete", kindclothingDelete);
		if(StringUtils.isBlank(kindclothingDelete.getId())){//如果ID是空为添加
			model.addAttribute("isAdd", true);
		}
		return "mods/kindclothing/kindclothingDeleteForm";
	}

	/**
	 * 保存销毁记录
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingDelete:add","kindclothing:kindclothingDelete:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(KindclothingDelete kindclothingDelete, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, kindclothingDelete)){
			return form(kindclothingDelete, model);
		}
		//新增或编辑表单保存
		kindclothingDeleteService.save(kindclothingDelete);//保存
		addMessage(redirectAttributes, "保存销毁记录成功");
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingDelete/?repage";
	}
	
	/**
	 * 删除销毁记录
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingDelete:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingDelete kindclothingDelete, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingDeleteService.delete(kindclothingDelete);
		j.setMsg("删除销毁记录成功");
		return j;
	}
	
	/**
	 * 批量删除销毁记录
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingDelete:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingDeleteService.delete(kindclothingDeleteService.get(id));
		}
		j.setMsg("删除销毁记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingDelete:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingDelete kindclothingDelete, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "销毁记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingDelete> page = kindclothingDeleteService.findPage(new Page<KindclothingDelete>(request, response, -1), kindclothingDelete);
    		new ExportExcel("销毁记录", KindclothingDelete.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出销毁记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingDelete:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingDelete> list = ei.getDataList(KindclothingDelete.class);
			for (KindclothingDelete kindclothingDelete : list){
				try{
					kindclothingDeleteService.save(kindclothingDelete);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条销毁记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条销毁记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入销毁记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingDelete/?repage";
    }
	
	/**
	 * 下载导入销毁记录数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingDelete:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "销毁记录数据导入模板.xlsx";
    		List<KindclothingDelete> list = Lists.newArrayList(); 
    		new ExportExcel("销毁记录数据", KindclothingDelete.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingDelete/?repage";
    }

}