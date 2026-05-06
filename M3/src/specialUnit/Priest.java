package specialUnit;

public class Priest extends SpecialUnit {

	public Priest(int armor, int baseDamage) {
		super(armor, baseDamage);
	}

	public int attack() {
		int damage_attack_does = getBaseDamage() + getBaseDamage() * (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
		return damage_attack_does;
	}

	public int getFoodCost() {
		return FOOD_COST_PRIEST;
	}

	public int getWoodCost() {
		return WOOD_COST_PRIEST;
	}

	public int getIronCost() {
		return IRON_COST_PRIEST;
	}

	public int getManaCost() {
		return MANA_COST_PRIEST;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_PRIEST;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_PRIEST;
	}
	
}
