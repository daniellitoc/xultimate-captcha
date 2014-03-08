package org.danielli.xultimate.captcha.image.background;

import java.awt.AlphaComposite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundGenerator;

/**
 * 透明颜色的背景提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class TransparentBackgroundGenerator implements BackgroundGenerator {

	@Override
	public BufferedImage createBackground(int width, int height) {
		BufferedImage bg = new BufferedImage(width, height, BufferedImage.TRANSLUCENT);

		Graphics2D graphics = (Graphics2D) bg.getGraphics();
		graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.CLEAR, 0.0f));
		graphics.fillRect(0, 0, width, height);
		graphics.dispose();

		return bg;
	}

}
