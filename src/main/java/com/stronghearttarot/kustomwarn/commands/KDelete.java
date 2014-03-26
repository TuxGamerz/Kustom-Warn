package com.stronghearttarot.kustomwarn.commands;

import com.stronghearttarot.kustomwarn.KustomWarn;
import com.stronghearttarot.kustomwarn.utils.Warnings;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KDelete implements CommandExecutor {

    private KustomWarn plugin;

    public KDelete(KustomWarn instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        ConsoleCommandSender consoleSender = plugin.getServer().getConsoleSender();
        if (commandLabel.equalsIgnoreCase("kdelete"))
        {
            if (!(sender instanceof Player))
            {
                if (args.length < 2)
                {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /kdelete [player] [warning number]");
                    return true;
                }
                else
                {
                    List<Warnings> warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", args[0]).findList();
                    if (!warnings.isEmpty())
                    {
                        for (Warnings warning : warnings)
                        {
                            if (warning.getWarningNumber().equals(String.valueOf(args[1])))
                            {
                                plugin.getDatabase().delete(warning);
                            }
                        }
                    }
                    consoleSender.sendMessage(prefix + ChatColor.GREEN + "Warning " + String.valueOf(args[1]) + " for " + args[0] + " has been deleted!");
                    return true;
                }
            }
            else
            {
                Player player = (Player) sender;
                if (args.length < 2)
                {
                    player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /kdelete [player] [warning number]");
                    return true;
                }
                else
                {
                    List<Warnings> warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", args[0]).findList();
                    if (!warnings.isEmpty())
                    {
                        for (Warnings warning : warnings)
                        {
                            if (warning.getWarningNumber().equals(String.valueOf(args[1])))
                            {
                                plugin.getDatabase().delete(warning);
                            }
                        }
                        consoleSender.sendMessage(prefix + ChatColor.GREEN + "Warning " + String.valueOf(args[1]) + " for " + args[0] + " has been deleted!");
                    }
                    return true;
                }
            }
        }
        return true;
    }
}
