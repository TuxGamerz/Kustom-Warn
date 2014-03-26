package com.stronghearttarot.kustomwarn.commands;

import com.stronghearttarot.kustomwarn.KustomWarn;
import com.stronghearttarot.kustomwarn.utils.Warnings;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.ConsoleCommandSender;


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
        String kickMessage = plugin.getConfig().getString("Kick Message");
        String banMessage = plugin.getConfig().getString("Ban Message");
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
                        List warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).findList();
                        if (plugin.getConfig().getBoolean("Alert Admins"))
                        {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user");
                        }
                        if ((warnings.size() + 1) >= plugin.getConfig().getInt("Kick After"))
                        {
                            targetPlayer.setPlayerListName(ChatColor.AQUA + targetPlayer.getName());
                        }
                        else if ((warnings.size() + 1) >= plugin.getConfig().getInt("Ban After"))
                        if(warnings.size() != 0)
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                        }
                        else
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                        }
                        targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                        Warnings warning = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                        if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                        {
                            warning = new Warnings();
                            warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                            warning.setPlayerName(targetPlayer.getName());
                            warning.setAdminName("console user");
                        }
                        plugin.getDatabase().save(warning);

                        if ((warnings.size() + 1) == plugin.getConfig().getInt("Kick After"))
                        {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                        }
                        else if((warnings.size() + 1) == plugin.getConfig().getInt("Ban After"))
                        {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                            targetPlayer.setBanned(true);
                        }
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
                        List warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).findList();
                        if (plugin.getConfig().getBoolean("Alert Admins"))
                        {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user for " + reason);
                        }
                        if(warnings.size() != 0)
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                        }
                        else
                        {
                            consoleSender.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                        }
                        if (plugin.getConfig().getBoolean("Alert Admins"))
                        {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user");
                        }
                        if ((warnings.size() + 1) >= plugin.getConfig().getInt("Kick After"))
                        {
                            targetPlayer.setPlayerListName(ChatColor.AQUA + targetPlayer.getName());
                        }
                        targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                        Warnings warning = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                        if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                        {
                            warning = new Warnings();
                            warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                            warning.setPlayerName(targetPlayer.getName());
                            warning.setAdminName("console user");
                            warning.setWarningReason(reason);
                        }
                        plugin.getDatabase().save(warning);

                        if ((warnings.size() + 1) == plugin.getConfig().getInt("Kick After"))
                        {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                        }
                        else if((warnings.size() + 1) == plugin.getConfig().getInt("Ban After"))
                        {
                            targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                            targetPlayer.setBanned(true);
                        }
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
                        if (plugin == null)
                        {
                            player.sendMessage(prefix + "Plugin is null");
                        }
                        player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player] (reason)");
                        return true;
                    }
                    else if (args.length == 1)
                    {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        if (targetPlayer != null) {
                            List warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).findList();
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
                            Warnings warning = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                            if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                            {
                                warning = new Warnings();
                                warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                                warning.setPlayerName(targetPlayer.getName());
                                warning.setAdminName(player.getName());
                            }
                            plugin.getDatabase().save(warning);

                            if ((warnings.size() + 1) == plugin.getConfig().getInt("Kick After"))
                            {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            }
                            else if((warnings.size() + 1) == plugin.getConfig().getInt("Ban After"))
                            {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                            if (plugin.getConfig().getBoolean("Alert Admins"))
                            {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user");
                            }
                            if ((warnings.size() + 1) >= plugin.getConfig().getInt("Kick After"))
                            {
                                targetPlayer.setPlayerListName(ChatColor.AQUA + targetPlayer.getName());
                            }
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
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        if (targetPlayer != null)
                        {
                            List warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).findList();
                            if (plugin.getConfig().getBoolean("Alert Admins"))
                            {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by " + player.getName() + " for " + reason);
                            }
                            if(warnings.size() != 0)
                            {
                                player.sendMessage(prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned " + String.valueOf(warnings.size() + 1) + " times");
                            }
                            if (setKicked(targetPlayer.getName()))
                            {
                                targetPlayer.kickPlayer(plugin);
                            }
                            else
                            {
                                player.sendMessage(prefix + ChatColor.AQUA + "This is " + targetPlayer.getName() + "\'s first warning");
                            }
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                            Warnings warning = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(warnings.size() + 1)).findUnique();
                            if (warnings.size() < plugin.getConfig().getInt("Ban After"))
                            {
                                warning = new Warnings();
                                warning.setWarningNumber(String.valueOf(warnings.size() + 1));
                                warning.setPlayerName(targetPlayer.getName());
                                warning.setAdminName(player.getName());
                                warning.setWarningReason(reason);
                            }
                            plugin.getDatabase().save(warning);

                            if (plugin.getConfig().getBoolean("Alert Admins"))
                            {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + targetPlayer.getName() + " has been warned by a console user");
                            }
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

    public boolean setKicked(String playerName)
    {
        List warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", playerName).findList();
        if ((warnings.size() + 1) == plugin.getConfig().getInt("Kick After"))
        {
            return true;
        }
        return false;
    }

    public boolean setBanned(String playerName)
    {
        List warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", playerName).findList();
        if ((warnings.size() + 1) == plugin.getConfig().getInt("Ban After"))
        {
            return true;
        }
        return false;
    }

}
