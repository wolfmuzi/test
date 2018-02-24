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
import com.iuling.mods.base.entity.OrderCode;
import com.iuling.mods.base.service.OrderCodeService;

/**
 * 生成各类单号Controller
 * @author 潘俞再
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/base/orderCode")
public class OrderCodeController extends BaseController {

	@Autowired
	private OrderCodeService orderCodeService;
	
	@ModelAttribute
	public OrderCode get(@RequestParam(required=false) String id) {
		OrderCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = orderCodeService.get(id);
		}
		if (entity == null){
			entity = new OrderCode();
		}
		return entity;
	}
	
	/**
	 * 单号列表页面
	 */
	@RequiresPermissions("base:orderCode:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/orderCodeList";
	}
	
		/**
	 * 单号列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:orderCode:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(OrderCode orderCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<OrderCode> page = orderCodeService.findPage(new Page<OrderCode>(request, response), orderCode); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑单号表单页面
	 */
	@RequiresPermissions(value={"base:orderCode:view","base:orderCode:add","base:orderCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(OrderCode orderCode, Model model) {
		model.addAttribute("orderCode", orderCode);
		return "mods/base/orderCodeForm";
	}

	/**
	 * 保存单号
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:orderCode:add","base:orderCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(OrderCode orderCode, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, orderCode)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		orderCodeService.save(orderCode);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存单号成功");
		return j;
	}
	
	/**
	 * 删除单号
	 */
	@ResponseBody
	@RequiresPermissions("base:orderCode:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(OrderCode orderCode, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		orderCodeService.delete(orderCode);
		j.setMsg("删除单号成功");
		return j;
	}
	
	/**
	 * 批量删除单号
	 */
	@ResponseBody
	@RequiresPermissions("base:orderCode:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			orderCodeService.delete(orderCodeService.get(id));
		}
		j.setMsg("删除单号成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:orderCode:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(OrderCode orderCode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "单号"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<OrderCode> page = orderCodeService.findPage(new Page<OrderCode>(request, response, -1), orderCode);
    		new ExportExcel("单号", OrderCode.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出单号记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:orderCode:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<OrderCode> list = ei.getDataList(OrderCode.class);
			for (OrderCode orderCode : list){
				try{
					orderCodeService.save(orderCode);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条单号记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条单号记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入单号失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/orderCode/?repage";
    }
	
	/**
	 * 下载导入单号数据模板
	 */
	@RequiresPermissions("base:orderCode:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "单号数据导入模板.xlsx";
    		List<OrderCode> list = Lists.newArrayList(); 
    		new ExportExcel("单号数据", OrderCode.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/orderCode/?repage";
    }

}