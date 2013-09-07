package org.danielli.xultimate.captcha;

/**
 * 验证码提供器组件。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的答案的所属类型。
 * @param <A> 用户回答的答案的所属类型。
 */
public interface CaptchaProvider<Q, A> {
	
	String QUESTION_NAME = "captcha.question.name";
	
	/**
	 * 创建一个验证码组件。
	 * @return 验证码组件。
	 */
	Captcha<Q, A> createCaptcha();
	
}
