package info.shelfunit.newin9;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class OptionalRunner {

    public void workWithStreams() {
        System.out.println( "Starting workWithStreams" );
        Stream< Optional < Integer > > optionals = Stream.of(
                Optional.of( 1 ),
                Optional.empty(),
                Optional.of( 2 )
        );
        Stream< Integer > ints = optionals.flatMap( o -> o.stream() );
        ints.forEach( i -> System.out.print( "i: " + i + ", " ) );
        System.out.println();
    } // workWithStreams

    public void workWithBooks() {
        System.out.println( "In workWithBooks" );
        Set< String > authors = Book.getBooks()
                .map( b -> b.authors.stream().findFirst() )
                .flatMap( optAuthor -> optAuthor.stream() )
                .collect( Collectors.toSet() );
        authors.forEach( a -> System.out.println( "author: " + a ) );
        System.out.println();
    } // workWithBooks

    public void workWithIfPresentOrElse() {
        System.out.println( "Starting workWithIfPresentOrElse" );
        Optional< Book > full = Optional.of( Book.getBook() );
        full.ifPresentOrElse( f -> System.out.println( "Here is f: " + f ), () -> System.out.println( "Nothing here" ) );
        System.out.println( "Now with an empty Optional" );
        Optional.empty().ifPresentOrElse( f -> System.out.println( "Here is f: " + f ), () -> System.out.println( "Nothing here" ) );
    } // workWithIfPresentOrElse

    static Optional< Book > getBestOffer() {
        return Optional.empty();
    }

    static Optional< Book > getExternalOffer() {
        return Optional.of( new Book( "External Book", Set.of(), 11.99 ) );
    }

    public void workWithOr() {
        System.out.println( "In workWithOr" );
        System.out.println( "We want to avoid nested calls to orElse" );
        Optional< Book > localFallback = Optional.of( Book.getBook() );
        Optional< Book > bestBook =
                getBestOffer()
                .or( () -> getExternalOffer() )
                .or( () -> localFallback ); // getExternalOffer returns a full Optional, so this is never triggered
        System.out.println( "Here is bestBook: " + bestBook );
    } // workWithOr

    public static void main( String args[] ) {
        OptionalRunner oRnnr = new OptionalRunner();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithStreams":
                oRnnr.workWithStreams();
                break;
            case "workWithBooks":
                oRnnr.workWithBooks();
                break;
            case "workWithIfPresentOrElse":
                oRnnr.workWithIfPresentOrElse();
                break;
            case "workWithOr":
                oRnnr.workWithOr();
                break;
            case "findGitConflict":
                if ( null != args[ 1 ]  ) {
                    // sr.findGitConflict( args[ 1 ] );
                }
                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }

    } // main

}
