package cz.larkyy.lfarmingrobots;

import cz.larkyy.lfarmingrobots.handlers.ConfigManager;
import cz.larkyy.lfarmingrobots.handlers.config.CfgVar;
import cz.larkyy.lfarmingrobots.handlers.config.ConfigHandler;
import cz.larkyy.lfarmingrobots.handlers.messages.MessageHandler;
import cz.larkyy.lfarmingrobots.handlers.messages.MsgsCfgVar;
import cz.larkyy.lfarmingrobots.robots.RobotStorage;
import cz.larkyy.lfarmingrobots.robots.runnables.FieldRunnable;
import cz.larkyy.lfarmingrobots.robots.runnables.FieldVisualization;
import cz.larkyy.lfarmingrobots.robots.runnables.RotationRunnable;
import cz.larkyy.llibrary.chat.ChatUtils;
import cz.larkyy.llibrary.config.annotation.ConfigAnnotationManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

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

        getServer().getPluginManager().registerEvents(new Listeners(),this);

        ChatUtils.sendConsoleMsg(this, MessageHandler.replacePlaceholders(MessageHandler.plugin_enabled));

        new BukkitRunnable() {
            @Override
            public void run() {
                RobotStorage.loadDataCfg();

                new FieldRunnable().runTaskTimer(getInstance(),0,50);
                new RotationRunnable().runTaskTimer(getInstance(),0,1);
                new FieldVisualization().runTaskTimer(getInstance(),0,20);
            }
        }.runTaskLater(this,50);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public static LFarmingRobots getInstance() {
        return instance;
    }
}
