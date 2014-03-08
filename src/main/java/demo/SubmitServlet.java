package demo;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.danielli.xultimate.captcha.AnswerHandler;
import org.danielli.xultimate.captcha.config.AnswerDto;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.web.util.ServletRequestUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.util.WebUtils;

@WebServlet("/admin/login/submit")
public class SubmitServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
		
		@SuppressWarnings("unchecked")
		AnswerHandler<String> audioAnswerHandler = webApplicationContext.getBean("audioValidatableAnswerHandler", AnswerHandler.class);
		@SuppressWarnings("unchecked")
		AnswerHandler<String> imageAnswerHandler = webApplicationContext.getBean("imageValidatableAnswerHandler", AnswerHandler.class);
		
		String type = ServletRequestUtils.getStringParameter(request, "type");
		String value = ServletRequestUtils.getStringParameter(request, "captchaText");
		
		Cookie cookie2Cookie = WebUtils.getCookie(request, "cookie2");
		
		AnswerDto<String> answerDto = new AnswerDto<String>();
		answerDto.setSessionId(cookie2Cookie.getValue());
		answerDto.setUserAnswer(value);
		if (StringUtils.equals("audio", type)) {
			System.out.println(audioAnswerHandler.isValid(answerDto));
		} else {
			System.out.println(imageAnswerHandler.isValid(answerDto));
		}
	}

}
