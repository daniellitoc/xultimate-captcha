package org.danielli.xultimate.captcha;

import org.springframework.core.NestedRuntimeException;

public class CaptchaException extends NestedRuntimeException {

	private static final long serialVersionUID = -8855614034035077301L;

	public CaptchaException(String msg) {
		super(msg);
	}
	
	public CaptchaException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
