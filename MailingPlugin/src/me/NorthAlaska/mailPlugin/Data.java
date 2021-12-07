package me.NorthAlaska.mailPlugin;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import me.NorthAlaska.mailPlugin.*;

import org.bukkit.Location;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;

public class Data implements Serializable
{
	private static transient final long serialVersionUID = -1681012206529286330L;
	
	public ArrayList<MailBox> array;
	private Main plugin;
	
	public Data(ArrayList<MailBox> array, Main plugin)
	{
		this.array = array;
		//this.plugin = plugin;
	}
	
	public Data(Main plugin)
	{
		//this.plugin = plugin;
	}
	public Data()
	{
		
	}
    
	//Saves data of the mailboxes the parameters are the filepath and the arraylist from the PostalService object
	public boolean saveData(String filePath, ArrayList<MailBox> b) {
		this.array = b;
        try {
            BukkitObjectOutputStream out = new BukkitObjectOutputStream(new GZIPOutputStream(new FileOutputStream(filePath)));
            out.writeObject(this);
            out.close();
            return true;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return false;
        }
    }
	
	//Returns the array that has been saved in the class
	public ArrayList<MailBox> getArray()
	{
		return array; 
	}
	
	//Loads data from the file given its filePath
	public static Data loadData(String filePath) 
	{
        try {
            BukkitObjectInputStream in = new BukkitObjectInputStream(new GZIPInputStream(new FileInputStream(filePath)));
            Data data = (Data) in.readObject();
            in.close();
            return data;
        } catch (ClassNotFoundException | IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;
        }
        
	}
	
	public String toString()
	{
		return "Data for arraylist: " +array;
	}
}
