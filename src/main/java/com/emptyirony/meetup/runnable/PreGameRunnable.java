package com.emptyirony.meetup.runnable;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import strafe.games.miku.util.CC;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/1/11 20:31
 * 4
 */
public class PreGameRunnable extends BukkitRunnable {
    private int time = 120;

    @Override
    public void run() {
        if (time == 60 || time == 30 || time == 15 || time == 10 || time <=5 && time>1){
            Bukkit.broadcastMessage(CC.translate("&eThe game will be start in &b"+time+"&e seconds"));
            Bukkit.getOnlinePlayers().forEach(player->{
                player.playSound(player.getLocation(), Sound.NOTE_PLING,1,1);
            });
        }

        if (time==0){
            new TeleportRunnable(this.game).runTaskTimer(UHC.getIns(),20,10);
            cancel();
        }


    }
}
