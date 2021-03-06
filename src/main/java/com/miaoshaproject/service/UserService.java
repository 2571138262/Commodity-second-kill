package com.miaoshaproject.service;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.service.model.UserModel;

public interface UserService {

    /**
     * 通过用户id获取用户对象的方法
     * @param id
     */
    UserModel getUserById(Integer id);
    
    void register(UserModel userModel) throws BusinessException;

    /**
     * 用户登录
     * @param telphone 用户注册手机
     * @param encryptPassword 用户加密后的密码
     * @throws BusinessException
     */
    UserModel validateLogin(String telphone, String encryptPassword) throws BusinessException;
    
}
