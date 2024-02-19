package itemSorter;

public class SorterUtils {
	static String weaponName(String item) {
		int startIndex = item.indexOf(":") + 1;
		if (startIndex < 0) {
			startIndex = 0;
		}
		int endIndex;
			if (item.contains("+")) {
				endIndex = item.indexOf("+") - 1;
			} else {
				endIndex = item.indexOf("[") - 1;
			}
		return item.substring(startIndex, endIndex).trim();
	}
	
	static String statsAmount(String item) {
		int startIndex = item.indexOf("[");
		int endIndex = item.lastIndexOf("]") + 1;
		return item.substring(startIndex, endIndex);
	}
	
	static boolean isUntekked(String item) {
		return item.contains("UNTEKKED");
	}
}
