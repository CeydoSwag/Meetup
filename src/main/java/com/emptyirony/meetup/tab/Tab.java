package com.emptyirony.meetup.tab;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.util.TextUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import strafe.games.miku.util.CC;
import strafe.games.miku.util.tab.ZigguratAdapter;
import strafe.games.miku.util.tab.utils.BufferedTabObject;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author CziSKY
 * @Since 2020-01-14 21:05
 */
public class Tab implements ZigguratAdapter {

    @Override
    public Set<BufferedTabObject> getSlots(Player player) {
        switch (KuRo.getIns().getGame().getStatus()){
            case WAITING:
            case TELEPORTING: {
                return getWaitingTab(player);
            }
            case STARTING:{
                return getStartingTab(player);
            }
            case GAMING:{
                return getFightingTab(player);
            }
            case END:{
                return getEndTab(player);
            }
        }
        return null;
    }

    public String getFooter() {
        return null;
    }

    public String getHeader() {
        return null;
    }

    private Set<BufferedTabObject> getWaitingTab(Player player) {
        final Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(this.wrapObject(2, "&fOnline: " + Bukkit.getOnlinePlayers().size()));
        tabObjects.add(this.wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(this.wrapObject(42, "&fNeed Players: " + KuRo.getIns().getGame().getMinmumPlayer()));
        tabObjects.add(this.wrapObject(24, "&6&lYour Statistics"));
        tabObjects.add(this.wrapObject(25, "&cCOMING SOON"));
        this.addBasics(tabObjects);
        return tabObjects;
    }

    private Set<BufferedTabObject> getStartingTab(Player player) {
        final Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(this.wrapObject(2, "&fOnline: " + Bukkit.getOnlinePlayers().size()));
        tabObjects.add(this.wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(this.wrapObject(42, "&fStarts in: " + TextUtil.secondsToString(KuRo.getIns().getGame().getStartCountdown())));
        tabObjects.add(this.wrapObject(24, "&6&lYour Statistics"));
        tabObjects.add(this.wrapObject(25, "&cCOMING SOON"));
        this.addBasics(tabObjects);
        return tabObjects;
    }

    private Set<BufferedTabObject> getFightingTab(Player player) {
        final Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(this.wrapObject(2, "&fOnline: " + Bukkit.getOnlinePlayers().size()));
        tabObjects.add(this.wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(this.wrapObject(42, "&fRemaining: " + KuRo.getIns().getGame().getAlivePlayer()));
        tabObjects.add(this.wrapObject(24, "&6&lYour Statistics"));
        tabObjects.add(this.wrapObject(25, "&cCOMING SOON"));
        this.addBasics(tabObjects);
        return tabObjects;
    }

    private Set<BufferedTabObject> getEndTab(Player player) {
        final Set<BufferedTabObject> tabObjects = new HashSet<>();
        tabObjects.add(this.wrapObject(2, "&fOnline: " + Bukkit.getOnlinePlayers().size()));
        tabObjects.add(this.wrapObject(22, player.getPing(), "&fYour Connection"));
        tabObjects.add(this.wrapObject(42, "&fWinner: " + KuRo.getIns().getGame().getWinner().getName()));
        tabObjects.add(this.wrapObject(24, "&6&lYour Statistics"));
        tabObjects.add(this.wrapObject(25, "&cCOMING SOON"));
        this.addBasics(tabObjects);
        return tabObjects;
    }

    private BufferedTabObject wrapObject(int slot, String text) {
        return this.wrapObject(slot, 1, text);
    }

    private BufferedTabObject wrapObject(int slot, int ping, String text) {
        return new BufferedTabObject().slot(Integer.valueOf(slot)).ping(Integer.valueOf(ping)).text(CC.translate(text));
    }

    private void addBasics(final Set<BufferedTabObject> bufferedTabObjects) {
        bufferedTabObjects.add(this.wrapObject(1, "&6&lStrafeKits.Games"));
        bufferedTabObjects.add(this.wrapObject(21, "&6&lMeetUp"));
        bufferedTabObjects.add(this.wrapObject(41, "&6&lStrafeKits.Games"));
        bufferedTabObjects.add(this.wrapObject(67, "&eWarning!"));
        bufferedTabObjects.add(this.wrapObject(68, "&eWe recommend"));
        bufferedTabObjects.add(this.wrapObject(69, "&eto use 1.7.10"));
        bufferedTabObjects.add(this.wrapObject(70, "&eor Lunar Client"));
        bufferedTabObjects.add(this.wrapObject(71, "&efor better"));
        bufferedTabObjects.add(this.wrapObject(72, "&egaming experience"));
        bufferedTabObjects.add(this.wrapObject(73, "&eand more fluid pvp"));
    }

}
