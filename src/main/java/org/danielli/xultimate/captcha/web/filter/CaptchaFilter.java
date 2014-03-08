package org.danielli.xultimate.captcha.web.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.CaptchaGenerator;
import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.captcha.web.interceptor.AbstractCaptchaInterceptor;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * 生成验证码。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 验证码问题所属类型。
 * @param <A> 验证码答案所属类型。
 */
public class CaptchaFilter<Q, A> extends OncePerRequestFilter {
	
	/** 验证码提供器 */
	protected CaptchaGenerator<Q, A> captchaGenerator;
	/** 验证码拦截器列表 */
	protected AbstractCaptchaInterceptor<Q, A>[] captchaInterceptors;
	
	@Override
	public void afterPropertiesSet() throws ServletException {
		super.afterPropertiesSet();
		Arrays.sort(captchaInterceptors, new Comparator<AbstractCaptchaInterceptor<Q, A>>() {
			@Override
			public int compare(AbstractCaptchaInterceptor<Q, A> o1, AbstractCaptchaInterceptor<Q, A> o2) {
				return o2.getPriority() - o1.getPriority();
			}
		});
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		for (AbstractCaptchaInterceptor<Q, A> captchaInterceptor : captchaInterceptors) {
			if (!captchaInterceptor.preCreateCaptcha(request, response)) 
				return;
		}
		Captcha<Q, A> captcha = captchaGenerator.createCaptcha();
		for (AbstractCaptchaInterceptor<Q, A> captchaInterceptor : captchaInterceptors) {
			captchaInterceptor.postCreateCaptcha(request, response, captcha);
		}
	}

	/**
	 * 设置验证码提供器。
	 * 
	 * @param captchaGenerator 验证码提供器。
	 */
	public void setCaptchaGenerator(CaptchaGenerator<Q, A> captchaGenerator) {
		this.captchaGenerator = captchaGenerator;
	}

	/**
	 * 设置验证码拦截器列表。
	 * 
	 * @param captchaInterceptors 验证码拦截器列表。
	 */
	public void setCaptchaInterceptors(AbstractCaptchaInterceptor<Q, A>[] captchaInterceptors) {
		this.captchaInterceptors = captchaInterceptors;
	}
}
