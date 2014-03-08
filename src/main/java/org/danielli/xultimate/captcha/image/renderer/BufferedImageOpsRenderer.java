package org.danielli.xultimate.captcha.image.renderer;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;

import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.util.ArrayUtils;

/**
 * @author mag
 * @Date 5 mars 2008
 */
public class BufferedImageOpsRenderer implements BufferedImageRenderer {
	
	protected BufferedImageOp[] bufferedImageOps;
	
	public BufferedImageOpsRenderer(BufferedImageOp[] bufferedImageOps) {
		this.bufferedImageOps = bufferedImageOps;
	}

	@Override
	public BufferedImage render(BufferedImage sourceBufferedImage) {
		if (ArrayUtils.isNotEmpty(bufferedImageOps)) { 
			for (BufferedImageOp bufferedImageOp : bufferedImageOps) {
				sourceBufferedImage = bufferedImageOp.filter(sourceBufferedImage, null);
			}
		}

		return sourceBufferedImage;
	}
}
