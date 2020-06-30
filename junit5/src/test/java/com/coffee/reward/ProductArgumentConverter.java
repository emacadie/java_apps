package com.coffee.reward;

import com.coffee.product.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class ProductArgumentConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert( Object source, Class <? > targetType ) {
        Assertions.assertEquals( String.class, source.getClass(), "can only convert from String" );
        Assertions.assertEquals( Product.class, targetType, "can only convert to Product" );
        String[] productString = source.toString().split( ";" );
        Product product = new Product(
                Long.parseLong( productString[ 0 ] ),
                productString[ 1 ].trim(),
                Double.parseDouble( productString[ 2 ] )
                );
        return product;
    }
}
