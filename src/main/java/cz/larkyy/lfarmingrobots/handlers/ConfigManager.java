package cz.larkyy.lfarmingrobots.handlers;

import cz.larkyy.lfarmingrobots.LFarmingRobots;
import cz.larkyy.llibrary.config.Config;
import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {

    public static final Config CONFIG = new Config(getInstance(), "config.yml");
    public static final Config MESSAGES = new Config(getInstance(), "messages.yml");

    public static void loadConfigs() {
        CONFIG.load();
        MESSAGES.load();
    }

    public static FileConfiguration getConfig() {
        return CONFIG.getConfiguration();
    }

    public static FileConfiguration getMessagesConfig() {
        return MESSAGES.getConfiguration();
    }

    private static LFarmingRobots getInstance() {
        return LFarmingRobots.getInstance();
    }
}