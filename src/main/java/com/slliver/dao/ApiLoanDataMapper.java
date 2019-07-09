package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.domain.BaseSearchConditionWithoutPagination;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiLoanData;

import java.util.List;

public interface ApiLoanDataMapper extends RobinMapper<ApiLoanData> {

    List<ApiLoanData> selectListByPage(BaseSearchCondition condition);

    /**
     * (旧版本，适合App)极速贷接口
     * @param condition
     * @return
     */
    List<ApiLoanData> selectListByApi(BaseSearchCondition condition);

    /**
     * (新版本，适合H5)极速贷接口
     * @param condition
     * @return
     */
    List<ApiLoanData> selectListByApiNoPagination(BaseSearchConditionWithoutPagination condition);
}
