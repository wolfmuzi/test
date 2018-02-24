/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
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
import com.iuling.mods.fabric.entity.FabricBind;
import com.iuling.mods.fabric.service.FabricBindService;

/**
 * 面料色卡绑定记录Controller
 * @author 潘俞再
 * @version 2017-09-21
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricBind")
public class FabricBindController extends BaseController {

	@Autowired
	private FabricBindService fabricBindService;
	
	@ModelAttribute
	public FabricBind get(@RequestParam(required=false) String id) {
		FabricBind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricBindService.get(id);
		}
		if (entity == null){
			entity = new FabricBind();
		}
		return entity;
	}
	
	/**
	 * 色卡列表页面
	 */
	@RequiresPermissions("fabric:fabricBind:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricBindList";
	}
	
		/**
	 * 色卡列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricBind fabricBind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricBind> page = fabricBindService.findPage(new Page<FabricBind>(request, response), fabricBind); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑色卡表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricBind:view","fabric:fabricBind:add","fabric:fabricBind:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricBind fabricBind, Model model) {
		model.addAttribute("fabricBind", fabricBind);
		return "mods/fabric/fabricBindForm";
	}

	/**
	 * 保存色卡
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricBind:add","fabric:fabricBind:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricBind fabricBind, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricBind)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricBindService.save(fabricBind);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存色卡成功");
		return j;
	}
	
	/**
	 * 删除色卡
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricBind fabricBind, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricBindService.delete(fabricBind);
		j.setMsg("删除色卡成功");
		return j;
	}
	
	/**
	 * 批量删除色卡
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricBindService.delete(fabricBindService.get(id));
		}
		j.setMsg("删除色卡成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricBind:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricBind fabricBind, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "色卡"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricBind> page = fabricBindService.findPage(new Page<FabricBind>(request, response, -1), fabricBind);
    		new ExportExcel("色卡", FabricBind.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出色卡记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricBind:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricBind> list = ei.getDataList(FabricBind.class);
			for (FabricBind fabricBind : list){
				try{
					fabricBindService.save(fabricBind);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条色卡记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条色卡记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入色卡失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricBind/?repage";
    }
	
	/**
	 * 下载导入色卡数据模板
	 */
	@RequiresPermissions("fabric:fabricBind:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "色卡数据导入模板.xlsx";
    		List<FabricBind> list = Lists.newArrayList(); 
    		new ExportExcel("色卡数据", FabricBind.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricBind/?repage";
    }

}