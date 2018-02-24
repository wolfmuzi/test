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
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.entity.Warehouse;
import com.iuling.mods.base.service.ColorsService;
import com.iuling.mods.base.service.FabricService;
import com.iuling.mods.base.service.SupplierService;
import com.iuling.mods.base.service.WarehouseService;
import com.iuling.mods.fabric.entity.FabricStock;
import com.iuling.mods.fabric.service.FabricBindService;
import com.iuling.mods.fabric.service.FabricStockService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 面料色卡管理Controller
 * @author 宋小雄
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/base/fabric")
public class FabricController extends BaseController {

	@Autowired
	private FabricService fabricService;

	@Autowired
	private FabricStockService fabricStockService;
	@Autowired
	private FabricBindService fabricBindService;

	@Autowired
	private SupplierService supplierService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private ColorsService colorsService;

	@ModelAttribute
	public Fabric get(@RequestParam(required=false) String id) {
		Fabric entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricService.get(id);
		}
		if (entity == null){
			entity = new Fabric();
		}
		return entity;
	}
	
	/**
	 * 面料色卡信息列表页面
	 */
	@RequiresPermissions("base:fabric:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/fabricList";
	}
	
		/**
	 * 面料色卡信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:fabric:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Fabric fabric, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Fabric> page = fabricService.findPage(new Page<Fabric>(request, response), fabric); 
		return getBootstrapData(page);
	}

	/**
	 * 面料基础信息数据校验
	 */
	@ResponseBody
	@RequiresPermissions("base:fabric:list")
	@RequestMapping(value = "validDatas")
	public AjaxJson validDatas() {
		AjaxJson j = new AjaxJson();
		//所有样衣信息
		Fabric fabric = new Fabric();
		fabric.setDelFlag("0");
		List<Fabric> list = fabricService.findAllFabricList(fabric);
		//所有库存信息
		FabricStock stock = new FabricStock();
		stock.setDelFlag("0");
		List<FabricStock> stocklist = fabricStockService.findAllFabricStockList(stock);

		//如样衣数量与库存相同，返回成功
		if(list.size() == stocklist.size()){
			j.setMsg("校验面料数据成功");
			return j;
		}

		List<String> temp = new ArrayList<>();
		for (FabricStock s : stocklist){
			temp.add(s.getFabric().getId());
		}

		for (Fabric f : list) {
			if(!temp.contains(f.getId())){
				FabricStock fs = new FabricStock();
				fs.setFabric(f);
				fs.setBarCode(f.getBarcode());
				fs.setShelvesStatus(1);
				fs.setBindStatus(1);
				fs.setName(f.getName());
				fs.setSupplier(f.getSupplier());
				fabricStockService.save(fs);
			}
		}
		j.setMsg("校验面料数据成功");
		return j;
	}

	/**
	 * 查看，增加，编辑面料色卡信息表单页面
	 */
	@RequiresPermissions(value={"base:fabric:view","base:fabric:add","base:fabric:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Fabric fabric, Model model) {
		model.addAttribute("fabric", fabric);
		return "mods/base/fabricForm";
	}

	/**
	 * 保存面料色卡信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:fabric:add","base:fabric:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Fabric fabric, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabric) || fabric.getBarcode() == null || "".equals(fabric.getBarcode())){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		/**
		 * 判断条码是否重复
		 */

		Fabric f = new Fabric();
		f.setBarcode(fabric.getBarcode());
		List<Fabric> list = fabricService.findList(f);
		if (StringUtils.isBlank(fabric.getId())) {
			if (list.size() > 0) {
				j.setSuccess(false);
				j.setMsg("面料编码不能重复！");
				return j;
			}
		}else{
			Fabric fab = fabricService.get(fabric.getId());
			if (list.size() > 0 && !fab.getBarcode().equals(list.get(0).getBarcode())) {
				j.setSuccess(false);
				j.setMsg("面料编码不能重复！");
				return j;
			}
		}
		if (StringUtils.isBlank(fabric.getId())){
			//同步库存表
			fabricService.save(fabric);
			FabricStock stock = new FabricStock();
			stock.setFabric(fabric);
			stock.setBarCode(fabric.getBarcode());
			stock.setShelvesStatus(1);
			stock.setBindStatus(1);
			stock.setName(fabric.getName());
			stock.setSupplier(fabric.getSupplier());
			fabricStockService.save(stock);
		}else{
			fabricService.save(fabric);
			fabricStockService.executeUpdateSql("update fabric_stock set bar_code= '"+fabric.getBarcode()+"',name = '"+fabric.getName()+"',supplier_id='"+fabric.getSupplier().getId()+"' where fabric_id='"+fabric.getId()+"'");
			fabricBindService.executeUpdateSql("update fabric_bind set bar_code= '"+fabric.getBarcode()+"',name = '"+fabric.getName()+"',supplier_id='"+fabric.getSupplier().getId()+"' where fabric_id='"+fabric.getId()+"'");
		}

		j.setSuccess(true);
		j.setMsg("保存面料色卡信息成功");
		return j;
	}
	
	/**
	 * 删除面料色卡信息
	 */
	@ResponseBody
	@RequiresPermissions("base:fabric:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Fabric fabric, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();

		Fabric f = fabricService.get(fabric.getId());
		/**
		 * 同步库存表
		 */
		fabricStockService.executeDeleteSql("delete from fabric_stock where bar_code = '"+f.getBarcode()+"'");


		fabricService.delete(fabric);
		j.setMsg("删除面料色卡信息成功");
		return j;
	}
	
	/**
	 * 批量删除面料色卡信息
	 */
	@ResponseBody
	@RequiresPermissions("base:fabric:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){

			Fabric f = fabricService.get(id);
			fabricStockService.executeDeleteSql("delete from fabric_stock where bar_code = '"+f.getBarcode()+"'");
			fabricService.delete(fabricService.get(id));
		}
		j.setMsg("删除面料色卡信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:fabric:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Fabric fabric, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "面料信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Fabric> page = fabricService.findPage(new Page<Fabric>(request, response, -1), fabric);

    		new ExportExcel("面料信息", Fabric.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出面料色卡信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:fabric:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;
			String mess = null;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Fabric> list = ei.getDataList(Fabric.class);
			for (Fabric fabric : list){
				try{
					Fabric f = new Fabric();
					f.setBarcode(fabric.getBarcode());
					List<Fabric> temp = fabricService.findList(f);
					if (temp.size() > 0) {//如果有重复
						chong++;
						continue;
					}

					//根据名称获取供应商
					if(fabric.getSupplier() != null &&fabric.getSupplier().getName() != null) {
						Supplier supplier = new Supplier();
						supplier.setName(fabric.getSupplier().getName());
						List<Supplier> suppliers = supplierService.findList(supplier);
						if(suppliers != null && suppliers.size()>0){
							fabric.setSupplier(suppliers.get(0));
						}else{
							if(mess==null){
								mess = supplier.getName();
							}else {
								mess += ","+supplier.getName() ;
							}
							failureNum++;
							continue;
						}
					}
					//根据编码获取颜色
					if(fabric.getColor() != null &&fabric.getColor().getNumber() != null) {
						Colors color = new Colors();
						color.setNumber(fabric.getColor().getNumber());
						List<Colors> colors = colorsService.findList(color);
						if(colors != null && colors.size()>0){
							fabric.setColor(colors.get(0));
						}
					}

					//根据名称获取仓库
					if(fabric.getWarehouse() != null &&fabric.getWarehouse().getName() != null) {
						Warehouse warehouse = new Warehouse();
						warehouse.setName(fabric.getWarehouse().getName());
						List<Warehouse> warehouses = warehouseService.findList(warehouse);
						if(warehouses != null && warehouses.size()>0){
							fabric.setWarehouse(warehouses.get(0));
						}
					}

					fabricService.save(fabric);
					/**
					 * 同步库存表
					 */
					FabricStock stock = new FabricStock();
					stock.setFabric(fabric);
					stock.setBarCode(fabric.getBarcode());
					stock.setShelvesStatus(1);
					stock.setBindStatus(1);
					stock.setName(fabric.getName());
					stock.setSupplier(fabric.getSupplier());

					fabricStockService.save(stock);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					ex.printStackTrace();
					failureNum++;
				}
			}
			if (failureNum>0){
				if(mess != null){
					mess = "["+ mess + "]未在供应商列表，请先手动添加！";
					failureMsg.insert(0, "，失败 "+failureNum+" 条面料信息记录。"+mess);
				}else {
					failureMsg.insert(0, "，失败 " + failureNum + " 条面料信息记录。");
				}
			}
			String msg = "";
			if(chong > 0){
				msg = ","+chong+"条记录重复条码未导入";
			}
			if(msg != null) {
				addMessage(redirectAttributes, "已成功导入 " + successNum + " 条面料信息记录" + failureMsg + msg);
			}else {
				addMessage(redirectAttributes, "已成功导入 " + successNum + " 条面料信息记录" + failureMsg);
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入面料信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/fabric/?repage";
    }
	
	/**
	 * 下载导入面料色卡信息数据模板
	 */
	@RequiresPermissions("base:fabric:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "面料信息数据导入模板.xlsx";
    		List<Fabric> list = Lists.newArrayList(); 
    		new ExportExcel("面料信息数据", Fabric.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/fabric/?repage";
    }

}