package info.shelfunit.morelambda;

public class Person {
    private int age;
    private String lastName;
    private String firstName;

    public Person( int age ) {
        this.setAge( age );
    }

    public Person( int age, String fName, String lName ) {
        this.setFirstName( fName );
        this.setLastName( lName );
        this.setAge( age );
    }

    public Person( String fName, String lName, int age ) {
        this.setFirstName( fName );
        this.setLastName( lName );
        this.setAge( age );
    }

    public int getAge() {
        return age;
    }

    public void setAge( int age ) {
        if ( ( age >= 1 ) && ( age <= 100 ) ) {
            this.age = age;
        } else {
            this.age = 0;
        }
    }

    public String toString() {
        return this.getFirstName() + " " + this.getLastName() + " " + Integer.toString( this.getAge() );
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName( String lastName ) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName( String firstName ) {
        this.firstName = firstName;
    }
}
