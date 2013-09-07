package org.danielli.xultimate.captcha.image.background;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;

import org.danielli.xultimate.captcha.image.BackgroundProvider;

/**
 * 椭圆的背景提供器。
 * 
 * @author Daniel Li
 * @since 6 December 2012
 */
public class EllipseBackgroundProvider implements BackgroundProvider {

	@Override
	public BufferedImage createBackground(int width, int height) {
		BufferedImage result = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		
        Graphics2D graphics = (Graphics2D) result.getGraphics();
        BasicStroke bs = new BasicStroke(2.0f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 2.0f, new float[] { 2.0f, 2.0f }, 0.0f);
        graphics.setStroke(bs);
        AlphaComposite ac = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.75f);
        graphics.setComposite(ac);
        graphics.translate(width * -1.0, 0.0);
        double delta = 5.0;
        double xt;
        for (xt = 0.0; xt < (2.0 * width); xt += delta) {
            Arc2D arc = new Arc2D.Double(0, 0, width, height, 0.0, 360.0, Arc2D.OPEN);
            graphics.draw(arc);
            graphics.translate(delta, 0.0);
        }
        graphics.dispose();
        
        return result;
	}

}
