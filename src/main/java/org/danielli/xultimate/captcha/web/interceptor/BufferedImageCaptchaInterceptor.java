package org.danielli.xultimate.captcha.web.interceptor;

import httl.util.StringUtils;

import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.util.io.IOUtils;
import org.danielli.xultimate.web.util.ServletRequestUtils;

/**
 * 图象验证码拦截器。用于处理问题为图象并输出。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 答案的所属类型。
 */
public class BufferedImageCaptchaInterceptor<Q, A> extends AbstractErrorBufferedImageCaptchaInterceptor<Q, A> {
	/** 问题处理器 */
	protected QuestionHandler<Q, BufferedImage> questionHandler;
	
	@Override
	public boolean preCreateCaptcha(HttpServletRequest request, HttpServletResponse response) {
		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId");
		if (StringUtils.isEmpty(sessionId)) {
			writeErrorBufferedddImage(response);
			return false;
		}
		return true;
	}

	@Override
	public void postCreateCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha<Q, A> captcha) {
		response.setContentType("image/jpg");
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			ImageIO.write(questionHandler.getResult(captcha.getQuestion()), "jpg", out);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		} finally {
			IOUtils.closeQuietly(out);
		}
	}

	@Override
	public int getPriority() {
		return 300000;
	}


	/**
	 * 设置问题处理器。
	 * @param questionHandler 问题处理器。
	 */
	public void setQuestionHandler(QuestionHandler<Q, BufferedImage> questionHandler) {
		this.questionHandler = questionHandler;
	}

}
