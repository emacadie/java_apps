package info.shelfunit.morelambda;

@FunctionalInterface
public interface MyPredicate< Q > {
    public boolean test( Q q );

    public default MyPredicate< Q > and( MyPredicate< Q > other ) {
       return q -> test( q ) && other.test( q );
    }

    public default MyPredicate< Q > or( MyPredicate< Q > other ) {
        return q -> test( q ) || other.test( q );
    }

    public default MyPredicate< Q > negate(  ) {
        return q -> !test( q );
    }

    public static < Q > MyPredicate< Q > isEqual( Q other ) {
        return q -> q.equals( other );
    }
}
