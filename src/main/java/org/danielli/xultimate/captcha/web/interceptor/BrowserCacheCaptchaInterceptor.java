package org.danielli.xultimate.captcha.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.web.util.BrowserCacheGenerator;

/**
 * 浏览器缓存验证码拦截器。避免频繁刷新。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 答案的所属类型。
 */
public class BrowserCacheCaptchaInterceptor<Q, A> extends AbstractCaptchaInterceptor<Q, A> {

	/** 浏览器缓存生成器 */
	protected BrowserCacheGenerator browserCacheGenerator;
	/** 缓存时间 */
	protected Integer cacheSeconds;

	@Override
	public void postCreateCaptcha(HttpServletRequest request, HttpServletResponse response, Captcha<Q, A> handler) {
		try {
			browserCacheGenerator.checkAndPrepare(request, response, cacheSeconds, true);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
	}

	@Override
	public int getPriority() {
		return 500000;
	}

	/**
	 * 设置浏览器缓存生成器。
	 * @param browserCacheGenerator 浏览器缓存生成器。
	 */
	public void setBrowserCacheGenerator(BrowserCacheGenerator browserCacheGenerator) {
		this.browserCacheGenerator = browserCacheGenerator;
	}

	/**
	 * 设置缓存时间。
	 * @param cacheSeconds 缓存时间。
	 */
	public void setCacheSeconds(Integer cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}

}
