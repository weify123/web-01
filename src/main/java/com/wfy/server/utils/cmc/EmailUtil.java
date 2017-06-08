package com.wfy.server.utils.cmc;

import com.fhic.business.server.common.BusinessServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.StringTokenizer;

public class EmailUtil {
	private static final Logger logger = LoggerFactory
			.getLogger(EmailUtil.class);

	public static void smtp(String receiver, String subject, String content)
			throws MessagingException {
		if (BusinessServerConfig.getMailStmpHost() == null)
			throw new MessagingException("smtpHost not found");
		if (BusinessServerConfig.getMailStmpUser() == null)
			throw new MessagingException("user not found");
		if (BusinessServerConfig.getMailStmpPassword() == null)
			throw new MessagingException("password not found");
		if (BusinessServerConfig.getMailStmpPort() == null)
			throw new MessagingException("port not found");
		Properties properties = new Properties();
		properties
				.put("mail.smtp.host", BusinessServerConfig.getMailStmpHost());// 设置smtp主机
		properties
				.put("mail.smtp.port", BusinessServerConfig.getMailStmpPort());
		properties.put("mail.smtp.auth", "true");// 使用smtp身份验证
		Session session = Session.getDefaultInstance(properties,
				new Authenticator() {
					public PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(BusinessServerConfig
								.getMailStmpUser(), BusinessServerConfig
								.getMailStmpPassword());
					}
				});
		// 获得邮件会话对象
		MimeMessage mimeMsg = new MimeMessage(session);// 创建MIME邮件对象
		// 设置邮件主题
		if (subject != null) {
			mimeMsg.setSubject(subject, "GBK");
		}
		// 设置发件人地址
		if (BusinessServerConfig.getMailStmpUser() != null) {
			mimeMsg.setFrom(new InternetAddress(BusinessServerConfig
					.getMailStmpUser()));
		}
		// 设置收件人地址
		if (receiver != null) {
			mimeMsg.setRecipients(Message.RecipientType.TO, parse(receiver));
		}
		MimeBodyPart part = new MimeBodyPart();// mail内容部分
		part.setText(content == null ? "" : content, "GBK");
		// 设置邮件格式为html cqc
		part.setContent(content.toString(), "text/html;charset=GBK");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(part);// 在 Multipart 中增加mail内容部分
		mimeMsg.setContent(multipart);// 增加 Multipart 到信息体
		mimeMsg.setSentDate(new Date());// 设置发送日期
		Transport.send(mimeMsg);// 发送邮件

		logger.debug("email send successfully,receiver is {},subject is {}",
				receiver, subject);
	}

	private static InternetAddress[] parse(String addressSet)
			throws AddressException {
		ArrayList<InternetAddress> list = new ArrayList<InternetAddress>();
		StringTokenizer tokens = new StringTokenizer(addressSet, ";");
		while (tokens.hasMoreTokens()) {
			list.add(new InternetAddress(tokens.nextToken().trim()));
		}
		InternetAddress[] addressArray = new InternetAddress[list.size()];
		list.toArray(addressArray);
		return addressArray;
	}

	public static boolean sendMails(String receivers, String subject,
			String content) {
		if (receivers == null || content == null) {
			return false;
		}
		try {
			smtp(receivers, subject, content);
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		try {
			EmailUtil.sendMails(
					BusinessServerConfig.getMailRemindReleaseReceivers(),
					"tttt", "test");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
