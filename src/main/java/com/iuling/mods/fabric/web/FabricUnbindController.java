/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.fabric.web;

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
import com.iuling.mods.fabric.entity.FabricUnbind;
import com.iuling.mods.fabric.service.FabricUnbindService;

/**
 * 解绑色卡记录Controller
 * @author 潘俞再
 * @version 2017-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricUnbind")
public class FabricUnbindController extends BaseController {

	@Autowired
	private FabricUnbindService fabricUnbindService;
	
	@ModelAttribute
	public FabricUnbind get(@RequestParam(required=false) String id) {
		FabricUnbind entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricUnbindService.get(id);
		}
		if (entity == null){
			entity = new FabricUnbind();
		}
		return entity;
	}
	
	/**
	 * 解绑记录列表页面
	 */
	@RequiresPermissions("fabric:fabricUnbind:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricUnbindList";
	}
	
		/**
	 * 解绑记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricUnbind fabricUnbind, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricUnbind> page = fabricUnbindService.findPage(new Page<FabricUnbind>(request, response), fabricUnbind); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑解绑记录表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricUnbind:view","fabric:fabricUnbind:add","fabric:fabricUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricUnbind fabricUnbind, Model model) {
		model.addAttribute("fabricUnbind", fabricUnbind);
		return "mods/fabric/fabricUnbindForm";
	}

	/**
	 * 保存解绑记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricUnbind:add","fabric:fabricUnbind:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricUnbind fabricUnbind, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricUnbind)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricUnbindService.save(fabricUnbind);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存解绑记录成功");
		return j;
	}
	
	/**
	 * 删除解绑记录
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricUnbind fabricUnbind, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricUnbindService.delete(fabricUnbind);
		j.setMsg("删除解绑记录成功");
		return j;
	}
	
	/**
	 * 批量删除解绑记录
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricUnbindService.delete(fabricUnbindService.get(id));
		}
		j.setMsg("删除解绑记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricUnbind:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricUnbind fabricUnbind, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "解绑记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricUnbind> page = fabricUnbindService.findPage(new Page<FabricUnbind>(request, response, -1), fabricUnbind);
    		new ExportExcel("解绑记录", FabricUnbind.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出解绑记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricUnbind:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricUnbind> list = ei.getDataList(FabricUnbind.class);
			for (FabricUnbind fabricUnbind : list){
				try{
					fabricUnbindService.save(fabricUnbind);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条解绑记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条解绑记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入解绑记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricUnbind/?repage";
    }
	
	/**
	 * 下载导入解绑记录数据模板
	 */
	@RequiresPermissions("fabric:fabricUnbind:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "解绑记录数据导入模板.xlsx";
    		List<FabricUnbind> list = Lists.newArrayList(); 
    		new ExportExcel("解绑记录数据", FabricUnbind.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricUnbind/?repage";
    }

}