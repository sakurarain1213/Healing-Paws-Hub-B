package com.example.hou.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class CaseTypeCreateValidator implements ConstraintValidator<CaseTypeCreateConstraint, List<String>> {

    private int maxLength;

    @Override
    public void initialize(CaseTypeCreateConstraint constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if (list == null || list.isEmpty() || list.size() > 10) {
            return false;
        }

        for (String str : list) {
            if (str.length() > maxLength) {
                return false;
            }
        }

        return true;
    }
}
