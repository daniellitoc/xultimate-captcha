package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.Captcha;
import org.danielli.xultimate.captcha.CaptchaProvider;
import org.danielli.xultimate.captcha.Validator;

/**
 * 抽象验证码提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * 
 * @param <Q> 问题的答案的所属类型。
 * @param <A> 用户回答的答案的所属类型。
 * @param <UA> 用户回答的答案的所属类型。
 */
public abstract class AbstractCaptchaProvider<Q, A, UA> implements CaptchaProvider<Q, A> {

	private Validator<A, UA> validator;
	
	public void setValidator(Validator<A, UA> validator) {
		this.validator = validator;
	}
	
	protected Captcha<Q, A> createCaptcha(Q question, A answer) {
		SimpleQuestion<Q> simpleQuestion = new SimpleQuestion<Q>(question);
		SimpleAnswer<A, UA> simpleAnswer = new SimpleAnswer<A, UA>(answer, validator);
	    return new SimpleCaptcha<Q, A>(simpleQuestion, simpleAnswer);
	}
}
