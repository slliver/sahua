package com.slliver.web;

import com.slliver.base.controller.WebBaseController;
import com.slliver.common.domain.AjaxRichResult;
import com.slliver.common.utils.ImgCompressUtil;
import com.slliver.common.utils.ResourceUtil;
import com.slliver.entity.ApiBanner;
import com.slliver.entity.ApiResource;
import com.slliver.service.ApiResourceService;
import com.slliver.service.BannerService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPOutputStream;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/15 10:13
 * @version: 1.0
 */
@Controller
@RequestMapping("resource")
public class ResourceController extends WebBaseController<ApiResource> {


    @Autowired
    private ApiResourceService resourceService;

    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public AjaxRichResult upload(@RequestParam("file") CommonsMultipartFile[] files, @RequestParam("module") String module, HttpServletRequest request) {
        AjaxRichResult result = new AjaxRichResult();
        module = StringUtils.isBlank(module) ? "bannner" : module;
        try {
            List<String> pkids = new ArrayList<>();
            for (CommonsMultipartFile file : files) {
                ApiResource attach = new ApiResource();
                String pkid = get32UUID();
                attach.setPkid(pkid);
                int res = resourceService.addAttachment(file, attach, module);
                if (res == 1) {
                    pkids.add(pkid);
                }
            }
            result.setSucceed(pkids, "上传成功");
        } catch (Exception e) {
            logger.error(e.toString(), e);
            result.setFailMsg("上传失败");
        }

        return result;
    }

    @RequestMapping(value = "/showImage", method = RequestMethod.GET)
    public void showImage(String pkid, String size, HttpServletRequest request, HttpServletResponse response) {
        ApiResource attach = this.resourceService.selectByPkid(pkid);
        if (attach != null) {
            String imgPath = getImageUrlBySize(attach.getFolder(), attach.getNameSystem().substring(0, attach.getNameSystem().indexOf(".")),
                    attach.getExtention(), StringUtils.isNotEmpty(size) ? size : "");
            String oriName = attach.getNameOriginal();
            if (new File(imgPath).exists()) {
                viewImage(pkid, imgPath, oriName, request, response);
            } else {
                logger.info("本地图片文件不存在!");
            }
        } else {
            logger.info("文件不存在!");
        }
    }

    @Autowired
    private BannerService bannerService;

    @RequestMapping(value = "/showBannerImage", method = RequestMethod.GET)
    public String showBannerImage(Model model, @RequestParam("bannerPkid") String bannerPkid, HttpServletRequest request, HttpServletResponse response) {
        ApiBanner banner = this.bannerService.selectByPkid(bannerPkid);
        model.addAttribute("resourcePkid", banner != null ? banner.getLogoPkid() : "");
        return getViewPath("show");
    }


    /**
     * 预览图片
     *
     * @param pkid
     * @param imgPath  服务器完整路径
     * @param oriName
     * @param request
     * @param response
     */
    private void viewImage(String pkid, String imgPath, String oriName,
                           HttpServletRequest request, HttpServletResponse response) {
        InputStream is = null;
        OutputStream os = null;
        GZIPOutputStream gos = null;

        try {
            is = new FileInputStream(imgPath);
            os = response.getOutputStream();
            String fileName = oriName;
            // 需要修改IE自定义用户代理字符串（gpedit.msc）
            if (request.getHeader("User-Agent").toUpperCase().indexOf("MSIE") > 0) {
                fileName = URLEncoder.encode(fileName, "UTF-8");
            } else {
                fileName = new String(fileName.getBytes("UTF-8"), "ISO8859-1");
            }
            response.setHeader("Content-disposition", "inline;filename=" + fileName);
            response.setCharacterEncoding("UTF-8");
            IOUtils.copy(is, os);
            // 压缩
            gos = new GZIPOutputStream(os);
            gos.finish();
            gos.flush();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        } finally {
            try {
                if (is != null) {
                    is.close();
                }
                if (os != null) {
                    os.close();
                }
                if (gos != null) {
                    gos.close();
                }
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }

    public String getImageUrlBySize(String folder, String simpleName, String ext, String size) {
        if (StringUtils.isEmpty(size)) {
            return getImageUrl(folder, simpleName, ext);
        } else {
            return getImageFullUrl(folder, simpleName, ext, size);
        }
    }

    /**
     * 原图大小
     */
    public String getImageUrl(String folder, String simpleName, String ext) {
        StringBuffer sb = new StringBuffer();
        String resourceHost = ResourceUtil.SERVER_PATH;
        if (!StringUtils.isBlank(resourceHost)) {
            if ((!resourceHost.endsWith("/")) && (!resourceHost.endsWith("\\"))) {
                resourceHost = resourceHost.concat("/");
            }
            sb.append(resourceHost);
        }
        sb.append(folder);
        sb.append(simpleName);
        sb.append(".");
        sb.append(ext);
        return sb.toString();
    }

    /**
     * 针对图片，返回指定大小的图片地址
     */
    public String getImageFullUrl(String folder, String simpleName, String ext, String size) {
        StringBuffer sb = new StringBuffer();
        String resourceHost = ResourceUtil.SERVER_PATH;
        if (!StringUtils.isBlank(resourceHost)) {
            if ((!resourceHost.endsWith("/")) && (!resourceHost.endsWith("\\"))) {
                resourceHost = resourceHost.concat("/");
            }
            sb.append(resourceHost);
        }
        sb.append(folder);
        sb.append(simpleName);
        sb.append("_");
        sb.append(size);
        sb.append(".");
        sb.append(ext);

        String oldUrl = resourceHost + folder + simpleName + "." + ext;
        if (size.indexOf("_") > -1) {
            // 如果缩略图不存在，创建压缩图 add slliver 2017-11-02 13:20
            File file = new File(sb.toString());
            if(!file.exists()){
                String[] s = size.split("_");
                ImgCompressUtil.compressImage(oldUrl, sb.toString(), Integer.valueOf(s[0]), Integer.valueOf(s[1]));
            }
        } else {
            ImgCompressUtil.compressImage(oldUrl, sb.toString(), Integer.valueOf(size));
        }

        return sb.toString();
    }

    @Override
    protected String getPath() {
        return "/resource";
    }
}
