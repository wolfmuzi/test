/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.mods.sys.web;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iuling.comm.json.AjaxJson;
import com.iuling.core.persistence.Page;
import com.iuling.core.web.BaseController;
import com.iuling.mods.sys.entity.Log;
import com.iuling.mods.sys.service.LogService;

/**
 * 日志Controller
 * @author iuling
 * @version 2016-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:list")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		return "mods/sys/log/logList";
	}

	@ResponseBody
	@RequiresPermissions("sys:log:list")
	@RequestMapping(value = "data")
	public Map<String, Object> data(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
		return super.getBootstrapData(page);
	}

	
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequiresPermissions("sys:log:del")
	@RequestMapping(value = "deleteAll")
	public AjaxJson deleteAll(String ids) {
		AjaxJson j = new AjaxJson();
		String idArray[] =ids.split(",");
		for(String id : idArray){
			logService.delete(logService.get(id));
		}
		j.setSuccess(true);
		j.setMsg("删除日志成功！");
		return j;
	}
	
	/**
	 * 批量删除
	 */
	@ResponseBody
	@RequiresPermissions("sys:log:del")
	@RequestMapping(value = "empty")
	public AjaxJson empty() {
		AjaxJson j = new AjaxJson();
		logService.empty();
		j.setSuccess(true);
		j.setMsg("清空日志成功!");
		return j;
	}
}
