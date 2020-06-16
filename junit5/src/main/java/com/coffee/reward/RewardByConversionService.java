package com.coffee.reward;

import com.coffee.product.Product;
import java.util.List;

public class RewardByConversionService extends RewardService {
    private double amount;

    @Override
    public RewardInformation applyReward( List< Product > order, long customerPoints ) {
        RewardInformation rInfo = new RewardInformation();
        if ( customerPoints >= this.getNeededPoints() ) {
            double orderTotal = this.calculateTotal( order );
            if ( orderTotal > this.getAmount() ) {
                rInfo = new RewardInformation( this.getNeededPoints(), this.getAmount() );
            }
        }
        return rInfo;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount( double amount ) {
        this.amount = amount;
    }
}
