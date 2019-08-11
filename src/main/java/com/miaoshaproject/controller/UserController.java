package com.miaoshaproject.controller;

import com.alibaba.druid.util.StringUtils;
import com.miaoshaproject.controller.viewObject.UserVO;
import com.miaoshaproject.dao.UserDOMapper;
import com.miaoshaproject.dataobject.UserDO;
import com.miaoshaproject.error.BusinessException;
import com.miaoshaproject.error.EmBusinessError;
import com.miaoshaproject.response.CommonReturnType;
import com.miaoshaproject.service.UserService;
import com.miaoshaproject.service.model.UserModel;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import java.security.MessageDigest;
import java.util.Random;

// DEFAULT_ALLOWED_HEADERS;
// 允许跨域传输所有的header参数，将用于使用token放入header域，做session共享的跨域请求
@CrossOrigin(allowCredentials = "true", allowedHeaders = "*") // 完成所有的SpringBoot对应的返回web请求当中加上跨域请求的功能
@RestController
@RequestMapping("/user") 
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
     
    @Autowired
    private HttpServletRequest httpServletRequest;

    /**
     * 用户登录
     * @param telphone
     * @param password
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public CommonReturnType login(@RequestParam(name = "telphone")String telphone,
                                  @RequestParam(name = "password")String password) throws BusinessException {
        // 入参检验
        if (org.apache.commons.lang3.StringUtils.isEmpty(telphone) ||
                org.apache.commons.lang3.StringUtils.isEmpty(password)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR);
        }
        
        // 用户登录服务，用来校验用户登录是否合法
        UserModel userModel = userService.validateLogin(telphone, this.EncodeByMd5(password));

        // 将登录凭证加入到用户登录成功的session内
        this.httpServletRequest.getSession().setAttribute("IS_LOGIN", true);
        this.httpServletRequest.getSession().setAttribute("LOGIN_USER", userModel);
        
        return CommonReturnType.create(null);
    }

    /**
     * 用户注册接口
     * @param telphone
     * @param otpCode
     * @param name
     * @param gender
     * @param age
     * @param password
     * @return
     * @throws BusinessException
     */
    @RequestMapping(value = "/register", method = {RequestMethod.POST})
    public CommonReturnType register(@RequestParam(name = "telphone")String telphone,
                                     @RequestParam(name = "otpCode")String otpCode,
                                     @RequestParam(name = "name")String name,
                                     @RequestParam(name = "gender")Integer gender,
                                     @RequestParam(name = "age")Integer age,
                                     @RequestParam(name = "password")String password) throws BusinessException {
        
        // 验证手机号和对应的otpcode相符合
        String inSessionOtpCode = (String) this.httpServletRequest.getSession().getAttribute(telphone);
        if (!StringUtils.equals(otpCode, inSessionOtpCode)){
            throw new BusinessException(EmBusinessError.PARAMETER_VALIDATION_ERROR, "短信验证码不符合");
        }
        //用户的注册流程
        UserModel userModel = new UserModel();
//        userModel.setId(0);
        userModel.setName(name);
        userModel.setGender(new Byte(String.valueOf(gender.intValue())));
        userModel.setAge(age);
        userModel.setTelphone(telphone);
        userModel.setRegisterMode("byphone");
        userModel.setThirdPartyId("10001");
        userModel.setEncrptPassword(this.EncodeByMd5(password));
        
        userService.register(userModel);
        return CommonReturnType.create(null);
    }
    
    public String EncodeByMd5(String str){
        try {
            // 确定一个计算方法
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            BASE64Encoder base64en = new BASE64Encoder();
            // 加密字符串
            String newstr = base64en.encode(md5.digest(str.getBytes("utf-8")));
            return newstr;   
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
    
    /**
     * 用户获取otp短信接口
     */
    @RequestMapping(value = "/getotp", method = {RequestMethod.POST})
    public CommonReturnType getOpt(@RequestParam(name = "telphone")String telphone){
        //需要按照一定的规则生成OPT验证码
        Random random = new Random();
        int randomInt = random.nextInt(99999);
        randomInt += 10000;
        String otpCode = String.valueOf(randomInt);
        
        //将OPT验证码同对应的用户的手机号关联, 使用httpsession的方式绑定他的手机号与OTPCODE
        httpServletRequest.getSession().setAttribute(telphone, otpCode);
        
        
        
        //将opt验证码通过短信通道发送给用户, 省略
        System.out.println("telphone = " + telphone + " & otpCode = " + otpCode);
        
        
        return CommonReturnType.create(null);
    }


    /**
     * 获取用户对象
     * @param id
     */
    @GetMapping("/get")
    public CommonReturnType getUser(@RequestParam(name = "id") Integer id) throws BusinessException {
        //调用service服务获取对应的id的用户对象并返回给前端
        UserModel userModel = userService.getUserById(id);
        
        // 若获取的对应用户信息不存在
        if(userModel == null){
//            throw new BusinessException(EmBusinessError.USER_NOT_EXIST);
            userModel.setEncrptPassword("123");
        }
        
        
        // 将核心领域模型用户对象转化为可供UI使用的viewObject
        UserVO userVO = convertFromModel(userModel);
        
        // 返回通用对象
        return CommonReturnType.create(userVO);
    }
    
    private UserVO convertFromModel(UserModel userModel){
        if (userModel == null){
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(userModel, userVO);
        return userVO;
    }
}
