package main;

import java.util.ArrayList;





import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {

	private PluginManager pManager;
	
	@Override
	public void onEnable(){
		//new C(this);
		//new L(this);
		
		pManager = Bukkit.getPluginManager();
		pManager.registerEvents(this, this);
		
		getConfig().options().copyDefaults(true);
		saveConfig();
		reloadConfig();
	}
	
	 boolean allowed=false;
	 ArrayList<String> L1 = new ArrayList<String>();
	 ArrayList<String> L2 = new ArrayList<String>();
	 Player checkD1;
	 Player checkD2;
	 int gamesPlayed=0;
	 int round=1;
	 
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
	 if (!(sender instanceof Player)){
		 sender.sendMessage("Only players may execute this command!");
		 return true;
	 }
	 
	 Player p = (Player) sender;
	
	 if (cmd.getName().equalsIgnoreCase("tournament")) {
		 if (args.length == 0) {
			 sender.sendMessage(ChatColor.GOLD + "/tournament [start|stop]: Start or Stop the plugin from working.");
			 return true;
		 }
		 
		 if (args[0].equals("start")) {
			if (p.isOp()) {
				allowed=true;
				 p.sendMessage("A=true");
				 return true;
			} else {
				p.sendMessage("You do not have permission to execute this command!");
				return false;
			}
		 } 
		 
		 if(args[0].equals("stop")) {
			 if (p.isOp()) {
				 allowed=false;
				 p.sendMessage("A=false");
				 return true;
			 } else {
				 p.sendMessage("You do not have permission to execute this command!");
				 return false;
			 }
		 } 
		 
		 //TODO: Made some changes to this command. Test if it works.
		 if(args[0].equals("reset")) {
			 if (p.isOp()) {
				gamesPlayed=0;
				round=1;
				L1.clear();
				L2.clear();
				
				//TODO Notify players (if any) they are removed from queue.
				
				
				 return true;
			 } else {
				 p.sendMessage("You do not have permission to execute this command!");
				 return false;
			 }
		 } 
		 
		 if(args[0].equals("pos1")) {
			 if (p.isOp()) {
				//TODO: setpos1 - Teleports 1 of 2 to this location
				 
				 return true;
			 } else {
				 p.sendMessage("You do not have permission to execute this command!");
				 return false;
			 }
			 
			 
			 
		 } 
		 
		 if(args[0].equals("pos2")) {
			 if (p.isOp()) {
				 //TODO: setpos2 - Teleports 1 of 2 to this location
				 return true;
			 } else {
				 p.sendMessage("You do not have permission to execute this command!");
				 return false;
			 }
		 } 
		
		 if(args[0].equals("next")) {
			
			 if (p.isOp()) {
				 
				 int size = L1.size();
				 int size2 = L2.size();
				 Bukkit.broadcastMessage("1. Size L1 = "+size); //BCM for testing. Can be taken out later
				 Bukkit.broadcastMessage("2. Size L2 = "+size2);
				 Bukkit.broadcastMessage(L1.toString());
				 Bukkit.broadcastMessage(L2.toString());
				 
				 //check if enough players or if we should go to next round.
				 if(size==0&&gamesPlayed==0|size==1) {
					 p.sendMessage("Not enough players! Need atleast 2 players to start.");
					 return false;
				 }
				 if(size==0&&gamesPlayed==1) {
					 Bukkit.broadcastMessage(ChatColor.GREEN + "Round " +round);
					 L1.clear();
					 L1.addAll(L2);
					 L2.clear();
					 
					 if (L1.size()==1) {
						 Bukkit.broadcastMessage(ChatColor.GREEN + "Tournament winner is " + L1.get(0)+ "!");
					 }
					 
				 }
				 
				 size = L1.size();
				 
				 if(!(size%2==0)) {
					//exectute this code if size is Odd. 
					//last one add L2, remove from L1.
					 String lastName = L1.get(L1.size()-1);
					 
					 Bukkit.broadcastMessage(lastName + " test001");
					 L1.remove(lastName);
					 L2.add(lastName);
					 //send message to player L1.get(size-1). "Placed in second round."
					 
				 }
				 size = L1.size();
				 
				 //testing --- 
				 size2 = L2.size();
				 Bukkit.broadcastMessage("2. Size L2 = "+size2);
				 Bukkit.broadcastMessage(L1.toString());
				 Bukkit.broadcastMessage(L2.toString());
				 
				 if((size%2)==0){
					 //even - normal
					 
					String p1 = L1.get(0); //player 1
					String p2 = L1.get(1); //player 2 
					 
					
					
					//Do stuff for player 1
					Player pFight1 = Bukkit.getPlayer(p1);
					 checkD1 = pFight1;
					 
						float yaw = 90;
				        float pitch = 0;
				        Location loc1 = new Location(pFight1.getWorld(), 0.5, 31.0, 16.5, yaw, pitch);
				        pFight1.teleport(loc1);
					
				    //Do stuff for player 2
					Player pFight2 = Bukkit.getPlayer(p2);
					 checkD2 = pFight2;
					 
					float yaw1 = 90;
			        float pitch1 = 0;
			        Location loc2 = new Location(pFight2.getWorld(), 0.5, 31.0, -20.5, yaw1, pitch1);
			        pFight2.teleport(loc2);
					
					pFight1.getInventory().clear();
					pFight2.getInventory().clear();
					RemoveArmor(pFight1);
					RemoveArmor(pFight2);
					
					PlayerInventory i1 = pFight1.getInventory();
					PlayerInventory i2 = pFight2.getInventory();
					
					//TODO: When adding armor, reverse method above to set armor. --> Change method with to ARGS instead.
					//TODO: Ability to create new kits ingame and add/delete items to that kit. (low priority)
					i1.addItem(new ItemStack(Material.IRON_SWORD));
					i1.addItem(new ItemStack(Material.GOLDEN_APPLE));

					i2.addItem(new ItemStack(Material.IRON_SWORD));
					i2.addItem(new ItemStack(Material.GOLDEN_APPLE));
									
					
					//TODO: 
					//- Make sure their inventory is empty before getting teleported to arena. (Maybe ask them to write a command if their inventory is not empty before their inventorys are cleared) 
					//- Block movement for both players.
					//- Send text to 2 players asking them to write /tournament ready.
					//- If both ready - allow movement again. (Add countdown aswell)
					//- Check who is winner. (DONE) 
					//-	Announce winner + name. (DONE)
					//- Remove both from L1. Add winner to L2. (DONE)
					//- After removing from L1, check if size == 0. If Yes, announce "Round 2". Make sure L1 is clear. Copy L2 to L1. Clear L2 again. If no, do nothing. (DONE)
					
					
					
				 } 

				 
				 
				 
				 
				 
				 //--end on check op. Code above only. -----------------------------------------------------
			 } else {
				 p.sendMessage("You do not have permission to execute this command!");
				 return false;
			 }
			 
			 
		 }
			 
		 
		 if(args[0].equals("ready")) {
			//TODO: 
			 //Make it work like described above
			 p.sendMessage("test005");
		 }
		 
		 if(args[0].equals("join")) {
			 if (allowed) {
			 L1.add(p.getName());
			 p.sendMessage("Joined queue.");
			 } else {
				 p.sendMessage("Not able to join queue.");
			 }
		 }
		 
		 if(args[0].equals("leave")) {
			 if (allowed) {
			 if (L1.contains(p.getName())) {
				 L1.remove(p.getName());
				 p.sendMessage("You have been removed from the queue.");
			 } else {
				 p.sendMessage("Not in queue.");
			 }
		 } else {
			 p.sendMessage("Not able to leave queue."); //TODO: Deal with offline or people who no longer want to participate in a better way.
		 }
			 
			 
		 }
	 }
	 
	 
		return false;
	}
	
	public void RemoveArmor(Player player) { //TODO: add ARGS
	    player.getInventory().setHelmet(null);
	    player.getInventory().setChestplate(null);
	    player.getInventory().setLeggings(null);
	    player.getInventory().setBoots(null);
	}	
	
	float yawPDE = 90;
    float pitchPDE = 0;
   
    //TODO: Add command to set pos for this ingame
	int x=0;
	int y=33;
	int z=26;
	
	int q=0;
	@EventHandler
	public boolean anyName(PlayerDeathEvent event) {
        Player pD = event.getEntity();
        Bukkit.broadcastMessage("DeathEvent");
       
       
        	if (pD==checkD1&&q==0) {
        		q++;
        		//D2 has won.
        		Bukkit.broadcastMessage(ChatColor.GREEN + checkD2.getName() +" has won.");
        		checkD2.getInventory().clear();
        		//checkD2.setHealth(0.0);
        		//TODO: Test if teleport works - prob does
        		Location loc1 = new Location(checkD2.getWorld(), x+0.5, y+0.5, z+0.5, yawPDE, pitchPDE);
        		checkD2.teleport(loc1);
        		
        		L1.remove(checkD1.getName());
        		L1.remove(checkD2.getName());
        		
        		L2.add(checkD2.getName());
        		q=0;

				 gamesPlayed=1;
        		return true;
        	}
        	if (pD==checkD2&&q==0) {
        		q++;
        		//D1 has won.
        		Bukkit.broadcastMessage(ChatColor.GREEN + checkD1.getName() +" has won.");
        		checkD1.getInventory().clear();
        		//checkD1.setHealth(0.0);
        		//TODO: Test if teleport works - prob does
        		Location loc2 = new Location(checkD1.getWorld(), x+0.5, y+0.5, z+0.5, yawPDE, pitchPDE);
        		checkD2.teleport(loc2);
        		
        		L1.remove(checkD1.getName());
        		L1.remove(checkD2.getName()); //veranderd van index naar object
        		
        		
        		L2.add(checkD1.getName());
        		q=0;

				 gamesPlayed=1;
        		return true;
        	}
        	
        	
            
        
		return false;
    }
	
	
	//TODO: Create onRespawnEvent
	//TODO: Check if players are alive&&online everytime we are assuming they are - BV teleport, clearing inv, adding items to inv, etc.
	//TODO: Put everything in seperate classes - this is getting messy :(
	
	
	
	
	
}
