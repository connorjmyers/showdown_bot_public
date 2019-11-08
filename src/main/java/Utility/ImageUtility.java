package Utility;

import Showdown.Players.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

public class ImageUtility {

    /**
     * Converts BufferedImage to equivalent byte[] format
     * Reference: https://stackoverflow.com/questions/15414259/java-bufferedimage-to-byte-array-and-back
     *
     * @param image The BufferedImage to be converted to byte[]
     * @return image in byte[] format
     * @author Nikolay Kuznetsov
     */
    public static byte[] BufferedImagetoBytes(BufferedImage image) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", baos);
        } catch (IOException e) {
            System.out.println(e);
        }
        return baos.toByteArray();
    }

    /**
     * Grabs image from directory
     *
     * @return A BufferedImage of the Player
     */
    public static BufferedImage setImage(String directory) {

        try {
            return ImageIO.read(new File(directory));
        } catch (IOException e) {
            System.out.println("Can't find file: " + directory);
        }
        return null;
    }

    /**
     * Creates the image of a round (i.e. two Player's faces next to each other)
     *
     * @param alivePlayer The Player whose turn it is.
     * @param deadPlayer The Player who is being dealt with (e.g. being fought)
     */
    public static BufferedImage createRoundImage(Player alivePlayer, Player deadPlayer) {

        BufferedImage alivePlayerImage = scale(ImageUtility.setImage(alivePlayer.getImageDirectory()), 500, 500);
        BufferedImage deadPlayerImage = scale(ImageUtility.setImage(deadPlayer.getImageDirectory()), 500, 500);
        drawDead(deadPlayerImage);

        BufferedImage roundImage = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        roundImage = scale(roundImage, 1000, 500);
        Graphics2D displayVisual = roundImage.createGraphics();
        displayVisual.setBackground(Color.white);

        displayVisual.drawImage(alivePlayerImage, 0, 0, null);
        displayVisual.drawImage(deadPlayerImage, 500, 0, null);
        return roundImage;
    }

    /**
     * @param src The image you want to scale.
     * @param w   The width you want the image scaled to.
     * @param h   The height you want the image scaled to.
     * @return Returns image with new dimensions (i.e. with a width of w and a height of h)
     * @author rlbaker
     * <p>
     * Notes:
     * I DID NOT WRITE THIS CODE!!!
     * Sourced from https://stackoverflow.com/questions/9417356/bufferedimage-resize
     */
    private static BufferedImage scale(BufferedImage src, int w, int h) {
        BufferedImage img =
                new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
        int x, y;
        int ww = src.getWidth();
        int hh = src.getHeight();
        int[] ys = new int[h];
        for (y = 0; y < h; y++)
            ys[y] = y * hh / h;
        for (x = 0; x < w; x++) {
            int newX = x * ww / w;
            for (y = 0; y < h; y++) {
                int col = src.getRGB(newX, ys[y]);
                img.setRGB(x, y, col);
            }
        }
        return img;
    }

    /**
     * Draws a cross over the image of a Player, indicating they died in the round.
     *
     * @param image Image of the Player that died.
     * @return BufferedImage of Player with a red cross over it.
     */
    private static BufferedImage drawDead(BufferedImage image) {
        Graphics2D displayVisual = image.createGraphics();
        displayVisual.setStroke(new BasicStroke(20)); //makes line THICC
        displayVisual.setColor(Color.red); //makes line red
        displayVisual.drawLine(0, 0, 500, 500);
        displayVisual.drawLine(500, 0, 0, 500);
        return image;
    }

    /**
     * creates a collage of the input images.
     * I.e. puts images next to each other on 500px x 500px BufferedImage
     *
     * @param left  The image on the left of the collage.
     * @param right The image on the right of the collage.
     * @return The BufferedImage collage.
     */
    public static BufferedImage createDualImage(BufferedImage left, BufferedImage right) {
        left = scale(left, 500, 500);
        right = scale(right, 500, 500);
        BufferedImage dual = new BufferedImage(1000, 500, BufferedImage.TYPE_INT_RGB);
        Graphics2D displayVisual = dual.createGraphics();
        displayVisual.drawImage(left, 0, 0, null);
        displayVisual.drawImage(right, 500, 0, null);
        return dual;
    }

    /**
     * Creates image of winner. "Adds the quote '*~*a tear shed for those lost'*~*"
     *
     * @param image The BufferedImage of the Winner
     * @return Image of player with aforementioned message written over it.
     * @author MEMORYNOTFOUND, Jessica Brown, Connor Myers
     * Note:
     * MEMORYNOTFOUND's code: https://memorynotfound.com/adding-text-image-watermark-image-java/
     * Jessica Brown's code: https://stackoverflow.com/questions/7679459/thick-border-of-drawn-string
     */
    public static BufferedImage winningImage(BufferedImage image) {

        String message1 = "  *~*a tear shed for ";
        String message2 = "  those lost*~*";
        BufferedImage watermarked = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_RGB);
        Font font = new Font("Arial Black", Font.BOLD, 25);

        //initializes necessary graphic properties
        Graphics2D w = (Graphics2D) watermarked.getGraphics();
        w.drawImage(image, 0, 0, null);
        w.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));

        //Makes text not look like ass
        w.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS,
                RenderingHints.VALUE_FRACTIONALMETRICS_ON);

        //Where the text will go on the image.
        int x = 0;
        int y = image.getHeight() - image.getHeight() / 3;

        //creating outline and adds text overlay to the image
        w.setColor(Color.black);
        w.drawString(message1, x, y + 1);
        w.drawString(message1, x, y - 1);
        w.drawString(message1, x + 1, y + 1);
        w.drawString(message1, x + 1, y - 1);
        w.setColor(Color.white);
        w.drawString(message1, x, y);

        //Changing base coordinates for second part of message
        x = x + 20;
        y = y + 20;

        //Second part of message
        w.setColor(Color.black);
        w.drawString(message2, x, y + 1);
        w.drawString(message2, x, y - 1);
        w.drawString(message2, x + 1, y + 1);
        w.drawString(message2, x + 1, y - 1);
        w.setColor(Color.white);
        w.drawString(message2, x, y);

        w.dispose();

        return watermarked;
    }
}
