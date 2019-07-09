package com.slliver.web;

import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.slliver.Utils.EncryptUtil;
import com.slliver.Utils.ValidateCodeUtil;
import com.slliver.alipay.config.AlipayConfig;
import com.slliver.alipay.util.StringUtil;
import com.slliver.base.controller.ApiBaseController;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.domain.BaseSearchConditionWithoutPagination;
import com.slliver.common.Constant;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.common.domain.UserValidate;
import com.slliver.common.paging.PageWapper;
import com.slliver.common.utils.CipherUtil;
import com.slliver.common.utils.IpAddressUtil;
import com.slliver.common.utils.TokenUtil;
import com.slliver.entity.*;
import com.slliver.service.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;

/**
 * @Description: 非token方式访问系统功能
 * @author: slliver
 * @date: 2018/3/8 13:42
 * @version: 1.0
 */
@RestController
@RequestMapping("api/test")
public class ApiTestController extends ApiBaseController {

    @Autowired
    private ApiUserService userService;
    @Autowired
    private ApiSmsCodeService smsCodeService;
    @Autowired
    private ApiHelpDataService helpDataService;

    @Autowired
    private ApiUserCoinWithdrawService apiUserCoinWithdrawService;

    @GetMapping(value = "/verificationCode")
    public ApiRichResult getVerificationCode(@RequestParam("phone") String phone, String validcode, HttpSession session) {
        ApiRichResult result = new ApiRichResult();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String ipAddress = IpAddressUtil.getIpAddress(request);

        Object code = session.getAttribute("validate");
        String validCode = null;
        if (code != null) {
            validCode = code.toString();
        }
        if (!StringUtils.equals(validcode, validCode) || validcode == null) {
            result.setFailMsg("图形验证码错误");
        } else {
            UserValidate validate = this.smsCodeService.validateGetCode(phone, ipAddress);
            session.removeAttribute("validate");
            result.setSucceed(validate, "接口调用成功");
        }
        return result;
    }

    /**
     * 用户注册
     * @param phone
     * @param code
     * @return
     */
    /**
     * @RequestMapping(value = "/register", method = {RequestMethod.POST})
     * public ApiRichResult register(@RequestParam("phone") String phone, @RequestParam("code") String code) {
     * ApiRichResult result = new ApiRichResult();
     * UserValidate validate = this.userService.validateRegister(phone, code);
     * result.setSucceed(validate, "接口调用成功");
     * return result;
     * }
     **/

    @RequestMapping("/getValidateCode")
    @ResponseBody
    public ApiRichResult getValidateCode(HttpSession session) {
        ApiRichResult result = new ApiRichResult();
        ValidateCodeUtil.Validate v = ValidateCodeUtil.getRandomCode();     //直接调用静态方法，返回验证码对象
        if(v!=null){
            session.setAttribute("validate", EncryptUtil.toMD5(v.getValue().toLowerCase() + EncryptUtil.PRIVATE_KEY));	//将验证码值保存session
//            session.setAttribute("validate", v.getValue().toLowerCase());	//将验证码值保存session
            ApiValidateCode code = new ApiValidateCode();
            code.setSessionId(session.getId());
            code.setCodeImg(v.getBase64Str());

            result.setSucceed(code, "获取验证码成功");
        } else {
            result.setFailMsg("获取验证码失败");
        }

        return result;
    }

    @PostMapping(value = "/register" )
    public ApiRichResult register(ApiUser apiUser) {
        ApiRichResult result = new ApiRichResult();

        UserValidate validate = this.userService.validateRegister(apiUser);
        result.setSucceed(validate, "接口调用成功");

        return result;
    }

    @RequestMapping(value = "/login")
    public ApiRichResult login(@RequestParam("userName") String name, @RequestParam("password") String password) {
        ApiRichResult result = new ApiRichResult();
        result.setSucceed("1000", "用户名密码登录接口调用成功, 用户名密码正确");
        return result;
    }

    /**
     * 用户手机号码登录
     *
     * @param phone
     * @param code
     * @return
     */
    @PostMapping(value = "/phoneLogin")
    public ApiRichResult phoneLogin(@RequestParam("phone") String phone, @RequestParam("code") String code) {
        ApiRichResult result = new ApiRichResult();
        UserValidate validate = this.userService.validatePhoneLogin(phone, code);
        result.setSucceed(validate, "接口调用成功");
        return result;
    }

    @Autowired
    private ApiLoanDataService loanDataService;

    @GetMapping(value = "/index")
    public ApiRichResult index(@RequestHeader("request_token") String token, BaseSearchCondition condition) {
        ApiRichResult result = new ApiRichResult();
        // 获取用户信息
        String userPkid = TokenUtil.getUserPkid(token);
        PageWapper<ApiLoanData> page = loanDataService.selectListByApi(condition);
        result.setSucceed(page, "接口调用成功, 当前第" + page.getPageNum() + "页");
        return result;
    }


    @PostMapping(value = "/addUser")
    public ApiRichResult addUser(ApiUser user) {
        ApiRichResult result = new ApiRichResult();
        // 第一次加密md5
        String pwrsMD5 = CipherUtil.generatePassword(Constant.DEFAULT_PASSWORD);
        String salt = CipherUtil.createSalt();
        user.setPassword(CipherUtil.createPwdEncrypt(user.getUserName(), pwrsMD5, salt));
        user.setSalt(salt);
        user.setPhone("18040127055");
        this.userService.insert(user);
        result.setSucceed("ok");
        return result;
    }

    /*************************************************************************************************************************************************************************************/
        // 2019-02-14 新增接口
    /*************************************************************************************************************************************************************************************/
    /**
     * 极速贷列表不分页
     */
    @PostMapping(value = "/loanList")
    public ApiRichResult index(BaseSearchConditionWithoutPagination condition, HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        List<ApiLoanData> list = loanDataService.selectListByApiNoPagination(condition);
        int dataCount = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            dataCount = list.size();
            for (ApiLoanData loan : list) {
                if (StringUtils.isNotEmpty(loan.getUrl())) {
                    loan.setUrl(StringEscapeUtils.unescapeHtml4(loan.getUrl()));
                }
                loan.setHttpUrl(Constant.SERVER_IMAGE_ADDRESS + "/" + loan.getHttpUrl());
            }
        }

        // 记录条数
        result.setResCount(dataCount);
        result.setSucceed(list, "接口调用成功");
        return result;
    }

    /**
     * 极速贷详情
     */
    @GetMapping(value = "/detail/{loanPkid}")
    public ApiRichResult detail(@PathVariable String loanPkid) {
        ApiRichResult result = new ApiRichResult();
        ApiLoanData data = this.loanDataService.selectLoanDetails(loanPkid);
        if(data != null){
            if(StringUtils.isNoneBlank(data.getUrl())){
                data.setUrl(StringEscapeUtils.unescapeHtml4(data.getUrl()));
            }
            logger.info("非token方式访问极速贷详情");
        }
        result.setSucceed(data, "获取数据成功~");
        return result;
    }

    @Autowired
    private IndexMessageService indexMessageService;

    @PostMapping(value = "/addIndexMessage")
    public ApiRichResult addIndexMessage(ApiIndexMessage indexMessage) {
        ApiRichResult result = new ApiRichResult();
        indexMessage.setTotalNum(1234);
        indexMessage.setTodayNum(4567);
        indexMessage.setLoanNum(1230);
        indexMessageService.insert(indexMessage);
        result.setSucceed("ok");
        return result;
    }

    @RequestMapping("/update")
    @ResponseBody
    public ApiRichResult update(String platform, String version) {
        ApiRichResult result = new ApiRichResult();

        ApiUpdate update = new ApiUpdate();
        long lastVersion = 100;
        String lastVersionStr = "1.0.0";
        if (StringUtils.equals(platform, "1")) {
            lastVersion = 100;
        } else if (StringUtils.equals(platform, "2")){
            lastVersion = 111;
            lastVersionStr = "1.1.1";
        }
        boolean isUpdate = false;
        if (version != null && version.contains(".")) {
			long versionInt = Long.valueOf(version.replace(".", ""));
            isUpdate = lastVersion > versionInt;
			update.setUpdate(isUpdate);
		} else {
        	update.setUpdate(true);
		}
		if (isUpdate) {
            update.setForce(true);
            update.setVersion(lastVersionStr);
            update.setUrl("http://twp.shandaib.com/app.html");
            String[] desc = {"1. 解决bug", "2. 优化体验"};
            update.setDesc(desc);
            update.setTitle("有新版本~");
        }

        result.setSucceed(update, "获取数据成功");

        return result;
    }

	@RequestMapping("/config")
	@ResponseBody
	public ApiRichResult config(String channel, String platform, String version) {
		ApiRichResult result = new ApiRichResult();
		ApiConfig config = new ApiConfig();
		ArrayList channelInreview = new ArrayList();
		channelInreview.add("yingyongbao");
        channelInreview.add("baidu");
        channelInreview.add("wandoujia");
        channelInreview.add("xiaomi");
        channelInreview.add("vivo");
        channelInreview.add("oppo");
        channelInreview.add("huawei");
        channelInreview.add("samsung");
        channelInreview.add("jinli");
        channelInreview.add("leshi");
        channelInreview.add("meizu");
        channelInreview.add("lenovo");
        channelInreview.add("mumayi");
        channelInreview.add("chuizi");
        channelInreview.add("yingyonghui");
        channelInreview.add("anzhishichang");
        channelInreview.add("sougou");
        channelInreview.add("bingdian");

        ArrayList channelOnline = new ArrayList();
        channelOnline.add("sahua");
//        channelOnline.add("yingyongbao");
        channelInreview.add("360shoujizhushou");
		config.setGameAdChance(90);
		if (StringUtils.equals(platform, "1")) {
		    if (StringUtils.equals(version, "1.0.0")) {
		        config.setOnline(0);
                config.setShowBBX(false);
            }
        } else if (StringUtils.equals(platform, "2")) {
		    if (StringUtils.equals(version, "1.1.1")) {
		        if (channelInreview.contains(channel)) {
                    config.setOnline(0);
                    config.setShowBBX(false);
                } else if (channelOnline.contains(channel)) {
		            config.setOnline(1);
                    config.setShowBBX(true);
                } else {
                    config.setOnline(0);
                    config.setShowBBX(false);
                }
            } else {
                config.setOnline(1);
                config.setShowBBX(true);
            }
        }

		result.setSucceed(config, "获取数据成功");

		return result;
	}

    /**
     * 极速贷列表不分页
     */
    @PostMapping(value = "/coinlist")
    @ResponseBody
    public ApiRichResult coinlist(BaseSearchConditionWithoutPagination condition, HttpServletRequest request) {
        ApiRichResult result = new ApiRichResult();
        List list = new ArrayList();
        //0  大转盘，1. 阅读新闻。2. 看视频。 3.玩游戏。  4. 做任务。5.邀请
//        ApiCoinData lucky = new ApiCoinData("幸运大抽奖", "拼手气赚金币，每天送不停", "立即抽奖", "","0", "gold_001");
//        list.add(lucky);
//        ApiCoinData news = new ApiCoinData("认真阅读文章或视频", "认真阅读，金币奖励不断", "去阅读", "","1", "gold_002");
//        list.add(news);
//        ApiCoinData watch = new ApiCoinData("看视频赚金币", "趣味短视频，送现金奖励", "去看看", "","2", "gold_003");
//        list.add(watch);
//        ApiCoinData game = new ApiCoinData("玩游戏，领金币", "游戏多多，金币多多", "玩一下", "","3", "gold_004");
//        list.add(game);
//        ApiCoinData task = new ApiCoinData("做任务送金币", "任务多多，金币多多", "做任务", "","4", "gold_005");
//        list.add(task);
//        ApiCoinData invate = new ApiCoinData("邀请好友送金币", "邀请好友，与好友一起嗨", "马上邀请", "","5", "gold_006");
//        list.add(invate);
        // 记录条数
        result.setResCount(list.size());
        result.setSucceed(list, "接口调用成功");
        return result;
    }

    @PostMapping(value = "/helplist")
    public ApiRichResult helplist(HttpServletRequest request, BaseSearchCondition condition) {
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

    @RequestMapping(value="/notifyUrl.json")
    @ResponseBody
    public String aliNotify(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) throws IOException {

        Properties prop = AlipayConfig.getProperties();
        // 编码
        String charset = prop.getProperty("CHARSET");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean validation = false;
        String result = "false";
        try {
            validation = AlipaySignature.rsaCheckV1(params, prop.getProperty("ALIPAY_RSA2_PUBLIC_KEY"), charset,
                    prop.getProperty("SIGN_TYPE"));

            if (validation) {
                //TODO 根据业务需要进行处理
                result = "success";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return result;

    }

    @RequestMapping(value="/returnUrl.json")
    @ResponseBody
    public String alipayReturn(HttpServletRequest request,HttpServletResponse response,ModelMap modelMap) throws IOException {

        Properties prop = AlipayConfig.getProperties();
        // 编码
        String charset = prop.getProperty("CHARSET");
        Map<String, String> params = new HashMap<String, String>();
        Map<String, String[]> requestParams = request.getParameterMap();
        for (Iterator<String> iter = requestParams.keySet().iterator(); iter.hasNext();) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        boolean validation = false;
        String result = null;
        try {
            validation = AlipaySignature.rsaCheckV1(params, prop.getProperty("ALIPAY_RSA2_PUBLIC_KEY"), charset,
                    prop.getProperty("SIGN_TYPE"));
            if (validation) {
                result = "验证通过";
            }else{
                result = "验证失败，请确认“支付宝公钥”是否匹配，注意非商户的公钥是支付宝公钥";
            }
        } catch (AlipayApiException e) {
            e.printStackTrace();
            result = "处理出错，请查看代码进行排查";
        }

        //TODO 根据业务需要进行处理

//        response.setContentType("text/html;charset=" + charset);
//        response.setCharacterEncoding(charset);
//        response.getWriter().write(result);// 直接将完整的表单html输出到页面
//        response.getWriter().flush();
//        response.getWriter().close();
        return result;

    }
}
