package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KReload implements CommandExecutor {

    private KustomWarn plugin;

    public KReload(KustomWarn instance) {
        this.plugin = instance;
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
