package com.slliver.common.mapper;

import com.slliver.common.mapper.provider.RobinSpecialProvider;
import org.apache.ibatis.annotations.DeleteProvider;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import tk.mybatis.mapper.common.Mapper;

public interface RobinMapper<T> extends Mapper<T>, RobinBatchMapper<T> {

    @DeleteProvider(type = RobinSpecialProvider.class, method = "dynamicSQL")
    public int deleteLogically(@Param("pkid")String pkid, @Param("modifyUser")String modifyUser);

    @UpdateProvider(type = RobinSpecialProvider.class, method = "dynamicSQL")
    public int updateAuditInfo(T t);

}
