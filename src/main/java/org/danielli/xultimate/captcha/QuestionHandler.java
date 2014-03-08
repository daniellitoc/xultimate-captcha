package org.danielli.xultimate.captcha;

import org.danielli.xultimate.captcha.config.Question;

/**
 * 问题处理器组件。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <R> 加工结果的所属类型。
 */
public interface QuestionHandler<Q, R> {
	
	/**
	 * 获取加工结果。
	 * @param question 问题组件。
	 * @return 加工后的结果。如图片。
	 * @throws CaptchaException 生成获取加工结果过程中可能出现的任何异常。
	 */
	R getResult(Question<Q> question) throws CaptchaException;
	
}
