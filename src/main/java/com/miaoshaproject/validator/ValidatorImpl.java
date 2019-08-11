package com.miaoshaproject.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 在这个检验实现类被调用之后会回调afterPropertiesSet()方法
 */
@Component
public class ValidatorImpl implements InitializingBean {

    private Validator validator;

    /**
     * 实现校验方法并返回校验结果   --- 如果有错误将错误结果取出放到返回结果中
     * @param bean
     * @return
     */
    public ValidationResult validate(Object bean){
        final ValidationResult result = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = validator.validate(bean);
        if (constraintViolationSet.size() > 0){
            // 有错误
            result.setHasErrors(true);
            constraintViolationSet.forEach(constraintViolation -> {
                // 将错误的字段找到，存到ValidationResult结果集中
                String errMsg = constraintViolation.getMessage();
                String propertyName = constraintViolation.getPropertyPath().toString();
                result.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return result;
    }

    /**
     * javax.Validator的校验器 
     * 在这个检验实现类被调用之后会回调afterPropertiesSet()方法
     * @throws Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // 将hibernate validator通过工厂的初始化方式使其实例化
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
