package org.danielli.xultimate.captcha.config;

import java.io.Serializable;

/**
 * 问题组件。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 * @param <Q> 问题的所属类型。
 */
public interface Question<Q> extends Serializable {
	
	/**
	 * 获取问题。
	 * @return 问题。
	 */
	Q getValue();
	
}
