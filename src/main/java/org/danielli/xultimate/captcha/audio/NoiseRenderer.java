package org.danielli.xultimate.captcha.audio;

import java.util.List;

/**
 * 声音渲染器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public interface NoiseRenderer {
	
	/**
	 * 添加声音渲染。
	 * 
	 * @param target 发音列表。
	 * @return 合成声音。
	 */
	Sample addNoise(List<Sample> target);
}
