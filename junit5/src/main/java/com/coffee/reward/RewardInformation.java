package com.coffee.reward;

public class RewardInformation {
    private long pointsRedeemed;
    private double discount;

    public RewardInformation() {}

    public RewardInformation( long argPointsRedeemed, double argDiscount ) {
        this.setPointsRedeemed( argPointsRedeemed );
        this.setDiscount( argDiscount );
    }

    public long getPointsRedeemed() {
        return pointsRedeemed;
    }

    public void setPointsRedeemed( long pointsRedeemed ) {
        this.pointsRedeemed = pointsRedeemed;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount( double discount ) {
        this.discount = discount;
    }
}
