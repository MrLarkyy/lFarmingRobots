package cz.larkyy.lfarmingrobots.handlers.messages;


import cz.larkyy.llibrary.chat.ChatUtils;
import org.bukkit.entity.Player;

import java.util.List;

public class MessageHandler {

    @MsgsCfgVar("enabled")
    public static String plugin_enabled;

    @MsgsCfgVar("prefix")
    public static String plugin_prefix;

    @MsgsCfgVar("robotItemReceived")
    public static String robot_item_received;

    public static void sendMessage(Player p, String msg) {
        p.sendMessage(ChatUtils.format(replacePlaceholders(msg)));
    }
    public static void sendMessage(Player p, List<String> msgs) {
        for (String msg : msgs) {
            p.sendMessage(ChatUtils.format(replacePlaceholders(msg)));
        }
    }

    public static String replacePlaceholders(String msg) {
        return msg.replace("%prefix%", MessageHandler.plugin_prefix);
    }

}
