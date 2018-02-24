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
import com.iuling.mods.clothingmanageformj.entity.KindclothingUnbindItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingUnbindItemService;

/**
 * 样衣解绑详情Controller
 * @author 潘俞再
 * @version 2017-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingUnbindItem")
public class KindclothingUnbindItemController extends BaseController {

	@Autowired
	private KindclothingUnbindItemService kindclothingUnbindItemService;
	
	@ModelAttribute
	public KindclothingUnbindItem get(@RequestParam(required=false) String id) {
		KindclothingUnbindItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingUnbindItemService.get(id);
		}
		if (entity == null){
			entity = new KindclothingUnbindItem();
		}
		return entity;
	}
	
	/**
	 * 样衣解绑详情列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingUnbind:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingUnbindItemList";
	}
	/**
	 * 详情页面跳转
	 */
	@RequiresPermissions("kindclothing:kindclothingUnbind:list")
	@RequestMapping(value = {"toDetail/{id}", "GET"})
	public String toDetail(@PathVariable String id, HttpServletRequest request) {

		request.setAttribute("id",id);

		return "mods/kindclothing/kindclothingUnbindItemList";
	}
		/**
	 * 样衣解绑详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingUnbindItem kindclothingUnbindItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingUnbindItem> page = kindclothingUnbindItemService.findPage(new Page<KindclothingUnbindItem>(request, response), kindclothingUnbindItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣解绑详情表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingUnbind:view","kindclothing:kindclothingUnbind:add","kindclothing:kindclothingUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingUnbindItem kindclothingUnbindItem, Model model) {
		model.addAttribute("kindclothingUnbindItem", kindclothingUnbindItem);
		return "mods/kindclothing/kindclothingUnbindItemForm";
	}

	/**
	 * 保存样衣解绑详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingUnbind:add","kindclothing:kindclothingUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingUnbindItem kindclothingUnbindItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingUnbindItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingUnbindItemService.save(kindclothingUnbindItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣解绑详情成功");
		return j;
	}
	
	/**
	 * 删除样衣解绑详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingUnbindItem kindclothingUnbindItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingUnbindItemService.delete(kindclothingUnbindItem);
		j.setMsg("删除样衣解绑详情成功");
		return j;
	}
	
	/**
	 * 批量删除样衣解绑详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingUnbindItemService.delete(kindclothingUnbindItemService.get(id));
		}
		j.setMsg("删除样衣解绑详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingUnbind:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingUnbindItem kindclothingUnbindItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣解绑详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingUnbindItem> page = kindclothingUnbindItemService.findPage(new Page<KindclothingUnbindItem>(request, response, -1), kindclothingUnbindItem);
    		new ExportExcel("样衣解绑详情", KindclothingUnbindItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣解绑详情记录失败！失败信息："+e.getMessage());
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
			List<KindclothingUnbindItem> list = ei.getDataList(KindclothingUnbindItem.class);
			for (KindclothingUnbindItem kindclothingUnbindItem : list){
				try{
					kindclothingUnbindItemService.save(kindclothingUnbindItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条样衣解绑详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣解绑详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣解绑详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingUnbindItem/?repage";
    }
	
	/**
	 * 下载导入样衣解绑详情数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingUnbind:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣解绑详情数据导入模板.xlsx";
    		List<KindclothingUnbindItem> list = Lists.newArrayList(); 
    		new ExportExcel("样衣解绑详情数据", KindclothingUnbindItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingUnbindItem/?repage";
    }

}