package me.NorthAlaska.mailPlugin.join;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.Properties;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import me.NorthAlaska.mailPlugin.Main;
import me.NorthAlaska.mailPlugin.PostalService;
import me.NorthAlaska.mailPlugin.utils.Utils;

public class BreakListener implements Listener
{
	private Main plugin;
	private Properties p = new Properties();
	private static Material[] signs = {Material.OAK_WALL_SIGN, Material.BIRCH_WALL_SIGN, Material.SPRUCE_WALL_SIGN, Material.DARK_OAK_WALL_SIGN, Material.ACACIA_WALL_SIGN, Material.CRIMSON_WALL_SIGN, Material.WARPED_WALL_SIGN};
	
	public BreakListener(Main plugin)
	{
		this.plugin = plugin; 
		Bukkit.getPluginManager().registerEvents(this, plugin);
		
	}
	
	//Getter Methods
	public static Material[] getSigns()
	{
		return signs;
	}
	
	@EventHandler
	public void breakEvent(BlockBreakEvent e)
	{
		Block b = e.getBlock();
		
		World w = b.getWorld();
		
		if (isSign(signs, b))
		{
			Sign theSign = (Sign)b.getState();
			String signText = "&7&l" +e.getPlayer().getName();
			String ownerOfBox = theSign.getLine(1).substring(4);
			
			if (theSign.getLine(0).equals(Utils.chat("[&7&lMail Box&r]")))
			{
				int index = plugin.getP().findMailIndex(ownerOfBox);
				if (index != -1)
				{
					plugin.getP().removeMailbox(index);
					Bukkit.getConsoleSender().sendMessage(Utils.chat(plugin.getD().toString()));
					e.getPlayer().sendMessage(Utils.chat("&cMailbox Deleted"));
				}
			}
		}
		else if (b.getType() == Material.CHEST)
		{
			int[] cords = findSign(b.getX(), b.getY(), b.getZ(), signs, w);
			
			if (cords != null)
			{
				Location temp5 = new Location(w, cords[0], cords[1], cords[2]);
				Sign s = (Sign) temp5.getBlock().getState();
				int index = plugin.getP().findMailIndex(s.getLine(1).substring(4));
				if (s.getLine(0).equals(Utils.chat("[&7&lMail Box&r]")))
				{
					if (index != -1)
					{
						plugin.getP().removeMailbox(index);
					}
					e.getPlayer().sendMessage(Utils.chat("&cMailbox Deleted"));
				}
			}
		}
	}
	
	
	
	/*
	 * Searches around the area around the chest and finds where the sign might be that has the MailBox data
	 * you enter 3 integers for the coordinates a arraylist of the sign ids and the world of the chest, and will return the cords of the sign in an array
	 */
	public static int[] findSign(int sX, int sY, int sZ, Material[] signs, World world)
	{
		int[] cord = new int[3];
		
		Location l = new Location(world, sX - 1, sY, sZ);
		Location lo = new Location(world, sX + 1, sY, sZ);
		Location loc = new Location(world, sX, sY, sZ - 1);
		Location loca = new Location(world, sX, sY, sZ + 1);
		
		Material loc1 = l.getBlock().getType();
		Material loc2 = lo.getBlock().getType();
		Material loc3 = loc.getBlock().getType();
		Material loc4 = loca.getBlock().getType();
		
		for (Material m: signs)
		{
			if(loc1 == m)
			{
				cord[0] = sX - 1;
				cord[1] = sY;
				cord[2] = sZ;
			}
		
			else if(loc2 == m)
			{
				cord[0] = sX + 1;
				cord[1] = sY;
				cord[2] = sZ;
			}
			else if(loc3 == m)
			{
				cord[0] = sX;
				cord[1] = sY;
				cord[2] = sZ - 1;
			}
			else if(loc4 == m)
			{
				cord[0] = sX;
				cord[1] = sY;
				cord[2] = sZ + 1;
			}
			else
			{
				return null;
			}
		}
		
		return cord; 
	}
	
	
	//Checks if a block is a sign you enter a array with the sign block ids and the block you want to test
	public boolean isSign(Material[] signs, Block block)
	{
		for (int i = 0; i < signs.length; i++)
		{
			if (signs[i] == block.getType())
			{
				return true;
			}
		}
		return false;
	}
}
