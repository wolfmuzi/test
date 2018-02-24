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
import com.iuling.mods.base.entity.BaseCommonRole;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLend;
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingLendItemMapper;
import com.iuling.mods.clothingmanageformj.service.KindclothingLendService;
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
 * 样衣借用管理Controller
 * @author 彭成
 * @version 2017-09-22
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingLend")
public class KindclothingLendController extends BaseController {

	@Autowired
	private KindclothingLendService kindclothingLendService;
	@Autowired
	private KindclothingLendItemMapper kindclothingLendItemMapper;

	@ModelAttribute
	public KindclothingLend get(@RequestParam(required=false) String id) {
		KindclothingLend entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingLendService.get(id);
		}
		if (entity == null){
			entity = new KindclothingLend();
		}
		return entity;
	}

	/**
	 * 样衣借用列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingLend:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingLendList";
	}
	
		/**
	 * 样衣借用列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLend:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingLend kindclothingLend, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingLend> page = kindclothingLendService.findPage(new Page<KindclothingLend>(request, response), kindclothingLend);

		int nums = 0;
		BaseCommonRole b1 = null;
		BaseCommonRole b2 = null;

		KindclothingLendItem kindclothingLendItem = new KindclothingLendItem();
		kindclothingLendItem.setLendIsReturn(1);

		for(KindclothingLend k: page.getList()){
			kindclothingLendItem.setBaseCommonRole(k.getBaseCommonRole());
			b1 = kindclothingLendItem.getBaseCommonRole();
			b2 = kindclothingLend.getBaseCommonRole();
			if( b1 != b2) {
				nums = kindclothingLendItemMapper.sumByBaseCommonRoleId(kindclothingLendItem);  //获得用户未归还总数
			}
			k.setLendTotal(nums);
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣借用表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingLend:view","kindclothing:kindclothingLend:add","kindclothing:kindclothingLend:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingLend kindclothingLend, Model model) {
		model.addAttribute("kindclothingLend", kindclothingLend);
		return "mods/kindclothing/kindclothingLendForm";
	}

	/**
	 * 保存样衣借用
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingLend:add","kindclothing:kindclothingLend:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingLend kindclothingLend, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingLend)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingLendService.save(kindclothingLend);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣借用成功");
		return j;
	}
	
	/**
	 * 删除样衣借用
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLend:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingLend kindclothingLend, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingLendService.delete(kindclothingLend);
		j.setMsg("删除样衣借用成功");
		return j;
	}
	
	/**
	 * 批量删除样衣借用
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLend:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingLendService.delete(kindclothingLendService.get(id));
		}
		j.setMsg("删除样衣借用成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingLend:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingLend kindclothingLend, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣借用"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingLend> page = kindclothingLendService.findPage(new Page<KindclothingLend>(request, response, -1), kindclothingLend);
    		new ExportExcel("样衣借用", KindclothingLend.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣借用记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingLend:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingLend> list = ei.getDataList(KindclothingLend.class);
			for (KindclothingLend kindclothingLend : list){
				try{
					kindclothingLendService.save(kindclothingLend);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条样衣借用记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣借用记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣借用失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingLend/?repage";
    }
	
	/**
	 * 下载导入样衣借用数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingLend:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣借用数据导入模板.xlsx";
    		List<KindclothingLend> list = Lists.newArrayList(); 
    		new ExportExcel("样衣借用数据", KindclothingLend.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingLend/?repage";
    }

}