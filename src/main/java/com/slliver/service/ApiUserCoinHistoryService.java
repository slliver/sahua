package com.slliver.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.slliver.Utils.CommonUtil;
import com.slliver.base.dao.UserCoinActionMapper;
import com.slliver.base.dao.UserCoinMapper;
import com.slliver.base.dao.UserMapper;
import com.slliver.base.domain.BaseEvent;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.entity.User;
import com.slliver.base.entity.UserCoin;
import com.slliver.base.entity.UserCoinAction;
import com.slliver.base.service.BaseService;
import com.slliver.common.domain.ApiRichResult;
import com.slliver.common.utils.UuidUtil;
import com.slliver.dao.ApiUserCoinHistoryMapper;
import com.slliver.dao.ApiUserCoinMapper;
import com.slliver.dao.ApiUserMapper;
import com.slliver.entity.*;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("apiUserCoinHistoryService")
public class ApiUserCoinHistoryService extends BaseService<ApiUserCoinHistory> {

	@Autowired
	ApiUserMapper userMapper;

	@Autowired
	ApiUserCoinMapper apiUserCoinMapper;

	@Autowired
	private ApiUserCoinHistoryMapper mapper;

	@Autowired
	private UserCoinActionMapper userCoinActionMapper;

	@Autowired
	private ApiUserCoinService apiUserCoinService;

	public List<ApiUserCoinHistory> obtainCoinHistory(BaseSearchCondition condition) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", condition.getPhone());
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		PageHelper.startPage(condition.getPageNum(), condition.getPageSize());
		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		example.createCriteria().andEqualTo("userPkid", user.getPkid()).andNotEqualTo("amount", "0");
		List<ApiUserCoinHistory> list = mapper.selectByExample(example);

		return list;
	}

	public int obtainCoinHistoryCount(BaseSearchCondition condition) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", condition.getPhone());
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return 0;
		}

		ApiUser user = users.get(0);

		Example example = new Example(ApiUserCoinHistory.class);
		example.createCriteria().andEqualTo("userPkid", user.getPkid()).andNotEqualTo("amount", "0");

		return mapper.selectCountByExample(example);
	}

	public synchronized List<ApiUserCoinHistory> obtainCoinHistoryViaTimestamp(String phone, String timestamp) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		example.createCriteria().andEqualTo("userPkid", user.getPkid()).andEqualTo("timestamp", timestamp);
		List<ApiUserCoinHistory> list = mapper.selectByExample(example);

		return list;
	}

	public String obtainVideoLeftTime(String phone) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		example.createCriteria().andEqualTo("userPkid", user.getPkid()).andEqualTo("action", "WVD");

		List<ApiUserCoinHistory> list = mapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return "0";
		} else {
			ApiUserCoinHistory history = list.get(0);
			Date last = history.getMakeTime();
			Date now = new Date();
			long left = (now.getTime() - last.getTime()) / 1000;
			if (left > 5 * 60) {
				return "0";
			} else {
				return (5 * 60 - left) + "";
			}
		}
	}

	public ApiLuckyCount obtainLuckycount(String phone) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		long zero = CommonUtil.getStartTime();
		long twelve = CommonUtil.getEndTime();
		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		example.createCriteria().andEqualTo("userPkid", user.getPkid())
				.andEqualTo("action", "LOT")
				.andGreaterThanOrEqualTo("makeTime", new Timestamp(zero))
				.andLessThanOrEqualTo("makeTime", new Timestamp(twelve));

		List<ApiUserCoinHistory> list = mapper.selectByExample(example);

		ApiLuckyCount count = new ApiLuckyCount();
		if (list == null || list.size() == 0) {
			count.setCount(20);
		} else {
			count.setCount(20 - list.size());
		}

		count.setChance(30);

		return count;
	}

	public int[] obtainBox(String phone) {
		int[] box = {0, 0, 0, 0};
		//0 不能开启，1可以开启，2已开启
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		long zero = CommonUtil.getStartTime();
		long twelve = CommonUtil.getEndTime();
		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		example.createCriteria().andEqualTo("userPkid", user.getPkid())
				.andLike("action", "BOX%")
				.andGreaterThanOrEqualTo("makeTime", new Timestamp(zero))
				.andLessThanOrEqualTo("makeTime", new Timestamp(twelve));

		List<ApiUserCoinHistory> list = mapper.selectByExample(example);
		if (list != null) {
			for ( int i = 0; i < list.size(); i++) {
				ApiUserCoinHistory history = list.get(i);
				if (history.getAction().startsWith("BOX")) {
					int index = Integer.valueOf(history.getAction().substring(4, 5));
					box[index] = 2;
				}
			}
		}

		Example example1 = new Example(ApiUserCoinHistory.class);
		example1.setOrderByClause("make_time desc");
		example1.createCriteria().andEqualTo("userPkid", user.getPkid())
				.andEqualTo("action", "LOT")
				.andGreaterThanOrEqualTo("makeTime", new Timestamp(zero))
				.andLessThanOrEqualTo("makeTime", new Timestamp(twelve));

		List<ApiUserCoinHistory> list1 = mapper.selectByExample(example1);
		if (list1 != null) {
			for (int i = 0; i < 4; i++) {
				if (list1.size() >= (i + 1) * 5) {
					if (box[i] == 0) {
						box[i] = 1;
					}
				}
			}
		}
		return box;
	}

	@Transactional
	public ApiRichResult saveEvent(BaseEvent event) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", event.getPhone());
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		Example example = new Example(UserCoinAction.class);
		example.createCriteria().andEqualTo("action", event.getEvent());
		List<UserCoinAction> list = userCoinActionMapper.selectByExample(example);
		if (list == null || list.size() == 0) {
			return null;
		}

		UserCoinAction action = list.get(0);

		ApiRichResult result = new ApiRichResult();

		ApiUserEventCoin eventCoin;

		if (StringUtils.equals(event.getEvent(), "CDB")) { //金币翻倍
			eventCoin = dealDoubleCoin(user, action, event);
		} else if (StringUtils.equals(event.getEvent(), "OFFLINE")) {    //离线金币
			eventCoin = dealOffline(user, action, event);
		} else if (StringUtils.equals(event.getEvent(), "SIGN")) {        //签到送金币
			eventCoin = dealSign(user, action, event);
		} else if (StringUtils.equals(event.getEvent(), "LOT")) {	//大转盘
			eventCoin = dealLucky(user, action, event);
	    } else if (StringUtils.equals(event.getEvent(), "CONVERT")) {
			return dealConvert(user, action, event);
		} else {
			eventCoin = dealOther(user, action, event);
		}

		eventCoin.setEvent(event.getEvent());
		if (eventCoin == null) {
			result.setFailMsg("上报事件失败");
		} else {
			result.setSucceed(eventCoin, "获取数据成功~");
		}

		return result;
	}

	private ApiUserEventCoin dealAfter(ApiUser user, UserCoinAction action, BaseEvent event, long amount) {
		ApiUserEventCoin eventCoin = new ApiUserEventCoin();

		Example userCoinExample = new Example(ApiUserCoin.class);
		userCoinExample.createCriteria().andEqualTo("userPkid", user.getPkid());
		List<ApiUserCoin> apiUserCoins = apiUserCoinMapper.selectByExample(userCoinExample);
		ApiUserCoin apiUserCoin = null;
		if (apiUserCoins == null || apiUserCoins.size() == 0) {
			apiUserCoin = apiUserCoinService.createUserCoin(user);
		} else {
			apiUserCoin = apiUserCoins.get(0);
		}

		apiUserCoin.setUserAmount(apiUserCoin.getUserAmount() + amount);
		buildUpdate(apiUserCoin);
		apiUserCoinMapper.updateByExampleSelective(apiUserCoin, userCoinExample);

		eventCoin.setUserAmount(apiUserCoin.getUserAmount());

		BigDecimal bg = new BigDecimal(apiUserCoin.getUserAmount() / 10000f);
		String banlance = bg.setScale(2, BigDecimal.ROUND_HALF_DOWN).toString();
		eventCoin.setBalance(banlance);
		eventCoin.setPower(amount);
		String todayAmount = mapper.obtainTodayAmountViaUser(user.getPkid());
		long tAmount = 0;
		if (!StringUtils.isEmpty(todayAmount)) {
			tAmount = Long.valueOf(todayAmount);
		}
		eventCoin.setTodayAmount(tAmount);

		return eventCoin;
	}

	public List<ApiSign> obtainSignList(String phone) {
		Example userExample = new Example(ApiUser.class);
		userExample.createCriteria().andEqualTo("phone", phone);
		List<ApiUser> users = userMapper.selectByExample(userExample);
		if (users == null || users.size() == 0) {
			return null;
		}

		ApiUser user = users.get(0);

		List<String> list = mapper.obtainTodaySign(user.getPkid());
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("HH");
		String time = f.format(date);
		List signs = new ArrayList();
		for (int i = 0; i < 24; i++) {
			ApiSign sign = new ApiSign();
			sign.setHour(String.valueOf(i));
			if (list.contains(String.valueOf(i))) {
				sign.setSign("1");
			} else {
				sign.setSign("0");
				if (i > Integer.valueOf(time)) {
					sign.setSign("3");
				} else if (i == Integer.valueOf(time)) {
					sign.setSign("2");
				}
			}
			signs.add(sign);
		}

		return signs;
	}

	private ApiRichResult dealConvert(ApiUser user, UserCoinAction action, BaseEvent event) {
		ApiRichResult result = new ApiRichResult();

		long zero = CommonUtil.getStartTime();
		long twelve = CommonUtil.getEndTime();
		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		example.createCriteria().andEqualTo("userPkid", user.getPkid())
				.andEqualTo("action", event.getEvent())
				.andGreaterThanOrEqualTo("makeTime", new Timestamp(zero))
				.andLessThanOrEqualTo("makeTime", new Timestamp(twelve));

		List<ApiUserCoinHistory> list = mapper.selectByExample(example);
		int sum = 0;
		for (ApiUserCoinHistory history : list) {
			sum += history.getAmount();
		}
		if (sum > action.getMinPower()) {
			result.setFailMsg("兑换金币数已达到今日最大值，明天在来。");
			return result;
		}

		JSONObject jsonObject = JSON.parseObject(event.getParams());
		int score = 0;
		if (jsonObject != null) {
			score = jsonObject.getIntValue("score");
		}

		if (score < action.getMaxPower()) {
			result.setFailMsg("超过2000筹码才可以兑换");
			return result;
		}

		long amount = score / 100;
		insertHistory(user, action, amount, event);

		ApiUserEventCoin eventCoin = dealAfter(user, action, event, amount);
		eventCoin.setEvent(event.getEvent());

		result.setSucceed(eventCoin, "上报事件成功");

		return result;
	}

	private ApiUserEventCoin dealDoubleCoin(ApiUser user, UserCoinAction action, BaseEvent event) {
		Example example = new Example(ApiUserCoinHistory.class);
		example.setOrderByClause("make_time desc");
		List<ApiUserCoinHistory> apiUserCoinHistories = mapper.selectByExample(example);
		ApiUserCoinHistory history = apiUserCoinHistories.get(0);
		long amount = history.getAmount();

		insertHistory(user, action, amount, event);

		return dealAfter(user, action, event, amount);
	}

	private ApiUserEventCoin dealOffline(ApiUser user, UserCoinAction action, BaseEvent event) {
		long amount = 0;
		JSONObject jsonObject = JSON.parseObject(event.getParams());
		if (jsonObject != null) {
			if (jsonObject.containsKey("pause_time")) {
				long pauseTime = Long.valueOf(jsonObject.getString("pause_time"));
				long current = System.currentTimeMillis();
				long distance = (current - pauseTime) / 1000;
				long min = 30 * 60;
				long max = 2 * 60 * 60;
				if (distance < min) {
					amount = action.getMinPower();
				} else if (distance <= max) {
					amount = action.getMinPower() + (distance - min) * (action.getMaxPower() - action.getMinPower()) / (max - min);
				} else {
					amount = action.getMaxPower();
				}
			} else {
				amount = 0;
			}
		}

		insertHistory(user, action, amount, event);

		return dealAfter(user, action, event, amount);
	}

	private ApiUserEventCoin dealSign(ApiUser user, UserCoinAction action, BaseEvent event) {
		long amount = (long)(action.getMinPower()+Math.random()*(action.getMaxPower()-action.getMinPower()));
		List list = mapper.obtainTodaySign(user.getPkid());
		Date date = new Date();
		SimpleDateFormat f = new SimpleDateFormat("HH");
		String time = f.format(date);
		if (list.contains(time)) {
			amount = 0;
		} else {
			insertHistory(user, action, amount, event);
		}
		return dealAfter(user, action, event, amount);
	}

	private ApiUserEventCoin dealLucky(ApiUser user, UserCoinAction action, BaseEvent event) {
		long amount = (long)(action.getMinPower()+Math.random()*(action.getMaxPower()-action.getMinPower()));
		JSONObject jsonObject = JSON.parseObject(event.getParams());
		if (jsonObject != null) {
			//type 0 金币，type非0礼物
			if (jsonObject.containsKey("type")) {
				int type = jsonObject.getIntValue("type");
				if (type != 0) {
					amount = 0;
				}
			} else {
				return dealAfter(user, action, event, 0);
			}

			ApiLuckyCount count = obtainLuckycount(user.getPhone());
			if (count.getCount() == 0) {
				return dealAfter(user, action, event, 0);
			}
		} else {
			amount = 0;
		}

		insertHistory(user,action, amount, event);

		return dealAfter(user, action, event, amount);
	}

	private ApiUserEventCoin dealOther(ApiUser user, UserCoinAction action, BaseEvent event) {
		if (event.getEvent().startsWith("BOX")) { //抽奖额外奖励只能领一次
			long zero = CommonUtil.getStartTime();
			long twelve = CommonUtil.getEndTime();
			Example example = new Example(ApiUserCoinHistory.class);
			example.setOrderByClause("make_time desc");
			example.createCriteria().andEqualTo("userPkid", user.getPkid())
					.andEqualTo("action", event.getEvent())
					.andGreaterThanOrEqualTo("makeTime", new Timestamp(zero))
					.andLessThanOrEqualTo("makeTime", new Timestamp(twelve));

			List<ApiUserCoinHistory> list = mapper.selectByExample(example);
			if (list != null) {
				if (list.size() > 0) {
					return dealAfter(user, action, event, 0);
				}
			}

			int index = Integer.valueOf(event.getEvent().substring(4, 5));
			Example example1 = new Example(ApiUserCoinHistory.class);
			example1.setOrderByClause("make_time desc");
			example1.createCriteria().andEqualTo("userPkid", user.getPkid())
					.andEqualTo("action", "LOT")
					.andGreaterThanOrEqualTo("makeTime", new Timestamp(zero))
					.andLessThanOrEqualTo("makeTime", new Timestamp(twelve));

			List<ApiUserCoinHistory> list1 = mapper.selectByExample(example1);
			if (list1 != null) {
				if (list1.size() < 5 * (index + 1)) {
					return dealAfter(user, action, event, 0);
				}
			} else {
				return dealAfter(user, action, event, 0);
			}
		}
		long amount = (long)(action.getMinPower()+Math.random()*(action.getMaxPower()-action.getMinPower()));
		insertHistory(user, action, amount, event);

		return dealAfter(user, action, event, amount);
	}

	private void insertHistory(User user, UserCoinAction action, long amount, BaseEvent event) {
		ApiUserCoinHistory history = new ApiUserCoinHistory();
		history.setUserPkid(user.getPkid());
		history.setAction(action.getAction());
		history.setActionName(action.getActionName());
		history.setTimestamp(event.getTimestamp());
		history.setAmount(amount);
		buildInsert(history);
		mapper.insert(history);
	}
}
