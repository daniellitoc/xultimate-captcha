package org.danielli.xultimate.captcha.audio.support;

import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.captcha.Question;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.audio.Mixer;
import org.danielli.xultimate.captcha.audio.NoiseRenderer;
import org.danielli.xultimate.captcha.audio.Sample;
import org.danielli.xultimate.captcha.audio.VoiceProvider;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 问题处理器的默认实现。用于将问题类型为String处理为Sample。
 *
 * @author Daniel Li
 * @since 6 December 2012
 */
public class AudioQuestionHandler implements QuestionHandler<String, Sample> {
	
	private VoiceProvider[] voiceProviders;
	private NoiseRenderer[] noiseRenderers;	
	
	@Override
	public Sample getResult(Question<String> question) {
		char[] ansAry = question.getValue().toCharArray();
		
		List<Sample> samples = new ArrayList<Sample>();
		Assert.notEmpty(voiceProviders, "this array [voiceProviders] must not be empty: it must contain at least 1 element");
		
		for (char ch : ansAry) {
			VoiceProvider voiceProvider = voiceProviders[RandomNumberUtils.nextInt(voiceProviders.length)];
			samples.add(voiceProvider.getVocalization(ch));
		}
		if (ArrayUtils.isNotEmpty(noiseRenderers)) {
			NoiseRenderer noiseRenderer = noiseRenderers[RandomNumberUtils.nextInt(noiseRenderers.length)];
			return noiseRenderer.addNoise(samples);
		}

        return Mixer.append(samples);
	}

	public VoiceProvider[] getVoiceProviders() {
		return voiceProviders;
	}

	public void setVoiceProviders(VoiceProvider[] voiceProviders) {
		this.voiceProviders = voiceProviders;
	}

	public NoiseRenderer[] getNoiseRenderers() {
		return noiseRenderers;
	}

	public void setNoiseRenderers(NoiseRenderer[] noiseRenderers) {
		this.noiseRenderers = noiseRenderers;
	}

}
