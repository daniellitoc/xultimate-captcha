package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.config.Answer;
import org.danielli.xultimate.util.builder.BuildType;
import org.danielli.xultimate.util.builder.Buildable;
import org.danielli.xultimate.util.builder.ToStringBuilderUtils;

/**
 * 问题的回答组件的默认实现。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 问题的答案的所属类型。
 */
@Buildable( BuildType.TO_STRING )
public class DefaultAnswer<A> implements Answer<A>  {
	
	private static final long serialVersionUID = -2124033993577240609L;
	
	private A value;
	
	
	public DefaultAnswer() {
	}
	
	public DefaultAnswer(A value) {
		this.value = value;
	}
	
	@Override
	public A getValue() {
		return this.value;
	}
	
	public void setValue(A value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		return ToStringBuilderUtils.reflectionToString(this);
	}
}
