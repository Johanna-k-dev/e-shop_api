package com.greta.e_shop_api;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@RestController
public class EShopApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(EShopApiApplication.class, args);
	}

    @GetMapping("/hello")
    public String sayHello() {
        return "Hello World!";
    };

    @GetMapping("/am-i-a-warthog")
    public String amIAWarthog() {
        return "Yes of course !";
    };
}
