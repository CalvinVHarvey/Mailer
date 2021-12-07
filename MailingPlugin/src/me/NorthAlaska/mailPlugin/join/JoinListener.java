package me.NorthAlaska.mailPlugin.join;

import me.NorthAlaska.mailPlugin.*;
import me.NorthAlaska.mailPlugin.utils.Utils;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener
{
	private static Main plugin;
	
	public JoinListener(Main plugin)
	{
		this.plugin = plugin; 
		Bukkit.getPluginManager().registerEvents(this, plugin);
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent e)
	{
		Player p = e.getPlayer();
		
		if (!p.hasPlayedBefore())
		{
			Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("firstjoin_message").replace("<player>", p.getName())));
			p.sendMessage("Hello this is a test :)");
		}
		else
		{
			Bukkit.broadcastMessage(Utils.chat(plugin.getConfig().getString("join_message").replace("<player>", p.getName())));
		}
	}
	
}
