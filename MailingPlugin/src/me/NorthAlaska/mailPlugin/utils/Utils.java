package me.NorthAlaska.mailPlugin.utils;

import org.bukkit.ChatColor;
import org.bukkit.persistence.PersistentDataContainer;

public class Utils 
{
	public Utils()
	{
		
	}
	
	
	//Makes it so you can use essentials like color codes in the code when using Strings
	public static String chat(String s)
	{
		return ChatColor.translateAlternateColorCodes('&', s);
	}
	
	
	

	
	
}
