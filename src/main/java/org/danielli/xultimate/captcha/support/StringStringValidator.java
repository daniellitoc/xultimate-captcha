package org.danielli.xultimate.captcha.support;

import java.io.Serializable;

import org.danielli.xultimate.captcha.Validator;
import org.danielli.xultimate.util.StringUtils;

/**
 * 针对问题的答案的所属类型为{@link String}}且用户回答的答案的所属类型为{@link String}}的验证器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class StringStringValidator implements Validator<String, String>, Serializable {
	
	private static final long serialVersionUID = -3910782779313714291L;

	@Override
	public boolean validate(String answer, String userAnswer) {
		return StringUtils.equalsIgnoreCase(answer, userAnswer);
	}

}
