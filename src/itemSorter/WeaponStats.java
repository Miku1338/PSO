package itemSorter;

import java.util.Arrays;

public class WeaponStats {
	private final int[] stats;
	private final boolean untekked;
	
	public WeaponStats(String weapon) {
		untekked = weapon.contains("(U)");
		String weaponStats = SorterUtils.statsAmount(weapon);
		String[] pieces = weaponStats.split("/|\\|");
		pieces[0] = pieces[0].substring(1);
		pieces[pieces.length - 1] = pieces[pieces.length - 1].substring(0, pieces[pieces.length - 1].length() - 1);
		stats = new int[5];
		for (int i = 0; i < 5; ++i) {
			if (pieces[i].contains("(U)")) {
				stats[i] = Integer.parseInt(pieces[i].substring(0, pieces[i].indexOf(']')));
			} else {
				stats[i] = Integer.parseInt(pieces[i]);
			}
		}
	}
	
	public int[] getStatsInt() {
		return stats;
	}
	
	public int[] getStatsBoosted() {
		if (!untekked) {
			return stats;
		} else {
			int[] statsBoosted = new int[5];
			for (int i = 0; i < 5; ++i) {
				if (stats[i] > 0) {
					statsBoosted[i] = stats[i] + 10;
				} else {
					statsBoosted[i] = stats[i];
				}
			}
			return statsBoosted;
		}
	}
	
	public boolean isUntekked() {
		return untekked;
	}
	
	public String getStatsString() {
		return Arrays.toString(stats);
	}
}
