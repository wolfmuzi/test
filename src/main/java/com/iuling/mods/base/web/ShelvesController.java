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
import com.iuling.mods.base.entity.Colors;
import com.iuling.mods.base.entity.Shelves;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.entity.Warehouse;
import com.iuling.mods.base.service.ColorsService;
import com.iuling.mods.base.service.ShelvesService;
import com.iuling.mods.base.service.SupplierService;
import com.iuling.mods.base.service.WarehouseService;
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
 * 货架管理Controller
 * @author 潘俞再
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/base/shelves")
public class ShelvesController extends BaseController {

	@Autowired
	private ShelvesService shelvesService;
	@Autowired
	private SupplierService supplierService;

	@Autowired
	private WarehouseService warehouseService;


	@ModelAttribute
	public Shelves get(@RequestParam(required=false) String id) {
		Shelves entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = shelvesService.get(id);
		}
		if (entity == null){
			entity = new Shelves();
		}
		return entity;
	}
	
	/**
	 * 货架列表页面
	 */
	@RequiresPermissions("base:shelves:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/shelvesList";
	}
	
		/**
	 * 货架列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Shelves shelves, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Shelves> page = shelvesService.findPage(new Page<Shelves>(request, response), shelves); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑货架表单页面
	 */
	@RequiresPermissions(value={"base:shelves:view","base:shelves:add","base:shelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Shelves shelves, Model model) {
		model.addAttribute("shelves", shelves);
		return "mods/base/shelvesForm";
	}

	/**
	 * 保存货架
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:shelves:add","base:shelves:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Shelves shelves, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, shelves)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		if(shelves.getCode() == null || "".equals(shelves.getCode())){
			shelves.setIsBind(1);
		}else{

			/**
			 * 判断条码是否重复
			 */
			Shelves s = new Shelves();
			s.setCode(shelves.getCode());
			List<Shelves> list = shelvesService.findList(s);
			if(list.size() > 0 && !list.get(0).getId().equals(shelves.getId())){
				j.setSuccess(false);
				j.setMsg("货架条码已存在！");
				return j;
			}
			shelves.setIsBind(2);
		}

		shelvesService.save(shelves);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存货架成功");
		return j;
	}
	
	/**
	 * 删除货架
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Shelves shelves, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		shelvesService.delete(shelves);
		j.setMsg("删除货架成功");
		return j;
	}
	
	/**
	 * 批量删除货架
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			shelvesService.delete(shelvesService.get(id));
		}
		j.setMsg("删除货架成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:shelves:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Shelves shelves, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "货架"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Shelves> page = shelvesService.findPage(new Page<Shelves>(request, response, -1), shelves);
    		new ExportExcel("货架", Shelves.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出货架记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:shelves:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		int chong = 0;
		try {
			int successNum = 0;
			int failureNum = 0;

			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Shelves> list = ei.getDataList(Shelves.class);

			for (Shelves shelves : list){
				if(shelves.getCode() == null || "".equals(shelves.getCode())){
					shelves.setIsBind(1);
				}else{
					shelves.setIsBind(2);
					/**
					 * 判断条码是否重复
					 */
					Shelves s = new Shelves();
					s.setCode(shelves.getCode());
					List<Shelves> shelvess = shelvesService.findList(s);
					if(shelvess.size() > 0){
						chong++;
						continue;
					}
				}
				try{


					//根据编码获取供应商
					if(shelves.getSupplier() != null &&shelves.getSupplier().getNumber() != null) {
						Supplier supplier = new Supplier();
						supplier.setNumber(shelves.getSupplier().getNumber());
						List<Supplier> suppliers = supplierService.findList(supplier);
						if(suppliers != null && suppliers.size()>0){
							shelves.setSupplier(suppliers.get(0));
						}
					}


					//根据编码获取仓库

					if(shelves.getWarehouse() != null &&shelves.getWarehouse().getNumber() != null) {
						Warehouse warehouse = new Warehouse();
						warehouse.setNumber(shelves.getWarehouse().getNumber());
						List<Warehouse> warehouses = warehouseService.findList(warehouse);
						if(warehouses != null && warehouses.size()>0){
							shelves.setWarehouse(warehouses.get(0));
						}
					}
					shelvesService.save(shelves);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条货架记录。");
			}
			String msg = "";
			if(chong > 0){
				msg = chong+"条记录重复条码未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条货架记录"+failureMsg +" , "+ msg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入货架失败！失败信息："+e.getMessage());
		}


		return "redirect:"+Global.getAdminPath()+"/base/shelves/?repage";
    }
	
	/**
	 * 下载导入货架数据模板
	 */
	@RequiresPermissions("base:shelves:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "货架数据导入模板.xlsx";
    		List<Shelves> list = Lists.newArrayList(); 
    		new ExportExcel("货架数据", Shelves.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/shelves/?repage";
    }

}