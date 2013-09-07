package org.danielli.xultimate.captcha;

/**
 * 验证器。用于验证问题的答案与用户回答的答案。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 问题的答案的所属类型。
 * @param <UA> 用户回答的答案的所属类型。
 */
public interface Validator<A, UA> {
	
	/**
	 * 对问题的答案与用户回答的答案进行验证。
	 * @param answer 问题的答案。
	 * @param userAnswer 用户回答的答案。
	 * @return 验证的结果。如果问题的答案与用户回答的答案匹配正确返回true，否则返回false。
	 */
	boolean validate(A answer, UA userAnswer);
	
}
