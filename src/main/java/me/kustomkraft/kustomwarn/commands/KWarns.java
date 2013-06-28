package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class KWarns implements CommandExecutor {

    private KustomWarn plugin;

    public KWarns(KustomWarn plugin) {
        this.plugin = plugin;
    }
    //TODO once the dbstore class is working implement it here for a player to list their own  warnings
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
                    if (plugin.warnedPlayers.getWarnings(player.getName()) == 0) {
                        player.sendMessage(prefix + ChatColor.BLUE + "You have not received any warnings!");
                        return true;
                    } else {
                        player.sendMessage(prefix + ChatColor.RED + "You have received " + plugin.warnedPlayers.getWarnings(player.getName()) + " warning(s)");
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
