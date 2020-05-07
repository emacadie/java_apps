package info.shelfunit.lambdas;

public class Person {
    private int age;

    private String lastName;

    public Person( int age ) {
        this.setAge( age );
    }

    public Person( int age, String lName ) {
        this.setLastName( lName );
        this.setAge( age );
    }

    public Person( String lName, int age ) {
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
        return "Person has age: " + Integer.toString( this.getAge() ) + " and last name " + this.getLastName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
