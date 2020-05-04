package info.shelfunit.contact.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class AppHelper {
    
    /**
     * This will cast lists that you get from Hibernate.
     * @param <T>
     * @param clazz
     * @param c
     * @return 
     */
    public static < T > List< T > castList( Class< ? extends T > clazz, Collection< ? > c ) {
        List< T > r = new ArrayList< T >( c.size() );
        for ( Object o: c ) {
            r.add( clazz.cast( o ) );
        }
        return r;
    } // end method castList
    
} // end class AppHelper
