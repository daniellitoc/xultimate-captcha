package org.danielli.xultimate.captcha.audio.noise;

import java.io.File;
import java.util.List;

import javax.sound.sampled.AudioSystem;

import org.danielli.xultimate.captcha.audio.Mixer;
import org.danielli.xultimate.captcha.audio.NoiseRenderer;
import org.danielli.xultimate.captcha.audio.Sample;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.math.RandomNumberUtils;
import org.springframework.beans.factory.InitializingBean;

/**
 * 声音渲染器默认实现。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class DefaultNoiseRenderer implements NoiseRenderer, InitializingBean {
	
	protected File[] files;
	
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notEmpty(files, "this array [files] must not be empty: it must contain at least 1 element");
	}
	
	@Override
	public Sample addNoise(List<Sample> target) {
		File file = files[RandomNumberUtils.nextInt(files.length)];
        Sample appended = Mixer.append(target);
		try {
			// Decrease the volume of the noise to make sure the voices can be heard
	        return Mixer.mix(appended, 1.0, new Sample(AudioSystem.getAudioInputStream(file)), 0.6);
		} catch (Exception e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}

	public void setFiles(File[] files) {
		this.files = files;
	}
}
