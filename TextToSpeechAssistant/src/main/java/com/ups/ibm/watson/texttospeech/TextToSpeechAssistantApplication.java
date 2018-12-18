package com.ups.ibm.watson.texttospeech;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableAutoConfiguration
public class TextToSpeechAssistantApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextToSpeechAssistantApplication.class, args);
		System.out.println("Text To Speech Assistant Application is Ready !!!");
	}
	
//	@Bean
//	public RestTemplate restTemplate() {
//		return new RestTemplate();
//	}
	
}
