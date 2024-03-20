package com.example.hou.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CaseTypeUpdateValidator implements ConstraintValidator<CaseTypeUpdateConstraint, List<String>> {
    private int maxLength;

    @Override
    public void initialize(CaseTypeUpdateConstraint constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if(list == null || list.isEmpty())return true;
        if(list.size() > 10)return false;

        for (String str : list) {
            if (str.length() > maxLength) {
                return false;
            }
        }

        return true;
    }

}
