package Attack;

public class Crosswob extends attackUnit{
	
	public Crosswob(int armor,int baseDamage) {
		super(armor,baseDamage);
	}
	
	public Crosswob() {
		super(ARMOR_CROSSBOW,BASE_DAMAGE_CROSSBOW);
	}
	
	public int attack() {
		int damage_attack_does = getBaseDamage() + getBaseDamage() * (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
		if (isSanctified()) {
			damage_attack_does += getBaseDamage() * PLUS_ATTACK_UNIT_SANCTIFIED / 100;
		} 
		return damage_attack_does;
	}

	public int getFoodCost() {
		return FOOD_COST_CROSSBOW;
	}

	public int getWoodCost() {
		return WOOD_COST_CROSSBOW;
	}

	public int getIronCost() {
		return IRON_COST_CROSSBOW;
	}

	public int getManaCost() {
		return MANA_COST_CROSSBOW;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_CROSSBOW;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_CROSSBOW;
	}
	
}
