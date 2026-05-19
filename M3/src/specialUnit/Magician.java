package specialUnit;

public class Magician extends SpecialUnit {

	public Magician(int armor, int baseDamage) {
		super(armor, baseDamage);
	}

	public int attack() {
		int damage_attack_does = getBaseDamage() + getBaseDamage() * (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
		return damage_attack_does;
	}

	public int getFoodCost() {
		return FOOD_COST_MAGICIAN;
	}

	public int getWoodCost() {
		return WOOD_COST_MAGICIAN;
	}

	public int getIronCost() {
		return IRON_COST_MAGICIAN;
	}

	public int getManaCost() {
		return MANA_COST_MAGICIAN;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_MAGICIAN;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_MAGICIAN;
	}

	public void setSanctified(boolean sanctified) {
		
	}
	
	
	
}
