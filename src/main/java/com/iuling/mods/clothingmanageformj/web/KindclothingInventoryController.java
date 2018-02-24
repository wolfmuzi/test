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
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventory;
import com.iuling.mods.clothingmanageformj.entity.KindclothingInventoryItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingInventoryItemService;
import com.iuling.mods.clothingmanageformj.service.KindclothingInventoryService;
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
 * 样衣盘点Controller
 * @author 彭成
 * @version 2017-09-26
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingInventory")
public class KindclothingInventoryController extends BaseController {

	@Autowired
	private KindclothingInventoryService kindclothingInventoryService;
	@Autowired
	private KindclothingInventoryItemService kindclothingInventoryItemService;

	@ModelAttribute
	public KindclothingInventory get(@RequestParam(required=false) String id) {
		KindclothingInventory entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingInventoryService.get(id);
		}
		if (entity == null){
			entity = new KindclothingInventory();
		}
		return entity;
	}
	
	/**
	 * 样衣列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingInventory:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingInventoryList";
	}
	
		/**
	 * 样衣列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingInventory kindclothingInventory, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingInventory> page = kindclothingInventoryService.findPage(new Page<KindclothingInventory>(request, response), kindclothingInventory); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingInventory:view","kindclothing:kindclothingInventory:add","kindclothing:kindclothingInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingInventory kindclothingInventory, Model model) {
		model.addAttribute("kindclothingInventory", kindclothingInventory);
		return "mods/kindclothing/kindclothingInventoryForm";
	}

	/**
	 * 保存样衣
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingInventory:add","kindclothing:kindclothingInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingInventory kindclothingInventory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingInventory)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingInventoryService.save(kindclothingInventory);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣成功");
		return j;
	}

	/**
	 * 更改处理状态
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingInventory:add","kindclothing:kindclothingInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "statusHandle")
	public AjaxJson statusHandle(KindclothingInventory kindclothingInventory, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		KindclothingInventory k = kindclothingInventoryService.getByCode(kindclothingInventory);
		if (kindclothingInventory.getCode() == null || k == null){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		KindclothingInventoryItem item = new KindclothingInventoryItem();
		item.setCode(kindclothingInventory.getCode());
		item.setType(2);  //处理状态
		item.setStatus(1);  //上架状态
		List<KindclothingInventoryItem> list = kindclothingInventoryItemService.findByParam(item);
		if(list.size() > 0){//如果盘盈还有未上架的
			j.setSuccess(false);
			j.setMsg("盘盈的样衣没有全部上架！");
			return j;
		}

		k.setStatus(2);
		kindclothingInventoryService.save(k);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("处理成功");
		return j;
	}


	/**
	 * 删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingInventory kindclothingInventory, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingInventoryService.delete(kindclothingInventory);
		j.setMsg("删除样衣成功");
		return j;
	}
	
	/**
	 * 批量删除样衣
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingInventoryService.delete(kindclothingInventoryService.get(id));
		}
		j.setMsg("删除样衣成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingInventory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingInventory kindclothingInventory, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingInventory> page = kindclothingInventoryService.findPage(new Page<KindclothingInventory>(request, response, -1), kindclothingInventory);
    		new ExportExcel("样衣", KindclothingInventory.class).setDataList(page.getList()).write(response, fileName).dispose();
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
	@RequiresPermissions("kindclothing:kindclothingInventory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingInventory> list = ei.getDataList(KindclothingInventory.class);
			for (KindclothingInventory kindclothingInventory : list){
				try{
					kindclothingInventoryService.save(kindclothingInventory);
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
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingInventory/?repage";
    }
	
	/**
	 * 下载导入样衣数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingInventory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣数据导入模板.xlsx";
    		List<KindclothingInventory> list = Lists.newArrayList(); 
    		new ExportExcel("样衣数据", KindclothingInventory.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingInventory/?repage";
    }

}