package org.danielli.xultimate.captcha.support;

import org.danielli.xultimate.captcha.Captcha;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 组合单词验证码提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <UA> 用户回答的答案的所属类型。
 */
public class ComposeDictionaryWordCaptchaProvider<UA> extends AbstractCaptchaProvider<String, String, UA> {

	private String[] firstDictionaryWords;
	private String[] secondDictionaryWords;
	private int length;
	
	public ComposeDictionaryWordCaptchaProvider(int length, String[] firstDictionaryWords, String[] secondDictionaryWords) {
		this.firstDictionaryWords = firstDictionaryWords;
		this.secondDictionaryWords = secondDictionaryWords;
		this.length = length;
	}
	
	@Override
	public Captcha<String, String> createCaptcha() {
		int firstLength  = RandomNumberUtils.nextInt(length);
		String firstDictionaryWord = firstDictionaryWords[RandomNumberUtils.nextInt(firstDictionaryWords.length)];
		if (firstDictionaryWord.length() > firstLength) {
			firstDictionaryWord = firstDictionaryWord.substring(0, firstLength);
		} 
		String secondDictionaryWord = secondDictionaryWords[RandomNumberUtils.nextInt(secondDictionaryWords.length)];
		if (secondDictionaryWord.length() > (length - firstLength)) {
			secondDictionaryWord = secondDictionaryWord.substring(0, (length - firstLength));
		} 
		String result = firstDictionaryWord + secondDictionaryWord;
		return createCaptcha(result, result);
	}
}
