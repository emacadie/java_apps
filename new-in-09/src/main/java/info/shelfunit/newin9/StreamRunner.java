package info.shelfunit.newin9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Stream;

import java.util.stream.Collectors;

public class StreamRunner {

    public void workWithTakeWhile() {
        System.out.println( "In workWithTakeWhile" );
        List< String > stringList = new ArrayList< String >();
        stringList.add( "aaa" );
        stringList.add( "bbb" );
        stringList.add( "ccc" );
        stringList.add( "dddd" );
        stringList.add( "eeee" );
        stringList.add( "fff" );
        stringList.add( "gggg" );
        stringList.forEach( e -> System.out.print( "next element in list: " + e + " " ) );
        System.out.println( "\nNow let's do takeWhile w/predicate of a.length() <= 3" );
        stringList.stream().takeWhile( a -> a.length() <= 3 ).forEach( i -> System.out.print( i + ", " ) );
        System.out.println( "\nNow let's do filter w/same predicate" );
        stringList.stream().filter( a -> a.length() <= 3 ).forEach( i -> System.out.print( i + ", " ) );
        System.out.println( "\nNow let's do dropWhile w/same predicate" );
        stringList.stream().dropWhile( a -> a.length() <= 3 ).forEach( i -> System.out.print( i + ", " ) );
        System.out.println();
    } // workWithTakeWhile

    public void workWithBooks() {
        System.out.println( "In workWithBooks" );
        long zero = Stream.ofNullable( null ).count();
        long one = Stream.ofNullable( Book.getBook() ).count();
        System.out.printf( "zero: %d, one: %d \n", zero, one );

        System.out.println( "Before ofNullable, it was hard to deal w/nulls" );
        Book book = getPossiblyNull(true );
        Stream< String > authors =
                book == null ? Stream.empty() : book.authors.stream();
        authors.forEach( System.out::println );

        System.out.println( "calling getPossiblyNull w/true in Stream.ofNullable" );
        Stream.ofNullable( getPossiblyNull(true ) )
                .flatMap( b -> b.authors.stream() )
                .forEach( System.out::println );

        System.out.println( "With ofNullable, dealing w/nulls is easier" ); // this seems
        Stream.ofNullable( getPossiblyNull(false ) )
                .flatMap( b -> b.authors.stream() )
                .forEach( System.out::println );

    } // workWithBooks

    private static Book getPossiblyNull( boolean isNull ) {
        return isNull ? null : Book.getBook();
    }

    public void findGitConflict( String path ) {
        try {
            System.out.println( "findGitConflict w/path: " + path );
            Files.lines( Paths.get( path ) )
                    .dropWhile( l -> !l.contains( "<<<<<<<" ) )
                    .skip( 1 )
                    .takeWhile( l -> !l.contains( ">>>>>>>" ) )
                    .forEach( l -> System.out.println( l ) );
        } catch ( IOException ioEx ) {
            System.out.println( "IOException in findGitConflict w/path: " + path  );

        }
    } // findGitConflict

    public void workWithCollectors() {
        System.out.println( "workWithCollectors" );
        Map< Integer, List< Integer > > ints = Stream.of( 1, 2, 3, 3 )
                .collect( Collectors.groupingBy( i -> i % 2,  Collectors.toList() ) );
        System.out.println( "Here is ints: " + ints.toString() );
    } // workWithCollectors

    public static void main( String args[] ) {
        StreamRunner sr = new StreamRunner();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithTakeWhile":
                sr.workWithTakeWhile();
                break;
            case "workWithBooks":
                sr.workWithBooks();
                break;
            case "findGitConflict":
                if ( null != args[ 1 ]  ) {
                    sr.findGitConflict( args[ 1 ] );
                }
                break;
            case "workWithCollectors":
                sr.workWithCollectors();
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }

    }

}
