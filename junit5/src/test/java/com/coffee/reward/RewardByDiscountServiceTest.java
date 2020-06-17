package com.coffee.reward;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.coffee.product.Product;
// import org.junit.jupiter.params.shadow.com.univocity.parsers.annotations.Nested;

@DisplayName( "RewardByDiscountService Test" )
// @TestInstance( TestInstance.Lifecycle.PER_CLASS )
@TestInstance( TestInstance.Lifecycle.PER_METHOD )
public class RewardByDiscountServiceTest {

    private RewardByDiscountService reward = null;

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
    @DisplayName( "test needed points" )
    void testNeededPoints() {
        System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        Assertions.assertEquals( 100, reward.getNeededPoints() );
    }

    @Test
    @DisplayName( "test percentage for points" )
    void testPercentageForPoints() {
        System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

        Assertions.assertEquals( 0.1, reward.getPercentage() );
    }


    @Nested
    @DisplayName( "Given there is a small order" )
    class SmallOrder {
        private List< Product > smallOrder;

        @BeforeEach
        void setUp() {
            Product smallDecaf = new Product( 1, "Small Decaf", 1.99 );
            smallOrder = Collections.singletonList( smallDecaf );
        }

        @Test
        @DisplayName( "test zero customer points" )
        void testZeroCustomerPoints() {
            System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

            RewardInformation info = reward.applyReward( smallOrder, 0 );
            Assertions.assertEquals( 0.1, reward.getPercentage() );
            Assertions.assertEquals( 0, info.getDiscount() );
            Assertions.assertEquals( 0, info.getPointsRedeemed() );
        }

        @Nested
        @DisplayName( "Given the customer has zero points" )
        class ZeroPoints {
            private RewardInformation info = null;
            @BeforeEach
            void setUp() {
                info = reward.applyReward( smallOrder, 0 );
            }
            @Test
            @DisplayName( "Then the discount should be 0" )
            void checkDiscount() {
                Assertions.assertEquals( 0, info.getDiscount() );
            }
            @Test
            @DisplayName( "Then the points redeemed should be 0" )
            void checkPointsRedeemed() {
                Assertions.assertEquals( 0, info.getPointsRedeemed() );
            }
        } // end class ZeroPoints
    } // end class SmallOrder

    @Nested
    class BigOrder {
        private List< Product > bigOrder = null;

        @BeforeEach
        void setUp() {
            Product bigDecaf = new Product( 2, "Big Decaf", 2.49 );
            Product bigLatte = new Product( 3, "Big Latte", 2.99 );
            Product bigTea   = new Product( 4, "Big Tea", 2.99 );
            Product espresso = new Product( 5, "Espresso", 2.99 );
            bigOrder = Arrays.asList( bigDecaf, bigLatte, bigTea, espresso );
        }

        @Test
        void testEnoughCustomerPointsForDiscountInBigOrder() {
            System.out.println( "\nstarting method " + this.getClass().getName() + Thread.currentThread().getStackTrace()[ 1 ].getMethodName() );

            RewardInformation info = reward.applyReward( bigOrder, 200 );
            Assertions.assertEquals( 0.1, reward.getPercentage() );
            Assertions.assertEquals( 1.14, info.getDiscount(), 0.01 ); // delta is third arg
            Assertions.assertEquals( 100, info.getPointsRedeemed() );
        }

    } // class BigOrder

}
