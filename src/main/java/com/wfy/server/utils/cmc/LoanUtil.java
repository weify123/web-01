package com.wfy.server.utils.cmc;

import com.fhic.business.server.common.BusinessServerParameter;
import com.fhic.business.server.exception.ClientParameterException;
import com.fhic.business.server.vo.LoanVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

public class LoanUtil {
	private static final Logger logger = LoggerFactory.getLogger(LoanUtil.class);

	// 一天的毫秒数
	private static final long ONE_DAY_MS = 24 * 60 * 60 * 1000;
	// 一年多少天
	private static final long DAYS_PER_YEAR = 360;

	/**
	 * 根据投资模式，计算利息所得 目前只支持固定开始时间和投资周期的情况,PERIOD_TYPE=5
	 * 
	 * @param loan
	 *            贷款对象，包含了贷款额度、利率、期限等信息
	 * @return Loan
	 * @throws Exception
	 */
	public static LoanVo getInterest(LoanVo loan) {
		Long amount = loan.getAmount();
		Short period = loan.getPeriod();
		Short floatingPeriod = loan.getFloatingPeriod();
		Short rate = loan.getRate();
		Short overdueRate = loan.getOverdueRate();
		Date startDate = loan.getStartDate();
		Date returnDate = loan.getReturnDate();

		Calendar cal = Calendar.getInstance();
		cal.setTime(startDate);
		cal.add(Calendar.DAY_OF_MONTH, period - 1);
		Date endTime = cal.getTime();
		if (endTime.getTime() < returnDate.getTime() && floatingPeriod != null && floatingPeriod > 0) {
			cal = Calendar.getInstance();
			cal.setTime(startDate);
			cal.add(Calendar.DAY_OF_MONTH, period + floatingPeriod - 1);
			endTime = cal.getTime();
			if (endTime.getTime() > returnDate.getTime()) { // 加上浮动期限后，大于还款期限
				cal = Calendar.getInstance();
				cal.setTime(startDate);
				cal.add(Calendar.DAY_OF_MONTH, period - 1);
				Short diff = DateUtil.getDays(cal.getTime(), returnDate).shortValue();
				period = (short) (period + diff);
			} else {
				period = (short) (period + floatingPeriod);
			}
		}

		// 设置0值，防止调用空指针
		loan.setInterest(0L);
		loan.setOverdueInterest(0L);
		// 开始时间固定，结束时间固定
		if (BusinessServerParameter.PERIOD_TYPE_SF_PF.intValue() == (loan.getPeriodType() % 100)) {
			Date endDate = new Date(startDate.getTime() + ONE_DAY_MS * period.intValue());
			// 计算正常利息, 提前还款利息也是按照合同期限的利息计算
			loan.setInterest(getInterest(amount, rate, period));
			// 计算罚息利息
			if (returnDate.after(endDate)) {
				loan.setOverdueInterest(getInterest(amount, overdueRate, endDate, returnDate));
			}
		}
		return loan;
	}

	/**
	 * 获取利息收益
	 * 
	 * @param amount
	 *            借款额
	 * @param rate
	 *            年利率，1000代表10%的年利率
	 * @param period
	 *            借款天数
	 * @return Long 期望利息
	 */
	public static Long getInterest(Long amount, Short rate, Short period) {
		return amount * rate.longValue() * period.longValue() / (DAYS_PER_YEAR * 10000);
	}

	/**
	 * 根据开始和结束时间计算利息，不考虑罚息
	 * 
	 * @param amount
	 *            借款额
	 * @param rate
	 *            年利率，1000代表10%的年利率
	 * @param startDate
	 *            开始时间
	 * @param endDate
	 *            还款时间
	 * @return Long 期望利息
	 * @throws Exception
	 */
	public static Long getInterest(Long amount, Short rate, Date startDate, Date endDate) {
		try {
			if (endDate.after(startDate)) {
				long days = DateUtil.getDays(startDate, endDate);
				return getInterest(amount, rate, Short.parseShort(days + ""));
			}
			return 0L;
		} catch (Exception e) {
			logger.error("get interest,amount:{},rate:{},startDate:{},endDate:{}", amount, rate, startDate, endDate);
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			throw new ClientParameterException(e.getMessage());
		}
	}

	/**
	 * 获取预期利率
	 * 
	 * @param gross
	 *            本金
	 * @param income
	 *            最终受益
	 * @param period
	 *            借款期限
	 * @return 年利率，1000代表10%的年利率
	 */
	public static Integer getRate(Long gross, Long income, Short period) {
		// 收入=本金 + 本金*利率*期限/(360天 * 10000) ,[10000,代表利率倍数,输出Rate=20，代表0.2%的利率]
		Long rate = (income - gross) * DAYS_PER_YEAR * 10000 / (gross * period.longValue());
		return rate.intValue();
	}

	public static void main(String[] args) throws Exception {
		// System.out.println(getInterest(10000L, (short)1000, (short)180));
		// System.out.println(getOverDueInterest(10000L, (short) 1000, new SimpleDateFormat("yyyymmdd").parse("20150101"), new Date()));
		// LoanVo loan = new LoanVo();
		// loan.setPeriodType(5);
		// loan.setAmount(20177777L);
		// loan.setStartDate(new SimpleDateFormat("yyyyMMdd HHmmss").parse("20160607 235959"));
		// loan.setPeriod((short) 39);
		// loan.setRate((short) 200);
		// loan.setOverdueRate((short) 1000);
		// loan.setReturnDate(new SimpleDateFormat("yyyyMMdd HHmmss").parse("20150102 100001"));
		// loan = getInterest(loan);
		// System.out.println("total:" + (loan.getInterest() + loan.getOverdueInterest()));
		// System.out.println(loan.getInterest() + "/" + loan.getOverdueInterest());
		//
		// System.out.println("days:" + DateUtil.getDays(loan.getStartDate(), loan.getReturnDate()));
		// System.out.println(getInterest(10000000L, (short) 200, (short) 200));
		//
		// System.out.println(getRate(9000L, 10500L, (short) 360));
		long interests = LoanUtil.getInterest(1000L, (short) 880, (short) 30);
		int rate = LoanUtil.getRate(1000L, 1000L + interests, (short) 30);
		System.out.println("interests:" + interests);
		System.out.println("rate:" + rate);
	}
}
