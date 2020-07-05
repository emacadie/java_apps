package com.coffee.reward.junit4;

import com.coffee.product.Product;
import com.coffee.reward.RewardByConversionService;
import com.coffee.reward.RewardInformation;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class RewardByConversionServiceJUnit4Test {

    private RewardByConversionService reward = null;

    @Before
    public void setUp() {
        reward = new RewardByConversionService();
        reward.setAmount( 10 );
        reward.setNeededPoints( 100 );
    }

    @Test
    public void correctAmount() {
        Assert.assertEquals( 10, reward.getAmount(), 0.01 );
    }

    @Test
    public void emptyOrderZeroPoints() {
        RewardInformation info = reward.applyReward( getEmptyOrder(), 0 );
        Assert.assertEquals( 0, info.getDiscount(), 0.01 );
        Assert.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    public void notEnoughPoints() {
        RewardInformation info = reward.applyReward( getSampleOrder(), 10 );
        Assert.assertEquals( 0, info.getDiscount(), 0.01 );
        Assert.assertEquals( 0, info.getPointsRedeemed() );
    }

    @Test
    public void emptyOrderEnoughPoints() {
        RewardInformation info = reward.applyReward( getEmptyOrder(), 200 );
        Assert.assertEquals( 0, info.getDiscount(), 0.01 );
        Assert.assertEquals( 0, info.getPointsRedeemed() );
    }

    private List< Product > getEmptyOrder() {
        return Arrays.asList();
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
