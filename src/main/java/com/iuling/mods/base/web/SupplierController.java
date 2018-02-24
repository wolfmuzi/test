/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.web;

import com.google.common.collect.Lists;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.service.SupplierService;
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
 * 供应商管理Controller
 * @author 宋小雄
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/base/supplier")
public class SupplierController extends BaseController {

	@Autowired
	private SupplierService supplierService;
	
	@ModelAttribute
	public Supplier get(@RequestParam(required=false) String id) {
		Supplier entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = supplierService.get(id);
		}
		if (entity == null){
			entity = new Supplier();
		}
		return entity;
	}
	
	/**
	 * 供应商信息列表页面
	 */
	@RequiresPermissions("base:supplier:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/supplierList";
	}
	
		/**
	 * 供应商信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:supplier:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Supplier supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Supplier> page = supplierService.findPage(new Page<Supplier>(request, response), supplier); 
		return getBootstrapData(page);
	}

		/**
	 * 供应商信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:supplier:list")
	@RequestMapping(value = "data1/{type}")
	public Map<String, Object> data1(@PathVariable Integer type, Supplier supplier, HttpServletRequest request, HttpServletResponse response, Model model) {
		if(type != null){
			supplier.setType(type);
		}
		Page<Supplier> page = supplierService.findPage(new Page<Supplier>(request, response), supplier);
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑供应商信息表单页面
	 */
	@RequiresPermissions(value={"base:supplier:view","base:supplier:add","base:supplier:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Supplier supplier, Model model) {
		model.addAttribute("supplier", supplier);
		return "mods/base/supplierForm";
	}

	/**
	 * 保存供应商信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:supplier:add","base:supplier:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Supplier supplier, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, supplier)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 判断条码是否重复
		 */
		Supplier s = new Supplier();
		s.setNumber(supplier.getNumber());
		List<Supplier> list = supplierService.findList(s);
		if(list.size() > 0 && !list.get(0).getId().equals(supplier.getId())){
			j.setSuccess(false);
			j.setMsg("供应商编码已存在！");
			return j;
		}

		supplierService.save(supplier);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存供应商信息成功");
		return j;
	}
	
	/**
	 * 删除供应商信息
	 */
	@ResponseBody
	@RequiresPermissions("base:supplier:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Supplier supplier, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		supplierService.delete(supplier);
		j.setMsg("删除供应商信息成功");
		return j;
	}
	
	/**
	 * 批量删除供应商信息
	 */
	@ResponseBody
	@RequiresPermissions("base:supplier:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			supplierService.delete(supplierService.get(id));
		}
		j.setMsg("删除供应商信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:supplier:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Supplier supplier, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "供应商信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Supplier> page = supplierService.findPage(new Page<Supplier>(request, response, -1), supplier);
    		new ExportExcel("供应商信息", Supplier.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出供应商信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:supplier:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Supplier> list = ei.getDataList(Supplier.class);
			for (Supplier supplier : list){
				try{
					Supplier f = new Supplier();
					f.setNumber(supplier.getNumber());
					List<Supplier> temp = supplierService.findList(f);
					if (temp.size() > 0) {//如果有重复
						chong++;
						continue;
					}
					supplierService.save(supplier);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条供应商信息记录。");
			}
			String msg = "";
			if(chong > 0){
				msg = chong+"条记录重复条码未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条供应商信息记录"+failureMsg+" , "+msg);

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入供应商信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/supplier/?repage";
    }
	
	/**
	 * 下载导入供应商信息数据模板
	 */
	@RequiresPermissions("base:supplier:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "供应商信息数据导入模板.xlsx";
    		List<Supplier> list = Lists.newArrayList(); 
    		new ExportExcel("供应商信息数据", Supplier.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/supplier/?repage";
    }

}