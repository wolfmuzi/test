/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

import com.google.common.collect.Lists;
import com.iuling.comm.config.Global;
import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.utils.DateUtils;
import com.iuling.comm.utils.StringUtils;
import com.iuling.comm.utils.excel.ExportExcel;
import com.iuling.comm.utils.excel.ImportExcel;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.fabric.entity.FabricInventoryItme;
import com.iuling.mods.fabric.service.FabricInventoryItmeService;
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
 * 色卡盘点详情Controller
 * @author 潘俞再
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricInventoryItme")
public class FabricInventoryItmeController extends BaseController {

	@Autowired
	private FabricInventoryItmeService fabricInventoryItmeService;
	
	@ModelAttribute
	public FabricInventoryItme get(@RequestParam(required=false) String id) {
		FabricInventoryItme entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricInventoryItmeService.get(id);
		}
		if (entity == null){
			entity = new FabricInventoryItme();
		}
		return entity;
	}

	/**
	 * 色卡盘点详情列表页面
	 */
	@RequiresPermissions("fabric:fabricInventory:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricInventoryItmeList";
	}



	/**
	 * 详情页面跳转
	 */
	@RequiresPermissions("fabric:fabricInventory:list")
	@RequestMapping(value = {"toDetail/{code}/{type}", "GET"})
	public String toDetail(@PathVariable String code,@PathVariable Integer type,HttpServletRequest request) {

		request.setAttribute("code",code);
		request.setAttribute("type",type);
		return "mods/fabric/fabricInventoryItmeList";
	}
	
		/**
	 * 色卡盘点详情列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricInventoryItme fabricInventoryItme, HttpServletRequest request, HttpServletResponse response, Model model) {
//		Page<Fabric> page = new Page(request, response);
//		Fabric entity = new Fabric();
//		entity.setPage(page);
//		page.setList(fabricInventoryItmeService.findByParam(fabricInventoryItme));
		Fabric fabric = new Fabric();
		if(fabricInventoryItme.getCodes() != null && fabricInventoryItme.getCodes() != ""){
			fabric.setCode(fabricInventoryItme.getCodes());
		}
		if(fabricInventoryItme.getTypes() != null && fabricInventoryItme.getTypes() != ""){
			fabric.setType(fabricInventoryItme.getTypes());
		}
		if(fabricInventoryItme.getFabric() != null && fabricInventoryItme.getFabric().getBarcode() != ""
				&& fabricInventoryItme.getFabric().getBarcode() != null){
			fabric.setBarcode(fabricInventoryItme.getFabric().getBarcode());
		}
		return getBootstrapData(fabricInventoryItmeService.findPage(new Page<Fabric>(request, response),fabric));
	}

	/**
	 * 查看，增加，编辑色卡盘点详情表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricInventory:view","fabric:fabricInventory:add","fabric:fabricInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricInventoryItme fabricInventoryItme, Model model) {
		model.addAttribute("fabricInventoryItme", fabricInventoryItme);
		return "mods/fabric/fabricInventoryItmeForm";
	}

	/**
	 * 保存色卡盘点详情
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricInventory:add","fabric:fabricInventory:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricInventoryItme fabricInventoryItme, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricInventoryItme)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricInventoryItmeService.save(fabricInventoryItme);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存色卡盘点详情成功");
		return j;
	}
	
	/**
	 * 删除色卡盘点详情
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricInventoryItme fabricInventoryItme, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricInventoryItmeService.delete(fabricInventoryItme);
		j.setMsg("删除色卡盘点详情成功");
		return j;
	}
	
	/**
	 * 批量删除色卡盘点详情
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricInventoryItmeService.delete(fabricInventoryItmeService.get(id));
		}
		j.setMsg("删除色卡盘点详情成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricInventory:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricInventoryItme fabricInventoryItme, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "色卡盘点详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricInventoryItme> page = fabricInventoryItmeService.findPage(new Page<FabricInventoryItme>(request, response, -1), fabricInventoryItme);
    		new ExportExcel("色卡盘点详情", FabricInventoryItme.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出色卡盘点详情记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricInventory:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricInventoryItme> list = ei.getDataList(FabricInventoryItme.class);
			for (FabricInventoryItme fabricInventoryItme : list){
				try{
					fabricInventoryItmeService.save(fabricInventoryItme);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条色卡盘点详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条色卡盘点详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入色卡盘点详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricInventoryItme/?repage";
    }
	
	/**
	 * 下载导入色卡盘点详情数据模板
	 */
	@RequiresPermissions("fabric:fabricInventory:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "色卡盘点详情数据导入模板.xlsx";
    		List<FabricInventoryItme> list = Lists.newArrayList(); 
    		new ExportExcel("色卡盘点详情数据", FabricInventoryItme.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricInventoryItme/?repage";
    }

}