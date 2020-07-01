package com.coffee.reward;

import org.junit.jupiter.api.extension.ExtensionContext;


import java.util.Optional;

public class ExtensionUtils {
    public static final ExtensionContext.Namespace NAMESPACE =
            ExtensionContext.Namespace.create( "My", "Custom", "Namespace", "for", "my", "extension" );

    public static final String EXCEPTION_KEY = "EXCEPTION";


    public static ExtensionContext getEngineContext( ExtensionContext argContext ) {
        Optional< ExtensionContext > context = Optional.of( argContext );
        // while ( context.isPresent() && !( context.get() instanceof JupiterEngineExecutionContext ) ) {
        while ( context.isPresent() && ( null != context.get().getParent() ) ) {
            context = context.get().getParent();
        }
        return context.get();
    }

}
