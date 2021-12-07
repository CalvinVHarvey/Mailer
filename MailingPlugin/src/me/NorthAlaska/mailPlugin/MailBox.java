package me.NorthAlaska.mailPlugin;

import java.io.Serializable;
import java.util.ArrayList;

import me.NorthAlaska.mailPlugin.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;

public class MailBox implements Serializable 
{
	private static transient final long serialVersionUID = -1681012206529286330L;
	private String owner;
	private transient Chest chest;
	private transient Location sign;
	private transient Main plugin;
	
	private int x;
	private int y;
	private int z;
	
	private String world; 
	
	public MailBox(Chest chest, String owner, Location l, int x, int y, int z, String d, Main plugin)
	{
		this.chest = chest;
		this.owner = owner;
		sign = l;
		this.x = x;
		this.y = y;
		this.z = z;
		this.world = d;
		this.plugin = plugin;
	}
	
	
	public void setPlugin(Main plugin)
	{
		this.plugin = plugin;
	}
	
//Getter Methods	
	public Location getSign()
	{
		return sign;
	}
	
	public Chest getChestFromCords()
	{
		Location l = new Location(plugin.getServer().getWorld(world), x, y, z);
		
		Chest temp = (Chest) l.getBlock().getState();
		
		return temp;
	}
	
	public String getOwner()
	{
		return owner;
	}
	
	public Chest getChest()
	{
		return chest;
	}
	
//Setter Methods
	public void setSign(int x, int y, int z)
	{
		Location l = new Location(chest.getWorld(), x, y, z);
		sign = l;
	}
	
	public void setOwner(String owner)
	{
		this.owner = owner;
	}
	public void setChest(Chest chest)
	{
		this.chest = chest;
	}
	
	public int getX()
	{
		return x;
	}
	public int getY()
	{
		return y;
	}
	public int getZ()
	{
		return z;
	}
	
//Additional Methods
	
	public void sendMail(ItemStack book)
	{
		chest.getInventory().addItem(book);
	}
	
	
	
}
