package com.isaia.repository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import redis.clients.jedis.Jedis;

@Repository
public class URLRepository {

	private final Jedis jedis;
	private final String idKey;
	private final String urlKey;
	private static final Logger LOGGER = LoggerFactory.getLogger(URLRepository.class);

	public URLRepository() {
		this.jedis = new Jedis();
		this.idKey = "id";
		this.urlKey = "url:";
	}

	public URLRepository(final Jedis jedis, final String idKey, final String urlKey) {
		this.jedis = jedis;
		this.idKey = idKey;
		this.urlKey = urlKey;
	}

	public Long incrementID() {
		Long id = this.jedis.incr(this.idKey);
		LOGGER.info("Incrementing ID: {}", id - 1);
		return id - 1;
	}

	public void saveUrl(final String key, final String longUrl) {
		LOGGER.info("Saving: {} at {}", longUrl, key);
		this.jedis.hset(this.urlKey, key, longUrl);
	}

	public String getUrl(final Long id) throws Exception {
		LOGGER.info("Retrieving at {}", id);
		String url = this.jedis.hget(this.urlKey, "url:" + id);
		if (url == null) {
			throw new Exception("URL at key" + id + " does not exist");
		}
		return this.jedis.hget(this.urlKey, "url:" + id);
	}
}
