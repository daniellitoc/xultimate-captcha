package org.danielli.xultimate.captcha.image.decorator;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.Glyphs;

public interface GlyphsDecorator {
	
	void decorate(Graphics2D g2, Glyphs glyphs, BufferedImage backround);
	
}
