package com.emptyirony.meetup.util;

import com.emptyirony.meetup.KuRo;
import lombok.Data;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.scheduler.BukkitRunnable;

@Data
public class BorderUtil {

    private World world;
    private int radius;
    private int highestBlock;

    private boolean force;

    @SuppressWarnings("unused")
    private BorderUtil() {
    }

    public BorderUtil(final World world, final int radius, final int highestBlock) {
        this.world = world;
        this.radius = radius;
        this.highestBlock = highestBlock;
    }

    public BorderUtil force() {
        force = true;
        return this;
    }

    @SuppressWarnings("deprecation")
    private void setBorderBlock(final int x, final int z) {

        Block block = world.getHighestBlockAt(x, z).getRelative(BlockFace.DOWN);

        while (isContainAbleMaterials(block.getType()) && block.getY() > 1) {
            block = block.getRelative(BlockFace.DOWN);
        }

        final Location location = block.getLocation();

        for (int i = 0; i < highestBlock; i++) {
            location.add(0, 1, 0).getBlock().setType(Material.BEDROCK);
        }
    }

    public void start(final boolean callEvent) {

        final int plus = force ? 5000 : 500;

        new BukkitRunnable() {

            private int counter = -radius;
            private boolean phase1 = false;
            private boolean phase2 = false;
            private boolean phase3 = false;

            @Override
            public void run() {

                if (!phase1) {
                    final int maxCounter = counter + plus;
                    final int x = -radius;
                    for (int z = counter; z <= radius && counter <= maxCounter; z++, counter++) {
                        setBorderBlock(x, z);
                    }

                    if (counter >= radius) {
                        counter = -radius;
                        phase1 = true;
                    }
                    return;
                }

                if (!phase2) {
                    final int maxCounter = counter + plus;
                    final int x = radius;
                    for (int z = counter; z <= radius && counter <= maxCounter; z++, counter++) {
                        setBorderBlock(x, z);
                    }

                    if (counter >= radius) {
                        counter = -radius;
                        phase2 = true;
                    }
                    return;
                }

                if (!phase3) {
                    final int maxCounter = counter + plus;
                    final int z = -radius;
                    for (int x = counter; x <= radius && counter <= maxCounter; x++, counter++) {
                        if (x == radius || x == -radius) {
                            continue;
                        }
                        setBorderBlock(x, z);
                    }

                    if (counter >= radius) {
                        counter = -radius;
                        phase3 = true;
                    }
                    return;
                }


                final int maxCounter = counter + plus;
                final int z = radius;
                for (int x = counter; x <= radius && counter <= maxCounter; x++, counter++) {
                    if (x == radius || x == -radius) {
                        continue;
                    }
                    setBorderBlock(x, z);
                }

                if (counter >= radius) {

                    this.cancel();
                }
            }
        }.runTaskTimer(KuRo.getIns(), 0L, force ? 1L : 5L);
    }

    public boolean isContainAbleMaterials(final Material material) {
        return (material == Material.AIR || material == Material.LOG || material == Material.LOG_2 || material == Material.LEAVES || material == Material.LEAVES_2 || material == Material.STATIONARY_WATER);
    }

}
