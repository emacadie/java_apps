package com.coffee.product;

public enum SpecialProductsEnum {
    SMALL_DECAF( 1 ),
    BIG_DECAF( 2 ),
    BIG_LATTE( 3 ),
    BIG_TEA( 4 ),
    ESPRESSO( 5 );

    private final int productId;

    private SpecialProductsEnum( int argProductId ) {
        this.productId = argProductId;
    }

    public int getProductId() {
        return this.productId;
    }
}
