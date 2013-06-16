package me.kustomkraft.kustomwarn;

import me.kustomkraft.kustomwarn.commands.*;
import me.kustomkraft.kustomwarn.utils.DBStore;
import me.kustomkraft.kustomwarn.utils.LocalStore;
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import javax.persistence.PersistenceException;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KustomWarn extends JavaPlugin {

    private Logger logger = Bukkit.getLogger();
    public LocalStore warnedPlayers;

    @Override
    public void onEnable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");

        String pluginFolder = getDataFolder().getAbsolutePath();
        new File(pluginFolder).mkdirs();

        PluginManager pm = this.getServer().getPluginManager();

        warnedPlayers = new LocalStore(new File(pluginFolder + File.separatorChar + "Warned.log"));

        getCommand("kwarns").setExecutor(new KWarns(this));
        getCommand("kwarn").setExecutor(new KWarn(this));
        getCommand("kdelete").setExecutor(new KDelete(this));
        getCommand("klist").setExecutor(new KList(this));

        saveDefaultConfig();
        warnedPlayers.save();
    }

    @Override
    public void onDisable(){
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been disabled!");
    }

    private void setupDatabase() {
        try {
            getDatabase().find(DBStore.class).findRowCount();
        } catch (PersistenceException ex) {
            System.out.println("Installing database for " + getDescription().getName() + " due to first time usage");
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses() {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(DBStore.class);
        return list;
    }

}
