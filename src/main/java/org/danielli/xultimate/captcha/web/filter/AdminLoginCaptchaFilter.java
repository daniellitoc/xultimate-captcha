package org.danielli.xultimate.captcha.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.CaptchaProvider;
import org.danielli.xultimate.captcha.Validatable;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.web.util.WebUtils;
import org.springframework.stereotype.Component;

/**
 * 验证码组件过滤器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
@Component("adminLoginCaptchaFilter")
public class AdminLoginCaptchaFilter implements Filter {
	public static final String CAPTCHA_PARAM_NAME = "j_captcha_paran_name";

	public void init(FilterConfig filterConfig) throws ServletException {}

	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		
		@SuppressWarnings("unchecked")
		Validatable<String, String> validatable = (Validatable<String, String>) request.getSession().getAttribute(CaptchaProvider.QUESTION_NAME);
		if (validatable.validateTo(request.getParameter(CAPTCHA_PARAM_NAME))) {
			filterChain.doFilter(request, response);
		} else {
			String urlReferer = WebUtils.getUrlReferer(request);
			urlReferer = StringUtils.isNotEmpty(urlReferer) ? urlReferer + "?error=captcha" : request.getContextPath();
			response.sendRedirect(urlReferer);
		}
	}
	
	public void destroy() {}

}