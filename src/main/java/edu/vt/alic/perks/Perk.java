package edu.vt.alic.perks;

/**
 * Enum that contains all available perks.
 * 
 * @author Ali Camlibel (Lambdastic)
 * @since 05/15/16
 * @version 1.0
 */
public enum Perk {
		
	HUNGER (1),
	DAMAGE (1),
	XP (1),
	LUCK (1),
	ARMOR (10),
	MONEY (1),
	DETECT (1),
	SPEED (1),
	SALVAGE (1);
	
	private int adds;
	
	/**
	 * @param adds how much each level adds to the total
	 */
	Perk(int adds) {
		this.adds = adds;
	}
	
	/**
	 * @return adds amount
	 */
	public int getAdds() {
		return adds;
	}
	
	/**
	 * @param col is the top row in the GUI.
	 * @return Perk enum value based on what column
	 */
	public static Perk getPerkByColumn(int col) {
		switch (col) {
		case 0: return Perk.HUNGER;
		case 1: return Perk.DAMAGE;
		case 2: return Perk.XP;
		case 3: return Perk.LUCK;
		case 4: return Perk.ARMOR;
		case 5: return Perk.MONEY;
		case 6: return Perk.DETECT;
		case 7: return Perk.SPEED;
		case 8: return Perk.SALVAGE;
		}
		
		return Perk.HUNGER;
	}
	
	/**
	 * Example: 0, 9, and 18 indexes are all vertical of each other, so I know that column is the Hunger perk
	 * 
	 * @param pos index in the GUI from 0 to 26 (inclusive)
	 * @return Perk based on where in the GUI it is
	 */
	public static Perk getPerkByPosition(int pos) {
		if (pos == 0 || pos == 9 || pos == 18)
			return Perk.HUNGER;
		else if (pos == 1 || pos == 10 || pos == 19)
			return Perk.DAMAGE;
		else if (pos == 2 || pos == 11 || pos == 20)
			return Perk.XP;
		else if (pos == 3 || pos == 12 || pos == 21)
			return Perk.LUCK;
		else if (pos == 4 || pos == 13 || pos == 22)
			return Perk.ARMOR;
		else if (pos == 5 || pos == 14 || pos == 23)
			return Perk.MONEY;
		else if (pos == 6 || pos == 15 || pos == 24)
			return Perk.DETECT;
		else if (pos == 7 || pos == 16 || pos == 25)
			return Perk.SPEED;
		else if (pos == 8 || pos == 17 || pos == 26)
			return Perk.SALVAGE;
		
		return Perk.HUNGER;
	}
	
	/**
	 * @param pos index in the GUI from 0 to 26 (inclusive)
	 * @return 1 if in the first row, 2 if in the second row, 3 if else
	 */
	public static int getPerkRank(int pos) {
		if (pos >= 0 && pos <= 8)
			return 1;
		else if (pos >= 9 && pos <= 17)
			return 2;
		else
			return 3;
	}
	
}