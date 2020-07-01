package com.coffee.reward;

import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target( { ElementType.TYPE, ElementType.METHOD } )
@Retention( RetentionPolicy.RUNTIME )
@ExtendWith(
        {
                IllegalArgumentExceptionHandlerExtension.class,
                RewardByConversionParameterResolver.class,
                DisableTestsifExceptionThrownExtension.class
        }
)
public @interface TestWithErrorHandler {

}
