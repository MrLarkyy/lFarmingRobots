package cz.larkyy.lfarmingrobots;

import cz.larkyy.lfarmingrobots.handlers.config.ConfigHandler;
import cz.larkyy.lfarmingrobots.handlers.messages.MessageHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class TestListener implements Listener {
    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player p = e.getPlayer();
        p.getInventory().addItem(ConfigHandler.loadRobotItem());
        MessageHandler.sendMessage(p,MessageHandler.robot_item_received);
    }
}
