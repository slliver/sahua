package com.slliver.service;

import com.slliver.base.service.BaseService;
import com.slliver.common.domain.UploadedFile;
import com.slliver.common.utils.ResourceUtil;
import com.slliver.entity.ApiResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 13:05
 * @version: 1.0
 */
@Service("apiResourceService")
public class ApiResourceService extends BaseService<ApiResource> {

    public int addAttachment(CommonsMultipartFile file, ApiResource attach, String module) {
        String serverPath = ResourceUtil.SERVER_PATH;
        // 上传文件的真实路径
        String folder = ResourceUtil.getFolder(serverPath, module);
        String uploadDir = ResourceUtil.getWriteRealPath(serverPath, folder);
        attach.setFolder(folder);
        try {
            ApiResource resource = insert(attach, file);
            copyFile(uploadDir, file, resource.getNameSystem());
            return 1;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return 0;
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 复制文件
     */
    public String copyFile(String uploadDir, CommonsMultipartFile uploadedFile, String fileName) throws IOException {
        UploadedFile upFile = new UploadedFile();
        upFile.setType(uploadedFile.getContentType());
        upFile.setName(fileName);
        upFile.setLength(uploadedFile.getBytes().length);
        upFile.setBytes(uploadedFile.getBytes());

        if ((!uploadDir.endsWith("/")) && (!uploadDir.endsWith("\\"))) {
            uploadDir = uploadDir.concat("/");
        }

        String uploadFilePath = uploadDir.concat(upFile.getName());
        FileCopyUtils.copy(upFile.getBytes(), new FileOutputStream(uploadFilePath));
        logger.info("copy file...");
        return uploadFilePath;
    }


    private ApiResource insert(ApiResource resource, CommonsMultipartFile uploadedFile) {
        String nameOrigin = uploadedFile.getOriginalFilename();
        if (nameOrigin.indexOf(",") > 0) {
            nameOrigin = nameOrigin.replaceAll(",", "");
        }

        String ext = ResourceUtil.getFileExtention(nameOrigin);
        String systemName = ResourceUtil.getFileSystemName(nameOrigin);
        String simpleName = systemName.substring(0, systemName.lastIndexOf("."));
        String url = ResourceUtil.getUrl(resource.getFolder(), systemName);

        resource.setNameOriginal(ResourceUtil.getFileOriginalName(nameOrigin, 100, 100));
        resource.setNameSystem(systemName);
        resource.setType(String.valueOf(ResourceUtil.getFileType(ext)));
        resource.setSimpleName(simpleName);
        resource.setExtention(ext);
        resource.setUrl(url);
        resource.setSize(uploadedFile.getSize());
        insert(resource);

        return resource;
    }

    /**
     * 设置资源文件上传后的文件的全路径，这里保存的也是不带根目录的，示例 :1101/20160118/edd9fc38_dwmxcz6z.jpg
     */
    public static String setUrl(String folder, String fileName) {
        StringBuffer sb = new StringBuffer();
        sb.append(folder);
        sb.append(fileName);
        return sb.toString();
    }
}
