package com.slliver.service;

import com.github.pagehelper.PageHelper;
import com.slliver.base.domain.BaseSearchCondition;
import com.slliver.base.domain.BaseSearchConditionWithoutPagination;
import com.slliver.base.service.BaseService;
import com.slliver.common.Constant;
import com.slliver.common.paging.PageWapper;
import com.slliver.dao.ApiLoanDataMapper;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiLoanData;
import com.slliver.entity.ApiLoanDetail;
import com.slliver.entity.ApiResource;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.*;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/12 16:39
 * @version: 1.0
 */
@Service("apiLoanDataService")
public class ApiLoanDataService extends BaseService<ApiLoanData> {

    @Autowired
    private ApiLoanDataMapper mapper;

    @Autowired
    private BannerService bannerService;

    @Autowired
    private LoanDetailService loanDetailService;

    @Autowired
    private ApiResourceService resourceService;

    public PageWapper<ApiLoanData> selectListByPage(BaseSearchCondition condition) {
        Integer pageNum = 0;
        Integer pageSize = Constant.WEB_PAGE_SIZE;
        if (condition != null) {
            pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
            pageSize = condition.getPageSize() != null ? condition.getPageSize() : Constant.WEB_PAGE_SIZE;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ApiLoanData> loanDataList = this.mapper.selectListByPage(condition);
        return new PageWapper<>(loanDataList);
    }

    /**
     * 接口使用
     */
    public PageWapper<ApiLoanData> selectListByApi(BaseSearchCondition condition) {
        Integer pageNum = 0;
        Integer pageSize = Constant.WEB_PAGE_SIZE;
        if (condition != null) {
            pageNum = condition.getPageNum() != null ? condition.getPageNum() : 0;
            pageSize = condition.getPageSize() != null ? condition.getPageSize() : Constant.WEB_PAGE_SIZE;
        }

        PageHelper.startPage(pageNum, pageSize);
        List<ApiLoanData> loanDataList = this.mapper.selectListByApi(condition);
        return new PageWapper<>(loanDataList);
    }

    /**
     * 极速贷信息列表不分页
     */
    public List<ApiLoanData> selectListByApiNoPagination(BaseSearchConditionWithoutPagination condition) {
        return this.mapper.selectListByApiNoPagination(condition);
    }


    public boolean save(ApiLoanData loan) {
        if (loan.getPriority() == null) {
            loan.setPriority((short) 999);
        }
        int count = this.insert(loan);
        if (count == 0) {
            return false;
        }

        String loanPkid = loan.getPkid();
        String bannerPkid = loan.getBannerPkid();
        if (StringUtils.isNotBlank(bannerPkid)) {
            ApiBanner banner = bannerService.selectByPkid(bannerPkid);
            banner.setBussinessPkid(loanPkid);
            this.bannerService.update(banner);
        }
        // 更新对应的明细信息
        saveOrUpdateDetail(loan);

        return true;
    }

    public boolean updateLoan(ApiLoanData loan) {
        String originalBannerPkid = loan.getOriginalBannerPkid();
        String bannerPkid = loan.getBannerPkid();
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
                    bannerNew.setBussinessPkid(loan.getPkid());
                    this.bannerService.update(bannerNew);
                }
            }
        }

        // 更新自己
        if (loan.getPriority() == null) {
            loan.setPriority((short) 999);
        }
        this.update(loan);
        // 更新对应的明细信息
        saveOrUpdateDetail(loan);
        return true;
    }

    public ApiLoanData selectByOrgName(String orgName) {
        Example example = new Example(ApiLoanData.class);
        example.createCriteria().andEqualTo("orgName", orgName);
        List<ApiLoanData> list = this.selectByExample(example);
        return CollectionUtils.isNotEmpty(list) ? list.get(0) : null;
    }

    public Integer delete(BaseSearchCondition[] params) {
        Integer result = 0;
        if (params == null) {
            return result;
        }

        for (BaseSearchCondition condition : params) {
            int del = this.deleteLogically(condition.getPkid());
            result = result + del;
            // 删除已经绑定的bannner

        }

        return result;
    }

    /**
     * 删除
     */
    public void deleteByPkid(String pkid) {
        if (StringUtils.isNotBlank(pkid)) {
            ApiLoanData loan = this.selectByPkid(pkid);
            String bannerPkid = loan.getBannerPkid();
            if (StringUtils.isNotBlank(bannerPkid)) {
                ApiBanner banner = this.bannerService.selectByPkid(bannerPkid);
                // 更新banner的引用为空
                banner.setBussinessPkid("");
                this.bannerService.update(banner);
            }

            // 删除对应的明细信息
            saveOrUpdateDetail(loan);
            this.deletePhysically(loan);
        }
    }

    /**
     * 处理明细
     */
    private void saveOrUpdateDetail(ApiLoanData loan) {
        String loanPkid = loan.getPkid();
        if (StringUtils.isNotBlank(loanPkid)) {
            // 删除当前极速贷绑定的信息
            loanDetailService.deleteBatch(loanPkid);
        }

        saveDetail(loanPkid, loan.getApplyConditions());
        saveDetail(loanPkid, loan.getReqMaterials());
        saveDetail(loanPkid, loan.getEarlyRepayments());
    }

    private void saveDetail(String loanPkid, List<ApiLoanDetail> list) {
        if (CollectionUtils.isEmpty(list)) {
            return;
        }
        ApiLoanDetail record = null;
        Short soft = 1;
        for (ApiLoanDetail detail : list) {
            if (StringUtils.isNotBlank(detail.getRemark())) {
                record = new ApiLoanDetail();
                record.setLoanPkid(loanPkid);
                record.setType(detail.getType());
                record.setRemark(detail.getRemark());
                record.setFlagSort(soft++);
                this.loanDetailService.insert(record);
            }
        }
    }


    public Map<String, List<ApiLoanDetail>> selectDetails(String loanPkid) {
        Map<String, List<ApiLoanDetail>> result = new HashMap<>();
        List<ApiLoanDetail> lsit = this.loanDetailService.selectListByLoanPkid(loanPkid);
        if (CollectionUtils.isEmpty(lsit)) {
            return result;
        }

        // 申请条件
        List<ApiLoanDetail> applyConditions = new ArrayList();
        // 所需材料
        List<ApiLoanDetail> reqMaterials = new ArrayList();
        // 提前还款说明
        List<ApiLoanDetail> earlyRepayments = new ArrayList();

        for (ApiLoanDetail detail : lsit) {
            String type = detail.getType();
            if (StringUtils.isBlank(type)) {
                continue;
            }
            if (Objects.equals("1", type)) {
                applyConditions.add(detail);
            } else if (Objects.equals("2", type)) {
                reqMaterials.add(detail);
            } else if (Objects.equals("3", type)) {
                earlyRepayments.add(detail);
            }
        }

        result.put("applyConditions", applyConditions);
        result.put("reqMaterials", reqMaterials);
        result.put("earlyRepayments", earlyRepayments);

        return result;
    }

    public ApiLoanData selectLoanDetails(String loanPkid) {
        ApiLoanData loanData = this.selectByPkid(loanPkid);
        if (loanData != null) {
            // 获取Logo图片地址
            loanData.setHttpUrl(this.getLogoUrl(loanData.getLogoPkid()));

            List<ApiLoanDetail> lsit = this.loanDetailService.selectListByLoanPkid(loanPkid);
            if (CollectionUtils.isEmpty(lsit)) {
                return loanData;
            }

            // 申请条件
            List<ApiLoanDetail> applyConditions = new ArrayList();
            // 所需材料
            List<ApiLoanDetail> reqMaterials = new ArrayList();
            // 提前还款说明
            List<ApiLoanDetail> earlyRepayments = new ArrayList();

            for (ApiLoanDetail detail : lsit) {
                String type = detail.getType();
                if (StringUtils.isBlank(type)) {
                    continue;
                }
                if (Objects.equals("1", type)) {
                    applyConditions.add(detail);
                } else if (Objects.equals("2", type)) {
                    reqMaterials.add(detail);
                } else if (Objects.equals("3", type)) {
                    earlyRepayments.add(detail);
                }
            }

            loanData.setApplyConditions(applyConditions);
            loanData.setReqMaterials(reqMaterials);
            loanData.setEarlyRepayments(earlyRepayments);


        }

        return loanData;
    }

    private String getLogoUrl(String logoPkid) {
        if (StringUtils.isBlank(logoPkid)) {
            return "";
        }

        ApiResource resource = this.resourceService.selectByPkid(logoPkid);
        return Constant.SERVER_IMAGE_ADDRESS + "/" + resource.getUrl();
    }
}
