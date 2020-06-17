package com.coffee.reward;

import com.coffee.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RewardByGiftServiceTest {
    private RewardByGiftService reward = null;

    @BeforeEach
    void setUp() {
        reward = new RewardByGiftService();
        reward.setGiftProductId( 4 );
        reward.setNeededPoints( 100 );
    }

    private List< Product > buildSampleOrder( int numberOfProducts ) {
        List< Product > list = IntStream.range( 1, numberOfProducts )
                .mapToObj( i -> new Product( i, "Product " + i, 2.99 ) )
                .collect( Collectors.toList() );
        return list;
    }

    @Test
    @DisplayName( "Correct product ID is set" )
    void correctProductID() {
        Assertions.assertEquals( 4, reward.getGiftProductId() );
    }

    @Test
    @DisplayName(  "Reward applied with enough points" )
    void rewardApplied() {
        RewardInformation info = reward.applyReward( this.buildSampleOrder( 10 ), 200 );
        // Assertions.assertNotNull( info );
        // Assertions.assertEquals( 2, info.getDiscount() );
        // Assertions.assertEquals( 10, info.getPointsRedeemed() ); -> this one fails
        Assertions.assertAll(
                "Reward info errors",
                () -> Assertions.assertNotNull( info ),
                () -> Assertions.assertEquals( 2.99, info.getDiscount() ),
                () -> Assertions.assertEquals( 100, info.getPointsRedeemed() )
        );
        Map< String, String > envMap = System.getenv();
        envMap.forEach( ( k, v ) -> System.out.println( "Key: " + k + ", value: " + v ) );
    }

    @Test
    @DisplayName( "Exception thrown with invalid product ID" )
    void exceptionThrownWhenInvalidProductID() {
        long productId = 0;
        // first arg must be Something.class
        // and we can capture the exception and do some asserts with it
        RuntimeException rex = Assertions.assertThrows( RuntimeException.class,
                () -> {
            reward.setGiftProductId( productId );
        });
        Assertions.assertTrue( rex.getMessage().contains( String.valueOf( productId ) ) );
    }

    @Test
    @DisplayName( "Should not exceed timeout" )
    void timeoutException() {
        int numberOfProducts = 50_000;
        reward.setGiftProductId( numberOfProducts - 1 );
        RewardInformation info = Assertions.assertTimeout(
                Duration.ofMillis( 400 ),
                () -> reward.applyReward( this.buildSampleOrder( numberOfProducts ), 200 )
        );
        Assertions.assertEquals( 2.99, info.getDiscount() );
    }

    @Test
    @DisplayName( "Should not exceed preemptive timeout" )
    @Disabled( "I don't like preemptive timeout" )
    void preemptiveTimeoutException() {
        int numberOfProducts = 50_000;
        reward.setGiftProductId( numberOfProducts - 1 );
        RewardInformation info = Assertions.assertTimeoutPreemptively(
                Duration.ofMillis( 400 ),
                () -> reward.applyReward( this.buildSampleOrder( numberOfProducts ), 200 )
        );
        Assertions.assertEquals( 2.99, info.getDiscount() );
        // assertTimeoutPreemptively happens in a different thread, won't tell you how many milliseconds you took
    }

    @Test
    @DisplayName( "Test assumptions and correct product ID is set" )
    void testYourAssumptionsAndCorrectProductID() {
        // System.out.println( "System os: " + System.getProperty( "os.name" ) );
        Assumptions.assumeTrue( "Linux".equals( System.getProperty( "os.name" ) ),
                () -> "Aborting test: not on a real OS: " + System.getProperty( "os.name" ) );
        Assertions.assertEquals( 4, reward.getGiftProductId() );
    }

}
