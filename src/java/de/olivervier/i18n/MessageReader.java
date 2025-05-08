package de.olivervier.i18n;

import java.util.ResourceBundle;

public class MessageReader {
    
    public static ResourceBundle messages = ResourceBundle.getBundle("resources/i18n/ApplicationMessages");

    public static String getMessage(String key) {
        return messages.getString(key);
    }

}