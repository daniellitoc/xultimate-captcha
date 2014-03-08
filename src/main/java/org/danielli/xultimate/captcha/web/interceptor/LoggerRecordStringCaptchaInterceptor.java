package org.danielli.xultimate.captcha.web.interceptor;

import httl.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.config.Captcha;
import org.danielli.xultimate.web.util.ServletRequestUtils;
import org.danielli.xultimate.web.util.WebUtils;
import org.joda.time.DateTime;
import org.springframework.core.task.TaskExecutor;

/**
 * 信息记录验证拦截器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 * @param <A> 答案的所属类型。
 */
public class LoggerRecordStringCaptchaInterceptor<Q, A> extends AbstractCaptchaInterceptor<Q, A> {
	/** 任务执行器 */
	protected TaskExecutor taskExecutor;
	
	@Override
	public boolean preCreateCaptcha(final HttpServletRequest request, HttpServletResponse response) {
		String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId");
		if (StringUtils.isEmpty(sessionId)) {
			taskExecutor.execute(new Runnable() {
				@Override
				public void run() {
					String fromSite = ServletRequestUtils.getStringParameter(request, "site", "未知");
					String remoteAddr = WebUtils.getRemoteAddr(request);
					DateTime currentTime = new DateTime();
					LOGGER.warn("{}||{}||{}", fromSite, remoteAddr, currentTime);
				}
			});
		}
		return true;
	}

	@Override
	public void postCreateCaptcha(final HttpServletRequest request, final HttpServletResponse response, final Captcha<Q, A> captcha) {
		taskExecutor.execute(new Runnable() {
			@Override
			public void run() {
				String remoteAddr = WebUtils.getRemoteAddr(request);
				String fromSite = ServletRequestUtils.getStringParameter(request, "identity", "未知");
				String sessionId = ServletRequestUtils.getStringParameter(request, "sessionId");
				DateTime currentTime = new DateTime();
				LOGGER.info("{}||{}||{}||{}||{}", fromSite, sessionId, remoteAddr, currentTime, captcha);
			}
		});
	}

	@Override
	public int getPriority() {
		return 400000;
	}

	/**
	 * 设置任务执行器。
	 * @param taskExecutor 任务执行器。
	 */
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
}
