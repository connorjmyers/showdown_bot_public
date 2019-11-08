package Utility.Facebook;

import java.awt.image.BufferedImage;

/**
 * FacebookPost of facebook post. Includes text and/or an image/anything else I might add.
 *
 * @author Connor Myers
 * @version 1.0 12/04/2019
 */
public class FacebookPost {

    private String textContent = "";
    private BufferedImage imageContent;

    /**
     * Returns the String contained in FacebookPost
     * @return
     * The String contained in FacebookPost
     */
    public String getTextContent(){
        return this.textContent;
    }

    /**
     * Returns the BufferedImage contained in FacebookPost
     * @return
     * The BufferedImage contained in FacebookPost
     */
    public BufferedImage getImageContent(){
        return this.imageContent;
    }

    /**
     * Adds more text to the FacebookPost's textContent
     * @param textToAdd
     * The String you want to add to textContent
     */
    public void addToText(String textToAdd) {
        this.textContent = this.textContent + "\n" + textToAdd + "\n";
    }

    public void setTextContent(String text){
        this.textContent = text;
    }

    /**
     * Set the imageContent of FacebookPost object to specific BufferedImage
     * @param image
     * BufferedImage that you want to post.
     */
    public void setImageContent(BufferedImage image){
        this.imageContent = image;
    }
}

