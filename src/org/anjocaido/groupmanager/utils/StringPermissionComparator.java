/*
 *  GroupManager - A plug-in for Spigot/Bukkit based Minecraft servers.
 *  Copyright (C) 2020  ElgarL
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */
package org.anjocaido.groupmanager.utils;

import java.util.Comparator;

/**
 * 
 * @author gabrielcouto
 */
public class StringPermissionComparator implements Comparator<String> {

	@Override
	public int compare(String permA, String permB) {

		boolean ap = permA.startsWith("+");
		boolean bp = permB.startsWith("+");
		boolean am = permA.startsWith("-");
		boolean bm = permB.startsWith("-");
		if (ap && bp) {
			return 0;
		}
		if (ap && !bp) {
			return -1;
		}
		if (!ap && bp) {
			return 1;
		}
		if (am && bm) {
			return 0;
		}
		if (am && !bm) {
			return -1;
		}
		if (!am && bm) {
			return 1;
		}
		return permA.compareToIgnoreCase(permB);
	}

	private static StringPermissionComparator instance;

	public static StringPermissionComparator getInstance() {

		if (instance == null) {
			instance = new StringPermissionComparator();
		}
		return instance;
	}
}
