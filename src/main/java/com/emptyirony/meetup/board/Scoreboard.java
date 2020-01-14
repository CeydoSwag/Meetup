package com.emptyirony.meetup.board;

import com.emptyirony.meetup.KuRo;
import com.emptyirony.meetup.object.GameStatus;
import org.apache.commons.lang.StringEscapeUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import strafe.games.miku.util.board.assemble.AssembleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author CziSKY
 * @Since 2020-01-14 20:17
 */
public class Scoreboard implements AssembleAdapter {
    public String getTitle(Player player) {
        return ChatColor.translateAlternateColorCodes('&', "&6&lStrafeKits &7" + StringEscapeUtils.unescapeJava("\\u2503") + " &fMeetUp");
    }

    @Override
    public List<String> getLines(Player player) {
        List<String> defaultNullBoard = new ArrayList<String>();
        defaultNullBoard.add("");
        switch (KuRo.getIns().getGame().getStatus()){
            case WAITING: {
                return getNeedMorePlayersBoard();
            }
            case STARTING:{
                return getStartingBoard();
            }
            case TELEPORTING:{
                return getTeleportingBoard();
            }
            case GAMING:{
                return getFightingBoard(player);
            }
            case END:{
                return getEndBoard();
            }
        }
        return defaultNullBoard;
    }
    private List<String> getNeedMorePlayersBoard() {
        List<String> strings = new ArrayList<>();
        strings.add("&7&m--------------------------");
        strings.add("&fPlayers: &6" + Bukkit.getOnlinePlayers().size() + "/" + KuRo.getIns().getGame().getMinmumPlayer());
        strings.add("&fNeed more players to start.");
        strings.add("&7&m--------------------------");
        return strings;
    }

    private List<String> getStartingBoard() {
        List<String> strings = new ArrayList<>();
        strings.add("&7&m--------------------------");
        strings.add("&fPlayers: &6" + Bukkit.getOnlinePlayers());
        strings.add("&fStarting in: &6" + "NONE");
        strings.add("&7&m--------------------------");
        return strings;
    }

    private List<String> getTeleportingBoard() {
        List<String> strings = new ArrayList<>();
        strings.add("&7&m--------------------------");
        strings.add("&fWe're teleporting players.");
        strings.add("&fPlease take your hands off the keyboard!");
        strings.add("&7&m--------------------------");
        return strings;
    }

    private List<String> getFightingBoard(Player player) {
        List<String> strings = new ArrayList<>();
        strings.add("&7&m--------------------------");
        strings.add("&fGame Time: &6" + "COUNTDOWN");
        strings.add("&fRemaining: &6" + KuRo.getIns().getGame().getAlivePlayer());
        strings.add("&fKills: &6" + "");
        strings.add("&fBorders: &6" + KuRo.getIns().getGame().getBorder());
        strings.add("");
        strings.add("&fSpectators: &6" + (Bukkit.getOnlinePlayers().size() - KuRo.getIns().getGame().getAlivePlayer().size()));
        strings.add("&7&m--------------------------");
        return strings;
    }

    private List<String> getEndBoard() {
        List<String> strings = new ArrayList<>();
        strings.add("&7&m--------------------------");
        strings.add("&eWinner: &f" + KuRo.getIns().getGame().getWinner());
        strings.add("&6Congratulations!");
        strings.add("&7&m--------------------------");
        return strings;
    }
}
