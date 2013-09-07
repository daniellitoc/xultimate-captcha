package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;

import org.danielli.xultimate.captcha.image.Glyphs;

/**
 * @author mag
 * @Date 6 mars 2008
 */
public interface GlyphsVisitors {
	
	void visit(Glyphs glyphs, Rectangle2D backroundBounds);
	
}
