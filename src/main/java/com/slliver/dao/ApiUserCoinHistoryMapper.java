package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiUserCoin;
import com.slliver.entity.ApiUserCoinHistory;

import java.util.List;

public interface ApiUserCoinHistoryMapper extends RobinMapper<ApiUserCoinHistory> {

	String obtainTodayAmountViaUser(String pkid);

	List obtainTodaySign(String userPkid);
}
