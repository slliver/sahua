package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.constant.DeviceContant;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiLoanData;
import com.slliver.entity.ApiLoanDetail;
import com.slliver.service.ApiLoanDataService;
import com.slliver.service.BannerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Description: 极速贷
 * @author: slliver
 * @date: 2018/3/14 10:08
 * @version: 1.0
 */
@Controller
@RequestMapping("loan")
public class LoanController extends WebBaseController<ApiLoanData> {

    @Autowired
    private ApiLoanDataService loanDataService;
    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Model model, BaseSearchCondition condition) {
        PageWapper<ApiLoanData> pageWapper = this.loanDataService.selectListByPage(condition);
        model.addAttribute("dataList", pageWapper.getList());
        model.addAttribute("pagnation", pageWapper.getPagingHtml());
        return getViewPath("list");
    }

    /**
    @Autowired
    private MessageSender messageSender;
    @RequestMapping(value = "/test", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Model model, BaseSearchCondition condition) {
        // 设置RoutingKey，匹配message.*即可
        messageSender.setRoutingKey("message.test");
        // 发送消息
        messageSender.sendDataToQueue("insert Queue");
        return getViewPath("test");
    }
    **/

    @GetMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("bannerList", this.bannerService.selectByBussinessType(Constant.BANNER_LOAN));
        model.addAttribute("deviceList", DeviceContant.getDeviceMap());
        return getViewPath("add");
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public AjaxRichResult save(ApiLoanData loan) {
        AjaxRichResult result = new AjaxRichResult();
        String orgName = loan.getOrgName();
        if (StringUtils.isBlank(loan.getLogoPkid())) {
            result.setFailMsg("请上传机构logo");
            return result;
        }

        if (StringUtils.isBlank(orgName)) {
            result.setFailMsg("请输入机构名称");
            return result;
        }

        ApiLoanData record = this.loanDataService.selectByOrgName(orgName);
        if (record != null) {
            result.setFailMsg("请输入机构名称已存在");
            return result;
        }

        if(loan.getTotalApply() != null){
            if (!this.isInteger(loan.getTotalApply().toString())) {
                result.setFailMsg("申请人数只能是数字");
                return result;
            }
        }

        try {
            this.loanDataService.save(loan);
        } catch (Exception e) {
            logger.error("保存极速贷记录报错");
        }

        result.setSucceedMsg("保存成功");
        return result;
    }

    @GetMapping(value = "/{pkid}/edit")
    public String edit(Model model, @PathVariable String pkid) {
        ApiLoanData loan = this.loanDataService.selectByPkid(pkid);
        if (loan != null) {
            if (StringUtils.isNotBlank(loan.getBannerPkid())) {
                loan.setOriginalBannerPkid(loan.getBannerPkid());
            }
        }
        model.addAttribute("loan", loan);
        List<ApiBanner> bannerList = this.bannerService.selectByBussinessType(Constant.BANNER_LOAN);
        /**
        if (CollectionUtils.isNotEmpty(bannerList)) {
            if (loan != null) {
                String bannerPkid = loan.getBannerPkid();
                if (StringUtils.isNotBlank(bannerPkid)) {
                    ApiBanner banner = bannerService.selectByPkid(bannerPkid);
                    bannerList.add(banner);
                }
            }
        }
        **/

        // 修改 2019-07-03 10:15
        if (CollectionUtils.isNotEmpty(bannerList)) {
            // 如果当前已经绑定banner，加入进去
            if (loan != null) {
                String bannerPkid = loan.getBannerPkid();
                if (StringUtils.isNotBlank(bannerPkid)) {
                    ApiBanner banner = bannerService.selectByPkid(bannerPkid);
                    bannerList.add(banner);
                }
            }
        }else{
            // 没有可以选择的，要看当前是否已经绑定banner
            if (loan != null) {
                String bannerPkid = loan.getBannerPkid();
                if (StringUtils.isNotBlank(bannerPkid)) {
                    ApiBanner banner = bannerService.selectByPkid(bannerPkid);
                    bannerList.add(banner);
                }
            }
        }

        Map<String, List<ApiLoanDetail>> detailMap = this.loanDataService.selectDetails(pkid);

        model.addAttribute("bannerList", bannerList);
        model.addAttribute("applyConditions", detailMap.get("applyConditions"));
        model.addAttribute("reqMaterials", detailMap.get("reqMaterials"));
        model.addAttribute("earlyRepayments", detailMap.get("earlyRepayments"));
        model.addAttribute("deviceList", DeviceContant.getDeviceMap());
        return getViewPath("edit");
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxRichResult update(Model model, ApiLoanData loan) {
        AjaxRichResult result = new AjaxRichResult();
        String orgName = loan.getOrgName();
        if (StringUtils.isBlank(orgName)) {
            result.setFailMsg("请输入机构名称");
            return result;
        }

        ApiLoanData record = this.loanDataService.selectByOrgName(orgName);
        if (record != null) {
            if (!Objects.equals(record.getPkid(), loan.getPkid())) {
                result.setFailMsg("请输入机构名称已存在");
                return result;
            }
        }

        if(loan.getTotalApply() != null){
            if (!this.isInteger(loan.getTotalApply().toString())) {
                result.setFailMsg("申请人数只能是数字");
                return result;
            }
        }

        try {
            this.loanDataService.updateLoan(loan);
        } catch (Exception e) {
            logger.error("保存极速贷记录报错");
        }

        result.setSucceedMsg("保存成功");
        return result;
    }

    @RequestMapping(value = "{pkid}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String pkid) {
        this.loanDataService.deleteByPkid(pkid);
        return "redirect:/loan/list";
    }

    @RequestMapping(value = "deletes", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRichResult deletes(@RequestBody BaseSearchCondition[] params) {
        AjaxRichResult result = new AjaxRichResult();
        try {
            this.loanDataService.delete(params);
            result.setSucceedMsg("删除成功");
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result.setFailMsg("删除失败");
        }

        return result;
    }

    @Override
    protected String getPath() {
        return "/loan";
    }

    public boolean isInteger(String input) {
        return input.matches("[1-9][0-9]*");
    }

}
