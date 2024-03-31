package com.example.hou.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.regex.Pattern;

public class AffairUpdateValidator implements ConstraintValidator<AffairUpdateConstraint, List<String>> {
    private int maxLength;

    @Override
    public void initialize(AffairUpdateConstraint constraintAnnotation) {
        this.maxLength = constraintAnnotation.maxLength();
    }

    @Override
    public boolean isValid(List<String> list, ConstraintValidatorContext context) {
        if (list == null || list.isEmpty()) {
            return true;
        }

        if (list.size() > maxLength)return false;

        for (String str : list) {
            if (str.length() != 24 || !Pattern.matches("^[a-z0-9]+$", str)) {
                return false;
            }

        }

        return true;
    }
}
