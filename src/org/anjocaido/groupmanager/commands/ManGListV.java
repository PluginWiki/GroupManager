/**
 * 
 */
package org.anjocaido.groupmanager.commands;

import java.util.ArrayList;
import java.util.List;

import org.anjocaido.groupmanager.GroupManager;
import org.anjocaido.groupmanager.data.Group;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;


/**
 * @author ElgarL
 *
 */
public class ManGListV extends BaseCommand implements TabCompleter {

	/**
	 * 
	 */
	public ManGListV() {}

	@Override
	protected boolean parseCommand(@NotNull String[] args) {

		// Validating state of sender
		if (dataHolder == null || permissionHandler == null) {
			if (!setDefaultWorldHandler(sender))
				return true;
		}
		// Validating arguments
		if (args.length != 1) {
			sender.sendMessage(ChatColor.RED + "Review your arguments count! (/manglistv <group>)");
			return true;
		}
		auxGroup = dataHolder.getGroup(args[0]);
		if (auxGroup == null) {
			sender.sendMessage(ChatColor.RED + "'" + args[0] + "' Group doesnt exist!");
			return true;
		}
		if (auxGroup.isGlobal()) {
			sender.sendMessage(ChatColor.RED + "GlobalGroups do NOT support Info Nodes.");
			return true;
		}
		// Validating permission
		// Seems OK
		auxString = "";
		for (String varKey : auxGroup.getVariables().getVarKeyList()) {
			Object o = auxGroup.getVariables().getVarObject(varKey);
			auxString += ChatColor.GOLD + varKey + ChatColor.WHITE + ":'" + ChatColor.GREEN + o.toString() + ChatColor.WHITE + "', ";
		}
		if (auxString.lastIndexOf(",") > 0) {
			auxString = auxString.substring(0, auxString.lastIndexOf(","));
		}
		sender.sendMessage(ChatColor.YELLOW + "Variables of group " + auxGroup.getName() + ": ");
		sender.sendMessage(auxString + ".");
		auxString = "";
		for (String grp : auxGroup.getInherits()) {
			auxString += grp + ", ";
		}
		if (auxString.lastIndexOf(",") > 0) {
			auxString = auxString.substring(0, auxString.lastIndexOf(","));
			sender.sendMessage(ChatColor.YELLOW + "Plus all variables from groups: " + auxString);
		}

		return true;
	}
	
	@Override
	public @Nullable List<String> onTabComplete(@NotNull CommandSender sender, @NotNull Command command, @NotNull String alias, @NotNull String[] args) {

		parseSender(sender, alias);
		
		List<String> result = new ArrayList<String>();
		/*
		 * Return a TabComplete for groups.
		 */
		if (args.length == 1) {

			for (Group g : dataHolder.getGroupList()) {
				result.add(g.getName());
			}
			
			/*
			 * Include global groups.
			 */
			for (Group g : GroupManager.getGlobalGroups().getGroupList()) {
				result.add(g.getName());
			}
		}
		return result;
	}

}