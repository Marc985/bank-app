package com.prog3.exam;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
public class ConnectionDB {
    private final String username=System.getenv("USERNAME");

    private final String password=System.getenv("PASSWORD");
    private final String url=System.getenv("URL");
    @Bean
    public  Connection getConnection() throws SQLException {


        return  DriverManager.getConnection(
                url,
                username,
                password
        );

    }

}
