package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;

import org.danielli.xultimate.captcha.image.Glyphs;

/**
 * @author mag
 * @Date 6 mars 2008
 */
public class OverlapGlyphsVisitor implements GlyphsVisitors {

	private double overlapPixels = 0;

	public OverlapGlyphsVisitor(double overlapPixels) {
		this.overlapPixels = overlapPixels;
	}

	@Override
	public void visit(Glyphs gv, Rectangle2D backroundBounds) {

		for (int i = 1; i < gv.size(); i++) {
			double tx = gv.getBoundsX(i - 1) + gv.getBoundsWidth(i - 1)
					- gv.getBoundsX(i) - Math.abs(gv.getRSB(i - 1))
					- Math.abs(gv.getLSB(i)) - overlapPixels;
			double ty = 0;
			// System.out.println(gv.getRSB(i-1)+" ; " +gv.getLSB(i-1));
			// System.out.println(gv.getRSB(i)+" ; " +gv.getLSB(i));
			// System.out.println("tx="+tx+",ty="+ty);
			gv.translate(i, tx, ty);
		}
	}
}