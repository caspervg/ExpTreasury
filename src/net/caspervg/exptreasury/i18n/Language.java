package net.caspervg.exptreasury.i18n;

import java.util.ResourceBundle;

public class Language {

    private static ResourceBundle bundle;
    static {
        bundle = ResourceBundle.getBundle("i18n/messages");
    }

    public static ResourceBundle getBundle() {
        return bundle;
    }
}
