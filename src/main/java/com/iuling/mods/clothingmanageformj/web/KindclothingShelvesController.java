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
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelves;
import com.iuling.mods.clothingmanageformj.service.KindclothingShelvesService;

/**
 * 样衣上架列表Controller
 * @author 彭成
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingShelves")
public class KindclothingShelvesController extends BaseController {

	@Autowired
	private KindclothingShelvesService kindclothingShelvesService;
	
	@ModelAttribute
	public KindclothingShelves get(@RequestParam(required=false) String id) {
		KindclothingShelves entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingShelvesService.get(id);
		}
		if (entity == null){
			entity = new KindclothingShelves();
		}
		return entity;
	}
	
	/**
	 * 样衣列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingShelves:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingShelvesList";
	}
	
		/**
	 * 样衣列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingShelves kindclothingShelves, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingShelves> page = kindclothingShelvesService.findPage(new Page<KindclothingShelves>(request, response), kindclothingShelves); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingShelves:view","kindclothing:kindclothingShelves:add","kindclothing:kindclothingShelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingShelves kindclothingShelves, Model model) {
		model.addAttribute("kindclothingShelves", kindclothingShelves);
		return "mods/kindclothing/kindclothingShelvesForm";
	}

	/**
	 * 保存样衣
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingShelves:add","kindclothing:kindclothingShelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingShelves kindclothingShelves, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingShelves)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingShelvesService.save(kindclothingShelves);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣成功");
		return j;
	}
	
	/**
	 * 删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingShelves kindclothingShelves, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingShelvesService.delete(kindclothingShelves);
		j.setMsg("删除样衣成功");
		return j;
	}
	
	/**
	 * 批量删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingShelvesService.delete(kindclothingShelvesService.get(id));
		}
		j.setMsg("删除样衣成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelves:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingShelves kindclothingShelves, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingShelves> page = kindclothingShelvesService.findPage(new Page<KindclothingShelves>(request, response, -1), kindclothingShelves);
    		new ExportExcel("样衣", KindclothingShelves.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("kindclothing:kindclothingShelves:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingShelves> list = ei.getDataList(KindclothingShelves.class);
			for (KindclothingShelves kindclothingShelves : list){
				try{
					kindclothingShelvesService.save(kindclothingShelves);
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
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingShelves/?repage";
    }
	
	/**
	 * 下载导入样衣数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingShelves:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣数据导入模板.xlsx";
    		List<KindclothingShelves> list = Lists.newArrayList(); 
    		new ExportExcel("样衣数据", KindclothingShelves.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingShelves/?repage";
    }

}