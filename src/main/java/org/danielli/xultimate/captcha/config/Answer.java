package org.danielli.xultimate.captcha.config;

import java.io.Serializable;

/**
 * 问题的答案组件。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 问题的答案的所属类型。
 */
public interface Answer<A> extends Serializable {
	
	/**
	 * 获取问题的答案。
	 * @return 问题的答案。
	 */
	A getValue();
	
}
