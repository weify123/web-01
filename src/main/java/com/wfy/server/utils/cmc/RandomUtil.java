package com.wfy.server.utils.cmc;

import java.util.*;

/**
 * Utility class to generate random value or string.
 * 
 * 
 */
public final class RandomUtil {

	private static Random random = new Random();

	/**
	 * All methods are static; we do not expect anyone to instantiate this class
	 */
	public RandomUtil() {
	}

	/**
	 * Returns an <code>int</code> value with a positive sign, greater than or equal to <code>0</code> and less than <code>10,000,000</code>. Returned values are chosen pseudorandomly with
	 * (approximately) uniform distribution from that range.
	 * 
	 * @return an <code>int</code> value within the range
	 */
	public static int random() {
		return (int) (Math.random() * 10000000D);
	}

	/**
	 * Returns an <code>int</code> value with a positive sign, greater than or equal to <code>0</code> and less than <code>10,000,000</code>. Returned values are chosen pseudorandomly with
	 * (approximately) uniform distribution from that range.
	 * 
	 * @return an <code>int</code> value within the range
	 */
	public static int randomMax(int max) {
		return (int) (Math.random() * max);
	}

	public static long randomMax(long max) {
		return (long) (Math.random() * max);
	}

	/**
	 * Returns an <code>int</code> value, greater than or equal to <em>lower</em> and less than or equal to <em>upper</em>.
	 * 
	 * @param lower
	 *            The min value
	 * @param upper
	 *            The max value
	 * @return an <code>int</code> value within the range
	 */
	public static int random(int lower, int upper) {
		if (lower < 0 || upper < 0) {
			throw new IllegalArgumentException();
		}

		int len = String.valueOf(upper).length();
		int base = 1;
		for (int i = 0; i < len; i++)
			base *= 10;

		int num;
		do
			num = (int) (Math.random() * (double) base);
		while (num < lower || num > upper);
		return num;
	}

	/**
	 * Returns a <code>String</code> with <em>len</em> characters randomly selected from "23456789ABCDEFGHIJKLMNOQRSTUWXYZ" set.
	 * 
	 * @param len
	 *            The desired length of random string
	 * @return a <code>String</code> with size <em>len</em>
	 */
	public static String random(int len) {
		String sys = "23456789ABCDEFGHIJKLMNQRSTUWXYZ";
		// Random random = new Random();
		String re = "";
		for (int i = 0; i < len; i++) {
			int n = random.nextInt(31);
			re += sys.substring(n, n + 1);
		}
		return re;
	}

	public static String randomDegital(int len) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < len; i++) {
			result.append(random.nextInt(10));
		}
		return result.toString();
	}

	/**
	 * Constructs a random string of printable characters.
	 * 
	 * @param len
	 *            The desired length of the random string.
	 * @return String of size n holding random printable characters.
	 */
	public static String randomString(int len) {
		String result = "";

		for (int i = 0; i < len; i++) {
			result = result + randomChar();
		}
		return result;
	}

	/**
	 * Generate a single random, printable character
	 * 
	 * @return character with ascii value 32 - 126
	 */
	public static char randomChar() {
		/*
		 * printable characters are ascii 32 - 126 (that's 95 characters) so we generate 0-95 and add 32 to get this range
		 */
		int val = random.nextInt(95) + 32;
		/**
		 * 不要0/o,1/i等不容易辨认的字符
		 */
		if (val == 48 || val == 79 || val == 105 || val == 111)
			val++;
		return (char) val;
	}

	/**
	 * 水机取数组中的指定个数对象
	 * 
	 * @return 返回取出来的对象数组
	 */
	@SuppressWarnings("unchecked")
	public static Object[] randomObject(Object[] objs, int size) {
		if (objs == null || objs.length < size) {
			return null;
		}
		if (objs.length == size) {
			return objs;
		}
		List list = new ArrayList();
		for (Object obj : objs) {
			list.add(obj);
		}
		Object[] result = new Object[size];
		for (int i = 0; i < size; i++) {
			int id = randomMax(list.size());
			result[i] = list.get(id);
			list.remove(result[i]);
		}
		return result;
	}

	/**
	 * 可产生指定重复个数的的随机数组,确保重复个数不超过 repeatableCount
	 * 
	 * 注意，该方法不适合生成大型数组
	 * 
	 * @param objs
	 *            源数组
	 * @param size
	 *            总共产生多少个
	 * @param repeatableCount
	 *            可重复的个数
	 * @return 返回取出来的对象数组
	 */
	public static Object[] randomObject(Object[] objs, int size, int repeatableCount) {
		if (objs == null || objs.length * repeatableCount < size) {
			return null;
		}
		List<Collection<Object>> sets = new ArrayList<Collection<Object>>();
		for (int i = 0; i < repeatableCount; i++) {
			// 之所以不用HashSet是因为如果objs全是数字的话,根据hash算法,生成出来的数字全是有序的
			List<Object> set = new ArrayList<Object>();
			sets.add(set);
		}
		List<Object> list = new ArrayList<Object>(Arrays.asList(objs));

		for (int i = 0; i < size;) {
			int id = randomMax(list.size());
			Object temp = list.get(id);
			boolean needRemove = false;
			for (int k = 0; k < sets.size(); k++) {
				Collection<Object> col = sets.get(k);
				if (!col.contains(temp)) {
					col.add(temp);
					i++;
					break;
				} else if (k == sets.size() - 1) {// 如果连最后一个集合都已经包含了该对象,那么该对象就应该被移除
					needRemove = true;
				}
			}
			if (needRemove) {
				list.remove(temp);
			}
		}

		List<Object> retList = new ArrayList<Object>();
		for (int i = 0; i < sets.size(); i++) {
			retList.addAll(sets.get(i));
		}
		return retList.toArray();
	}

	public static void main(String[] args) {
		// String[] src =
		// {"01","02","03","04","05","06","07","08","09","10","11","12","13","14"};
		// Object[] result = randomObject(src, 3);
		// System.out.println(result[0]);
		// System.out.println(result[1]);
		// System.out.println(result[2]);
		String[] src = { "01", "02", "03", "04" };
		Object[] objs = randomObject(src, 8, 2);
		for (int i = 0; i < objs.length; i++) {
			System.out.println(objs[i].toString());
		}
	}

}
