package com.wfy.server.utils.cmc;

public class ObjectMapperUtil {
	private static ObjectMapperConfig mapper = new ObjectMapperConfig();

	public static ObjectMapperConfig getJsonMapper() {
		return mapper;
	}
}
