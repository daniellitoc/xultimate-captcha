package org.danielli.xultimate.captcha.image;

import java.awt.Shape;
import java.awt.font.GlyphMetrics;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;

/**
 * Container class for a list of GlyphVector
 * 
 * @author mag
 * @Date 6 mars 2008
 */
public class Glyphs {

	// List<GlyphAbsolutePositionAndRenderContext> vectors = new
	// ArrayList<GlyphAbsolutePositionAndRenderContext>();

	List<GlyphVector> vectors = new ArrayList<GlyphVector>();

	public void addGlyphVector(GlyphVector glyph) {
		this.vectors.add(glyph);

		// new GlyphAbsolutePositionAndRenderContext(glyph,frc,new
		// Point2D.Float(0,0)));
	}

	public int size() {
		return vectors.size();
	}

	public GlyphVector get(int index) {
		return this.vectors.get(index);
	}

	public double getBoundsX(int index) {
		return getBounds(index).getX();
	}

	public double getBoundsY(int index) {
		return getBounds(index).getY();
	}

	public double getBoundsWidth(int index) {
		return getBounds(index).getWidth();
	}

	public double getBoundsHeight(int index) {
		return getBounds(index).getHeight();
	}

	public double getX(int index) {
		return get(index).getGlyphPosition(0).getX();
	}

	public double getY(int index) {
		return get(index).getGlyphPosition(0).getY();
	}

	public Shape getOutline(int index) {
		return get(index).getOutline();
	}

	public double getBoundsX() {
		return getBounds().getX();
	}

	public double getBoundsY() {
		return getBounds().getY();
	}

	public double getBoundsWidth() {
		return getBounds().getWidth();
	}

	public double getBoundsHeight() {
		return getBounds().getHeight();
	}

	public double getMaxX(int index) {
		return getBounds(index).getMaxX();
	}

	public double getMaxY(int index) {
		return getBounds(index).getMaxY();
	}

	public double getMinX(int index) {
		return getBounds(index).getMinX();
	}

	public double getMinY(int index) {
		return getBounds(index).getMinX();
	}

	public GlyphVector getGlyphVector(int index) {
		return this.vectors.get(index);
	}

	public Rectangle2D getBounds(int index) {
		return this.vectors.get(index).getVisualBounds();
	}

	public Rectangle2D getBounds() {
		Rectangle2D bounds = size() > 0 ? getBounds(0)
				: new Rectangle2D.Double(0, 0, 0, 0);
		for (int i = 1; i < size(); i++) {
			bounds = bounds.createUnion(getBounds(i));
		}
		return bounds;
	}

	public GlyphMetrics getMetrics(int index) {
		return get(index).getGlyphMetrics(0);
	}

	public double getLSB(int index) {
		return getMetrics(index).getLSB();
	}

	public double getRSB(int index) {
		return getMetrics(index).getRSB();
	}

	public double getAdvance(int index) {
		return getMetrics(index).getAdvance();
	}

	public double getInternalWidth(int index) {
		return getAdvance(index) - getRSB(index) - getLSB(index);
	}

	public Rectangle2D getInternalBounds(int index) {
		return getMetrics(index).getBounds2D();
	}

	public double getInternalBoundsX(int index) {
		return getInternalBounds(index).getX();
	}

	public double getInternalBoundsY(int index) {
		return getInternalBounds(index).getY();
	}

	public double getInternalBoundsWidth(int index) {
		return getInternalBounds(index).getWidth();
	}

	public double getInternalBoundsHeigth(int index) {
		return getInternalBounds(index).getHeight();
	}

	public double getAdvanceX(int index) {
		return getMetrics(index).getAdvanceX();
	}

	public double getAdvanceY(int index) {
		return getMetrics(index).getAdvanceY();
	}

	public double getMaxHeight() {
		double max = 0;
		for (int i = 1; i < size(); i++) {
			max = Math.max(getBoundsHeight(i), max);
		}
		return max;
	}

	public double getMaxWidth() {
		double max = 0;
		for (int i = 1; i < size(); i++) {
			max = Math.max(getBoundsWidth(i), max);
		}
		return max;
	}

	public void translate(double x, double y) {
		for (int i = 0; i < size(); i++) {
			translate(i, x, y);
		}
	}

	public void translate(int index, double x, double y) {
		setPosition(index, x + getX(index), y + getY(index));
	}

	public void setPosition(int index, double x, double y) {
		vectors.get(index).setGlyphPosition(0, new Point2D.Double(x, y));
	}

	public void addAffineTransform(AffineTransform at) {
		for (int i = 0; i < size(); i++) {
			addAffineTransform(i, at);
		}
	}

	public void addAffineTransform(int index, AffineTransform at) {
		AffineTransform t = vectors.get(index).getGlyphTransform(0);
		if (t == null) {
			t = at;
		} else {
			t.concatenate(at);
		}
		vectors.get(index).setGlyphTransform(0, t);
	}

	public void rotate(int index, double angle) {
		get(index).setGlyphTransform(
				0,
				AffineTransform.getRotateInstance(angle, getBoundsX(index)
						+ getBoundsWidth(index) / 2, getBoundsY(index)
						+ getBoundsHeight(index) / 2));
	}

	@SuppressWarnings("unused")
	public String toString() {
		final String R = "\n";
		final String RS = "\n\t";
		final String RSS = "\n\t\t";

		StringBuffer buf = new StringBuffer();
		buf.append("{Glyphs=");
		for (int i = 0; i < size(); i++) {
			buf.append(RS);
			buf.append("{GlyphVector=" + i + " : ");

			for (int j = 0; j < this.get(i).getNumGlyphs(); j++) {

				buf.append("Glyph=" + j);

				buf.append("; Bounds=");
				buf.append(this.get(i).getGlyphVisualBounds(j).getBounds2D());
				buf.append("; Font=");
				buf.append(this.get(i).getFont());
			}

			buf.append("}");

		}
		buf.append(R);
		buf.append("Bounds : ");
		buf.append(this.getBounds());
		buf.append("}");
		return buf.toString();
	}
}
