package org.danielli.xultimate.captcha.audio.voice;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.captcha.audio.Sample;
import org.danielli.xultimate.captcha.audio.VoiceProvider;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.StringUtils;
import org.danielli.xultimate.util.io.FileUtils;
import org.danielli.xultimate.util.io.FilenameUtils;
import org.danielli.xultimate.util.math.RandomNumberUtils;

public class DefaultVoiceProvider implements VoiceProvider {
	
	private File[] files;
	private String fileWildcardMatcher;
	
	@Override
	public Sample getVocalization(char letter) {
		Assert.notEmpty(files, "this array [files] must not be empty: it must contain at least 1 element");
		String letterFileWildcardMatcher = StringUtils.replace(fileWildcardMatcher, "{letter}", Character.toString(letter));
		List<File> filterFiles = new ArrayList<File>();
		for (File file : files) {
			if (FilenameUtils.wildcardMatch(file.getName(), letterFileWildcardMatcher)) {
				filterFiles.add(file);
			}
		}
		Assert.notEmpty(filterFiles, "this array [files] must contain [" + letterFileWildcardMatcher + "] at least 1 element");
		File file = filterFiles.get(RandomNumberUtils.nextInt(filterFiles.size()));
		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);
			return new Sample(new ByteArrayInputStream(bytes));
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public File[] getFiles() {
		return files;
	}

	public void setFiles(File[] files) {
		this.files = files;
	}

	public String getFileWildcardMatcher() {
		return fileWildcardMatcher;
	}

	public void setFileWildcardMatcher(String fileWildcardMatcher) {
		this.fileWildcardMatcher = fileWildcardMatcher;
	}

}
