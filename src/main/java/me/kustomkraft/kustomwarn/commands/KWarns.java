package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import me.kustomkraft.kustomwarn.utils.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class KWarns implements CommandExecutor {

    private KustomWarn plugin;

    public KWarns(KustomWarn instance) {
        plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        String prefix = ChatColor.GREEN + "[Kustom Warn]";
        if (commandLabel.equalsIgnoreCase("kwarns")) {
            if (!(sender instanceof Player)) {
                consoleSender.sendMessage(prefix + ChatColor.RED + "This command can only be used by a player!");
                return true;
            } else {
                Player player = (Player) sender;
                if (args.length == 0){
                    List warnings = plugin.getDatabase().find(LocalStore.class).where().ieq("playerName", player.getName()).findList();
                    if (warnings != null){
                        player.sendMessage(ChatColor.AQUA + "===============" + ChatColor.YELLOW + " Kustom Warn " + ChatColor.AQUA + "===============");
                        for (Object s: warnings)
                        {
                            player.sendMessage(ChatColor.YELLOW + String.valueOf(s));
                        }
                        player.sendMessage(ChatColor.AQUA + "===========================================");
                        return true;
                    } else {
                        player.sendMessage(prefix + ChatColor.YELLOW + "You don't have any warnings to view");
                        return true;
                    }
                } else {
                    player.sendMessage(prefix + ChatColor.RED + "Too many arguments!");
                    player.sendMessage(prefix + ChatColor.RED + "Usage: /kwarns");
                    return true;
                }
            }
        }
        return true;
    }
}
