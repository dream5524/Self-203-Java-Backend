package com.kms.seft203.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Pattern;

/*
 * This class used to validate well-formed email and customize the @ValidEmail annotation
 * */
public class EmailValidatorImp implements ConstraintValidator<ValidEmail, String> {
    private String regex = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
            + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        //Do nothing
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Pattern pat = Pattern.compile(regex);
        return pat.matcher(email).matches();
    }
}
