package com.emptyirony.meetup.object;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.manager.HotBar;
import lombok.Data;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import strafe.games.miku.util.PlayerUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    public Game(KuRo kuRo){
        this.kuro = kuRo;
        this.status = GameStatus.WAITING;
        this.border = 210;
        this.alivePlayer = new ArrayList<>();
        this.enableScenarios = new ArrayList<>();
    }

    public int shrinkBorder(){
        if (border <= 25){
            return 25;
        }

        switch (border){
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

    public void onDeath(Player player){
        if (player == null){
            return;
        }

        if (!alivePlayer.contains(player.getUniqueId())){
            return;
        }

        alivePlayer.remove(player.getUniqueId());
        player.setFakingDeath(true);
        player.spigot().setCollidesWithEntities(true);
        PlayerUtil.reset(player);
        HotBar.giveSpectatorHotBar(player);

    }

    public void tryEnd(){
        if (alivePlayer.size() > 1){
            return;
        }

        if (alivePlayer.size() == 0){
            return;
        }

        Player player = Bukkit.getPlayer(alivePlayer.get(0));
        if (player == null){



        }
    }

    public void start(){

        Bukkit

    }

}
