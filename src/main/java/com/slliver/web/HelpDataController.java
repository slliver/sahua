package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.constant.DeviceContant;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiCreditCard;
import com.slliver.entity.ApiHelpData;
import com.slliver.service.ApiCreditCardService;
import com.slliver.service.ApiHelpDataService;
import com.slliver.service.BannerService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 13:22
 * @version: 1.0
 */
@Controller
@RequestMapping("helpdata")
public class HelpDataController extends WebBaseController<ApiCreditCard> {

    @Autowired
    private ApiHelpDataService helpDataService;
    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Model model, BaseSearchCondition condition) {
        PageWapper<ApiHelpData> pageWapper = this.helpDataService.selectListByPage(condition);
        model.addAttribute("dataList", pageWapper.getList());
        model.addAttribute("pagnation", pageWapper.getPagingHtml());
        return getViewPath("list");
    }

    @GetMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("bannerList", this.bannerService.selectByBussinessType(Constant.BBANNER_HELP_DATA));
        model.addAttribute("deviceList", DeviceContant.getDeviceMap());
        return getViewPath("add");
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public AjaxRichResult save(Model model, ApiHelpData card) {
        AjaxRichResult result = new AjaxRichResult();

        if (StringUtils.isBlank(card.getLogoPkid())) {
            result.setFailMsg("请上传对应的logo");
            return result;
        }

        String bankName = card.getBankName();
        if (StringUtils.isBlank(bankName)) {
            result.setFailMsg("请输入帮帮名称");
            return result;
        }

        String cardName = card.getCardName();
        if (StringUtils.isBlank(cardName)) {
            result.setFailMsg("请输入帮帮名称");
            return result;
        }

        ApiHelpData record = this.helpDataService.selectByCardName(cardName.trim());
        if (record != null) {
            result.setFailMsg("请输入帮帮名称已存在");
            return result;
        }

        try {
            this.helpDataService.save(card);
        } catch (Exception e) {
            logger.error("保存帮帮记录报错");
        }

        result.setSucceedMsg("保存成功");
        return result;
    }

    @GetMapping(value = "/{pkid}/edit")
    public String edit(Model model, @PathVariable String pkid) {
        ApiHelpData card = this.helpDataService.selectByPkid(pkid);
        String bannerPkid = "";
        if(card != null){
            bannerPkid = card.getBannerPkid();
            if(StringUtils.isNotBlank(bannerPkid)){
                card.setOriginalBannerPkid(bannerPkid);
            }
        }
        model.addAttribute("card", card);
        List<ApiBanner> bannerList = this.bannerService.selectByBussinessType(Constant.BBANNER_HELP_DATA);
        if (CollectionUtils.isNotEmpty(bannerList)) {
            // 如果当前已经绑定banner，加入进去
            if (card != null) {
                if (StringUtils.isNotBlank(bannerPkid)) {
                    ApiBanner banner = bannerService.selectByPkid(bannerPkid);
                    bannerList.add(banner);
                }
            }
        }
        model.addAttribute("bannerList", bannerList);
        model.addAttribute("deviceList", DeviceContant.getDeviceMap());
        return getViewPath("edit");
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxRichResult update(Model model, ApiHelpData card) {
        AjaxRichResult result = new AjaxRichResult();

        if (StringUtils.isBlank(card.getLogoPkid())) {
            result.setFailMsg("请上传银行对应的logo");
            return result;
        }

        String bankName = card.getBankName();
        if (StringUtils.isBlank(bankName)) {
            result.setFailMsg("请输入银行名称");
            return result;
        }

        String cardName = card.getCardName();
        if (StringUtils.isBlank(cardName)) {
            result.setFailMsg("请输入信用卡名称");
            return result;
        }

        ApiHelpData record = this.helpDataService.selectByCardName(cardName.trim());
        if (record != null) {
            if (!Objects.equals(record.getPkid(), card.getPkid())) {
                result.setFailMsg("请输入信用卡名称已存在");
                return result;
            }
        }

        try {
            this.helpDataService.updateCreditCard(card);
            result.setSucceedMsg("修改帮帮记录成功");
        } catch (Exception e) {
            logger.error("更新帮帮记录报错");
        }

        return result;
    }

    @RequestMapping(value = "{pkid}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String pkid){
        this.helpDataService.deleteByPkid(pkid);
        return "redirect:/helpdata/list";
    }


    @Override
    protected String getPath() {
        return "/help_data";
    }
}
