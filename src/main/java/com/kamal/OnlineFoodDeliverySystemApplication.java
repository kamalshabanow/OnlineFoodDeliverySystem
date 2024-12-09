package com.kamal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class OnlineFoodDeliverySystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineFoodDeliverySystemApplication.class, args);
    }

}
