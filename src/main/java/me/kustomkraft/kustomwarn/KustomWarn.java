package me.kustomkraft.kustomwarn;

import me.kustomkraft.kustomwarn.commands.*;
import me.kustomkraft.kustomwarn.utils.LocalStore;
import me.kustomkraft.kustomwarn.utils.PluginUpdater;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.mcstats.Metrics;

import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class KustomWarn extends JavaPlugin {

    private Logger logger = Bukkit.getLogger();

    protected PluginUpdater pluginUpdater;

    @Override
    public void onEnable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");

        getCommand("kwarns").setExecutor(new KWarns(this));
        getCommand("kwarn").setExecutor(new KWarn(this));
        getCommand("kdelete").setExecutor(new KDelete(this));
        getCommand("klist").setExecutor(new KList(this));
        getCommand("kreload").setExecutor(new KReload(this));

        setupDatabase();
        try
        {
            Metrics metrics = new Metrics(this);
            metrics.start();
        } catch (IOException e)
        {
            logger.severe("Error: " + e.getMessage());
        }

        this.pluginUpdater = new PluginUpdater(this, "http://dev.bukkit.org/bukkit-plugins/kustom-warn/files.rss");
        if (this.getConfig().getBoolean("Auto Update"))
        {
            if (this.pluginUpdater.updateRequired())
            {
                    logger.info("New version available");
                    logger.info("Get it at: " + pluginUpdater.getVersionLink());
            }
        }

        if (getConfig().getBoolean("Alert Players"))
        {
            getServer().getPluginManager().registerEvents(new Listener()
            {
                @EventHandler
                public void alertOnJoin(PlayerJoinEvent event)
                {
                    event.getPlayer().sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.YELLOW + "This server is protected by Kustom Warn please follow the rules");
                }
            }, this);
        }
        saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        logger.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been disabled!");
    }

    public void setupDatabase()
    {
        try
        {
            getDatabase().find(LocalStore.class).findRowCount();
        }
        catch (PersistenceException e)
        {
            logger.severe("Error: " + e.getMessage());
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses()
    {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(LocalStore.class);
        return list;
    }

}
