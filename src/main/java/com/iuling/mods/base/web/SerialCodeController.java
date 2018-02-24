/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Jeehop</a> All rights reserved.
 */
package com.iuling.mods.base.web;

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
import com.iuling.mods.base.entity.SerialCode;
import com.iuling.mods.base.service.SerialCodeService;

/**
 * 流水号编码信息Controller
 * @author 宋小雄
 * @version 2017-09-18
 */
@Controller
@RequestMapping(value = "${adminPath}/base/serialCode")
public class SerialCodeController extends BaseController {

	@Autowired
	private SerialCodeService serialCodeService;
	
	@ModelAttribute
	public SerialCode get(@RequestParam(required=false) String id) {
		SerialCode entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serialCodeService.get(id);
		}
		if (entity == null){
			entity = new SerialCode();
		}
		return entity;
	}
	
	/**
	 * 流水号编码列表页面
	 */
	@RequiresPermissions("base:serialCode:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/base/serialCodeList";
	}
	
		/**
	 * 流水号编码列表数据
	 */
	@ResponseBody
	@RequiresPermissions("base:serialCode:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(SerialCode serialCode, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerialCode> page = serialCodeService.findPage(new Page<SerialCode>(request, response), serialCode); 
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑流水号编码表单页面
	 */
	@RequiresPermissions(value={"base:serialCode:view","base:serialCode:add","base:serialCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerialCode serialCode, Model model) {
		model.addAttribute("serialCode", serialCode);
		return "mods/base/serialCodeForm";
	}

	/**
	 * 保存流水号编码
	 */
	@ResponseBody
	@RequiresPermissions(value={"base:serialCode:add","base:serialCode:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(SerialCode serialCode, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, serialCode)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		serialCodeService.save(serialCode);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存流水号编码成功");
		return j;
	}
	
	/**
	 * 删除流水号编码
	 */
	@ResponseBody
	@RequiresPermissions("base:serialCode:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(SerialCode serialCode, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		serialCodeService.delete(serialCode);
		j.setMsg("删除流水号编码成功");
		return j;
	}
	
	/**
	 * 批量删除流水号编码
	 */
	@ResponseBody
	@RequiresPermissions("base:serialCode:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serialCodeService.delete(serialCodeService.get(id));
		}
		j.setMsg("删除流水号编码成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("base:serialCode:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(SerialCode serialCode, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "流水号编码"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerialCode> page = serialCodeService.findPage(new Page<SerialCode>(request, response, -1), serialCode);
    		new ExportExcel("流水号编码", SerialCode.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出流水号编码记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("base:serialCode:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SerialCode> list = ei.getDataList(SerialCode.class);
			for (SerialCode serialCode : list){
				try{
					serialCodeService.save(serialCode);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条流水号编码记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条流水号编码记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入流水号编码失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/serialCode/?repage";
    }
	
	/**
	 * 下载导入流水号编码数据模板
	 */
	@RequiresPermissions("base:serialCode:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "流水号编码数据导入模板.xlsx";
    		List<SerialCode> list = Lists.newArrayList(); 
    		new ExportExcel("流水号编码数据", SerialCode.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/base/serialCode/?repage";
    }

}