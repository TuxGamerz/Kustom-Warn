package me.kustomkraft.kustomwarn;

import java.io.File;
import java.util.logging.Logger;

import me.kustomkraft.kustomwarn.commands.ListCommand;
import me.kustomkraft.kustomwarn.commands.WarnCommand;
import me.kustomkraft.kustomwarn.utils.LocalStore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

public class KustomWarnMain extends JavaPlugin {

    public Logger logger = Bukkit.getLogger();
    public LocalStore warnedPlayers;

    public void onEnable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");

        String pluginFolder = getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();

        warnedPlayers = new LocalStore(new File(pluginFolder + File.separatorChar + "Warned.log"));
        warnedPlayers.load();

        getCommand("warn").setExecutor(new WarnCommand(this));
        getCommand("list").setExecutor(new ListCommand(this));
        getConfig().options().copyDefaults(true);
        saveConfig();
    }

    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been disabled!");

        warnedPlayers.save();
    }
}