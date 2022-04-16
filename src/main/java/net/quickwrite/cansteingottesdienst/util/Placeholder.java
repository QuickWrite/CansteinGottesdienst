package net.quickwrite.cansteingottesdienst.util;

public class Placeholder {
    public static String replace(String message, String replacer, Object with) {
        return message.replace("%" + replacer + "%", with.toString());
    }
}
