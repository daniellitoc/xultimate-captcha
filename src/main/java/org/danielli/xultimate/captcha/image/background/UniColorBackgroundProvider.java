package org.danielli.xultimate.captcha.image.background;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundProvider;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 单一颜色的背景提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class UniColorBackgroundProvider implements BackgroundProvider {

	private Color[] colors;

	public UniColorBackgroundProvider() {
		this(new Color[] { Color.WHITE });
	}

	public UniColorBackgroundProvider(Color[] colors) {
		this.colors = colors;
	}

	@Override
	public BufferedImage createBackground(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		Graphics2D graphics = (Graphics2D) img.getGraphics();
		Color color = colors[RandomNumberUtils.nextInt(colors.length)];
		graphics.setColor(color);
		graphics.setBackground(color);
		graphics.fillRect(0, 0, width, height);
		graphics.dispose();

		return img;
	}
}
