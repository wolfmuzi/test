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
import com.iuling.mods.fabric.entity.FabricDelete;
import com.iuling.mods.fabric.service.FabricDeleteService;

/**
 * 面料销毁记录Controller
 * @author 潘俞再
 * @version 2017-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/fabric/fabricDelete")
public class FabricDeleteController extends BaseController {

	@Autowired
	private FabricDeleteService fabricDeleteService;
	
	@ModelAttribute
	public FabricDelete get(@RequestParam(required=false) String id) {
		FabricDelete entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fabricDeleteService.get(id);
		}
		if (entity == null){
			entity = new FabricDelete();
		}
		return entity;
	}
	
	/**
	 * 面料销毁记录列表页面
	 */
	@RequiresPermissions("fabric:fabricDelete:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/fabric/fabricDeleteList";
	}
	
		/**
	 * 面料销毁记录列表数据
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricDelete:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(FabricDelete fabricDelete, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FabricDelete> page = fabricDeleteService.findPage(new Page<FabricDelete>(request, response), fabricDelete); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑面料销毁记录表单页面
	 */
	@RequiresPermissions(value={"fabric:fabricDelete:view","fabric:fabricDelete:add","fabric:fabricDelete:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FabricDelete fabricDelete, Model model) {
		model.addAttribute("fabricDelete", fabricDelete);
		return "mods/fabric/fabricDeleteForm";
	}

	/**
	 * 保存面料销毁记录
	 */
	@ResponseBody
	@RequiresPermissions(value={"fabric:fabricDelete:add","fabric:fabricDelete:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(FabricDelete fabricDelete, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, fabricDelete)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		fabricDeleteService.save(fabricDelete);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存面料销毁记录成功");
		return j;
	}
	
	/**
	 * 删除面料销毁记录
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricDelete:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(FabricDelete fabricDelete, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		fabricDeleteService.delete(fabricDelete);
		j.setMsg("删除面料销毁记录成功");
		return j;
	}
	
	/**
	 * 批量删除面料销毁记录
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricDelete:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fabricDeleteService.delete(fabricDeleteService.get(id));
		}
		j.setMsg("删除面料销毁记录成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("fabric:fabricDelete:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(FabricDelete fabricDelete, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "面料销毁记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FabricDelete> page = fabricDeleteService.findPage(new Page<FabricDelete>(request, response, -1), fabricDelete);
    		new ExportExcel("面料销毁记录", FabricDelete.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出面料销毁记录记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("fabric:fabricDelete:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<FabricDelete> list = ei.getDataList(FabricDelete.class);
			for (FabricDelete fabricDelete : list){
				try{
					fabricDeleteService.save(fabricDelete);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条面料销毁记录记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条面料销毁记录记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入面料销毁记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricDelete/?repage";
    }
	
	/**
	 * 下载导入面料销毁记录数据模板
	 */
	@RequiresPermissions("fabric:fabricDelete:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "面料销毁记录数据导入模板.xlsx";
    		List<FabricDelete> list = Lists.newArrayList(); 
    		new ExportExcel("面料销毁记录数据", FabricDelete.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/fabric/fabricDelete/?repage";
    }

}