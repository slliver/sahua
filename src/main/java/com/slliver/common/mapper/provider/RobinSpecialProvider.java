package com.slliver.common.mapper.provider;

import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 15:23
 * @version: 1.0
 */
public class RobinSpecialProvider  extends MapperTemplate {

    public RobinSpecialProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
        super(mapperClass, mapperHelper);
    }

    public String deleteBatchLogically(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName(entityClass)).append(" SET flag_delete = 1 Where pkid in ");
        sql.append("<foreach item=\"o\" collection=\"list\" open=\"(\" separator=\",\" close=\")\" >");
        sql.append("#{o.pkid,jdbcType=VARCHAR}");
        sql.append("</foreach>");
        return sql.toString();
    }
    public String deleteBatchLogicallyByIds(MappedStatement ms) {
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName(entityClass)).append(" SET flag_delete = 1, modify_time = NOW(), modify_user = #{modifyUser} Where pkid in ");
        sql.append("<foreach item=\"o\" collection=\"list\" open=\"(\" separator=\",\" close=\")\" >");
        sql.append("#{o,jdbcType=VARCHAR}");
        sql.append("</foreach>");
        return sql.toString();
    }

    public String deleteLogically(MappedStatement ms){
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName(entityClass)).append(" SET flag_delete = 1, modify_time = NOW(), modify_user = #{modifyUser} Where pkid = #{pkid}");
        return sql.toString();
    }

    public String updateAuditInfo(MappedStatement ms){
        final Class<?> entityClass = getEntityClass(ms);
        //开始拼sql
        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE ").append(tableName(entityClass)).append(" SET flag_audit = #{flagAudit} Where pkid = #{pkid}");
        return sql.toString();
    }
}
