package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarnMain;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import sun.util.resources.CalendarData_mk;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ListCommand implements CommandExecutor {

    private KustomWarnMain plugin;

    public ListCommand(KustomWarnMain plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        Player targetPlayer = sender.getServer().getPlayer(args[0]);
        if(commandLabel.equalsIgnoreCase("list")){
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    consoleSender.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + "Not enough arguments!");
                    return true;
                }else if (args.length == 1) {
                    if (targetPlayer != null) {
                        if (!targetPlayer.hasPermission("kustomwarn.exempt")) {
                            if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == 0) {
                                consoleSender.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.GOLD + targetPlayer.getName() + " has not received any warnings!");
                                return true;
                            }
                            consoleSender.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + targetPlayer.getName() + " has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)");
                            return true;
                        }

                        return true;
                    }
                    consoleSender.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + "Player not found!");
                    return true;
                }
            } else {
                Player player = (Player)sender;
                if (player.hasPermission("kustomwarn.check")) {
                    if (args.length == 0) {
                        if (this.plugin.warnedPlayers.getWarnings(player.getName()) == 0) {
                            player.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.GOLD + "You have not received any warnings!");
                            return true;
                        }
                        player.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + "You have received " + this.plugin.warnedPlayers.getWarnings(player.getName()) + " warnings!");
                    }else if (args.length == 1) {
                        if (player.hasPermission("kustomwarn.check")) {
                            if (targetPlayer != null) {
                                if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == 0) {
                                    player.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.GOLD + targetPlayer.getName() + " has not received any warnings!");
                                    return true;
                                }
                                player.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + targetPlayer.getName() + " has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)");
                                return true;
                            }
                            player.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + "Player not found!");
                            return true;
                        }
                        player.sendMessage(ChatColor.GREEN + "[Kustom Warn]" + ChatColor.RED + "You don't have permission to perform this command!");
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
