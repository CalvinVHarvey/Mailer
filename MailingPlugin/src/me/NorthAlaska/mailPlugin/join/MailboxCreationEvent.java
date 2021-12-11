package me.NorthAlaska.mailPlugin.join;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
//import org.bukkit.block.data.type.Chest;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import me.NorthAlaska.mailPlugin.MailBox;
import me.NorthAlaska.mailPlugin.Main;
import me.NorthAlaska.mailPlugin.*;
import me.NorthAlaska.mailPlugin.utils.Utils;

public class MailboxCreationEvent implements Listener
{
	@SuppressWarnings("unused")
	private Main plugin;
	
	public MailboxCreationEvent(Main plugin)
	{
		this.plugin = plugin;   
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
		
	}
	
	
	@SuppressWarnings("unused")
	@EventHandler
	public void vaultDetect(SignChangeEvent e)
	{
		Player p = e.getPlayer();
		Block b = e.getBlock();
		String text = e.getLine(0);
		
		int x = b.getX();
		int y = b.getY();
		int z = b.getZ();
		
		Location l = new Location(p.getWorld(), x, y, z);
		
		int[] cords = findChest(x, y, z, p.getWorld());
		
		if(text.toLowerCase().equals("[mailbox]") && plugin.getP().findMailIndex(p.getName()) == -1)
		{
			e.setLine(0, Utils.chat("[&7&lMail Box&r]"));
			e.setLine(1, Utils.chat("&7&l" +p.getName()));
			
			p.sendMessage(Utils.chat("&aSuccessfully Created a Mailbox"));
			String owner = p.getName();
			Location temp = new Location(p.getWorld(), cords[0], cords[1], cords[2]);
			Chest chest = (Chest)temp.getBlock().getState();
			String world = chest.getWorld().getName();
			
			MailBox mail = new MailBox(chest, owner, l, cords[0], cords[1], cords[2], world, plugin);
			
			plugin.getP().addMailbox(mail);
			
			plugin.getD().saveData("Mailboxes.data", plugin.getP().getPostOffice());
		}
		else if(text.toLowerCase().equals("[mailbox]") && plugin.getP().findMailIndex(p.getName()) != -1)
		{
			p.sendMessage(Utils.chat("&cYou already have a Mail Box"));
		}
	}
	
	public int[] findChest(int sX, int sY, int sZ, World world)
	{
		int[] cord = new int[3];
		
		Location l = new Location(world, sX - 1, sY, sZ);
		Location lo = new Location(world, sX + 1, sY, sZ);
		Location loc = new Location(world, sX, sY, sZ - 1);
		Location loca = new Location(world, sX, sY, sZ + 1);
		
		Material target = Material.CHEST; 
		
		if(l.getBlock().getType() == target)
		{
			cord[0] = sX - 1;
			cord[1] = sY;
			cord[2] = sZ;
		}
		else if(lo.getBlock().getType() == target)
		{
			cord[0] = sX + 1;
			cord[1] = sY;
			cord[2] = sZ;
		}
		else if(loc.getBlock().getType() == target)
		{
			cord[0] = sX;
			cord[1] = sY;
			cord[2] = sZ - 1;
		}
		else if(loca.getBlock().getType() == target)
		{
			cord[0] = sX;
			cord[1] = sY;
			cord[2] = sZ + 1;
		}
		
		return cord; 
	}
	
}
