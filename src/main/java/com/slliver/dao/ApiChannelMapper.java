package com.slliver.dao;

import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiChannel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApiChannelMapper extends RobinMapper<ApiChannel> {

    List<ApiChannel> selectListByPage(BaseSearchCondition condition);


    List<ApiChannel> selectListByUserPkid(@Param("userPkid") String userPkid);
}
