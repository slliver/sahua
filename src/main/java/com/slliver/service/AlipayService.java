package com.slliver.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.slliver.alipay.config.AlipayConfig;
import com.slliver.alipay.config.DefaultAlipayClientFactory;
import com.slliver.alipay.entites.Result;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
public class AlipayService {

	public Result<AlipayFundTransToaccountTransferResponse> alipayTransfer(AlipayFundTransToaccountTransferModel alipayModel) {
		Result<AlipayFundTransToaccountTransferResponse> result = new Result<AlipayFundTransToaccountTransferResponse>();
		Properties prop = AlipayConfig.getProperties();

		//初始化请求类
		AlipayFundTransToaccountTransferRequest alipayRequest = new AlipayFundTransToaccountTransferRequest();
		//添加demo请求标示，用于标记是demo发出
		alipayRequest.putOtherTextParam(AlipayConfig.ALIPAY_DEMO, AlipayConfig.ALIPAY_DEMO_VERSION);
		//设置业务参数，alipayModel为前端发送的请求信息，开发者需要根据实际情况填充此类
		alipayRequest.setBizModel(alipayModel);
		alipayRequest.setReturnUrl(prop.getProperty("RETURN_URL"));
		alipayRequest.setNotifyUrl(prop.getProperty("NOTIFY_URL"));
		//sdk请求客户端，已将配置信息初始化
		AlipayClient alipayClient = DefaultAlipayClientFactory.getAlipayClient();
		try {
			//因为是接口服务，使用exexcute方法获取到返回值
			AlipayFundTransToaccountTransferResponse alipayResponse = alipayClient.execute(alipayRequest);
			if(alipayResponse.isSuccess()){
				System.out.println("调用成功");
				//TODO 实际业务处理，开发者编写。可以通过alipayResponse.getXXX的形式获取到返回值
			} else {
				System.out.println("调用失败");
			}
			result.setSuccess(true);
			result.setValue(alipayResponse);
			return result;

		} catch (AlipayApiException e) {
			e.printStackTrace();
			result.setSuccess(false);
			if(e.getCause() instanceof java.security.spec.InvalidKeySpecException){
				result.setMessage("商户私钥格式不正确，请确认配置文件Alipay-Config.properties中是否配置正确");
				return result;
			}
		}

		return null;
	}
}
