package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;

import org.danielli.xultimate.captcha.image.Glyphs;
import org.danielli.xultimate.util.math.RandomNumberUtils;

/**
 * 
 * @author mag
 * @Date 6 mars 2008
 */
public class TranslateAllToRandomPointVisitor implements GlyphsVisitors {
	private double horizontalMargins = 0;
	private double verticalMargins = 0;

	public TranslateAllToRandomPointVisitor() {
	}

	/**
	 * 
	 * @param horizontalmargins
	 *            the horizontal margin, default 0
	 * @param verticalmargins
	 *            the vertical margin, default 0
	 */
	public TranslateAllToRandomPointVisitor(double horizontalmargins,
			double verticalmargins) {
		this.horizontalMargins = horizontalmargins;
		this.verticalMargins = verticalmargins;
	}

	@Override
	public void visit(Glyphs glyphs, Rectangle2D backroundBounds) {
		double xRange = backroundBounds.getWidth() - glyphs.getBoundsWidth()
				- horizontalMargins;
		double yRange = backroundBounds.getHeight() - glyphs.getBoundsHeight()
				- verticalMargins;
		double tx = xRange * RandomNumberUtils.nextDouble()
				- glyphs.getBoundsX() + horizontalMargins / 2;
		double ty = yRange * RandomNumberUtils.nextDouble()
				- glyphs.getBoundsY() + verticalMargins / 2;
		glyphs.translate(tx, ty);
	}
}