package com.coffee.product;

public class Product {
    private long id;
    private String name;
    private double price;

    public Product( long argId, String argName, double argPrice ) {
        this.setId( argId );
        this.setName( argName );
        this.setPrice( argPrice );
    }

    public long getId() {
        return id;
    }

    public void setId( long id ) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName( String name ) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice( double price ) {
        this.price = price;
    }

    @Override
    public boolean equals( Object o ) {
        if (this == o ) {
            return true;
        }
        if ( null == o || getClass() != o.getClass() ) {
            return false;
        }
        Product p = ( Product ) o;
        return this.getId() == p.getId();
    }

    @Override
    public int hashCode() {
        return ( int ) ( this.getId() ^ ( this.getId() >>> 32 ) );
    }
}
