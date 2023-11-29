package com.simplepicpay.shared;
import java.util.ResourceBundle;

public final class MessageResources {
	private static ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");

	private MessageResources() {
		// Empty
	}

	public static String getText(String key) {
		return resourceBundle.getString(key);
	}
}
