package cz.larkyy.lfarmingrobots.robots.runnables;

import cz.larkyy.lfarmingrobots.robots.Robot;
import cz.larkyy.lfarmingrobots.robots.RobotStorage;
import org.bukkit.Bukkit;
import org.bukkit.CropState;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.Farmland;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class FieldRunnable extends BukkitRunnable {
    @Override
    public void run() {
        if (RobotStorage.ROBOTS.isEmpty()) {
            return;
        }
        for (Map.Entry<Location, Robot> pair : RobotStorage.ROBOTS.entrySet()) {
            Robot robot = pair.getValue();

            if (robot.getField().isEmpty()) {
                continue;
            }


            List<Location> field = robot.getField();
            Collections.shuffle(field);

            for (Location loc : robot.getField()) {
                Block b = loc.clone().add(0,1,0).getBlock();
                if (b.getType().equals(Material.POTATOES) ||
                        b.getType().equals(Material.WHEAT)) {
                    Ageable age = (Ageable) b.getBlockData();
                    if (age.getAge()<7) {
                        age.setAge(7);
                        b.setBlockData(age);
                    }
                }
            }

            for (Location loc : field) {
                Block b = loc.clone().add(0,1,0).getBlock();

                if (!loc.getBlock().getType().equals(Material.FARMLAND)) {
                    loc.getBlock().setType(Material.FARMLAND);
                }

                if (b.getType().equals(Material.AIR)) {
                    Material mat;
                    switch (robot.getCropType()) {
                        case POTATO:
                            mat = Material.POTATOES;
                            break;
                        default:
                            mat = Material.WHEAT;
                            break;
                    }
                    b.setType(mat);
                    return;
                }
            }
        }
    }
}
