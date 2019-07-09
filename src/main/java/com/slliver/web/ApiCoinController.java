package com.slliver.web;

import com.slliver.Utils.EncryptUtil;
import com.slliver.base.domain.BaseEvent;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.entity.*;
import com.slliver.service.AlipayService;
import com.slliver.service.ApiUserCoinHistoryService;
import com.slliver.service.ApiUserCoinService;
import com.slliver.service.ApiUserCoinWithdrawService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/coin")
public class ApiCoinController {

	@Autowired
	private ApiUserCoinService apiUserCoinService;

	@Autowired
	private ApiUserCoinHistoryService apiUserCoinHistoryService;

	@Autowired
	private ApiUserCoinWithdrawService apiUserCoinWithdrawService;

	@RequestMapping(value = "/total")
	@ResponseBody
	public ApiRichResult total(BaseSearchCondition condition) {
		ApiRichResult result = new ApiRichResult();
		ApiUserCoin data = apiUserCoinService.selectUserCoin(condition);
		BigDecimal bg = new BigDecimal(data.getBalance());
		String banlance = bg.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
		data.setBalance(banlance);
		data.setRecord(false);
		result.setSucceed(data, "获取数据成功~");
		return result;
	}

	@RequestMapping("/signlist")
	@ResponseBody
	public ApiRichResult signlist(String phone) {
		ApiRichResult result = new ApiRichResult();
		List list = apiUserCoinHistoryService.obtainSignList(phone);
		if (list != null) {
			result.setSucceed(list, "获取数据成功");
		} else {
			result.setFailMsg("获取数据失败");
		}
		return result;
	}

	@RequestMapping("/currenttime")
	@ResponseBody
	public ApiRichResult currenttime() {
		ApiRichResult result = new ApiRichResult();
		result.setSucceed(System.currentTimeMillis() + "", "获取数据成功");

		return result;
	}

	@RequestMapping("/videolefttime")
	@ResponseBody
	public ApiRichResult videolefttime(String phone) {
		ApiRichResult result = new ApiRichResult();
		String leftTime = apiUserCoinHistoryService.obtainVideoLeftTime(phone);
		result.setSucceed(leftTime, "获取数据成功");

		return result;
	}

	@RequestMapping("/luckycount")
	@ResponseBody
	public ApiRichResult luckycount(String phone) {
		ApiRichResult result = new ApiRichResult();
		ApiLuckyCount count = apiUserCoinHistoryService.obtainLuckycount(phone);
		result.setSucceed(count, "获取数据成功");

		return result;
	}

	@RequestMapping("/box")
	@ResponseBody
	public ApiRichResult box(String phone) {
		ApiRichResult result = new ApiRichResult();
		int []box = apiUserCoinHistoryService.obtainBox(phone);
		result.setSucceed(box, "获取数据成功");

		return result;
	}

	@RequestMapping(value = "/event")
	@ResponseBody
	public ApiRichResult event(BaseEvent event) {
		ApiRichResult result = new ApiRichResult();
		String hash = EncryptUtil.toMD5(event.getPhone() + event.getTimestamp() + EncryptUtil.PRIVATE_KEY);
		if (!StringUtils.equals(hash, event.getHash())) {
			result.setFailMsg("签名验证失败");
			return result;
		}
		List list = apiUserCoinHistoryService.obtainCoinHistoryViaTimestamp(event.getPhone(), event.getTimestamp());
		if (list != null && list.size() > 0) {
			result.setFailMsg("上报事件重复");
			return result;
		}

		return apiUserCoinHistoryService.saveEvent(event);
//		eventCoin.setEvent(event.getEvent());
//		if (eventCoin == null) {
//			result.setFailMsg("获取数据失败");
//		} else {
//			result.setSucceed(eventCoin, "获取数据成功~");
//		}
//		return result;
	}

	@RequestMapping("/history")
	@ResponseBody
	public ApiRichResult history(BaseSearchCondition condition) {
		ApiRichResult result = new ApiRichResult();
		List list = apiUserCoinHistoryService.obtainCoinHistory(condition);
		if (list == null) {
			result.setFailMsg("获取数据失败");
		} else {
			result.setSucceed(list, "获取数据成功~");
			result.setResCount(apiUserCoinHistoryService.obtainCoinHistoryCount(condition));
		}
		return result;
	}

	@RequestMapping("/user")
	@ResponseBody
	public ApiRichResult withdraw(String phone) {
		ApiRichResult result = new ApiRichResult();
		ApiUserCoin coin = apiUserCoinService.obtainUserCoin(phone);
		if (coin == null) {
			result.setFailMsg("获取数据失败");
		} else {
			result.setSucceed(coin, "获取数据成功");
		}
		return result;
	}

	@RequestMapping("/withdraw")
	@ResponseBody
	public ApiRichResult withdraw(String phone, String amount, String account, String realname) {
		ApiRichResult result = new ApiRichResult();
		if (StringUtils.isEmpty(phone)) {
			result.setFailMsg("手机号不能为空");
			return result;
		}
		if (StringUtils.isEmpty(amount)) {
			result.setFailMsg("金额不能为空");
			return result;
		}
		ApiRichResult ret = apiUserCoinWithdrawService.withdraw(phone, amount, account, realname);


		return ret;
	}

	@RequestMapping("/withdraw_history")
	@ResponseBody
	public ApiRichResult withdrawHistory(BaseSearchCondition condition) {
		ApiRichResult result = new ApiRichResult();
		List list = apiUserCoinWithdrawService.withdrawHistory(condition);
		if (list == null || list.size() == 0) {
			result.setSucceed(new ArrayList<>(),"获取数据成功");
		} else {
			result.setSucceed(list, "获取数据成功");
			result.setResCount(apiUserCoinWithdrawService.withdrawHistoryCount(condition));
		}

		return result;
	}

	@RequestMapping("/add_invate")
	@ResponseBody
	public ApiRichResult addInvate(String phone, String code) {
		ApiRichResult result = apiUserCoinService.addInvate(phone, code);

		return result;
	}

	@RequestMapping("/invate_info")
	@ResponseBody
	public ApiRichResult invateInfo(String phone) {
		ApiRichResult result = apiUserCoinService.obtainInvateInfo(phone);

		return result;
	}
}
