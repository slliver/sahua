package com.slliver.service;

import com.github.pagehelper.PageHelper;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.service.BaseService;
import com.slliver.common.Constant;
import com.slliver.common.paging.PageWapper;
import com.slliver.dao.ApiBannerMapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiCreditCard;
import com.slliver.entity.ApiLoanData;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/16 15:52
 * @version: 1.0
 */
@Service("bannerService")
public class BannerService extends BaseService<ApiBanner> {

    @Autowired
    private ApiBannerMapper mapper;
    @Autowired
    private ApiLoanDataService loanDataService;
    @Autowired
    private ApiCreditCardService creditCardService;

    public PageWapper<ApiBanner> selectListByPage(BaseSearchCondition condition) {
        Integer pageNum = 0;
        Integer pageSize = Constant.API_PAGE_SIZE;
        if (condition != null) {
            pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
            pageSize = condition.getPageSize() != null ? condition.getPageSize() : pageSize;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ApiBanner> list = this.mapper.selectListByPage(condition);
        return new PageWapper<>(list);
    }


    /**
     * 根据业务类型获取对应的banner图片
     *
     * @param bussinessType
     * @return
     */
    public List<ApiBanner> selectByBussinessTypeApi(String bussinessType) {
        return mapper.selectByBussinessTypeApi(bussinessType);
    }

    public List<ApiBanner> selectByBussinessType(String bussinessType) {
        return mapper.selectByBussinessType(bussinessType);
    }

    /**
     * 解除绑定
     */
    public boolean updateBind(String pkid) {
        ApiBanner banner = this.selectByPkid(pkid);
        if (banner == null) {
            return false;
        }
        if (StringUtils.isBlank(banner.getBussinessPkid())) {
            return false;
        }

        updateCancelBind(banner.getBussinessType(), banner.getBussinessPkid());
        // 更新bannner
        banner.setBussinessPkid("");
        update(banner);
        return true;
    }

    public void delete(String pkid) {
        ApiBanner banner = this.selectByPkid(pkid);
        if (banner == null) {
            return;
        }

        this.deletePhysically(banner);
        updateCancelBind(banner.getBussinessType(), banner.getBussinessPkid());
    }

    public void updateCancelBind(String bussinessType, String bussinessPkid) {
        if (StringUtils.isBlank(bussinessPkid)) {
            return;
        }

        if (Objects.equals(Constant.BANNER_LOAN, bussinessType)) {
            // 极速贷
            ApiLoanData loan = this.loanDataService.selectByPkid(bussinessPkid);
            loan.setBannerPkid("");
            this.loanDataService.update(loan);
        }
        if (Objects.equals(Constant.BBANNER_CREDIT_CARD, bussinessType)) {
            // 信用卡
            ApiCreditCard card = this.creditCardService.selectByPkid(bussinessPkid);
            card.setBannerPkid("");
            this.creditCardService.update(card);
        }
    }
}
