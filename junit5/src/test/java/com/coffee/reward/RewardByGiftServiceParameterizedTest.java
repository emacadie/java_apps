package com.coffee.reward;

import com.coffee.product.Product;
import com.coffee.product.SpecialProductsEnum;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class RewardByGiftServiceParameterizedTest {

    private RewardByGiftService reward = null;

    @BeforeEach
    void setUp( TestInfo testInfo ) {
        reward = new RewardByGiftService();
        reward.setNeededPoints( 100 );
        System.out.println( "-- in BeforeEach: " + testInfo.getDisplayName() );
    }

    @ParameterizedTest( name = "Test #{index}: productId={0}" )
    @ValueSource( longs = { 1, 2, 3, 4 } )
    void discountShouldBeApplied( long productId, TestInfo testInfo, TestReporter tReporter ) {
        System.out.println( "Display name: " + testInfo.getDisplayName() );
        tReporter.publishEntry( "Product ID ",  String.valueOf( productId ) );
        reward.setGiftProductId( productId );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    @ParameterizedTest( name = "Enum Test #{index}: productId={0}" )
    @EnumSource( SpecialProductsEnum.class )
    void discountShouldBeAppliedEnumSource( SpecialProductsEnum product ) {
        reward.setGiftProductId( product.getProductId() );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    // we can just use part of an enum
    @ParameterizedTest( name = "Some of Enum Test #{index}: productId={0}" )
    @EnumSource( value = SpecialProductsEnum.class, names = { "BIG_LATTE", "BIG_TEA" } )
    void discountShouldBeAppliedSomeOfEnumSource( SpecialProductsEnum product ) {
        reward.setGiftProductId( product.getProductId() );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    // we can just use part of an enum by specifying what to exclude
    @ParameterizedTest( name = "Exclude some of Enum Test #{index}: productId={0}" )
    @EnumSource( value = SpecialProductsEnum.class, names = { "BIG_LATTE", "BIG_TEA" }, mode = EnumSource.Mode.EXCLUDE )
    void discountShouldBeAppliedExcludeSomeOfEnumSource( SpecialProductsEnum product ) {
        reward.setGiftProductId( product.getProductId() );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    // we can also use reg-exes in "names" and set EnumSource.Mode.MATCH_ALL
    @ParameterizedTest( name = "Filter some of Enum Test #{index}: productId={0}" )
    @EnumSource( value = SpecialProductsEnum.class, names =  "^BIG.*", mode = EnumSource.Mode.MATCH_ALL )
    void discountShouldBeAppliedFilterEnumSource( SpecialProductsEnum product ) {
        reward.setGiftProductId( product.getProductId() );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }


    @ParameterizedTest( name = "Method Test #{index}: productId={0}" )
    @MethodSource( "productIds" ) // name of method in this class
    void discountShouldBeAppliedMethodSource( long productId ) {
        reward.setGiftProductId( productId );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), 200 );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    private static LongStream productIds() {
        return LongStream.range( 1, 6 );
    }

    @ParameterizedTest( name = "Multi param Method Test #{index}: productId={0} customerPoints={1}" )
    @MethodSource( "productIdsCustomerPoints" ) // name of method in this class
    void discountShouldBeAppliedMethodSourceMultiParams( long productId, long customerPoints ) {
        reward.setGiftProductId( productId );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), customerPoints );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    private static Stream< Arguments > productIdsCustomerPoints() {
        return productIds()
                .mapToObj(
                        productId -> Arguments.of( productId, 100 * productId )
                );
    }

    @ParameterizedTest( name = "CSV Source Test #{index}: productId={0} customerPoints={1}" )
    @CsvSource( { "1, 200", "2, 150", "5, 100" } ) // each element in array provides enough args for one test method run
    void discountShouldBeAppliedCsvSource( long productId, long customerPoints ) {
        reward.setGiftProductId( productId );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), customerPoints );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    @ParameterizedTest( name = "CSV File Test #{index}: productId={0} customerPoints={1}" )
    @CsvFileSource( resources = "/product.points.csv" )
    void discountShouldBeAppliedCsvFileSource( long productId, long customerPoints ) {
        reward.setGiftProductId( productId );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), customerPoints );
        Assertions.assertTrue( info.getDiscount() > 0 );
    }

    @ParameterizedTest( name = "Argument Source Test #{index}: productId={0} customerPoints={1}" )
    @ArgumentsSource( ProductIdArgumentsProvider.class )
    void discountShouldBeAppliedArgumentsSource( long productId, long customerPoints ) {
        reward.setGiftProductId( productId );
        RewardInformation info = reward.applyReward( this.getSampleOrder(), customerPoints );
        Assertions.assertTrue( info.getDiscount() > 0 );
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
