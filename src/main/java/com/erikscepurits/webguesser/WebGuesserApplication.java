package com.erikscepurits.webguesser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@CrossOrigin("*")
public class WebGuesserApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebGuesserApplication.class, args);
	}
}
