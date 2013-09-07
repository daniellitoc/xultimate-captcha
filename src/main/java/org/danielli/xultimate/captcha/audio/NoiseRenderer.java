package org.danielli.xultimate.captcha.audio;

import java.util.List;


public interface NoiseRenderer {
	Sample addNoise(List<Sample> target);
}
