package com.slliver.service;

import com.slliver.Utils.CommonUtil;
import com.slliver.Utils.RC4Util;
import com.slliver.base.domain.BaseEvent;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.entity.User;
import com.slliver.base.service.BaseService;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.common.utils.UuidUtil;
import com.slliver.dao.ApiUserCoinMapper;
import com.slliver.dao.ApiUserMapper;
import com.slliver.entity.*;
import org.apache.commons.lang3.StringUtils;
import org.bouncycastle.crypto.engines.RC4Engine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

@Service("apiUserCoinService")
public class ApiUserCoinService extends BaseService<ApiUserCoin> {

	@Autowired
	private ApiUserMapper apiUserMapper;

	@Autowired
	private ApiUserCoinMapper mapper;

	@Autowired
	private ApiUserCoinHistoryService apiUserCoinHistoryService;

	public ApiUserCoin selectUserCoin(BaseSearchCondition condition) {
		ApiUserCoin apiUserCoin = mapper.selectUserCoinByCondition(condition);
		if (apiUserCoin == null) {
			Example userExample = new Example(User.class);
			userExample.createCriteria().andEqualTo("phone", condition.getPhone());
			List<ApiUser> users = apiUserMapper.selectByExample(userExample);
			if (users == null || users.size() == 0) {
				return null;
			}
			apiUserCoin = createUserCoin(users.get(0));
			apiUserCoin = mapper.selectUserCoinByCondition(condition);
		}

		return apiUserCoin;
	}

	public ApiUserCoin obtainUserCoin(String phone) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = apiUserMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}
		ApiUser user = users.get(0);

		Example example = new Example(ApiUserCoin.class);
		example.createCriteria().andEqualTo("userPkid", user.getPkid());

		List<ApiUserCoin> userCoins = mapper.selectByExample(example);
		if (userCoins == null || userCoins.size() == 0) {
			return createUserCoin(user);
		}

		return userCoins.get(0);
	}

	@Transactional
	public ApiRichResult addInvate(String phone, String code) {
		ApiRichResult result = new ApiRichResult();

		ApiUserCoin userCoin = obtainUserCoin(phone);
		if (userCoin == null) {
			result.setFailMsg("用户不存在");
			return result;
		}

		if (StringUtils.equals(code, userCoin.getInvateCode())) {
			result.setFailMsg("邀请人不能为自己");
			return result;
		}

		if (!StringUtils.isEmpty(userCoin.getInvater())) {
			result.setFailMsg("已经存在邀请人");
			return result;
		}

		ApiUserCoin invateUser = obtainUserViaCode(code);
		if (invateUser == null) {
			result.setFailMsg("邀请码不存在");
			return result;
		}

		if (StringUtils.equals(userCoin.getInvateCode(), invateUser.getInvater())) {
			result.setFailMsg("不能添加被邀请人的邀请码");
			return result;
		}

		int todayCount = getTodayInvateCount(code);
		if (todayCount >= 10) {
			result.setFailMsg("今日次数已用完");
			return result;
		}

		userCoin.setInvater(code);
		userCoin.setInvateDate(new Date());
		buildUpdate(userCoin);
		mapper.updateByPrimaryKeySelective(userCoin);

		ApiUser user = apiUserMapper.selectByPrimaryKey(invateUser.getUserPkid());
		if (user == null) {
			result.setFailMsg("用户不存在");
			return result;
		}

		BaseEvent event = new BaseEvent();
		event.setEvent("INVATE");
		event.setPhone(user.getPhone());
		event.setTimestamp(System.currentTimeMillis() + "");
		apiUserCoinHistoryService.saveEvent(event);

		result.setSucceedMsg("添加成功");

		return result;
	}

	public ApiRichResult obtainInvateInfo(String phone) {
		ApiRichResult result = new ApiRichResult();

		ApiUserCoin userCoin = obtainUserCoin(phone);
		if (userCoin == null) {
			result.setFailMsg("用户不存在");
			return result;
		}

		int count = getTodayInvateCount(userCoin.getInvateCode());

		ApiInvateInfo invateInfo = new ApiInvateInfo();
		invateInfo.setCode(userCoin.getInvateCode());
		invateInfo.setInvater(userCoin.getInvater());
		invateInfo.setTotal(10);
		invateInfo.setLeft(10 - count);

		result.setSucceed(invateInfo, "获取数据成功");

		return result;
	}

	private int getTodayInvateCount(String code) {
		long zero = CommonUtil.getStartTime();
		long twelve = CommonUtil.getEndTime();
		Example example = new Example(ApiUserCoin.class);
		example.setOrderByClause("invate_date desc");
		example.createCriteria().andEqualTo("invater", code)
				.andGreaterThanOrEqualTo("invateDate", new Timestamp(zero))
				.andLessThanOrEqualTo("invateDate", new Timestamp(twelve));

		return mapper.selectCountByExample(example);
	}

	private ApiUserCoin obtainUserViaCode(String code) {
		Example example = new Example(ApiUserCoin.class);
		example.createCriteria().andEqualTo("invateCode", code);

		List<ApiUserCoin> userCoins = mapper.selectByExample(example);
		if (userCoins == null || userCoins.size() == 0) {
			return null;
		}

		return userCoins.get(0);
	}

	public ApiUserCoin createUserCoin(ApiUser user) {

		Example userCoinExample = new Example(ApiUserCoin.class);
		userCoinExample.createCriteria().andEqualTo("userPkid", user.getPkid());
		List<ApiUserCoin> apiUserCoins = mapper.selectByExample(userCoinExample);
		if (apiUserCoins != null && apiUserCoins.size() > 0) {
			return apiUserCoins.get(0);
		}

		ApiUserCoin apiUserCoin = new ApiUserCoin();
		apiUserCoin.setPkid(UuidUtil.get32UUID());
		apiUserCoin.setUserPkid(user.getPkid());
		apiUserCoin.setUserAmount(0L);
		apiUserCoin.setTodayAmount(0L);
		apiUserCoin.setBalance("0");
		apiUserCoin.setInvateCode(RC4Util.encry_RC4_string(CommonUtil.createRandomCharData(2), apiUserCoin.getPkid()).toUpperCase());
		buildInsert(apiUserCoin);
		int count = mapper.insert(apiUserCoin);

		if (count > 0) {

			//新用户送金币

			BaseEvent event = new BaseEvent();
			event.setEvent("NEW_USER");
			event.setPhone(user.getPhone());
			event.setTimestamp(System.currentTimeMillis() + "");
			apiUserCoinHistoryService.saveEvent(event);
		}

		return apiUserCoin;
	}
}
