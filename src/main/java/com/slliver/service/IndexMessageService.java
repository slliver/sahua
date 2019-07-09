package com.slliver.service;

import com.slliver.base.service.BaseService;
import com.slliver.entity.ApiIndexMessage;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/22 13:41
 * @version: 1.0
 */
@Service("indexMessageService")
public class IndexMessageService extends BaseService<ApiIndexMessage> {

    public ApiIndexMessage selectIndex(){
        Example example = new Example(ApiIndexMessage.class);
        List<ApiIndexMessage> list = this.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }
}
