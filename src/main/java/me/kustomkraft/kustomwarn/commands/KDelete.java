package me.kustomkraft.kustomwarn.commands;

import me.kustomkraft.kustomwarn.KustomWarn;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class KDelete implements CommandExecutor {

    private KustomWarn plugin;

    public KDelete(KustomWarn plugin) {
        this.plugin = plugin;
    }
    /*TODO Make sure i can delete stuff when using the bukkit api for the database. If so implement the delete method from whatever documentation i can find
    */
    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        return true;
    }
}
