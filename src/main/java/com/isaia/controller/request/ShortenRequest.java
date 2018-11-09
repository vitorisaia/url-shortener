package com.isaia.controller.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ShortenRequest {
	private String url;

	@JsonCreator
	public ShortenRequest() {

	}

	@JsonCreator
	public ShortenRequest(@JsonProperty("url") final String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setUrl(final String url) {
		this.url = url;
	}
}