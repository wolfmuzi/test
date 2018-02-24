/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.clothingmanageformj.web;

import com.google.common.collect.Lists;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelves;
import com.iuling.mods.clothingmanageformj.entity.KindclothingShelvesItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingShelvesItemService;
import com.iuling.mods.clothingmanageformj.service.KindclothingShelvesService;
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
 * 样衣上架详情Controller
 * @author 彭成
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingShelvesItem")
public class KindclothingShelvesItemController extends BaseController {

	@Autowired
	private KindclothingShelvesItemService kindclothingShelvesItemService;

	@Autowired
	private KindclothingShelvesService kindclothingShelvesService;

	@ModelAttribute
	public KindclothingShelvesItem get(@RequestParam(required=false) String id) {
		KindclothingShelvesItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingShelvesItemService.get(id);
		}
		if (entity == null){
			entity = new KindclothingShelvesItem();
		}
		return entity;
	}

	/**
	 * 样衣列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingShelvesItemList";
	}


	/**
	 * 跳转上架详情列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:list")
	@RequestMapping(value = {"toDetail/{id}", "GET"})
	public String toDetail(@PathVariable String id, HttpServletRequest request)
	{
		KindclothingShelves kindclothingShelves = kindclothingShelvesService.get(id);
		request.setAttribute("kindclothingShelves",kindclothingShelves);
		return "mods/kindclothing/kindclothingShelvesItemList";
	}


		/**
	 * 样衣列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingShelvesItem kindclothingShelvesItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingShelvesItem> page = kindclothingShelvesItemService.findPage(new Page<KindclothingShelvesItem>(request, response), kindclothingShelvesItem);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingShelvesItem:view","kindclothing:kindclothingShelvesItem:add","kindclothing:kindclothingShelvesItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingShelvesItem kindclothingShelvesItem, Model model) {
		model.addAttribute("kindclothingShelvesItem", kindclothingShelvesItem);
		return "mods/kindclothing/kindclothingShelvesItemForm";
	}

	/**
	 * 保存样衣
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingShelvesItem:add","kindclothing:kindclothingShelvesItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingShelvesItem kindclothingShelvesItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingShelvesItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingShelvesItemService.save(kindclothingShelvesItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣成功");
		return j;
	}

	/**
	 * 删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingShelvesItem kindclothingShelvesItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingShelvesItemService.delete(kindclothingShelvesItem);
		j.setMsg("删除样衣成功");
		return j;
	}

	/**
	 * 批量删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingShelvesItemService.delete(kindclothingShelvesItemService.get(id));
		}
		j.setMsg("删除样衣成功");
		return j;
	}

	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingShelvesItem kindclothingShelvesItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingShelvesItem> page = kindclothingShelvesItemService.findPage(new Page<KindclothingShelvesItem>(request, response, -1), kindclothingShelvesItem);
    		new ExportExcel("样衣", KindclothingShelvesItem.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingShelvesItem> list = ei.getDataList(KindclothingShelvesItem.class);
			for (KindclothingShelvesItem kindclothingShelvesItem : list){
				try{
					kindclothingShelvesItemService.save(kindclothingShelvesItem);
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
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingShelvesItem/?repage";
    }

	/**
	 * 下载导入样衣数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingShelvesItem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣数据导入模板.xlsx";
    		List<KindclothingShelvesItem> list = Lists.newArrayList();
    		new ExportExcel("样衣数据", KindclothingShelvesItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingShelvesItem/?repage";
    }

}