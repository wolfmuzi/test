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
import com.iuling.mods.base.entity.*;
import com.iuling.mods.base.service.*;
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.service.KindclothingBindService;
import com.iuling.mods.clothingmanageformj.service.KindclothingStockService;
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
 * 样衣基础信息管理Controller
 * @author 宋小雄
 * @version 2017-09-19
 */
@Controller
@RequestMapping(value = "${adminPath}/base/kindClothing")
public class KindClothingController extends BaseController {

	@Autowired
	private KindClothingService kindClothingService;
	@Autowired
	private KindclothingStockService kindclothingStockService;
	@Autowired
	private KindclothingBindService kindclothingBindService;
	@Autowired
	private SupplierService supplierService;

	@Autowired
	private WarehouseService warehouseService;

	@Autowired
	private ColorsService colorsService;
	@Autowired
	private SizesService sizesService;
	@Autowired
	private MaxClassService maxClassService;

	@ModelAttribute
	public KindClothing get(@RequestParam(required=false) String id) {
		KindClothing entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindClothingService.get(id);
		}
		if (entity == null){
			entity = new KindClothing();
		}
		return entity;
	}
	
	/**
	 * 样衣基础信息列表页面
	 */
	@RequiresPermissions("base:kindClothing:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/kindClothingList";
	}
	
		/**
	 * 样衣基础信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:kindClothing:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindClothing kindClothing, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindClothing> page = kindClothingService.findPage(new Page<KindClothing>(request, response), kindClothing); 
		return getBootstrapData(page);
	}

	/**
	 * 样衣基础信息数据校验
	 */
	@ResponseBody
	@RequiresPermissions("base:kindClothing:list")
	@RequestMapping(value = "validDatas")
	public AjaxJson validDatas() {
		AjaxJson j = new AjaxJson();
		//所有样衣信息
		List<KindClothing> list = kindClothingService.findAllKindClothing();
		//所有库存信息
		List<KindclothingStock> stocklist = kindclothingStockService.findAllKindClothingStock();

		//如样衣数量与库存相同，返回成功
		if(list.size() == stocklist.size()){
			j.setMsg("校验样衣数据成功");
			return j;
		}

		List<String> temp = new ArrayList<>();
		for (KindclothingStock s : stocklist){
			temp.add(s.getKindClothing().getId());
		}

		for (KindClothing k : list) {
			if(!temp.contains(k.getId())){
				KindclothingStock stock = new KindclothingStock();
				stock.setKindClothing(k);
				stock.setBarCode(k.getNumber());
				stock.setShelvesStatus(1);
				stock.setBindStatus(1);
				stock.setName(k.getName());
				stock.setIsLend("1");
				stock.setSupplier(k.getSupplier());
				kindclothingStockService.save(stock);
			}
		}
		j.setMsg("校验样衣数据成功");
		return j;
	}

	/**
	 * 查看，增加，编辑样衣基础信息表单页面
	 */
	@RequiresPermissions(value={"base:kindClothing:view","base:kindClothing:add","base:kindClothing:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindClothing kindClothing, Model model) {
		if(kindClothing.getWarehouse() == null || StringUtils.isBlank(kindClothing.getId())){
			Warehouse warehouse = warehouseService.getByName("广州仓");
			kindClothing.setWarehouse(warehouse);
		}
		model.addAttribute("kindClothing", kindClothing);
		return "mods/base/kindClothingForm";
	}

	/**
	 * 保存样衣基础信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:kindClothing:add","base:kindClothing:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindClothing kindClothing, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindClothing)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 默认广州仓
		 */
		if(StringUtils.isBlank(kindClothing.getWarehouse().getName())){
			Warehouse warehouse = warehouseService.getByName("广州仓");
			kindClothing.setWarehouse(warehouse);
		}

		/**
		 * 判断条码是否重复
		 */
		KindClothing f = new KindClothing();
		f.setNumber(kindClothing.getNumber());
		List<KindClothing> list = kindClothingService.findList(f);
		if (StringUtils.isBlank(kindClothing.getId())) {
			if (list.size() > 0) {
				j.setSuccess(false);
				j.setMsg("样衣编码不能重复！");
				return j;
			}
		}else{
			KindClothing fab = kindClothingService.get(kindClothing.getId());
			if (list.size() > 0 && !fab.getNumber().equals(list.get(0).getNumber())) {
				j.setSuccess(false);
				j.setMsg("样衣编码不能重复！");
				return j;
			}
		}

		if (StringUtils.isBlank(kindClothing.getId())){
			//同步库存表
			kindClothingService.save(kindClothing);//新建或者编辑保存
			KindclothingStock stock = new KindclothingStock();
			stock.setKindClothing(kindClothing);
			stock.setBarCode(kindClothing.getNumber());
			stock.setShelvesStatus(1);
			stock.setBindStatus(1);
			stock.setName(kindClothing.getName());
			stock.setIsLend("1");
			stock.setSupplier(kindClothing.getSupplier());
			kindclothingStockService.save(stock);
		}else{
			kindClothingService.save(kindClothing);//新建或者编辑保存
			kindclothingStockService.executeUpdateSql("update kindclothing_stock set bar_code='"+kindClothing.getNumber()+"',name = '"+kindClothing.getName()+"',supplier_id='"+kindClothing.getSupplier().getId()+"' where kindclothing_id='"+kindClothing.getId()+"'");
			kindclothingBindService.executeUpdateSql("update kindclothing_bind set bar_code='"+kindClothing.getNumber()+"',name = '"+kindClothing.getName()+"',supplier_id='"+kindClothing.getSupplier().getId()+"' where kindclothing_id='"+kindClothing.getId()+"'");

		}

		j.setSuccess(true);
		j.setMsg("保存样衣基础信息成功");
		return j;
	}
	
	/**
	 * 删除样衣基础信息
	 */
	@ResponseBody
	@RequiresPermissions("base:kindClothing:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindClothing kindClothing, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();


		/**
		 * 同步库存表
		 */
		KindClothing f = kindClothingService.get(kindClothing.getId());
		kindclothingStockService.executeDeleteSql("delete from kindclothing_stock where bar_code = '"+f.getNumber()+"'");
		kindClothingService.delete(kindClothing);
		j.setMsg("删除样衣基础信息成功");
		return j;
	}
	
	/**
	 * 批量删除样衣基础信息
	 */
	@ResponseBody
	@RequiresPermissions("base:kindClothing:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){

			/**
			 * 同步库存表
			 */
			KindClothing f = kindClothingService.get(id);
			kindclothingStockService.executeDeleteSql("delete from kindclothing_stock where bar_code = '"+f.getNumber()+"'");
			kindClothingService.delete(kindClothingService.get(id));
		}
		j.setMsg("删除样衣基础信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:kindClothing:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindClothing kindClothing, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣基础信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindClothing> page = kindClothingService.findPage(new Page<KindClothing>(request, response, -1), kindClothing);
    		new ExportExcel("样衣基础信息", KindClothing.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣基础信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("base:kindClothing:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;
			String mess = null;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindClothing> list = ei.getDataList(KindClothing.class);
			for (KindClothing kindClothing : list){
				try{
					KindClothing f = new KindClothing();
					f.setNumber(kindClothing.getNumber());
					List<KindClothing> temp = kindClothingService.findList(f);
					if (temp.size() > 0) {//如果有重复
						chong++;
						continue;
					}
					//根据名称获取供应商
					if(kindClothing.getSupplier() != null && kindClothing.getSupplier().getName() != null) {
						Supplier supplier = new Supplier();
						supplier.setName(kindClothing.getSupplier().getName());
						List<Supplier> suppliers = supplierService.findList(supplier);
						if(suppliers != null && suppliers.size()>0){
							kindClothing.setSupplier(suppliers.get(0));
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
					if(kindClothing.getColour() != null &&kindClothing.getColour().getNumber() != null) {
						Colors color = new Colors();
						color.setNumber(kindClothing.getColour().getNumber());
						List<Colors> colors = colorsService.findList(color);
						if(colors != null && colors.size()>0){
							kindClothing.setColour(colors.get(0));
						}
					}
					//根据名称获取仓库
					if(kindClothing.getWarehouse() != null &&kindClothing.getWarehouse().getName() != null) {
						Warehouse warehouse = new Warehouse();
						warehouse.setName(kindClothing.getWarehouse().getName());
						List<Warehouse> warehouses = warehouseService.findList(warehouse);
						if(warehouses != null && warehouses.size()>0){
							kindClothing.setWarehouse(warehouses.get(0));
						}
					}
					//根据编号获取尺码
					if(kindClothing.getSizes() != null &&kindClothing.getSizes().getCode() != null) {
						Sizes sizes = new Sizes();
						sizes.setCode(kindClothing.getSizes().getCode());
						List<Sizes> sizesList = sizesService.findList(sizes);
						if(sizesList != null && sizesList.size()>0){
							kindClothing.setSizes(sizesList.get(0));
						}
					}
					//根据编号获取大类
					if(kindClothing.getMaxClass() != null &&kindClothing.getMaxClass().getCode() != null) {
						MaxClass maxClass = new MaxClass();
						maxClass.setCode(kindClothing.getMaxClass().getCode());
						List<MaxClass> maxClassesList = maxClassService.findList(maxClass);
						if(maxClassesList != null && maxClassesList.size()>0){
							kindClothing.setMaxClass(maxClassesList.get(0));
						}
					}

					kindClothingService.save(kindClothing);
					/**
					 * 同步库存表
					 */
					KindclothingStock stock = new KindclothingStock();
					stock.setBarCode(kindClothing.getNumber());
					stock.setShelvesStatus(1);
					stock.setKindClothing(kindClothing);
					stock.setBindStatus(1);
					stock.setIsLend("1");
					stock.setName(kindClothing.getName());
					stock.setSupplier(kindClothing.getSupplier());
					kindclothingStockService.save(stock);
					successNum++;
				}catch(ConstraintViolationException ex){
					ex.printStackTrace();
					failureNum++;
				}catch (Exception ex) {
					ex.printStackTrace();
					failureNum++;
				}
			}
			if (failureNum>0){
				if(mess != null){
					mess = "["+ mess + "]未在供应商列表，请先手动添加！";
					failureMsg.insert(0, "，失败 "+failureNum+" 条样衣基础信息记录。"+ mess);
				}else{
					failureMsg.insert(0, "，失败 "+failureNum+" 条样衣基础信息记录。");
				}
			}

			String msg = null;
			if(chong > 0){
				msg = ","+chong+"条记录重复数据未导入";
			}
			if(msg != null){
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣基础信息记录"+failureMsg + msg);
			}else {
				addMessage(redirectAttributes, "已成功导入 " + successNum + " 条样衣基础信息记录" + failureMsg);
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣基础信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/kindClothing/?repage";
    }
	
	/**
	 * 下载导入样衣基础信息数据模板
	 */
	@RequiresPermissions("base:kindClothing:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣基础信息数据导入模板.xlsx";
    		List<KindClothing> list = Lists.newArrayList(); 
    		new ExportExcel("样衣基础信息数据", KindClothing.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/kindClothing/?repage";
    }

}