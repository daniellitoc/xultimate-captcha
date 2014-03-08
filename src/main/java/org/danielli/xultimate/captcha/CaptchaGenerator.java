package org.danielli.xultimate.captcha;

import org.danielli.xultimate.captcha.config.Captcha;

/**
 * 验证码提供器组件。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 答案的所属类型。
 */
public interface CaptchaGenerator<Q, A> {
	
	/**
	 * 创建一个验证码组件。
	 * @return 验证码组件。
	 * @throws CaptchaException 生成验证码过程中可能出现的任何异常。
	 */
	Captcha<Q, A> createCaptcha() throws CaptchaException;
	
}
