package cz.larkyy.lfarmingrobots;

import config.annotation.ConfigAnnotationManager;
import cz.larkyy.lfarmingrobots.handlers.ConfigManager;
import cz.larkyy.lfarmingrobots.handlers.config.CfgVar;
import cz.larkyy.lfarmingrobots.handlers.config.ConfigHandler;
import cz.larkyy.lfarmingrobots.handlers.messages.MessageHandler;
import cz.larkyy.lfarmingrobots.handlers.messages.MsgsCfgVar;
import cz.larkyy.llibrary.chat.ChatUtils;
import org.bukkit.plugin.java.JavaPlugin;

public final class LFarmingRobots extends JavaPlugin {

    private static LFarmingRobots instance;

    @Override
    public void onEnable() {
        instance = this;
        getDataFolder().mkdirs();
        ConfigManager.loadConfigs();

        ConfigAnnotationManager msgsAnnotation = new ConfigAnnotationManager(MsgsCfgVar.class,this,ConfigManager.getMessagesConfig());
        ConfigAnnotationManager cfgAnnotation = new ConfigAnnotationManager(CfgVar.class,this,ConfigManager.getConfig());
        msgsAnnotation.register(MessageHandler.class);
        cfgAnnotation.register(ConfigHandler.class);
        msgsAnnotation.update();
        cfgAnnotation.update();

        getServer().getPluginManager().registerEvents(new TestListener(),this);

        getServer().getConsoleSender().sendMessage("Is message null: "+(MessageHandler.plugin_enabled == null));
        ChatUtils.sendConsoleMsg(this, MessageHandler.replacePlaceholders(MessageHandler.plugin_enabled));
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LFarmingRobots getInstance() {
        return instance;
    }
}
