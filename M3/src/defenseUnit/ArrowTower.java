package defenseUnit;

public class ArrowTower extends DefenseUnit {

	public ArrowTower(int armor, int baseDamage) {
		super(armor,baseDamage);
	}
	


	

	public int attack() {
		int damage_attack_does = getBaseDamage() + getBaseDamage() * (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
		if (isSanctified()) {
			damage_attack_does += getBaseDamage() * PLUS_ATTACK_UNIT_SANCTIFIED / 100;
		} 
		return damage_attack_does;
	}

	public int getFoodCost() {
		return FOOD_COST_ARROWTOWER;
	}

	public int getWoodCost() {
		return WOOD_COST_ARROWTOWER;
	}

	public int getIronCost() {
		return IRON_COST_ARROWTOWER;
	}

	public int getManaCost() {
		return MANA_COST_ARROWTOWER;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_ARROWTOWER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_ARROWTOWER;
	}
	
	


	
}
