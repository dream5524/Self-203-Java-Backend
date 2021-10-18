package com.kms.seft203.validator;

import com.auth0.jwt.interfaces.Payload;

import javax.validation.Constraint;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, TYPE_USE})
@Constraint(validatedBy = EmailValidatorImp.class)

/*
* This class used to show message when errors occur from input clients side.
* */
public @interface ValidEmail {
    String message() default "Please fill in correct format of email address, ex: abc@gmail.com";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
