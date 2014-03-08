package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.config.Answer;
import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.captcha.config.Question;
import org.danielli.xultimate.util.builder.BuildType;
import org.danielli.xultimate.util.builder.Buildable;
import org.danielli.xultimate.util.builder.ToStringBuilderUtils;

/**
 * 验证码组件的默认实现。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 问题的答案的所属类型。
 */
@Buildable( BuildType.TO_STRING )
public class DefaultCaptcha<Q, A> implements Captcha<Q, A> {
	
	private static final long serialVersionUID = -7342872727073204401L;
	
	private Question<Q> question;
	private Answer<A> answer;
	
	public DefaultCaptcha() {
	}
	
	public DefaultCaptcha(Question<Q> question, Answer<A> answer) {
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
	
	@Override
	public String toString() {
		return ToStringBuilderUtils.reflectionToString(this);
	}
}
