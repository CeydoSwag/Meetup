package com.emptyirony.meetup.object;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.manager.HotBar;
import com.emptyirony.meetup.util.SitUtil;
import com.emptyirony.meetup.util.TextUtil;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.util.CC;
import strafe.games.miku.util.PlayerUtil;

import java.util.*;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2019/12/31 23:55
 * 4
 */
@Data
public class Game {

    private KuRo kuro;
    private GameStatus status;
    private World world;
    private int border;
    private List<UUID> alivePlayer;
    private List<Scenarios> enableScenarios;
    private int minmumPlayer;
    private Player winner;
    private int startCountdown;
    private List<Kit> kits;

    public Game(KuRo kuRo) {
        this.kuro = kuRo;
        this.status = GameStatus.WAITING;
        this.border = 210;
        this.alivePlayer = new ArrayList<>();
        this.enableScenarios = new ArrayList<>();
        this.minmumPlayer = 3;
        this.winner = null;
        kits = new ArrayList<>();
    }

    public boolean canStart() {
        return Bukkit.getOnlinePlayers().size() >= this.minmumPlayer;
    }

    public int shrinkBorder() {
        if (border <= 25) {
            return 25;
        }

        switch (border) {
            case 210:
                border = 150;
                break;
            case 150:
                border = 100;
                break;
            case 100:
                border = 50;
                break;
            case 50:
                border = 25;
                break;
        }
        return border;
    }

    public void onDeath(Player player) {
        if (player == null) {
            return;
        }

        if (!alivePlayer.contains(player.getUniqueId())) {
            return;
        }

        alivePlayer.remove(player.getUniqueId());
        player.setFakingDeath(true);
        player.spigot().setCollidesWithEntities(true);
        PlayerUtil.reset(player);
        HotBar.giveSpectatorHotBar(player);

    }

    public void scatter() {
        Queue<Player> playerQueue = new LinkedList<>(Bukkit.getOnlinePlayers());

        new BukkitRunnable() {
            @Override
            public void run() {
                Random random = new Random();
                int x;
                int z;
                int y;
                SitUtil sit = KuRo.getIns().getSit();
                Player player = playerQueue.poll();
                if (player == null) {
                    return;
                }

                if (random.nextBoolean()) {
                    x = random.nextInt(border - 5);
                } else {
                    x = -(random.nextInt(border - 5));
                }
                if (random.nextBoolean()) {
                    z = random.nextInt(border - 5);
                } else {
                    z = -(random.nextInt(border - 5));
                }

                y = world.getHighestBlockAt(x, z).getY() + 2;

                player.teleport(new Location(world, x, y, z));
                sit.sitPlayer(player);

                if (playerQueue.size() == 0) {
                    cancel();
                    addKit();
                }

            }
        }.runTaskTimer(KuRo.getIns(), 20, 5);
    }

    public void startCountdown() {
        this.startCountdown = 60;
        this.status = GameStatus.STARTING;
    }

    public void loopCountdown() {
        if (!this.canStart()) {
            this.status = GameStatus.WAITING;
            this.startCountdown = 60;
            Bukkit.getOnlinePlayers().forEach(player -> {
                PlayerUtil.reset(player);
                player.updateInventory();
            });
            TextUtil.sendMessage("&eTimer canceled. need more players!");
            return;
        }
        if (this.startCountdown > 0) {
            if (this.startCountdown == 60 || this.startCountdown == 30 || this.startCountdown == 15 || this.startCountdown == 10 || this.startCountdown <= 5 && this.startCountdown > 1) {
                Bukkit.broadcastMessage(CC.translate("&eThe game will be start in &b" + this.startCountdown + "&e seconds"));
                Bukkit.getOnlinePlayers().forEach(player -> player.playSound(player.getLocation(), Sound.NOTE_PLING, 1, 1));
            }
            --this.startCountdown;
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.setLevel(this.startCountdown);
                player.setExp(this.startCountdown * 1000 / 60000.0f);
            });
        } else {
            Bukkit.getOnlinePlayers().forEach(player -> {
                alivePlayer.add(player.getUniqueId());
            });

            scatter();
        }
    }

    public void addKit() {
        for (Player player : Bukkit.getOnlinePlayers()) {


        }
    }

}
