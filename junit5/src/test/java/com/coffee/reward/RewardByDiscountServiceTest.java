package com.coffee.reward;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import com.coffee.product.Product;

public class RewardByDiscountServiceTest {

    @Test
    void testNeededPoints() {
        RewardByDiscountService reward = new RewardByDiscountService();
        reward.setNeededPoints( 100 );
        Assertions.assertEquals( 100, reward.getNeededPoints() );

    }

    @Test
    void testPercentageForPoints() {
        RewardByDiscountService reward = new RewardByDiscountService();
        reward.setPercentage( 0.1 );
        Assertions.assertEquals( 0.1, reward.getPercentage() );

    }

    @Test
    void testZeroCustomerPoints() {
        RewardByDiscountService reward = new RewardByDiscountService();
        reward.setPercentage( 0.1 );
        reward.setNeededPoints( 100 );
        Product smallDecaf = new Product( 1, "Small Decaf", 1.99 );
        List< Product > order = Collections.singletonList( smallDecaf );

        RewardInformation info = reward.applyReward( order, 0 );
        Assertions.assertEquals( 0.1, reward.getPercentage() );
        Assertions.assertEquals( 0, info.getDiscount() );
        Assertions.assertEquals( 0, info.getPointsRedeemed() );
    }
}
