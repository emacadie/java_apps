package com.coffee.reward;

import com.coffee.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;

import java.util.List;
import java.util.Random;

public class RewardByGiftServiceRepeatTest implements TestHelper {

    private RewardByGiftService reward = null;

    @BeforeEach
    void setUp() {
        reward = new RewardByGiftService();
        reward.setNeededPoints( 100 );
        System.out.println( "setUp called" );
    }


    @DisplayName( "When gift product is not in order, reward should not be applied" )
    // @RepeatedTest( 5 ) // just specify 5 reps
    @RepeatedTest( value = 5, name = "-{displayName} -> {currentRepetition}/{totalRepetitions}" )
    void giftProductNotInOrder() {
        reward.setGiftProductId( getRandomProductIdNotInOrder() );
        RewardInformation info = reward.applyReward( getSampleOrder(), 200 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }


    @DisplayName( "When gift product is not in order, reward should not be applied" )
    // @RepeatedTest( 5 ) // just specify 5 reps
    @RepeatedTest( value = 5, name = "-{displayName} -> {currentRepetition}/{totalRepetitions}" )
    void giftProductNotInOrderWithRepetitionInfo(RepetitionInfo rInfo) {
        long productId = rInfo.getCurrentRepetition() + 1000;
        System.out.println( "Using product Id: " + productId );
        RewardInformation info = reward.applyReward( getSampleOrder(), 200 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }

    private long getRandomProductIdNotInOrder() {
        Random r = new Random();
        return r.longs(1000, 2000).findFirst().getAsLong();
    }


    @Override
    public RewardService getRewardService() {
        return reward;
    }
}
