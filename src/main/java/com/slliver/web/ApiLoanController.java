package com.slliver.web;

import com.slliver.base.controller.ApiBaseController;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.entity.ApiLoanData;
import com.slliver.service.ApiLoanDataService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @Description: 极速贷接口
 * @author: slliver
 * @date: 2018/3/14 10:08
 * @version: 1.0
 */
@RestController
@RequestMapping("api/loan")
public class ApiLoanController extends ApiBaseController<ApiLoanData> {

    @Autowired
    private ApiLoanDataService loanDataService;

    @GetMapping(value = "/detail/{loanPkid}")
//    public ApiRichResult detail(@RequestHeader("request_token") String token, @PathVariable String loanPkid) {
    public ApiRichResult detail(HttpServletRequest request, @PathVariable String loanPkid) {
        ApiRichResult result = new ApiRichResult();
        ApiLoanData data = this.loanDataService.selectLoanDetails(loanPkid);
        if(data != null){
            if(StringUtils.isNoneBlank(data.getUrl())){
                data.setUrl(StringEscapeUtils.unescapeHtml4(data.getUrl()));
            }
        }
        result.setSucceed(data, "获取数据成功~");
        return result;
    }
}
