
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {
		// You fill this in
		message = new GLabel("");
		add(message);
	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		// You fill this in

		remove(message);
		GLabel newMsg = new GLabel(msg);
		newMsg.setFont(MESSAGE_FONT);
		double x = (getWidth() - newMsg.getWidth()) / 2.0;
		double y = getHeight() - BOTTOM_MESSAGE_MARGIN;
		newMsg.setLocation(x, y);
		message = newMsg;
		add(message);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		removeAll();
		// You fill this in
		// Add name label to display
		GLabel nameLabel = new GLabel(profile.getName());
		GPoint nameLabelPt = new GPoint(LEFT_MARGIN, nameLabel.getAscent() + TOP_MARGIN);
		nameLabel.setFont(PROFILE_NAME_FONT);
		nameLabel.setColor(Color.BLUE);
		nameLabel.setLocation(nameLabelPt);
		add(nameLabel);

		// Add profile image to display
		GPoint imagePt = new GPoint(LEFT_MARGIN, nameLabel.getY() + IMAGE_MARGIN);
		// If no image just display a rectangle within a no image label.
		if (profile.getImage() == null) {
			GRect rect = new GRect(IMAGE_WIDTH, IMAGE_HEIGHT);
			rect.setLocation(imagePt);
			add(rect);
			GLabel imageLabel = new GLabel("No Image");
			imageLabel.setFont(PROFILE_IMAGE_FONT);
			double imageLabelX = (rect.getWidth() - imageLabel.getWidth()) / 2.0 + rect.getX();
			double imageLabelY = (rect.getHeight() - imageLabel.getAscent()) / 2.0 + imageLabel.getAscent()
					+ rect.getY();
			imageLabel.setLocation(imageLabelX, imageLabelY);
			add(imageLabel);
		} else {
			GImage image = profile.getImage();
			image.scale(IMAGE_WIDTH, IMAGE_HEIGHT);
			image.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			image.setLocation(imagePt);
			add(image);
		}

		// Add status label to display
		String statusStr = profile.getStatus().equals("") ? "No current status"
				: profile.getName() + " is " + profile.getStatus();
		GLabel statusLabel = new GLabel(statusStr);
		statusLabel.setFont(PROFILE_STATUS_FONT);
		GPoint statusPt = new GPoint(LEFT_MARGIN,
				IMAGE_HEIGHT + imagePt.getY() + STATUS_MARGIN + statusLabel.getAscent());
		statusLabel.setLocation(statusPt);
		add(statusLabel);

		// Add friends list to display
		GPoint friendListPt = new GPoint(getWidth() / 2.0, imagePt.getY());
		GLabel friendLabel = new GLabel("Friends: ");
		friendLabel.setLocation(friendListPt);
		friendLabel.setFont(PROFILE_FRIEND_LABEL_FONT);
		add(friendLabel);
		double y = friendListPt.getY();
		Iterator<String> it = profile.getFriends();
		while (it.hasNext()) {
			String friend = it.next();
			GLabel label = new GLabel(friend);
			y += label.getHeight();
			GPoint pt = new GPoint(friendListPt.getX(), y);
			label.setLocation(pt);
			add(label);
		}
	}

	private GLabel message;
}
