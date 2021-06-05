package cz.larkyy.lfarmingrobots.handlers.config;

import items.ItemsUtils;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

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
    public static List<String> robot_item_modelData;

    public static ItemStack loadRobotItem() {
        return ItemsUtils.makeItemStack(
                Material.valueOf(robot_item_material),
                robot_item_displayName,
                robot_item_lore,
                "farming-robot"
        );
    }
}
