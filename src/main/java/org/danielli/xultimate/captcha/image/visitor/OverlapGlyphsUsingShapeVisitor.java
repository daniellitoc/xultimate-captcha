package org.danielli.xultimate.captcha.image.visitor;

import java.awt.geom.Rectangle2D;
import java.awt.geom.Area;

import org.danielli.xultimate.captcha.image.Glyphs;

/**
 * @author mag
 * @Date 7 mars 2008
 */
public class OverlapGlyphsUsingShapeVisitor extends OverlapGlyphsVisitor {
	private double overlapPixels;

	public OverlapGlyphsUsingShapeVisitor(double overlapPixels) {
		super(0);
		this.overlapPixels = overlapPixels;
	}

	@Override
	public void visit(Glyphs gv, Rectangle2D backroundBounds) {
		// super.decorate(gv, backroundBounds);

		// evaluate overlapPixel

		for (int i = 1; i < gv.size(); i++) {
			// System.out.println("I "+i);
			// first position the shapes near the preceding one
			gv.translate(i, getSidingPosition(gv, i), 0);

			// then evaluate if there is a real overlap
			if (mayGlyphsOverlapAtIndex(gv, i)) {
				// ok overlap is possible, try to reach
				double realPossibleOverlap = getMaximumPossibleOverlap(gv, i);
				double currentOverlapWidth = intersectAndGetOverlapWidth(gv, i);
				double currentOverlapStatus = currentOverlapWidth
						- realPossibleOverlap;
				double bestReacheadOverlapStatus = Math
						.abs(currentOverlapStatus);
				// System.out.println("Real possible "+realPossibleOverlap);
				boolean stillOk = true;
				// System.out.println("bestReacheadOverlapStatus : "+bestReacheadOverlapStatus);
				while (Math.abs(currentOverlapStatus) >= overlapPixels / 10
						&& stillOk) {
					double step = currentOverlapStatus / 2;
					// System.out.println("translated "+ step);
					gv.translate(i, step, 0);
					currentOverlapWidth = intersectAndGetOverlapWidth(gv, i);
					currentOverlapStatus = currentOverlapWidth
							- realPossibleOverlap;
					// System.out.println("bestReacheadOverlapStatus : "+bestReacheadOverlapStatus);
					// System.out.println("currentOverlapStatus : "+currentOverlapStatus);

					// we already reach the best overlap
					if (Math.abs(currentOverlapStatus) >= bestReacheadOverlapStatus
							&& (currentOverlapWidth != 0 || gv.getMaxX(i - 1)
									- gv.getMinX(i) > gv.getBoundsWidth(i - 1))) {
						// System.out.println("BREAK");
						// tranlsate back
						if (currentOverlapWidth == 0) {
							// back to siding
							gv.translate(i, getSidingPosition(gv, i), 0);
							// System.out.println("no overlap");
						} else {
							// back on step
							gv.translate(i, -step, 0);
							// System.out.println("best reached");
						}
						// System.out.println("currentOverlapStatus : "+(intersectAndGetOverlapWidth(gv,
						// i)-realPossibleOverlap));
						stillOk = false;
					}
					bestReacheadOverlapStatus = Math.min(
							Math.abs(currentOverlapStatus),
							bestReacheadOverlapStatus);
				}
				// System.out.println("bestReacheadOverlapStatus : "+bestReacheadOverlapStatus);
			} else {
				System.out.println("NOT POSSIBLE");
			}
		}
	}

	private double getSidingPosition(Glyphs gv, int i) {
		return gv.getBoundsX(i - 1) + gv.getBoundsWidth(i - 1)
				- gv.getBoundsX(i) - Math.abs(gv.getRSB(i - 1))
				- Math.abs(gv.getLSB(i));
	}

	private double intersectAndGetOverlapWidth(Glyphs gv, int i) {
		return getIntesection(gv, i).getBounds2D().getWidth();
	}

	private Area getIntesection(Glyphs gv, int index) {
		Area intersect = new Area(gv.getOutline(index - 1));
		intersect.intersect(new Area(gv.getOutline(index)));
		return intersect;
	}

	private double getMaximumPossibleOverlap(Glyphs gv, int index) {
		return Math.min(Math.min(overlapPixels, gv.getBoundsWidth(index)),
				gv.getBoundsWidth(index - 1));
	}

	private boolean mayGlyphsOverlapAtIndex(Glyphs gv, int index) {
		return gv.getMinY(index - 1) > gv.getMaxY(index)
				|| gv.getMinY(index) > gv.getMaxY(index - 1);
	}
}
