package cz.larkyy.lfarmingrobots.handlers.config;

import cz.larkyy.lfarmingrobots.LFarmingRobots;
import cz.larkyy.lfarmingrobots.robots.Robot;
import cz.larkyy.llibrary.items.ItemUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

import java.util.List;

public class ConfigHandler {

    // ROBOT ITEM
    @CfgVar("robotItem.material")
    public static String robot_item_material;

    @CfgVar("robotItem.displayName")
    public static String robot_item_displayName;

    @CfgVar("robotItem.lore")
    public static List<String> robot_item_lore;

    @CfgVar("robotItem.modelData")
    public static String robot_item_modelData;

    // WAND ITEM

    @CfgVar("selectWandItem.material")
    public static String wand_item_material;

    @CfgVar("selectWandItem.displayName")
    public static String wand_item_displayName;

    @CfgVar("selectWandItem.lore")
    public static List<String> wand_item_lore;

    @CfgVar("selectWandItem.modelData")
    public static String wand_item_modelData;

    // PERMISSIONS

    @CfgVar("permissions.admin")
    public static String admin_permission;

    public static ItemStack loadRobotItem() {
        ItemStack is;
        if (robot_item_modelData == null) {
            is = ItemUtils.makeItemStack(
                    Material.valueOf(robot_item_material),
                    robot_item_displayName,
                    robot_item_lore
            );
        } else {
            is = ItemUtils.makeItemStack(
                    Material.valueOf(robot_item_material),
                    robot_item_displayName,
                    robot_item_lore,
                    Integer.parseInt(robot_item_modelData)
            );
        }
        ItemUtils.addItemData(LFarmingRobots.getInstance(), is, PersistentDataType.STRING, "identifier", "Farming Robot");
        return is;
    }

    public static ItemStack loadWandItem(Robot robot) {
        ItemStack is;
        if (wand_item_modelData == null) {
            is = ItemUtils.makeItemStack(
                    Material.valueOf(wand_item_material),
                    wand_item_displayName,
                    wand_item_lore
            );
        } else {
            is = ItemUtils.makeItemStack(
                    Material.valueOf(wand_item_material),
                    wand_item_displayName,
                    wand_item_lore,
                    Integer.parseInt(wand_item_modelData)
            );
        }
        ItemUtils.addItemData(LFarmingRobots.getInstance(), is, PersistentDataType.STRING, "identifier", "Field Selection Wand");
        ItemUtils.addItemData(LFarmingRobots.getInstance(), is, PersistentDataType.STRING, "robot", robot.getLocationString());
        return is;
    }
}
