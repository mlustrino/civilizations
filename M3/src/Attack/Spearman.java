package Attack;

public class Spearman extends attackUnit{
	
	public Spearman(int armor,int baseDamage) {
		super(armor,baseDamage);
	}
	
	public Spearman() {
		super(ARMOR_SPEARMAN,BASE_DAMAGE_SPEARMAN);
	}
	
	public int attack() {
		int damage_attack_does = getBaseDamage() + getBaseDamage() * (getExperience() * PLUS_ATTACK_UNIT_PER_EXPERIENCE_POINT) / 100;
		if (isSanctified()) {
			damage_attack_does += getBaseDamage() * PLUS_ATTACK_UNIT_SANCTIFIED / 100;
		} 
		return damage_attack_does;
	}

	public int getFoodCost() {
		return FOOD_COST_SPEARMAN;
	}

	public int getWoodCost() {
		return WOOD_COST_SPEARMAN;
	}

	public int getIronCost() {
		return IRON_COST_SPEARMAN;
	}

	public int getManaCost() {
		return MANA_COST_SPEARMAN;
	}

	public int getChanceGeneratingWaste() {
		return CHANCE_GENERATNG_WASTE_SPEARMAN;
	}

	public int getChanceAttackAgain() {
		return CHANCE_ATTACK_AGAIN_SPEARMAN;
	}
	
}

