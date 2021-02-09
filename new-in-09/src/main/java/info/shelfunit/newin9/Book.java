package info.shelfunit.newin9;

import java.util.Set;
import java.util.stream.Stream;

public class Book {
    public final String title;
    public final Set< String > authors;
    public final double price;

    public Book ( String argTitle, Set< String > argAuthors, double argPrice ) {
        this.title   = argTitle;
        this.authors = argAuthors;
        this.price   = argPrice;
    }

    public String getTitle() {
        return title;
    }

    public Set< String > getAuthors() {
        return authors;
    }

    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", price=" + price +
                '}';
    }

    public static Stream< Book > getBooks() {
        return Stream.of(
                new Book("Java 9 Modularity", Set.of( "Sander Mak", "Paul Bakker" ), 33.99 ),
                new Book("Designing Data-Intensive Applications", Set.of( "Martin Klepmann" ), 38.99 ),
                new Book("Java 8 Lambdas", Set.of( "Richard Warburton" ), 33.99 ),
                new Book( "Java Stream Almanac", Set.of( "Poor Richard" ), 9.00 ),
                new Book( "The Expanded Java Stream Almanac", Set.of( "Poor Richard" ), 11.00 ),
                new Book( "Structure and Interpretation of Java", Set.of( "Gerald Fussman" ), 9.00 )
        );
    }

    public static Book getBook() {
        return getBooks().findFirst().get();
    }

}
