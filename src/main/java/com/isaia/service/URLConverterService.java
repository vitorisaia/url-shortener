package com.isaia.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.isaia.repository.URLRepository;
import com.isaia.utility.IDConverter;

@Service
public class URLConverterService {

	@Autowired
	private URLRepository urlRepository;

	public String shortenURL(final String localURL, final String longUrl) {
		Long id = this.urlRepository.incrementID();

		String uniqueID = IDConverter.INSTANCE.createUniqueID(id);
		this.urlRepository.saveUrl(URLRepository.URL + id, longUrl);

		String baseString = this.formatLocalURLFromShortener(localURL);
		String shortenedURL = baseString + uniqueID;

		return shortenedURL;
	}

	public String getLongURLFromID(final String uniqueID) throws Exception {
		Long dictionaryKey = IDConverter.INSTANCE.getDictionaryKeyFromUniqueID(uniqueID);
		return this.urlRepository.getUrl(dictionaryKey);
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