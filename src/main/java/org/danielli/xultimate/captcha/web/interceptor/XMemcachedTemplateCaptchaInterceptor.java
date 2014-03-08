package org.danielli.xultimate.captcha.web.interceptor;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.rubyeye.xmemcached.MemcachedClient;

import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;
import org.danielli.xultimate.web.util.ServletRequestUtils;

/**
 * XMemcachedTemplate验证码拦截器。用于存储回答到Memcached。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 答案的所属类型。
 */
public class XMemcachedTemplateCaptchaInterceptor<Q, A> extends AbstractCaptchaInterceptor<Q, A> {

	protected XMemcachedTemplate xMemcachedTemplate;
	/** 过期时间 */
	protected Integer expireSeconds;
	/** KEY表达式 */
	protected String keyExpression;
	/** 替换表达式 */
	protected String replaceExpression;
	/** 格式化器 */
	protected Formatter<String, Map<String, ? extends Object>, String> formatter;
	
	@Override
	public void postCreateCaptcha(HttpServletRequest request, HttpServletResponse response, final Captcha<Q, A> captcha) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(replaceExpression, ServletRequestUtils.getStringParameter(request, "sessionId"));
		final String captchaId = formatter.format(keyExpression, parameter);
		
		xMemcachedTemplate.execute(new XMemcachedCallback() {
			
			@Override
			public void doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				memcachedClient.set(captchaId, expireSeconds, captcha.getAnswer());
			}
		});
	}

	@Override
	public int getPriority() {
		return 200000;
	}

	public void setxMemcachedTemplate(XMemcachedTemplate xMemcachedTemplate) {
		this.xMemcachedTemplate = xMemcachedTemplate;
	}

	/**
	 * 设置过期时间。
	 * @param expireSeconds 过期时间。
	 */
	public void setExpireSeconds(Integer expireSeconds) {
		this.expireSeconds = expireSeconds;
	}

	/**
	 * 设置KEY表达式。
	 * @param keyExpression KEY表达式。
	 */
	public void setKeyExpression(String keyExpression) {
		this.keyExpression = keyExpression;
	}

	/**
	 * 设置替换表达式。
	 * @param replaceExpression 替换表达式。
	 */
	public void setReplaceExpression(String replaceExpression) {
		this.replaceExpression = replaceExpression;
	}

	/**
	 * 设置格式化器。
	 * @param formatter 格式化器。
	 */
	public void setFormatter(Formatter<String, Map<String, ? extends Object>, String> formatter) {
		this.formatter = formatter;
	}

}
