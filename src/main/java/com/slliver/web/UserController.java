package com.slliver.web;

import com.slliver.Utils.RC4Util;
import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.constant.DeviceContant;
import com.slliver.common.paging.PageWapper;
import com.slliver.common.utils.CipherUtil;
import com.slliver.common.utils.ContextUtil;
import com.slliver.entity.ApiUser;
import com.slliver.service.ApiUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/22 14:16
 * @version: 1.0
 */
@Controller
@RequestMapping("/user")
public class UserController extends WebBaseController<ApiUser> {

	@Autowired
	private ApiUserService userService;

	@RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
	public String list(Model model, BaseSearchCondition condition) {
		PageWapper<ApiUser> pageWapper = new PageWapper<>();
		ApiUser user = ContextUtil.getCurrentUser();
		if (StringUtils.equals(user.getRole(), "ADMIN")) {
//			condition.setChannelNo(null);
			pageWapper = this.userService.selectListByPage(condition);
		} else {
			if (StringUtils.equals(user.getChannelNo(), condition.getChannelNo())) {
				condition.setFlagDelete((short)0);
				pageWapper = this.userService.selectListByPage(condition);
			}
		}
		model.addAttribute("dataList", pageWapper.getList());
		model.addAttribute("pagnation", pageWapper.getPagingHtml());
		model.addAttribute("condition", condition);
		// 设备类型
		model.addAttribute("deviceList", DeviceContant.getDeviceMap());
		return getViewPath("list");
	}

	@RequestMapping(value = "/channellist", method = {RequestMethod.POST, RequestMethod.GET})
	public String channellist(Model model, BaseSearchCondition condition) {
		PageWapper<ApiUser> pageWapper = new PageWapper<>();
		ApiUser user = ContextUtil.getCurrentUser();
		if (StringUtils.equals(user.getRole(), "ADMIN")) {
			condition.setChannelNo(null);
			pageWapper = this.userService.selectListByPage(condition);
		} else {
			if (StringUtils.equals(user.getChannelNo(), condition.getChannelNo())) {
				condition.setFlagDelete((short)0);
				pageWapper = this.userService.selectListByPage(condition);
			}
		}

		model.addAttribute("dataList", pageWapper.getList());
		model.addAttribute("pagnation", pageWapper.getPagingHtml());
		model.addAttribute("condition", condition);
		// 设备类型
		model.addAttribute("deviceList", DeviceContant.getDeviceMap());
		return getViewPath("list");
	}

	@Override
	protected String getPath() {
		return "/user";
	}

	public static void main(String[] args) {
		String userName = "13800138000";
		String pwrsMD5 = CipherUtil.generatePassword("111111");
		String salt = CipherUtil.createSalt();
		String password = CipherUtil.createPwdEncrypt(userName, pwrsMD5, salt);
		System.out.println(salt);
		System.out.println("password == " + password);
	}
}
