package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.logging.Logger;

public class KReload implements CommandExecutor {

    private KustomWarn plugin;

    public KReload(KustomWarn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        if (commandLabel.equalsIgnoreCase("kreload")) {
            plugin.reloadConfig();
            sender.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.YELLOW + "Config Reloaded");
            return true;
        }
        return true;
    }

}
