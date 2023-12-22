package itemSorter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SorterMain {
	
	public static void main(String[] args) {
		Map<String, List> weaponsMap = new HashMap<>();
		Map<String, List> armorMap = new HashMap<>();
		Map<String, List> techMap = new HashMap<>();
		List<String> magList = new ArrayList<>();
		Map<String, Integer> otherMap = new HashMap<>();
		Scanner s = new Scanner(System.in);
		while (true) {
			String itemData = s.nextLine();
			if (itemData.equals("end")) {
				break;
			} else if (itemData.contains("[E]") || isSRank(itemData) || itemData.contains("Normal") || !itemData.contains(": ")) {
				continue;
			} else {
				if (isWeapon(itemData)) {
					String weaponName = weaponName(itemData);
					int hit = hitAmount(itemData);
					boolean isUntekked = isUntekked(itemData);
					String hitString;
					if (!isUntekked) {
						hitString = Integer.toString(hit);
					} else if (hit > 0) {
						hitString = hit + "U";
					} else {
						hitString = "0";
					}
					if (weaponsMap.containsKey(weaponName)) {
						weaponsMap.get(weaponName).add(hitString);
					} else {
						List<String> newList = new ArrayList<String>();
						newList.add(hitString);
						weaponsMap.put(weaponName, newList);
					}
				} else if (isArmor(itemData)) {
					String armorName = armorName(itemData);
					String defense = Integer.toString(defenseAmount(itemData));
					String evade = Integer.toString(evadeAmount(itemData));

					if (armorMap.containsKey(armorName)) {
						armorMap.get(armorName).add(defense + "/" + evade);
					} else {
						List<String> newList = new ArrayList<String>();
						newList.add(defense + "/" + evade);
						armorMap.put(armorName, newList);
					}
				} else if (isTech(itemData)) {
					String techName = techName(itemData);
					String level = techLevel(itemData);

					if (techMap.containsKey(techName)) {
						techMap.get(techName).add(level);
					} else {
						List<String> newList = new ArrayList<String>();
						newList.add(level);
						techMap.put(techName, newList);
					}
				} else if (isMag(itemData)) {
					magList.add(magData(itemData));
				} else {
					String itemName = otherName(itemData);
					int count = otherCount(itemData);
					if (otherMap.containsKey(itemName)) {
						otherMap.put(itemName, otherMap.get(itemName) + count);
					} else {
						otherMap.put(itemName, count);
					}
				}
			}
		}
		
		System.out.println("WEAPONS!");
		List<String> weapons  = new ArrayList<>();
		for (String weaponName : weaponsMap.keySet()) {
			List<String> hitList = weaponsMap.get(weaponName);
			Collections.sort(hitList);
			weapons.add("[b]" + weaponName + "[/b] with these hit amounts: " + Arrays.toString(hitList.toArray()));
		}
		Collections.sort(weapons);
		for (String weapon : weapons) {
			System.out.println(weapon);
		}
		
		System.out.println("\nARMORS/SHIELDS!");
		List<String> armors = new ArrayList<>();
		for (String armorName : armorMap.keySet()) {
			List<String> defenseList = armorMap.get(armorName);
			Collections.sort(defenseList);
			armors.add("[b]" + armorName + "[/b] with these defense/evade amounts: " + Arrays.toString(defenseList.toArray()));
		}
		Collections.sort(armors);
		for (String armor : armors) {
			System.out.println(armor);
		}
		
		System.out.println("\nTECHS!");
		List<String> techs = new ArrayList<>();
		for (String techName : techMap.keySet()) {
			List<String> levelList = techMap.get(techName);
			Collections.sort(levelList);
			techs.add("[b]" + techName + "[/b]LV: " + Arrays.toString(levelList.toArray()));
		}
		Collections.sort(techs);
		for (String tech : techs) {
			System.out.println(tech);
		}
		
		System.out.println("\nMAGS!");
		Collections.sort(magList);
		for (String mag : magList) {
			System.out.println(mag);
		}
		
		System.out.println("\nOTHERS!");
		List<String> others = new ArrayList<>();
		for (String otherName : otherMap.keySet()) {
			others.add("[b]" + otherName + "[/b] x" + otherMap.get(otherName));
		}
		Collections.sort(others);
		for (String other : others) {
			System.out.println(other);
		}
	}
	
	private static boolean isWeapon(String item) {
		return item.contains("%)");
	}
	
	private static boolean isSRank(String item) {
		return !item.contains("DFP") && item.contains("+") && !item.contains("%");
	}
	
	private static String weaponName(String item) {
		int startIndex = item.indexOf(":") + 2;
		if (startIndex < 0) {
			startIndex = 0;
		}
		int endIndex;
			if (item.contains("+")) {
				endIndex = item.indexOf("+") - 1;
			} else {
				endIndex = item.indexOf("(N") - 1;
			}
		return item.substring(startIndex, endIndex);
	}
	
	private static int hitAmount(String item) {
		int startIndex = item.indexOf("%/H") + 4;
		int endIndex = item.lastIndexOf("%)");
		int hit = Integer.parseInt(item.substring(startIndex, endIndex));
		return hit;
	}
	
	private static boolean isUntekked(String item) {
		return item.contains("UNTEKKED");
	}
	
	private static boolean isArmor(String item) {
		return item.contains("DFP") && item.contains("EVP");
	}
	
	private static int defenseAmount(String item) {
		int startIndex = item.indexOf("DFP+") + 4;
		int endIndex = item.indexOf(" EVP+");
		int defense = Integer.parseInt(item.substring(startIndex, endIndex));
		return defense;
	}
	
	private static int evadeAmount(String item) {
		int startIndex = item.indexOf("EVP+") + 4;
		int endIndex = item.lastIndexOf(")");
		int defense = Integer.parseInt(item.substring(startIndex, endIndex));
		return defense;
	}
	
	private static String armorName(String item) {
		int startIndex = item.indexOf(":") + 2;
		if (startIndex < 0) {
			startIndex = 0;
		}
		return item.substring(startIndex, item.indexOf("(") - 1);
	}
	
	private static boolean isTech(String item) {
		return item.contains("LV") && item.contains("disk");
	}
	
	private static String techName(String item) {
		int startIndex = item.indexOf(":") + 2;
		if (startIndex < 0) {
			startIndex = 0;
		}
		return item.substring(startIndex, item.indexOf("LV") - 1);
	}
	
	private static String techLevel(String item) {
		return item.substring(item.indexOf("LV") + 2, item.indexOf("disk") - 1);
	}
	
	private static String otherName(String item) {
		int startIndex = item.indexOf(":") + 2;
		if (startIndex < 0) {
			startIndex = 0;
		}
		Pattern pattern = Pattern.compile("x[1-9]");
	    Matcher matcher = pattern.matcher(item);
	    int endIndex = -1;
	    if(matcher.find()){
	    	endIndex = matcher.start() - 1;
	    }
	    if (endIndex == -1) {
	    	return item.substring(startIndex);
	    } else {
	    	return item.substring(startIndex, endIndex);
	    }
	}
	
	private static boolean isMag(String item) {
		return (item.contains("Synchro"));
	}
	
	private static String magData(String item) {
		int startIndex = item.indexOf(":") + 2;
		if (startIndex < 0) {
			startIndex = 0;
		}
		return item.substring(startIndex);
	}
	
	private static int otherCount(String item) {
		int count = 1;
		if (Character.isDigit(item.charAt(item.length()-1))) {
			int startIndex = item.lastIndexOf("x") + 1;
			count = Integer.parseInt(item.substring(startIndex));
		}
		return count;
	}
	
}

