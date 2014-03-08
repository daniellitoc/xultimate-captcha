package org.danielli.xultimate.captcha.support.generator;

import org.danielli.xultimate.captcha.CaptchaException;
import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 组合单词验证码提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <UA> 用户回答的答案的所属类型。
 */
public class ComposeDictionaryWordCaptchaGenerator<UA> extends AbstractValidatableCaptchaGenerator<String, String, UA> {

	private String[] firstDictionaryWords;
	private String[] secondDictionaryWords;
	private int length;
	
	public ComposeDictionaryWordCaptchaGenerator(int length, String[] firstDictionaryWords, String[] secondDictionaryWords) {
		this.firstDictionaryWords = firstDictionaryWords;
		this.secondDictionaryWords = secondDictionaryWords;
		this.length = length;
	}
	
	@Override
	public Captcha<String, String> createCaptcha() throws CaptchaException {
		try {
			int firstLength  = RandomNumberUtils.nextInt(length);
			String firstDictionaryWord = firstDictionaryWords[RandomNumberUtils.nextInt(firstDictionaryWords.length)];
			firstDictionaryWord = StringUtils.substring(firstDictionaryWord, 0, firstLength);

			String secondDictionaryWord = secondDictionaryWords[RandomNumberUtils.nextInt(secondDictionaryWords.length)];
			secondDictionaryWord = StringUtils.substring(secondDictionaryWord, 0, length - firstLength);
		
			String result = firstDictionaryWord + secondDictionaryWord;
			return createCaptcha(result, result);
		} catch (Exception e) {
			throw new CaptchaException(e.getMessage(), e);
		}
	}
}
