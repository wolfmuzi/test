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
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturnItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingReturnItemService;
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
 * 样衣归还详情Controller
 * @author 彭成
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingReturnItem")
public class KindclothingReturnItemController extends BaseController {

	@Autowired
	private KindclothingReturnItemService kindclothingReturnItemService;
	
	@ModelAttribute
	public KindclothingReturnItem get(@RequestParam(required=false) String id) {
		KindclothingReturnItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingReturnItemService.get(id);
		}
		if (entity == null){
			entity = new KindclothingReturnItem();
		}
		return entity;
	}

	/**
	 * 跳转 本次归还详情
	 */
	@RequiresPermissions("kindclothing:kindclothingReturnItem:list")
	@RequestMapping(value = {"toDetail/{returnNo}", "GET"})
	public String toDetail(@PathVariable String returnNo, HttpServletRequest request){
		request.setAttribute("returnNo",returnNo);
		return "mods/kindclothing/kindclothingReturnItemList";
	}

	/**
	 * 样衣归还详情列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingReturnItem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingReturnItemList";
	}
	
		/**
	 * 样衣归还详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturnItem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingReturnItem kindclothingReturnItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingReturnItem> page = kindclothingReturnItemService.findPage(new Page<KindclothingReturnItem>(request, response), kindclothingReturnItem); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣归还详情表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingReturnItem:view","kindclothing:kindclothingReturnItem:add","kindclothing:kindclothingReturnItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingReturnItem kindclothingReturnItem, Model model) {
		model.addAttribute("kindclothingReturnItem", kindclothingReturnItem);
		return "mods/kindclothing/kindclothingReturnItemForm";
	}

	/**
	 * 保存样衣归还详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingReturnItem:add","kindclothing:kindclothingReturnItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingReturnItem kindclothingReturnItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingReturnItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingReturnItemService.save(kindclothingReturnItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣归还详情成功");
		return j;
	}
	
	/**
	 * 删除样衣归还详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturnItem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingReturnItem kindclothingReturnItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingReturnItemService.delete(kindclothingReturnItem);
		j.setMsg("删除样衣归还详情成功");
		return j;
	}
	
	/**
	 * 批量删除样衣归还详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturnItem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingReturnItemService.delete(kindclothingReturnItemService.get(id));
		}
		j.setMsg("删除样衣归还详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturnItem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingReturnItem kindclothingReturnItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣归还详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingReturnItem> page = kindclothingReturnItemService.findPage(new Page<KindclothingReturnItem>(request, response, -1), kindclothingReturnItem);
    		new ExportExcel("样衣归还详情", KindclothingReturnItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣归还详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingReturnItem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingReturnItem> list = ei.getDataList(KindclothingReturnItem.class);
			for (KindclothingReturnItem kindclothingReturnItem : list){
				try{
					kindclothingReturnItemService.save(kindclothingReturnItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条样衣归还详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣归还详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣归还详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingReturnItem/?repage";
    }
	
	/**
	 * 下载导入样衣归还详情数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingReturnItem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣归还详情数据导入模板.xlsx";
    		List<KindclothingReturnItem> list = Lists.newArrayList(); 
    		new ExportExcel("样衣归还详情数据", KindclothingReturnItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingReturnItem/?repage";
    }

}