package com.example.demo.controller.validation;

import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.text.ParseException;
import java.text.SimpleDateFormat;
@Slf4j
public class DateValidator implements ConstraintValidator<Date, java.util.Date> {
    private String after;
    private String before;
    @Override
    public void initialize(Date constraintAnnotation) {
        this.after = constraintAnnotation.after();
        this.before = constraintAnnotation.before();
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(java.util.Date date, ConstraintValidatorContext constraintValidatorContext) {
        if (date != null) {
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            java.util.Date afterDate;
            try {
                afterDate = format.parse(this.after);
            } catch (ParseException e) {
                log.error("В аннотации Date внесена некорректно заданная дата after {}", after);
                throw new IllegalStateException(e);
            }
            java.util.Date beforeDate;
            try {
                beforeDate = format.parse(this.before);
            } catch (ParseException e) {
                log.error("В аннотации Date внесена некорректно заданная дата before {}", before);
                throw new IllegalStateException(e);
            }

            return date.after(afterDate) && date.before(beforeDate);
        }
        return true;
    }
}
