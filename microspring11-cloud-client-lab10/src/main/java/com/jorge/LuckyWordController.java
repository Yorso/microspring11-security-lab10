package com.jorge;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LuckyWordController {
	 
	@Value("${lucky-word}") String luckyWord;

	@RequestMapping("/lucky-word")
	public String showLuckyWord() {
		return "The lucky word is: " + luckyWord;
	}
}