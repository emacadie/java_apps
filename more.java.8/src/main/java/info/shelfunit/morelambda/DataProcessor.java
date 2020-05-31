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

    public void workWithFunctionalInterfaces() {
        System.out.println( "-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        Predicate< String > p1 = s -> s.length() < 20;
        Predicate< String > p2 = s -> s.length() > 5;
        String firstString  = "This is the first string";
        String secondString = "second string";
        String shortString = "java";
        System.out.println( "here is first string: "  + firstString  + "; testing with predicate 1: " + p1.test( firstString ) );
        System.out.println( "here is second string: " + secondString + "; testing with predicate 1: " + p1.test( secondString ) );
        System.out.println( "here is short string: "  + shortString  + "; testing with predicate 1: " + p1.test( shortString ) );

        System.out.println( "here is first string: "  + firstString  + "; testing with predicate 2: " + p2.test( firstString ) );
        System.out.println( "here is second string: " + secondString + "; testing with predicate 2: " + p2.test( secondString ) );
        System.out.println( "here is short string: "  + shortString  + "; testing with predicate 2: " + p2.test( shortString ) );

        // chain them
        Predicate< String > p3 = p1.and( p2 );

        System.out.println( "here is first string: "  + firstString  + "; testing with predicate 3: " + p3.test( firstString ) );
        System.out.println( "here is second string: " + secondString + "; testing with predicate 3: " + p3.test( secondString ) );
        System.out.println( "here is short string: "  + shortString  + "; testing with predicate 3: " + p3.test( shortString ) );
        Predicate< String > p4 = p1.or( p2 );
        System.out.println( "here is first string: "  + firstString  + "; testing with predicate 4: " + p4.test( firstString ) );
        System.out.println( "here is second string: " + secondString + "; testing with predicate 4: " + p4.test( secondString ) );
        System.out.println( "here is short string: "  + shortString  + "; testing with predicate 4: " + p4.test( shortString ) );

        Predicate< String > p5 = Predicate.isEqual( "java" ); // seems like more work
        System.out.println( "here is first string: "  + firstString  + "; testing with predicate 5: " + p5.test( firstString ) );
        System.out.println( "here is second string: " + secondString + "; testing with predicate 5: " + p5.test( secondString ) );
        System.out.println( "here is short string: "  + shortString  + "; testing with predicate 5: " + p5.test( shortString ) );

        Predicate< Integer > p6 = Predicate.isEqual( 1 );
        // try w/primitive
        System.out.println( "p6 w/int = 1: " + p6.test( 1 ) );
        int i = 1;
        System.out.println( "p6 w/int i = 1: " + p6.test( i ) );
    } // workWithFunctionalInterfaces

    public static void main( String args[] ) {

        DataProcessor dp = new DataProcessor();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithPredicates" :
                dp.workWithPredicates();
                break;
            case "workWithFunctionalInterfaces" :
                dp.workWithFunctionalInterfaces();

                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
} // end class
