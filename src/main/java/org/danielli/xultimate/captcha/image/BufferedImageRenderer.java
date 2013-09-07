package org.danielli.xultimate.captcha.image;

import java.awt.image.BufferedImage;

/**
 * 图片渲染器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public interface BufferedImageRenderer {
	
	/**
	 * 渲染图片。
	 * @param sourceBufferedImage 原图片。
	 * @return 渲染后的图片。
	 */
	BufferedImage render(BufferedImage sourceBufferedImage);
	
}
