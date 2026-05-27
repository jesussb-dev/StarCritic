package com.starcritic.dam_proyect.util;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * Utilidades de manipulación de imágenes para los renderers de la UI.
 *
 * @author Jesús Santos Baquero
 */
public final class ImageUtils {

    private ImageUtils() {
    }

    /**
     * Escalar una imagen a las dimensiones indicadas aplicando interpolación
     * bilineal y máxima calidad de rendering.
     * @param src la imagen origen a escalar.
     * @param w el ancho destino en píxeles.
     * @param h el alto destino en píxeles.
     * @return una nueva {@link BufferedImage} ARGB con la imagen escalada.
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
