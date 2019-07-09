package com.slliver.common;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/5 13:37
 * @version: 1.0
 */
public final class Constant {

    public static final String SUCCESS = "success";

    public static class SYS_REQUEST_PARAMS {
        public static final String CURR_MENU = "menu";
        public static final String CURR_PAGE = "currPageId";
        public static final String PKID = "pkid";
        public static final String TIMESTAMP = "timestamp";
        public static final String SIGN = "sign";
    }

    public static class SYS_CONFIG_KEY {
        public static final String SERVERPATH = "serverPath";
        public static final String SECURITY_KEY = "ABCDEF";
    }

    public static class SESSION_KEY {
        /**
         * 登录验证码，目前不需要。需要时候放开即可
         */
        public static final String SECURITY_CODE = "sys_sessionSecCode";
        public static final String MENU_ID = "sys_currMenuId";
        public static final String PAGE_ID = "sys_currPageId";
        public static final String USER = "sys_sessionUser";
        public static final String MENULIST = "sys_menuList";
        public static final String TOKEN_SET = "sys_token_set";
        public static final String TOKEN = "sys_token";
        public static final String GENERATE_TOKEN_HIDDEN = "sys_token_hidden";
        public static final String ERROR_MSG = "errorMsg";
        public static final String COMPANY_PKID = "sys_currentCompanyPkid";
        public static final String COMPANY_NAME = "sys_currentCompanyName";
        public static final String COMPANY_CODE = "sys_currentCompanyCode";
        public static final String LOGIN_KEY = "sys_login_pkid";    // 登陆时放入登陆用户的pkid
    }

    public static class SYS_REQUEST_HEADERS {
        public static final String APPLICATION_JSON = "application/json";
    }

    public static class SYS_COMMON_STATUS {
        public static final Short VALID = 1;
        public static final Short INVALID = 0;
        public static final Short DELETE = 1;
        public static final Short NOT_DELETE = 0;
        public static final String STR_TRUE = "true";
        public static final String STR_FALSE = "true";
    }


    public static class SHORT_VALUES {
        public static final Short VALUE_0 = 0;
        public static final Short VALUE_1 = 1;
        public static final Short VALUE_2 = 2;
        public static final Short VALUE_3 = 3;
        public static final Short VALUE_4 = 4;
        public static final Short VALUE_999 = 999;
    }

    /**
     * 权限验证反馈 请求非法
     */
    public static final String PERMISSION_REFUSE_NO_PERMISSION = "permission.refuse.no_permission";
    public static final String SIGN_METHOD_MD5 = "md5";
    public static final String SIGN_METHOD_HMAC = "hmac";
    public static final String CHARSET_UTF8 = "utf-8";
    public static final String CURRENT_USER_KEY = "user";
    public static final String CURRENT_SHOP_KEY = "shop";

    public static class SYS_LOG {
        public static final String DATABASE_LOG = "database"; // 1: log 存储到DB中

    }

    public static class SYS_MODULES {
        public static final String SYS = "sys";
        public static final String BDM = "bdm";
        public static final String ACT = "act";
        public static final String DEMO = "demo";
    }

    /**
     * 返回值 成功(1)
     */
    public static final int SUCCEED = 1;
    /**
     * 返回值 失败(0)
     */
    public static final int FAIL = 0;
    /**
     * 返回值 check出错(2)
     */
    public static final int CHECKERROR = 2;

    /**
     * token过期时间 默认24小时
     */
//    public static final long TOKEN_EXPIRES_HOUR = 60 * 60 * 24 * 365;
    /**
     * token过期时间
     */
    public static final int TOKEN_EXPIRES_MINUTE = 60;
    public static final int TOKEN_EXPIRES_HOUR = 24;
    public static final int TOKEN_EXPIRES_DAY = 365;
    public static final int TOKEN_EXPIRES_YEAR = 1;

    public static final String REQUEST_TOKEN = "request_token";
    public static final int PAGE_SIZE = 1;
    public static final int WEB_PAGE_SIZE = 10;
    public static final int API_PAGE_SIZE = 10;

    // 默认密码
    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 默认秘钥
     */
    public static final String SERCET_KEY = "robin_api_sercetkey";

    /**
     * 图片服务器地址
     */
//    public static final String SERVER_IMAGE_ADDRESS = "http://twp.ruidunys.com:8089";

    /**
     * 图片服务器地址
     */
    public static final String SERVER_IMAGE_ADDRESS = "http://robin.slliver.com:8089";

    /**
     * H5服务
     */
    public static final String SERVER_APP_ADDRESS = "http://reg.shandaib.com";

    public static final String SERVER_FULL_ADDRESS = "http://twp.shandaib.com";


    /**
     * 系统中资源类型
     */

    public static class RESOURCE {
        // 图片
        public final static int IMAGE = 1;

        // 文档
        public final static int DOCUMENT = 2;

        // 视频
        public final static int VIDEO = 3;

        // 音频
        public final static int AUDIO = 4;
    }

    // banner 极速贷
    public static final String BANNER_LOAN = "1";
    // banner 信用卡
    public static final String BBANNER_CREDIT_CARD = "2";
    // banner 帮帮
    public static final String BBANNER_HELP_DATA = "3";
    // banner 贷款大全
    public static final String BBANNER_DAQUAN_DATA = "4";
}
