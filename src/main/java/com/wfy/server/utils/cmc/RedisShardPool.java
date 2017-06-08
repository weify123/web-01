package com.wfy.server.utils.cmc;

import com.fhic.business.server.common.BusinessServerConfig;
import com.fhic.business.server.common.BusinessServerParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.jedis.ShardedJedis;
import redis.clients.jedis.ShardedJedisPool;
import redis.clients.util.Hashing;
import redis.clients.util.Sharded;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RedisShardPool {
	private static final Logger logger = LoggerFactory.getLogger(RedisShardPool.class);

	static ShardedJedisPool pool;
	static {
		JedisPoolConfig config = new JedisPoolConfig();// Jedis池配置
		config.setMaxTotal(500);
		config.setMaxIdle(1000 * 60);// 对象最大空闲时间
		config.setMaxWaitMillis(1000 * 10);// 获取对象时最大等待时间

		String redisServers = BusinessServerConfig.getInstance().getRedisServers();
		if (redisServers == null || redisServers.indexOf(":") <= -1) {
			throw new RuntimeException("redis server is null or config error.");
		}
		// 格式: 192.168.1.1:6379,192.168.1.2:6379
		String[] hostAndPorts = redisServers.split(",");
		List<JedisShardInfo> jdsInfoList = new ArrayList<JedisShardInfo>(hostAndPorts.length);
		for (String hostAndPort : hostAndPorts) {
			String[] hp = hostAndPort.split(":");

			JedisShardInfo info = new JedisShardInfo(hp[0], hp[1]);
			info.setPassword(BusinessServerConfig.getInstance().getRedisServerPassword());
			jdsInfoList.add(info);
		}

		pool = new ShardedJedisPool(config, jdsInfoList, Hashing.MURMUR_HASH, Sharded.DEFAULT_KEY_TAG_PATTERN);
	}

	private static ShardedJedis getJedis() {
		return pool.getResource();
	}

	private static void returnResource(ShardedJedis jedis) {
		if (jedis != null && pool != null) {
			pool.returnResource(jedis);
		}
	}

	public static void sadd(String key, String member) {
		ShardedJedis jedis = getJedis();
		try {
			jedis.sadd(key, member);
			logger.debug("call sadd for redis,key:{} , member:{}", key, member);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static Set<String> smembers(String key) {
		ShardedJedis jedis = getJedis();
		try {
			return jedis.smembers(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	public static void sremove(String key, String member) {
		ShardedJedis jedis = getJedis();
		try {
			boolean exist = jedis.sismember(key, member);
			if (exist) {
				jedis.srem(key, member);
				logger.debug("call sremove for redis,key:{} ,member:{}", key, member);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static void hset(String key, String field, String value) {
		ShardedJedis jedis = getJedis();
		try {
			jedis.hset(key, field, value);
			logger.debug("call hset for redis,key:{} ,field:{} ,value:{}", key, field, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static String hget(String key, String field) {
		ShardedJedis jedis = getJedis();
		try {
			return jedis.hget(key, field);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	public static void hremove(String key, String field) {
		ShardedJedis jedis = getJedis();
		try {
			boolean exist = jedis.hexists(key, field);
			if (exist) {
				jedis.hdel(key, field);
				logger.debug("call hremove for redis,key:{} ,field:{}", key, field);
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static Map<String, String> hgetAll(String key) {
		ShardedJedis jedis = getJedis();
		try {
			return jedis.hgetAll(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	public static void publish(String key, String value) {
		ShardedJedis jedis = getJedis();
		try {
			jedis.lpush(key, value);
			logger.debug("call publish for redis,key:{} ,value:{}", key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static void rpush(String key, String... value) {
		ShardedJedis jedis = getJedis();
		try {
			jedis.rpush(key, value);
			logger.debug("call rpush for redis,key:{}, value:{}", key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static List<String> lrange(String key, int start, int end) {
		ShardedJedis jedis = getJedis();
		try {
			return jedis.lrange(key, start, end);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	public static void lrem(String key, String value) {
		ShardedJedis jedis = getJedis();
		try {
			long count = jedis.lrem(key, 0, value);
			logger.debug("call lrem for redis,key:{} ,value:{}, count:{}", key, value, count);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static void del(String key) {
		ShardedJedis jedis = getJedis();
		try {
			jedis.del(key);
			logger.debug("call del for redis,key:{}", key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static void expire(String key, int seconds) {
		ShardedJedis jedis = getJedis();
		try {
			jedis.expire(key, seconds);
			logger.debug("call expire for redis,key:{}, seconds:{}", key, seconds);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static String get(String key) {
		ShardedJedis jedis = getJedis();
		try {
			return jedis.get(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
		return null;
	}

	public static void set(String key, String value) {
		ShardedJedis jedis = getJedis();
		try {
			logger.debug("call set for redis,key:{} ,value:{}", key, value);
			jedis.set(key, value);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
	}

	public static boolean exists(String key) {
		ShardedJedis jedis = getJedis();
		try {
			return jedis.exists(key);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			returnResource(jedis);
		}
		return false;
	}

	public static void main(String[] args) {
		Map<String, String> map = hgetAll(BusinessServerParameter.REDIS_INVEST_KEY);
		Set<String> keys = map.keySet();
		for (String key : keys) {
			System.out.println(key + ":" + map.get(key));
		}

		// RedisShardPool.hset(BusinessServerParameter.REDIS_SETTLEMENT_BATCH_KEY,
		// "10304055414886400",
		// BusinessServerParameter.SETTLEMENT_BATCH_TYPE_LOAN_FAILED+"");

		String s = RedisShardPool.hget(BusinessServerParameter.REDIS_RECHARGE_KEY, "5674395674611712");
		System.out.println(s);

		// RedisShardPool.hremove(BusinessServerParameter.REDIS_SETTLEMENT_KEY,
		// "5715327199481856");
	}
}
