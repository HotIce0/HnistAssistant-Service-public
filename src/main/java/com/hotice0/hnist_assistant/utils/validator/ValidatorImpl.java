package com.hotice0.hnist_assistant.utils.validator;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

/**
 * @Author HotIce0
 * @Create 2019-05-07 21:08
 */
@Component
public class ValidatorImpl implements InitializingBean {
    private Validator validator;

    public ValidationResult validate(Object bean) {
        ValidationResult validationResult = new ValidationResult();
        Set<ConstraintViolation<Object>> constraintViolationSet = this.validator.validate(bean);
        if(constraintViolationSet.size() > 0) {
            validationResult.setHasError(true);
            constraintViolationSet.forEach(constraintViolation->{
                String propertyName = constraintViolation.getPropertyPath().toString();
                String errMsg = constraintViolation.getMessage();
                validationResult.getErrorMsgMap().put(propertyName, errMsg);
            });
        }
        return validationResult;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        this.validator = Validation.buildDefaultValidatorFactory().getValidator();
    }
}
