package info.shelfunit.morelambda;

import java.util.Comparator;

public class FirstRunner {

    public void doStuffInlineAndLambda() {
        System.out.println( "-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        String firstString = "This is a string";
        String secondString = "This is a second string";
        Comparator< String > comp = new Comparator< String >() {
            public int compare( String s1, String s2 ) {
                return Integer.compare( s1.length(), s2.length() );
            }
        };
        Comparator< String > comp2 = ( String s1, String s2 ) -> Integer.compare( s1.length(), s2.length() );
        System.out.println( "Strings in inline comparator: " + comp.compare( firstString, secondString ) );
        System.out.println( "Strings w/lambda comparator: " + comp2.compare( firstString, secondString ) );
        System.out.println( "Lambda Runnable: " );
        Runnable r = () -> {
            int i = 0;
            while ( i++ < 10 ) {
                System.out.println( "Here is i: " + i );
            }
        }; // still close with parens
        r.run();
    } // doStuffInlineAndLambda

    public static void main( String args[] ) {

        FirstRunner fr = new FirstRunner();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "doStuffInlineAndLambda" :
                fr.doStuffInlineAndLambda();
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
}
