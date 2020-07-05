package com.coffee.reward;

import com.coffee.product.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DynamicContainer;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.TestFactory;
import org.junit.jupiter.api.function.ThrowingConsumer;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.DynamicContainer.dynamicContainer;
import static org.junit.jupiter.api.DynamicTest.dynamicTest;

@Tag( "slow" )
public class RewardByGiftServiceDynamicTest {

    private RewardByGiftService reward;

    @BeforeEach
    void setUp() {
        System.out.println( "Before each" );
        reward = new RewardByGiftService();
        reward.setNeededPoints( 100 );
    }

    @TestFactory
    Collection< DynamicTest > dynamicTestFromCollection() {
        return Arrays.asList(
                dynamicTest( "First Dynamic Test from Collection",
                        () -> assertEquals( 1, 1 ) ),
                dynamicTest( "Second Dynamic Test from Collection",
                        () -> assertEquals( 1, 1 ) )
        );
    } // dynamicTestFromCollection

    @TestFactory
    Iterator< DynamicTest > dynamicTestFromIterator() {
        return Arrays.asList(
                dynamicTest( "First Dynamic Test from Iterator",
                        () -> assertEquals( 1, 1 ) ),
                dynamicTest( "Second Dynamic Test from Iterator",
                        () -> assertEquals( 1, 1 ) )
        ).iterator();
    } // dynamicTestFromIterator

    @TestFactory
    Stream< DynamicTest > giftProductNotInOrderRewardNotApplied() {
           return this.getStreamOfRandomNumbers()
                   .limit( 5 )
                   .mapToObj( randomId ->
                           dynamicTest( "Testing product ID " + randomId,
                                   () -> {
                               reward.setGiftProductId( randomId );
                               RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
                               assertEquals( 0, info.getDiscount() );
                               assertEquals( 0, info.getPointsRedeemed() );
                                   }
                                   )
                   );
    } // giftProductNotInOrderRewardNotApplied

    @TestFactory
    Stream< DynamicTest > giftProductNotInOrderRewardNotAppliedAgain() {
        Iterator< Long > inputGeneratorIterator = this.getStreamOfRandomNumbers().limit( 5 ).iterator();
        Function< Long, String > displayNameGeneratorFunction = randomId -> "Testing product id " + randomId;
        ThrowingConsumer< Long > testExecutorThrowingConsumer = randomId -> {
            reward.setGiftProductId( randomId );
            RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
            assertEquals( 0, info.getDiscount() );
            assertEquals( 0, info.getPointsRedeemed() );
        };
        return DynamicTest.stream( inputGeneratorIterator, displayNameGeneratorFunction, testExecutorThrowingConsumer );
    } // giftProductNotInOrderRewardNotAppliedAgain

    @TestFactory
    Stream< DynamicContainer > dynamicTestsWithContainers() {
        return LongStream.range( 1, 6 )
                .mapToObj( productId -> dynamicContainer(
                        "Container for id: " + productId,
                        Stream.of(
                                dynamicTest( "Valid id", () -> assertTrue( productId > 0 ) ),
                                dynamicContainer( "Test", Stream.of(
                                        dynamicTest( "Discount Applied", () -> {
                                            reward.setGiftProductId( productId );
                                            RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
                                            assertTrue( info.getDiscount() > 0 );
                                        } ) // dynamicTest
                                        ) // Stream.of
                                ) // dynamicContainter
                        ) )
                );
    } // dynamicTestsWithContainers

    private LongStream getStreamOfRandomNumbers() {
        Random r = new Random();
        return r.longs( 1000, 2000 );
    }

    public List< Product > getSampleOrder() {
        Product smallDecaf = new Product( 1, "Small Decaf", 1.99 );
        Product bigDecaf   = new Product( 2, "Big Decaf", 2.49 );
        Product bigLatte   = new Product( 3, "Big Latte", 2.99 );
        Product bigTea     = new Product( 4, "Big Tea", 2.99 );
        Product espresso   = new Product( 5, "Espresso", 2.99 );
        return Arrays.asList( smallDecaf, bigDecaf, bigLatte, bigTea, espresso );
    }
}
