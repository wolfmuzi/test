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
import com.iuling.mods.clothingmanageformj.entity.KindclothingStock;
import com.iuling.mods.clothingmanageformj.param.KindclothingLendParam;
import com.iuling.mods.clothingmanageformj.service.KindclothingReturnService;
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
import java.util.List;
import java.util.Map;

/**
 * 样衣库存Controller
 * @author 彭成
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingStock")
public class KindclothingStockController extends BaseController {

	@Autowired
	private KindclothingStockService kindclothingStockService;
	@Autowired
	private KindclothingReturnService kindclothingReturnService;

	@ModelAttribute
	public KindclothingStock get(@RequestParam(required=false) String id) {
		KindclothingStock entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingStockService.get(id);
		}
		if (entity == null){
			entity = new KindclothingStock();
		}
		return entity;
	}
	
	/**
	 *  库存清单列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingStock:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingStockList";
	}
	
		/**
	 *  库存清单列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingStock kindclothingStock, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingStock> page = kindclothingStockService.findPage(new Page<KindclothingStock>(request, response), kindclothingStock);

		List<KindclothingLendItem> list = null;
		KindclothingLendParam param = new KindclothingLendParam();
		String baseCommonRole = null;
		for(KindclothingStock k : page.getList()){
			if(("2").equals(k.getIsLend()) ){
				param.setBarCode1(k.getBarCode());
				list = kindclothingReturnService.findByBarCodeCommonRole(param);
				baseCommonRole = list.get(0).getBaseCommonRole().getName();
				k.setBaseCommonRole(baseCommonRole);
			}
		}

		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑 库存清单表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingStock:view","kindclothing:kindclothingStock:add","kindclothing:kindclothingStock:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingStock kindclothingStock, Model model) {
		model.addAttribute("kindclothingStock", kindclothingStock);
		return "mods/kindclothing/kindclothingStockForm";
	}

	/**
	 * 保存 库存清单
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingStock:add","kindclothing:kindclothingStock:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingStock kindclothingStock, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingStock)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingStockService.save(kindclothingStock);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存 库存清单成功");
		return j;
	}
	
	/**
	 * 删除 库存清单
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingStock kindclothingStock, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingStockService.delete(kindclothingStock);
		j.setMsg("删除 库存清单成功");
		return j;
	}
	
	/**
	 * 批量删除 库存清单
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingStockService.delete(kindclothingStockService.get(id));
		}
		j.setMsg("删除 库存清单成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingStock:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingStock kindclothingStock, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = " 库存清单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingStock> page = kindclothingStockService.findPage(new Page<KindclothingStock>(request, response, -1), kindclothingStock);
    		new ExportExcel(" 库存清单", KindclothingStock.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出 库存清单记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingStock:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingStock> list = ei.getDataList(KindclothingStock.class);
			for (KindclothingStock kindclothingStock : list){
				try{
					kindclothingStockService.save(kindclothingStock);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条 库存清单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条 库存清单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入 库存清单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingStock/?repage";
    }
	
	/**
	 * 下载导入 库存清单数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingStock:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = " 库存清单数据导入模板.xlsx";
    		List<KindclothingStock> list = Lists.newArrayList(); 
    		new ExportExcel(" 库存清单数据", KindclothingStock.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingStock/?repage";
    }

}