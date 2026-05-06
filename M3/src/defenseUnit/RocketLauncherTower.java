package defenseUnit;

class RocketLauncherTower extends DefenseUnit {

	public RocketLauncherTower(int armor, int baseDamage) {
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
		return FOOD_COST_ROCKETLAUNCHERTOWER;
	}

	public int getWoodCost() {
		return WOOD_COST_ROCKETLAUNCHERTOWER;
	}

	public int getIronCost() {
		return IRON_COST_ROCKETLAUNCHERTOWER;
	}

	public int getManaCost() {
		return MANA_COST_ROCKETLAUNCHERTOWER;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_ROCKETLAUNCHERTOWER;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_ROCKETLAUNCHERTOWER;
	}
}
