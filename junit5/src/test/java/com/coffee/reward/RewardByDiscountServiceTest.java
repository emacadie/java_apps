package com.coffee.reward;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.coffee.product.Product;
import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

// @TestInstance( TestInstance.Lifecycle.PER_CLASS )
@TestInstance( TestInstance.Lifecycle.PER_METHOD )
public class RewardByDiscountServiceTest {

    private RewardByDiscountService reward = null;
    private List< Product > smallOrder;
    private List< Product > bigOrder = null;

    RewardByDiscountServiceTest() {
        System.out.println( "In constructor for RewardByDiscountServiceTest" );
    }

    @BeforeAll
    static void setUpAll() {
        System.out.println( "BeforeAll" );
    }

    @BeforeEach // cannot be static
    void setUp() {
        System.out.println( "BeforeEach" );
        reward = new RewardByDiscountService();
        reward.setNeededPoints( 100 );
        reward.setPercentage( 0.1 );

        Product smallDecaf = new Product( 1, "Small Decaf", 1.99 );
        smallOrder = Collections.singletonList( smallDecaf );

        Product bigDecaf = new Product( 2, "Big Decaf", 2.49 );
        Product bigLatte = new Product( 3, "Big Latte", 2.99 );
        Product bigTea   = new Product( 4, "Big Tea", 2.99 );
        Product espresso = new Product( 5, "Espresso", 2.99 );
        bigOrder = Arrays.asList( bigDecaf, bigLatte, bigTea, espresso );
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println( "AfterAll" );
    }

    @AfterEach // canot be static
    void tearDown() {
        System.out.println( "AfterEach" );
    }

    @Test
    void testNeededPoints() {
        System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        // reward.setNeededPoints( 100 );
        Assertions.assertEquals( 100, reward.getNeededPoints() );
    }

    @Test
    void testPercentageForPoints() {
        System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        // reward.setPercentage( 0.1 );
        Assertions.assertEquals( 0.1, reward.getPercentage() );
    }

    @Nested
    class SmallOrder {

    }

    @Test
    void testZeroCustomerPoints() {
        System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        // reward.setPercentage( 0.1 );
        // Product smallDecaf = new Product( 1, "Small Decaf", 1.99 );
        // List< Product > order = Collections.singletonList( smallDecaf );

        RewardInformation info = reward.applyReward( smallOrder, 0 );
        Assertions.assertEquals( 0.1, reward.getPercentage() );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    void testEnoughCustomerPointsForDiscountInBigOrder() {
        System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        RewardInformation info = reward.applyReward( bigOrder, 200 );
        Assertions.assertEquals( 0.1, reward.getPercentage() );
        Assertions.assertEquals( 1.14, info.getDiscount(), 0.01 ); // delta is third arg
        Assertions.assertEquals( 100, info.getPointsRedeemed() );
    }
}
