package me.kustomkraft.kustomwarn;

//Internal imports
import me.kustomkraft.kustomwarn.commands.*;
import me.kustomkraft.kustomwarn.utils.Metrics;
import me.kustomkraft.kustomwarn.utils.Warnings;
import me.kustomkraft.kustomwarn.utils.PluginUpdater;

//Bukkit imports
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;

//Standard java imports
import javax.persistence.PersistenceException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("unused")
public class KustomWarn extends JavaPlugin {

    private Logger log = Bukkit.getLogger();

    protected PluginUpdater pluginUpdater;

    @Override
    public void onEnable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        log.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");

        try
        {
            Metrics metrics = new Metrics(this);
            metrics.start();
            log.info("Metrics Started");
        }
        catch (IOException e)
        {
            log.severe("Error: " + e.getMessage());
        }

        getCommand("kwarns").setExecutor(new KWarns(this));
        getCommand("kwarn").setExecutor(new KWarn(this));
        getCommand("kdelete").setExecutor(new KDelete(this));
        getCommand("klist").setExecutor(new KList(this));
        getCommand("kreload").setExecutor(new KReload(this));

        setupDatabase();

        this.pluginUpdater = new PluginUpdater(this, "http://dev.bukkit.org/bukkit-plugins/kustom-warn/files.rss");
        if (this.getConfig().getBoolean("Auto Update"))
        {
            if (this.pluginUpdater.updateRequired())
            {
                    log.info("New version available");
                    log.info("Get it at: " + pluginUpdater.getVersionLink());
            }
        }

        if (getConfig().getBoolean("Alert Players"))
        {
            getServer().getPluginManager().registerEvents(new Listener()
            {
                String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
                @EventHandler
                public void alertOnJoin(PlayerJoinEvent event)
                {
                    event.getPlayer().sendMessage(prefix + ChatColor.YELLOW + "This server is protected by Kustom Warn please follow the rules");
                }
            }, this);
        }
        saveDefaultConfig();
    }

    @Override
    public void onDisable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        log.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been disabled!");
    }

    public void setupDatabase()
    {
        try
        {
            getDatabase().find(Warnings.class).findRowCount();
        }
        catch (PersistenceException e)
        {
            log.severe("Error: " + e.getMessage());
            installDDL();
        }
    }

    @Override
    public List<Class<?>> getDatabaseClasses()
    {
        List<Class<?>> list = new ArrayList<Class<?>>();
        list.add(Warnings.class);
        return list;
    }

}
