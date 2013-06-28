package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ListOff implements CommandExecutor {

    private KustomWarn plugin;

    public ListOff(KustomWarn plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        Server server = Bukkit.getServer();
        if (command.getName().equalsIgnoreCase("blist")){
            if (args.length == 0){
                OfflinePlayer[] offlinePlayer = server.getOfflinePlayers().clone();
                sender.sendMessage(String.valueOf(offlinePlayer));
            }
        }
        return true;
    }
}
