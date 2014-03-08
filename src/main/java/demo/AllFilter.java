package demo;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.web.util.WebUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class AllFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		Cookie cookie2 = WebUtils.getCookie(request, "cookie2");
		Cookie nicknameCookie = WebUtils.getCookie(request, "nickname");
		if (cookie2 == null || nicknameCookie == null) {
			cookie2 = new Cookie("cookie2", UUID.randomUUID().toString());
			response.addCookie(cookie2);
			nicknameCookie = new Cookie("nickname", "danielli");
			response.addCookie(nicknameCookie);
		} else {
			Cookie tokenCookie = WebUtils.getCookie(request, "token");
			if (tokenCookie != null && StringUtils.equals("1", tokenCookie.getValue())) {
				filterChain.doFilter(request, response);
			} else {
				tokenCookie = new Cookie("token", "1");
				response.addCookie(tokenCookie);
				System.out.println(cookie2.getValue() + "是" + nicknameCookie.getValue());
			}
			
		}
	}
}
