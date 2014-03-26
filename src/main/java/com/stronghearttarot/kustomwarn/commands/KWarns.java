package com.stronghearttarot.kustomwarn.commands;

import com.stronghearttarot.kustomwarn.KustomWarn;
import com.stronghearttarot.kustomwarn.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KWarns implements CommandExecutor
{

    private KustomWarn plugin;

    public KWarns(KustomWarn instance)
    {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        String bannerUpper = ChatColor.AQUA +  "================" + ChatColor.YELLOW + " Kustom Warn " + ChatColor.AQUA + "===============";
        String bannerLower = ChatColor.AQUA +  "============================================";
        if (commandLabel.equalsIgnoreCase("kwarns"))
        {
            if (!(sender instanceof Player))
            {
                if (args.length == 0)
                {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player]");
                    return true;
                }
                else
                {
                    List<Warnings> warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", args[0]).findList();
                    if (!warnings.isEmpty())
                    {
                        consoleSender.sendMessage(bannerUpper);
                        consoleSender.sendMessage(ChatColor.YELLOW + "Viewing " + args[0] + "\'s warnings: ");
                        for (Warnings warning: warnings)
                        {
                            String warningReason;
                            if (warning.getWarningReason() == null)
                            {
                                warningReason = "Not Supplied";
                            }
                            else
                            {
                                warningReason = warning.getWarningReason();
                            }
                            consoleSender.sendMessage(ChatColor.AQUA + String.valueOf(warning.getId()) + " | Reason: " + warningReason + " | Admin: " + warning.getAdminName());
                        }
                        consoleSender.sendMessage(ChatColor.AQUA + bannerLower);
                        return true;
                    }
                    else
                    {
                        consoleSender.sendMessage(prefix + ChatColor.GREEN + args[0] + " doesn't have any warnings to view");
                        return true;
                    }
                }
            }
            else
            {
                Player player = (Player) sender;
                if (args.length == 0)
                {
                    player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player]");
                    return true;
                }
                else
                {
                    if (player.hasPermission("kustomwarn.other") || player.isOp())
                    {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        List<Warnings> warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", args[0]).findList();
                        if (!warnings.isEmpty())
                        {
                            player.sendMessage(bannerUpper);
                            player.sendMessage(ChatColor.YELLOW + "Viewing " + targetPlayer.getName() + "\'s warnings: ");
                            for (Warnings warning: warnings)
                            {
                                String warningReason;
                                if (warning.getWarningReason() == null)
                                {
                                    warningReason = "Not Supplied";
                                }
                                else
                                {
                                    warningReason = warning.getWarningReason();
                                }
                                player.sendMessage(ChatColor.AQUA + String.valueOf(warning.getId()) + " | Reason: " + warningReason + " | Admin: " + warning.getAdminName());
                            }
                            player.sendMessage(ChatColor.AQUA + bannerLower);
                            return true;
                        }
                        else
                        {
                            player.sendMessage(prefix + ChatColor.GREEN + args[0] + " doesn\'t have any warnings to view!");
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }

}
