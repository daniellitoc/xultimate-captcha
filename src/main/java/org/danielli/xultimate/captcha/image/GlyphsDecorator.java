package org.danielli.xultimate.captcha.image;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface GlyphsDecorator {
	
	void decorate(Graphics2D g2, Glyphs glyphs, BufferedImage backround);
	
}
