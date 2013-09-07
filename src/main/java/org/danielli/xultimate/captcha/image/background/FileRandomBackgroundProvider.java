package org.danielli.xultimate.captcha.image.background;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundProvider;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 随机图片文件的背景提供器。
 *
 * @author Daniel Li
 * @since 6 December 2012
 */
public class FileRandomBackgroundProvider implements BackgroundProvider {

	private BufferedImage[] bufferedImages;

	public FileRandomBackgroundProvider(BufferedImage[] bufferedImages) {
		this.bufferedImages = bufferedImages;
	}

	@Override
	public BufferedImage createBackground(int width, int height) {
		BufferedImage tileImage = bufferedImages[RandomNumberUtils.nextInt(bufferedImages.length)];
		if (tileImage.getWidth() == width && tileImage.getHeight() == height) {
			return tileImage;
		}
		BufferedImage image = new BufferedImage(width, height, tileImage.getType());
		Graphics2D graphics = (Graphics2D) image.getGraphics();
		int NumberX = (width / tileImage.getWidth());
		int NumberY = (height / tileImage.getHeight());
		for (int k = 0; k <= NumberY; k++) {
			for (int l = 0; l <= NumberX; l++) {
				graphics.drawImage(tileImage, l * tileImage.getWidth(), k * tileImage.getHeight(), Math.min(tileImage.getWidth(), width), Math.min(tileImage.getHeight(), height), null);
			}
		}
		graphics.dispose();
		return image;
	}
}
