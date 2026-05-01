package com.github.mdutka5.simplifiedstockmarket;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:16:///stockmarket",
        "spring.datasource.username=postgres",
        "spring.datasource.password=postgres",
        "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver"
})
public abstract class BaseIntegrationTest {
}


//package com.github.mdutka5.simplifiedstockmarket;
//
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.DynamicPropertyRegistry;
//import org.springframework.test.context.DynamicPropertySource;
//import org.testcontainers.containers.PostgreSQLContainer;
//import org.testcontainers.junit.jupiter.Container;
//import org.testcontainers.junit.jupiter.Testcontainers;
//
//@SpringBootTest
//@AutoConfigureMockMvc
//@Testcontainers
//public abstract class BaseIntegrationTest {
//
//    @Container
//    static final PostgreSQLContainer<?> postgres;
//
//    static {
//        System.setProperty("api.version", "1.41");
//        System.setProperty("docker.host", "unix:///run/docker.sock");
//        postgres = new PostgreSQLContainer<>("postgres:16")
//                .withDatabaseName("stockmarket")
//                .withUsername("postgres")
//                .withPassword("postgres");
//        postgres.start();  // start once manually
//    }
//
//    @DynamicPropertySource
//    static void configureProperties(DynamicPropertyRegistry registry) {
//        registry.add("spring.datasource.url", postgres::getJdbcUrl);
//        registry.add("spring.datasource.username", postgres::getUsername);
//        registry.add("spring.datasource.password", postgres::getPassword);
//    }
//}