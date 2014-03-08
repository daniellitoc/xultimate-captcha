package org.danielli.xultimate.captcha.audio.voice;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.sound.sampled.AudioSystem;

import org.danielli.xultimate.captcha.audio.Sample;
import org.danielli.xultimate.captcha.audio.VoiceGenerator;
import org.danielli.xultimate.context.format.Formatter;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.io.FilenameUtils;
import org.danielli.xultimate.util.math.RandomNumberUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 默认声音提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class DefaultVoiceGenerator implements VoiceGenerator, InitializingBean {
	/** 声音文件列表 */
	protected File[] files;
	/** 匹配表达式 */
	protected String fileWildcardMatcher;
	/** 替换KEY */
	protected String matcherKey;
	/** 格式化器 */
	protected Formatter<String, Map<String, ? extends Object>, String> formatter;

	protected Map<Character, List<File>> letterResultCacheMap = new ConcurrentHashMap<>();
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(files, "this array [files] must not be empty: it must contain at least 1 element");
	}
	
	/**
	 * 根据字符获取匹配到的文件列表。
	 * 
	 * @param letter 字符。
	 * @return 匹配到的文件列表。
	 */
	private List<File> getFileterFiles(char letter) {
		List<File> filterFiles = letterResultCacheMap.get(letter);
		if (filterFiles == null) {
			synchronized (letterResultCacheMap) {
				filterFiles = letterResultCacheMap.get(letter);
				if (filterFiles == null) {
					Map<String, Object> parameter = new HashMap<String, Object>();
					parameter.put(matcherKey, Character.toString(letter));
					String letterFileWildcardMatcher = formatter.format(fileWildcardMatcher, parameter);
					
					filterFiles = new ArrayList<File>();
					for (File file : files) {
						if (FilenameUtils.wildcardMatch(file.getName(), letterFileWildcardMatcher)) {
							filterFiles.add(file);
						}
					}
					letterResultCacheMap.put(letter, filterFiles);
					Assert.notEmpty(filterFiles, "this array [files] must contain [" + letterFileWildcardMatcher + "] at least 1 element");
				}
			}
		}
		return filterFiles;
	}

	@Override
	public Sample getVocalization(char letter) {
		List<File> filterFiles = getFileterFiles(letter);
		File file = filterFiles.get(RandomNumberUtils.nextInt(filterFiles.size()));
		try {
			return new Sample(AudioSystem.getAudioInputStream(file));
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	/**
	 * 设置声音文件列表。
	 * 
	 * @param files 声音文件列表。
	 */
	public void setFiles(File[] files) {
		this.files = files;
	}

	/**
	 * 设置匹配表达式。
	 * 
	 * @param fileWildcardMatcher 匹配表达式。
	 */
	public void setFileWildcardMatcher(String fileWildcardMatcher) {
		this.fileWildcardMatcher = fileWildcardMatcher;
	}

	/**
	 * 设置格式化器。
	 * 
	 * @param formatter 格式化器。
	 */
	public void setFormatter(Formatter<String, Map<String, ? extends Object>, String> formatter) {
		this.formatter = formatter;
	}

	/***
	 * 设置替换KEY。
	 * 
	 * @param matcherKey 替换KEY。
	 */
	public void setMatcherKey(String matcherKey) {
		this.matcherKey = matcherKey;
	}

}
