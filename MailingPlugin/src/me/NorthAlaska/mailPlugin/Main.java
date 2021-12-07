package me.NorthAlaska.mailPlugin;

import me.NorthAlaska.mailPlugin.commands.SendMailCommand;
import me.NorthAlaska.mailPlugin.join.BreakListener;
import me.NorthAlaska.mailPlugin.join.JoinListener;
import me.NorthAlaska.mailPlugin.join.MailboxCreationEvent;
import me.NorthAlaska.mailPlugin.utils.Utils;

import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


	 
public class Main extends JavaPlugin 
{
	
	Gson g = new Gson();
	
	public PostalService p = new PostalService();
	
	Data d = new Data();
	
	
	//Initiates the start up process as well as loads all the data that was saved
	@Override
	public void onEnable ()
	{
		new SendMailCommand(this);
		saveDefaultConfig();
		new JoinListener(this);
		new MailboxCreationEvent(this);
		new BreakListener(this);
		//new PostalService();
		new Utils();
		Data temp = Data.loadData("Mailboxes.data");
		if (Data.loadData("Mailboxes.data") != null)
		{
			Bukkit.getConsoleSender().sendMessage(Utils.chat("&aSuccessfuly Loaded MailBoxes"));
			Bukkit.getConsoleSender().sendMessage(Utils.chat(temp.toString()));
			if (p.getPostOffice() != null || temp.getArray() != null && temp.getArray().size() > 0)
			{
				p.setPostOffice(temp.getArray());
			}
			else
			{     
				p.setPostOffice(new ArrayList<MailBox>());
			}
		}
		if (p.getPostOffice() != null && p.getPostOffice().size() > 0)
		{
			for (int i = 0; i < p.getPostOffice().size(); i++)
			{
				MailBox cur = p.getPostOffice().get(i);
				cur.setPlugin(this);
				
				if (this != null)
				{
					cur.setChest(cur.getChestFromCords());
				}
				int[] cords = BreakListener.findSign(cur.getX(), cur.getY(), cur.getZ(), BreakListener.getSigns(), cur.getChest().getWorld());
				cur.setSign(cords[0], cords[1], cords[2]);
			}
		}
		
	}
	//Getter Methods
	public Data getD()
	{
		return d;
	}
	
	public PostalService getP()
	{
		return p; 
	}
	
	//Saves all the Mail boxes on the map to save the data
	@Override
	public void onDisable ()
	{
		if (d.saveData("Mailboxes.data", p.getPostOffice()))
		{
			Bukkit.getConsoleSender().sendMessage(Utils.chat("&aSuccessfully saved the Mailboxes"));
		}
		else
		{
			Bukkit.getConsoleSender().sendMessage(Utils.chat("&cError saving MailBoxes"));
		}
		
		
	}
	
	
}
