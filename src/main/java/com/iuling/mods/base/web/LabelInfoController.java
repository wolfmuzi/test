/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.web;

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
import com.iuling.mods.base.entity.LabelInfo;
import com.iuling.mods.base.service.LabelInfoService;

/**
 * 标签信息管理Controller
 * @author 宋小雄
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/base/labelInfo")
public class LabelInfoController extends BaseController {

	@Autowired
	private LabelInfoService labelInfoService;
	
	@ModelAttribute
	public LabelInfo get(@RequestParam(required=false) String id) {
		LabelInfo entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = labelInfoService.get(id);
		}
		if (entity == null){
			entity = new LabelInfo();
		}
		return entity;
	}
	
	/**
	 * 标签信息列表页面
	 */
	@RequiresPermissions("base:labelInfo:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/labelInfoList";
	}
	
		/**
	 * 标签信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:labelInfo:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(LabelInfo labelInfo, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<LabelInfo> page = labelInfoService.findPage(new Page<LabelInfo>(request, response), labelInfo); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑标签信息表单页面
	 */
	@RequiresPermissions(value={"base:labelInfo:view","base:labelInfo:add","base:labelInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(LabelInfo labelInfo, Model model) {
		model.addAttribute("labelInfo", labelInfo);
		return "mods/base/labelInfoForm";
	}

	/**
	 * 保存标签信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:labelInfo:add","base:labelInfo:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(LabelInfo labelInfo, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, labelInfo)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		labelInfoService.save(labelInfo);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存标签信息成功");
		return j;
	}
	
	/**
	 * 删除标签信息
	 */
	@ResponseBody
	@RequiresPermissions("base:labelInfo:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(LabelInfo labelInfo, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		labelInfoService.delete(labelInfo);
		j.setMsg("删除标签信息成功");
		return j;
	}
	
	/**
	 * 批量删除标签信息
	 */
	@ResponseBody
	@RequiresPermissions("base:labelInfo:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			labelInfoService.delete(labelInfoService.get(id));
		}
		j.setMsg("删除标签信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:labelInfo:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(LabelInfo labelInfo, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "标签信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<LabelInfo> page = labelInfoService.findPage(new Page<LabelInfo>(request, response, -1), labelInfo);
    		new ExportExcel("标签信息", LabelInfo.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出标签信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:labelInfo:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<LabelInfo> list = ei.getDataList(LabelInfo.class);
			for (LabelInfo labelInfo : list){
				try{
					labelInfoService.save(labelInfo);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条标签信息记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条标签信息记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入标签信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/labelInfo/?repage";
    }
	
	/**
	 * 下载导入标签信息数据模板
	 */
	@RequiresPermissions("base:labelInfo:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "标签信息数据导入模板.xlsx";
    		List<LabelInfo> list = Lists.newArrayList(); 
    		new ExportExcel("标签信息数据", LabelInfo.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/labelInfo/?repage";
    }

}