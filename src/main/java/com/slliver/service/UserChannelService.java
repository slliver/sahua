package com.slliver.service;

import com.slliver.base.service.BaseService;
import com.slliver.entity.ApiUserChannel;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: 用户渠道授权
 * @author: slliver
 * @date: 2018/11/21 9:13
 * @version: 1.0
 */
@Service
public class UserChannelService extends BaseService<ApiUserChannel> {

    public List<ApiUserChannel> selectByUserPkid(String userPkid){
        Example example = new Example(ApiUserChannel.class);
        example.createCriteria().andEqualTo("flagDelete", 0).andEqualTo("userPkid", userPkid);
        return this.selectByExample(example);
    }

    public List<ApiUserChannel> selectByChannelPkid(String channelPkid){
        Example example = new Example(ApiUserChannel.class);
        example.createCriteria().andEqualTo("flagDelete", 0).andEqualTo("channelPkid", channelPkid);
        return this.selectByExample(example);
    }

    public Integer saveAuthorization(ApiUserChannel[] items){
        if(items == null || items.length == 0){
            return 0;
        }

        String userPkid = items[0].getUserPkid();
        List<ApiUserChannel> historyList = this.selectByUserPkid(userPkid);
        this.deleteBatchLogically(historyList);

        short sort = 1;
        for(ApiUserChannel item : items){
            item.setFlagSort(sort);
            this.insert(item);
            sort++;
        }

        return 1;
    }

    public void deleteByChannelPkid(String channelPkid){
        List<ApiUserChannel> list = this.selectByChannelPkid(channelPkid);
        this.deleteBatchLogically(list);
    }
}
