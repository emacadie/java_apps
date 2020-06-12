package info.shelfunit.morelambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamProcessor {

    public void startWithStreams() {
        System.out.println("-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
        List< Person> rootPersonList = Arrays.asList(
                new Person( "George", "Washington", 57 ),
                new Person( "James", "Madison", 57 ),
                new Person( "James", "Monroe", 58 ),
                new Person( "Andrew", "Johnson", 56 ),
                new Person( "Lyndon", "Johnson", 55 )
        );
        List< Person > personList = new ArrayList< Person >( rootPersonList );
        System.out.println( "Here is personList:" );
        personList.forEach( p -> System.out.print( p.toString() + ", " ) );
        System.out.print( "\nAges greater than 56: " );
        personList.stream() // makes a Stream of Person objects
                .map( p -> p.getAge() ) // returns a Stream of integers
                .filter( age -> age > 56 ) // returns a Stream of integers
                .forEach( i -> System.out.print( i + ", " ) ); // terminal operation
        // if you want to print out the Person objects w/ages greater than 56, just filter
        System.out.print( "\nPeople with ages greater than 56: " );
        personList.stream() // makes a Stream of Person objects
                .filter( p -> p.getAge() > 56 ) // returns a Stream of integers
                .forEach( i -> System.out.print( i + ", " ) ); // terminal operation
        System.out.println();

        List< Integer > intList = Arrays.asList( 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 );
        System.out.println( "Here is our list of integers:" );
        intList.forEach( i -> System.out.println( "i: " + i + ", is it even?: " + ( ( i % 2 ) == 0 )  ) );
        System.out.println( "\nCalling skip(3), limit(4) and filtering on the evens" );
        intList.stream()
                .skip( 2 )
                .limit( 4 )
                .peek( q -> System.out.println( "Peeking with element " + q ) )
                .filter( x -> ( x % 2 ) == 0  )
                .forEach( i -> System.out.print( "i: " + i + ", " ) );
        System.out.println();
    } // startWithStreams

    public void workWithReduce() {
        System.out.println("-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
        List< Person> rootPersonList = Arrays.asList(
                new Person( "William", "Harrison", 68 ),
                new Person( "John", "Adams", 61 ),
                new Person( "George", "Washington", 57 ),
                new Person( "James", "Madison", 57 ),
                new Person( "James", "Monroe", 58 ),
                new Person( "Andrew", "Johnson", 56 ),
                new Person( "Lyndon", "Johnson", 55 ),
                new Person( "Jimmy", "Carter", 52 ),
                new Person( "Franklin", "Roosevelt", 51 ),
                new Person( "Bill", "Clinton", 46 ),
                new Person( "John", "Kennedy", 43 )
        );
        List< Person > presList = new ArrayList< Person >( rootPersonList );
        System.out.println( "Here is presList:" );
        presList.forEach( p -> System.out.print( p.toString() + ", " ) );
        // allMatch, anyMatch and noneMatch are terminal operations
        // short-circuiting terminal operations since they may not go through the entire stream
        System.out.println( "\nuse allMatch to ask if all presidents were older than 35 when taking office: " +
                presList.stream().allMatch( p -> p.getAge() >= 35 )
        );
        System.out.println( "use noneMatch to ask if any presidents were younger than 35 when taking office: " +
                presList.stream().allMatch( p -> p.getAge() < 35 )
        );
        System.out.println( "use anyMatch to ask if any presidents were older than 60 when taking office: " +
                presList.stream().anyMatch( p -> p.getAge() > 60 )
        );
        // findFirst and findAny
        // return Optional - you can call Optional.orElse() if those return nothing
        Person defaultPerson = new Person( "DEFAULT", "DEFAULT", 0 );
        System.out.println( "Any presidents younger than 50: " );
        Optional< Person > optPers01 = presList.stream().filter( p -> p.getAge() < 50 ).findAny();
        System.out.println( "our findAny returns: " + optPers01.orElse( defaultPerson ) );
        System.out.println( "Any presidents younger than 40: " );
        Optional< Person > optPers02 = presList.stream().filter( p -> p.getAge() <= 40 ).findAny();
        System.out.println( "our findAny returns: " + optPers02 );

        List< Integer > integerList = Arrays.asList( 0, 5, 10, 15, 20, 25, 30, 35, 40 );
        System.out.println( "Our integer list" );
        integerList.forEach( i -> System.out.print( "i: " + i + ", " ) );
        Stream< Integer > integerStream = integerList.stream();
        // first arg is identity of accumulator function, second arg is the function
        // I put the second arg in parens in this example. Everything after the comma is the second arg
        System.out.println();
        Integer sum = integerStream.reduce( 1, ( ( num1, num2 ) -> {
            System.out.println( "Adding " + num1 + " and " + num2 + " to get " + ( num1 + num2 ) );
            return num1 + num2;
        } ) );
        // orig: Integer sum = integerStream.reduce( 0, ( num1, num2 ) -> ( num1 + num2 ) );
        System.out.println( "Here is sum: " + sum );
        // System.out.println( "Count of stream: " + integerStream.count() ); // will throw exception
    } // workWithReduce

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
        return generatedString;
    } // generateRandomAlphanumericString

    public void doMoreWorkWithStreams() {
        System.out.println("-----\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[1].getMethodName());
        List< Integer > integerList = Arrays.asList( 0, 1, 2, 3, 4 );
        // build stream by calling List.stream()
        integerList.stream().forEach( i -> System.out.print( "i is: " + i + ", " ) );
        System.out.println( "\nSame list elements using Stream.of" );
        // build stream w/Stream.of
        Stream< Integer > stream2 = Stream.of(  0, 1, 2, 3, 4 );
        stream2.forEach( i -> System.out.print( "i is: " + i + ", " ) );
        System.out.println( "\nStream generated w/Stream.generate" );
        // build infinite stream with Stream.generate
        // UUID.randomUUID().toString() this.generateRandomAlphanumericString()
        Stream< String > stringStream = Stream.generate( () -> UUID.randomUUID().toString() + " " +
                this.generateRandomAlphanumericString() );
        stringStream.limit( 5 ).forEach( s -> System.out.println( "s: " + s ) );
        System.out.print( "Use Stream.iterate: " ); // also makes an infinite stream, so gotta call limit
        Stream< String > ss02 = Stream.iterate( "+", s -> s );
        ss02.limit( 10 ).forEach( s -> System.out.print( s ) );
        System.out.println(  "\nLet's iterate with numbers: " );
        Stream< Integer > intStream02 = Stream.iterate( 0, ( n -> n + 2 ) );
        intStream02.limit( 10 ).forEach( i -> System.out.print( "i: " + i + ", " ) );
        System.out.println( "\nRandom integers generated by ThreadLocalRandom" );
        // ThreadLocalRandom can return streams of random numbers
        IntStream intStream03 = ThreadLocalRandom.current().ints(); // another unlimited stream
        intStream03.limit( 10 ).forEach( i -> System.out.print( "i: " + i + ", " ) );
        System.out.println();
    } // doMoreWorkWithStreams

    public static void main( String args[] ) {

        StreamProcessor  sp = new StreamProcessor();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "startWithStreams":
                sp.startWithStreams();
                break;
            case "workWithReduce":
                sp.workWithReduce();
                break;
            case "doMoreWorkWithStreams":
                sp.doMoreWorkWithStreams();
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
} // StreamProcessor
