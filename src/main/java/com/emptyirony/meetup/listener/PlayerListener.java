package com.emptyirony.meetup.listener;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.manager.HotBar;
import com.emptyirony.meetup.object.Game;
import com.emptyirony.meetup.object.GameProfile;
import com.emptyirony.meetup.object.GameStatus;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.*;
import org.bukkit.event.weather.WeatherChangeEvent;
import strafe.games.miku.profile.Profile;
import strafe.games.miku.util.CC;
import strafe.games.miku.util.PlayerUtil;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/1/11 19:56
 * 4
 */
public class PlayerListener implements Listener {
    private Game game;

    public PlayerListener(){
        game = KuRo.getIns().getGame();
    }

    @EventHandler
    public void onLogin(AsyncPlayerPreLoginEvent event){
        new GameProfile(event.getUniqueId());
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event){
        Player player = event.getPlayer();
        event.setJoinMessage(null);
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            PlayerUtil.reset(player);
            HotBar.giveMainHotBar(player);
            String username = Profile.getByUuid(player.getUniqueId()).getColoredUsername();
            event.setJoinMessage(CC.translate(username+"&e joined the game(&a"+ Bukkit.getOnlinePlayers().size()+"&7/&c30&e)"));
        }else {
            if (!game.getAlivePlayer().contains(player.getUniqueId())){
                PlayerUtil.reset(player);
                HotBar.giveSpectatorHotBar(player);
            }
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onFoodLevelChange(FoodLevelChangeEvent event){
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDamage(EntityDamageEvent event){
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBreak(BlockBreakEvent event){
        if (event.getPlayer().getGameMode()== GameMode.CREATIVE){
            return;
        }
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onWeatherChange(WeatherChangeEvent event){
        if (event.toWeatherState()){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteract(PlayerInteractEvent event){
        if (event.getPlayer().getGameMode()== GameMode.CREATIVE){
            return;
        }
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onPickUp(PlayerPickupItemEvent event){
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onDrop(PlayerDropItemEvent event){
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlace(BlockPlaceEvent event){
        if (event.getPlayer().getGameMode()== GameMode.CREATIVE){
            return;
        }
        if (game.getStatus() == GameStatus.WAITING || game.getStatus() == GameStatus.STARTING){
            event.setCancelled(true);
        }
    }


}
