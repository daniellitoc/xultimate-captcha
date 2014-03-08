package org.danielli.xultimate.captcha.image;

import java.awt.geom.Rectangle2D;

/**
 * @author mag
 * @Date 6 mars 2008
 */
public interface GlyphsVisitors {
	
	void visit(Glyphs glyphs, Rectangle2D backroundBounds);
	
}
