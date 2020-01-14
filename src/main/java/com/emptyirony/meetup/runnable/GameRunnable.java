package com.emptyirony.meetup.runnable;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.object.Game;

import java.util.TimerTask;

/**
 * @Author CziSKY
 * @Since 2020-01-14 20:48
 */
public class GameRunnable extends TimerTask {

    private KuRo plugin;

    public GameRunnable(KuRo plugin) {
        this.plugin = plugin;
    }

    @Override
    public void run(){
        Game game = this.plugin.getGame();
        switch (game.getStatus()){
            case WAITING:{
                if (!game.canStart()){
                    break;
                }
                game.startCountdown();
            }
            case STARTING:{
                game.loopCountdown();
            }
        }
    }

}
