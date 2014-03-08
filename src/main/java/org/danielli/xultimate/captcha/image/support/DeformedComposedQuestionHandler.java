package org.danielli.xultimate.captcha.image.support;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.CaptchaException;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.config.Question;
import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.ArrayUtils;

/**
 * 高级图片组合处理器。通过其他处理器组合完成辅助功能。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * 
 * @param <Q> 问题的答案的所属类型。
 */
public class DeformedComposedQuestionHandler<Q> implements QuestionHandler<Q, BufferedImage> {
	/** 底层问题处理器 */
	protected QuestionHandler<Q, BufferedImage> questionHandler;
	/** 是否添加边框 */
	protected boolean addBorder = true;
	/** 边框颜色 */
	protected Color borderColor = Color.GRAY;
	/** 边框厚度 */
	protected int borderThickness = 1;
	/** 图片渲染器列表 */
	protected BufferedImageRenderer[] bufferedImageRenderers;
	
	@Override
	public BufferedImage getResult(Question<Q> question) throws CaptchaException {
		BufferedImage bufferedImage = questionHandler.getResult(question);
		try {
			if (ArrayUtils.isNotEmpty(bufferedImageRenderers)) {
				for (BufferedImageRenderer bufferedImageRenderer : bufferedImageRenderers) {
					bufferedImage = bufferedImageRenderer.render(bufferedImage);
				}
			}
			
			Graphics2D g = (Graphics2D) bufferedImage.getGraphics();
			if (addBorder) {
				int width = bufferedImage.getWidth();
				int height = bufferedImage.getHeight();
				
				g.setColor(borderColor);
				g.setStroke(new BasicStroke((float) borderThickness));
				g.drawLine(0, 0, 0, width);
		        g.drawLine(0, 0, width, 0);
		        g.drawLine(0, height - 1, width, height - 1);
		        g.drawLine(width - 1, height - 1, width - 1, 0);
			}
			g.dispose();
			return bufferedImage;
		} catch (Exception e) {
			throw new CaptchaException(e.getMessage(), e);
		}
	}

	public void setQuestionHandler(QuestionHandler<Q, BufferedImage> questionHandler) {
		this.questionHandler = questionHandler;
	}

	public void setAddBorder(boolean addBorder) {
		this.addBorder = addBorder;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public void setBorderThickness(int borderThickness) {
		this.borderThickness = borderThickness;
	}

	public void setBufferedImageRenderers(BufferedImageRenderer[] bufferedImageRenderers) {
		this.bufferedImageRenderers = bufferedImageRenderers;
	}

}
