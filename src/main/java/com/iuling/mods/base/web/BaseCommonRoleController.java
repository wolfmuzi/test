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
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.base.entity.Fabric;
import com.iuling.mods.base.entity.Supplier;
import com.iuling.mods.base.service.BaseCommonRoleService;
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
 * 设计师质检员管理Controller
 * @author 彭成
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/base/baseCommonRole")
public class BaseCommonRoleController extends BaseController {

	@Autowired
	private BaseCommonRoleService baseCommonRoleService;
	
	@ModelAttribute
	public BaseCommonRole get(@RequestParam(required=false) String id) {
		BaseCommonRole entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = baseCommonRoleService.get(id);
		}
		if (entity == null){
			entity = new BaseCommonRole();
		}
		return entity;
	}
	
	/**
	 * 设计师质检员列表页面
	 */
	@RequiresPermissions("base:baseCommonRole:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/baseCommonRoleList";
	}
	
		/**
	 * 设计师质检员列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:baseCommonRole:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(BaseCommonRole baseCommonRole, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<BaseCommonRole> page = baseCommonRoleService.findPage(new Page<BaseCommonRole>(request, response), baseCommonRole); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑设计师质检员表单页面
	 */
	@RequiresPermissions(value={"base:baseCommonRole:view","base:baseCommonRole:add","base:baseCommonRole:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(BaseCommonRole baseCommonRole, Model model) {
		model.addAttribute("baseCommonRole", baseCommonRole);
		return "mods/base/baseCommonRoleForm";
	}

	/**
	 * 保存设计师质检员
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:baseCommonRole:add","base:baseCommonRole:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(BaseCommonRole baseCommonRole, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, baseCommonRole)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}

		/**
		 * 判断条码是否重复
		 */
		BaseCommonRole s = new BaseCommonRole();
		s.setCode(baseCommonRole.getCode());
		List<BaseCommonRole> list = baseCommonRoleService.findList(s);
		if(list.size() > 0 && !list.get(0).getId().equals(baseCommonRole.getId())){
			j.setSuccess(false);
			j.setMsg("编码已存在！");
			return j;
		}
		baseCommonRoleService.save(baseCommonRole);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存员工成功");
		return j;
	}
	
	/**
	 * 删除设计师质检员
	 */
	@ResponseBody
	@RequiresPermissions("base:baseCommonRole:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(BaseCommonRole baseCommonRole, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		baseCommonRoleService.delete(baseCommonRole);
		j.setMsg("删除员工成功");
		return j;
	}
	
	/**
	 * 批量删除设计师质检员
	 */
	@ResponseBody
	@RequiresPermissions("base:baseCommonRole:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			baseCommonRoleService.delete(baseCommonRoleService.get(id));
		}
		j.setMsg("删除员工成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:baseCommonRole:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(BaseCommonRole baseCommonRole, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "员工信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<BaseCommonRole> page = baseCommonRoleService.findPage(new Page<BaseCommonRole>(request, response, -1), baseCommonRole);
    		new ExportExcel("员工信息", BaseCommonRole.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出员工员记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:baseCommonRole:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			int chong = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<BaseCommonRole> list = ei.getDataList(BaseCommonRole.class);
			for (BaseCommonRole baseCommonRole : list){
				try{
					BaseCommonRole f = new BaseCommonRole();
					f.setCode(baseCommonRole.getCode());
					List<BaseCommonRole> temp = baseCommonRoleService.findList(f);
					if (temp.size() > 0) {//如果有重复
						chong++;
						continue;
					}
					baseCommonRoleService.save(baseCommonRole);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条设员工记录。");
			}
			String msg = "";
			if(chong > 0){
				msg = chong+"条记录重复条码未导入";
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条员工记录"+failureMsg+" , "+msg);

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入员工失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/baseCommonRole/?repage";
    }
	
	/**
	 * 下载导入设计师质检员数据模板
	 */
	@RequiresPermissions("base:baseCommonRole:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "员工数据导入模板.xlsx";
    		List<BaseCommonRole> list = Lists.newArrayList(); 
    		new ExportExcel("员工数据", BaseCommonRole.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/baseCommonRole/?repage";
    }

}