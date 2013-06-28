package me.kustomkraft.kustomwarn;

import me.kustomkraft.kustomwarn.commands.*;
import me.kustomkraft.kustomwarn.utils.LocalStore;
import me.kustomkraft.kustomwarn.utils.PluginUpdater;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.PersistenceException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KustomWarn extends JavaPlugin {

    private Logger logger = Bukkit.getLogger();
    public LocalStore warnedPlayers;
    protected PluginUpdater pluginUpdater;

    @Override
    public void onEnable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");

        String pluginFolder = getDataFolder().getAbsolutePath();
        (new File(pluginFolder)).mkdirs();

        warnedPlayers = new LocalStore(new File(pluginFolder + File.separatorChar + "Warned.log"));
        warnedPlayers.load();

        getCommand("kwarns").setExecutor(new KWarns(this));
        getCommand("kwarn").setExecutor(new KWarn(this));
        getCommand("kdelete").setExecutor(new KDelete(this));
        getCommand("klist").setExecutor(new KList(this));
        getCommand("blist").setExecutor(new ListOff(this));

        this.pluginUpdater = new PluginUpdater(this, "http://dev.bukkit.org/bukkit-plugins/kustom-warn/files.rss");
        if (this.getConfig().getBoolean("Auto Update")){
            if (this.pluginUpdater.updateRequired()) {
                    logger.info("New version available");
                    logger.info("Get it at: " + pluginUpdater.getVersionLink());
            }
        }

        saveDefaultConfig();
    }

    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been disabled!");
        pluginUpdater.getFile("http://dev.bukkit.org/media/files/706/595/Kustom-Warn.jar");
        warnedPlayers.save();
    }

}
