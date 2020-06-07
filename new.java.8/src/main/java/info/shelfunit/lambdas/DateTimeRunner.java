package info.shelfunit.lambdas;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.Set;

public class DateTimeRunner {

    private static final String className = "DateTimeRunner.";
    public void workWithInstant() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        // Instant is a point in time
        try {
            Instant int1 = Instant.now();
            System.out.println( "Here is int1: " + int1 );
            // sleep for 4 seconds
            Thread.sleep( 4000 );
            Instant int2 = Instant.now();
            System.out.println( "Here is int1 after four seconds: " + int1 );
            System.out.println( "Here is int2: " + int2 );
            // Duration is the amount of time between two Instance instances
            Duration diffOfInts = Duration.between( int1, int2 );
            System.out.println( "Seconds in our duration diffOfInts: " + diffOfInts.getSeconds() );
            System.out.println( "Nanoseconds in our duration diffOfInts: " + diffOfInts.getNano() );
            System.out.println( "Instant.MIN: " + Instant.MIN );
            System.out.println( "Instant.MAX: " + Instant.MAX );
            System.out.println( "int1: " + int1 );
            System.out.println( "Four seconds after int1: " + int1.plusSeconds( 4 ) );
            System.out.println( "int2: " + int2 );
            // Instant instances are immutable
        } catch ( InterruptedException e ) {
                e.printStackTrace();
        }
    } // workWithInstant

    public void workWithNewDates() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        LocalDate nowLd = LocalDate.now();

        LocalDate usa = LocalDate.of( 1776, Month.JULY, 4 );
        System.out.println( "now made with 'now': " + nowLd );
        System.out.println( "USA!! made with 'of': " + usa + " no more zero-based months!" );
        System.out.println( usa + " was a " + usa.getDayOfWeek() );
        System.out.println( "Period is like Duration for LocalDate w/LocalDate.until" );
        Period usaPeriod = usa.until( nowLd );
        System.out.println( "America's period: " + usaPeriod + " which is " + usaPeriod.getYears() + " years " + usaPeriod.getMonths()  +
                " months and " + usaPeriod.getDays() + " days"
        );
        long usaInDays = usa.until( nowLd, ChronoUnit.DAYS );
        System.out.println( "Total number of days for USA: " + usaInDays );
        System.out.println( "USA in months: " + usa.until( nowLd, ChronoUnit.MONTHS ) );
        System.out.println( "Time to adjust with TemporalAdjusters which do day of week, first/last day of month/year" );
        LocalDate nextSunday = nowLd.with( TemporalAdjusters.next( DayOfWeek.SUNDAY ) );
        System.out.println( "nowLd: " + nowLd + " and next Sunday: " + nextSunday );

        System.out.println( "LocalTime is just a time" );
        System.out.println( "Now is: " + LocalTime.now().toString() + " or make one of any time you want with 'of': " +
                LocalTime.of( 10, 20 ).toString() + " ; hour is 0 to 23, so back to 0-based"
        );
        System.out.println( "Now: " + LocalTime.now().toString() + " and 8 hours from now: " + LocalTime.now().plusHours( 8 ) );
        System.out.println( "Now: " + LocalTime.now().toString() + " and 8 minutes from now: " + LocalTime.now().plusMinutes( 8 ) );
        System.out.println( "LocalTime also has minusHours and minusMinutes" );

    } // workWithNewDates

    public void workWithZones() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        System.out.println( "Look at zones: " );
        Set< String > allZones = ZoneId.getAvailableZoneIds();

        allZones.stream().forEach( zone -> System.out.println( zone.toString() ) );
        System.out.println( "How many zones is that? Answer: " + allZones.size() );
    } // workWithZones

    public void formatDates() {
        System.out.println( "-----\nstarting method " + className + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );
        LocalDateTime now = LocalDateTime.now();
        System.out.println( "Here is now w/default formatting: " + now );
        System.out.println( "now formatted with BASIC_ISO_DATE: " +  DateTimeFormatter.BASIC_ISO_DATE.format( now ) );
        System.out.println( "now formatted with ISO_DATE: " +  DateTimeFormatter.ISO_DATE.format( now ) );
        System.out.println( "Old java.util classes have toInstant (old to new) methods, or static from (new to old)" );
    } // formatDates

    public static void main( String args[] ) {
        // java.util.Date and java.util.Calendar are not very intuitive
        // you can make a Date private, but if they use the getter, they can still change it
        // unless you do defensive copying - call date.getTime()
        // but that can be a lot of overhead if the getter is called a lot
        // perhaps Date should be immutable
        // now java.time is the way to go
        DateTimeRunner dtr = new DateTimeRunner();
        String methodToRun = args[ 0 ];
        switch( methodToRun ) {
            case "workWithInstant" :
                dtr.workWithInstant();
                break;
            case "workWithNewDates":
                dtr.workWithNewDates();
                break;
            case "workWithZones":
                dtr.workWithZones();
                break;
            case "formatDates":
                dtr.formatDates();
                break;
                /*
            case "workingWithOptions":
                sr.workingWithOptions();
                break;
            case "workWithMoreReducersAndCollections":
                sr.workWithMoreReducersAndCollections();
                break;
            case "workLiveCodingExercise":
                sr.workLiveCodingExercise( args[ 1 ] );
                break;
                */
            default:
                System.out.println( "No method named " + methodToRun );
        }
    } // end main
} // end class DateTimeRunner

