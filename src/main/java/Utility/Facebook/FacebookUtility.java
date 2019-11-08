package Utility.Facebook;

import Managers.FacebookManager;
import Utility.ImageUtility;
import com.restfb.BinaryAttachment;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;

/**
 * Controls posting to Facebook via the FacebookManager
 */
public class FacebookUtility {

    private FacebookClient fbClient;
    private FacebookManager facebookManager;

    public FacebookUtility() {
        facebookManager = FacebookManager.getFacebookManager();
        fbClient = facebookManager.getClient();
    }

    /**
     * Posts a message (text only) onto Facebook page
     * @param content The FacebookPost object containing the message you want to post to Facebook
     */
    public void postText(FacebookPost content) {
        fbClient.publish(facebookManager.getPageID() + "/feed",
                FacebookType.class, Parameter.with("message", content.getTextContent()));
    }

    /**
     * Posts a message and photo onto Facebook page
     * @param postContent FacebookPost object containing the content you want to post
     */
    public void postImageWithText(FacebookPost postContent) {

        byte[] imageAsBytes = ImageUtility.BufferedImagetoBytes(postContent.getImageContent());

        FacebookType photo = fbClient.publish(facebookManager.getPageID() + "/photos",
                FacebookType.class,
                BinaryAttachment.with("photo",
                        imageAsBytes,
                        "image/jpg"),
                Parameter.with("message",
                        postContent.getTextContent()));
    }
}
