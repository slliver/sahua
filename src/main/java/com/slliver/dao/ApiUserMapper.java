package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiLoanData;
import com.slliver.entity.ApiUser;

import java.util.List;

public interface ApiUserMapper extends RobinMapper<ApiUser> {
    List<ApiUser> selectListByPage(BaseSearchCondition condition);
}
