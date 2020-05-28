package info.shelfunit.morelambda;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.BinaryOperator;
import java.util.function.Function;

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
        // method references vs explicit
        BinaryOperator< Integer > sum1 = ( i1, i2 ) -> i1 + i2;
        BinaryOperator< Integer > sum2 = ( i1, i2 ) -> Integer.sum( i1, i2 );
        BinaryOperator< Integer > sum3 = Integer::sum;
        Random random = new Random();
        int int1 = random.nextInt();
        int int2 = random.nextInt();
        // System.out.println( "sum1 ( i1 + i2 ): " + sum1( int1, int2 ) );
        // BinaryOperator used for reduce
    } // doStuffInlineAndLambda

    public void comparePerson() {
        System.out.println( "-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        Comparator< Person > cmpAge = ( p1, p2 ) -> p2.getAge() - p1.getAge();
        Comparator< Person > cmpFirstName = ( p1, p2 ) -> p1.getFirstName().compareTo( p2.getFirstName() );
        Comparator< Person > cmpLastName  = ( p1, p2 ) -> p1.getLastName(). compareTo( p2.getLastName() );

        Function< Person, Integer > fAge       = p -> p.getAge();
        Function< Person, String >  fFirstName = p -> p.getFirstName();
        Function< Person, String >  fLastName  = p -> p.getLastName();

        Comparator< Person > cmpFAge       = Comparator.comparing( fAge ); // or p -> p.getAge() or Person::getAge
        Comparator< Person > cmpFFirstName = Comparator.comparing( fFirstName );
        Comparator< Person > cmpFLastName  = Comparator.comparing( fLastName );

        Comparator< Person > cmpLAge       = Comparator.comparing( p -> p.getAge() ); // or p -> p.getAge() or Person::getAge
        Comparator< Person > cmpLFirstName = Comparator.comparing( p -> p.getFirstName() );
        Comparator< Person > cmpLLastName  = Comparator.comparing( p -> p.getLastName() );

        Comparator< Person > cmpMRAge       = Comparator.comparing( Person::getAge );
        Comparator< Person > cmpMRFirstName = Comparator.comparing( Person::getFirstName );
        Comparator< Person > cmpMRLastName  = Comparator.comparing( Person::getLastName );
        // chaining comparators
        Comparator< Person > compAgeThenLastName   = cmpLAge.     thenComparing( cmpLLastName );
        Comparator< Person > compLastThenFirstName = cmpLLastName.thenComparing( cmpLFirstName );
        List< Person > personList = Arrays.asList(
                new Person( "George", "Washington", 57 ),
                new Person( "James", "Madison", 57 ),
                new Person( "James", "Monroe", 58 ),
                new Person( "Andrew", "Johnson", 56 ),
                new Person( "Lyndon", "Johnson", 55 )
        );
        for ( int pCount = 0; ( pCount < personList.size() -1 ); pCount++ ) {
            Person currPers = personList.get( pCount );
            System.out.println( "***** Looking at person: " + currPers.toString() );
            for ( int nextP = ( pCount + 1 ); nextP < personList.size(); nextP++ ) {
                Person nextPers = personList.get( nextP );
                System.out.println( "++ Comparing to: " + nextPers.toString() + ", with nextP of " + nextP );
                System.out.println( "Comparing with long comparators (age, fName, lName): " +
                    cmpAge.compare( currPers, nextPers ) + ", " + cmpFirstName.compare( currPers, nextPers ) +
                    ", " + cmpLastName.compare( currPers, nextPers )
                );
                System.out.println( "Comparing with function comparators (age, fName, lName): " +
                    cmpFAge.compare( currPers, nextPers ) + ", " + cmpFFirstName.compare( currPers, nextPers ) +
                    ", " + cmpFLastName.compare( currPers, nextPers )
                );
                System.out.println( "Comparing with lambda comparators (age, fName, lName): " +
                    cmpLAge.compare( currPers, nextPers ) + ", " + cmpLFirstName.compare( currPers, nextPers ) +
                    ", " + cmpLLastName.compare( currPers, nextPers )
                );
                System.out.println( "Comparing with method reference comparators (age, fName, lName): " +
                    cmpMRAge.compare( currPers, nextPers ) + ", " + cmpMRFirstName.compare( currPers, nextPers ) +
                    ", " + cmpMRLastName.compare( currPers, nextPers )
                );
                System.out.println( "Using compAgeThenLastName: " + compAgeThenLastName.compare( currPers, nextPers ) +
                    ", using compLastThenFirstName: " + compLastThenFirstName.compare( currPers, nextPers ) );

            } // for ( int nextP = ( pCount + 1 ); nextP < personList.size(); nextP++ )
        } // for ( int pCount = 0; ( pCount < personList.size() -1 ); pCount++ )
    } // comparePerson

    public static void main( String args[] ) {

        FirstRunner fr = new FirstRunner();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "doStuffInlineAndLambda" :
                fr.doStuffInlineAndLambda();
                break;
            case "comparePerson" :
                fr.comparePerson();
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
}
