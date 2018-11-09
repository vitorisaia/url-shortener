package com.isaia.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.isaia.controller.request.ShortenRequest;
import com.isaia.service.URLConverterService;
import com.isaia.utility.URLValidator;

@RestController
public class URLController {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLController.class);

	@Autowired
	private URLConverterService urlConverterService;

	@RequestMapping(value = "/shortener", method = RequestMethod.POST, consumes = { "application/json" })
	public String shortenUrl(@RequestBody @Valid final ShortenRequest shortenRequest, final HttpServletRequest request)
			throws Exception {

		LOGGER.info("Regular URL: " + shortenRequest.getUrl());

		String longUrl = shortenRequest.getUrl();

		if (URLValidator.INSTANCE.validateURL(longUrl)) {
			String localURL = request.getRequestURL().toString();
			String shortenedUrl = this.urlConverterService.shortenURL(localURL, shortenRequest.getUrl());

			LOGGER.info("Short URL: " + shortenedUrl);

			return shortenedUrl;
		}

		throw new Exception("Please enter a valid URL");
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public RedirectView redirectUrl(@PathVariable final String id, final HttpServletRequest request,
			final HttpServletResponse response) throws IOException, URISyntaxException, Exception {

		LOGGER.debug("Received shortened url to redirect: " + id);

		String redirectUrlString = this.urlConverterService.getLongURLFromID(id);
		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://" + redirectUrlString);

		return redirectView;
	}
}
