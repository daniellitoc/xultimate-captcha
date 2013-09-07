package org.danielli.xultimate.captcha.audio;

public interface VoiceProvider {
	Sample getVocalization(char letter);
}
