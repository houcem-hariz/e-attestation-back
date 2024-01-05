package com.eattestation.eattestationback.web.validation;

import jakarta.validation.*;

import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

public class ObjectValidation<T> {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    private final Validator validator = factory.getValidator();

    public Set<String> validate(T objectToValidate){
        Set<ConstraintViolation<T>> violations = validator.validate(objectToValidate);

        if (!violations.isEmpty()){
            return violations.stream()
                    .map(ConstraintViolation::getMessage)
                    .collect(Collectors.toSet());
        }

        return Collections.emptySet();
    }
}
