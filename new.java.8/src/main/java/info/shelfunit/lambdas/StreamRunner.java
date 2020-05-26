package info.shelfunit.lambdas;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamRunner {
    private static final String className = "StreamRunner.";

    List< Person > personList = Arrays.asList(
            new Person( 5, "Washington" ),
            new Person( 10, "Adams" ),
            new Person( 15, "Jefferson" ),
            new Person( 20, "Madison" ),
            new Person( 25, "Monroe" ),
            new Person( 30, "Jackson" ),
            new Person( 35, "Van Buren" )
    );
    List< Integer > integerList = Arrays.asList( 0, 5, 10, 15, 20, 25, 30, 35, 40 );

    public void workWithConsumers() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        List< Integer > ageList   = new ArrayList< Integer >();
        // "map" algorithm - takes a list and returns another list based on input list
        // "filter" - takes a list and filters out elements that do not meet a criteria (or filter in those that do)
        // "reduce" - takes a list and returns one value based on all values in the list, like SQL aggregation
        // taking an average is not a reduction, it's two operations
        // streams are typed interfaces
        // not a Java collection
        // streams are a way to process large amounts of data, including in parallel, or even small amounts of data
        // new API so JDK authors would not have to change collections API
        // Stream: Object upon which you can define operations (map, filter, reduce)
        // Streams do not hold data, should not change the data it processes (but input can be different)
        // Streams can process data in one pass
        Stream< Person > stream = personList.stream();
        System.out.println( "About to print personList as a stream" );
        stream.forEach( p -> System.out.println( p ) );

        // you can chain Consumers with andThen
        List< String > strings = Arrays.asList( "001", "002", "003", "004", "005" );
        Consumer< String > c1 = s -> strings.add( s );
        Consumer< String > c2 = s -> System.out.println( "Here is element: " + s );
        Consumer< String > c3 = c1.andThen( c2 );

    } // workWithMapping

    public void workWithFilters() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // filter takes predicate
        // filter people with age over 20
        // there is a Predicate class, but you can define predicate inline
        // filter method returns a Stream
        Stream< Person > stream = personList.stream();
        Stream< Person > ageOver20 = stream.filter( person -> ( person.getAge() > 20 ) );
        System.out.println( "Printing people with age over 20" );
        ageOver20.forEach( p -> System.out.println( "Here is person: " + p.toString() ) );
        Predicate< Integer > p1 = i -> i > 20;
        Predicate< Integer > p2 = i -> i < 30;
        Predicate< Integer > p3 = i -> i == 0;
        Predicate< Integer > p4 = p1.and( p2 ).or( p3 ); // ( p1 AND p2 ) OR p3
        Predicate< Integer > p5 = p3.or( p1 ).and( p2 ); // ( p3 OR p1 ) AND p2
        Stream< Integer > integerStream = integerList.stream();
        Stream< Integer > p4Stream = integerStream.filter( p4 );
        // Stream< Integer > p5Stream = integerStream.filter( p5 ); // caused exception - integerStream was closed
        // so a Stream can only be used once
        Stream< Integer > p5Stream = integerList.stream().filter( p5 ); // works
        integerList.forEach( i -> System.out.print( "Next int: " + i + ", " ) );
        System.out.print( "\np1: Greater than 20: " );
        integerList.stream().filter( p1 ).forEach( q -> System.out.print( q + ", " ) );
        System.out.print( "\np1: Less than 30: " );
        integerList.stream().filter( p2 ).forEach( q -> System.out.print( q + ", " ) );
        System.out.print( "\np4 stream (greater than 20 AND less than 30) or 0: " );
        p4Stream.forEach( i -> System.out.print( i + ", " ) );
        System.out.print( "\np5 stream ( 0 or under 30 ) and greater than 20: " );
        p5Stream.forEach( i -> System.out.print( i + ", " ) );
        System.out.println( "" );

        Predicate< String > pTwo = Predicate.isEqual( "two" );
        Stream< String > inPlaceStream = Stream.of( "one", "two", "three" ); // pretty slick
        Stream< String > filteredInPlace = inPlaceStream.filter( pTwo );
        System.out.print( "Here is filteredInPlace with pTwo: "  );
        filteredInPlace.forEach( q -> System.out.print( q + " " ) );
        Stream< String > inPlaceNumbers = Stream.of( "one", "two", "three", "four", "five" ); // pretty slick
        System.out.print( "\ninPlaceNumbers: " );
        inPlaceNumbers.forEach( i -> System.out.print( i + " " ) );
        Predicate< String > p6 = s -> s.length() > 3;
        System.out.print( "\nNow with just those with length greater than three: " );
        Stream.of( "one", "two", "three", "four", "five" ).filter( p6 ).forEach( q -> System.out.print( q + " " ) );

        // again, but with another predicate
        Predicate< String > pTwoAgain = Predicate.isEqual( "two" );
        Predicate< String > pThree = Predicate.isEqual( "three" );
        System.out.println( "\nAgain, with pTwoAgain and pThree predicates: " );
        Stream.of( "one", "two", "three", "four", "five" ).filter( pTwoAgain.or( pThree ) ).forEach( q -> System.out.print( q + " " ) );

        System.out.println( "\nLooking at Person stream with peek " );
        List< String > result = new ArrayList< String >();

        personList.stream().peek( p -> System.out.print( "Before: " + p + " " ) )
                .filter( p -> p.getAge() > 20 )
                .peek( p -> System.out.print( "After: "  + p + " " ) )
                .forEach( p -> result.add( p.toString() ) ); // did not work with this as a call to peek
        ; // look up page on intermediary operations
        System.out.print( "\nHere is result: " );
        result.forEach( r -> System.out.print( r + " " ) );
        System.out.print( "\nfrom the javadoc: " );
        Stream.of( "one", "two", "three", "four" )
                .filter( e -> e.length() > 3 )
                .peek( e -> System.out.print( "Filtered value: " + e + " " ) )
                .map( String::toUpperCase )
                .peek( e -> System.out.print( "Mapped value: " + e + " ") )
                .collect( Collectors.toList() );

        Stream< String > anotherStream = Stream.of( "one", "two", "three", "four", "five" );
        List< String > anotherList = new ArrayList< String >();
        anotherStream.peek( p -> System.out.print( p + " " ) )
                .filter( pTwoAgain.or( pThree ) )
                .forEach( p -> anotherList.add( p ) );
        System.out.print( "\nHere is anotherList after being filtered: " );
        anotherList.forEach( e -> System.out.print( e + " " ) );

        System.out.println( "\nDone with workWithFilters" );
    } // workWithFilters

    public void workWithMaps() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // map as in "map/reduce"
        // map returns a Stream (intermediary operation)
        // map takes a java.util.function.Function, which has compose and andThen
        // Function also has identity, which I never quite understood
        // also: flatMap - map takes object and returns object, flatMap takes object and returns a Stream of objects
        // It is a stream of streams, flattened and becomes a single stream
        List< Integer > list1 = Arrays.asList( 1, 2, 3, 4, 5, 6, 7 );
        List< Integer > list2 = Arrays.asList( 2, 4, 6 );
        List< Integer > list3 = Arrays.asList( 3, 5, 7 );
        List< List < Integer > > listOfLists = Arrays.asList( list1, list2, list3 );
        System.out.println( "Here is the listOfLists: " + listOfLists );
        listOfLists.stream().map( l -> l.size() )
            .forEach( l -> System.out.print( "Size of element: " + l + ", " ) );
        System.out.print( "\nDoing that again with a Function" );
        Function< List< ? >, Integer > size = List::size; // it looks like you have to use the shorthand aka method reference
        Function< List< ? >, Integer > size2 = list -> list.size(); // I like this better
        listOfLists.stream().map( size2 )
                .forEach( l -> System.out.print( "Size of element w/size2: " + l + ", " ) );
        // for flatMap
        System.out.println( "\nusing a function for flatMap: " );
        Function< List< Integer >, Stream< Integer > > flatMapper = l -> l.stream();
        listOfLists.stream()
                .flatMap( flatMapper )
                .forEach( l -> System.out.print( l + ", " ) );
        System.out.println( "\n" );
    } // workWithMaps

    public void workWithReduce() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // 2 kinds of reduction:
        // aggregation (min, max, sum, etc)
        // mutable reduction
        Stream< Integer > integerStream = integerList.stream();
        // first arg is identity of accumulator function, second arg is the function
        // I put the second arg in parens in this example. Everything after the comma is the second arg
        Integer sum = integerStream.reduce( 0, ( age1, age2 ) -> ( age1 + age2 ) );
        System.out.println( "integerList: " + integerList );
        System.out.println( "Sum using reduce: " + sum );
        // reduction of empty stream is identity element
        // one element: stream is that element with identity element
        Stream< Integer > intStream = Stream.of( 1 ); // Arrays.asList( 2 ).stream();
        BinaryOperator< Integer > sumOp = ( i1, i2 ) -> ( i1 + i2 );
        Integer id = 0; // identity element for the sum
        int red = intStream.reduce( id, sumOp );
        System.out.println( "Result of reduce on a stream with one element: " + red );

        Stream< Integer > emptyStream = Stream.empty();
        int redEmpty = emptyStream.reduce( id, sumOp );
        System.out.println( "Result of reduce on a stream with no elements: " + redEmpty );
        // available reductions: max, min, count, allMatch, noneMatch, anyMatch (aggregation)
        // I guess booleans could be a reduction. I never thought of that.
        // It's like doing a filter, then calling a function on the filtered list
        // reductions that return one value: findFirst, findAny
        // I guess returning an Optional is like reduce, in a way
        // reduce is a terminal operation
    } // workWithReduce

    public void workingWithOptions() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // what about calling reduce to get max?
        Stream< Integer > integerStream = integerList.stream();
        // need to return an Optional - it might not have a result
        Optional< Integer > max = integerStream.max( Comparator.naturalOrder() );
        System.out.println( "Here is max: " + max );
        Stream< Integer > emptyIntStream = Stream.empty();
        Optional< Integer > maxOfEmpty = emptyIntStream.max( Comparator.naturalOrder() );
        System.out.println( "Here is maxOfEmpty: " + maxOfEmpty );
        // Optional.orElse is a way to do the same thing as an if/else block
        System.out.println( "max.orElse( -1000 ): " + max.orElse( -1000 ) );
        System.out.println( "maxOfEmpty.orElse( -1000 ): " + maxOfEmpty.orElse( -1000 ) );
        // You could also do orElseThrow , you can make an exception with a lambda

        Optional< Integer > minAge = personList.stream()
                .map( person -> person.getAge() )
                .filter( age -> age > 20 )
                .min(Comparator.naturalOrder() ); // terminal operation that triggers processing
        System.out.println( "minAge: " + minAge );
        Integer red = integerList.stream()
                .reduce( 0, ( ( i1, i2 ) -> ( i1 + 12 ) ) );
        System.out.println( "Here is integerList: " +  integerList + ", with red: " + red );
        Integer redWith10 = integerList.stream()
                .reduce( 10, ( ( i1, i2 ) -> ( i1 + 12 ) ) );
        System.out.println( "Here is integerList: " +  integerList + ", with redWith10: " + redWith10 );
        // maybe you want to start somewhere other than 0

    } // workingWithOptions

    public void workWithMoreReducersAndCollections() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        String result = personList.stream()
                .filter( p -> p.getAge() > 20 )
                .map( p -> p.getLastName() )
                .collect( Collectors.joining( ", " ) );
        System.out.println( "Here is result using joining: " + result );
        List < String > resultList = personList.stream()
                .filter( p -> p.getAge() > 20 )
                .map( Person::getLastName )
                .collect( Collectors.toList() );
        System.out.println( "Here is resultList: " + resultList );
        // collect in a Map
        Map< Integer, List< Person > > resultMap = personList.stream()
                .filter( p -> p.getAge() > 20 )
                .collect( Collectors.groupingBy( p -> p.getAge() ) );
        System.out.println( "Here is resultMap: " + resultMap );

        // downstream collector
        Map< Integer, Long > resultDSMap = personList.stream()
                .filter( p -> p.getAge() > 20 )
                .collect( Collectors.groupingBy( Person::getAge, Collectors.counting() ) );
        System.out.println( "Here is resultDSMap: " + resultDSMap );
    } // workWithMoreReducersAndCollections

    public void workLiveCodingExercise( String filePath ) {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        System.out.println( "filePath: " + filePath );
        List< Person > filePersonList = new ArrayList< Person >();
        try ( BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        new FileInputStream( filePath ) ) );
             Stream< String > theStream = reader.lines();
        ) {
            theStream.map( line ->{
                String[] s = line.split( " " );
                Person p = new Person( s[ 0 ].trim(), Integer.parseInt( s[ 1 ] ) );
                filePersonList.add( p );
                return p;
            } )
                    .forEach(p -> System.out.println( p.toString() + " " ) );

        } catch ( IOException ioEx ) {
            ioEx.printStackTrace();
        }
        Stream< Person > fplStream = filePersonList.stream();
        Optional< Person > ageOpt  =  fplStream.filter( p -> p.getAge() >= 20 )
                .min( Comparator.comparing( Person::getAge ) );
        System.out.println( "youngest from ageOpt: " + ageOpt );
        // get oldest person

        Stream< Person > fplStream2 = filePersonList.stream();
        Optional< Person > ageOpt2  =  fplStream2.filter( p -> p.getAge() >= 20 )
                .max( Comparator.comparing( Person::getAge ) );
        System.out.println( "oldest from ageOpt: " + ageOpt2 );

        Stream< Person > fplStream3 = filePersonList.stream();
        Map< Integer, List< Person > > persMap =
        fplStream3.collect( Collectors.groupingBy( Person::getAge ) );
        System.out.println(  "persMap, grouped by age: " + persMap );

        Map< Integer, Long > persLongMap =
                filePersonList.stream().collect( Collectors.groupingBy( Person::getAge, Collectors.counting() ) );
        System.out.println(  "\npersLongMap, grouped by age w/downstream collector: " + persLongMap );

        Map< Integer, List< String > > persMap2 =
                filePersonList.stream().collect(
                        Collectors.groupingBy( Person::getAge,
                                Collectors.mapping( Person::getLastName, Collectors.toList() ) ) );
        System.out.println(  "\npersMap2, another downstream collector: " + persMap2 );
    } // workLiveCodingExercise

    public static void main( String args[] ) {
        StreamRunner sr = new StreamRunner();

        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithConsumers" :
                sr.workWithConsumers();
                break;
            case "workWithFilters":
                sr.workWithFilters();
                break;
            case "workWithMaps":
                sr.workWithMaps();
                break;
            case "workWithReduce":
                sr.workWithReduce();
                break;
            case "workingWithOptions":
                sr.workingWithOptions();
                break;
            case "workWithMoreReducersAndCollections":
                sr.workWithMoreReducersAndCollections();
                break;
            case "workLiveCodingExercise":
                sr.workLiveCodingExercise( args[ 1 ] );
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
} // end class

