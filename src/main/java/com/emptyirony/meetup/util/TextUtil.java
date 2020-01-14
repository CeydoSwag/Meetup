package com.emptyirony.meetup.util;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

/**
 * @Author CziSKY
 * @Since 2020-01-14 20:59
 */
public class TextUtil {
    public static String secondsToString(int pTime) {
        return String.format("%02d:%02d", pTime / 60, pTime % 60);
    }

    public static void sendMessage(String message) {
        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
