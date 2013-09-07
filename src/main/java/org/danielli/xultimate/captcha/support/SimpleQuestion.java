package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.Question;

/**
 * 问题组件的默认实现。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 */
public class SimpleQuestion<Q> implements Question<Q> {
	
	private static final long serialVersionUID = -2517477580861410619L;
	
	private Q value;
	
	public SimpleQuestion() {
	}
	
	public SimpleQuestion(Q value) {
		this.value = value;
	}
	
	@Override
	public Q getValue() {
		return this.value;
	}
	
	public void setValue(Q value) {
		this.value = value;
	}

}
