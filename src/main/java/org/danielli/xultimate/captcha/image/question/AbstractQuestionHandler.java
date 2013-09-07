package org.danielli.xultimate.captcha.image.question;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.Question;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.image.BackgroundProvider;
import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.ArrayUtils;

public abstract class AbstractQuestionHandler<Q> implements QuestionHandler<Q, BufferedImage> {

	private int width;
	private int height;
	
	private BackgroundProvider backgroundProvider;
	private BufferedImageRenderer[] backgroundRenderers;
	private BufferedImageRenderer[] finalBufferedImageRenderers;
	
	public abstract BufferedImage render(Question<Q> question, BufferedImage sourceBufferedImage);
	
	@Override
	public BufferedImage getResult(Question<Q> question) {
		BufferedImage backgroundBufferedImage = backgroundProvider.createBackground(width, height);
		
		BufferedImage finalBufferedImage = new BufferedImage(backgroundBufferedImage.getWidth(), backgroundBufferedImage.getHeight(), backgroundBufferedImage.getType());
		Graphics2D graphics = (Graphics2D) finalBufferedImage.getGraphics();
		graphics.drawImage(backgroundBufferedImage, 0, 0, finalBufferedImage.getWidth(), finalBufferedImage.getHeight(), null);
		graphics.dispose();
		
		if (ArrayUtils.isNotEmpty(backgroundRenderers)) {
			for (BufferedImageRenderer bufferedImageRenderer : backgroundRenderers) {
				finalBufferedImage = bufferedImageRenderer.render(finalBufferedImage);
			}
		}
		
		BufferedImage questionBufferedImage = new BufferedImage(finalBufferedImage.getWidth(), finalBufferedImage.getHeight(), BufferedImage.TYPE_INT_ARGB);

		questionBufferedImage = render(question, questionBufferedImage);
		
		graphics = (Graphics2D) finalBufferedImage.getGraphics();
		graphics.drawImage(questionBufferedImage, 0, 0, null);
		graphics.dispose();
		
		if (ArrayUtils.isNotEmpty(finalBufferedImageRenderers)) {
			for (BufferedImageRenderer bufferedImageRenderer : finalBufferedImageRenderers) {
				finalBufferedImage = bufferedImageRenderer.render(finalBufferedImage);
			}
		}
		return finalBufferedImage;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public BackgroundProvider getBackgroundProvider() {
		return backgroundProvider;
	}

	public void setBackgroundProvider(BackgroundProvider backgroundProvider) {
		this.backgroundProvider = backgroundProvider;
	}

	public BufferedImageRenderer[] getBackgroundRenderers() {
		return backgroundRenderers;
	}

	public void setBackgroundRenderers(BufferedImageRenderer[] backgroundRenderers) {
		this.backgroundRenderers = backgroundRenderers;
	}

	public BufferedImageRenderer[] getFinalBufferedImageRenderers() {
		return finalBufferedImageRenderers;
	}

	public void setFinalBufferedImageRenderers(
			BufferedImageRenderer[] finalBufferedImageRenderers) {
		this.finalBufferedImageRenderers = finalBufferedImageRenderers;
	}

}
