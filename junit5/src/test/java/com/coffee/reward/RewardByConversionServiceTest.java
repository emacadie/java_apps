package com.coffee.reward;

import com.coffee.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

public class RewardByConversionServiceTest implements TestHelper {

    private RewardByConversionService reward = null;

    @BeforeEach // cannot be static
    void setUp() {
        System.out.println( "beforeEach in RewardByConversionServiceTest" );
        reward = new RewardByConversionService();
        reward.setAmount( 10 );
        reward.setNeededPoints( 100 );
    }

    @Test
    @DisplayName( "Correct amount is set" )
    void correctAmount() {
        Assertions.assertEquals( 10, reward.getAmount() );
    }



    @Test
    @DisplayName( "When empty order and zero points no rewards should be applied" )
    void emptyOrderZeroPoints() {
        RewardInformation info = reward.applyReward( this.getEmptyOrder(), 0 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    @DisplayName( "When not enough points no rewards should be applied" )
    void notEnoughPoints() {
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 0 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    @DisplayName( "When empty order and enough points no rewards should be applied" )
    void emptyOrderEnoughPoints() {
        RewardInformation info = reward.applyReward( this.getEmptyOrder(), 200 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    @DisplayName( "When empty order and enough points no rewards should be applied w/assumption" )
    void emptyOrderEnoughPointsWithAssumption() {
        RewardInformation info = reward.applyReward( this.getEmptyOrder(), 200 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assumptions.assumeTrue( "1".equals( System.getenv( "TEST_POINTS" ) ) );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    @DisplayName( "When empty order and enough points no rewards should be applied w/AssumingThat" )
    void emptyOrderEnoughPointsWithAssumingThat() {
        RewardInformation info = reward.applyReward( this.getEmptyOrder(), 200 );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assumptions.assumingThat( !"1".equals( System.getenv( "TEST_POINTS" ) ) ,
                () -> {
                    Assertions.assertEquals( 0, info.getPointsRedeemed() );
                });
    } // emptyOrderEnoughPointsWithAssumingThat

    @Override
    public RewardService getRewardService() {
        return reward;
    }

}
