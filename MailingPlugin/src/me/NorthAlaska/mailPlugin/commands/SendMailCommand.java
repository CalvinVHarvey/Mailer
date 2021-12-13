package me.NorthAlaska.mailPlugin.commands;

import me.NorthAlaska.mailPlugin.*;
import me.NorthAlaska.mailPlugin.utils.Utils;

import org.bukkit.command.CommandExecutor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.ItemMeta;

import com.google.gson.Gson;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

//Send a book and quill to another player
//With text on it as a form of mail
public class SendMailCommand implements CommandExecutor
{
	private Main plugin;
	
	public SendMailCommand(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("sendMail").setExecutor(this);
	}
	
	/*
	 * Mail send command suppose to make it where you can do /sendmail or /send and then a players name it will send the book
	 * quil that you are holding in your hand to them and if they aren't online it will send it to their mail box if
	 * they have one
	 */
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
	{ 
		if(!(sender instanceof Player))
		{
			sender.sendMessage("This is only available to players!");
			return true;
		}
		
		Player p = (Player) sender;
		
		if (args.length == 0)
		{
			p.sendMessage(Utils.chat("&cYou must specify the player you wish to send mail to"));
			return true;
		}
		
		Player t = Bukkit.getPlayerExact(args[0]);
		ItemStack currentItem = p.getItemInHand();
		int cost = plugin.getConfig().getInt("mail_cost");
		String costItem = plugin.getConfig().getString("cost_material");
		
		if (t != null && t instanceof Player && t != sender)
		{
			if (p.hasPermission("mailer.send"))
			{
				if (p.getItemInHand().getType() == Material.WRITTEN_BOOK)
				{
					PlayerInventory inv = t.getInventory();
					PlayerInventory senderInv = p.getInventory();
					if (senderInv.contains(Material.matchMaterial(plugin.getConfig().getString("cost_material"))))
					{
						senderInv.removeItem(cost());
						inv.addItem(currentItem);
						senderInv.removeItem(currentItem);
						p.sendMessage(Utils.chat("&aSending Document to Recipient. . ."));
						t.sendMessage(Utils.chat("&aRecieved Mail from " +p.getName()));
					}
					else
					{
						p.sendMessage(Utils.chat("&cYou do not sufficient funds, cost is " +cost +" " +costItem));
					}
				}
				return true;
			}
			else
			{
				p.sendMessage("No Permissions to execute this.");
			}
		}
		else if(t == sender)
		{
			p.sendMessage(Utils.chat("&4You cannot send mail to yourself!"));
		}
		else
		{
			OfflinePlayer o = Bukkit.getOfflinePlayer(args[0]);
			String name = o.getName();
			MailBox target = plugin.getP().findMailBox(name);
			if (plugin.getP().findMailBox(name) == null)
			{
				p.sendMessage(Utils.chat("&cPlayer is not Online nor do they have a Mailbox."));
			}
			else
			{
				if (p.hasPermission("mailer.send"))
				{
					if (p.getItemInHand().getType() == Material.WRITTEN_BOOK)
					{
						PlayerInventory inv = p.getInventory();
						if (inv.contains(Material.matchMaterial(plugin.getConfig().getString("cost_material"))))
						{
							inv.removeItem(cost());
							target.sendMail(currentItem);
							inv.removeItem(currentItem);
							p.sendMessage(Utils.chat("&aSending Document to Recipient's Mail Box. . ."));
						}
						else
						{
							p.sendMessage(Utils.chat("&cYou do not have sufficient funds."));
						}
					}
				}
				return true;
			}
		}

		
		return false; 
	}
	
	public boolean isFull(PlayerInventory s)
	{
		ItemStack[] p = s.getContents();
		for (int i = 0; i < p.length; i++)
		{
			if (p[i] == null)
			{
				return false;
			}
		}
		return true;
	}


	private ItemStack cost() {
		// TODO Auto-generated method stub
		ItemStack temp = new ItemStack(Material.matchMaterial(plugin.getConfig().getString("cost_material")), plugin.getConfig().getInt("mail_cost"));
		return temp;
	}
}
