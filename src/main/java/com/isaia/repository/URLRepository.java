package com.isaia.repository;

import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

@Repository
public class URLRepository {

	private static final String ID = "id";
	public static final String URL = "url:";

	private final Jedis jedis;
	private final String idKey;
	private final String urlKey;

	public URLRepository() {
		this.jedis = new Jedis();
		this.idKey = ID;
		this.urlKey = URL;
	}

	public Long incrementID() {
		return this.jedis.incr(this.idKey) - 1;
	}

	public void saveUrl(final String key, final String longUrl) {
		this.jedis.hset(this.urlKey, key, longUrl);
	}

	public String getUrl(final Long id) throws Exception {
		String url = this.jedis.hget(this.urlKey, URL + id);

		if (url == null)
			throw new Exception("URL at key" + id + " does not exist");

		return url;
	}
}
