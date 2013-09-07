package org.danielli.xultimate.captcha;

/**
 * 提供验证功能。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 问题的答案的所属类型。
 * @param <UA> 用户回答的答案的所属类型。
 */
public interface Validatable<A, UA> {
	
	/**
	 * 验证用户回答的答案。
	 * @param userAnswer 用户回答的答案。
	 * @return 验证的结果。如果与用户回答的答案匹配正确返回true，否则返回false。
	 */
	boolean validateTo(UA userAnswer);
	
	/**
	 * 设置验证器。
	 * @param validator 验证器。用于验证问题的答案与用户回答的答案。
	 */
	void setValidator(Validator<A, UA> validator);
	
}
