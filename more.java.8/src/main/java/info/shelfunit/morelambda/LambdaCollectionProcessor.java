package info.shelfunit.morelambda;

import java.util.*;

public class LambdaCollectionProcessor {

    public void workWithCollections() {
        System.out.println("-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
        List< Person> rootPersonList = Arrays.asList(
            new Person( "George", "Washington", 57 ),
            new Person( "James", "Madison", 57 ),
            new Person( "James", "Monroe", 58 ),
            new Person( "Andrew", "Johnson", 56 ),
            new Person( "Lyndon", "Johnson", 55 )
        );
        List< Person > personList = new ArrayList< Person >( );
        System.out.println( "Using forEach on personList: " );
        personList.forEach( p -> System.out.println( "Here is person: " + p.toString() ) );
        personList.removeIf( p -> p.getAge() >= 57 );
        System.out.println( "Person List younger than 57:" );
        personList.forEach( p -> System.out.println( p.toString() ) );
    } // workWithCollections

    public void workWithMaps() {
        System.out.println("-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
        Map< Integer, String > presMap = new LinkedHashMap< Integer, String >();
        presMap.putIfAbsent( 1, "George Washington");
        presMap.putIfAbsent( 2, "John Adams" );
        presMap.putIfAbsent( 3, "Thomas Jefferson" );
        presMap.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        System.out.println( "\ngetOrDefault( 1, 'not a president' ): " + presMap.getOrDefault( 1, "not a president" ) );
        System.out.println( "getOrDefault( 4, 'not a president' ): " + presMap.getOrDefault( 4, "not a president" ) );
        // we have a key 1, so this returns "George Washington" (value of 1) and does not update map
        System.out.println( "\nAbout to call putIfAbsent( 1, 'James Madison' ): " + presMap.putIfAbsent( 1, "James Madison" ) );
        presMap.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        // we do not have a key 4, so this adds the key/value to the map and returns null
        System.out.println( "About to call putIfAbsent( 4, 'James Madison' ): " + presMap.putIfAbsent( 4, "James Madison" ) );

        presMap.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        // put in an incorrect value
        presMap.putIfAbsent( 5, "James Munro" );
        System.out.println( "Map with incorrect value: " );
        presMap.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        System.out.println( "Calling presMap.replace( 6, \"John Q Adams\" ): " + presMap.replace( 6, "John Q Adams" ) );
        System.out.println( "Calling presMap.replace( 5, \"James Monroe\" ): " + presMap.replace( 5, "James Monroe" ) );
        System.out.println( "Map with correct value: " );
        presMap.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        Map< Integer, String > presMap2 = new LinkedHashMap< Integer, String >( presMap );
        System.out.println( "\npresMap2 before replaceAll: " );
        presMap2.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        /*
        Since we have one line, we COULD do it this way:
        presMap2.replaceAll( ( key, pres ) ->
            pres.toUpperCase()
        );
        See below if we have multiple lines inside a pair of braces, a return statement, and a colon after closing paren
        */
        presMap2.replaceAll( ( key, pres ) -> {
            return pres.toUpperCase();
        } );

        System.out.println( "presMap2 after replaceAll: " );
        presMap2.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
        System.out.println( "presMap after replaceAll: " );
        presMap.forEach( ( num, pres ) -> System.out.println( "Pres number " + num + ": " + pres ) );
    } // workWithMaps

    public void sleep( int numSeconds ) {
        try {
            Thread.sleep( 1000 * numSeconds );
        } catch ( InterruptedException e ) {
            e.printStackTrace();
        }
    } // sleep

    // from https://www.baeldung.com/java-random-string
    public String generateRandomAlphanumericString() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 10;
        Random random = new Random();

        String generatedString = random.ints( leftLimit, rightLimit + 1 )
                .filter( i -> ( i <= 57 || i >= 65 ) && ( i <= 90 || i >= 97 ) )
                .limit( targetStringLength )
                .collect( StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append )
                .toString();
        System.out.println( "\nIn generateRandomAlphanumericString, about to return: " + generatedString );
        return generatedString;
    } // generateRandomAlphanumericString

    public void computeWithMaps() {
        System.out.println("-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
        // compute: args: key (which may not be in map), and value to associate w/key, may be null
        Map< Integer, String > numAndVals = new LinkedHashMap< Integer, String>();
        System.out.println( "Calling compute w/key 1: " +
            numAndVals.compute( 1, ( ( key, val ) -> { return this.generateRandomAlphanumericString(); } ) )  );
        this.sleep(2 );
        numAndVals.forEach( ( num, val ) -> System.out.print( "Number: " + num + ", val: " + val + ", " ) );

        System.out.println( "\nCalling compute w/key 1 again: " +
            numAndVals.compute( 1, ( ( key, val ) -> this.generateRandomAlphanumericString() ) ) );
        this.sleep(2 );
        numAndVals.forEach( ( num, val ) -> System.out.print( "Number: " + num + ", val: " + val + ", " ) );

        System.out.println( "\nCalling computeIfAbsent w/key 1 again: " +
                numAndVals.computeIfAbsent( 1, ( key ->  this.generateRandomAlphanumericString() ) ) );
        System.out.println( "Calling computeIfAbsent w/key 2: " +
                numAndVals.computeIfAbsent( 2, ( key ->  this.generateRandomAlphanumericString() ) ) );
        this.sleep(2 );
        numAndVals.forEach( ( num, val ) -> System.out.print( "Number: " + num + ", val: " + val + ", " ) );

        System.out.println( "\nCalling computeIfPresent w/key 2: " +
                numAndVals.computeIfPresent( 2, ( ( key, val ) ->  this.generateRandomAlphanumericString() ) ) );
        System.out.println( "Calling computeIfPresent w/key 3: " +
                numAndVals.computeIfPresent( 3, ( ( key, val ) ->  this.generateRandomAlphanumericString() ) ) );
        this.sleep(2 );
        numAndVals.forEach( ( num, val ) -> System.out.print( "Number: " + num + ", val: " + val + ", " ) );
        System.out.println();
    } // computeWithMaps


    public static void main( String args[] ) {

        LambdaCollectionProcessor  lcp = new LambdaCollectionProcessor ();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithCollections" :
                lcp.workWithCollections();
                break;
            case "workWithMaps" :
                lcp.workWithMaps();
                break;
            case "computeWithMaps":
                lcp.computeWithMaps();
                break;

            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
} // LambdaCollectionProcessor
