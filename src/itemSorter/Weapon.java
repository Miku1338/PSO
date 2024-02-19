package itemSorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Weapon {
	private final String name;
	private final WeaponStats stats;
	
	public Weapon(String weapon) {
		name = SorterUtils.weaponName(weapon);
		stats = new WeaponStats(weapon);
	}
	
	public String getName() {
		return name;
	}
	
	public int calculateValue() {
		double value = 1;
		File f = new File("C:\\Users\\aleph\\eclipse-workspace\\PSO\\Weapon Values");
		Scanner s = null;
		try {
			s = new Scanner(f);
			while (s.hasNextLine()) {
				String line = s.nextLine();
				if (line.startsWith(name) || line.contains("Other")) {
					String[] pieces = line.split("\\s+");
					int piecesLength = pieces.length;
					int hit = stats.getStatsBoosted()[4];
					double[] allValues = new double[21];
					allValues[0] = Integer.parseInt(pieces[piecesLength - 6]);
					allValues[3] = Integer.parseInt(pieces[piecesLength - 5]);
					allValues[1] = Math.pow(allValues[0]*allValues[0]*allValues[3], 1/3.0);
					allValues[2] = Math.pow(allValues[0]*allValues[3]*allValues[3], 1/3.0);
					allValues[6] = Integer.parseInt(pieces[piecesLength - 4]);
					allValues[4] = Math.pow(allValues[3]*allValues[3]*allValues[6], 1/3.0);
					allValues[5] = Math.pow(allValues[3]*allValues[6]*allValues[6], 1/3.0);
					allValues[9] = Integer.parseInt(pieces[piecesLength - 3]);
					allValues[7] = Math.pow(allValues[6]*allValues[6]*allValues[9], 1/3.0);
					allValues[8] = Math.pow(allValues[6]*allValues[9]*allValues[9], 1/3.0);
					allValues[12] = Integer.parseInt(pieces[piecesLength - 2]);
					allValues[10] = Math.pow(allValues[9]*allValues[9]*allValues[12], 1/3.0);
					allValues[11] = Math.pow(allValues[9]*allValues[12]*allValues[12], 1/3.0);
					allValues[20] = Integer.parseInt(pieces[piecesLength - 1]);
					allValues[16] = Math.pow(allValues[12]*allValues[20], 0.5);
					allValues[14] = Math.pow(allValues[12]*allValues[16], 0.5);
					allValues[18] = Math.pow(allValues[16]*allValues[20], 0.5);
					for (int i = 0; i < 4; ++i) {
						allValues[13 + 2*i] = Math.pow(allValues[13 + 2*i - 1]*allValues[13 + 2*i + 1], 0.5);
					}
					
					if (hit == -5) {
						value = 1;
					} else {
						value = allValues[hit / 5];
					}
					if (value < 25) {
						value *= attributeBonus();
					} else {
						value += attributeBonusValuable();
					}
					break;
					
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		return (int) Math.round(value);
	}
	
	private double attributeBonus() {
		int[] statsInt = stats.getStatsInt();
		int nativePercent = statsInt[0];
		int abeastPercent = statsInt[1];
		int machinePercent = statsInt[2];
		int darkPercent = statsInt[3];
		int maxPercent = Math.max(Math.max(Math.max(nativePercent, abeastPercent), machinePercent), darkPercent);
		double multiplier = 1 + 0.005*nativePercent + 0.005*abeastPercent + 0.005*machinePercent + 0.005*darkPercent;
		if (maxPercent > 75) {
			multiplier += maxPercent*0.05;
		} else if (maxPercent > 50) {
			multiplier += maxPercent*0.025;
		}
		return multiplier;
	}
	
	private int attributeBonusValuable() {
		int[] statsInt = stats.getStatsInt();
		int value = 3*statsInt[0] + 3*statsInt[1] + 3*statsInt[2] + 3*statsInt[3];
		if (value == 0) {
			value = 15;
		}
		return value;
	}
}
