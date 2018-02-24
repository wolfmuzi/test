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
import com.iuling.mods.clothingmanageformj.entity.KindclothingBind;
import com.iuling.mods.clothingmanageformj.service.KindclothingBindService;

/**
 * 样衣绑定信息Controller
 * @author 彭成
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingBind")
public class KindclothingBindController extends BaseController {

	@Autowired
	private KindclothingBindService kindclothingBindService;
	
	@ModelAttribute
	public KindclothingBind get(@RequestParam(required=false) String id) {
		KindclothingBind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingBindService.get(id);
		}
		if (entity == null){
			entity = new KindclothingBind();
		}
		return entity;
	}
	
	/**
	 * 样衣列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingBind:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingBindList";
	}
	
		/**
	 * 样衣列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingBind kindclothingBind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingBind> page = kindclothingBindService.findPage(new Page<KindclothingBind>(request, response), kindclothingBind); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingBind:view","kindclothing:kindclothingBind:add","kindclothing:kindclothingBind:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingBind kindclothingBind, Model model) {
		model.addAttribute("kindclothingBind", kindclothingBind);
		return "mods/kindclothing/kindclothingBindForm";
	}

	/**
	 * 保存样衣
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingBind:add","kindclothing:kindclothingBind:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingBind kindclothingBind, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingBind)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingBindService.save(kindclothingBind);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣成功");
		return j;
	}
	
	/**
	 * 删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingBind kindclothingBind, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingBindService.delete(kindclothingBind);
		j.setMsg("删除样衣成功");
		return j;
	}
	
	/**
	 * 批量删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingBindService.delete(kindclothingBindService.get(id));
		}
		j.setMsg("删除样衣成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingBind:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingBind kindclothingBind, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingBind> page = kindclothingBindService.findPage(new Page<KindclothingBind>(request, response, -1), kindclothingBind);
    		new ExportExcel("样衣", KindclothingBind.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingBind:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingBind> list = ei.getDataList(KindclothingBind.class);
			for (KindclothingBind kindclothingBind : list){
				try{
					kindclothingBindService.save(kindclothingBind);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条样衣记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingBind/?repage";
    }
	
	/**
	 * 下载导入样衣数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingBind:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣数据导入模板.xlsx";
    		List<KindclothingBind> list = Lists.newArrayList(); 
    		new ExportExcel("样衣数据", KindclothingBind.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingBind/?repage";
    }

}