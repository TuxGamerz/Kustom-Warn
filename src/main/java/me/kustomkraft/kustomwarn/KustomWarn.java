package me.kustomkraft.kustomwarn;

import me.kustomkraft.kustomwarn.commands.*;
import me.kustomkraft.kustomwarn.utils.LocalStore;
import me.kustomkraft.kustomwarn.utils.PluginUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.logging.Logger;

public class KustomWarn extends JavaPlugin {

    private Logger logger = Bukkit.getLogger();
    private FileConfiguration customConfiguration;
    private File customConfigurationFile;

    public LocalStore warnedPlayers;

    protected PluginUpdater pluginUpdater;

    public void loadConfig() {
        getCustomConfiguration().options().copyDefaults(true);
        saveCustomConfiguration();
    }

    public void reloadCustomConfiguration() {
        if (customConfigurationFile == null) {
            customConfigurationFile = new File(getDataFolder(), "Warnings.yml");
        }
        customConfiguration = YamlConfiguration.loadConfiguration(customConfigurationFile);

        InputStream configStream = getResource("Warnings.yml");
        if (configStream != null) {
            YamlConfiguration defineConfig = YamlConfiguration.loadConfiguration(configStream);
            customConfiguration.setDefaults(defineConfig);
        }
    }

    public FileConfiguration getCustomConfiguration() {
        if (customConfiguration == null) {
            reloadCustomConfiguration();
        }
        return customConfiguration;
    }

    public void saveCustomConfiguration() {
        if ((customConfiguration == null) || (customConfigurationFile == null)) return;

        try {
            getCustomConfiguration().save(customConfigurationFile);
        } catch (IOException e) {
            logger.severe("Error: " + e.getMessage());
        }
    }

    @Override
    public void onEnable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");
        loadConfig();

        getCommand("kwarns").setExecutor(new KWarns(this));
        getCommand("kwarn").setExecutor(new KWarn(this));
        getCommand("kdelete").setExecutor(new KDelete(this));
        getCommand("klist").setExecutor(new KList(this));
        getCommand("kreload").setExecutor(new KReload(this));

        this.pluginUpdater = new PluginUpdater(this, "http://dev.bukkit.org/bukkit-plugins/kustom-warn/files.rss");
        if (this.getConfig().getBoolean("Auto Update")){
            if (this.pluginUpdater.updateRequired()) {
                    logger.info("New version available");
                    logger.info("Get it at: " + pluginUpdater.getVersionLink());
            }
        }

        if (getConfig().getBoolean("Alert Players")) {
            getServer().getPluginManager().registerEvents(new Listener() {
                @EventHandler
                public void alertOnJoin(PlayerJoinEvent event) {
                    event.getPlayer().sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.YELLOW + "This server is protected by Kustom Warn please follow the rules");
                }
            }, this);
        }
        saveDefaultConfig();
    }

    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been disabled!");
    }

}
