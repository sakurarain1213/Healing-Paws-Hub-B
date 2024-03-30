package com.example.hou.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class AffairCreateValidator implements ConstraintValidator<AffairCreateConstraint, List<String>> {
    private int maxLength;

    @Override
    public void initialize(AffairCreateConstraint constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if (list == null || list.isEmpty() || list.size() > maxLength) {
            return false;
        }

        for (String str : list) {
            if (str.length() != 24 || !Pattern.matches("^[a-z0-9]+$", str)) {
                return false;
            }

        }

        return true;
    }
}
