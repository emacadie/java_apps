package info.shelfunit.newin9;

import java.util.ArrayList;
import java.util.List;

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

    public static void main( String args[] ) {
        StreamRunner sr = new StreamRunner();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithTakeWhile":
                sr.workWithTakeWhile();
                break;
            case "workWithReduce":

                break;
            case "doMoreWorkWithStreams":

                break;
            default:
                System.out.println( "No method named " + methodToRun );
        }

    }

}
