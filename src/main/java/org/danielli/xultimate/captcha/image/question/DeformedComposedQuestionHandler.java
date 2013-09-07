package org.danielli.xultimate.captcha.image.question;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.Question;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.ArrayUtils;

public class DeformedComposedQuestionHandler<Q> implements QuestionHandler<Q, BufferedImage> {

	private QuestionHandler<Q, BufferedImage> questionHandler;
	
	private boolean addBorder = true;
	private Color borderColor = Color.GRAY;
	private int borderThickness = 1;
	private BufferedImageRenderer[] bufferedImageRenderers;
	
	@Override
	public BufferedImage getResult(Question<Q> question) {
		BufferedImage bufferedImage = questionHandler.getResult(question);
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
