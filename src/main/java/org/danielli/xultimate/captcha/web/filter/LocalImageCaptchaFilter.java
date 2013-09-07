package org.danielli.xultimate.captcha.web.filter;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.Captcha;
import org.danielli.xultimate.captcha.CaptchaProvider;
import org.danielli.xultimate.captcha.QuestionHandler;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 图片验证码生成器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
@Component("localImageCaptchaFilter")
public class LocalImageCaptchaFilter extends OncePerRequestFilter {

	@Resource(name = "imageQuestionHandler")
	private QuestionHandler<String, BufferedImage> imageQuestionHandler;
	
	@Resource(name = "defaultCaptchaProvider")
	private CaptchaProvider<String, String> captchaProvider;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/png");
		ServletOutputStream out = response.getOutputStream();

		Captcha<String, String> captcha = captchaProvider.createCaptcha();
		request.getSession().setAttribute(CaptchaProvider.QUESTION_NAME, captcha.getAnswer());

		ImageIO.write(imageQuestionHandler.getResult(captcha.getQuestion()), "png", out);
		out.flush();
		out.close();
	}
}