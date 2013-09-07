package org.danielli.xultimate.captcha.image.support;

import java.awt.Color;
import java.awt.Font;
import java.awt.image.BufferedImageOp;

import org.danielli.xultimate.captcha.image.BufferedImageRenderer;
import org.danielli.xultimate.captcha.image.background.UniColorBackgroundProvider;
import org.danielli.xultimate.captcha.image.decorator.GlyphsDecorator;
import org.danielli.xultimate.captcha.image.decorator.RandomLinesGlyphsDecorator;
import org.danielli.xultimate.captcha.image.question.DeformedComposedQuestionHandler;
import org.danielli.xultimate.captcha.image.question.GlyphsQuestionHandler;
import org.danielli.xultimate.captcha.image.renderer.BufferedImageOpsRenderer;
import org.danielli.xultimate.captcha.image.visitor.GlyphsVisitors;
import org.danielli.xultimate.captcha.image.visitor.OverlapGlyphsUsingShapeVisitor;
import org.danielli.xultimate.captcha.image.visitor.TranslateAllToRandomPointVisitor;
import org.danielli.xultimate.captcha.image.visitor.TranslateGlyphsVerticalRandomVisitor;
import org.springframework.beans.factory.FactoryBean;

import com.jhlabs.image.PinchFilter;
import com.jhlabs.math.ImageFunction2D;

public class TaobaoQuestionHandlerFactory implements FactoryBean<DeformedComposedQuestionHandler<String>> {

	@Override
	public DeformedComposedQuestionHandler<String> getObject() throws Exception {
		GlyphsQuestionHandler textQuestionHandler = new GlyphsQuestionHandler();
		textQuestionHandler.setColors(new Color[] { Color.BLUE });
		textQuestionHandler.setFonts(new Font[] { new Font("Arial", Font.ITALIC, 22), new Font("Caslon",Font.BOLD, 22) } );
		textQuestionHandler.setManageFontByCharacter(false);
		textQuestionHandler.setManageColorPerGlyph(false);
		textQuestionHandler.setGlyphsVisitors(new GlyphsVisitors[] { new TranslateGlyphsVerticalRandomVisitor(1),  new OverlapGlyphsUsingShapeVisitor(3), new TranslateAllToRandomPointVisitor() });
		textQuestionHandler.setGlyphsDecorators(new GlyphsDecorator[] { new RandomLinesGlyphsDecorator(1, new Color[] { Color.BLUE, Color.WHITE, Color.WHITE }, 1, 3) });
        
        UniColorBackgroundProvider textBackground = new UniColorBackgroundProvider(new Color[] { Color.WHITE } );
        textQuestionHandler.setBackgroundProvider(textBackground);
        textQuestionHandler.setWidth(100);
        textQuestionHandler.setHeight(30);
        
        DeformedComposedQuestionHandler<String> finalQuestionHandler = new DeformedComposedQuestionHandler<>();
        finalQuestionHandler.setQuestionHandler(textQuestionHandler);
        
    	// 好像是线程不安全的，不知道有没有影响。
        PinchFilter pinch = new PinchFilter();
        pinch.setAmount(-.5f);
        pinch.setRadius(30);
        pinch.setAngle((float) (Math.PI/16));
        pinch.setCentreX(0.5f);
        pinch.setCentreY(-0.01f);
        pinch.setEdgeAction(ImageFunction2D.CLAMP);       
        
        PinchFilter pinch2 = new PinchFilter();
        pinch2.setAmount(-.6f);
        pinch2.setRadius(30);
        pinch2.setAngle((float) (Math.PI/16));
        pinch2.setCentreX(0.3f);
        pinch2.setCentreY(1.01f);
        pinch2.setEdgeAction(ImageFunction2D.CLAMP);

        PinchFilter pinch3 = new PinchFilter();
        pinch3.setAmount(-.6f);
        pinch3.setRadius(30);
        pinch3.setAngle((float) (Math.PI/16));
        pinch3.setCentreX(0.8f);
        pinch3.setCentreY(-0.01f);
        pinch3.setEdgeAction(ImageFunction2D.CLAMP);
       
        BufferedImageRenderer imageRenderer = new BufferedImageOpsRenderer(new BufferedImageOp[] { pinch, pinch2, pinch3 });
		
        finalQuestionHandler.setBufferedImageRenderers(new BufferedImageRenderer[] { imageRenderer });
        finalQuestionHandler.setAddBorder(true);
        return finalQuestionHandler;
	}

	@Override
	public Class<?> getObjectType() {
		return DeformedComposedQuestionHandler.class;
	}

	@Override
	public boolean isSingleton() {
		return true;
	}

}
