package defenseUnit;

class Catapult extends DefenseUnit {

	public Catapult(int armor, int baseDamage) {
		super(armor, baseDamage);
	}
	
	public int attack() {
		int damage_attack_does = getBaseDamage() + getBaseDamage() * (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
		if (isSanctified()) {
			damage_attack_does += getBaseDamage() * PLUS_ATTACK_UNIT_SANCTIFIED / 100;
		} 
		return damage_attack_does;
	}

	public int getFoodCost() {
		return FOOD_COST_CATAPULT;
	}

	public int getWoodCost() {
		return WOOD_COST_CATAPULT;
	}

	public int getIronCost() {
		return IRON_COST_CATAPULT;
	}

	public int getManaCost() {
		return MANA_COST_CATAPULT;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_CATAPULT;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_CATAPULT;
	}
	
}
