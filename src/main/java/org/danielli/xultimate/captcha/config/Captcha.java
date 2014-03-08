package org.danielli.xultimate.captcha.config;

import java.io.Serializable;

/**
 * 验证码组件。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 问题的答案的所属类型。
 */
public interface Captcha<Q, A> extends Serializable {
	
	/**
	 * 获取问题组件。
	 * @return 问题组件。
	 */
	Question<Q> getQuestion();
	
	/**
	 * 获取问题的回答组件。
	 * @return 问题的回答组件。
	 */
	Answer<A> getAnswer();
	
}
