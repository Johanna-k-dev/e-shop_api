package com.greta.e_shop_api;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;


@SpringBootApplication
@RestController
public class EShopApiApplication {

	public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMalformed().ignoreIfMissing().load();

        dotenv.entries().forEach(e ->
                System.setProperty(e.getKey(), e.getValue())
        );
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
