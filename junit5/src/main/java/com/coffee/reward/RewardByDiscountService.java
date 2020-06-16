package com.coffee.reward;

import com.coffee.product.Product;
import java.util.List;

public class RewardByDiscountService extends RewardService {
    private double amount;
    private double percentage;

    @Override
    public RewardInformation applyReward( List< Product > order, long customerPoints ) {
        RewardInformation rInfo = new RewardInformation();
        if ( customerPoints >= this.getNeededPoints( ) ) {
            double orderTotal = calculateTotal( order );
            double discount = orderTotal * this.getPercentage();
            rInfo = new RewardInformation( this.getNeededPoints(), discount );
        }
        return rInfo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount( double amount ) {
        this.amount = amount;
    }

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage( double percentage ) {
        if ( percentage > 0 ) {
            this.percentage = percentage;
        }
    }
}