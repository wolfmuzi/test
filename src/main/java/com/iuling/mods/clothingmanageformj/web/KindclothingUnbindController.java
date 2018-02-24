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
import com.iuling.mods.clothingmanageformj.entity.KindclothingUnbind;
import com.iuling.mods.clothingmanageformj.service.KindclothingUnbindService;

/**
 * 样衣解绑记录Controller
 * @author 彭成
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingUnbind")
public class KindclothingUnbindController extends BaseController {

	@Autowired
	private KindclothingUnbindService kindclothingUnbindService;
	
	@ModelAttribute
	public KindclothingUnbind get(@RequestParam(required=false) String id) {
		KindclothingUnbind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingUnbindService.get(id);
		}
		if (entity == null){
			entity = new KindclothingUnbind();
		}
		return entity;
	}
	
	/**
	 * 解绑记录列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingUnbind:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingUnbindList";
	}
	
		/**
	 * 解绑记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingUnbind kindclothingUnbind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingUnbind> page = kindclothingUnbindService.findPage(new Page<KindclothingUnbind>(request, response), kindclothingUnbind); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑解绑记录表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingUnbind:view","kindclothing:kindclothingUnbind:add","kindclothing:kindclothingUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingUnbind kindclothingUnbind, Model model) {
		model.addAttribute("kindclothingUnbind", kindclothingUnbind);
		return "mods/kindclothing/kindclothingUnbindForm";
	}

	/**
	 * 保存解绑记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingUnbind:add","kindclothing:kindclothingUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingUnbind kindclothingUnbind, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingUnbind)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingUnbindService.save(kindclothingUnbind);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存解绑记录成功");
		return j;
	}
	
	/**
	 * 删除解绑记录
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingUnbind kindclothingUnbind, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingUnbindService.delete(kindclothingUnbind);
		j.setMsg("删除解绑记录成功");
		return j;
	}
	
	/**
	 * 批量删除解绑记录
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingUnbindService.delete(kindclothingUnbindService.get(id));
		}
		j.setMsg("删除解绑记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingUnbind kindclothingUnbind, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "解绑记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingUnbind> page = kindclothingUnbindService.findPage(new Page<KindclothingUnbind>(request, response, -1), kindclothingUnbind);
    		new ExportExcel("解绑记录", KindclothingUnbind.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出解绑记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingUnbind:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingUnbind> list = ei.getDataList(KindclothingUnbind.class);
			for (KindclothingUnbind kindclothingUnbind : list){
				try{
					kindclothingUnbindService.save(kindclothingUnbind);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条解绑记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条解绑记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入解绑记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingUnbind/?repage";
    }
	
	/**
	 * 下载导入解绑记录数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingUnbind:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "解绑记录数据导入模板.xlsx";
    		List<KindclothingUnbind> list = Lists.newArrayList(); 
    		new ExportExcel("解绑记录数据", KindclothingUnbind.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingUnbind/?repage";
    }

}