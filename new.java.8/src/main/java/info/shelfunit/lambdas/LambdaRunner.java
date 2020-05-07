package info.shelfunit.lambdas;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class LambdaRunner {

    private static final String className = "LambdaRunner.";

    public void useAnonFileFilter() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // anonymous class
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept( File file ) {
                return file.getName().endsWith( ".java" );
            }
        };
        File dir = new File( "c:/EKM/KEES/Defects/Done/86842/files/" );
        File[] javaFiles = dir.listFiles( fileFilter );
        System.out.println( "javaFiles.length with anonymous filter: " + javaFiles.length );
    }

    public void useLambdaFileFilter() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // lambda - can you use multiple lines?
        FileFilter filter = ( File file ) -> file.getName().endsWith( ".java" );
        File dir = new File( "c:/EKM/KEES/Defects/Done/86842/files/" );
        File[] javaFiles = dir.listFiles( filter );
        System.out.println( "javaFiles.length with lambda filter: " + javaFiles.length );
        System.out.println( "Lambda with more than one line of code coming up:" );
        Runnable r = () -> {
            for ( int i = 0; i < 5; i++ ) {
                System.out.println( "\tHello: i is: " + i );
            }
        }; // don't forget the semi-colon
        r.run();
        String s01 = "hello";
        String s02 = "bye";
        System.out.println( "Trying lambda with multiple args using strings " + s01 + ", and " + s02 );
        Comparator< String > c = ( String s1, String s2 ) -> {
            return Integer.compare( s1.length(), s2.length() );
        };
        System.out.println( "Calling our Comparator with our lambda is a variable: " + c.compare( s01, s02) );
        System.out.println( "Making another comparator lambda omitting types" );
        Comparator< String > c2 = (s1, s2 ) -> {
            return Integer.compare( s1.length(), s2.length());
        };
        System.out.println( "Calling our Comparator w/omitted variables with our lambda is a variable: " + c2.compare( s01, s02) );
    } // end lambdaFileFilter

    public void useDataWithLambdas() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        List< String > stringList = new ArrayList< String >();
        stringList.add( "001" );
        stringList.add( "002" );
        stringList.add( "003" );
        System.out.println( "About to print stringList with foreach syntax" );
        stringList.forEach( sListString -> System.out.println( "Member of stringList: " + sListString ) );
        System.out.println( "Now print stringList with method reference" );
        stringList.forEach( System.out::println );

    } // end useDataWithLambdas

    public void useNewPatterns() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        Predicate< String > p1 = s -> s.length() < 20;
        Predicate< String > p2 = s -> s.length() > 10;
        Predicate< String > p3 = p1.and( p2 );
        // Predicate< String > id = Predicate.isEqual( target );
        List< String > strings = Arrays.asList( "001", "002", "003", "004", "005" );
        List< String > result = new ArrayList< String >();
        System.out.println( "result.size(): " + result.size() );
        Consumer< String > c1 = s -> System.out.println( s );
        System.out.println( "strings.forEach using the Consumer c1" );
        strings.forEach( c1 );
        Consumer< String > c2 = s -> {
            System.out.print( "In c2, adding " + s + ";  " );
            result.add( s );
        };
        System.out.println( "\nChaining consumers to build result list" );
        strings.forEach( c1.andThen( c2 ) );
        System.out.println( "\nresult.size(): " + result.size() );
        System.out.println( "About to call again for more chaining" );
        strings.forEach( c1.andThen( c2 ) );
        System.out.println( "\nresult.size(): " + result.size() );
        
    } // end useNewPatterns

    public static void main( String args[] ) {
        LambdaRunner lr = new LambdaRunner();
        lr.useAnonFileFilter();
        lr.useLambdaFileFilter();
        lr.useDataWithLambdas();
        lr.useNewPatterns();
    } // end main
} // end LambdaRunner

