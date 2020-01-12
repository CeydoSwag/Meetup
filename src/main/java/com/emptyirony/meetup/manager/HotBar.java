package com.emptyirony.meetup.manager;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import strafe.games.miku.util.ItemBuilder;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/1/11 19:28
 * 4
 */
public class HotBar {

    public static void giveSpectatorHotBar(Player player){
        ItemStack compass = new ItemBuilder(Material.COMPASS)
                .name("&cPlayer Tracker")
                .lore("&7Right-Click to open players tracker")
                .build();

        ItemStack diamond = new ItemBuilder(Material.DIAMOND_SWORD)
                .name("&aScreenShot")
                .build();

        ItemStack bed = new ItemBuilder(Material.BED_BLOCK)
                .name("&cReturn to lobby")
                .lore("&7Right-Click to return lobby")
                .build();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setContents(null);

        player.getInventory().setItem(0,compass);
        player.getInventory().setItem(4,diamond);
        player.getInventory().setItem(8,bed);

    }

    public static void giveMainHotBar(Player player){
        ItemStack book = new ItemBuilder(Material.BOOK)
                .name("&bKit Edit")
                .lore("&7Right-Click edit your kit")
                .build();

        ItemStack vote = new ItemBuilder(Material.PAINTING)
                .name("&aVote Scenarios")
                .build();

        ItemStack bed = new ItemBuilder(Material.BED_BLOCK)
                .name("&cReturn to lobby")
                .lore("&7Right-Click to return lobby")
                .build();

        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.getInventory().setContents(null);

        player.getInventory().setItem(0,book);
        player.getInventory().setItem(4,vote);
        player.getInventory().setItem(8,bed);
    }
}
