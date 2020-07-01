package com.coffee.reward;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.TestExecutionExceptionHandler;

import static com.coffee.reward.ExtensionUtils.NAMESPACE;
import static com.coffee.reward.ExtensionUtils.EXCEPTION_KEY;
import static com.coffee.reward.ExtensionUtils.getEngineContext;

public class IllegalArgumentExceptionHandlerStoreExtension implements TestExecutionExceptionHandler {
    public void handleTestExecutionException( ExtensionContext eContext, Throwable throwable )
            throws Throwable {
        System.out.println( "throwable is: " + throwable.getClass().getName().toString() );
        if ( throwable instanceof IllegalArgumentException ) {
            // eContext.getStore( NAMESPACE ).put( EXCEPTION_KEY, throwable );
            getEngineContext( eContext ).getStore( NAMESPACE ).put( EXCEPTION_KEY, throwable );
            return; // swallows exception
        }
        throw throwable;
    }
}
