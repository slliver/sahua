package com.slliver.service;

import com.slliver.base.service.BaseService;
import com.slliver.dao.ApiLoanDetailMapper;
import com.slliver.entity.ApiLoanDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/21 10:22
 * @version: 1.0
 */
@Service
public class LoanDetailService extends BaseService<ApiLoanDetail> {

    @Autowired
    private ApiLoanDetailMapper mapper;

    public void deleteBatch(String loanPkid){
        mapper.deleteBatch(loanPkid);
    }


    public List<ApiLoanDetail> selectListByLoanPkid(String loanPkid){
        Example example = new Example(ApiLoanDetail.class);
        example.orderBy("type").orderBy("flagSort");
        example.createCriteria().andEqualTo("loanPkid", loanPkid);
        return this.selectByExample(example);
    }

}
