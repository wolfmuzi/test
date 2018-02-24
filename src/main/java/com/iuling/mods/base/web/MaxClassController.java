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
import com.iuling.mods.base.entity.MaxClass;
import com.iuling.mods.base.service.MaxClassService;
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
 * 大类信息管理Controller
 * @author 彭成
 * @version 2017-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/base/maxClass")
public class MaxClassController extends BaseController {

	@Autowired
	private MaxClassService maxClassService;
	
	@ModelAttribute
	public MaxClass get(@RequestParam(required=false) String id) {
		MaxClass entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = maxClassService.get(id);
		}
		if (entity == null){
			entity = new MaxClass();
		}
		return entity;
	}
	
	/**
	 * 大类信息列表页面
	 */
	@RequiresPermissions("base:maxClass:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/maxClassList";
	}
	
		/**
	 * 大类信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:maxClass:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(MaxClass maxClass, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MaxClass> page = maxClassService.findPage(new Page<MaxClass>(request, response), maxClass);
		return getBootstrapData(page);
}

	/**
	 * 查看，增加，编辑大类信息表单页面
	 */
	@RequiresPermissions(value={"base:maxClass:view","base:maxClass:add","base:maxClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MaxClass maxClass, Model model) {
		model.addAttribute("maxClass", maxClass);
		return "mods/base/maxClassForm";
	}

	/**
	 * 保存大类信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:maxClass:add","base:maxClass:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(MaxClass maxClass, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, maxClass)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 判断大类代码是否重复
		 */
		MaxClass m = new MaxClass();
		m.setCode(maxClass.getCode());
		List<MaxClass> list = maxClassService.findList(m);
		if(list.size() > 0 && !list.get(0).getId().equals(maxClass.getId())){
			j.setSuccess(false);
			j.setMsg("大类代码已存在！");
			return j;
		}
		/**
		 * 判断大类名称是否重复
		 */
		MaxClass ms = new MaxClass();
		ms.setName(maxClass.getName());
		List<MaxClass> lists = maxClassService.findList(ms);
		if(lists.size() > 0 && !lists.get(0).getId().equals(maxClass.getId())){
			j.setSuccess(false);
			j.setMsg("大类名称已存在！");
			return j;
		}


		maxClassService.save(maxClass);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存大类信息成功");
		return j;
	}
	
	/**
	 * 删除大类信息
	 */
	@ResponseBody
	@RequiresPermissions("base:maxClass:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(MaxClass maxClass, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		maxClassService.delete(maxClass);
		j.setMsg("删除大类信息成功");
		return j;
	}
	
	/**
	 * 批量删除大类信息
	 */
	@ResponseBody
	@RequiresPermissions("base:maxClass:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			maxClassService.delete(maxClassService.get(id));
		}
		j.setMsg("删除大类信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:maxClass:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(MaxClass maxClass, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "大类信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MaxClass> page = maxClassService.findPage(new Page<MaxClass>(request, response, -1), maxClass);
    		new ExportExcel("大类信息", MaxClass.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出大类信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:maxClass:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;    //重复记录
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MaxClass> list = ei.getDataList(MaxClass.class);
			for (MaxClass maxClass : list){
				try{
					MaxClass m = new MaxClass();
					m.setCode(maxClass.getCode());
					List<MaxClass> temp = maxClassService.findList(m);
					if (temp.size() > 0) {//如果代码有重复
						chong++;
						continue;
					}
					MaxClass ms = new MaxClass();
					ms.setName(maxClass.getName());
					List<MaxClass> temps = maxClassService.findList(ms);
					if (temps.size() > 0) {//如果名称有重复
						chong++;
						continue;
					}

					maxClassService.save(maxClass);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条大类信息记录。");
			}
			String msg = "";  //重复提示信息
			if(chong > 0){
				msg = chong+"条记录重复数据未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条大类信息记录"+failureMsg+ "," +msg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入大类信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/maxClass/?repage";
    }
	
	/**
	 * 下载导入大类信息数据模板
	 */
	@RequiresPermissions("base:maxClass:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "大类信息数据导入模板.xlsx";
    		List<MaxClass> list = Lists.newArrayList(); 
    		new ExportExcel("大类信息数据", MaxClass.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/maxClass/?repage";
    }

}