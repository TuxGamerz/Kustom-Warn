package me.kustomkraft.kustomwarn.utils;

import java.io.File;

import java.util.List;
import java.util.logging.Logger;

import me.kustomkraft.kustomwarn.KustomWarn;
import me.kustomkraft.kustomwarn.commands.KWarn;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;

public class LocalStore{

    private FileConfiguration customConfiguration = null;
    private File customConfigurationFile = null;
    private KustomWarn plugin;
    private KWarn kWarn;
    private Logger logger = Bukkit.getLogger();
    private List warningsList;

    public LocalStore(KustomWarn plugin) {
        this.plugin = plugin;
    }

    public int getWarningTotal(String playerName) {
        int warnings = warningsList.size();
        return warnings;
    }

    public void  addWarning(String playerName, String adminName, String date) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("By " + adminName + date);
        warningsList = plugin.getCustomConfiguration().getStringList(kWarn.offenderName + ".warnings");
        plugin.getCustomConfiguration().set(playerName + ".warnings", warningsList);
        warningsList.clear();
    }

    public void addWarningReason(String playerName, String adminName, String warningReason, String date) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("By " + adminName + " for " + warningReason + date);
        warningsList = plugin.getCustomConfiguration().getStringList(kWarn.offenderName + ".warnings");
        plugin.getCustomConfiguration().set(playerName + ".warnings", warningsList);
        warningsList.clear();
    }

    public void removeWarning() {

    }

}