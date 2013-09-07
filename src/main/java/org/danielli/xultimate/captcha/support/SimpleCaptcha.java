package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.Answer;
import org.danielli.xultimate.captcha.Captcha;
import org.danielli.xultimate.captcha.Question;

/**
 * 验证码组件的默认实现。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 问题的答案的所属类型。
 */
public class SimpleCaptcha<Q, A> implements Captcha<Q, A> {
	
	private static final long serialVersionUID = -7342872727073204401L;
	
	private Question<Q> question;
	private Answer<A> answer;
	
	public SimpleCaptcha() {
	}
	
	public SimpleCaptcha(Question<Q> question, Answer<A> answer) {
		this.question = question;
		this.answer = answer;
	}
	
	@Override
	public Question<Q> getQuestion() {
		return this.question;
	}
	
	public void setQuestion(Question<Q> question) {
		this.question = question;
	}

	@Override
	public Answer<A> getAnswer() {
		return this.answer;
	}
	
	public void setAnswer(Answer<A> answer) {
		this.answer = answer;
	}

}
