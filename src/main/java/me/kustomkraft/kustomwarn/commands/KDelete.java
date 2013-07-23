package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import me.kustomkraft.kustomwarn.utils.LocalStore;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

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
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer != null)
                    {
                        LocalStore warning = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(args[1])).findUnique();
                        if (warning != null)
                        {
                            plugin.getDatabase().delete(warning);
                        }
                        return true;
                    }
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
                    Player targetPlayer = player.getServer().getPlayer(args[0]);
                    if (targetPlayer != null)
                    {
                        LocalStore warning = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", targetPlayer.getName()).ieq("warningNumber", String.valueOf(args[1])).findUnique();
                        if (warning != null)
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
