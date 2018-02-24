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
import com.iuling.mods.clothingmanageformj.entity.KindclothingLendItem;
import com.iuling.mods.clothingmanageformj.entity.KindclothingReturn;
import com.iuling.mods.clothingmanageformj.mapper.KindclothingLendItemMapper;
import com.iuling.mods.clothingmanageformj.service.KindclothingReturnService;
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
 * 样衣归还管理Controller
 * @author 彭成
 * @version 2017-09-23
 */
@Controller
@RequestMapping(value = "${adminPath}/kindclothing/kindclothingReturn")
public class KindclothingReturnController extends BaseController {

	@Autowired
	private KindclothingReturnService kindclothingReturnService;
	@Autowired
	private KindclothingLendItemMapper kindclothingLendItemMapper;

	@ModelAttribute
	public KindclothingReturn get(@RequestParam(required=false) String id) {
		KindclothingReturn entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = kindclothingReturnService.get(id);
		}
		if (entity == null){
			entity = new KindclothingReturn();
		}
		return entity;
	}

	/**
	 * 样衣归还列表页面
	 */
	@RequiresPermissions("kindclothing:kindclothingReturn:list")
	@RequestMapping(value = {"list", ""})
	public String list() {
		return "mods/kindclothing/kindclothingReturnList";
	}
	
		/**
	 * 样衣归还列表数据
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(KindclothingReturn kindclothingReturn, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<KindclothingReturn> page = kindclothingReturnService.findPage(new Page<KindclothingReturn>(request, response), kindclothingReturn);

		int nums = 0;
		BaseCommonRole b1 = null;
		BaseCommonRole b2 = null;

		KindclothingLendItem kindclothingLendItem = new KindclothingLendItem();
		kindclothingLendItem.setLendIsReturn(1);

		for(KindclothingReturn k: page.getList()){
			kindclothingLendItem.setBaseCommonRole(k.getBaseCommonRole());
			b1 = kindclothingLendItem.getBaseCommonRole();
			b2 = kindclothingReturn.getBaseCommonRole();
			if( b1 != b2) {  //如同一人,则不进行查询
				nums = kindclothingLendItemMapper.sumByBaseCommonRoleId(kindclothingLendItem);  //获得用户未归还总数
			}
			k.setSurplusNum(nums);
		}
		return getBootstrapData(page);
	}

	/**
	 * 查看，增加，编辑样衣归还表单页面
	 */
	@RequiresPermissions(value={"kindclothing:kindclothingReturn:view","kindclothing:kindclothingReturn:add","kindclothing:kindclothingReturn:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(KindclothingReturn kindclothingReturn, Model model) {
		model.addAttribute("kindclothingReturn", kindclothingReturn);
		return "mods/kindclothing/kindclothingReturnForm";
	}

	/**
	 * 保存样衣归还
	 */
	@ResponseBody
	@RequiresPermissions(value={"kindclothing:kindclothingReturn:add","kindclothing:kindclothingReturn:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public AjaxJson save(KindclothingReturn kindclothingReturn, Model model, RedirectAttributes redirectAttributes) throws Exception{
		AjaxJson j = new AjaxJson();
		if (!beanValidator(model, kindclothingReturn)){
			j.setSuccess(false);
			j.setMsg("非法参数！");
			return j;
		}
		kindclothingReturnService.save(kindclothingReturn);//新建或者编辑保存
		j.setSuccess(true);
		j.setMsg("保存样衣归还成功");
		return j;
	}
	
	/**
	 * 删除样衣归还
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:del")
	@RequestMapping(value = "delete")
	public AjaxJson delete(KindclothingReturn kindclothingReturn, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		kindclothingReturnService.delete(kindclothingReturn);
		j.setMsg("删除样衣归还成功");
		return j;
	}
	
	/**
	 * 批量删除样衣归还
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			kindclothingReturnService.delete(kindclothingReturnService.get(id));
		}
		j.setMsg("删除样衣归还成功");
		return j;
	}
	
	/**
	 * 导出excel文件
	 */
	@ResponseBody
	@RequiresPermissions("kindclothing:kindclothingReturn:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public AjaxJson exportFile(KindclothingReturn kindclothingReturn, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson j = new AjaxJson();
		try {
            String fileName = "样衣归还"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<KindclothingReturn> page = kindclothingReturnService.findPage(new Page<KindclothingReturn>(request, response, -1), kindclothingReturn);
    		new ExportExcel("样衣归还", KindclothingReturn.class).setDataList(page.getList()).write(response, fileName).dispose();
    		j.setSuccess(true);
    		j.setMsg("导出成功！");
    		return j;
		} catch (Exception e) {
			j.setSuccess(false);
			j.setMsg("导出样衣归还记录失败！失败信息："+e.getMessage());
		}
			return j;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("kindclothing:kindclothingReturn:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<KindclothingReturn> list = ei.getDataList(KindclothingReturn.class);
			for (KindclothingReturn kindclothingReturn : list){
				try{
					kindclothingReturnService.save(kindclothingReturn);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条样衣归还记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条样衣归还记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入样衣归还失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingReturn/?repage";
    }
	
	/**
	 * 下载导入样衣归还数据模板
	 */
	@RequiresPermissions("kindclothing:kindclothingReturn:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "样衣归还数据导入模板.xlsx";
    		List<KindclothingReturn> list = Lists.newArrayList(); 
    		new ExportExcel("样衣归还数据", KindclothingReturn.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/kindclothing/kindclothingReturn/?repage";
    }

}