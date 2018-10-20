
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.graphics.GImage;
import acm.program.*;
import acm.util.ErrorException;

import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		// You fill this in
		initInteractors();
		database = new FacePamphletDatabase();
		canvas = new FacePamphletCanvas();
		add(canvas);
	}

	private void initInteractors() {
		nameText = new JTextField(TEXT_FIELD_SIZE);
		addProButton = new JButton("Add");
		delProButton = new JButton("Delete");
		lookupButton = new JButton("Lookup");
		statusText = new JTextField(TEXT_FIELD_SIZE);
		statusText.setActionCommand("Change Status");
		statusText.addActionListener(this);
		changeStatButton = new JButton("Change Status");
		pictureText = new JTextField(TEXT_FIELD_SIZE);
		pictureText.addActionListener(this);
		pictureText.setActionCommand("Change Picture");
		changePicButton = new JButton("Change Picture");
		friendText = new JTextField(TEXT_FIELD_SIZE);
		friendText.addActionListener(this);
		friendText.setActionCommand("Add Friend");
		addFriendButton = new JButton("Add Friend");
		add(nameText, NORTH);
		add(addProButton, NORTH);
		add(delProButton, NORTH);
		add(lookupButton, NORTH);
		add(statusText, WEST);
		add(changeStatButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(pictureText, WEST);
		add(changePicButton, WEST);
		add(new JLabel(EMPTY_LABEL_TEXT), WEST);
		add(friendText, WEST);
		add(addFriendButton, WEST);
		addActionListeners();
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		// You fill this in as well as add any additional methods
		String name = nameText.getText();
		String status = statusText.getText();
		String picture = pictureText.getText();
		String friend = friendText.getText();
		switch (e.getActionCommand()) {
		case "Add":
			if (!name.isEmpty()) {
				String msg = "A profile with the name " + name + " already exists";
				if (database.containsProfile(name)) {

					// Use exist profile as current profile.
					currentProfile = database.getProfile(name);
				} else {
					FacePamphletProfile profile = new FacePamphletProfile(name);
					database.addProfile(profile);

					// Use new profile as current profile.
					currentProfile = profile;
					canvas.displayProfile(profile);
					msg = "New profile created";
				}
				canvas.showMessage(msg);
			}
			break;

		case "Delete":
			if (!name.isEmpty()) {
				String msg = "Profile of " + name + " deleted";
				if (database.containsProfile(name)) {
					database.deleteProfile(name);
					// Delete current profile.
					currentProfile = null;
				} else {
					msg = "Delete: profile with " + name + " does not exist.";
				}
				canvas.showMessage(msg);
			}
			break;

		case "Lookup":
			if (!name.isEmpty()) {
				String msg = "Displaying " + name;
				if (database.containsProfile(name)) {
					// Use lookuped profile as current profile.
					currentProfile = database.getProfile(name);
					canvas.displayProfile(currentProfile);
				} else {
					msg = "A profile with the name " + name + " does not exist";

					// Delete current profile.
					currentProfile = null;
				}
				canvas.showMessage(msg);
			}
			break;

		case "Change Status":
			if (!status.isEmpty()) {
				String msg = "Please select a profile to change status";
				if (currentProfile != null) {
					currentProfile.setStatus(status);
					println("Change Status: " + status);
					msg = "Status updated to " + status;
				}
				canvas.showMessage(msg);
			}
			break;

		case "Change Picture":
			if (!picture.isEmpty()) {
				String msg = "Unable to open image file: " + picture;
				if (currentProfile != null) {
					GImage image = null;
					try {
						image = new GImage(picture);
					} catch (ErrorException ex) {
						println("Selected file is invalid");
					}
					if (image != null) {
						currentProfile.setImage(image);
						msg = "Picture updated";
					}
				}
				canvas.showMessage(msg);
			}
			break;

		case "Add Friend":
			if (!friend.isEmpty()) {
				if (currentProfile != null) {
					if (database.containsProfile(friend)) {
						if (currentProfile.addFriend(friend)) {
							database.getProfile(friend).addFriend(currentProfile.getName());
							canvas.showMessage(friend + " added as a friend");
						} else {
							canvas.showMessage(currentProfile.getName() + " already has " + friend + " as a friend.");
						}
					} else {
						canvas.showMessage(friend + " does not exist.");
					}
				} else {
					noCurrentProfile("friend");
				}
			}
			break;
		}

	}

	private void noCurrentProfile(String arg) {
		println("Select a profile to change the " + arg + " of.");
	}

	/* Interactors */
	private JTextField nameText;
	private JButton addProButton;
	private JButton delProButton;
	private JButton lookupButton;
	private JTextField statusText;
	private JButton changeStatButton;
	private JTextField pictureText;
	private JButton changePicButton;
	private JTextField friendText;
	private JButton addFriendButton;

	/* Database instance variables */
	private FacePamphletDatabase database;

	private FacePamphletProfile currentProfile;

	private FacePamphletCanvas canvas;

}
