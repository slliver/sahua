package com.slliver.dao;

import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiLoanDetail;
import org.apache.ibatis.annotations.Param;

public interface ApiLoanDetailMapper extends RobinMapper<ApiLoanDetail>{


    void deleteBatch(@Param("loanPkid") String loanPkid);
}
