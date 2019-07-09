package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiBanner;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiBannerMapper extends RobinMapper<ApiBanner> {

    List<ApiBanner> selectListByPage(BaseSearchCondition condition);

    List<ApiBanner> selectByBussinessTypeApi(@Param("bussinessType") String bussinessType);
    List<ApiBanner> selectByBussinessType(@Param("bussinessType")String bussinessType);
}
