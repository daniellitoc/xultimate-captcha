package org.danielli.xultimate.captcha;

import org.danielli.xultimate.captcha.config.AnswerDto;

/**
 * 验证码回答处理器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <UA> 用户输入的答案的所属类型。
 */
public interface AnswerHandler<UA> {
	
	/**
	 * 是否是有效验证码回答。
	 * @param answerDto 验证码回答DTO。
	 * @return 如果返回true，则表示验证通过，否则验证失败。
	 */
	boolean isValid(AnswerDto<UA> answerDto);

}
