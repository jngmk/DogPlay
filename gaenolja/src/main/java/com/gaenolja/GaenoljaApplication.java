package com.gaenolja;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class GaenoljaApplication {

	public static void main(String[] args) {
		SpringApplication.run(GaenoljaApplication.class, args);
	}

}
