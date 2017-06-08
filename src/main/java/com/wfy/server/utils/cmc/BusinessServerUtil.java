package com.wfy.server.utils.cmc;

import com.fhic.business.server.common.BusinessServerParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BusinessServerUtil {
	private static final Logger logger = LoggerFactory.getLogger(BusinessServerUtil.class);

	public static final Pattern phoneNumberPattern = Pattern.compile("^((13[0-9])|145|147|(15[^4,\\D])|(18[0-9])|(17[0-9]))\\d{8}$");

	public static final Pattern emailAddressPattern = Pattern.compile("^([a-z0-9A-Z]+[-\\._]*)+@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$");

	public static boolean validatePhoneNunber(String phoneNumber) {
		Matcher matcher = phoneNumberPattern.matcher(phoneNumber);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static boolean validateEmailAddress(String emailAddress) {
		Matcher matcher = emailAddressPattern.matcher(emailAddress);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

	public static Date parseDate(String dateStr, String pattern) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static String getSexByIdCard(String idCard) {
		if (StringUtils.isEmpty(idCard)) {
			return null;
		}
		if (idCard.length() != 18 && idCard.length() != 15) {
			return null;
		}
		String sex = null;
		if (idCard.length() == 18) {
			// 18位身份证号码的，取第17位
			sex = idCard.substring(16, 17);
		} else if (idCard.length() == 15) {
			// 15位身份证号码的，取最后一位
			sex = idCard.substring(14);
		}
		try {
			Integer i = Integer.valueOf(sex);
			// 奇数为男性，偶数为女性
			if (i % 2 == 0) {
				return BusinessServerParameter.SEX_FEMALE;
			} else {
				return BusinessServerParameter.SEX_MAN;
			}
		} catch (NumberFormatException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static Date getBirthdayByIdCard(String idCard) {
		try {
			if (idCard != null) {
				String birthday = null;
				if (idCard.length() == 18) {
					birthday = idCard.substring(6, 14);
				} else if (idCard.length() == 15) {
					birthday = idCard.substring(6, 12);
					birthday = "19" + birthday;
				}
				if (birthday != null) {
					Date date = DateUtil.getDate(birthday);
					Calendar cal = Calendar.getInstance();
					cal.setTime(date);
					// 默认生日的时间是中午12点
					cal.add(Calendar.HOUR_OF_DAY, 12);
					return cal.getTime();
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static Integer getIntegerValueFromMap(Map<?, ?> map, String key) {
		Object value = map.get(key);
		if (value != null) {
			return Integer.valueOf(value.toString());
		} else {
			return null;
		}
	}

	public static Long getLongValueFromMap(Map<?, ?> map, String key) {
		Object value = map.get(key);
		if (value != null) {
			return Long.valueOf(value.toString());
		} else {
			return null;
		}
	}

	public static Short getShortValueFromMap(Map<?, ?> map, String key) {
		Object value = map.get(key);
		if (value != null) {
			return Short.valueOf(value.toString());
		} else {
			return null;
		}
	}

	public static String getStringValueFromMap(Map<?, ?> map, String key) {
		Object value = map.get(key);
		if (value != null) {
			return value.toString();
		} else {
			return null;
		}
	}

	public static Date getDateValueFromMap(Map<?, ?> map, String key) {
		Object value = map.get(key);
		if (value != null) {
			return (Date) value;
		} else {
			return null;
		}
	}

	public static Boolean getBooleanValueFromMap(Map<?, ?> map, String key) {
		Object value = map.get(key);
		if (value != null) {
			return (Boolean) value;
		} else {
			return null;
		}
	}

	/**
	 * 计算净值，单位为分，保留两位小数。如果除净，不进位，否则进位。
	 * 
	 * @param gross
	 *            总价
	 * @param quantity
	 *            数量
	 * @return 单价
	 */
	public static BigDecimal getTradePrice(Long gross, Integer quantity) {
		BigDecimal totalGross = new BigDecimal(gross);
		BigDecimal totalQuantity = new BigDecimal(quantity);
		BigDecimal price = totalGross.divide(totalQuantity, 2, BigDecimal.ROUND_DOWN);
		return price;
	}

	/**
	 * 计算总额，单位为分，不保留小数。 净值 * 份额，如果有小数，则进位。
	 * 
	 * @param price
	 *            单价
	 * @param quantity
	 *            份额
	 * @return 总费用
	 */
	public static BigDecimal getTradeGross(BigDecimal price, Integer quantity) {
		BigDecimal gross = price.multiply(new BigDecimal(quantity));
		gross = gross.setScale(0, RoundingMode.DOWN);
		return gross;
	}

	public static Double decimalCarry(Double value, int scale) {
		BigDecimal d = new BigDecimal(value.toString());
		d = d.setScale(scale, BigDecimal.ROUND_UP);
		return d.doubleValue();
	}

	public static Double decimalDown(Double value, int scale) {
		BigDecimal d = new BigDecimal(value.toString());
		d = d.setScale(scale, BigDecimal.ROUND_DOWN);
		return d.doubleValue();
	}

	public static String hidePhoneNumber(String phoneNumber) {
		String hideNumber = phoneNumber.substring(2, 10);
		return "**" + hideNumber + "*";
	}

	public static void main(String[] args) {
		System.out.println(validatePhoneNunber("14713131546"));
		System.out.println(validateEmailAddress("131131315461@aa.cn"));
		System.out.println(getSexByIdCard("430421196809248"));
		System.out.println(getBirthdayByIdCard("430421196809248111"));
		System.out.println(getBirthdayByIdCard("123456850609111"));
		System.out.println(decimalCarry(0.12340, 4));
		System.out.println(decimalDown(0.1234567, 4));

		long originalPrincipal = 50000_00;
		long tradedPrincipal = 0;
		Long applyMaxGross = 5039100L;
		Long quantity = (originalPrincipal - tradedPrincipal) / 100;
		double maxPrice = applyMaxGross.doubleValue() / quantity;
		System.out.println(maxPrice);
		double d = BusinessServerUtil.decimalDown(maxPrice, 4);// 保留四位小数
		System.out.println(d);
		System.out.println(hidePhoneNumber("12345678912"));
	}
}
