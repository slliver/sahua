package com.slliver.service;

import com.slliver.base.service.BaseService;
import com.slliver.dao.ApiLoanDaquanDetailMapper;
import com.slliver.entity.ApiLoanDaquanDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: 贷款大全明细
 * @author: slliver
 * @date: 2019/7/8 9:34
 * @version: 1.0
 */
@Service
public class LoanDaquanDetailService extends BaseService<ApiLoanDaquanDetail>  {
    @Autowired
    private ApiLoanDaquanDetailMapper mapper;

    public void deleteBatch(String loanPkid){
        mapper.deleteBatch(loanPkid);
    }


    public List<ApiLoanDaquanDetail> selectListByLoanPkid(String loanPkid){
        Example example = new Example(ApiLoanDaquanDetail.class);
        example.orderBy("type").orderBy("flagSort");
        example.createCriteria().andEqualTo("loanPkid", loanPkid);
        return this.selectByExample(example);
    }
}
