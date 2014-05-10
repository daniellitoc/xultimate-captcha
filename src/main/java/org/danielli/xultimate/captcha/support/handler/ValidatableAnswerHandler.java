package org.danielli.xultimate.captcha.support.handler;

import java.util.HashMap;
import java.util.Map;

import net.rubyeye.xmemcached.MemcachedClient;

import org.apache.commons.lang3.BooleanUtils;
import org.danielli.xultimate.captcha.AnswerHandler;
import org.danielli.xultimate.captcha.config.AnswerDto;
import org.danielli.xultimate.captcha.support.ValidatableAnswer;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.XMemcachedReturnedCallback;
import org.danielli.xultimate.context.kvStore.memcached.xmemcached.support.XMemcachedTemplate;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 可验证答案处理器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <A> 验证码答案所属类型。
 * @param <UA> 用户输入的答案类型。
 */
public class ValidatableAnswerHandler<A, UA> implements AnswerHandler<UA> {

	protected XMemcachedTemplate xMemcachedTemplate;
	
	/** KEY表达式 */
	protected String keyExpression;
	/** 替换表达式 */
	protected String replaceExpression;
	/** 格式化器 */
	protected Formatter<String, Map<String, ? extends Object>, String> formatter;
	
	public static final Logger LOGGER = LoggerFactory.getLogger(ValidatableAnswerHandler.class);
	
	@Override
	public boolean isValid(final AnswerDto<UA> answerDto) {
		Map<String, Object> parameter = new HashMap<String, Object>();
		parameter.put(replaceExpression, answerDto.getSessionId());
		final String captchaId = formatter.format(keyExpression, parameter);
		Boolean result = xMemcachedTemplate.execute(new XMemcachedReturnedCallback<Boolean>() {
			
			@Override
			public Boolean doInXMemcached(MemcachedClient memcachedClient) throws Exception {
				ValidatableAnswer<A, UA> validatable = memcachedClient.get(captchaId);
				boolean result = false;
				A questionAnswer = null;
				if (validatable != null) {
					result = validatable.validateTo(answerDto.getUserAnswer());
					questionAnswer = validatable.getValue();
				}
				DateTime currentTime = new DateTime();
				LOGGER.info("{}||{}||{}||{}||{}", answerDto.getSessionId(), currentTime, answerDto.getUserAnswer(), questionAnswer, result);
				if (result) {
					memcachedClient.deleteWithNoReply(captchaId);
				}
				return result;
			}
			
		});
		return BooleanUtils.isTrue(result);
	}

	public void setxMemcachedTemplate(XMemcachedTemplate xMemcachedTemplate) {
		this.xMemcachedTemplate = xMemcachedTemplate;
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
