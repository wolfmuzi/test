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
import com.iuling.mods.base.service.ColorsService;
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
 * 颜色信息管理Controller
 * @author 宋小雄
 * @version 2017-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/base/colors")
public class ColorsController extends BaseController {

	@Autowired
	private ColorsService colorsService;
	
	@ModelAttribute
	public Colors get(@RequestParam(required=false) String id) {
		Colors entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = colorsService.get(id);
		}
		if (entity == null){
			entity = new Colors();
		}
		return entity;
	}
	
	/**
	 * 颜色信息列表页面
	 */
	@RequiresPermissions("base:colors:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/colorsList";
	}
	
		/**
	 * 颜色信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:colors:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Colors colors, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Colors> page = colorsService.findPage(new Page<Colors>(request, response), colors); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑颜色信息表单页面
	 */
	@RequiresPermissions(value={"base:colors:view","base:colors:add","base:colors:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Colors colors, Model model) {
		model.addAttribute("colors", colors);
		return "mods/base/colorsForm";
	}

	/**
	 * 保存颜色信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:colors:add","base:colors:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Colors colors, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, colors)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 判断条码是否重复
		 */
		Colors f = new Colors();
		f.setNumber(colors.getNumber());
		List<Colors> list = colorsService.findList(f);
		if (StringUtils.isBlank(colors.getId())) {
			if (list.size() > 0) {
				j.setSuccess(false);
				j.setMsg("颜色编码不能重复！");
				return j;
			}
		}else{
			Colors fab = colorsService.get(colors.getId());
			if (list.size() > 0 && !fab.getNumber().equals(list.get(0).getNumber())) {
				j.setSuccess(false);
				j.setMsg("颜色编码不能重复！");
				return j;
			}
		}
		colorsService.save(colors);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存颜色信息成功");
		return j;
	}
	
	/**
	 * 删除颜色信息
	 */
	@ResponseBody
	@RequiresPermissions("base:colors:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Colors colors, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		colorsService.delete(colors);
		j.setMsg("删除颜色信息成功");
		return j;
	}
	
	/**
	 * 批量删除颜色信息
	 */
	@ResponseBody
	@RequiresPermissions("base:colors:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			colorsService.delete(colorsService.get(id));
		}
		j.setMsg("删除颜色信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:colors:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Colors colors, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "颜色信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Colors> page = colorsService.findPage(new Page<Colors>(request, response, -1), colors);
    		new ExportExcel("颜色信息", Colors.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出颜色信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:colors:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Colors> list = ei.getDataList(Colors.class);
			for (Colors colors : list){
				try{
					Colors f=colorsService.findUniqueByProperty("number",colors.getNumber());
					if (f != null){//如果有重复
						chong++;
						continue;
					}
//					Colors f = new Colors();
//					f.setNumber(colors.getNumber());
//					List<Colors> temp = colorsService.findList(f);
//					if (temp.size() > 0) {//如果有重复
//						chong++;
//						continue;
//					}
					colorsService.save(colors);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条颜色信息记录。");
			}
			String msg = "";
			if(chong > 0){
				msg = chong+"条记录重复条码未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条颜色信息记录"+failureMsg+" , "+msg);

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入颜色信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/colors/?repage";
    }
	
	/**
	 * 下载导入颜色信息数据模板
	 */
	@RequiresPermissions("base:colors:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "颜色信息数据导入模板.xlsx";
    		List<Colors> list = Lists.newArrayList(); 
    		new ExportExcel("颜色信息数据", Colors.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/colors/?repage";
    }

}