package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.captcha.image.GlyphsVisitors;

/**
 * @author mag
 * @Date 6 mars 2008
 */
public class MoveAllGlyphsToOriginVisitor implements GlyphsVisitors {

	@Override
	public void visit(Glyphs glyphs, Rectangle2D backroundBounds) {
		for (int i = 0; i < glyphs.size(); i++) {

			double tx = -glyphs.getX(i);
			double ty = -glyphs.getY(i);
			glyphs.translate(i, tx, ty);
		}
	}
}