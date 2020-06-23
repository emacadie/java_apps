package com.coffee.reward;

import com.coffee.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public interface TestHelper {

    RewardService getRewardService();

    @BeforeAll // must be static method w/this annotation, unless you make class TestInstance.Lifecycle.PER_CLASS
    static void beforeAllTests() {
        System.out.println( "In a static BeforeAll method in interface TestHelper" );
    }

    @BeforeEach
    default void beforeEach() {
        System.out.println( "beforeEach in interface TestHelper" );
    }

    @Test
    @DisplayName( "Correct points are set" )
    public default void correctPoints() {
        Assertions.assertEquals( 100, getRewardService().getNeededPoints() );
    }

    public default List< Product > getEmptyOrder() {
        return Arrays.asList();
    };

    public default List< Product > getSampleOrder() {
        Product bigDecaf = new Product( 2, "Big Decaf", 2.49 );
        Product bigLatte = new Product( 3, "Big Latte", 2.99 );
        Product bigTea   = new Product( 4, "Big Tea", 2.99 );
        Product espresso = new Product( 5, "Espresso", 2.99 );
        return Arrays.asList( bigDecaf, bigLatte, bigTea, espresso );
    }
}
