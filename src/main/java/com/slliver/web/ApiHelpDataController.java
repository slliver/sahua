package com.slliver.web;

import com.slliver.base.controller.ApiBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.ApiCreditCard;
import com.slliver.entity.ApiHelpData;
import com.slliver.service.ApiCreditCardService;
import com.slliver.service.ApiHelpDataService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 信用卡接口
 * @author: slliver
 * @date: 2018/3/16 13:00
 * @version: 1.0
 */
@RestController
@RequestMapping("api/helpdata")
public class ApiHelpDataController extends ApiBaseController<ApiHelpData> {

    /**
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    **/

    @Autowired
    private ApiHelpDataService helpDataService;

    @PostMapping(value = "/list")
    public ApiRichResult list(HttpServletRequest request, BaseSearchCondition condition) {
        ApiRichResult result = new ApiRichResult();
        // 获取用户信息,从缓存中获取用户信息
        PageWapper<ApiHelpData> page = helpDataService.selectListByApi(condition);
        if (page != null) {
            List<ApiHelpData> list = page.getList();
            for (ApiHelpData card : list) {
                if(StringUtils.isNotEmpty(card.getUrl())){
                    card.setUrl(StringEscapeUtils.unescapeHtml4(card.getUrl()));
                }
                card.setHttpUrl(Constant.SERVER_IMAGE_ADDRESS + "/" + card.getHttpUrl());
            }
            page.setList(list);
        }
        result.setSucceed(page, "获取数据成功, 当前第" + page.getPageNum() + "页");
        return result;
    }
}
