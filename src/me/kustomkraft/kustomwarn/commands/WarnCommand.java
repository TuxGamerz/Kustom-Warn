package me.kustomkraft.kustomwarn.commands;

import java.util.Calendar;
import java.util.Date;
import me.kustomkraft.kustomwarn.KustomWarnMain;
import org.bukkit.ChatColor;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class WarnCommand implements CommandExecutor{

    private KustomWarnMain plugin;
    private Date time = new Date();

    public String warningReason;
    public String selectedPlayer;
    public String admin;

    public WarnCommand(KustomWarnMain plugin) {
        this.plugin = plugin;
    }

    public String getDate(){
        String date;
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.setLenient(true);

        int hour = cal.get(Calendar.HOUR);
        int minute = cal.get(Calendar.MINUTE);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);

        date = String.format("%02d", hour) + ":" + String.format("%02d", minute) + " " + String.format("%02d", day) + "-" + String.format("%02d", month);
        return date;
    }

    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Server server = sender.getServer();
        ConsoleCommandSender consoleSender = server.getConsoleSender();
        String prefix = ChatColor.GREEN + "[Kustom Warn]";
        String reason = "";
        int kickCount = 0;
        for (int i = 1; i < args.length; i++) {
            reason = reason + args[i] + " ";
        }
        if (commandLabel.equalsIgnoreCase("warn")) {
            if (!(sender instanceof Player)) {
                if (args.length == 0) {
                    consoleSender.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                    return true;
                }if (args.length == 1) {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    int warningsKick = plugin.getConfig().getInt("Kick After");
                    int warningsBan = plugin.getConfig().getInt("Ban After") - plugin.warnedPlayers.getWarnings(targetPlayer.getName());
                    if (targetPlayer != null) {
                        if (!targetPlayer.hasPermission("kustomwarn.exempt")) {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "Player " + targetPlayer.getName() + " has been warned by a console user!");
                            targetPlayer.sendMessage(prefix + ChatColor.RED + this.plugin.getConfig().getString("Warning"));
                            selectedPlayer = targetPlayer.getName();
                            admin = "Console User";
                            plugin.warnedPlayers.add(targetPlayer.getName(), admin, getDate());
                            plugin.warnedPlayers.save();
                            if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Kick After")) {
                                targetPlayer.kickPlayer(this.plugin.getConfig().getString("Kick Message"));
                            } else if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Ban After")) {
                                targetPlayer.kickPlayer(this.plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "This player has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)!");
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsKick + " before they are kicked!");
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsBan + " before they are banned!");
                            return true;
                        }
                        consoleSender.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " is exempt from being warned!");
                        return true;
                    }

                    consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                    return true;
                }else if (args.length == 2) {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    int warningsKick = plugin.getConfig().getInt("Kick After") - plugin.warnedPlayers.getWarnings(targetPlayer.getName());
                    int warningsBan = plugin.getConfig().getInt("Ban After") - plugin.warnedPlayers.getWarnings(targetPlayer.getName());
                    if (targetPlayer != null) {
                        if (!targetPlayer.hasPermission("kustomwarn.exempt")) {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "Player " + targetPlayer.getName() + " has been warned by a console user for " + reason);
                            targetPlayer.sendMessage(prefix + ChatColor.RED + this.plugin.getConfig().getString("Warning For") + " " + reason);
                            warningReason = reason;
                            admin = "Console User";
                            plugin.warnedPlayers.addReason(targetPlayer.getName(), admin, reason, getDate());
                            plugin.warnedPlayers.save();
                            if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Kick After")) {
                                targetPlayer.kickPlayer(this.plugin.getConfig().getString("Kick Message"));
                            } else if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Ban After")) {
                                targetPlayer.kickPlayer(this.plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "This player has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)!");
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsKick + " before they are kicked!");
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsBan + " before they are banned!");
                            return true;
                        }
                        consoleSender.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " is exempt from being warned!");
                        return true;
                    }

                    consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                    return true;
                }
            } else {
                Player player = (Player)sender;
                if ((player.hasPermission("kustomwarn.warn")) || (player.isOp())) {
                    if (args.length == 0) {
                        player.sendMessage(prefix + ChatColor.RED + "Not enough arguments!");
                        player.sendMessage(prefix + ChatColor.RED + "Usage: /warn [player] {reason}");
                        return true;
                    }else if (args.length == 1) {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        int warningsKick = plugin.getConfig().getInt("Kick After");
                        int warningsBan = plugin.getConfig().getInt("Ban After") - plugin.warnedPlayers.getWarnings(targetPlayer.getName());
                        if (targetPlayer != null) {
                            if (!targetPlayer.hasPermission("kustomwarn.exempt")) {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "Player " + targetPlayer.getName() + " has been warned by " + player.getName());
                                targetPlayer.sendMessage(prefix + ChatColor.RED + this.plugin.getConfig().getString("Warning"));
                                admin = player.getName();
                                plugin.warnedPlayers.add(targetPlayer.getName(), admin, getDate());
                                plugin.warnedPlayers.save();
                                if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Kick After")) {
                                    targetPlayer.kickPlayer(this.plugin.getConfig().getString("Kick Message"));
                                } else if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Ban After")) {
                                    targetPlayer.kickPlayer(this.plugin.getConfig().getString("Ban Message"));
                                    targetPlayer.setBanned(true);
                                }
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "This player has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)!");
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsKick + " before they are kicked!");
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsBan + " before they are banned!");
                                return true;
                            }
                            player.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " is exempt from being warned!");
                            return true;
                        }

                        player.sendMessage(prefix + ChatColor.RED + "Player not found!");
                        return true;
                    }else if (args.length >= 2) {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        int warningsKick = plugin.getConfig().getInt("Kick After");
                        int warningsBan = plugin.getConfig().getInt("Ban After") - plugin.warnedPlayers.getWarnings(targetPlayer.getName());
                        if (targetPlayer != null) {
                            if (!targetPlayer.hasPermission("kustomwarn.exempt")) {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "Player " + targetPlayer.getName() + " has been warned by " + player.getName() + " for " + reason);
                                targetPlayer.sendMessage(prefix + ChatColor.RED + this.plugin.getConfig().getString("Warning For") + " " + reason);
                                warningReason = reason;
                                admin = player.getName();
                                plugin.warnedPlayers.addReason(targetPlayer.getName(), admin, reason, getDate());
                                plugin.warnedPlayers.save();
                                if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Kick After")) {
                                    targetPlayer.kickPlayer(this.plugin.getConfig().getString("Kick Message"));
                                } else if (this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == this.plugin.getConfig().getInt("Ban After")) {
                                    targetPlayer.kickPlayer(this.plugin.getConfig().getString("Ban Message"));
                                    targetPlayer.setBanned(true);
                                }
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "This player has been warned " + this.plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + " time(s)!");
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsKick + " before they are kicked!");
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.AQUA + "They have " + warningsBan + " before they are banned!");
                                return true;
                            }
                            player.sendMessage(prefix + ChatColor.RED + targetPlayer.getName() + " is exempt from being warned!");
                            return true;
                        }
                        player.sendMessage(prefix + ChatColor.RED + "Player not found!");
                        return true;
                    }
                    player.sendMessage(prefix + ChatColor.RED + "You don't have permission to use this command!");
                    return true;
                }
            }
        }
        return true;
    }
}

/* Location:           C:\Users\gamerz\Downloads\Kustom_Warn.jar
 * Qualified Name:     me.kustomkraft.kustomwarn.commands.WarnCommand
 * JD-Core Version:    0.6.2
 */