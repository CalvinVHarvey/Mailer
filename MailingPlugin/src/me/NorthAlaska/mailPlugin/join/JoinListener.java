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
		
		MailBox m = plugin.getP().findMailBox(p.getName());
		
		if (m != null)
		{
			if (m.hasMail())
			{
				p.sendMessage(Utils.chat("&aYou Have Mail!"));
			}
		}
	}
	
}
