package cz.larkyy.lfarmingrobots.robots.runnables;

import cz.larkyy.lfarmingrobots.robots.RobotStorage;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.scheduler.BukkitRunnable;

public class RotationRunnable extends BukkitRunnable {
    @Override
    public void run() {
        if (RobotStorage.ROBOTS.isEmpty()) {
            return;
        }

        RobotStorage.ROBOTS.forEach(((uuid, robot) -> {
            ArmorStand as = robot.getAs();
            if (as==null) {
                as = RobotStorage.spawnAs(robot.getLocation());
                robot.setAs(as);
            }
            Location loc = as.getLocation().clone();
            loc.setYaw(loc.getYaw()+3);
            as.teleport(loc);
        }));
    }
}
