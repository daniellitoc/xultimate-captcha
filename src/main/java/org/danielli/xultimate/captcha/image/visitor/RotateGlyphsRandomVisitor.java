package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.captcha.image.GlyphsVisitors;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * @author mag
 * @Date 6 mars 2008
 */
public class RotateGlyphsRandomVisitor implements GlyphsVisitors {

	private double maxAngle = Math.PI / 8;

	public RotateGlyphsRandomVisitor() {
	}

	public RotateGlyphsRandomVisitor(double maxAngle) {
		this.maxAngle = maxAngle;
	}

	@Override
	public void visit(Glyphs gv, Rectangle2D backroundBounds) {
		for (int i = 0; i < gv.size(); i++) {
			gv.rotate(i, maxAngle * RandomNumberUtils.nextGaussian());
		}
	}
}