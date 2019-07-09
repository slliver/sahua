package com.slliver.base.controller;

/**
 * @Description: 用一句话具体描述类的功能
 * @author: slliver
 * @date: 2018/3/13 13:31
 * @version: 1.0
 */
public abstract class WebBaseController<T> extends BaseController {
    /**
     * 子类实现getPath方法，调用次方法获取JSP页面完整视图路径
     *
     * @param viewId JSP页面视图ID（eg:list）
     * @return JSP视图路径
     */
    protected String getViewPath(String viewId) {
        return getPath(new String[]{getPath(), viewId});
    }

    /**
     * 直接获取JSP页面路径
     *
     * @param pathPrefix 路径前缀，调用PathConstants常量类获取本模块的路径 (eg:Constants.BASE_PATH_PREFIX)
     * @param viewId     视图ID (eg:list)
     * @return JSP视图路径
     */
    protected String getViewPath(String pathPrefix, String viewId) {

        return pathPrefix + "/" + viewId;
    }

    /**
     * TCM Controller子类自己实现类。
     *
     * @return 子类实现JSP路径前缀
     */
    protected abstract String getPath();


    /**
     * TCM Base Controller 路径构造方法
     *
     * @param path 文件路径数组(eg:demo,list)
     * @return 返回构建好的前端页面路径
     */
    private String getPath(String[] path) {
        StringBuilder sBuilder = new StringBuilder();
        if (path != null && path.length > 0) {
            for (int i = 0; i < path.length; i++) {
                String p = path[i];
                if (p.startsWith("/") && p.endsWith("/")) {
                    p = p.substring(1);
                    p = p.substring(0, p.length() - 1);
                } else if (p.startsWith("/")) {
                    p = p.substring(1);
                } else if (p.endsWith("/")) {
                    p = p.substring(0, p.length() - 1);
                }
                if (i < path.length - 1) {
                    p = p + "/";
                }
                sBuilder.append(p);
            }
        }
        return sBuilder.toString();
    }
}
