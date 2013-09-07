package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.Captcha;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 提供从指定单词中随机选择一个并提供截取长度的验证码提供器组件。
 *
 * @author Daniel Li
 * @since 6 December 2012
 * @param <UA> 用户回答的答案的所属类型。
 */
public class DictionaryWordCaptchaProvider<UA> extends AbstractCaptchaProvider<String, String, UA> {

	private String[] dictionaryWords;
	private int length;
	
	public DictionaryWordCaptchaProvider(int length, String[] dictionaryWords) {
		this.length = length;
		this.dictionaryWords = dictionaryWords;
	}
	
	@Override
	public Captcha<String, String> createCaptcha() {
		String value = dictionaryWords[RandomNumberUtils.nextInt(dictionaryWords.length)];
		if (value.length() > length) {
			value = value.substring(0, length);
		}
		return createCaptcha(value, value);
	}
	
}
