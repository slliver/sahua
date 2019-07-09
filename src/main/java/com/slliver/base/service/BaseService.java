package com.slliver.base.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.slliver.base.domain.BaseDomain;
import com.slliver.common.Constant;
import com.slliver.common.exception.RQServiceException;
import com.slliver.common.mapper.RobinMapper;
import com.slliver.common.paging.PageWapper;
import com.slliver.common.utils.ContextUtil;
import com.slliver.common.utils.UuidUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.Date;
import java.util.List;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/9 9:20
 * @version: 1.0
 */
public class BaseService<T extends BaseDomain> {

    protected Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * @Description: 返回泛型的RobinMapper对象
     * @param obj
     * @return robinMapper
     * @throws
     */
    @Autowired
    protected RobinMapper<T> robinMapper;

    public Mapper<T> getRobinMapper() {
        return robinMapper;
    }

    /**
     * 保存一个对象
     *
     * @param obj 对象
     * @return 对象的ID
     */
    public int insert(T obj) {
        if (obj == null) {
            throw new RQServiceException("要插入的数据对象为空");
        }
        if (obj instanceof BaseDomain) {
            if (StringUtil.isEmpty(obj.getPkid())) {
                obj.setPkid(UuidUtil.get32UUID());
            }
            if (null == obj.getFlagDelete()) {
                obj.setFlagDelete(Constant.SYS_COMMON_STATUS.NOT_DELETE);
            }
            Date makeTime = new Date();
            if (null == obj.getMakeTime()) {
                obj.setMakeTime(makeTime);
            }

            if(StringUtils.isBlank(obj.getMakeUser())){
                obj.setMakeUser(ContextUtil.getCurrentUserPkid());
            }

            // add by zhujinhua begin at 2017-1-19 12:10:10  对象保存时，添加默认修改时间及修改人
            if (null == obj.getModifyTime()) {
                obj.setModifyTime(makeTime);
            }
            // add by zhujinhua end at 2017-1-19 12:10:10  对象保存时，添加默认修改时间及修改人

            if (obj.getFlagVersion() == null) {
                obj.setFlagVersion(System.currentTimeMillis());
            }
            if (obj.getFlagSort() == null) {
                obj.setFlagSort(Constant.SHORT_VALUES.VALUE_999);
            }
            //目前数据库设计中不使用默认值。并且考虑开发人员故意赋值为null的字段情况，还是使用insert，而不是insertselective
            return robinMapper.insert(obj);
        }
        return 0;
    }

    public void buildInsert(BaseDomain obj) {
        if (obj == null) {
            throw new RQServiceException("要插入的数据对象为空");
        }
        if (obj instanceof BaseDomain) {
            if (StringUtil.isEmpty(obj.getPkid())) {
                obj.setPkid(UuidUtil.get32UUID());
            }
            if (null == obj.getFlagDelete()) {
                obj.setFlagDelete(Constant.SYS_COMMON_STATUS.NOT_DELETE);
            }
            Date makeTime = new Date();
            if (null == obj.getMakeTime()) {
                obj.setMakeTime(makeTime);
            }

            if(StringUtils.isBlank(obj.getMakeUser())){
                obj.setMakeUser(ContextUtil.getCurrentUserPkid());
            }

            // add by zhujinhua begin at 2017-1-19 12:10:10  对象保存时，添加默认修改时间及修改人
            if (null == obj.getModifyTime()) {
                obj.setModifyTime(makeTime);
            }
            // add by zhujinhua end at 2017-1-19 12:10:10  对象保存时，添加默认修改时间及修改人

            if (obj.getFlagVersion() == null) {
                obj.setFlagVersion(System.currentTimeMillis());
            }
            if (obj.getFlagSort() == null) {
                obj.setFlagSort(Constant.SHORT_VALUES.VALUE_999);
            }
        }
    }

    /**
     * 物理删除一条记录,根据实体属性作为条件进行删除，查询条件使用等号
     */
    public int deleteLogically(T obj) {
        if (obj == null) {
            throw new RQServiceException("要删除的数据对象为空");
        }
        obj.setFlagDelete(Constant.SYS_COMMON_STATUS.DELETE);

        if (null == obj.getModifyTime()) {
            obj.setModifyTime(new Date());
        }
        return robinMapper.updateByPrimaryKeySelective(obj);
    }

    /**
     * 逻辑删除一个对象
     *
     * @param pkid 对象
     */
    public int deleteLogically(String pkid) {
        return robinMapper.deleteLogically(pkid, "");
    }

    public int deletePhysically(T obj) {
        if (obj == null) {
            throw new RQServiceException("要删除的数据对象为空");
        }
        return robinMapper.delete(obj);
    }

    public int deletePhysically(String pkid) {
        return robinMapper.deleteByPrimaryKey(pkid);
    }



    /**
     * 批量逻辑删除多个对象
     *
     * @param objList (主键)数组
     */
    public int deleteBatchLogically(List<T> objList) {
        //批量删除时如果list为空则忽略
        if (objList != null && objList.size() != 0) {
            for (T obj : objList) {
                if (obj != null) {
                    if (null == obj.getModifyTime()) {
                        obj.setModifyTime(new Date());
                    }
                }
            }
            return robinMapper.deleteBatchLogically(objList);
        }
        return 0;
    }

    /**
     * 批量逻辑删除多个对象
     *
     * @param objList (主键)数组
     */
    public int deleteBatchLogicallyByIDs(List<String> objList) {
        //批量删除时如果list为空则忽略
        if (objList != null && objList.size() != 0) {
            return robinMapper.deleteBatchLogicallyByIds(objList, "");
        }
        return 0;
    }


    /**
     * 更新一个对象,实质是调用zWMapper.updateByPrimaryKeySelective
     *
     * @param obj 对象
     */
    public int update(T obj) {
        if (obj == null) {
            throw new RQServiceException("要更新的数据对象为空");
        }
        BaseDomain ent = (BaseDomain) obj;
        if (null == ent.getModifyTime()) {
            ent.setModifyTime(new Date());
        }
        int resCount = robinMapper.updateByPrimaryKeySelective(obj);
        if (resCount > 0) {
            return resCount;
        } else {
            throw new RQServiceException("保存失败，修改前后数据版本不匹配，可能是其他人已经修改了数据");
        }
    }

    public void buildUpdate(BaseDomain obj) {
        if (obj == null) {
            throw new RQServiceException("要更新的数据对象为空");
        }
        BaseDomain ent = (BaseDomain) obj;
        if (null == ent.getModifyTime()) {
            ent.setModifyTime(new Date());
        }
    }

    public int updateAuditInfo(T obj) {
        //反写审核状态这个动作，暂时不更新修改时间和修改人这两个字段。
        if (obj == null) {
            throw new RQServiceException("要更新的数据对象为空");
        }
        return robinMapper.updateAuditInfo(obj);
    }

    /**
     * 获得对象列表
     *
     * @param o 对象
     * @return List
     */
    public List<T> select(T o) {
        return robinMapper.select(o);
    }

    /**
     * 按照主键进行选择
     *
     * @param pkid
     * @return
     */
    public T selectByPkid(String pkid) {
        return robinMapper.selectByPrimaryKey(pkid);
    }

    public int count(T obj) {
        if (obj == null) {
            throw new RQServiceException("用于查询的数据对象参数为空");
        }
        return robinMapper.selectCount(obj);
    }

    /**
     * 获得对象列表，以分页形式返回
     *
     * @param condition 对象
     * @param page      分页对象
     * @return List
     */
    public PageWapper<T> selectByPage(T condition, PageWapper<T> page) {
        if (condition == null || page == null) {
            throw new RQServiceException("用于查询的数据对象参数为空");
        }
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        PageHelper.startPage(pageNum, pageSize, page.getOrderBy());
        List<T> selectedRes = robinMapper.select(condition);
        return toPage(page, selectedRes);
    }

    /**
     * 获得对象列表，以分页形式返回
     *
     * @param example 对象
     * @param page    分页对象
     * @return List
     */
    public PageWapper<T> selectByPage(Example example, PageWapper<T> page) {
        if (example == null || page == null) {
            throw new RQServiceException("用于查询的数据对象参数为空");
        }
        int pageNum = page.getPageNum();
        int pageSize = page.getPageSize();
        PageHelper.startPage(pageNum, pageSize, page.getOrderBy());
        List<T> selectedRes = robinMapper.selectByExample(example);
        page.setList(selectedRes);
        return toPage(page, selectedRes);
    }

    /**
     * 分页数据类型转换
     *
     * @param page
     * @param selectedRes
     * @return
     */
    protected PageWapper<T> toPage(PageWapper<T> page, List<T> selectedRes) {
        page.setList(selectedRes);
        if (selectedRes instanceof Page) {
            Page<T> tkPage = (Page<T>) selectedRes;
            page.setSize((int) tkPage.getTotal());
            page.setTotal(tkPage.getTotal());
            page.setPages(tkPage.getPages());
            page.setStartRow(tkPage.getStartRow());
            page.setEndRow(tkPage.getEndRow());
            page.setOrderBy(tkPage.getOrderBy());
        }
        return page;
    }

    /**
     * 获得对象列表
     *
     * @param example 对象
     * @return List
     */
    public List<T> selectByExample(Example example) {
        if (example == null) {
            throw new RQServiceException("用于查询的数据对象参数为空");
        }
        List<T> selectedRes = robinMapper.selectByExample(example);
        return selectedRes;
    }
}
