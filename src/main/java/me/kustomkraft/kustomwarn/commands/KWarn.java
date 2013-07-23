package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;

import me.kustomkraft.kustomwarn.utils.LocalStore;

import org.bukkit.ChatColor;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import org.bukkit.entity.Player;

import java.util.List;

public class KWarn implements CommandExecutor
{

    private KustomWarn plugin;

    public KWarn(KustomWarn instance)
    {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        String reason = " ";
        for (int i = 1; i < args.length; i++)
        {
            reason += args[i] + " ";
        }
        if (commandLabel.equalsIgnoreCase("kwarn"))
        {
            if (!(sender instanceof Player))
            {
                if (args.length == 0)
                {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player] (reason)");
                    return true;
                }
                else if (args.length == 1)
                {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer != null)
                    {
                        List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).findList();
                        if (plugin.getConfig().getBoolean("Alert Admins"))
                        {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user");
                        }
                        if(warnings.size() != 0)
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                        }
                        else
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                        }
                        targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                        LocalStore warning = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                        if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                        {
                            warning = new LocalStore();
                            warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                            warning.setPlayerName(targetPlayer.getName());
                            warning.setAdminName("console user");
                        }
                        plugin.getDatabase().save(warning);
                        return true;
                    }
                    else
                    {
                        consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                        return true;
                    }
                }
                else if (args.length >= 2)
                {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer != null)
                    {
                        List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).findList();
                        if (plugin.getConfig().getBoolean("Alert Admins"))
                        {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user");
                        }
                        if(warnings.size() != 0)
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                        }
                        else
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                        }
                        targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                        LocalStore warning = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                        if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                        {
                            warning = new LocalStore();
                            warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                            warning.setPlayerName(targetPlayer.getName());
                            warning.setAdminName("console user");
                            warning.setWarningReason(reason);
                        }
                        plugin.getDatabase().save(warning);
                        return true;
                    }
                }
            }
            else
            {
                Player player = (Player) sender;
                if (player.hasPermission("kustomwarn.warn") || player.isOp())
                {
                    if (args.length == 0)
                    {
                        player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player] (reason)");
                        return true;
                    }
                    else if (args.length == 1)
                    {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        if (targetPlayer != null) {
                            List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).findList();
                            if (plugin.getConfig().getBoolean("Alert Admins"))
                            {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by " + player.getName());
                            }
                            if(warnings.size() != 0)
                            {
                                player.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                            }
                            else
                            {
                                player.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                            }
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                            LocalStore warning = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                            if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                            {
                                warning = new LocalStore();
                                warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                                warning.setPlayerName(targetPlayer.getName());
                                warning.setAdminName(player.getName());
                            }
                            plugin.getDatabase().save(warning);
                            return true;
                        }
                        else
                        {
                            player.sendMessage(prefix + ChatColor.RED + "Player not found!");
                            return true;
                        }
                    }
                    else if (args.length >= 2)
                    {
                        Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                        if (targetPlayer != null)
                        {
                            List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).findList();
                            if (plugin.getConfig().getBoolean("Alert Admins"))
                            {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by " + player.getName());
                            }
                            if(warnings.size() != 0)
                            {
                                player.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                            }
                            else
                            {
                                player.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                            }
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                            LocalStore warning = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                            if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                            {
                                warning = new LocalStore();
                                warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                                warning.setPlayerName(targetPlayer.getName());
                                warning.setAdminName(player.getName());
                                warning.setWarningReason(reason);
                            }
                            plugin.getDatabase().save(warning);
                            return true;
                        }
                        else
                        {
                            player.sendMessage(prefix + ChatColor.RED + "Player not found");
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
