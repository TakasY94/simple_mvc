package org.example.web.validators;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.apache.commons.beanutils.PropertyUtils;


public class AtLeastOneFieldNotNullValidator implements ConstraintValidator<AtLeastOneFieldNotNull, Object> {
    private String[] fieldNames;

    public void initialize(AtLeastOneFieldNotNull constraint) {
        this.fieldNames = constraint.fieldNames();
    }

    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) {
            return false;
        }
        try {
            for (String fieldName:fieldNames){
                Object property = PropertyUtils.getProperty(obj, fieldName);

                if (property != null && !property.toString().isEmpty()) return true;
            }
            return false;
        } catch (Exception e) {
            return false;
        }
    }
}