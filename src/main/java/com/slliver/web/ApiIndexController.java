package com.slliver.web;

import com.slliver.base.controller.ApiBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiIndexMessage;
import com.slliver.entity.ApiLoanData;
import com.slliver.entity.ApiMessageText;
import com.slliver.service.ApiLoanDataService;
import com.slliver.service.BannerService;
import com.slliver.service.IndexMessageService;
import com.slliver.service.MessageTextService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/8 16:35
 * @version: 1.0
 */
@RestController
@RequestMapping("api/index")
public class ApiIndexController extends ApiBaseController {
    /**
    @Autowired
    private RedisTemplate<String, String> redisTemplate;
    **/
    @Autowired
    private ApiLoanDataService loanDataService;
    @Autowired
    private BannerService bannerService;
    @Autowired
    private IndexMessageService indexMessageService;
    @Autowired
    private MessageTextService messageTextService;

    /**
     * 首页
     */
    @PostMapping(value = "/index")
//    public ApiRichResult index(@RequestHeader("request_token") String token, BaseSearchCondition condition) {
    public ApiRichResult index(BaseSearchCondition condition, HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        // 获取用户信息,从缓存中获取用户信息
//        String userPkid = redisTemplate.opsForValue().get(token);
//        System.out.println("从缓存中获取用户 pkid === >> " + userPkid);
        logger.info("从请求中获取token == >> " + request.getAttribute(Constant.REQUEST_TOKEN));
//        System.out.println("token  === >> " + token);
        PageWapper<ApiLoanData> page = loanDataService.selectListByApi(condition);
        if(page != null){
            List<ApiLoanData> list = page.getList();
            for (ApiLoanData loan : list) {
                if(StringUtils.isNotEmpty(loan.getUrl())){
                    loan.setUrl(StringEscapeUtils.unescapeHtml4(loan.getUrl()));
                }
                loan.setHttpUrl(Constant.SERVER_IMAGE_ADDRESS + "/" + loan.getHttpUrl());
            }
            page.setList(list);
        }
        result.setSucceed(page, "接口调用成功, 当前第" + page.getPageNum() + "页");
        return result;
    }


    /**
     * 获取banner
     * @param bussinessType
     * @return
     */
    @PostMapping(value = "/banner")
//    public ApiRichResult banner(@RequestHeader("request_token") String token, @RequestParam("bussinessType") String bussinessType, HttpServletRequest request) {
    public ApiRichResult banner(@RequestParam("bussinessType") String bussinessType, HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        List<ApiBanner> list = this.bannerService.selectByBussinessTypeApi(bussinessType);
//        String url = "http://" + request.getLocalAddr() + ":" + request.getServerPort();
        for (ApiBanner banner : list) {
//            banner.setHttpUrl(url + "/resource/" + banner.getHttpUrl());
            banner.setHttpUrl(Constant.SERVER_IMAGE_ADDRESS + "/" + banner.getHttpUrl());
            System.out.println("httpUrl == >>> " + banner.getHttpUrl());
        }

        result.setSucceed(list, "获取banner数据成功");
        return result;
    }

    /**
     * 提示消息
     */
    @PostMapping(value = "/message")
//    public ApiRichResult message(@RequestHeader("request_token") String token) {
    public ApiRichResult message(HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        ApiIndexMessage message = indexMessageService.selectIndex();
        result.setSucceed(message, "接口调用成功");
        return result;
    }

    /**
     * 提示消息字符串自定义
     */
    @PostMapping(value = "/message_text")
//    public ApiRichResult message(@RequestHeader("request_token") String token) {
    public ApiRichResult messageText(HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        ApiMessageText message = messageTextService.selectIndex();
        result.setSucceed(message, "接口调用成功");
        return result;
    }
}
