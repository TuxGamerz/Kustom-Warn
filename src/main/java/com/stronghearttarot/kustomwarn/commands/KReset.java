package com.stronghearttarot.kustomwarn.commands;

//Internal imports
import com.stronghearttarot.kustomwarn.KustomWarn;
import com.stronghearttarot.kustomwarn.utils.Warnings;

//Bukkit imports
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

//Java imports
import java.util.List;

public class KReset implements CommandExecutor
{

    private KustomWarn plugin;

    public KReset(KustomWarn instance)
    {
        plugin = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        ConsoleCommandSender consoleSender = plugin.getServer().getConsoleSender();
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        if (commandLabel.equalsIgnoreCase("kreset"))
        {
            if (!(sender instanceof Player))
            {
                if (args.length < 1)
                {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /kreset [player]");
                    return true;
                }
                else
                {
                    List<Warnings>warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", args[0]).findList();
                    for (Warnings warning : warnings)
                    {
                        plugin.getDatabase().delete(warning);
                    }
                    return true;
                }
            }
            else
            {
                Player player = (Player) sender;
                if (player.hasPermission("kustomwarn.reset") | player.isOp())
                {
                    if (args.length < 1)
                    {
                        player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /kreset [player]");
                        return true;
                    }
                    else
                    {
                        List<Warnings>warnings = plugin.getDatabase().find(Warnings.class).where().ieq("playerName", args[0]).findList();
                        for (Warnings warning : warnings)
                        {
                            plugin.getDatabase().delete(warning);
                        }
                        return true;
                    }
                }
            }
        }
        return true;
    }
}
