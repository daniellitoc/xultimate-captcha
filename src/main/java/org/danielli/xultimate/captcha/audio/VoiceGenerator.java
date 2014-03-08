package org.danielli.xultimate.captcha.audio;

/**
 * 声音生成器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public interface VoiceGenerator {
	
	/**
	 * 获取字符的发声。
	 * 
	 * @param letter 字符。
	 * @return 字符的发声。
	 */
	Sample getVocalization(char letter);
}
