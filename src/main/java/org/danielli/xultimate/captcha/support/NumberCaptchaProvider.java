package org.danielli.xultimate.captcha.support;

/**
 * 提供指定长度的随机数字验证码提供器组件。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <UA> 用户回答的答案的所属类型。
 */
public class NumberCaptchaProvider<UA> extends RandomWordCaptchaProvider<UA> {

	public NumberCaptchaProvider() {
		this(DEFAULT_LENGTH);
	}
	
	public NumberCaptchaProvider(int length) {
		super(length, new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' });
	}
	
}
