package com.coffee.reward;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.ArrayList;


public class RewardByConversionWithMethodExtensionTest {

    private RewardByConversionService reward;

    @BeforeAll
    static void setUpAll() {
        System.out.println( "In BeforeAll" );
    }

    @BeforeEach
    void setUp() {
        System.out.println( "In BeforeEach" );
        reward = new RewardByConversionService();
        reward.setNeededPoints( 10 );
        reward.setAmount( 10 );
    }

    @AfterEach
    void tearDown() {
        System.out.println( "In tearDown w/AfterEach" );
    }

    @AfterAll
    static void tearDownAll() {
        System.out.println( "In tearDownAll w/AfterAll" );
    }

    // we are only extending this method
    @Test
    @ExtendWith( LifecycleExtension.class )
    void changeAmount() {
        System.out.println( "Test changeAmount" );
        reward.setAmount( 20 );
        Assertions.assertEquals( 20, reward.getAmount() );
    }

    @Test
    void rewardNotAppliedEmptyOrder() {
        System.out.println( "Test rewardNotAppliedEmptyOrder" );
        RewardInformation info = reward.applyReward( new ArrayList<>(), 500 );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
        Assertions.assertEquals( 0, info.getDiscount() );
    }
}
