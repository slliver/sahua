package com.slliver.common.utils;

import com.slliver.common.Constant;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/15 10:22
 * @version: 1.0
 */
public class ResourceUtil {

    //public static final String UPLOAD_ROOT_PATH = "d:\\robin\\upload";
    //public static final String SERVER_PATH = "d:\\robin\\upload";

    public static final String UPLOAD_ROOT_PATH = "/home/robin/upload";
    public static final String SERVER_PATH = "/home/robin/upload";

    /**
     * 获取原始资源文件名并保存，如果文件长度大于数据库指定文件存储空间，进行处理
     *
     * @param fileName 当前资源原始文件
     * @param dataLen  该字段数据库最大存储长度
     * @param max      处理后的长度
     */
    public static String getFileOriginalName(String fileName, int dataLen, int max) {
        if (fileName.length() > dataLen) {
            String name = fileName.substring(0, fileName.lastIndexOf("."));
            if (fileName.length() > max) {
                return fileName.substring(0, max) + "." + getFileExtention(name);
            }
        }

        return fileName;
    }

    /**
     * 获取资源文件扩展名
     */
    public static String getFileExtention(String fileName) {
        int i = fileName.lastIndexOf('.');
        String extenstion = "";
        if ((i > -1) && (i < (fileName.length() - 1))) {
            extenstion = fileName.substring(i + 1);
        }

        return extenstion.toLowerCase();
    }

    /**
     * 随机生成资源文件名称名称，防止文件名称重复
     */
    public static String getFileSystemName(String fileName) {
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        StringBuffer sb = new StringBuffer();
        sb.append(RandomUtil.random(8).toLowerCase());
        sb.append("_");
        sb.append(RandomUtil.random(8).toLowerCase());
        sb.append(".");
        sb.append(ext);
        return sb.toString();
    }

    /**
     * 获取某个资源的完整路径
     */
    public static String getUrl(String forder, String fileName) {
        StringBuffer sb = new StringBuffer();
        /**
        String readHost = SysProp.getValue(SysConst.RESOURCE_READ_HOST);
        String readRoot = SysProp.getValue(SysConst.RESOURCE_READ_ROOT);
        if (!StringUtils.isBlank(readHost)) {
            sb.append(readHost);
            sb.append("/");
        }
        if (!StringUtils.isBlank(readRoot)) {
            sb.append(readRoot);
            sb.append("/");
        }
        **/
        sb.append(forder);
        sb.append(fileName);
        return sb.toString();
    }

    /**
     * 获取文件分类
     */
    public static int getFileType(String ext) {
        if (imageExtentionMap.get(ext) != null) {
            // 图片
            return Constant.RESOURCE.IMAGE;
        } else {
            return 0;
        }
    }

    /**
     * 系统中允许上传的图片类型
     */
    private static Map<String, String> imageExtentionMap = new HashMap<>();

    static {
        if (imageExtentionMap.isEmpty()) {
            imageExtentionMap.put("jpg", "jpg");
            imageExtentionMap.put("jpeg", "jpeg");
            imageExtentionMap.put("png", "png");
        }
    }

    public static Map<String, String> imageExtentionMap() {
        return imageExtentionMap;
    }

    public static String getFolder(String rootPath, String module) {
        StringBuffer folder = new StringBuffer();
        Date date = DateUtil.getCurrentDate();
        folder.append(module);
        folder.append("/");
        folder.append(DateFormatUtils.format(date, "yyyyMMdd"));
        folder.append("/");

        File dir = new File(rootPath + "/" + folder.toString());
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return folder.toString();
    }

    public static String getWriteRealPath(String rootPath, String folder) {
        StringBuffer writePath = new StringBuffer();
        writePath.append(rootPath);
        writePath.append("/");
        writePath.append(folder);
        return writePath.toString();
    }

    /**
     * 获取资源写入真实路径，如果配置了写入根目录，示例:根目录/1001/20160108/文件名，如果没有配置根目录: 1001/20160108/文件名
     *
     * @param modularName 模块名称{resource, document...}
     * @param folder
     * @param fileName
     * @return
     */
    /**
    public static String getWriteRealPath(String modularName, String folder, String fileName) {
        StringBuffer writePath = new StringBuffer();
        String writeRoot = "";
        if (modularName.equals(Constants.RESOURCE)) {
            writePath.append(SysProp.getValue(SysConst.RESOURCE_WRITE_HOME));
            writeRoot = SysProp.getValue(SysConst.RESOURCE_WRITE_ROOT);
        } else {
            writePath.append(SysProp.getValue(SysConst.DOCUMENT_WRITE_HOME));
            writeRoot = SysProp.getValue(SysConst.DOCUMENT_WRITE_ROOT);
        }

        if (!StringUtils.isBlank(writeRoot)) {
            writePath.append(writeRoot);
            writePath.append("/");
        }

        writePath.append(folder);
        writePath.append(fileName);
        return writePath.toString();
    }
    **/
}
