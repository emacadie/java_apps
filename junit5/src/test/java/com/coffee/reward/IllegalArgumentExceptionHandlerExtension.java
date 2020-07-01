package com.coffee.reward;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

public class IllegalArgumentExceptionHandlerExtension implements TestExecutionExceptionHandler {

    public void handleTestExecutionException ( ExtensionContext eContext, Throwable throwable )
    throws Throwable {
        System.out.println( "throwable is: " + throwable.getClass().getName().toString() );
        if ( throwable instanceof IllegalArgumentException ) {
            System.out.println( "Exception of type IllegalArgumentException thrown by " +
                    eContext.getRequiredTestClass().getName() +
                    " # " + eContext.getRequiredTestMethod() );
            return; // swallows exception
        }
        throw throwable;
    }

}
