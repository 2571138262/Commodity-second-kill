package com.miaoshaproject.controller;

import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 基类Controller
 */
public class BaseController {
    
    public static final String CONTENT_TYPE_FORMED="application/x-www-form-urlencoded";
    
    /**
     * 定义exceptionhandler解决未被controller层吸收的exception
     * 通常是处理业务逻辑抛出的异常，而不是服务端抛出的异常
     */
    @ExceptionHandler(Exception.class) // 当系统抛出根类为Exception的异常才会被处理
    @ResponseStatus(HttpStatus.OK)     // 服务器响应状态正常的时候， 即不是服务器的问题，而是业务逻辑的问题
    @ResponseBody
    public Object handlerException(HttpServletRequest request, Exception ex){
        Map<String, Object> responseData = new HashMap<>();
        if (ex instanceof BusinessException){
            // 转换成对应的定制化异常
            BusinessException businessException = (BusinessException)ex;
            // 将当前的异常信息取出来 存在map中返回给前端
            responseData.put("errCode", businessException.getErrCode());
            responseData.put("errMsg", businessException.getErrMsg());
        }else{
            ex.printStackTrace();
            responseData.put("errCode", EmBusinessError.UNKNOWN_ERROR.getErrCode());
            responseData.put("errMsg", EmBusinessError.UNKNOWN_ERROR.getErrMsg());
        }
        return CommonReturnType.create(responseData, "fail");
    }
}
