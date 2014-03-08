package org.danielli.xultimate.captcha.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.config.Captcha;

/**
 * 验证码拦截器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 问题的答案的所属类型。
 */
public interface CaptchaInterceptor<Q, A> {

	/**
	 * 生成验证码前执行。
	 * 
	 * @param request 请求。
	 * @param response 响应。
	 * @return 如果为true，则继续执行，如果为false，则不在执行。
	 */
	boolean preCreateCaptcha(HttpServletRequest request, HttpServletResponse response);
	
	/**
	 * 生成验证码后执行。
	 * 
	 * @param request 请求。
	 * @param response 响应。
	 * @param captcha 验证码。
	 */
	void postCreateCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha<Q, A> captcha);
	
}
