package com.miaoshaproject.service.model;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * User model 用户领域模型对象
 */
public class UserModel {

    /**
     * id
     */
    private Integer id;

    /**
     * 用户名
     */
    // @NotBlank 表示对应的name不能为null 不能为空字符串
    @NotBlank(message = "用户名不能为空")
    private String name;

    /**
     * 性别
     */
    // @NotNull 表示对应的gender不能为null 
    @NotNull(message = "性别不能不填")
    private Byte gender;

    /**
     * 年龄
     */
    // @NotNull 对应的属性不能为null
    @NotNull(message = "年龄不能不填")
    @Min(value = 0, message = "年龄必须大于0")
    @Max(value = 150, message = "年龄必须小于150")
    private Integer age;

    /**
     * 电话
     */
    @NotBlank(message = "手机号能为空")
    private String telphone;

    /**
     * 注册方式
     */
    private String registerMode;

    /**
     * 第三方账号id
     */
    private String thirdPartyId;

    /**
     * 用户密码
     */
    @NotBlank(message = "密码不能为空")
    private String encrptPassword;


    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Byte getGender() {
        return gender;
    }

    public void setGender(Byte gender) {
        this.gender = gender;
    }

    public String getTelphone() {
        return telphone;
    }

    public void setTelphone(String telphone) {
        this.telphone = telphone;
    }

    public String getRegisterMode() {
        return registerMode;
    }

    public void setRegisterMode(String registerMode) {
        this.registerMode = registerMode;
    }

    public String getThirdPartyId() {
        return thirdPartyId;
    }

    public void setThirdPartyId(String thirdPartyId) {
        this.thirdPartyId = thirdPartyId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword;
    }
}
