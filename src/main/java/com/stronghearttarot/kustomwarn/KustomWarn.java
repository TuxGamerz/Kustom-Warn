package com.stronghearttarot.kustomwarn;

//Internal imports
import com.stronghearttarot.kustomwarn.commands.*;
import com.stronghearttarot.kustomwarn.listeners.PlayerAlert;
import com.stronghearttarot.kustomwarn.utils.Warnings;
import com.stronghearttarot.kustomwarn.utils.PluginUpdater;

//Bukkit imports
import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.kitteh.tag.TagAPI;
import org.mcstats.Metrics;

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
    public TagAPI tags;

    @Override
    public void onEnable()
    {
        PluginDescriptionFile pdfFile = getDescription();
        log.info(pdfFile.getName() + " Version: " + pdfFile.getVersion() + " has been enabled!");

        PluginManager pm = getServer().getPluginManager();

        tags = (TagAPI)pm.getPlugin("TagAPI");

        if (tags == null)
        {
            log.info("TagAPI not found disabling support!");
        }
        else
        {
            log.info("TagAPI found enabling support!");
        }

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
        getCommand("kreset").setExecutor(new KReset(this));

        //Registering events
        pm.registerEvents(new TagName(this), this);
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
            pm.registerEvents(new PlayerAlert(this), this);
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
