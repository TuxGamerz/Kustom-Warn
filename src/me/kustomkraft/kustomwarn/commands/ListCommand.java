package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarnMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {

    private KustomWarnMain plugin;

    public ListCommand(KustomWarnMain plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        Player targetPlayer = sender.getServer().getPlayer(args[0]);
        String prefix = ChatColor.GREEN + "[Kustom Warn]";
        if(commandLabel.equalsIgnoreCase("list")){
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    return true;
                }else if (args.length == 1) {
                    if (targetPlayer != null) {
                        if (!targetPlayer.hasPermission("kustomwarn.exempt")) {
                            if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == 0) {
                                consoleSender.sendMessage(prefix + ChatColor.GOLD + targetPlayer.getName() + " has not received any warnings!");
                                return true;
                            }else{
                                consoleSender.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)");
                                return true;
                            }
                        }
                        return true;
                    }
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                    return true;
                }
            } else {
                Player player = (Player)sender;
                if (args.length == 0) {
                    if (this.plugin.warnedPlayers.getWarnings(player.getName()) == 0) {
                        player.sendMessage(prefix + ChatColor.GOLD + "You have not received any warnings!");
                        return true;
                    }else{
                        player.sendMessage(prefix + ChatColor.RED + "You have received " + this.plugin.warnedPlayers.getWarnings(player.getName()) + " warnings!");
                        return true;
                    }
                }else if (args.length == 1) {
                    if (player.hasPermission("kustomwarn.check")) {
                        if (targetPlayer != null) {
                            if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == 0) {
                                player.sendMessage(prefix + ChatColor.GOLD + targetPlayer.getName() + " has not received any warnings!");
                                return true;
                            }
                            player.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)");
                            return true;
                        }else{
                            player.sendMessage(prefix + ChatColor.RED + "Player not found!");
                            return true;
                        }
                    }else{
                        player.sendMessage(prefix + ChatColor.RED + "You don't have permission to perform this command!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
