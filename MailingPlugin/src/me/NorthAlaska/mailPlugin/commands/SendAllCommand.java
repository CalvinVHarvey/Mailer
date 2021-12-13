package me.NorthAlaska.mailPlugin.commands;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import me.NorthAlaska.mailPlugin.MailBox;
import me.NorthAlaska.mailPlugin.Main;
import me.NorthAlaska.mailPlugin.utils.Utils;


/* 
 * Command to send a book and quill to everyone who has a mail box on the server useful for things such as 
 * announcements for events and such
 */
public class SendAllCommand implements CommandExecutor
{
	private Main plugin;
	public SendAllCommand(Main plugin)
	{
		this.plugin = plugin;
		plugin.getCommand("sendAll").setExecutor(this);
	}
	
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) 
	{
		Player p = (Player) sender;
		ItemStack currentItem = p.getItemInHand();
		PlayerInventory inv = p.getInventory();
		if (p.hasPermission("mailer.sendall") && currentItem.getType() == Material.WRITTEN_BOOK)
		{
			for (int i = 0; i < plugin.getP().getPostOffice().size(); i++)
			{
				MailBox cur = plugin.getP().getPostOffice().get(i);
				cur.sendMail(currentItem);
			}
			inv.removeItem(currentItem);
			p.sendMessage(Utils.chat("&aSuccessfully sent mail to all players with a Mail Box"));
		
		}
		else if(currentItem.getType() != Material.WRITTEN_BOOK)
		{
			p.sendMessage(Utils.chat("&cYou need to be holding a book"));
	
		}
		else
		{
			p.sendMessage(Utils.chat("&cInsufficient Permissions"));
			
		}
		
		
		
		
		return false;
	}
	
	
}
