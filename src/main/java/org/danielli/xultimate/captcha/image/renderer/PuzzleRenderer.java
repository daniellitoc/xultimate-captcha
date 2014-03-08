/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package org.danielli.xultimate.captcha.image.renderer;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;

import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.math.RandomNumberUtils;

import com.jhlabs.image.RotateFilter;

/**
 * Deformation where the image is divided in little squares, moved individualty in a random position. Each move is
 * really light, in order to let the captcha readble.
 *
 * @author Benoit Doumas
 */
public class PuzzleRenderer implements BufferedImageRenderer {

	/**
     * Number of colums to divide the image, max number if rows and colums are managed randomly
     */
	protected int colNum = 6;

    /**
     * Number of rows to divide the image, max number if rows and colums are managed randomly
     */
	protected int rowNum = 4;

    /**
     * Maximal angle of rotation for each square.
     */
	protected double maxAngleRotation = 0.3;
    
    public PuzzleRenderer(int colNum, int rowNum, double maxAngleRotation) {
        this.colNum = colNum;
        this.rowNum = rowNum;
        this.maxAngleRotation = maxAngleRotation;
    }
	
	@SuppressWarnings("unused")
	@Override
	public BufferedImage render(BufferedImage sourceBufferedImage) {
		int height = sourceBufferedImage.getHeight();
        int width = sourceBufferedImage.getWidth();

        int xd = width / colNum;
        int yd = height / rowNum;

        BufferedImage backround = new BufferedImage(width, height, sourceBufferedImage.getType());
        Graphics2D pie = (Graphics2D) backround.getGraphics();

        pie.setColor(Color.white);
        pie.setBackground(Color.white);
        pie.fillRect(0, 0, width, height);
        pie.dispose();

        Graphics2D g = (Graphics2D) sourceBufferedImage.getGraphics();
        g.setBackground(Color.white);

        BufferedImage smallPart = new BufferedImage(xd, yd, sourceBufferedImage.getType());
        Graphics2D gSmall = smallPart.createGraphics();
        FilteredImageSource filtered;

        for (int i = 0; i < colNum; i++) {
            for (int j = 0; j < rowNum; j++) {
                gSmall.drawImage(sourceBufferedImage, 0, 0, xd, yd, xd * i, yd * j, xd * i + xd, yd * j + yd,
                        null);

                RotateFilter filter = new RotateFilter((float)maxAngleRotation * RandomNumberUtils.nextFloat() * (RandomNumberUtils.nextBoolean() ? -1 : 1));

                smallPart.getGraphics().dispose();

                g.drawImage(smallPart, xd * i, yd * j, null, null);
            }
        }

        return sourceBufferedImage;
	}

}
