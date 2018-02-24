/**
 * Copyright &copy; 2015-2020 <a href="http://www.iuling.com/">Iuling</a> All rights reserved.
 */
package com.iuling.mods.sys.security;

import com.iuling.comm.json.AjaxJson;
import com.iuling.comm.json.PrintJSON;
import com.iuling.comm.utils.StringUtils;
import com.iuling.mods.base.entity.Equipment;
import com.iuling.mods.base.entity.EquipmentUse;
import com.iuling.mods.base.service.EquipmentService;
import com.iuling.mods.base.service.EquipmentUseService;
import com.iuling.mods.sys.entity.Menu;
import com.iuling.mods.sys.entity.User;
import com.iuling.mods.sys.mapper.MenuMapper;
import com.iuling.mods.sys.mapper.UserMapper;
import com.iuling.mods.sys.security.SystemAuthorizingRealm.Principal;
import com.iuling.mods.sys.utils.UserUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.beans.Transient;
import java.util.*;

/**
 * 表单验证（包含验证码）过滤类
 * @author iuling
 * @version 2017-5-19
 */
@Service
public class FormAuthenticationFilter extends org.apache.shiro.web.filter.authc.FormAuthenticationFilter {

	public static final String DEFAULT_CAPTCHA_PARAM = "validateCode";
	public static final String DEFAULT_MOBILE_PARAM = "mobileLogin";
	public static final String DEFAULT_MESSAGE_PARAM = "message";

	private String captchaParam = DEFAULT_CAPTCHA_PARAM;
	private String mobileLoginParam = DEFAULT_MOBILE_PARAM;
	private String messageParam = DEFAULT_MESSAGE_PARAM;
	@Autowired
	private EquipmentService equipmentService;
	@Autowired
	private MenuMapper menuMapper;



	@Autowired
	private EquipmentUseService equipmentUseService;
	@Autowired
	private UserMapper userMapper;

	@Transient
	protected AuthenticationToken createToken(ServletRequest request, ServletResponse response) {
		String username = getUsername(request);
		String password = getPassword(request);
		if (password==null){
			password = "";
		}
		boolean rememberMe = isRememberMe(request);
		String host = StringUtils.getRemoteAddr((HttpServletRequest)request);
		String captcha = getCaptcha(request);
		boolean mobile = isMobileLogin(request);
		if(mobile){
			//如果是手机记录设备使用记录
			HttpServletRequest req = (HttpServletRequest)request;
			String code = req.getParameter("code");
			Equipment equipment = new Equipment();
			equipment.setNumber(code);
			List<Equipment> equipments = equipmentService.findList(equipment);
			if(equipments != null && equipments.size() > 0){
				//如果找到这个设备就把他状态改为使用中 并且 记录使用记录
				Equipment e = equipments.get(0);
				String status = e.getStatus();
				e.setStatus("1");
				equipmentService.save(e);
				if("2".equals(status)) {
					//记录使用记录
					EquipmentUse equipmentUse = new EquipmentUse();
					equipmentUse.setEquipment(e);
					equipmentUse.setStartTime(new Date());
					User user = new User();
					if ("admin".equals(username)) {
						user.setId("1");
						equipmentUse.setUser(user);
					} else {
						user.setLoginName(username);
						List<User> users = userMapper.findList(user);
						equipmentUse.setUser(users.get(0));
					}
					equipmentUseService.save(equipmentUse);
				}
			}
		}
		return new UsernamePasswordToken(username, password.toCharArray(), rememberMe, host, captcha, mobile);
	}

	public String getCaptchaParam() {
		return captchaParam;
	}

	protected String getCaptcha(ServletRequest request) {
		return WebUtils.getCleanParam(request, getCaptchaParam());
	}

	public String getMobileLoginParam() {
		return mobileLoginParam;
	}
	
	protected boolean isMobileLogin(ServletRequest request) {
        return WebUtils.isTrue(request, getMobileLoginParam());
    }
	
	public String getMessageParam() {
		return messageParam;
	}
	
	/**
	 * 登录成功之后跳转URL
	 */
	public String getSuccessUrl() {
		return super.getSuccessUrl();
	}
	
	@Override
	protected void issueSuccessRedirect(ServletRequest request,
			ServletResponse response) throws Exception {
		Principal p = UserUtils.getPrincipal();
		if (p != null && !p.isMobileLogin()){
			 WebUtils.issueRedirect(request, response, getSuccessUrl(), null, true);
		}else{
			AjaxJson j = new AjaxJson();
			j.setSuccess(true);
			j.setMsg("登录成功!");
			j.put("username", p.getLoginName());
			j.put("name", p.getName());
			j.put("mobileLogin", p.isMobileLogin());
			j.put("JSESSIONID", p.getSessionid());
			/**
			 * 查询这个用户所有的权限
			 */
			Menu m = new Menu();
			m.setUserId(p.getId());
			List<Menu> menus = menuMapper.findByUserId(m);
			List<Map<String,String>> menuList = new ArrayList<>();
			for(Menu menu:menus){
				String temp = menu.getPermission();
				if(temp != null && temp.indexOf(":list")!= -1){
					Map<String,String> map = new HashMap<>();
					map.put("permission",temp);
					map.put("name",menu.getName());
					menuList.add(map);
				}

			}

			j.put("menuList",menuList);
			PrintJSON.write((HttpServletResponse)response, j.getJsonStr());
		}
	}

	/**
	 * 登录失败调用事件
	 */
	@Override
	protected boolean onLoginFailure(AuthenticationToken token,
			AuthenticationException e, ServletRequest request, ServletResponse response) {
		String className = e.getClass().getName(), message = "";
		if (IncorrectCredentialsException.class.getName().equals(className)
				|| UnknownAccountException.class.getName().equals(className)){
			message = "用户或密码错误, 请重试.";
		}
		else if (e.getMessage() != null && StringUtils.startsWith(e.getMessage(), "msg:")){
			message = StringUtils.replace(e.getMessage(), "msg:", "");
		}
		else{
			message = "系统出现点问题，请稍后再试！";
			e.printStackTrace(); // 输出到控制台
		}
        request.setAttribute(getFailureKeyAttribute(), className);
        request.setAttribute(getMessageParam(), message);
        return true;
	}
	
}