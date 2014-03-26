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
        String bannerUpper = ChatColor.AQUA +  "================" + ChatColor.YELLOW + " Kustom Warn " + ChatColor.AQUA + "===============";
        String bannerLower = ChatColor.AQUA +  "============================================";
        if (commandLabel.equalsIgnoreCase("klist"))
        {
            if (!(sender instanceof Player))
            {
                consoleSender.sendMessage(prefix + ChatColor.RED + "This command can only be used by a player!");
                return true;
            }
            else
            {
                Player player = (Player) sender;
                if (player.hasPermission("kustomwarn.listself"))
                {
                    if (args.length == 0)
                    {
                        List<Warnings> warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", player.getName()).findList();
                        if (!warnings.isEmpty())
                        {
                            player.sendMessage(bannerUpper);
                            for (Warnings warning : warnings)
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
                                player.sendMessage(ChatColor.AQUA + warning.getWarningNumber() + " | Reason: " + warningReason + " | Admin: " + warning.getAdminName());
                            }
                            player.sendMessage(bannerLower);
                            return true;
                        }
                        else
                        {
                            player.sendMessage(prefix + ChatColor.YELLOW + "You don't have any warnings to view");
                            return true;
                        }
                    }
                    else
                    {
                        player.sendMessage(prefix + ChatColor.RED + "Too many arguments!");
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /klist");
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
