/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.web;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.iuling.mods.base.entity.KindClothing;
import com.iuling.mods.base.entity.Supplier;
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
import com.iuling.mods.base.entity.Sizes;
import com.iuling.mods.base.service.SizesService;

/**
 * 尺码信息管理Controller
 * @author 彭成
 * @version 2017-10-19
 */
@Controller
@RequestMapping(value = "${adminPath}/base/sizes")
public class SizesController extends BaseController {

	@Autowired
	private SizesService sizesService;
	
	@ModelAttribute
	public Sizes get(@RequestParam(required=false) String id) {
		Sizes entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sizesService.get(id);
		}
		if (entity == null){
			entity = new Sizes();
		}
		return entity;
	}
	
	/**
	 * 尺码信息列表页面
	 */
	@RequiresPermissions("base:sizes:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/sizesList";
	}
	
		/**
	 * 尺码信息列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:sizes:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Sizes sizes, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Sizes> page = sizesService.findPage(new Page<Sizes>(request, response), sizes); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑尺码信息表单页面
	 */
	@RequiresPermissions(value={"base:sizes:view","base:sizes:add","base:sizes:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(Sizes sizes, Model model) {
		model.addAttribute("sizes", sizes);
		return "mods/base/sizesForm";
	}

	/**
	 * 保存尺码信息
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:sizes:add","base:sizes:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(Sizes sizes, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, sizes)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 判断尺码编码是否重复
		 */
		Sizes s = new Sizes();
		s.setCode(sizes.getCode());
		List<Sizes> list = sizesService.findList(s);
		if (StringUtils.isBlank(sizes.getId())) {
			if (list.size() > 0) {
				j.setSuccess(false);
				j.setMsg("尺码代码不能重复！");
				return j;
			}
		}else{
			Sizes si = sizesService.get(sizes.getId());
			if (list.size() > 0 && !si.getCode().equals(list.get(0).getCode())) {
				j.setSuccess(false);
				j.setMsg("尺码代码不能重复！");
				return j;
			}
		}
		/**
		 * 判断尺码名称是否重复
		 */
		Sizes ss = new Sizes();
		ss.setName(sizes.getName());
		List<Sizes> lists = sizesService.findList(ss);
		if (StringUtils.isBlank(sizes.getId())) {
			if (lists.size() > 0) {
				j.setSuccess(false);
				j.setMsg("尺码名称不能重复！");
				return j;
			}
		}else{
			Sizes sis = sizesService.get(sizes.getId());
			if (lists.size() > 0 && !sis.getName().equals(lists.get(0).getName())) {
				j.setSuccess(false);
				j.setMsg("尺码名称不能重复！");
				return j;
			}
		}

		sizesService.save(sizes);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存尺码信息成功");
		return j;
	}
	
	/**
	 * 删除尺码信息
	 */
	@ResponseBody
	@RequiresPermissions("base:sizes:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(Sizes sizes, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		sizesService.delete(sizes);
		j.setMsg("删除尺码信息成功");
		return j;
	}
	
	/**
	 * 批量删除尺码信息
	 */
	@ResponseBody
	@RequiresPermissions("base:sizes:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sizesService.delete(sizesService.get(id));
		}
		j.setMsg("删除尺码信息成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:sizes:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(Sizes sizes, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "尺码信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<Sizes> page = sizesService.findPage(new Page<Sizes>(request, response, -1), sizes);
    		new ExportExcel("尺码信息", Sizes.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出尺码信息记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:sizes:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;    //重复次数
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<Sizes> list = ei.getDataList(Sizes.class);
			for (Sizes sizes : list){
				try{
					Sizes s = new Sizes();
					s.setCode(sizes.getCode());  //判断code是否重复
					List<Sizes> temp = sizesService.findList(s);
					if (temp.size() > 0) {//如果有重复
						chong++;
						continue;
					}
					Sizes ss = new Sizes();
					ss.setName(sizes.getName());  //判断name是否重复
					List<Sizes> temps = sizesService.findList(ss);
					if (temps.size() > 0) {//如果有重复
						chong++;
						continue;
					}
					sizesService.save(sizes);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			String msg = "";   //重复提示
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条尺码信息记录。");
			}
			if(chong > 0){
				msg = chong+"条记录重复数据未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条尺码信息记录"+failureMsg+","+msg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入尺码信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/sizes/?repage";
    }
	
	/**
	 * 下载导入尺码信息数据模板
	 */
	@RequiresPermissions("base:sizes:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "尺码信息数据导入模板.xlsx";
    		List<Sizes> list = Lists.newArrayList(); 
    		new ExportExcel("尺码信息数据", Sizes.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/sizes/?repage";
    }

}