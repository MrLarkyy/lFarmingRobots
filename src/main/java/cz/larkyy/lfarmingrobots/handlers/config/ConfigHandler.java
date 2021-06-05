package cz.larkyy.lfarmingrobots.handlers.config;

import cz.larkyy.lfarmingrobots.LFarmingRobots;
import items.ItemsUtils;
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

    public static ItemStack loadRobotItem() {
        ItemStack is;
        if (robot_item_modelData == null) {
            is = ItemsUtils.makeItemStack(
                    Material.valueOf(robot_item_material),
                    robot_item_displayName,
                    robot_item_lore
            );
        } else {
            is = ItemsUtils.makeItemStack(
                    Material.valueOf(robot_item_material),
                    robot_item_displayName,
                    robot_item_lore,
                    Integer.parseInt(robot_item_modelData)
            );
        }
        return ItemsUtils.addItemData(LFarmingRobots.getInstance(), is, PersistentDataType.INTEGER, "identifier", 1);
    }
}
