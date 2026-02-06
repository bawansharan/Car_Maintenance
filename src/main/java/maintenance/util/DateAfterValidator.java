package com.drivego.maintenance.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.lang.reflect.Field;
import java.time.LocalDate;

public class DateAfterValidator implements ConstraintValidator<DateAfter, Object> {
    
    private String serviceDateField;
    private String estimatedReadinessField;
    
    @Override
    public void initialize(DateAfter constraintAnnotation) {
        this.serviceDateField = constraintAnnotation.serviceDateField();
        this.estimatedReadinessField = constraintAnnotation.estimatedReadinessField();
    }
    
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // Let @NotNull handle null validation
        }
        
        try {
            LocalDate serviceDate = getFieldValue(value, serviceDateField, LocalDate.class);
            LocalDate estimatedReadiness = getFieldValue(value, estimatedReadinessField, LocalDate.class);
            
            // If estimatedReadiness is null, it's valid (optional field)
            if (estimatedReadiness == null) {
                return true;
            }
            
            // If serviceDate is null, let @NotNull handle it
            if (serviceDate == null) {
                return true;
            }
            
            // Check if estimatedReadiness is after serviceDate
            return estimatedReadiness.isAfter(serviceDate);
            
        } catch (Exception e) {
            // If we can't access the fields, consider it invalid
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T> T getFieldValue(Object object, String fieldName, Class<T> fieldType) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        Object value = field.get(object);
        return fieldType.cast(value);
    }
}


