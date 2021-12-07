package me.NorthAlaska.mailPlugin;

import java.util.ArrayList;

public class PostalService 
{
	/*Purpose is to add helpful methods for managing Mailboxes as well as storing the ones that are
	 * around the map of the world.
	 */
	public ArrayList<MailBox> postOffice;
	
	public PostalService ()
	{
		//this.postOffice = new ArrayList<MailBox>();
	}
	
	/* Finds a MailBox given a player name throughout the arraylist
	 *  and return that MailBox object
	 */
	public MailBox findMailBox(String target)
	{
		
		for (int i = 0; i < postOffice.size(); i++)
		{
			if (postOffice.get(i).getOwner().equals(target))
			{
				return postOffice.get(i);
			}
		}
		
		return null;
		
	}
	
	//Getter and Setter Methods
	public ArrayList<MailBox> getPostOffice()
	{
		return postOffice;
	}
	
	public void setPostOffice(ArrayList<MailBox> array)
	{
		postOffice = array;
	}
	
	public void addMailbox(MailBox mail)
	{
		postOffice.add(mail);
	}
	
	/*
	 * Finds the index in the arraylist given the players name just another form of the FindMailBox Method
	 * It will return an (int) of the index or -1 if it doesn't exist
	 */
	public int findMailIndex(String target)
	{
		if (postOffice != null)	
		{
			for (int i = 0; i < postOffice.size(); i++)
			{
				if (postOffice.get(i).getOwner().equals(target))
				{
					return i;
				}
			}
		}
		
		return -1;
	}
	
	//Removes a mailbox from the array
	public void removeMailbox(int index)
	{
		postOffice.remove(index);
	}
	
}
