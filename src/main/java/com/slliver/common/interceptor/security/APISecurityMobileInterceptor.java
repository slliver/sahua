package com.slliver.common.interceptor.security;

import com.google.gson.reflect.TypeToken;
import com.slliver.common.Constant;
import com.slliver.common.utils.APISecurityUtil;
import com.slliver.common.utils.JsonUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

public class APISecurityMobileInterceptor extends HandlerInterceptorAdapter {
	
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Map<String, String> rawMap = this.getRawMap(request);
		if(null != rawMap && rawMap.size() > 0) {
			// 1.验证sign是否存在
			String sign = rawMap.get(Constant.SYS_REQUEST_PARAMS.SIGN);
			if(StringUtils.isEmpty(sign)) {
				return false;
			}
			// 2.安全验证
			rawMap.remove(Constant.SYS_REQUEST_PARAMS.SIGN);
			String sign2 = APISecurityUtil.signTopRequest(rawMap, Constant.SIGN_METHOD_MD5, Constant.SYS_CONFIG_KEY.SECURITY_KEY);
			if(sign.equals(sign2)) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
    }
	
	private Map<String, String> getRawMap(HttpServletRequest request) {
		String line = "";
        StringBuilder body = new StringBuilder();
        int counter = 0;
        InputStream stream;
        try {
			stream = request.getInputStream();
			BufferedReader reader = new BufferedReader(new InputStreamReader(stream, Constant.CHARSET_UTF8));
			while ((line = reader.readLine()) != null) {
			    if(counter > 0){
			        body.append("\r\n");
			    }
			    body.append(line);
			    counter++;
			}
			return JsonUtil.fromJson(body.toString(), new TypeToken<Map<String, String>>(){}.getType());
		} catch (IOException e) {
			logger.error(e.getMessage(), e);
		}
        return null;
	}
	
}