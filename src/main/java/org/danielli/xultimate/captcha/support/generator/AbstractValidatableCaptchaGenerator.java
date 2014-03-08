package org.danielli.xultimate.captcha.support.generator;

import org.danielli.xultimate.captcha.CaptchaGenerator;
import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.captcha.config.Validator;
import org.danielli.xultimate.captcha.support.DefaultCaptcha;
import org.danielli.xultimate.captcha.support.DefaultQuestion;
import org.danielli.xultimate.captcha.support.ValidatableAnswer;

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
public abstract class AbstractValidatableCaptchaGenerator<Q, A, UA> implements CaptchaGenerator<Q, A> {

	private Validator<A, UA> validator;
	
	public void setValidator(Validator<A, UA> validator) {
		this.validator = validator;
	}
	
	protected Captcha<Q, A> createCaptcha(Q question, A answer) {
		DefaultQuestion<Q> simpleQuestion = new DefaultQuestion<Q>(question);
		ValidatableAnswer<A, UA> simpleAnswer = new ValidatableAnswer<A, UA>(answer, validator);
	    return new DefaultCaptcha<Q, A>(simpleQuestion, simpleAnswer);
	}
}
