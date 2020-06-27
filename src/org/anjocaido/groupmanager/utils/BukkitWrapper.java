/**
 * 
 */
package org.anjocaido.groupmanager.utils;

import java.util.List;
import java.util.UUID;

import org.anjocaido.groupmanager.GroupManager;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;


/**
 * @author ElgarL
 *
 */
public class BukkitWrapper {
	
	private final Plugin plugin;
	private OfflinePlayer cache = null;
	private static BukkitWrapper instance;
	
	private BukkitWrapper() {
		plugin = GroupManager.getPlugin(GroupManager.class);
	}
	
	public static BukkitWrapper getInstance(){
	    if(instance == null){
	        synchronized (BukkitWrapper.class) {
	            if(instance == null){
	                instance = new BukkitWrapper();
	            }
	        }
	    }
	    return instance;
	}
	
	/**
	 * Find a players UUID from the servers usercache.
	 * returns null if there is no match.
	 * 
	 * @param name	{@String} containing the players name.
	 * @return	{@UUID} for this player, or null if there is no data.
	 */
	public UUID getPlayerUUID (String name) {
		
		// Check our cache first
		if ((cache != null) && (cache.getName().equalsIgnoreCase(name)))
			return cache.getUniqueId();
		
		// Clear our cache as this is a different player
		cache = null;
		
		// Search all known players (to this server) for a matching name.
		OfflinePlayer offlinePlayer[] = plugin.getServer().getOfflinePlayers();
		
		for (OfflinePlayer player : offlinePlayer)
			if (player.getName().equalsIgnoreCase(name)) {
				cache = player;
				return player.getUniqueId();
			}

		// A player with this name has never been seen on this server.
		return null;
	}
	
	/**
	 * Find a players name from the servers usercache.
	 * returns null if there is no match.
	 * 
	 * @param uid	{@UUID} to lookup.
	 * @return	{@String} of the players name, or null if there is no data.
	 */
	public String getPlayerName(UUID uid) {
		
		// Check our cache first
		if ((cache != null) && (cache.getUniqueId().compareTo(uid) == 0))
			return cache.getName();
		
		// Clear our cache as this is a different player
		cache = null;
		
		// Search all known players (to this server) for a matching UUID.
		OfflinePlayer offlinePlayer[] = plugin.getServer().getOfflinePlayers();
				
		for (OfflinePlayer player : offlinePlayer)
			if (player.getUniqueId().compareTo(uid) == 0) {
				cache = player;
				return player.getName();
			}

		// A player with this UUID has never been seen on this server.
		return null;
	}
	
	/**
	 * (Deprecated) Gets an OfflinePlayer object ![NEVER USE]!
	 * adds this data to the servers usercache.
	 * Always returns an object with this name, but the UUID will be generated if there is no account.
	 * (performs a blocking web request if the player is not known to the server)
	 * 
	 * -----------------------------------------------------------------
	 * The reason to never use this is it adding data to the usercache.
	 * This can result in a name being associated with an invalid UUID
	 * possibly confusing future searches.
	 * -----------------------------------------------------------------
	 * 
	 * @param name	a sting of this players name (case insensitive)
	 * @return	{@OfflinePlayer} object for this name.
	 */
	@SuppressWarnings("deprecation")
	public OfflinePlayer bukkitGetOfflinePlayer(String name) {
		
		return plugin.getServer().getOfflinePlayer(name);
	}
	
	/**
	 * Gets an OfflinePlayer object
	 * does NOT add this data to the servers usercache.
	 * Always returns an object but the name will be null if the UUID is not in the usercache.
	 * 
	 * @param uid a {@UUID} for this player.
	 * @return	{@OfflinePlayer} object for this {@UUID}.
	 */
	public OfflinePlayer bukkitGetOfflinePlayer(UUID uid) {
		
		return plugin.getServer().getOfflinePlayer(uid);
	}
	
	public List<Player> matchPlayer(String name) {
		
		return plugin.getServer().matchPlayer(name);
	}
	
	public Player getPlayer(String name) {
		
		return plugin.getServer().getPlayer(name);
	}
	
	public Long getLastOnline(UUID uid) {
		
		// Check our cache first
		if ((cache != null) && (cache.getUniqueId().compareTo(uid) == 0))
			return cache.getLastPlayed();
		
		// Clear our cache as this is a different player
		cache = null;
		
		// Search all known players (to this server) for a matching UUID.
		if (getPlayerName(uid) != null)
				return cache.getLastPlayed();
		
		// A player with this UUID has never been seen on this server.
		return null;
	}
	
	public Long getFirstPlayed(UUID uid) {
		
		// Check our cache first
		if ((cache != null) && (cache.getUniqueId().compareTo(uid) == 0))
			return cache.getFirstPlayed();
		
		// Clear our cache as this is a different player
		cache = null;
		
		// Search all known players (to this server) for a matching UUID.
		if (getPlayerName(uid) != null)
				return cache.getFirstPlayed();
		
		// A player with this UUID has never been seen on this server.
		return null;
	}

}
