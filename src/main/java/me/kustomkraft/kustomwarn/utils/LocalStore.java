package me.kustomkraft.kustomwarn.utils;

import java.io.File;

import java.util.List;
import java.util.logging.Logger;

import me.kustomkraft.kustomwarn.KustomWarn;
import me.kustomkraft.kustomwarn.commands.KWarn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class LocalStore{

    private FileConfiguration customConfiguration;
    private File customConfigurationFile;
    private KustomWarn plugin;
    private KWarn kWarn;
    private Logger logger = Bukkit.getLogger();
    private List warningsList;

    public LocalStore(KustomWarn plugin) {
        this.plugin = plugin;
    }

    public int getWarningTotal(String playerName) {
        warningsList.size();
        return warningsList.size();
    }

    public void  addWarning(String playerName, String adminName, String date) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("By " + adminName + date);
        plugin.getCustomConfiguration().set(playerName + ".warnings", stringBuilder);
    }

    public void addWarningReason(String playerName, String adminName, String warningReason, String date) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("By " + adminName + " for " + warningReason + date);
        plugin.getCustomConfiguration().set(playerName + ".warnings", stringBuilder);
    }

    public void removeWarning() {

    }

}