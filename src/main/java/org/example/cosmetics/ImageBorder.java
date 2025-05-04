package org.example.cosmetics;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class ImageBorder implements Border {
    private final Image borderImage;
    private final boolean opaque;
    private final Insets inset;
    private final  int width,height;

    public ImageBorder(String imagePath,boolean visabel,Insets inserts,int widthG,int heightG) {
        this.borderImage = new ImageIcon(imagePath).getImage();
        this.opaque = visabel;
        this.inset=inserts;
        this.width =widthG;
        this.height=heightG;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        g.drawImage(borderImage, x-2, y, width+this.width, height+this.height, null);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return inset;
    }

    @Override
    public boolean isBorderOpaque() {
        return opaque;
    }
}
