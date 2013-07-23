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

public class KList implements CommandExecutor {

    private KustomWarn plugin;

    public KList (KustomWarn instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        if (commandLabel.equalsIgnoreCase("klist"))
        {
            if (!(sender instanceof Player))
            {
                if (args.length == 0)
                {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /klist [player]");
                    return true;
                }
                else
                {
                    if (consoleSender.hasPermission("kustomwarn.other") || consoleSender.isOp())
                    {
                        Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                        List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).findList();
                        if (warnings != null)
                        {
                            consoleSender.sendMessage(ChatColor.AQUA + "===============" + ChatColor.YELLOW + " Kustom Warn " + ChatColor.AQUA + "===============");
                            consoleSender.sendMessage(ChatColor.YELLOW + "Viewing " + targetPlayer.getName() + "'s warnings");
                            for (Object s: warnings)
                            {
                                consoleSender.sendMessage(ChatColor.YELLOW + String.valueOf(s));
                            }
                            consoleSender.sendMessage(ChatColor.AQUA + "===========================================");
                            return true;
                        }
                        else
                        {
                            consoleSender.sendMessage(prefix + ChatColor.GREEN + targetPlayer.getName() + " doesn't have any warnings to view");
                            return true;
                        }
                    }
                }
            }
            else
            {
                Player player = (Player) sender;
                if (args.length == 0)
                {
                    player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /klist [player]");
                    return true;
                }
                else
                {
                    if (player.hasPermission("kustomwarn.other") || player.isOp())
                    {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).findList();
                        if (warnings != null)
                        {
                            player.sendMessage(ChatColor.AQUA + "===============" + ChatColor.YELLOW + " Kustom Warn " + ChatColor.AQUA + "===============");
                            player.sendMessage(ChatColor.YELLOW + "Viewing " + targetPlayer.getName() + "'s warnings");
                            for (Object s: warnings)
                            {
                                player.sendMessage(ChatColor.YELLOW + String.valueOf(s));
                            }
                            player.sendMessage(ChatColor.AQUA + "===========================================");
                            return true;
                        }
                        else
                        {
                            player.sendMessage(prefix + ChatColor.GREEN + targetPlayer.getName() + " doesn't have any warnings to view!");
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
