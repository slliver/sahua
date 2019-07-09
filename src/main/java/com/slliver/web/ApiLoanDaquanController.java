package com.slliver.web;

import com.slliver.base.controller.ApiBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.ApiLoanDaquanData;
import com.slliver.entity.ApiLoanData;
import com.slliver.service.ApiLoanDaquanDataService;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 贷款大全接口
 * @author: slliver
 * @date: 2019/7/8 9:41
 * @version: 1.0
 */
@RestController
@RequestMapping("api/loan/daquan")
public class ApiLoanDaquanController extends ApiBaseController<ApiLoanDaquanData>  {
    @Autowired
    private ApiLoanDaquanDataService loanDataService;


    /**
     * 列表接口
     */
    @PostMapping(value = "/list")
    public ApiRichResult index(BaseSearchCondition condition, HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        PageWapper<ApiLoanDaquanData> page = loanDataService.selectListByApi(condition);
        if(page != null){
            List<ApiLoanDaquanData> list = page.getList();
            for (ApiLoanDaquanData loan : list) {
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


    @GetMapping(value = "/detail/{loanPkid}")
    public ApiRichResult detail(HttpServletRequest request, @PathVariable String loanPkid) {
        ApiRichResult result = new ApiRichResult();
        ApiLoanDaquanData data = this.loanDataService.selectLoanDetails(loanPkid);
        if(data != null){
            if(StringUtils.isNoneBlank(data.getUrl())){
                data.setUrl(StringEscapeUtils.unescapeHtml4(data.getUrl()));
            }
        }
        result.setSucceed(data, "获取数据成功~");
        return result;
    }
}
