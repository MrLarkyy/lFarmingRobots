package cz.larkyy.lfarmingrobots.robots;

import cz.larkyy.lfarmingrobots.LFarmingRobots;
import cz.larkyy.llibrary.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class RobotStorage {

    public static final Config DATA = new Config(LFarmingRobots.getInstance(),"data.yml");
    public static final Map<Location,Robot> ROBOTS = new HashMap<>();

    public static void loadDataCfg() {
        DATA.load();

        if (getDataCfg().getConfigurationSection("robots")==null) {
            return;
        }
        if (getDataCfg().getConfigurationSection("robots").getKeys(false).isEmpty()) {
            return;
        }

        getDataCfg().getConfigurationSection("robots").getKeys(false).forEach(id -> {
            String path = "robots."+id;
            String[] locString = id.split("\\|\\|");
            Location loc = new Location(
                    Bukkit.getWorld(locString[0]),
                    Double.parseDouble(locString[1]),
                    Double.parseDouble(locString[2]),
                    Double.parseDouble(locString[3])
            );
            CropType cropType = CropType.valueOf(getDataCfg().getString(path+".cropType"));
            List<Location> field = new ArrayList<>();
            getDataCfg().getStringList(path+".field").forEach(locStr -> {
                String[] loc1 = locStr.split("\\|\\|");
                field.add(new Location(
                        Bukkit.getWorld(loc1[0]),
                        Double.parseDouble(loc1[1]),
                        Double.parseDouble(loc1[2]),
                        Double.parseDouble(loc1[3])
                        )
                );
            });
            ROBOTS.put(loc,new Robot(loc,cropType,field));
        });
    }

    public static ArmorStand spawnAs(Location loc) {
        ArmorStand as = (ArmorStand) loc.getWorld().spawnEntity(loc.clone().add(0.5,3,0.5), EntityType.ARMOR_STAND);
        as.setGravity(false);
        as.setSmall(true);
        as.setInvisible(true);
        as.setAI(false);
        as.setPersistent(false);
        as.setSilent(true);
        as.getEquipment().setHelmet(new ItemStack(Material.PLAYER_HEAD));
        return as;
    }

    public static void addRobot(Robot robot) {
        ROBOTS.put(robot.getLocation(),robot);
        getDataCfg().set("robots."+robot.getLocationString()+".cropType",robot.getCropType().toString());
        DATA.save();
    }

    public static void removeRobot(Robot robot) {
        removeRobot(robot.getLocation());
    }

    public static void removeRobot(Location loc) {
        getDataCfg().set("robots."+ROBOTS.get(loc).getLocationString(),null);
        ROBOTS.remove(loc);
        DATA.save();
    }

    public static boolean isRobot(ArmorStand as) {
        if (ROBOTS.isEmpty()) {
            return false;
        }
        for (Map.Entry<Location,Robot> pair : ROBOTS.entrySet()) {
            if (pair.getValue().getAs().equals(as)) {
                return true;
            }
        }
        return false;
    }
    public static Robot getRobot(ArmorStand as) {
        if (ROBOTS.isEmpty()) {
            return null;
        }
        for (Map.Entry<Location,Robot> pair : ROBOTS.entrySet()) {
            if (pair.getValue().getAs().equals(as)) {
                return pair.getValue();
            }
        }
        return null;
    }

    public static FileConfiguration getDataCfg() {
        return DATA.getConfiguration();
    }
}
