package com.wfy.server.utils.cmc;

import com.fhic.business.server.common.BusinessServerConfig;

public class SerialNumberGenerator {
	private static SerialNumberGenerator instance = new SerialNumberGenerator();

	private static Snowflake snowflake = new Snowflake(BusinessServerConfig
			.getInstance().getSnowflakeWorkId());

	private SerialNumberGenerator() {

	}

	public static SerialNumberGenerator getInstance() {
		return instance;
	}

	public Long generateSerialNumber() {
		return snowflake.nextId();
	}

	public static void main(String[] args) {
		System.out.println(getInstance().generateSerialNumber());
		System.out.println(getInstance().generateSerialNumber());
	}
}
