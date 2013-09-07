package org.danielli.xultimate.captcha.audio.support;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.captcha.audio.NoiseRenderer;
import org.danielli.xultimate.captcha.audio.VoiceProvider;
import org.danielli.xultimate.captcha.audio.noise.DefaultNoiseRenderer;
import org.danielli.xultimate.captcha.audio.voice.DefaultVoiceProvider;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.io.ResourceUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.core.io.Resource;

public class AudioQuestionHandlerFactoryBean implements FactoryBean<AudioQuestionHandler> {

	private String noiseFilesLocationPattern;
	
	private String voiceFilesLocationPattern;
	
	private String voiceFileWildcardMatcher;
	
	@Override
	public AudioQuestionHandler getObject() throws Exception {
		DefaultNoiseRenderer defaultNoiseRenderer = new DefaultNoiseRenderer();
		Resource[] noiseResources = ResourceUtils.getResources(noiseFilesLocationPattern);
		File[] noiseFiles = new File[noiseResources.length];
		for (int i = 0; i < noiseFiles.length; i++) {
			noiseFiles[i] = noiseResources[i].getFile();
		}
		defaultNoiseRenderer.setFiles(noiseFiles);
		
		DefaultVoiceProvider defaultVoiceProvider = new DefaultVoiceProvider();
		Resource[] voiceResources = ResourceUtils.getResources(voiceFilesLocationPattern);
		File[] voiceFiles = new File[voiceResources.length];
		for (int i = 0; i < voiceResources.length; i++) {
			voiceFiles[i] = voiceResources[i].getFile();
		}
		defaultVoiceProvider.setFiles(voiceFiles);
		defaultVoiceProvider.setFileWildcardMatcher(voiceFileWildcardMatcher);
		
		List<NoiseRenderer> noiseRenderers = new ArrayList<NoiseRenderer>();
		noiseRenderers.add(defaultNoiseRenderer);
		List<VoiceProvider> voiceProviders = new ArrayList<VoiceProvider>();
		voiceProviders.add(defaultVoiceProvider);
		
		AudioQuestionHandler audioQuestionHandler = new AudioQuestionHandler();
		audioQuestionHandler.setNoiseRenderers(noiseRenderers.toArray(ArrayUtils.newInstance(NoiseRenderer.class, noiseRenderers.size())));
		audioQuestionHandler.setVoiceProviders(voiceProviders.toArray(ArrayUtils.newInstance(VoiceProvider.class, voiceProviders.size())));
		return audioQuestionHandler;
	}

	@Override
	public Class<AudioQuestionHandler> getObjectType() {
		return AudioQuestionHandler.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

	public String getNoiseFilesLocationPattern() {
		return noiseFilesLocationPattern;
	}

	public void setNoiseFilesLocationPattern(String noiseFilesLocationPattern) {
		this.noiseFilesLocationPattern = noiseFilesLocationPattern;
	}

	public String getVoiceFilesLocationPattern() {
		return voiceFilesLocationPattern;
	}

	public void setVoiceFilesLocationPattern(String voiceFilesLocationPattern) {
		this.voiceFilesLocationPattern = voiceFilesLocationPattern;
	}

	public String getVoiceFileWildcardMatcher() {
		return voiceFileWildcardMatcher;
	}

	public void setVoiceFileWildcardMatcher(String voiceFileWildcardMatcher) {
		this.voiceFileWildcardMatcher = voiceFileWildcardMatcher;
	}

}
