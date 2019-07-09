package com.slliver.dao;

import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiLoanDaquanDetail;
import org.apache.ibatis.annotations.Param;

public interface ApiLoanDaquanDetailMapper extends RobinMapper<ApiLoanDaquanDetail>  {
    void deleteBatch(@Param("loanPkid") String loanPkid);
}
