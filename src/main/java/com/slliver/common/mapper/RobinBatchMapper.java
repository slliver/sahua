package com.slliver.common.mapper;

import com.slliver.common.mapper.provider.RobinSpecialProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RobinBatchMapper<T> {

    @DeleteProvider(type = RobinSpecialProvider.class, method = "dynamicSQL")
    public int deleteBatchLogically(List<T> list);

    @DeleteProvider(type = RobinSpecialProvider.class, method = "dynamicSQL")
    public int deleteBatchLogicallyByIds(@Param("list")List<String> list, @Param("modifyUser")String modifyUser);
}
