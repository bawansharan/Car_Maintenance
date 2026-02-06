package com.drivego.maintenance.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DateAfterValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DateAfter {
    String message() default "Estimated readiness date must be after service date";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    
    String serviceDateField() default "serviceDate";
    String estimatedReadinessField() default "estimatedReadiness";
}


