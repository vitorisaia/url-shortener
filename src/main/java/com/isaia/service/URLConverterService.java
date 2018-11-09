package com.isaia.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaia.repository.URLRepository;
import com.isaia.utility.IDConverter;

@Service
public class URLConverterService {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLConverterService.class);
	private final URLRepository urlRepository;

	@Autowired
	public URLConverterService(final URLRepository urlRepository) {
		this.urlRepository = urlRepository;
	}

	public String shortenURL(final String localURL, final String longUrl) {

		LOGGER.info("Shortening {}", longUrl);

		Long id = this.urlRepository.incrementID();
		String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
		this.urlRepository.saveUrl("url:" + id, longUrl);

		String baseString = this.formatLocalURLFromShortener(localURL);
		String shortenedURL = baseString + uniqueID;

		return shortenedURL;
	}

	public String getLongURLFromID(final String uniqueID) throws Exception {

		Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		String longUrl = this.urlRepository.getUrl(dictionaryKey);

		LOGGER.info("Converting shortened URL back to {}", longUrl);

		return longUrl;
	}

	private String formatLocalURLFromShortener(final String localURL) {

		String[] addressComponents = localURL.split("/");

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < addressComponents.length - 1; ++i) {
			sb.append(addressComponents[i]);
		}

		sb.append('/');

		return sb.toString();
	}

}