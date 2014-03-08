package org.danielli.xultimate.captcha.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.config.Captcha;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 抽象验证码拦截器，提供优先级功能。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 问题的答案的所属类型。
 */
public abstract class AbstractCaptchaInterceptor<Q, A> implements CaptchaInterceptor<Q, A> {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(AbstractCaptchaInterceptor.class);
	
	/**
	 * 获取拦截器执行优先级，优先级越高，前置和后置方法就越优先执行。
	 * @return 优先级大小。
	 */
	public abstract int getPriority();	
	
	@Override
	public void postCreateCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha<Q, A> captcha) {

	}
	
	@Override
	public boolean preCreateCaptcha(HttpServletRequest request, HttpServletResponse response) {
		return true;
	}
}
