package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class KList implements CommandExecutor {

    private KustomWarn plugin;

    public KList (KustomWarn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        String prefix = ChatColor.GREEN + "[Kustom Warn]";
        if (commandLabel.equalsIgnoreCase("klist")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /klist [player]");
                    return true;
                } else if (args.length == 1) {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer.isOnline()) {
                        if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == 0) {

                        } else {

                        }
                    } else if (targetPlayer.hasPlayedBefore()){
                        if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == 0) {

                        } else {

                        }
                    } else {
                        consoleSender.sendMessage(prefix + ChatColor.RED + "This player has not played on this server!");
                        return true;
                    }
                }
            } else {
                if (args.length == 0) {

                } else if (args.length == 1) {

                }
            }
        }
        return true;
    }
}
