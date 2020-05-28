package info.shelfunit.morelambda;

import java.util.function.Predicate;

public class DataProcessor {

    // actually not possible in JDK 7; I think that in JDK7 lots of things had to be inline
    Predicate< String > pJDK7 = new Predicate< String >() {
        @Override
        public boolean test( String s ) {
            return s.length() < 20;
        }
    };

    public void workWithPredicates() {
        System.out.println( "-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        String firstString  = "This is the first string";
        String secondString = "second string";
        // Predicate with lambda expression
        Predicate< String > pJDK8 = ( String s ) -> s.length() < 20;
        // Predicate with untyped lambda expression
        Predicate< String > pJDK8Plain = s -> s.length() < 20;
        // How does compiler work without types?
        // there is only one method to implement, and Predicate< String > told us what it is
        // parameters and return types must be compatible
        // in some ways, not that different from pre-JDK8 interfaces
        System.out.println( "firstString: " + firstString + "; test with JDK 7 predicate: " + pJDK7.test( firstString ) );
        System.out.println( "firstString: " + firstString + "; test with JDK 8 predicate: " + pJDK8.test( firstString ) );
        System.out.println( "firstString: " + firstString + "; test with uptyped JDK 8 predicate: " + pJDK8Plain.test( firstString ) );

        System.out.println( "secondString: " + secondString + "; test with JDK 7 predicate: " + pJDK7.test( secondString ) );
        System.out.println( "secondString: " + secondString + "; test with JDK 8 predicate: " + pJDK8.test( secondString ) );
        System.out.println( "secondString: " + secondString + "; test with untyped JDK 8 predicate: " + pJDK8Plain.test( secondString ) );
        System.out.println( "calling getClass on pJDK8: " + pJDK8.getClass() );
    } // workWithPredicates

    public static void main( String args[] ) {

        DataProcessor dp = new DataProcessor();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithPredicates" :
                dp.workWithPredicates();
                break;
            case "comparePerson" :
                // fr.comparePerson();
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
} // end class
