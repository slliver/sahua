package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.domain.BaseSearchConditionWithoutPagination;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiLoanDaquanData;

import java.util.List;

public interface ApiLoanDaquanDataMapper extends RobinMapper<ApiLoanDaquanData> {

    List<ApiLoanDaquanData> selectListByPage(BaseSearchCondition condition);

    /**
     * (旧版本，适合App)极速贷接口
     * @param condition
     * @return
     */
    List<ApiLoanDaquanData> selectListByApi(BaseSearchCondition condition);

    /**
     * (新版本，适合H5)极速贷接口
     * @param condition
     * @return
     */
    List<ApiLoanDaquanData> selectListByApiNoPagination(BaseSearchConditionWithoutPagination condition);
}
