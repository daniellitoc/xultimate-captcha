package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.config.Validatable;
import org.danielli.xultimate.captcha.config.Validator;

/**
 * 支持验证功能的问题的回答组件。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 问题的答案的所属类型。
 * @param <UA> 用户回答的答案的所属类型。
 */
public class ValidatableAnswer<A, UA> extends DefaultAnswer<A> implements Validatable<A, UA> {
	
	private static final long serialVersionUID = -4520378880398236834L;
	
	private Validator<A, UA> validator;
	
	public ValidatableAnswer() {
	}
	
	public ValidatableAnswer(A value, Validator<A, UA> validator) {
		super(value);
		this.validator = validator;
	}
	
	@Override
	public void setValidator(Validator<A, UA> validator) {
		this.validator = validator;
	}

	@Override
	public boolean validateTo(UA userAnswer) {
		return validator.validate(getValue(), userAnswer);
	}
}
