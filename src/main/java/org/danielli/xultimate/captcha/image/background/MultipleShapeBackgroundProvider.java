package org.danielli.xultimate.captcha.image.background;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundProvider;

/**
 * 多图形的背景提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class MultipleShapeBackgroundProvider implements BackgroundProvider {

	private Color firstEllipseColor;
	private Color secondEllipseColor;
	private int spaceBetweenLine;
	private int spaceBetweenCircle;
	private int ellipseHeight;
	private int ellipseWidth;
	
	private Color firstRectangleColor;
	private Color secondRectangleColor;
	private int rectangleWidth;
	
	public MultipleShapeBackgroundProvider() {
		this(new Color(210, 210, 210), new Color(0, 0, 0), 10, 10, 8, 8, new Color(210, 210, 210), new Color(0, 0, 0), 3);
	}
	
	public MultipleShapeBackgroundProvider(Color firstEllipseColor, Color secondEllipseColor, Integer spaceBetweenLine, Integer spaceBetweenCircle, Integer ellipseHeight, Integer ellipseWidth, Color firstRectangleColor, Color secondRectangleColor, Integer rectangleWidth) {
		this.firstEllipseColor = firstEllipseColor;
		this.secondEllipseColor = secondEllipseColor;
		this.spaceBetweenLine = spaceBetweenLine;
		this.spaceBetweenCircle = spaceBetweenCircle;
		this.ellipseHeight = ellipseHeight;
		this.ellipseWidth = ellipseWidth;
		this.firstRectangleColor = firstRectangleColor;
		this.secondRectangleColor = secondRectangleColor;
		this.rectangleWidth = rectangleWidth;
	}
	
	
	@Override
	public BufferedImage createBackground(int width, int height) {
		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setBackground(Color.white);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,  RenderingHints.VALUE_ANTIALIAS_ON);

        for (int variableOnWidth = 0; variableOnWidth < width; variableOnWidth = variableOnWidth + spaceBetweenLine)
        {
            for (int variableOnHeight = 0; variableOnHeight < height; variableOnHeight = variableOnHeight  + spaceBetweenCircle) {
                Ellipse2D e2 = new Ellipse2D.Double(variableOnWidth, variableOnHeight, this.ellipseWidth, this.ellipseHeight);
                GradientPaint gp = new GradientPaint(0, this.ellipseHeight, firstEllipseColor, this.ellipseWidth, 0, secondEllipseColor, true);
                g2.setPaint(gp);
                g2.fill(e2);
            }
            GradientPaint gp2 = new GradientPaint(0, height,  firstRectangleColor, this.rectangleWidth, 0, secondRectangleColor, true);
            g2.setPaint(gp2);
            Rectangle2D r2 = new Rectangle2D.Double(variableOnWidth, 0, this.rectangleWidth, height);
            g2.fill(r2);
        }
        g2.dispose();
        
        return img;
	}
}
