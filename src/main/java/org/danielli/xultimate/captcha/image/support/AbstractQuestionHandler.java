package org.danielli.xultimate.captcha.image.support;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.CaptchaException;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.config.Question;
import org.danielli.xultimate.captcha.image.BackgroundGenerator;
import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.ArrayUtils;

/**
 * 图片抽象问题处理器。负责基本的处理过程，具体处理交由子类完成。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * 
 * @param <Q> 问题的答案的所属类型。
 */
public abstract class AbstractQuestionHandler<Q> implements QuestionHandler<Q, BufferedImage> {
	/** 图片宽度 */
	protected int width;
	/** 图片高度 */
	protected int height;
	/** 背景生成器 */
	protected BackgroundGenerator backgroundGenerator;
	/** 背景渲染器 */
	protected BufferedImageRenderer[] backgroundRenderers;
	/** 最终图片渲染器 */
	protected BufferedImageRenderer[] finalBufferedImageRenderers;
	
	/**
	 * 具体特效处理。
	 * 
	 * @param question 问题组件。
	 * @param sourceBufferedImage 原始BufferedImage。
	 * @return 处理后的BufferedImage。
	 */
	public abstract BufferedImage render(Question<Q> question, BufferedImage sourceBufferedImage) throws Exception;
	
	@Override
	public BufferedImage getResult(Question<Q> question) throws CaptchaException {
		try {
			BufferedImage backgroundBufferedImage = backgroundGenerator.createBackground(width, height);
			
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
		} catch (Exception e) {
			throw new CaptchaException(e.getMessage(), e);
		}
	}

	/**
	 * 设置图片宽度。
	 * 
	 * @param width 图片宽度。
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * 设置图片高度。
	 * 
	 * @param height 图片高度。
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	/**
	 * 设置背景渲染器。
	 * 
	 * @param backgroundRenderers 背景渲染器。
	 */
	public void setBackgroundRenderers(BufferedImageRenderer[] backgroundRenderers) {
		this.backgroundRenderers = backgroundRenderers;
	}

	/**
	 * 设置最终图片渲染器。
	 * 
	 * @param finalBufferedImageRenderers 最终图片渲染器。
	 */
	public void setFinalBufferedImageRenderers(BufferedImageRenderer[] finalBufferedImageRenderers) {
		this.finalBufferedImageRenderers = finalBufferedImageRenderers;
	}

	/**
	 * 设置背景生成器。
	 * 
	 * @param backgroundGenerator 背景生成器。
	 */
	public void setBackgroundGenerator(BackgroundGenerator backgroundGenerator) {
		this.backgroundGenerator = backgroundGenerator;
	}

}
