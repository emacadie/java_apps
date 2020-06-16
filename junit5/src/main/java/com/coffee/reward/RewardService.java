package com.coffee.reward;

import com.coffee.product.Product;
import java.util.List;

public abstract class RewardService {
    protected long neededPoints;

    public abstract RewardInformation applyReward( List< Product > order, long customerPoints );

    protected double calculateTotal( List< Product > order ) {
        double sum = 0;
        if ( null != order ) {
            sum = order.stream().mapToDouble( p -> p.getPrice() ).sum();
        }
        return sum;
    }

    public long getNeededPoints() {
        return neededPoints;
    }

    public void setNeededPoints( long argPoints ) {
        this.neededPoints = argPoints;
    }
}
