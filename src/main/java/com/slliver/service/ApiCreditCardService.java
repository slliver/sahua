package com.slliver.service;

import com.github.pagehelper.PageHelper;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.service.BaseService;
import com.slliver.common.Constant;
import com.slliver.common.paging.PageWapper;
import com.slliver.dao.ApiCreditCardMapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiCreditCard;
import com.slliver.entity.ApiLoanData;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;
import java.util.Objects;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 13:21
 * @version: 1.0
 */
@Service("apiCreditCardService")
public class ApiCreditCardService extends BaseService<ApiCreditCard> {

    @Autowired
    private ApiCreditCardMapper mapper;
    @Autowired
    private BannerService bannerService;

    public PageWapper<ApiCreditCard> selectListByPage(BaseSearchCondition condition) {
        Integer pageNum = 0;
        Integer pageSize = Constant.API_PAGE_SIZE;
        if (condition != null) {
            pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
            pageSize = condition.getPageSize() != null ? condition.getPageSize() : pageSize;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ApiCreditCard> list = this.mapper.selectListByPage(condition);
        return new PageWapper<>(list);
    }

    public PageWapper<ApiCreditCard> selectListByApi(BaseSearchCondition condition) {
        Integer pageNum = 0;
        Integer pageSize = Constant.API_PAGE_SIZE;
        if (condition != null) {
            pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
            pageSize = condition.getPageSize() != null ? condition.getPageSize() : pageSize;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ApiCreditCard> list = this.mapper.selectListByApi(condition);
        return new PageWapper<>(list);
    }

    public ApiCreditCard selectByCardName(String cardName) {
        Example example = new Example(ApiCreditCard.class);
        example.createCriteria().andEqualTo("cardName", cardName);
        List<ApiCreditCard> list = this.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    /**
     * 保存
     */
    public boolean save(ApiCreditCard card) {
        if(card.getPriority() == null){
            card.setPriority((short) 999);
        }
        int count = this.insert(card);
        if (count == 0) {
            return false;
        }
        if (StringUtils.isNotBlank(card.getBannerPkid())) {
            ApiBanner banner = bannerService.selectByPkid(card.getBannerPkid());
            banner.setBussinessPkid(card.getPkid());

            this.bannerService.update(banner);
        }

        return true;
    }

    /**
     * 修改
     */
    public boolean updateCreditCard(ApiCreditCard card) {
        String originalBannerPkid = card.getOriginalBannerPkid();
        String bannerPkid = card.getBannerPkid();
        if (StringUtils.isBlank(bannerPkid)) {
            // 解绑，得看原来有没有绑定
            ApiBanner banner = bannerService.selectByPkid(originalBannerPkid);
            if (banner != null) {
                banner.setBussinessPkid("");
                this.bannerService.update(banner);
            }
        } else {
            // 绑定
            if (!Objects.equals(originalBannerPkid, bannerPkid)) {
                // 更新旧的
                ApiBanner bannerOld = bannerService.selectByPkid(originalBannerPkid);
                if (bannerOld != null) {
                    bannerOld.setBussinessPkid("");
                    this.bannerService.update(bannerOld);
                }
                // 更新新的
                ApiBanner bannerNew = bannerService.selectByPkid(bannerPkid);
                if (bannerNew != null) {
                    bannerNew.setBussinessPkid(card.getPkid());
                    this.bannerService.update(bannerNew);
                }
                logger.info("credit card update");
            }
        }

        // 更新自己
        if(card.getPriority() == null){
            card.setPriority((short) 999);
        }
        this.update(card);
        return true;
    }
    /**
     * 删除
     */
    public void deleteByPkid(String pkid){
        if(StringUtils.isBlank(pkid)){
            return;
        }

        ApiCreditCard card = this.selectByPkid(pkid);
        String bannerPkid = card.getBannerPkid();
        if(StringUtils.isNotBlank(bannerPkid)){
            ApiBanner banner = this.bannerService.selectByPkid(bannerPkid);
            // 更新banner的引用为空
            banner.setBussinessPkid("");
            this.bannerService.update(banner);
        }

        this.deletePhysically(card);
    }
}
