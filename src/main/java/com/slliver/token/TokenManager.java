package com.slliver.token;

import com.slliver.common.domain.UserToken;

/**
 * 对 token 进行操作的接口
 */
public interface TokenManager {

    /**
     * 创建一个 token 关联上指定用户
     *
     * @param userPkid 指定用户的 id
     * @return 生成的 token
     */
    UserToken createToken(String userPkid);

    /**
     * 检查 token 是否有效
     *
     * @param model token
     * @return 是否有效
     */
    boolean checkToken(UserToken model);

    /**
     * 从字符串中解析 token
     *
     * @param authentication 加密后的字符串
     * @return
     */
    UserToken getToken(String authentication);

    /**
     * 清除 token
     *
     * @param userPkid 登录用户的 id
     */
    void deleteToken(String userPkid);
}
