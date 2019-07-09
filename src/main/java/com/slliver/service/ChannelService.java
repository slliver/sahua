package com.slliver.service;

import com.github.pagehelper.PageHelper;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.service.BaseService;
import com.slliver.common.Constant;
import com.slliver.common.paging.PageWapper;
import com.slliver.dao.ApiChannelMapper;
import com.slliver.entity.ApiChannel;
import com.slliver.entity.ApiUser;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 渠道维护
 * @author: slliver
 * @date: 2018/11/20 13:35
 * @version: 1.0
 */
@Service
public class ChannelService extends BaseService<ApiChannel> {

    @Autowired
    private ApiChannelMapper mapper;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private ApiUserService apiUserService;

    public PageWapper<ApiChannel> selectListByPage(BaseSearchCondition condition) {
        Integer pageNum = 0;
        Integer pageSize = Constant.WEB_PAGE_SIZE;
        if (condition != null) {
            pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
            pageSize = condition.getPageSize() != null ? condition.getPageSize() : Constant.WEB_PAGE_SIZE;
        }

        PageHelper.startPage(pageNum, pageSize);
        return new PageWapper<>(this.mapper.selectListByPage(condition));
    }

    public List<ApiChannel> selectByCode(String code) {
        Example example = new Example(ApiChannel.class);
        example.createCriteria().andEqualTo("flagDelete", 0).andEqualTo("code", code);
        return this.selectByExample(example);
    }

    /**
     * 是否已经存在当前渠道编码
     */
    public boolean flagHaveChannelCode(ApiChannel channel) {
        String code = channel.getCode();
        if (StringUtils.isBlank(code)) {
            return false;
        }

        List<ApiChannel> list = this.selectByCode(code);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        } else {
            String pkid = channel.getPkid();
            if (StringUtils.isNotBlank(pkid)) {
                ApiChannel history = this.selectByPkid(pkid);
                if (!Objects.equals(history.getPkid(), pkid)) {
                    // 存在
                    return true;
                }
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 是否已经存在当前渠道编码
     */
    public boolean flagHaveChargeUser(ApiChannel channel) {
        String code = channel.getChargeUser();
        if (StringUtils.isBlank(code)) {
            return false;
        }

        Example example = new Example(ApiChannel.class);
        example.createCriteria().andEqualTo("chargeUser", channel.getChargeUser());
        List<ApiChannel> list = mapper.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            return false;
        } else {
            return true;
        }
    }

    public Integer saveOrUpdate(ApiChannel channel) {

        String pkid = channel.getPkid();
        if (StringUtils.isNotBlank(pkid)) {
            return this.update(channel);
        } else {
            channel.setLevel((short) 0);
            channel.setParentPkid("00");
            return this.insert(channel);
        }
    }

    /**
     * 选择系统中可用的渠道
     */
    public List<ApiChannel> selectList() {
        Example example = new Example(ApiChannel.class);
        example.createCriteria().andEqualTo("flagDelete", 0).andEqualTo("type", 10);
        return this.selectByExample(example);
    }

    public List<ApiChannel> selectListByUserPkid(String userPkid) {
        return this.mapper.selectListByUserPkid(userPkid);
    }

    public Integer delete(ApiChannel[] items) {
        if (items == null || items.length == 0) {
            return 0;
        }

        for(ApiChannel item : items){
            String channelPkid = item.getPkid();
            // 删除用户关联的渠道
            userChannelService.deleteByChannelPkid(channelPkid);
            // 删除渠道
            this.deleteLogically(channelPkid);
        }

        return 1;
    }
}
