package info.shelfunit.lambdas;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.UUID;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class StringAndOtherRunner {
    private static final String className = "StringAndOtherRunner.";

    public void workWithStrings() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        String s = "Hello world!";
        IntStream stream = s.chars();
        System.out.println( "Map string '" + s + "' to upper case w/streams: " );
        stream.mapToObj( letter -> ( char ) letter )
                .map( Character::toUpperCase )
                .forEach( c -> System.out.print( " next char: " + c + " " ) );
        // concatenation w/ plus sign is not efficient, multiple strings created/destroyed
        // StringBuffer is StringBetter!
        // StringBuffer is synchronized
        // StringBuilder is StringEvenBetter
        StringBuilder newBuilder = new StringBuilder();
        newBuilder.append( "hello" );
        newBuilder.append( " " ).append( "everybody" ); // appends can be chained
        System.out.println( "\nStringBuilder: " + newBuilder );
        // JDK 7 and higher concatenations are compiled to StringBuilder
        // StringJoiner joins strings with a delimiter
        StringJoiner sj = new StringJoiner( ", " );
        sj.add( "Washington" ).add( "Adams" ).add( "Jefferson" ).add( "Madison" );
        System.out.println( "Here is our joined string (using chained 'add' calls): " + sj );
        // somewhere a Groovy hipster is saying "Dude, we could do that years ago"
        // StringJoiner can also have a prefix and a postfix
        StringJoiner sj2 = new StringJoiner( ", ", "{", "}" );
        sj2.add( "Monroe" ).add( "Jackson" ).add( "VanBuren" );
        System.out.println( "StringJoiner with pre- and suffix: " + sj2 );
        // also handles sets of 1
        StringJoiner sj3 = new StringJoiner( ", ", "{", "}" );
        sj3.add( "Cleveland" );
        System.out.println( "Set of presidents who had non-consecutive terms: " + sj3 );
    } // workLiveCodingExercise

    public void workWithIO( String filePath ) {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // Java 7 try with resources
        // enclose stuff in parens after try, still need semicolon for all that (if the try has more than one line)
        try ( BufferedReader reader = new BufferedReader(
                new FileReader(
                        new File( filePath )
                )
        ); ) {
            System.out.println( "Seeing if string 23 is in the file: " );
            Stream< String > stream = reader.lines(); // lines is a new method
            stream.filter( line -> line.contains( "23" ) )
                    .findFirst()
                    .ifPresent( System.out::println );
        } catch ( FileNotFoundException fnf ) {
            fnf.printStackTrace();;
        } catch ( IOException ioEx ) {
            ioEx.printStackTrace();
        }
        // Java 7 also has Path
        // gotta hard code for now
        Path path = Paths.get( "C:", "EKM", "gradle.projects", "lambdas", "persons.list.txt" );
        System.out.println( "Getting the file with Path which you get with varargs" );
        System.out.println( "Files.lines will create a Stream and Stream will close the underlying file" );
        try( Stream< String > stream2 = Files.lines( path ) ) {
            stream2.filter( line -> line.contains( "23" ) )
                    .findFirst()
                    .ifPresent( System.out::println );
        } catch ( IOException ioEx ) {
            ioEx.printStackTrace();
        }

    } // workWithIO

    public void workWithDirectories() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // Java 7 try with resources and Paths
        Path path = Paths.get( "C:", "EKM" );
        try( Stream< Path > pathStream = Files.list( path ) ) {
            pathStream.filter( thePath -> thePath.toFile().isDirectory() )
                    .forEach( dir -> System.out.println( "Here is the dir: " + dir ) );
        } catch ( IOException ioEx ) {
            ioEx.printStackTrace();
        }
        // Files.walk will go to the subdirs
        // there go a thousand textbook exercises
        System.out.println( "Now with Files.walk" );
        Path anotherPath = Paths.get( "C:", "EKM", "gradle.projects", "lambdas" );
        try( Stream< Path > pathStream = Files.walk( anotherPath ) ) {
            pathStream.filter( thePath -> thePath.toFile().isDirectory() )
                    .forEach( dir -> System.out.println( "Here is the dir: " + dir ) );
        } catch ( IOException ioEx ) {
            ioEx.printStackTrace();
        }
        // limit depth -> pass an integer
        System.out.println( "Go into the big one again with walk, but limit the depth" );
        try( Stream< Path > pathStream = Files.walk( path, 2 ) ) {
            pathStream.filter( thePath -> thePath.toFile().isDirectory() )
                    .forEach( dir -> System.out.println( "Here is the dir: " + dir ) );
        } catch ( IOException ioEx ) {
            ioEx.printStackTrace();
        }
    } // workWithDirectories

    public void workWithCollections() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        List< String > strings = Arrays.asList( "one", "two", "three" );
        strings.forEach( s -> System.out.print( s + " " ) );
        System.out.println( "\nokay, we could have used joiner" );
        // removeIf
        // Collection< String > strings02 = Arrays.asList( "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" );
        ArrayList< String > strings02 = new ArrayList< String >( Arrays.asList( "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" ) );
        System.out.println( "strings02 before: " + strings02.stream().collect( Collectors.joining( ", " ) ) );
        boolean b = strings02.removeIf( s -> s.length() > 4 ); // inline Predicate
        System.out.println( "Removing if length greater than 4 with Collection.removeIf()" );
        System.out.println( strings02.stream().collect( Collectors.joining( ", " ) ) );

        // replaceAll on List
        ArrayList< String > strings03 = new ArrayList< String >( Arrays.asList( "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" ) );
        System.out.println( "strings03 before calling List.replaceAll(): " + strings03.stream().collect( Collectors.joining( ", " ) ) );
        strings03.replaceAll( s -> s.toUpperCase() );
        System.out.println( "strings03 after calling List.replaceAll(): " + strings03.stream().collect( Collectors.joining( ", " ) ) );
        // so these are like map (replaceAll) and filter (removeIf) but you modify the original list instead of returning a new one

        // now let's sort them wiht new 'sort' method
        ArrayList< String > strings04 = new ArrayList< String >( Arrays.asList( "one", "two", "three", "four", "five", "six", "seven", "eight", "nine", "ten" ) );
        System.out.println( "strings04 before calling List.sort: " + strings04.stream().collect( Collectors.joining( ", " ) ) );
        strings04.sort( Comparator.naturalOrder() ); // who defines natural order?
        System.out.println( "here is strings04 sorted by natural order: " + strings04.stream().collect( Collectors.joining( ", " ) ) );
        // what is a Spliterator?

    } // workWithCollections

    public void workWithComparators() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        List< Person > personList = Arrays.asList(
                new Person( 5, "Washington" ),
                new Person( 10, "Adams" ),
                new Person( 15, "Jefferson" ),
                new Person( 20, "Madison" ),
                new Person( 25, "Monroe" ),
                new Person( 30, "Jackson" ),
                new Person( 35, "Van Buren" )
        );
        // using a JDK 7 Comparator anonymous class - won't work if either person is null
        Comparator< Person > compareLastName = new Comparator< Person >() {
            @Override
            public int compare( Person cp1, Person cp2 ) {
                return cp1.getLastName().compareTo( cp2.getLastName() );
            }
            // it looks like you cannot call these. I suppose you could make Person implement Comparator,
            // but I will wait and see what they do in the tutorial
            // I prefer a boolean telling me if something is greater than/less than or before/after
            // A number does not mean much.
            // What if you want to compare on one field, and then another, and then another? Chainging can get nasty
            public boolean isAfter( Person cp1, Person cp2 ) {
                if ( compare( cp1, cp2 ) > 0 ) {
                    return true;
                } else {
                    return false;
                }
            }

            public boolean isBefore( Person cp1, Person cp2 ) {
                if ( compare( cp1, cp2 ) > 0 ) {
                    return false;
                } else {
                    return true;
                }
            }
        };
        System.out.println( "JDK 7 Comparing Jefferson (" + personList.get( 2 ) + ") to Washington (" + personList.get( 0 ) +"): " +
                compareLastName.compare( personList.get( 2 ), personList.get( 0 ) ) );
        // it would be nice to be able to add convenience methods like that.
        // System.out.println( "Is Jefferson before Washington? :" + compareLastName.isAfter( personList.get( 2 ), personList.get( 0 ) ) );
        System.out.println( "JDK 7 Comparing Jefferson (" + personList.get( 2 ) + ") to Adams (" + personList.get( 1 ) + "): " +
                compareLastName.compare( personList.get( 2 ), personList.get( 1 ) ) );

        // new JDK 8
        Comparator< Person > compareLastNameWithJDK8 = Comparator.comparing( p -> p.getLastName() );
        System.out.println( "JDK 8 Comparing Jefferson (" + personList.get( 2 ) + ") to Washington (" + personList.get( 0 ) +"): " +
                compareLastNameWithJDK8.compare( personList.get( 2 ), personList.get( 0 ) ) );
        // it would be nice to be able to add convenience methods like that.
        // System.out.println( "Is Jefferson before Washington? :" + compareLastName.isAfter( personList.get( 2 ), personList.get( 0 ) ) );
        System.out.println( "JDK 8 Comparing Jefferson (" + personList.get( 2 ) + ") to Adams (" + personList.get( 1 ) + "): " +
                compareLastNameWithJDK8.compare( personList.get( 2 ), personList.get( 1 ) ) );
        // you can chain them with "thenComparing", like comparing by last name with Comparator.comparing
        // and then chaining to Comparator.thenComparing
        // I would prefer to implement Comparator
        List< Person > sortedPersonList = new ArrayList< Person >( personList );
        // Collections.copy( sortedPersonList, personList );
        System.out.print( "sortedPersonList before sorting: " );
        sortedPersonList.stream().forEach( p -> System.out.print( p.getLastName() + " " ) );

        StringJoiner sj = new StringJoiner( ", " );
        sortedPersonList.stream().forEach( p -> sj.add( p.getLastName() ) );
        System.out.println( "\nLet's try that again with StringJoiner: " + sj );
        // sort with the comparator
        sortedPersonList.sort( compareLastNameWithJDK8 );
        StringJoiner sjSort = new StringJoiner( ", " );
        sortedPersonList.stream().forEach( p -> sjSort.add( p.getLastName() ) );
        System.out.println( "sortedPersonList after sorting: " + sjSort );

        System.out.println( "Was personList sorted? "  );
        personList.stream().forEach( p -> System.out.print( p.getLastName() + " " ) );
        ArrayList< Person > revPersonList = new ArrayList< Person >();
        revPersonList.addAll( personList );
        StringJoiner rev01 = new StringJoiner( ", " );
        revPersonList.stream().forEach( p -> rev01.add( p.getLastName() ) );
        System.out.println( "\nrevPersonList before reverse sorting: " + rev01 );
        Comparator< Person > reverseComp = compareLastNameWithJDK8.reversed();
        revPersonList.sort( reverseComp );
        StringJoiner rev02 = new StringJoiner( ", " );
        revPersonList.stream().forEach( p -> rev02.add( p.getLastName() ) );
        System.out.println( "revPersonList after sorting: " + rev02 );

        // Comparator.nullsFirst will put the nulls in the front of the list, and nullsLast puts them at the end
        // numbers:
        // primitive types: byte, short, char, int, long, double, float, boolean, all have wrappers

    } // workWithComparators

    public void compareNumbers() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        long aLong = 3L;
        long bLong = 4L;
        System.out.println( "New methods for nums using long as example with longs " + 3L + " and " + 4L +
                ": Long.max: " + Long.max( 3L, 4L ) + ", Long.min: "  + Long.min( 3L, 4L ) );
        System.out.println( "New methods for nums using long as example with longs " + 3L + " and " + 4L +
                " and args reversed: Long.max: " + Long.max( 4L, 3L ) + ", Long.min: "  + Long.min( 4L, 3L ) );
        System.out.println( "New method sum is just addition of two longs: " + Long.sum( 3L, 4L ) );
        List< Long > longListA = Arrays.asList( 1L, 5L, 10L, 15L, 20L, 25L, 30L, 35L, 40L );
        Stream< Long > longStreamA = longListA.stream();
        Long longStreamSum = longStreamA.reduce( 0L, ( long1, long2 ) -> ( Long.sum( long1, long2 ) ) );
        System.out.println( "A whole list of longs? Just use reduce: " + longListA + " totals to: " + longStreamSum );
        BinaryOperator< Long > sumOp = ( l1, l2 ) -> l1 + l2;
        // BinaryOperator< Long > sumOp = Long::sum;
        Stream< Long > longStreamB = longListA.stream();
        // Long longStreamSumB = longStreamB.reduce( 0L, ( long1, long2 ) -> ( sumOp ) );
        Long longStreamSumB = longStreamB.reduce( 0L, sumOp );
        System.out.println( "Sum using reduce and a BinaryOperator: " + longStreamSumB );
        // hash code of int is the int itself
        // longs can get hash from java.lang.Long
        System.out.println( "Hash of 3L: " + Long.hashCode( 3L ) );

    } // compareNumbers

    public void compareMaps() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // now Map has forEach method - takes a java.util.function.BiConsumer as parameter
        Map< Integer, Person > integerPersonMap = new LinkedHashMap< Integer, Person >();
        integerPersonMap.put( 1, new Person( 5, "Washington" ) );
        integerPersonMap.put( 2, new Person( 10, "Adams" ) );
        integerPersonMap.put( 3, new Person( 15, "Jefferson" ) );
        integerPersonMap.put( 4, new Person( 20, "Madison" ) );
        integerPersonMap.put( 5, new Person( 25, "Monroe" ) );
        integerPersonMap.put( 6, new Person( 30, "Jackson" ) );
        integerPersonMap.put( 7, new Person( 35, "Van Buren" ) );
        integerPersonMap.forEach( ( num, pers ) -> System.out.println( "Here is pers " + num + ": " + pers.toString() ) );
        // now we have getOrDefault to handle nulls
        Person defaultPerson = new Person( 0, "DEFAULT" );
        System.out.println( "Map has 7 values: here is getOrDefault for 7: " + integerPersonMap.getOrDefault( 7, defaultPerson ) );
        System.out.println( "Map has 7 values: here is getOrDefault for 10: " + integerPersonMap.getOrDefault( 10, defaultPerson ) );
        // putIfAbsent only puts if the key is not there
        integerPersonMap.putIfAbsent( 8, new Person( 20, "Tyler" )  );
        System.out.println( "integerPersonMap after calling putIfAbsent for 8, Tyler: " + integerPersonMap.getOrDefault( 8, defaultPerson ) );
        integerPersonMap.putIfAbsent( 1, new Person( 14, "Polk" ) );
        System.out.println( "integerPersonMap after calling putIfAbsent for 1, Polk: " + integerPersonMap.getOrDefault( 1, defaultPerson ) );
        integerPersonMap.put( 1, new Person( 14, "Polk" ) );
        System.out.println( "integerPersonMap after calling regular put for 1, Polk: " + integerPersonMap.getOrDefault( 1, defaultPerson ) );
        // replace Polk with Washington
        integerPersonMap.replace( 1, new Person( 5, "Washington" ) );
        System.out.println( "integerPersonMap.get( 1 ) after replacing 1 with Washington: " + integerPersonMap.getOrDefault( 1, defaultPerson ) );
        // replaceAll, with multi-line lambda
        integerPersonMap.replaceAll( ( key, pers ) -> {
            UUID uuid = UUID.randomUUID();
            pers = new Person( pers.getAge(), pers.getLastName() + "__" + uuid.toString() );
            return pers;
        } );
        integerPersonMap.forEach( ( num, pers ) -> System.out.println( "Here is new robot president pers " + num + ": " + pers.toString() ) );
        integerPersonMap.remove( 8 );
        integerPersonMap.remove( 100 );
        System.out.println( "integerPersonMap after calling remove(8) (which is a key that was in our map) and remove(10) (which was not in the map): " );
        integerPersonMap.forEach( ( num, pers ) -> System.out.println( "Here is new robot president pers " + num + ": " + pers.toString() ) );
        // we can also call another version of remove that provides a value to ensure we are removing what we want
        integerPersonMap.remove( 1, integerPersonMap.get( 2 ) );
        System.out.println( "integerPersonMap after calling remove( 1, integerPersonMap.get( 2 ) ): " );
        integerPersonMap.forEach( ( num, pers ) -> System.out.println( "Here is new robot president pers " + num + ": " + pers.toString() ) );
        integerPersonMap.compute( 10, ( key, val )  -> {
            val = new Person( 10, "Hello" );
            return val;
        } );
        System.out.println( "integerPersonMap.get(10) after calling compute: " + integerPersonMap.get( 10 ) );
        integerPersonMap.compute( 6, ( key, val )  -> {
            val = new Person( 6, "Overwriting 6" );
            return val;
        } );
        System.out.println( "integerPersonMap.get(6) after calling compute: " + integerPersonMap.get( 6 ) );

        // if you give null to compute, it will take the value out of the map
        System.out.println( "integerPersonMap.getORDefault(3) before calling compute w/null: " + integerPersonMap.getOrDefault( 3, defaultPerson ) );
        integerPersonMap.compute( 3, ( key, val )  -> null );
        System.out.println( "integerPersonMap.getORDefault(3) after calling compute w/null: " + integerPersonMap.getOrDefault( 3, defaultPerson ) );

        integerPersonMap.computeIfPresent( 11, ( key, val )  -> {
            val = new Person( 11, "Eleven" );
            return val;
        } );
        // how about a method to set the default?
        System.out.println( "integerPersonMap.getOrDefault( 11, defaultPerson ) after calling computeIfPresent with 11: " +
                integerPersonMap.getOrDefault( 11, defaultPerson ) );
        integerPersonMap.computeIfAbsent( 11, key  -> {
            Person val = new Person( 11, "Eleven" );
            return val;
        } );
        // how about a method to set the default? that would be great
        System.out.println( "integerPersonMap.getOrDefault( 11, defaultPerson ) after calling computeIfAbsent with 11: " +
                integerPersonMap.getOrDefault( 11, defaultPerson ) );
        // compute functions good for making bimaps (maps whose values are maps)
        // note: Usually "bimap" refers to a bidirectional map: using keys to find values OR values to find keys
        // new method: merge - sounds like compute
        Map< String, List< String > > partyPresMapA = new LinkedHashMap< String, List< String > >();
        Map< String, List< String > > partyPresMapB = new LinkedHashMap< String, List< String > >();
        partyPresMapA.put( "Federalist", Arrays.asList( "John Adams" ) );
        partyPresMapB.put( "National Union", Arrays.asList( "Andrew Johnson" ) );
        partyPresMapA.put( "Whig", new ArrayList< String > ( Arrays.asList( "Millard Fillmore", "William Henry Harrison" ) ) );
        partyPresMapB.put( "Whig", new ArrayList< String > ( Arrays.asList( "Zachary Taylor", "John Tyler" ) ) );
        partyPresMapA.put( "Republican", new ArrayList< String > (
                Arrays.asList( "Chester A. Arthur", "Calvin Coolidge", "Dwight D. Eisenhower", "Gerald Ford" ) )
        );
        partyPresMapB.put( "Republican", new ArrayList< String > (
                Arrays.asList( "Rutherford B. Hayes", "Herbert Hoover", "Abraham Lincoln", "William McKinley", "Theodore Roosevelt", "William Howard Taft" ) )
        );
        partyPresMapB.put( "Democrat", new ArrayList< String > (
                Arrays.asList( "Jimmy Carter", "Grover Cleveland", "Bill Clinton", "Andrew Jackson", "Lyndon B. Johnson", "John F. Kennedy", "Barack Obama" ) )
        );
        partyPresMapA.put( "Democrat", new ArrayList< String > (
                Arrays.asList( "James K. Polk", "Franklin D. Roosevelt", "Harry S. Truman", "Woodrow Wilson" ) )
        );
        System.out.println( "\npartyPresMapA: " + partyPresMapA );
        System.out.println( "partyPresMapB: " + partyPresMapB );
        System.out.println( "Performing merge" );
        partyPresMapB.entrySet().stream().forEach( entry ->
                partyPresMapA.merge( entry.getKey(), entry.getValue(), ( list1, list2 ) -> {
                    System.out.println( "Key is " + entry.getKey() );
                    System.out.println( "list1 is: " + list1 );
                    System.out.println( "list2 is: " + list2 );
                    list1.addAll( list2 );
                    return list1;
                } )
        );
        System.out.println( "\npartyPresMapA: " + partyPresMapA );
        System.out.println( "partyPresMapB: " + partyPresMapB );
    } // compareMaps

    /*
    Java 8 allows multiple annotations: same annotation more than once
    might work for test cases
     */

    public static void main( String args[] ) {
        StringAndOtherRunner sr = new StringAndOtherRunner();

        String methodToRun = args[ 0 ];

        switch( methodToRun ) {
            case "workWithStrings":
                sr.workWithStrings();
                break;
            case "workWithIO":
                if ( null != args[ 1 ]  ) {
                    sr.workWithIO(args[1]);
                }
                break;
            case "workWithDirectories":
                sr.workWithDirectories();
                break;
            case "workWithCollections":
                sr.workWithCollections();
                break;
            case "workWithComparators":
                sr.workWithComparators();
                break;
            case "compareNumbers":
                sr.compareNumbers();
                break;
            case "compareMaps":
                sr.compareMaps();
                break;
                 /*
            case "workWithMoreReducersAndCollections":
                sr.workWithMoreReducersAndCollections();
                break;
            case "workLiveCodingExercise":
                sr.workLiveCodingExercise( args[ 1 ] );
                break;


         */
            default:
                System.out.println( "No method named " + methodToRun );
        } // switch

    } // end main
} // info.shelfunit.lambdas.StringAndOtherRunner
