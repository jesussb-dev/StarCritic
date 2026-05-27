package com.starcritic.dam_proyect.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public final class ImageUtils {

    private ImageUtils() {
    }

    /**
     * Scales an image into a TYPE_INT_ARGB BufferedImage using Graphics2D.
     * Avoids AreaAveragingScaleFilter (Image.SCALE_SMOOTH), which throws
     * ClassCastException ([I cannot be cast to [B) on some PNG color models.
     */
    public static BufferedImage scale(Image src, int w, int h) {
        BufferedImage scaled = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = scaled.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.setRenderingHint(RenderingHints.KEY_RENDERING,
                RenderingHints.VALUE_RENDER_QUALITY);
        g.drawImage(src, 0, 0, w, h, null);
        g.dispose();
        return scaled;
    }
}
