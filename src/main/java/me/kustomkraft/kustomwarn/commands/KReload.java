package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class KReload implements CommandExecutor
{

    private KustomWarn plugin;

    public KReload(KustomWarn instance)
    {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args)
    {
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        ConsoleCommandSender consoleSender = plugin.getServer().getConsoleSender();
        if (commandLabel.equalsIgnoreCase("kreload"))
        {
            if (!(sender instanceof Player))
            {
                plugin.reloadConfig();
                consoleSender.sendMessage(prefix + ChatColor.BLUE + "Config reloaded");
            }
            else
            {
                Player player = (Player) sender;
                if (player.hasPermission("kustomwarn.reload") || player.isOp())
                {
                    plugin.reloadConfig();
                    player.sendMessage(prefix + ChatColor.BLUE + "Config reloaded");
                }
            }
        }
        return true;
    }

}
