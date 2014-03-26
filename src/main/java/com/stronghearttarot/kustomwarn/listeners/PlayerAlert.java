package com.stronghearttarot.kustomwarn.listeners;

import com.stronghearttarot.kustomwarn.KustomWarn;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.util.logging.Logger;

@SuppressWarnings("unused")
public class PlayerAlert implements Listener
{

    protected Logger log = Bukkit.getLogger();

    private KustomWarn plugin;

    public PlayerAlert(KustomWarn instance)
    {
        plugin = instance;
    }

    @EventHandler
    public void onPlayerJoin(PlayerLoginEvent event)
    {
        String prefix = (ChatColor.BOLD + (ChatColor.BLUE + "[")) + (ChatColor.RESET + (ChatColor.YELLOW + "Kustom Warn")) + (ChatColor.BOLD + (ChatColor.BLUE + "]")) + ChatColor.RESET;
        Player player = event.getPlayer();
        if (plugin.getConfig().getBoolean("Alert Players"))
        {
            player.sendMessage(prefix + ChatColor.AQUA + "This server is protected by kustom warn! Please obey the rules!");
        }
    }

}
