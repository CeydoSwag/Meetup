package com.emptyirony.meetup;

import com.emptyirony.meetup.listener.PlayerListener;
import com.emptyirony.meetup.object.Game;
import com.emptyirony.meetup.object.Kit;
import com.emptyirony.meetup.util.BorderUtil;
import com.emptyirony.meetup.util.SitUtil;
import lombok.Getter;
import net.minecraft.server.v1_8_R3.BiomeBase;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

@Getter
public final class KuRo extends JavaPlugin {

    public KuRo(){
        this.timer = new Timer();
    }

    @Getter
    private static KuRo ins;
    private Timer timer;
    private Game game;
    private SitUtil sit;

    @Override
    public void onEnable() {
        ins = this;
        registerGame();
        registerListener();
        sit = new SitUtil();


    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void installBiomes() {
        Field biomesField;
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

    private void registerGame() {
        game = new Game(this);
        installBiomes();

        World world = Bukkit.createWorld(new WorldCreator("UHC"));
        game.setWorld(world);

        new com.emptyirony.meetup.world.WorldCreator(game);

        FileConfiguration configuration = YamlConfiguration.loadConfiguration(this.getResource("kits.yml"));
        int size = configuration.getConfigurationSection("kits.").getKeys(false).size();
        for (int i = 1; i < size; i++) {
            List<ItemStack> armor = (List<ItemStack>) configuration.getList("kits." + i + ".armor");
            List<ItemStack> contents = (List<ItemStack>) configuration.getList("kits." + i + ".contents");
            Kit kit = new Kit(armor, contents);
            game.getKits().add(kit);
        }

        new BorderUtil(game.getWorld(), game.getBorder(), 6).start(false);

    }

    private void registerListener(){
        Arrays.asList(
                new PlayerListener()
        ).forEach(listener-> this.getServer().getPluginManager().registerEvents(listener,this));
    }
}
