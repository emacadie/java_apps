package com.coffee.reward;

import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;

import java.util.Optional;

public class LifecycleExtension implements
        AfterAllCallback,
        AfterEachCallback,
        AfterTestExecutionCallback,
        BeforeAllCallback,
        BeforeEachCallback,
        BeforeTestExecutionCallback {

    // @Override
    public void afterAll( ExtensionContext context ) throws Exception {
        System.out.println( "afterAll for AfterAllCallback" );
    }

    public void afterEach( ExtensionContext context ) throws Exception {

        System.out.println( "afterEach for AfterEachCallback" );
    }

    public void afterTestExecution( ExtensionContext context ) throws Exception {
        System.out.println( "In afterTestExecution for AfterTestExecutionCallback" );
    }

    public void beforeAll( ExtensionContext context ) throws Exception {
        System.out.println( "In beforeAll for BeforeAllCallback" );
    }

    public void beforeEach( ExtensionContext context ) throws Exception {
        System.out.println( "In beforeEach for BeforeEachCallback" );
        // this allows you to print the method of the test with one method call
        // instead of using the stack trace every time like I usually do
        System.out.println( "---- context.getDisplayName() is: " + context.getDisplayName() );
        Optional< Class<?> > testClass = context.getTestClass();
        // System.out.println( "testClass.toString: " + testClass.toString() );
        // this prints out the name of the class
        System.out.println( "testClass.orElse: " + testClass.orElse( String.class ).getName() );
    }

    public void beforeTestExecution( ExtensionContext context ) throws Exception {
        System.out.println( "In beforeTestExecution for BeforeTestExecutionCallback" );
    }

}
