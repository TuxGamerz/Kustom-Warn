package me.kustomkraft.kustomwarn.commands;

//Internal imports
import me.kustomkraft.kustomwarn.KustomWarn;

//Bukkit imports
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class KReset implements CommandExecutor
{

    private KustomWarn plugin;

    public KReset(KustomWarn instance)
    {
        plugin = instance;
    }


    @Override
    public boolean onCommand(CommandSender sender, Command command, String commandLabel, String[] args) {
        ConsoleCommandSender consoleSender = plugin.getServer().getConsoleSender();
        if (commandLabel.equalsIgnoreCase("kreset"))
        {
            if (!(sender instanceof Player))
            {

            }
            else
            {

            }
        }
        return true;
    }
}
