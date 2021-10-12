package com.kms.seft203.validator;

import org.passay.PasswordValidator;
import org.passay.LengthRule;
import org.passay.EnglishCharacterData;
import org.passay.CharacterRule;
import org.passay.RuleResult;
import org.passay.WhitespaceRule;
import org.passay.PasswordData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

/*
This class is defined for validating password. Password must include:
- At least 8 characters
- At least one lower-case character
- At least one digit character
- At least one special character
If the password is valid, it return true. Otherwise, return false and message for user.
 */

public class PasswordValidatorImp implements ConstraintValidator<ValidPassword, String> {

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String password, final ConstraintValidatorContext context) {
        PasswordValidator validator = new PasswordValidator(Arrays.asList(
                //At least 8 characters
                new LengthRule(8, 30),
                //At least one upper-case character
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                //At least one lower-case character
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                //At least one digit character
                new CharacterRule(EnglishCharacterData.Digit, 1),
                //At least one special character
                new WhitespaceRule()));
        RuleResult result = validator.validate(new PasswordData(password));
        return result.isValid();
    }
}
