package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.Answer;
import org.danielli.xultimate.captcha.Validatable;
import org.danielli.xultimate.captcha.Validator;

/**
 * 问题的回答组件的默认实现。并支持验证功能。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 问题的答案的所属类型。
 * @param <UA> 用户回答的答案的所属类型。
 */
public class SimpleAnswer<A, UA> implements Answer<A>, Validatable<A, UA>  {
	
	private static final long serialVersionUID = -2124033993577240609L;
	
	private A value;
	private Validator<A, UA> validator;
	
	public SimpleAnswer() {
	}
	
	public SimpleAnswer(A value, Validator<A, UA> validator) {
		this.value = value;
		this.validator = validator;
	}
	
	@Override
	public A getValue() {
		return this.value;
	}
	
	public void setValue(A value) {
		this.value = value;
	}
	
	@Override
	public void setValidator(Validator<A, UA> validator) {
		this.validator = validator;
	}

	@Override
	public boolean validateTo(UA userAnswer) {
		return validator.validate(value, userAnswer);
	}

}
