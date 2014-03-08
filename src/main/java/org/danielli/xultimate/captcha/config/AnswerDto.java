package org.danielli.xultimate.captcha.config;

import java.io.Serializable;

/**
 * 验证码回答DTO。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <UA> 用户输入的答案的所属类型。
 */
public class AnswerDto<UA> implements Serializable {

	private static final long serialVersionUID = 4617931835678138452L;
	
	/** 会话ID，验证码标识 */
	private String sessionId;
	/** 用户输入的验证码回答 */
	private UA userAnswer;

	/**
	 * 获取会话ID，验证码标识。
	 * @return 会话ID，验证码标识。
	 */
	public String getSessionId() {
		return sessionId;
	}

	/**
	 * 设置会话ID，验证码标识。
	 * @param sessionId 会话ID，验证码标识。
	 */
	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	/**
	 * 获取用户输入的验证码回答。
	 * @return 用户输入的验证码回答。
	 */
	public UA getUserAnswer() {
		return userAnswer;
	}

	/**
	 * 设置用户输入的验证码回答。
	 * @param userAnswer 用户输入的验证码回答。
	 */
	public void setUserAnswer(UA userAnswer) {
		this.userAnswer = userAnswer;
	}
}
