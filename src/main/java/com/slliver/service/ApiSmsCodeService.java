package com.slliver.service;

import com.slliver.base.service.BaseService;
import com.slliver.common.Constant;
import com.slliver.common.DateConstant;
import com.slliver.common.ValidationConstant;
import com.slliver.common.constant.SmsContant;
import com.slliver.common.domain.UserToken;
import com.slliver.common.domain.UserValidate;
import com.slliver.common.utils.DateUtil;
import com.slliver.common.utils.SmsUtil;
import com.slliver.dao.ApiSmsCodeMapper;
import com.slliver.entity.ApiSmsCode;
import com.sun.org.apache.regexp.internal.RE;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/9 9:20
 * @version: 1.0
 */
@Service("apiSmsCodeService")
public class ApiSmsCodeService extends BaseService<ApiSmsCode> {

    @Autowired
    private ApiSmsCodeMapper mapper;

    /**
     * 验证用户通过手机获取验证码
     */
    public UserValidate validateGetCode(String phone, String ipAddress) {
        UserValidate result = new UserValidate();

        // 验证手机号是否有效
        String phoneStatusCode = validateNotNull(phone);
        if (!Objects.equals(ValidationConstant.SUCCESS, phoneStatusCode)) {
            result.setStatusCode(phoneStatusCode);
            result.setMessage(ValidationConstant.getStatusCodeMessage(phoneStatusCode));
            return result;
        }

        // 获取当前手机号码当前日期获取验证码的次数
        List<ApiSmsCode> list = this.selectListByPhone(phone, getCurrentDateString(DateConstant.YYYY_MM_DD));

        if (CollectionUtils.isEmpty(list)) {
            // 当日没有获取过
            String code = SmsUtil.generateRandomCode();
            save(phone, code, ipAddress);
            // 发送验证码
            SmsUtil.send(phone, code);

            result.setCode(code);
            result.setStatusCode(ValidationConstant.SUCCESS);
            result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.SUCCESS));
            return result;
        } else {
            // 已经发送过，需要进行校验当前日期已经获取过验证码
            ApiSmsCode smsCode = list.get(0);
            int count = smsCode.getCount();
            if (count > (SmsContant.CODE_MAX_RECEIVE_COUNT - 1)) {
                // 不能接收，给予提示
                result.setCode(smsCode.getCode());
                result.setStatusCode(ValidationConstant.CODE_MAX_COUNT);
                result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.CODE_MAX_COUNT));
                return result;
            }

            // 只要次数没超过，就继续发送验证码
            String code = SmsUtil.generateRandomCode();
            AtomicInteger integer = new AtomicInteger(count);
            Date expireDate = DateUtils.addMinutes(DateUtil.getCurrentDate(), SmsUtil.CODE_EXPIRE_MINUTE);
            smsCode.setExpireDate(expireDate);
            smsCode.setExpireTime(expireDate.getTime());
            smsCode.setCount(integer.addAndGet(1));
            smsCode.setCode(code);
            this.update(smsCode);

            // 和短信平台进行对接，发送验证码给客户 phone + code
            SmsUtil.send(phone, code);

            result.setCode(code);
            result.setStatusCode(ValidationConstant.SUCCESS);
            result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.SUCCESS));
            return result;
        }

        /** 注销掉原来逻辑 slliver 2019-07-03 10:19
        if (CollectionUtils.isEmpty(list)) {
            // 当日没有获取过
            String code = SmsUtil.generateRandomCode();
            if (StringUtils.equals(phone, "13800138000")) {
                code = "111111";
            } else {
                save(phone, code, ipAddress);

                // 发送验证码
                SmsUtil.send(phone, code);
            }

            result.setCode(code);
            result.setStatusCode(ValidationConstant.SUCCESS);
            result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.SUCCESS));
            return result;
        } else {
            // 已经发送过，需要进行校验当前日期已经获取过验证码
            ApiSmsCode smsCode = list.get(0);
            int count = smsCode.getCount();
            if (count > (SmsContant.CODE_MAX_RECEIVE_COUNT - 1)) {
                // 不能接收，给予提示
                result.setCode(smsCode.getCode());
                result.setStatusCode(ValidationConstant.CODE_MAX_COUNT);
                result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.CODE_MAX_COUNT));
                return result;
            }

            // 验证码是不是在有效期
            String code = "";
            Long nowTime = System.currentTimeMillis();
            Long expireTime = smsCode.getExpireTime();
            if (nowTime > expireTime) {
                // 验证码过期了，重新获取验证码
                code = SmsUtil.generateRandomCode();
                AtomicInteger integer = new AtomicInteger(count);
                Date expireDate = DateUtils.addMinutes(DateUtil.getCurrentDate(), SmsUtil.CODE_EXPIRE_MINUTE);
                smsCode.setExpireDate(expireDate);
                smsCode.setExpireTime(expireDate.getTime());
                smsCode.setCount(integer.addAndGet(1));
                smsCode.setCode(code);
                this.update(smsCode);

                // 和短信平台进行对接，发送验证码给客户 phone + code
                SmsUtil.send(phone, code);

                result.setCode(code);
                result.setStatusCode(ValidationConstant.SUCCESS);
                result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.SUCCESS));
                return result;
            } else {
                code = smsCode.getCode();
                // 验证码可用，不用发送验证码，直接告诉验证码可用
                result.setCode(code);

                result.setStatusCode(ValidationConstant.CODE_NOT_EXPIRE);
                result.setMessage(ValidationConstant.getStatusCodeMessage(ValidationConstant.CODE_NOT_EXPIRE));
                return result;
            }
        }
        **/
    }

    public String validateNotNull(final String phone) {
        if (StringUtils.isBlank(phone)) {
            return ValidationConstant.PHONE_NULL;
        }

        return ValidationConstant.SUCCESS;
    }

    /**
     * 用户获取验证码
     */
    @Deprecated
    public UserToken updateSendCode(String phone, String ipAddress) {
        UserToken result = new UserToken();
        String sendResult = "success";
        List<ApiSmsCode> list = this.selectListByPhone(phone, getCurrentDateString(DateConstant.YYYY_MM_DD));
        if (CollectionUtils.isEmpty(list)) {
            // 当前日期没有获取过，直接保存
            String code = SmsUtil.generateRandomCode();
            save(phone, code, ipAddress);
//            return SmsUtil.send(phone, code);

            result.setCode(code);
            result.setMessage(Constant.SUCCESS);
            return result;
        } else {
            String code = "";
            // 当前日期已经获取过验证码
            ApiSmsCode smsCode = list.get(0);
            int count = smsCode.getCount();
            if (count > (SmsContant.CODE_MAX_RECEIVE_COUNT - 1)) {
                // 不能接收，给予提示
                result.setMessage("max_error, 您今日获取的验证码次数已经超过最大次数，" + SmsContant.CODE_MAX_RECEIVE_COUNT + "次数");
                return result;
//                return "max_error, 您今日获取的验证码次数已经超过最大次数，" + SmsContant.CODE_MAX_RECEIVE_COUNT + "次数";
            }

            // 验证码是不是在有效期
            Long nowTime = System.currentTimeMillis();
            Long expireTime = smsCode.getExpireTime();
            if (nowTime > expireTime) {
                // 验证码过期了，重新获取验证码
                code = SmsUtil.generateRandomCode();
                AtomicInteger integer = new AtomicInteger(count);
                Date expireDate = DateUtils.addMinutes(DateUtil.getCurrentDate(), SmsUtil.CODE_EXPIRE_MINUTE);
                smsCode.setExpireDate(expireDate);
                smsCode.setExpireTime(expireDate.getTime());
                smsCode.setCount(integer.addAndGet(1));
                smsCode.setCode(code);
                this.update(smsCode);

                // 和短信平台进行对接，发送验证码给客户 phone + code
//                return SmsUtil.send(phone, code);
                result.setCode(code);
                result.setMessage(Constant.SUCCESS);
                return result;
            } else {
                code = smsCode.getCode();
                // 验证码可用，不用发送验证码，直接告诉验证码可用
//                return "code_valid, 验证码已经发送，请使用发送的验证码["+code+"]~";
                result.setCode(code);
                result.setMessage("code_valid, 验证码已经发送，请使用发送的验证码");
                return result;
            }
        }
    }

    /**
     * 用户获取验证码后保存验证码
     *
     * @param phone
     * @param code
     * @return
     */
    public int save(String phone, String code, String ipAddress) {
        ApiSmsCode sms = new ApiSmsCode();
        sms.setPhone(phone);
        sms.setCode(code);
        sms.setIpAddress(ipAddress);
        sms.setCount(1);
        // 验证码过期时间
        Date currentDate = DateUtil.getCurrentDate();
        Date expireDate = DateUtils.addMinutes(currentDate, SmsUtil.CODE_EXPIRE_MINUTE);
        sms.setExpireDate(expireDate);
        sms.setExpireTime(expireDate.getTime());
        sms.setFlagValid(Boolean.TRUE);
        sms.setMakeDate(DateFormatUtils.format(currentDate, DateConstant.YYYY_MM_DD));
        return insert(sms);
    }

    /**
     * 某个手机号某一天接收验证码的信息
     */
    public List<ApiSmsCode> selectListByPhone(String phone, String currentDate) {
        Example example = new Example(ApiSmsCode.class);
        example.createCriteria().andEqualTo("phone", phone).andEqualTo("makeDate", currentDate);
        List<ApiSmsCode> list = this.selectByExample(example);
        return list;
    }

    /**
     * 获取当前日期字符串格式
     *
     * @return
     */
    private String getCurrentDateString(String format) {
        return DateFormatUtils.format(DateUtil.getCurrentDate(), format);
    }

    /**
     * 根据手机号码和验证码判断验证码是否有效
     *
     * @param phone
     * @param code
     * @return
     */
    public String selectByPhoneAndCode(String phone, String code) {
        Example example = new Example(ApiSmsCode.class);
        example.createCriteria()
                .andEqualTo("phone", phone)
                .andEqualTo("code", code)
                .andEqualTo("makeDate", getCurrentDateString(DateConstant.YYYY_MM_DD));
        List<ApiSmsCode> list = this.selectByExample(example);
        if (CollectionUtils.isEmpty(list)) {
            // 输入的验证码错误
            return ValidationConstant.CODE_ERROR;
        }

        ApiSmsCode smsCode = list.get(0);
        Long nowTime = System.currentTimeMillis();
        Long expireTime = smsCode.getExpireTime();
        if (nowTime > expireTime) {
            // 输入的验证码已过期，请重新获取验证码
            return ValidationConstant.CODE_EXPIRE;
        }

        return ValidationConstant.SUCCESS;
    }

    public static void main(String[] args) {
        // 10分钟后的时间
        Date now = new Date();
        Date after = DateUtils.addMinutes(DateUtil.getCurrentDate(), SmsUtil.CODE_EXPIRE_MINUTE);
        System.out.println("当前日期 == >>" + now.getTime() + ",5分钟后" + after.getTime());
        System.out.println(now.getTime() - after.getTime());
        System.out.println("当前日期 == >> " + DateFormatUtils.format(now, "yyyy-MM-dd HH:mm:ss") + "，10分钟后" + DateFormatUtils.format(after, "yyyy-MM-dd HH:mm:ss"));
    }
}
