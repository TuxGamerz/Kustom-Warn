package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

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
                        if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) == 0) {
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has not received any warnings");
                            return true;
                        } else {
                            List warningsList = plugin.getCustomConfiguration().getStringList(targetPlayer.getName() + ".warnings");
                            if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) != 0) {
                                for (Object s : warningsList) {
                                    consoleSender.sendMessage(prefix + ChatColor.RED + s);
                                }
                            }
                        }
                    } else if (targetPlayer.hasPlayedBefore()){
                        if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) == 0) {
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has not received any warnings");
                            return true;
                        } else {
                            List warningsList = plugin.getCustomConfiguration().getStringList(targetPlayer.getName() + ".warnings");
                            if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) != 0) {
                                for (Object s : warningsList) {
                                    consoleSender.sendMessage(prefix + ChatColor.RED + s);
                                }
                            }
                        }
                    } else {
                        consoleSender.sendMessage(prefix + ChatColor.RED + "This player has not played on this server!");
                        return true;
                    }
                }
            } else {
                Player player = (Player) sender;
                if (args.length == 0) {
                    player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /klist [player]");
                    return true;
                } else if (args.length == 1) {
                    Player targetPlayer = player.getServer().getPlayer(args[0]);
                    if (targetPlayer.isOnline()) {
                        if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) == 0) {
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has not received any warnings");
                            return true;
                        } else {
                            List warningsList = plugin.getCustomConfiguration().getStringList(targetPlayer.getName() + ".warnings");
                            if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) != 0) {
                                for (Object s : warningsList) {
                                    consoleSender.sendMessage(prefix + ChatColor.RED + s);
                                }
                            }
                        }
                    } else if (targetPlayer.hasPlayedBefore()){
                        if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) == 0) {
                            player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has not received any warnings");
                            return true;
                        } else {
                            List warningsList = plugin.getCustomConfiguration().getStringList(targetPlayer.getName() + ".warnings");
                            if (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) != 0) {
                                for (Object s : warningsList) {
                                    player.sendMessage(prefix + ChatColor.RED + s);
                                }
                            }
                        }
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "This player has not played on this server!");
                        return true;
                    }

                }
            }
        }
        return true;
    }
}
