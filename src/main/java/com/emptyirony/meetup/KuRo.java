package com.emptyirony.meetup;

import com.emptyirony.meetup.listener.PlayerListener;
import com.emptyirony.meetup.object.Game;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;

@Getter
public final class KuRo extends JavaPlugin {

    @Getter
    private static KuRo ins;

    private Game game;

    @Override
    public void onEnable() {
        ins = this;
        registerGame();
        registerListener();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void installBiomes() {
        Field biomesField = null;
        try {
            biomesField = BiomeBase.class.getDeclaredField("biomes");
            biomesField.setAccessible(true);
            if (biomesField.get(null) instanceof BiomeBase[]) {
                BiomeBase[] biomes = (BiomeBase[]) biomesField.get(null);
                Arrays.fill(biomes, BiomeBase.PLAINS);

                biomesField.set(null, biomes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void registerGame(){
        game = new Game();
        installBiomes();

        World world = Bukkit.createWorld(new WorldCreator("UHC"));
        game.setWorld(world);
    }

    private void registerListener(){
        Arrays.asList(
                new PlayerListener()
        ).forEach(listener->{
            this.getServer().getPluginManager().registerEvents(listener,this);
        });
    }
}
