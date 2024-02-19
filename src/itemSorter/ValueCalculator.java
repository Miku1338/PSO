package itemSorter;

public class ValueCalculator {
	public int weaponValue(String weapon) {
		int value = 1;
		String name = SorterUtils.weaponName(weapon);
		String stats = SorterUtils.statsAmount(weapon);
		int[] statsInt = new WeaponStats(stats).getStatsInt();
		int hit = statsInt[4];
		
		return value;
	}

}
