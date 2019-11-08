package Managers;

import Utility.Facebook.FacebookPost;
import Utility.ImageUtility;
import Utility.TextUtility;
import com.restfb.*;
import com.restfb.exception.FacebookException;
import com.restfb.types.FacebookType;
import com.restfb.types.Page;
import com.restfb.types.User;

import java.io.IOException;

/**
 * Manages connecting and posting to Facebook
 *
 * @author Connor Myers
 * @version 1.0 19/09/2019
 */

public class FacebookManager {

    //Declaring fbClient, allows us to post to facebook. Initialised in constructor.
    private FacebookClient fbClient;
    private String pageAccessToken = "";
    private String pageID = "";

    private static final FacebookManager FACEBOOK_MANAGER = new FacebookManager();

    private FacebookManager() {
        initialiseAuthority();
        createClient();
    }

    public static FacebookManager getFacebookManager() {
        return FACEBOOK_MANAGER;
    }

    /**
     * Setting the pageAccessToken and pageID from .txt file
     */

    private void initialiseAuthority() {
        try {
            pageAccessToken = TextUtility.readFile("Database/Logins/pageAccessToken.txt").get(0);
            pageID = TextUtility.readFile("Database/Logins/pageID.txt").get(0);
        } catch (IOException E) {
            System.out.println("File does not exist.");
        }
    }

    /**
     * Initialises the fbClient
     */
    private void createClient() {

        User myuser = null;
        Page mypage = null;

        try {
            fbClient = new DefaultFacebookClient(pageAccessToken, Version.VERSION_2_8);
            myuser = fbClient.fetchObject("me", User.class);
            mypage = fbClient.fetchObject(pageID, Page.class);
        } catch (FacebookException ex) {
            ex.printStackTrace(System.err);
        }
    }

    public FacebookClient getClient() {
        return this.fbClient;
    }

    public String getPageID() {
        return pageID;
    }

    public String getPageAccessToken() {
        return pageAccessToken;
    }

    /**
     * Posts a message (text only) onto Facebook page
     * @param content The FacebookPost object containing the message you want to post to Facebook
     */
    public void postText(FacebookPost content) {
        fbClient.publish(this.getPageID() + "/feed",
                FacebookType.class, Parameter.with("message", content.getTextContent()));
    }

    /**
     * Posts a message and photo onto Facebook page
     * @param postContent FacebookPost object containing the content you want to post
     */
    public void postImageWithText(FacebookPost postContent) {

        byte[] imageAsBytes = ImageUtility.BufferedImagetoBytes(postContent.getImageContent());

        FacebookType photo = fbClient.publish(this.getPageID() + "/photos",
                FacebookType.class,
                BinaryAttachment.with("photo",
                        imageAsBytes,
                        "image/jpg"),
                Parameter.with("message",
                        postContent.getTextContent()));
    }
}

