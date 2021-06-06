package cz.larkyy.lfarmingrobots;

import cz.larkyy.lfarmingrobots.handlers.config.ConfigHandler;
import cz.larkyy.lfarmingrobots.handlers.messages.MessageHandler;
import cz.larkyy.lfarmingrobots.robots.CropType;
import cz.larkyy.lfarmingrobots.robots.Robot;
import cz.larkyy.lfarmingrobots.robots.RobotStorage;
import cz.larkyy.llibrary.items.ItemUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class Listeners implements Listener {

    @EventHandler
    public void onInteract(PlayerInteractAtEntityEvent e) {
        Player p = e.getPlayer();

        if (e.getHand().equals(EquipmentSlot.OFF_HAND)) {
            return;
        }

        if (e.getRightClicked() instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) e.getRightClicked();
            Robot robot = RobotStorage.getRobot(as);

            if (robot!=null) {
                e.setCancelled(true);

                switch (robot.getCropType()) {
                    case WHEAT:
                        robot.setCropType(CropType.POTATO);
                        p.sendMessage("Switched to potato");
                        break;
                    case POTATO:
                        robot.setCropType(CropType.WHEAT);
                        p.sendMessage("Switched to wheat");
                        break;
                }
            }
        }
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player)){
            return;
        }
        Player p = (Player) e.getDamager();

        if (e.getEntity() instanceof ArmorStand) {
            ArmorStand as = (ArmorStand) e.getEntity();
            Robot robot = RobotStorage.getRobot(as);

            if (robot!=null) {
                e.setCancelled(true);
                MessageHandler.sendMessage(p,MessageHandler.wand_item_received);
                p.getInventory().addItem(ConfigHandler.loadWandItem(robot));
            }
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        Player p = e.getPlayer();

        if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.LEFT_CLICK_BLOCK)) {

            if (e.getHand().equals(EquipmentSlot.OFF_HAND)) {
                return;
            }

            ItemStack is = p.getInventory().getItemInMainHand();

            if (ItemUtils.hasItemData(LFarmingRobots.getInstance(),is, PersistentDataType.STRING,"identifier")) {

                if (ItemUtils.getItemData(LFarmingRobots.getInstance(), is, PersistentDataType.STRING, "identifier").equals("Field Selection Wand")) {
                    e.setCancelled(true);

                    String[] locStr = ((String) ItemUtils.getItemData(LFarmingRobots.getInstance(), is, PersistentDataType.STRING, "robot")).split("\\|\\|");
                    Robot robot = RobotStorage.ROBOTS.get(
                            new Location(
                                    Bukkit.getWorld(locStr[0]),
                                    Double.parseDouble(locStr[1]),
                                    Double.parseDouble(locStr[2]),
                                    Double.parseDouble(locStr[3])
                            )
                    );
                    if (robot == null) {
                        p.sendMessage("Unknown robot");
                        return;
                    }

                    if (e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                        if (robot.getField().contains(e.getClickedBlock().getLocation())) {
                            p.sendMessage("already a field");
                            return;
                        }
                        robot.addFieldLoc(e.getClickedBlock().getLocation());
                        p.sendMessage("field added");
                    } else {
                        if (!robot.getField().contains(e.getClickedBlock().getLocation())) {
                            p.sendMessage("there is no field");
                            return;
                        }
                        robot.removeFieldLoc(e.getClickedBlock().getLocation());
                        p.sendMessage("field removed");
                    }

                }
            }
        }
    }

    @EventHandler (ignoreCancelled = true)
    public void onBlockPlace(BlockPlaceEvent e) {
        Player p = e.getPlayer();

        if (e.getItemInHand().isSimilar(new ItemStack(Material.EMERALD_BLOCK))) {
            p.getInventory().addItem(ConfigHandler.loadRobotItem());
            return;
        }

        if (e.getItemInHand().isSimilar(ConfigHandler.loadRobotItem())) {
            MessageHandler.sendMessage(p,MessageHandler.robot_placed);
            RobotStorage.addRobot(new Robot(e.getBlock().getLocation(),CropType.WHEAT));
            e.setCancelled(true);
        }
    }
}
