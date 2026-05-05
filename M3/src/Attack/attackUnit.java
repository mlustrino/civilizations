package Attack;

import variables.*;
import militaryUnit.*;

public class attackUnit implements Variables,MilitaryUnit{
	private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;
	private boolean sanctified;
	

	public attackUnit(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.initialArmor = armor;
		this.baseDamage = baseDamage;
		this.experience = 0;
		this.sanctified = false;
	}

	public int attack() {
		return 0;
	}

	public void takeDamage(int receivedDamage) {
		
	}

	public int getActualArmor() {
		return 0;
	}

	public int getFoodCost() {

		return 0;
	}

	public int getWoodCost() {
		
		return 0;
	}

	public int getIronCost() {

		return 0;
	}

	public int getManaCost() {
		
		return 0;
	}

	public int getChanceGeneratingWaste() {

		return 0;
	}

	public int getChanceAttackAgain() {
		
		return 0;
	}

	public void resetArmor() {
		
	}

	public void setExperience(int n) {
		
	}

	public int getExperience() {

		return 0;
	}
	
}
