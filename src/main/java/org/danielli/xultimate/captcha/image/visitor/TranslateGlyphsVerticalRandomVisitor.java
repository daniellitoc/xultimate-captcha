package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * @author mag
 * @Date 7 mars 2008
 */
public class TranslateGlyphsVerticalRandomVisitor implements GlyphsVisitors {

	private double verticalRange = 1;

	public TranslateGlyphsVerticalRandomVisitor(double verticalRange) {
		this.verticalRange = verticalRange;
	}

	@Override
	public void visit(Glyphs gv, Rectangle2D backroundBounds) {

		for (int i = 0; i < gv.size(); i++) {
			double tx = 0;
			double ty = verticalRange * RandomNumberUtils.nextGaussian();
			// System.out.println("tx="+tx+",ty="+ty);
			gv.translate(i, tx, ty);
		}
	}
}
