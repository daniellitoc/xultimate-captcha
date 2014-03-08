package org.danielli.xultimate.captcha.audio.support;

import java.util.ArrayList;
import java.util.List;

import org.danielli.xultimate.captcha.CaptchaException;
import org.danielli.xultimate.captcha.QuestionHandler;
import org.danielli.xultimate.captcha.audio.Mixer;
import org.danielli.xultimate.captcha.audio.NoiseRenderer;
import org.danielli.xultimate.captcha.audio.Sample;
import org.danielli.xultimate.captcha.audio.VoiceGenerator;
import org.danielli.xultimate.captcha.config.Question;
import org.danielli.xultimate.util.ArrayUtils;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.math.RandomNumberUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 问题处理器的默认实现。用于将问题类型为String处理为Sample。
 *
 * @author Daniel Li
 * @since 6 December 2012
 */
public class AudioQuestionHandler implements QuestionHandler<String, Sample>, InitializingBean {
	
	protected VoiceGenerator[] voiceGenerators;
	protected NoiseRenderer[] noiseRenderers;	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(voiceGenerators, "this array [voiceProviders] must not be empty: it must contain at least 1 element");
	}
	
	@Override
	public Sample getResult(Question<String> question) throws CaptchaException {
		try {
			char[] ansAry = question.getValue().toCharArray();
			List<Sample> samples = new ArrayList<Sample>(ansAry.length);
			for (char ch : ansAry) {
				VoiceGenerator voiceGenerator = voiceGenerators[RandomNumberUtils.nextInt(voiceGenerators.length)];
				samples.add(voiceGenerator.getVocalization(ch));
			}
			if (ArrayUtils.isNotEmpty(noiseRenderers)) {
				NoiseRenderer noiseRenderer = noiseRenderers[RandomNumberUtils.nextInt(noiseRenderers.length)];
				return noiseRenderer.addNoise(samples);
			}
	        return Mixer.append(samples);
		} catch (Exception e) {
			throw new CaptchaException(e.getMessage(), e);
		}
	}
	

	public void setNoiseRenderers(NoiseRenderer[] noiseRenderers) {
		this.noiseRenderers = noiseRenderers;
	}

	public void setVoiceGenerators(VoiceGenerator[] voiceGenerators) {
		this.voiceGenerators = voiceGenerators;
	}

}
