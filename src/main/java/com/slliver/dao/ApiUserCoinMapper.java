package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiUserCoin;

public interface ApiUserCoinMapper extends RobinMapper<ApiUserCoin> {

	ApiUserCoin selectUserCoinByCondition(BaseSearchCondition condition);
}
