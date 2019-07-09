package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiCreditCard;
import com.slliver.entity.ApiHelpData;

import java.util.List;

public interface ApiHelpDataMapper extends RobinMapper<ApiHelpData>{
    List<ApiHelpData> selectListByPage(BaseSearchCondition condition);

    List<ApiHelpData> selectListByApi(BaseSearchCondition condition);
}
