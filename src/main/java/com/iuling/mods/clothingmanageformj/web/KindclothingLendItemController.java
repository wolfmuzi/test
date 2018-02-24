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
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.service.KindclothingLendItemService;
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
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 样衣借用详情Controller
 * @author 彭成
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingLendItem")
public class KindclothingLendItemController extends BaseController {

	@Autowired
	private KindclothingLendItemService kindclothingLendItemService;
	
	@ModelAttribute
	public KindclothingLendItem get(@RequestParam(required=false) String id) {
		KindclothingLendItem entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingLendItemService.get(id);
		}
		if (entity == null){
			entity = new KindclothingLendItem();
		}
		return entity;
	}


	/**
	 * 跳转借用详情页,本次借用
	 */
	@RequiresPermissions("kindclothing:kindclothingLendItem:list")
	@RequestMapping(value = {"toDetail/{lendNo}", "GET"})
	public String toDetail(@PathVariable String lendNo, HttpServletRequest request){
		request.setAttribute("lendNo",lendNo);
		return "mods/kindclothing/kindclothingLendItemList";
	}

	/**
	 * 跳转借用详情页,借用总数
	 */
	@RequiresPermissions("kindclothing:kindclothingLendItem:list")
	@RequestMapping(value = {"toDetails/{id}/{type}", "GET"})
	public String toDetails(@PathVariable String id,@PathVariable String type, HttpServletRequest request){
		request.setAttribute("type",type);
		request.setAttribute("id",id);
		request.setAttribute("lendIsReturn",1);
		return "mods/kindclothing/kindclothingLendItemList";
	}

	/**
	 * 样衣借用详情列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingLendItem:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingLendItemList";
	}
	
	/**
	 * 样衣借用详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLendItem:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingLendItem kindclothingLendItem, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingLendItem> page = kindclothingLendItemService.findPage(new Page<KindclothingLendItem>(request, response), kindclothingLendItem);

		Date lendDate = null; //借用时间
		Date today = new Date();
		Long diff = null; //两个时间的差
		Long days = null; //两个时间的差换算为天
		Long day = null;  //天数
		String deadline = null; //剩余期限

		for(KindclothingLendItem item : page.getList()){
			day = 7L;
			lendDate = item.getCreateDate();
			diff = today.getTime() - lendDate.getTime();
			days = diff / (1000 * 60 * 60 * 24);
			if(days <= 7L){
				day = day - days;
				/*if(day == 0){
					day++;
				}*/
				deadline = day + "天";
			}else if(days > 7L){
				day = days - day;
				deadline = "逾期"+ day + "天";
			}
			item.setSurplusDate(deadline);   //录入剩余期限
		}

		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣借用详情表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingLendItem:view","kindclothing:kindclothingLendItem:add","kindclothing:kindclothingLendItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingLendItem kindclothingLendItem, Model model) {
		model.addAttribute("kindclothingLendItem", kindclothingLendItem);
		return "mods/kindclothing/kindclothingLendItemForm";
	}

	/**
	 * 保存样衣借用详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingLendItem:add","kindclothing:kindclothingLendItem:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingLendItem kindclothingLendItem, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingLendItem)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingLendItemService.save(kindclothingLendItem);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣借用详情成功");
		return j;
	}
	
	/**
	 * 删除样衣借用详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLendItem:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingLendItem kindclothingLendItem, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingLendItemService.delete(kindclothingLendItem);
		j.setMsg("删除样衣借用详情成功");
		return j;
	}
	
	/**
	 * 批量删除样衣借用详情
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLendItem:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingLendItemService.delete(kindclothingLendItemService.get(id));
		}
		j.setMsg("删除样衣借用详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLendItem:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingLendItem kindclothingLendItem, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣借用详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingLendItem> page = kindclothingLendItemService.findPage(new Page<KindclothingLendItem>(request, response, -1), kindclothingLendItem);
    		new ExportExcel("样衣借用详情", KindclothingLendItem.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣借用详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingLendItem:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingLendItem> list = ei.getDataList(KindclothingLendItem.class);
			for (KindclothingLendItem kindclothingLendItem : list){
				try{
					kindclothingLendItemService.save(kindclothingLendItem);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条样衣借用详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣借用详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣借用详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingLendItem/?repage";
    }
	
	/**
	 * 下载导入样衣借用详情数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingLendItem:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣借用详情数据导入模板.xlsx";
    		List<KindclothingLendItem> list = Lists.newArrayList(); 
    		new ExportExcel("样衣借用详情数据", KindclothingLendItem.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingLendItem/?repage";
    }

}