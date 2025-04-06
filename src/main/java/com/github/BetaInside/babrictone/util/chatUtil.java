package com.github.BetaInside.babrictone.util;

import static com.github.BetaInside.babrictone.babrictone.mc;

public class chatUtil {

    public static void addMessage(String message) {
        mc.ingameGUI.addChatMessage(fontColorUtil.DARK_PURPLE + "[" + fontColorUtil.LIGHT_PURPLE + "Babrictone" + fontColorUtil.DARK_PURPLE + "] " + fontColorUtil.GRAY + message);
    }

}
