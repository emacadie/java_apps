package info.shelfunit.conferencedemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class PersistenceConfiguration {
    /*
    @Bean
    public DataSource dataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        // Do I want this hard-coded in a file?
        builder.url( "jdbc:postgresql://localhost:5433/conference_app?user=conf_user&password=this-is-conference" );
        System.out.println( "using our own DataSource: " + builder.toString() );
        return builder.build();
    }
    */
}
