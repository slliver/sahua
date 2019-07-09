package com.slliver.service;

import com.github.pagehelper.PageHelper;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.service.BaseService;
import com.slliver.common.Constant;
import com.slliver.common.paging.PageWapper;
import com.slliver.dao.ApiUserCoinActionMapper;
import com.slliver.dao.ApiUserCoinMapper;
import com.slliver.entity.ApiUserCoin;
import com.slliver.entity.ApiUserCoinAction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service("apiUserCoinActionService")
public class ApiUserCoinActionService extends BaseService<ApiUserCoinAction> {

	@Autowired
	private ApiUserCoinActionMapper mapper;

	public PageWapper<ApiUserCoinAction> obtainEventList(BaseSearchCondition condition) {
		Integer pageNum = 0;
		Integer pageSize = Constant.WEB_PAGE_SIZE;
		if (condition != null) {
			pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
			pageSize = condition.getPageSize() != null ? condition.getPageSize() : Constant.WEB_PAGE_SIZE;
		}

		PageHelper.startPage(pageNum, pageSize);
		Example example = new Example(ApiUserCoinAction.class);
		example.setOrderByClause("make_time desc");
		return new PageWapper<>(mapper.selectByExample(example));
	}

	public ApiUserCoinAction obtainEventViapkid(String pkid) {
		return mapper.selectByPrimaryKey(pkid);
	}

	public int add(ApiUserCoinAction action) {
		Example example = new Example(ApiUserCoinAction.class);
		example.createCriteria().andEqualTo("action", action.getAction());
		List list = mapper.selectByExample(example);
		if (list != null && list.size() > 0) {
			return 0;
		}
		buildInsert(action);
		return mapper.insert(action);
	}

	public int update(ApiUserCoinAction action) {
		buildUpdate(action);
		return mapper.updateByPrimaryKeySelective(action);
	}

	public int delete(String pkid) {
		return mapper.deleteByPrimaryKey(pkid);
	}
}
