/**
 * @Description: 实现API签名校验的Interceptor
 * @author: 马双翼
 * @date: 2016-11-2
 * @version: 1.0
 * @change log:
 */

package com.slliver.common.interceptor.security;

import com.slliver.common.Constant;
import com.slliver.common.utils.APISecurityUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class APISecurityInterceptor extends HandlerInterceptorAdapter {
	
	@Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		// 1.验证sign是否存在
		String sign = request.getParameter(Constant.SYS_REQUEST_PARAMS.SIGN);
		if(StringUtils.isEmpty(sign)) {
			return false;
		}
		// 2.安全验证
		Map<String, String> parameterMap = getRequestMap(request);
		parameterMap.remove(Constant.SYS_REQUEST_PARAMS.SIGN);
		String sign2 = APISecurityUtil.signTopRequest(parameterMap, Constant.SIGN_METHOD_MD5, Constant.SYS_CONFIG_KEY.SECURITY_KEY);
		if(sign.equals(sign2)) {
			return true;
		} else {
			return false;
		}
    }

	private Map<String, String> getRequestMap(HttpServletRequest request){
		Map<String, String[]> properties = request.getParameterMap();
		Map<String, String> returnMap = new HashMap<String, String>(); 
		Iterator<Map.Entry<String,String[]>> entries = properties.entrySet().iterator(); 
		Map.Entry<String, String[]> entry; 
		String name = "";  
		String value = "";  
		while (entries.hasNext()) {
			entry = (Map.Entry<String, String[]>) entries.next(); 
			name = (String) entry.getKey(); 
			Object valueObj = entry.getValue(); 
			if(null == valueObj){ 
				value = ""; 
			}else if(valueObj instanceof String[]){ 
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){ 
					 value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1); 
			}else{
				value = valueObj.toString(); 
			}
			returnMap.put(name, value); 
		}
		return returnMap;
	}
	
}
