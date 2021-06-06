package cz.larkyy.lfarmingrobots.robots.runnables;

import cz.larkyy.lfarmingrobots.LFarmingRobots;
import cz.larkyy.lfarmingrobots.handlers.config.ConfigHandler;
import cz.larkyy.lfarmingrobots.robots.Robot;
import cz.larkyy.lfarmingrobots.robots.RobotStorage;
import cz.larkyy.llibrary.items.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.scheduler.BukkitRunnable;

public class FieldVisualization extends BukkitRunnable {
    @Override
    public void run() {
        if (Bukkit.getOnlinePlayers().isEmpty()) {
            return;
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!p.hasPermission(ConfigHandler.admin_permission)) {
                continue;
            }

            ItemStack is = p.getInventory().getItemInMainHand();

            if (!ItemUtils.hasItemData(LFarmingRobots.getInstance(),is,PersistentDataType.STRING,"identifier")) {
                continue;
            }

            if (ItemUtils.getItemData(LFarmingRobots.getInstance(),is, PersistentDataType.STRING,"identifier").equals("Field Selection Wand")) {
                String[] locStr = ((String)ItemUtils.getItemData(LFarmingRobots.getInstance(),is,PersistentDataType.STRING,"robot")).split("\\|\\|");
                Robot robot = RobotStorage.ROBOTS.get(
                        new Location(
                        Bukkit.getWorld(locStr[0]),
                        Double.parseDouble(locStr[1]),
                        Double.parseDouble(locStr[2]),
                        Double.parseDouble(locStr[3])
                        )
                );
                if (robot == null) {
                    continue;
                }
                if (robot.getField().isEmpty()) {
                    continue;
                }
                robot.getField().forEach(loc -> {
                    p.spawnParticle(Particle.VILLAGER_HAPPY,loc.clone().add(0.5,1.25,0.5),5,0.25,0.25,0.25);
                });
            }
        }
    }
}
