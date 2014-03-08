package org.danielli.xultimate.captcha.web.interceptor;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.context.image.awt.ImageUtils;
import org.danielli.xultimate.context.image.model.ImageSize;
import org.danielli.xultimate.util.io.IOUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 抽象错误图片输出验证码拦截器。包括一个默认图片，用于当检查请求参数错误时，将错误图片输出给对方。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 答案的所属类型。
 */
public abstract class AbstractErrorBufferedImageCaptchaInterceptor<Q, A> extends AbstractCaptchaInterceptor<Q, A> implements InitializingBean {
	/** 错误验证码使用的文字 */
	protected Font errorFont = new Font("Arial", Font.ITALIC, 18);
	/** 错误验证码使用的信息 */
	protected String errorMessage = "验证码无效";
	/** 错误验证码使用的文字颜色 */
	protected Color errorColor = Color.RED;
	/** 错误验证码使用的图片大小 */
	protected ImageSize errorImageSize = new ImageSize(100, 30);
	
	private BufferedImage errorBufferedImage;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		errorBufferedImage = ImageUtils.createTransluentBufferedImage(errorImageSize, errorMessage, errorFont, errorColor);
		BufferedImage img = new BufferedImage(errorImageSize.getWidth(), errorImageSize.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics2D = img.createGraphics();
		graphics2D.setBackground(Color.WHITE);
		graphics2D.clearRect(0, 0, errorBufferedImage.getWidth(), errorBufferedImage.getHeight());
		graphics2D.drawImage(errorBufferedImage, 0, 0, null);
		graphics2D.dispose();
		
		errorBufferedImage = img;
	}
	
	public void writeErrorBufferedddImage(HttpServletResponse response) {
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(errorBufferedImage, "jpg", out);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}
	
	/**
	 * 设置错误验证码使用的文字。
	 * @param errorFont 错误验证码使用的文字。
	 */
	public void setErrorFont(Font errorFont) {
		this.errorFont = errorFont;
	}

	/**
	 * 设置错误验证码使用的信息。
	 * @param errorMessage 错误验证码使用的信息。
	 */
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	/**
	 * 设置错误验证码使用的文字颜色。
	 * @param errorColor 错误验证码使用的文字颜色。
	 */
	public void setErrorColor(Color errorColor) {
		this.errorColor = errorColor;
	}

	/**
	 * 设置错误验证码使用的图片大小。
	 * @param errorImageSize 错误验证码使用的图片大小。
	 */
	public void setErrorImageSize(ImageSize errorImageSize) {
		this.errorImageSize = errorImageSize;
	}
}
