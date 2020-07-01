package com.coffee.reward;

import org.junit.jupiter.api.extension.ConditionEvaluationResult;
import org.junit.jupiter.api.extension.ExecutionCondition;
import org.junit.jupiter.api.extension.ExtensionContext;

import static com.coffee.reward.ExtensionUtils.NAMESPACE;
import static com.coffee.reward.ExtensionUtils.EXCEPTION_KEY;

public class DisableTestsifExceptionThrownExtension implements ExecutionCondition {

    public ConditionEvaluationResult evaluateExecutionCondition( ExtensionContext context ) {
        ConditionEvaluationResult result = ConditionEvaluationResult.enabled( "No exception thrown so far" );
        System.out.println( context );
        Throwable t = ( Throwable ) context.getStore( NAMESPACE ).get( EXCEPTION_KEY );
        if ( null != t ) {
            result = ConditionEvaluationResult.disabled( "An exception was thrown: " + t.getMessage() );
        }
        return result;
    }

}
