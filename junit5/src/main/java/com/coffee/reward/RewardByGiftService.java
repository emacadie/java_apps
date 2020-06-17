package com.coffee.reward;

import com.coffee.product.Product;
import java.util.List;
import java.util.Optional;

public class RewardByGiftService extends RewardService {
    private double amount;
    private double percentage;
    private long giftProductId;

    @Override
    public RewardInformation applyReward( List< Product > order, long customerPoints ) {
        RewardInformation rInfo = new RewardInformation();
        if ( customerPoints >= this.getNeededPoints( ) ) {
            Optional< Product > result = order
                    .stream()
                    .filter( p -> p.getId() == this.giftProductId )
                    .findAny();
            if ( result.isPresent() ) {
                rInfo = new RewardInformation( this.getNeededPoints(), result.get().getPrice() );
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

    public double getPercentage() {
        return percentage;
    }

    public void setPercentage( double percentage ) {
        if ( percentage > 0 ) {
            this.percentage = percentage;
        }
    }

    public long getGiftProductId() {
        return giftProductId;
    }

    public void setGiftProductId( long argGiftProductId ) {
        if ( argGiftProductId > 0 ) {
            this.giftProductId = argGiftProductId;
        } else {
            throw new IllegalArgumentException( argGiftProductId + " is not a valid product" );
        }

    }
}