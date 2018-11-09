package com.isaia.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class URLShortener {

	@PostMapping("/short-action")
	public String shorten(final String url) {
		System.out.println(url);
		return "talkei";
	}

}
