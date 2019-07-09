package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.common.utils.BannerUtil;
import com.slliver.entity.ApiBanner;
import com.slliver.service.BannerService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/16 15:55
 * @version: 1.0
 */
@Controller
@RequestMapping("banner")
public class BannerController extends WebBaseController<ApiBanner> {

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Model model, BaseSearchCondition condition) {
        PageWapper<ApiBanner> pageWapper = this.bannerService.selectListByPage(condition);
        model.addAttribute("dataList", pageWapper.getList());
        model.addAttribute("pagnation", pageWapper.getPagingHtml());
        return getViewPath("list");
    }

    @GetMapping(value = "/add")
    public String add(Model model) {
        model.addAttribute("bussinessList", BannerUtil.getBussnessMap());
        model.addAttribute("forwardList", BannerUtil.getForwardMap());
        return getViewPath("add");
    }

    @PostMapping(value = "/save")
    @ResponseBody
    public AjaxRichResult save(ApiBanner banner) {
        AjaxRichResult result = new AjaxRichResult();

        if (StringUtils.isBlank(banner.getLogoPkid())) {
            result.setFailMsg("请上传对应图片");
            return result;
        }

        try {
            this.bannerService.insert(banner);
        } catch (Exception e) {
            logger.error("保存banner记录报错");
            e.printStackTrace();
        }

        result.setSucceedMsg("保存成功");
        return result;
    }

    @GetMapping(value = "/{pkid}/edit")
    public String edit(Model model, @PathVariable String pkid) {
        model.addAttribute("banner", this.bannerService.selectByPkid(pkid));
        model.addAttribute("bussinessList", BannerUtil.getBussnessMap());
        model.addAttribute("forwardList", BannerUtil.getForwardMap());
        return getViewPath("edit");
    }

    @PostMapping(value = "/update")
    @ResponseBody
    public AjaxRichResult update(ApiBanner banner) {
        AjaxRichResult result = new AjaxRichResult();

        if (StringUtils.isBlank(banner.getLogoPkid())) {
            result.setFailMsg("请上传对应图片");
            return result;
        }

        try {
            this.bannerService.update(banner);
            result.setSucceedMsg("修改banner记录成功");
        } catch (Exception e) {
            logger.error("保存banner记录报错");
        }

        return result;
    }

    /**
     * 解除绑定
     */
    @RequestMapping(value = "{pkid}/cancalBind", method = RequestMethod.GET)
    public String cancalBind(@PathVariable String pkid) {
        this.bannerService.updateBind(pkid);
        return "redirect:/banner/list";
    }

    @RequestMapping(value = "{pkid}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable String pkid) {
        this.bannerService.delete(pkid);
        return "redirect:/banner/list";
    }

    @Override
    protected String getPath() {
        return "/banner";
    }
}
