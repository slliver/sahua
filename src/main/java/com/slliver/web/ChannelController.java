package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.common.Constant;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.paging.PageWapper;
import com.slliver.entity.ApiChannel;
import com.slliver.entity.ApiUser;
import com.slliver.entity.ApiUserChannel;
import com.slliver.service.ApiUserService;
import com.slliver.service.ChannelService;
import com.slliver.service.UserChannelService;
import com.slliver.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * @Description: 渠道管理
 * @author: slliver
 * @date: 2018/11/20 13:36
 * @version: 1.0
 */
@RequestMapping("channel")
@Controller
public class ChannelController extends WebBaseController<ApiChannel> {

    @Autowired
    private ChannelService channelService;

    @Autowired
    private UserChannelService userChannelService;

    @Autowired
    private ApiUserService apiUserService;

    @InitBinder
    public void intDate(WebDataBinder dataBinder){
        dataBinder.addCustomFormatter(new DateFormatter("yyyy-MM-dd HH:mm:ss"));
    }

    @RequestMapping(value = "/list", method = {RequestMethod.POST, RequestMethod.GET})
    public String list(Model model, BaseSearchCondition condition) {
        PageWapper<ApiChannel> pageWapper = this.channelService.selectListByPage(condition);
        model.addAttribute("dataList", pageWapper.getList());
        model.addAttribute("pagnation", pageWapper.getPagingHtml());
        model.addAttribute("condition", condition);
        return getViewPath("list");
    }

    @GetMapping(value = "add")
    public String add(Model model) {
        // 获取nginx服务器的地址
        model.addAttribute("serverPath", Constant.SERVER_FULL_ADDRESS);
        model.addAttribute("serverAppPath", Constant.SERVER_APP_ADDRESS);
        return getViewPath("add");
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRichResult update(ApiChannel channel, HttpServletRequest request) {
        AjaxRichResult result = new AjaxRichResult();
        if (StringUtils.isBlank(channel.getCode())) {
            result.setFailMsg("请输入渠道编码");
            return result;
        }
        boolean bind = this.channelService.flagHaveChannelCode(channel);
        if (bind) {
            result.setFailMsg("当前渠道编码已经存在");
            return result;
        }
        if (StringUtils.isBlank(channel.getName())) {
            result.setFailMsg("请输入渠道名称");
            return result;
        }

        if (StringUtils.isBlank(channel.getChargeUser())) {
            result.setFailMsg("请输入管理员名称");
            return result;
        }

        if (channel.getStartTime() == null || channel.getEndTime() == null){
            result.setFailMsg("扣量时间不能为空");
            return result;
        }

        if (channel.getEndTime().compareTo(channel.getStartTime()) < 0){
            result.setFailMsg("结束时间要大于等于开始时间");
            return result;
        }

        if (StringUtils.isEmpty(channel.getPkid())) {
            ApiUser user = new ApiUser();
            user.setUserName(channel.getCode());
//            user.setPhone(channel.getPhone());
            user.setName(channel.getName() + "管理员");
            user.setChannelNo(channel.getCode());
            user.setRole("ChannelAdmin");
            if (!apiUserService.saveChannelAdmin(user)) {
                result.setFailMsg("渠道编码已经存在");
                return result;
            }
        }

        try {
            int sresult = this.channelService.saveOrUpdate(channel);
            result.setSucceed(sresult > 0 ? "操作成功" : "操作失败");
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result.setFailMsg("操作失败");
        }
        return result;
    }

    @GetMapping(value = "{pkid}/edit")
    public String edit(@PathVariable String pkid, Model model) {
        // 获取nginx服务器的地址
        model.addAttribute("serverPath", Constant.SERVER_IMAGE_ADDRESS);
        model.addAttribute("serverAppPath", Constant.SERVER_APP_ADDRESS);
        model.addAttribute("channel", this.channelService.selectByPkid(pkid));
        return getViewPath("edit");
    }

    @GetMapping(value = "dialog")
    public String dialog(Model model, @RequestParam("userPkid") String userPkid) {
        model.addAttribute("channelList", this.channelService.selectListByUserPkid(userPkid));
        model.addAttribute("userPkid", userPkid);
        return getViewPath("dialog");
    }

    /**
     * 用户授权渠道
     */
    @PostMapping(value = "authorization")
    @ResponseBody
    public AjaxRichResult saveAuthorization(@RequestBody ApiUserChannel[] userChannels) {
        AjaxRichResult result = new AjaxRichResult();
        try {
            int sresult = this.userChannelService.saveAuthorization(userChannels);
            result.setSucceed(sresult > 0 ? "授权成功" : "授权失败");
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result.setFailMsg("授权失败");
        }
        return result;
    }


    @PostMapping(value = "delete")
    @ResponseBody
    public AjaxRichResult delete(@RequestBody ApiChannel[] channels) {
        AjaxRichResult result = new AjaxRichResult();
        try {
            int sresult = this.channelService.delete(channels);
            result.setSucceed(sresult > 0 ? "删除成功" : "删除失败");
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result.setFailMsg("删除失败");
        }
        return result;
    }

    @Override
    protected String getPath() {
        return "/channel";
    }
}
