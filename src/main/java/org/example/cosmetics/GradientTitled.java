package org.example.cosmetics;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GradientTitled extends TitledBorder {
    private final Color startColor;
    private final Color endColor;
    private final Image borderImage;
    private final Insets insets;
    private final int xG,yG,widthG,heightG;

    public GradientTitled(String title, Color startColor, Color endColor,String imagePath, Insets insetsG,int x,int y,int width, int height,double scaleY){
        super(title);
        this.setTitleFont(new Font("SansSerif", Font.BOLD, (int) (12*scaleY)));
        this.startColor = startColor;
        this.endColor = endColor;
        this.borderImage = new ImageIcon(imagePath).getImage();
        this.insets = insetsG != null ? insetsG : new Insets(10, 10, 10, 10);
        this.xG=x;this.yG=y;this.widthG=width;this.heightG=height;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        // Cast to Graphics2D
        Graphics2D g2d = (Graphics2D) g;

//        super.paintBorder(c, g, x, y, width, height);
        if (borderImage!=null) {
            g2d.drawImage(borderImage, x-this.xG, y, width+this.widthG, height, null);
        }

        // Enable anti-aliasing for smoother text
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Get the title font and calculate its position
        Font font = getTitleFont();
        if (font == null) {
            font = c.getFont();
        }
        g2d.setFont(font);

        FontMetrics metrics = g2d.getFontMetrics(font);
        int stringWidth = metrics.stringWidth(getTitle());
        int ascent = metrics.getAscent();

        // Calculate the position of the title
        int titleX = x + (width - stringWidth) / 2; // Centered horizontally
        int titleY = y + ascent;                   // Slightly below the top border

        // Create a gradient paint for the title
        GradientPaint gradient = new GradientPaint(
                titleX, titleY - ascent, startColor,  // Start position and color
                titleX + stringWidth, titleY - ascent, endColor // End position and color
        );
        g2d.setPaint(gradient);

        // Draw the title
        g2d.drawString(getTitle(), titleX, titleY);
    }
}
