package com.emptyirony.meetup.util;

/**
 * 2 * @Author: EmptyIrony
 * 3 * @Date: 2020/1/16 21:15
 * 4
 */

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class SitUtil {
    private HashMap<Player, Integer> sitted;

    public SitUtil() {
        this.sitted = new HashMap<>();
    }

    public void sitPlayer(Player p) {
        Location l = p.getLocation();
        EntityHorse horse = new EntityHorse(((CraftWorld) l.getWorld()).getHandle());
        EntityBat pig = new EntityBat(((CraftWorld) l.getWorld()).getHandle());
        pig.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
        pig.setInvisible(true);
        pig.setHealth(6.0f);
        horse.setLocation(l.getX(), l.getY(), l.getZ(), 0.0f, 0.0f);
        horse.setInvisible(true);
        PacketPlayOutSpawnEntityLiving packet = new PacketPlayOutSpawnEntityLiving(pig);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
        this.sitted.put(p, pig.getId());
        PacketPlayOutAttachEntity sit = new PacketPlayOutAttachEntity(0, ((CraftPlayer) p).getHandle(), pig);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sit);
        p.setAllowFlight(true);
    }

    public void unsitPlayer(Player p) {
        if (this.sitted.get(p) != null) {
            PacketPlayOutEntityDestroy packet = new PacketPlayOutEntityDestroy(this.sitted.get(p));
            ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
            p.setAllowFlight(false);
        }
    }
}
