package com.emptyirony.meetup.world;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.object.Game;
import com.emptyirony.meetup.util.BorderUtil;
import org.bukkit.*;
import org.bukkit.command.CommandSender;

/**
 * @Author CziSKY
 * @Since 2020-01-14 20:42
 */
public class WorldCreator {

    public WorldCreator(Game game) {
        this.handleWorld(game);
    }

    private void handleWorld(Game game) {
        KuRo.getIns().getGame().setBorder(150);
        World world = Bukkit.createWorld(new org.bukkit.WorldCreator(game.getWorld().getName()).environment(World.Environment.NORMAL).type(WorldType.NORMAL));
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("naturalRegeneration", "false");
        world.setDifficulty(Difficulty.NORMAL);
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb shape square");
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb " + world.getName() + " set " + game.getBorder() + " 0 0");
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb " + world.getName() + " fill " + game.getBorder());
        Bukkit.getServer().dispatchCommand((CommandSender)Bukkit.getConsoleSender(), "wb fill confirm");
        BorderUtil.buildWalls(game.getBorder(), Material.BEDROCK, 5, world);
    }

}
