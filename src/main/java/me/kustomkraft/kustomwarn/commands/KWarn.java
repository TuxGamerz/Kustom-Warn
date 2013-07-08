package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import me.kustomkraft.kustomwarn.utils.LocalStore;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

public class KWarn implements CommandExecutor {

    private KustomWarn plugin;
    private LocalStore warningPlayers;
    public String warningReason = null;
    public ArrayList<String> offlineWarnings = new ArrayList();
    private Logger logger = Bukkit.getLogger();
    public String offenderName;
    public String adminName;

    public KWarn(KustomWarn plugin) {
        this.plugin = plugin;
    }

    public String getDate() {
        String formattedDate = "";
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm dd-MM-yy");
        formattedDate = dateFormat.format(date);
        return formattedDate;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ConsoleCommandSender consoleSender = sender.getServer().getConsoleSender();
        String prefix = ChatColor.GREEN + "[Kustom Warn]";
        String reason = " ";
        String playerName;
        if (args.length < 1){
            sender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
            sender.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player] (reason)");
            return true;
        } else {
            playerName = args[0];
        }
        Server server = plugin.getServer();
        //Player targetPlayer = plugin.getServer().getPlayer(playerName);
        //String warningTotal;
        /*if (targetPlayer != null){
            offenderName = targetPlayer.getName();
            if (warningPlayers.getWarningTotal(targetPlayer.getName()) == 0){
                warningTotal = String.valueOf(0);
            } else {
                warningTotal = String.valueOf(warningPlayers.getWarningTotal(targetPlayer.getName()));
            }
        } else if (targetPlayer.getLastPlayed() != 0 && server.getOfflinePlayer(targetPlayer.getName()) != null) {
            offenderName = targetPlayer.getName();
            sender.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " is not online!");
            return true;
        }else {
            sender.sendMessage(prefix + ChatColor.RED + "Player not found!");
            return true;
        } */
        for (int i = 1; i < args.length; i++) {
            reason += args[i] + " ";
        }
        if (commandLabel.equalsIgnoreCase("kwarn")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player] (reason)");
                    return true;
                } else if (args.length == 1) {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer != null) {
                        offenderName = targetPlayer.getName();
                        if (plugin.getConfig().getBoolean("Alert Admins")) {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by a console user!");
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarningTotal(targetPlayer.getName())) + " time(s)!");
                            /*if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                //consoleSender.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + " time(s)!");
                                return true;
                            }*/
                            adminName = "Console User";
                            warningPlayers.addWarning(targetPlayer.getName(), adminName, getDate());
                            plugin.saveCustomConfiguration();
                            plugin.reloadCustomConfiguration();
                            return true;
                        } else {
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) + 1) + " time(s)!");
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                            /*if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                //consoleSender.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + warningTotal + " time(s)!");
                                return true;
                            }*/
                            adminName = "Console User";
                            warningPlayers.addWarning(targetPlayer.getName(), adminName, getDate());
                            plugin.saveCustomConfiguration();
                            plugin.reloadCustomConfiguration();
                            return true;
                        }
                    } else {
                        consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                        return true;
                    }
                } else if (args.length >= 2) {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer != null) {
                        offenderName = targetPlayer.getName();
                        if (plugin.getConfig().getBoolean("Alert Admins")) {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by a console user for " + reason);
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarningTotal(targetPlayer.getName())) + " time(s)");
                            /*if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                //consoleSender.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + warningTotal + " time(s)!");
                                return true;
                            } */
                            adminName = "Console User";
                            warningPlayers.addWarningReason(targetPlayer.getName(), adminName, reason,getDate());
                            plugin.saveCustomConfiguration();
                            plugin.reloadCustomConfiguration();
                            return true;
                        } else {
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarningTotal(targetPlayer.getName())) + " time(s)");
                            /*if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                //consoleSender.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + warningTotal + " time(s)!");
                                return true;
                            }*/
                            adminName = "Console User";
                            warningPlayers.addWarningReason(targetPlayer.getName(), adminName, reason,getDate());
                            plugin.saveCustomConfiguration();
                            plugin.reloadCustomConfiguration();
                            return true;
                        }
                    } else {
                        consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                        return true;
                    }
                }
            } else {
                Player player = (Player) sender;
                if (player.hasPermission("kustomwarn.warn") || player.isOp()) {
                    if (args.length == 0) {
                        player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /kwarn [player] (reason)");
                        return true;
                    } else if (args.length == 1) {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        if (targetPlayer != null) {
                            offenderName = targetPlayer.getName();
                            if (plugin.getConfig().getBoolean("Alert Admins")) {
                                Command.broadcastCommandMessage(consoleSender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by " + player.getName());
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning"));
                                player.sendMessage(prefix + ChatColor.YELLOW + "Player has been warned");
                                //if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                    //player.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                    //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + warningTotal + " time(s)!");
                                    //return true;
                                //}
                                adminName = player.getName();
                                warningPlayers.addWarning(targetPlayer.getName(), adminName, getDate());
                                plugin.saveCustomConfiguration();
                                plugin.reloadCustomConfiguration();
                                return true;
                            } else {
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                                adminName = player.getName();
                                warningPlayers.addWarning(targetPlayer.getName(), adminName, getDate());
                                plugin.saveCustomConfiguration();
                                plugin.reloadCustomConfiguration();
                                return true;
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Player not found!");
                            return true;
                        }
                    } else if (args.length >= 2) {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        if (targetPlayer != null) {
                            offenderName = targetPlayer.getName();
                            if (plugin.getConfig().getBoolean("Alert Admins")) {
                                Command.broadcastCommandMessage(consoleSender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by " + player.getName() + " for " + reason);
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                                player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) + 1) + " time(s)!");
                                /*if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                    //player.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                    //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + warningTotal + " time(s)!");
                                    adminName = player.getName();
                                    warningPlayers.addWarningReason(targetPlayer.getName(), adminName, reason,getDate());
                                    plugin.saveCustomConfiguration();
                                    plugin.reloadCustomConfiguration();
                                    return true;
                                }*/
                                return true;
                            } else {
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                                player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarningTotal(targetPlayer.getName()) + 1) + " time(s)!");
                                /*if (warningPlayers.getWarningTotal(targetPlayer.getName()) != 0){
                                    //player.sendMessage(prefix +ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + warningTotal + " time(s)!");
                                    //targetPlayer.sendMessage(prefix + ChatColor.RED + "You have been warned " + warningTotal + " time(s)!");
                                    adminName = player.getName();
                                    warningPlayers.addWarningReason(targetPlayer.getName(), adminName, reason,getDate());
                                    plugin.saveCustomConfiguration();
                                    plugin.reloadCustomConfiguration();
                                    return true;
                                }*/
                                return true;
                            }
                        } else {
                            player.sendMessage(prefix + ChatColor.RED + "Player not found");
                            return true;
                        }
                    }
                }
            }
        }
        return true;
    }
}
