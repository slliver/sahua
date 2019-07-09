package com.slliver.service;

import com.alipay.api.domain.AlipayFundTransToaccountTransferModel;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import com.github.pagehelper.PageHelper;
import com.slliver.alipay.entites.Result;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.service.BaseService;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.dao.ApiUserCoinHistoryMapper;
import com.slliver.dao.ApiUserCoinMapper;
import com.slliver.dao.ApiUserCoinWithdrawMapper;
import com.slliver.dao.ApiUserMapper;
import com.slliver.entity.ApiUser;
import com.slliver.entity.ApiUserCoin;
import com.slliver.entity.ApiUserCoinHistory;
import com.slliver.entity.ApiUserCoinWithdraw;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service("apiUserCoinWithdrawService")
public class ApiUserCoinWithdrawService extends BaseService<ApiUserCoin> {

	@Autowired
	private ApiUserMapper apiUserMapper;

	@Autowired
	private ApiUserCoinWithdrawMapper mapper;

	@Autowired
	private ApiUserCoinMapper apiUserCoinMapper;

	@Autowired
	private ApiUserCoinHistoryMapper apiUserCoinHistoryMapper;

	@Autowired
	private ApiUserCoinService apiUserCoinService;

	@Autowired
	private AlipayService alipayService;

	public List withdrawHistory(BaseSearchCondition condition) {
		Example example = new Example(ApiUser.class);
		example.createCriteria().andEqualTo("phone", condition.getPhone());
		List<ApiUser> users = apiUserMapper.selectByExample(example);
		if (users == null || users.size() == 0) {
			return null;
		}
		ApiUser user = users.get(0);

		PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
		Example withdrawExample = new Example(ApiUserCoinWithdraw.class);
		withdrawExample.setOrderByClause("make_time desc");
		withdrawExample.createCriteria().andEqualTo("userPkid", user.getPkid());

		return mapper.selectByExample(withdrawExample);
	}

	public int withdrawHistoryCount(BaseSearchCondition condition) {
		Example example = new Example(ApiUser.class);
		example.createCriteria().andEqualTo("phone", condition.getPhone());
		List<ApiUser> users = apiUserMapper.selectByExample(example);
		if (users == null || users.size() == 0) {
			return 0;
		}
		ApiUser user = users.get(0);

		Example withdrawExample = new Example(ApiUserCoinWithdraw.class);
		withdrawExample.createCriteria().andEqualTo("userPkid", user.getPkid());

		return mapper.selectCountByExample(withdrawExample);
	}

	@Transactional
	public ApiRichResult withdraw(String phone, String amount, String account, String realname) {
		ApiRichResult result = new ApiRichResult();
		Example example = new Example(ApiUser.class);
		example.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = apiUserMapper.selectByExample(example);
		if (users == null || users.size() == 0) {
			result.setFailMsg("用户不存在");
			return result;
		}
		ApiUser user = users.get(0);

		Example userCoinExample = new Example(ApiUserCoin.class);
		userCoinExample.createCriteria().andEqualTo("userPkid", user.getPkid());
		ApiUserCoin apiUserCoin = null;
		List<ApiUserCoin> userCoins = apiUserCoinMapper.selectByExample(userCoinExample);
		if (userCoins == null || userCoins.size() == 0) {
			apiUserCoin = apiUserCoinService.createUserCoin(user);
		} else {
			apiUserCoin = userCoins.get(0);
		}

		BigDecimal coin = new BigDecimal(amount).multiply(new BigDecimal(10000));
		if (coin.compareTo(new BigDecimal(apiUserCoin.getUserAmount())) > 0) {
			result.setFailMsg("金币不足");
			return result;
		}

		if (StringUtils.isEmpty(apiUserCoin.getAliAccount())
				|| StringUtils.isEmpty(apiUserCoin.getAliRealname())) {
			if (StringUtils.isEmpty(account)
					|| StringUtils.isEmpty(realname)) {
				result.setFailMsg("支付宝账号不完整");
				return result;
			}
			apiUserCoin.setAliAccount(account);
			apiUserCoin.setAliRealname(realname);

			buildUpdate(apiUserCoin);
			apiUserCoinMapper.updateByExampleSelective(apiUserCoin, userCoinExample);
		}

		ApiUserCoinWithdraw withdraw = new ApiUserCoinWithdraw();
		withdraw.setUserPkid(apiUserCoin.getUserPkid());
		withdraw.setAmount(Long.valueOf(amount));
		withdraw.setStatus("0");
		buildInsert(withdraw);
		mapper.insert(withdraw);

		apiUserCoin.setUserAmount(apiUserCoin.getUserAmount() - coin.longValue());
		apiUserCoinMapper.updateByExampleSelective(apiUserCoin, userCoinExample);

		ApiUserCoin userCoin = apiUserCoin;
		new Thread(new Runnable() {
			@Override
			public void run() {
				doWithdraw(withdraw, userCoin);
			}
		}).start();

		result.setSucceed("提现请求已受理");
		return result;
	}

	public void doWithdraw(ApiUserCoinWithdraw withdraw, ApiUserCoin userCoin) {
		Result result = processWithdraw(withdraw.getPkid(), withdraw.getAmount(), userCoin.getAliAccount(), userCoin.getAliRealname());
		if (result.isSuccess()) {
			Object response = result.getValue();
			if (response != null) {
				AlipayFundTransToaccountTransferResponse toaccountTransferResponse = (AlipayFundTransToaccountTransferResponse)response;
				if (!toaccountTransferResponse.isSuccess()) {
					withdraw.setStatus("2");
					withdraw.setCause(toaccountTransferResponse.getSubMsg());
					withdraw.setPayTime(new Date());

					buildUpdate(withdraw);
					mapper.updateByPrimaryKeySelective(withdraw);

					userCoin.setUserAmount(userCoin.getUserAmount() + withdraw.getAmount() * 10000);
					buildUpdate(userCoin);
					apiUserCoinMapper.updateByPrimaryKeySelective(userCoin);
				} else {
//					updateUserCoin(userCoin, withdraw);
				}
			} else {
//				updateUserCoin(userCoin, withdraw);
			}
		} else {
//			updateUserCoin(userCoin, withdraw);
		}
	}

	private void updateUserCoin(ApiUserCoin userCoin, ApiUserCoinWithdraw withdraw) {
		ApiUserCoinHistory history = new ApiUserCoinHistory();
		history.setUserPkid(userCoin.getUserPkid());
		history.setAmount(0 - withdraw.getAmount() * 10000);
		history.setTimestamp(System.currentTimeMillis() + "");
		history.setAction("withdraw");
		history.setActionName("提现");
		buildInsert(history);
		apiUserCoinHistoryMapper.insert(history);
	}

	private Result processWithdraw(String pkid, Long amount, String account, String realname) {
		AlipayFundTransToaccountTransferModel model = new AlipayFundTransToaccountTransferModel();
		model.setAmount(String.valueOf(amount));
		model.setOutBizNo(pkid);
		model.setPayeeAccount(account);
		model.setPayeeRealName(realname);
		model.setPayeeType("ALIPAY_LOGONID");
		model.setPayerShowName("闪代宝");

		Result result = alipayService.alipayTransfer(model);
		return result;
	}
}
