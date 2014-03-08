/*
 * JCaptcha, the open source java framework for captcha definition and integration
 * Copyright (c)  2007 jcaptcha.net. All Rights Reserved.
 * See the LICENSE.txt file distributed with this package.
 */
package org.danielli.xultimate.captcha.image.renderer;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;

import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.ArrayUtils;

/**
 * Use an array of java.awt.image.ImageFilter to deform an image
 *
 * @author <a href="mailto:mag@jcaptcha.net">Marc-Antoine Garrigue </a>
 * @version 1.0
 */
public class ImageFiltersRenderer implements BufferedImageRenderer {
	
    /**
     * Filters to defrom the image
     */
	protected ImageFilter[] imageFilters;
	 
	    /**
	     * Constructor with an array of ImageFilter
	     *
	     * @param filters Filters to defrom the image
	     */
	 public ImageFiltersRenderer(ImageFilter[] imageFilters) {
		 this.imageFilters = imageFilters;
	 }

	@Override
	public BufferedImage render(BufferedImage sourceBufferedImage) {
		if (ArrayUtils.isNotEmpty(imageFilters)) {
			 BufferedImage clone = new BufferedImage(sourceBufferedImage.getWidth(), sourceBufferedImage.getHeight(), sourceBufferedImage.getType());
	         //clone.getGraphics().drawImage(image, 0, 0, null, null);
	         FilteredImageSource filtered;
	         Image temp=null;

	         for (int i = 0; i < imageFilters.length; i++) {
	             ImageFilter filter = imageFilters[i];
	             filtered = new FilteredImageSource(sourceBufferedImage.getSource(), filter);
	             temp = Toolkit.getDefaultToolkit().createImage(filtered);
	         }
	         clone.getGraphics().drawImage(temp, 0, 0, null);
	         clone.getGraphics().dispose();
	         return clone;
		}
		return sourceBufferedImage;
	}
}
