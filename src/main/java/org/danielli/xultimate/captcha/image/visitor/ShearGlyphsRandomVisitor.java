package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;
import java.awt.geom.AffineTransform;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.captcha.image.GlyphsVisitors;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * @author mag
 * @Date 6 mars 2008
 */
public class ShearGlyphsRandomVisitor implements GlyphsVisitors {

	private double maxShearX;
	private double maxShearY;

	public ShearGlyphsRandomVisitor(double maxShearX, double maxShearY) {
		this.maxShearX = maxShearX;
		this.maxShearY = maxShearY;
	}

	@Override
	public void visit(Glyphs gv, Rectangle2D backroundBounds) {

		for (int i = 0; i < gv.size(); i++) {
			AffineTransform af = new AffineTransform();
			af.setToShear(maxShearX * RandomNumberUtils.nextGaussian(),
					maxShearY * RandomNumberUtils.nextGaussian());
			gv.addAffineTransform(i, af);
		}
	}
}