package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class KWarn implements CommandExecutor {

    private KustomWarn plugin;
    public String warningReason = null;
    public ArrayList<String> offlineWarnings = new ArrayList();

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
                        String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                        if (plugin.getConfig().getBoolean("Alert Admins")) {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by a console user!");
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning Message"));
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName())) + " time(s)!");
                            plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), "Console User", getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                            return true;
                        } else {
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                            plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), "console user", getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                        }
                        return true;
                    } else {
                        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                        String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                        if (offlinePlayer.getLastPlayed() != 0) {
                            if (plugin.getConfig().getBoolean("Alert Admin")) {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned by a console user");
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), "console user", getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    offlinePlayer.setBanned(true);
                                }
                                return true;
                            } else {
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), "console user", getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    offlinePlayer.setBanned(true);
                                }
                                return true;
                            }
                        } else {
                            consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                            return true;
                        }
                    }
                } else if (args.length >= 2) {
                    Player targetPlayer = consoleSender.getServer().getPlayer(args[0]);
                    if (targetPlayer != null) {
                        String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                        if (plugin.getConfig().getBoolean("Alert Admins")) {
                            Command.broadcastCommandMessage(sender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by a console user for " + reason);
                            targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName())) + " time(s)");
                            plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), "console user", reason, getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                            return true;
                        } else {
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                            consoleSender.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " is not currently online and will receive their warning when they join!");
                            plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), "console user", reason, getDate());
                            plugin.warnedPlayers.save();
                            if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                            } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                targetPlayer.setBanned(true);
                            }
                            return true;
                        }
                    } else {
                        OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                        String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                        if (offlinePlayer.getLastPlayed() != 0) {
                            if (plugin.getConfig().getBoolean("Alert Admin")) {
                                Command.broadcastCommandMessage(sender, prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned by a console user for " + reason);
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), "console user", reason, getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    offlinePlayer.setBanned(true);
                                }
                                return true;
                            } else {
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                consoleSender.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), "console user", reason, getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    offlinePlayer.setBanned(true);
                                }
                                return true;
                            }
                        } else {
                            consoleSender.sendMessage(prefix + ChatColor.RED + "Player not found!");
                            return true;
                        }
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
                            String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                            if (plugin.getConfig().getBoolean("Alert Admins")) {
                                Command.broadcastCommandMessage(consoleSender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by " + player.getName());
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning"));
                                player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                                plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), player.getName(), getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                                } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                    targetPlayer.setBanned(true);
                                }
                                return true;
                            } else {
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                                player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                                plugin.warnedPlayers.add(indexNumber, targetPlayer.getName(), player.getName(), getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                                } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                    targetPlayer.setBanned(true);
                                }
                                return true;
                            }
                        } else {
                            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                            String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                            if (offlinePlayer.getLastPlayed() != 0) {
                                if (plugin.getConfig().getBoolean("Alert Admin")) {
                                    Command.broadcastCommandMessage(consoleSender, prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned by " + player.getName());
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                    plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), player.getName(), getDate());
                                    plugin.warnedPlayers.save();
                                    if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                        offlinePlayer.setBanned(true);
                                    }
                                    return true;
                                } else {
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                    offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                    plugin.warnedPlayers.add(indexNumber, offlinePlayer.getName(), player.getName(), getDate());
                                    plugin.warnedPlayers.save();
                                    if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                        offlinePlayer.setBanned(true);
                                    }
                                    return true;
                                }
                            } else {
                                player.sendMessage(prefix + ChatColor.RED + "Player not found!");
                                return true;
                            }
                        }
                    } else if (args.length >= 2) {
                        Player targetPlayer = player.getServer().getPlayer(args[0]);
                        if (targetPlayer != null) {
                            String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1);
                            if (plugin.getConfig().getBoolean("Alert Admins")) {
                                Command.broadcastCommandMessage(consoleSender, prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned by " + player.getName() + " for " + reason);
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                                player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                                plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), player.getName(), reason, getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                                } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                    targetPlayer.setBanned(true);
                                }
                                return true;
                            } else {
                                targetPlayer.sendMessage(prefix + ChatColor.RED + plugin.getConfig().getString("Warning For") + " " + reason);
                                player.sendMessage(prefix + ChatColor.YELLOW + targetPlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) + 1) + " time(s)!");
                                plugin.warnedPlayers.addReason(indexNumber, targetPlayer.getName(), player.getName(), reason, getDate());
                                plugin.warnedPlayers.save();
                                if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Kick After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Kick Message"));
                                } else if (plugin.warnedPlayers.getWarnings(targetPlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                    targetPlayer.kickPlayer(plugin.getConfig().getString("Ban Message"));
                                    targetPlayer.setBanned(true);
                                }
                                return true;
                            }
                        } else {
                            OfflinePlayer offlinePlayer = plugin.getServer().getOfflinePlayer(args[0]);
                            String indexNumber = String.valueOf(plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1);
                            if (offlinePlayer.getLastPlayed() != 0) {
                                if (plugin.getConfig().getBoolean("Alert Admin")) {
                                    Command.broadcastCommandMessage(consoleSender, prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned by a console user for " + reason);
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                    offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                    plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), player.getName(), reason, getDate());
                                    plugin.warnedPlayers.save();
                                    if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                        offlinePlayer.setBanned(true);
                                    }
                                    return true;
                                } else {
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " has been warned " + (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) + 1) + " time(s)!");
                                    player.sendMessage(prefix + ChatColor.YELLOW + offlinePlayer.getName() + " is not currently online and will receive their warning when they join!");
                                    offlineWarnings.add(offlineWarnings.size() + 1, offlinePlayer.getName());
                                    plugin.warnedPlayers.addReason(indexNumber, offlinePlayer.getName(), player.getName(), reason, getDate());
                                    plugin.warnedPlayers.save();
                                    if (plugin.warnedPlayers.getWarnings(offlinePlayer.getName()) == plugin.getConfig().getInt("Ban After")) {
                                        offlinePlayer.setBanned(true);
                                    }
                                    return true;
                                }
                            }else{
                                player.sendMessage(prefix + ChatColor.RED + "Player not found");
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return true;
    }
}
