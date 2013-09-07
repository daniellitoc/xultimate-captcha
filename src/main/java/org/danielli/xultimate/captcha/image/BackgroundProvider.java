package org.danielli.xultimate.captcha.image;

import java.awt.image.BufferedImage;

/**
 * 背景图提供器。
 *
 * @author Daniel Li
 * @since 6 December 2012
 */
public interface BackgroundProvider {
    
	/**
	 * 创建背景图。
	 * @param width 图片宽度。
	 * @param height 图片高度。
	 * @return
	 */
    BufferedImage createBackground(int width, int height);
}
