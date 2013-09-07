package org.danielli.xultimate.captcha.audio.noise;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.List;

import org.danielli.xultimate.captcha.audio.Mixer;
import org.danielli.xultimate.captcha.audio.NoiseRenderer;
import org.danielli.xultimate.captcha.audio.Sample;
import org.danielli.xultimate.util.Assert;
import org.danielli.xultimate.util.io.FileUtils;
import org.danielli.xultimate.util.math.RandomNumberUtils;

public class DefaultNoiseRenderer implements NoiseRenderer {
	
	private File[] files;
	
	@Override
	public Sample addNoise(List<Sample> target) {
        Sample appended = Mixer.append(target);
		Assert.notEmpty(files, "this array [files] must not be empty: it must contain at least 1 element");
		File file = files[RandomNumberUtils.nextInt(files.length)];
		try {
			byte[] bytes = FileUtils.readFileToByteArray(file);
			// Decrease the volume of the noise to make sure the voices can be heard
	        return Mixer.mix(appended, 1.0, new Sample(new ByteArrayInputStream(bytes)), 0.6);
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

}
