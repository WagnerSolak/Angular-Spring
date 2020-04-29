package com.example.solak.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.example.solak.api.config.property.FinanceiroApiProperty;

@SpringBootApplication  // aplicação com spring boot que aborda várias outras
@EnableConfigurationProperties(FinanceiroApiProperty.class) //serve para podermos usar a config externa, profiles
public class FinanceiroApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(FinanceiroApiApplication.class, args);
	}

}
