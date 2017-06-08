package com.wfy.server.utils.cmc;

public class Snowflake {
	private final long workerId;
	// 2016-01-01 00:00:00 的毫秒数
	private final static long twepoch = 1451577600000L - 10 * 24 * 60* 60 *1000;
	private long sequence = 0L;
	private final static long workerIdBits = 10L;
	public final static long maxWorkerId = -1L ^ -1L << workerIdBits;
	private final static long sequenceBits = 12L;
	private final static long workerIdShift = sequenceBits;
	private final static long timestampLeftShift = sequenceBits + workerIdBits;
	public final static long sequenceMask = -1L ^ -1L << sequenceBits;
	private long lastTimestamp = -1L;

	public Snowflake(final long workerId) {
		super();
		if (workerId > maxWorkerId || workerId < 0) {
			throw new IllegalArgumentException(String.format(
					"worker Id can't be greater than %d or less than 0",
					maxWorkerId));
		}
		this.workerId = workerId;
	}

	public synchronized long nextId() {
		long timestamp = this.timeGen();
		if (this.lastTimestamp == timestamp) {
			this.sequence = (this.sequence + 1) & sequenceMask;
			if (this.sequence == 0) {
				// 如果当前这一毫秒内的ID已经用完了，等待下一毫秒
				System.out.println("###########" + sequenceMask);
				timestamp = this.tilNextMillis(this.lastTimestamp);
			}
		} else {
			this.sequence = 0;
		}
		if (timestamp < this.lastTimestamp) {
			try {
				throw new Exception(
						String.format(
								"Clock moved backwards.  Refusing to generate id for %d milliseconds",
								this.lastTimestamp - timestamp));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.lastTimestamp = timestamp;
		// 由于41位毫秒数只能保持到2039年，但是因为毫秒数是从 1970-01-01 00:00:00算起的
		// 所以这里我们从2016-01-01 00:00:00算起，这样相当于我们可以多存46(2016 - 1970)年的数据
		// 其实也就是相当于把2016-01-01 00:00:00当成毫秒数的开始数字1970-01-01 00:00:00，总共可以使用69年
		// 也就是说：从2016开始，之后的69年内，使用该算法，都不会产生重复的ID
		long nextId = ((timestamp - twepoch << timestampLeftShift))
				| (this.workerId << workerIdShift) | (this.sequence);
		return nextId;
	}

	private long tilNextMillis(final long lastTimestamp) {
		long timestamp = this.timeGen();
		// 获取下一个毫米数，一直循环等待，直到到了下一个毫秒
		while (timestamp <= lastTimestamp) {
			timestamp = this.timeGen();
		}
		return timestamp;
	}

	private long timeGen() {
		return System.currentTimeMillis();
	}

	public static void main(String[] args) {
		try {
			// 当前的机器的ID为2
			Snowflake worker2 = new Snowflake(2);
			System.out.println(worker2.nextId());
			// System.out.println(Long.toBinaryString(-1L ^ -1L << 4));
			//
			// String strDate = "2016-01-01 00:00:00";
			// SimpleDateFormat sdf = new
			// SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			// Date d = sdf.parse(strDate);
			// System.out.println(d.getTime());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
