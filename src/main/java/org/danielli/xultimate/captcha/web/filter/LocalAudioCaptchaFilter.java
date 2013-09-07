package org.danielli.xultimate.captcha.web.filter;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioSystem;

import org.danielli.xultimate.captcha.Captcha;
import org.danielli.xultimate.captcha.CaptchaProvider;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.audio.Sample;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 声音验证码生成器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
@Component("localAudioCaptchaFilter")
public class LocalAudioCaptchaFilter extends OncePerRequestFilter {

	@Resource(name = "audioQuestionHandler")
	private QuestionHandler<String, Sample> audioQuestionHandler;
	
	@Resource(name = "numberCaptchaProvider")
	private CaptchaProvider<String, String> captchaProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		response.setHeader("Cache-Control", "no-store");
		response.setHeader("Pragma", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("audio/wave");
		ServletOutputStream out = response.getOutputStream();
		
		Captcha<String, String> captcha = captchaProvider.createCaptcha();
		request.getSession().setAttribute(CaptchaProvider.QUESTION_NAME, captcha.getAnswer());
		
        AudioSystem.write(audioQuestionHandler.getResult(captcha.getQuestion()).getAudioInputStream(), AudioFileFormat.Type.WAVE, out);
		out.flush();
		out.close();
	}

}