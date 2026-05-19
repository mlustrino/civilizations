package specialUnit;

import militaryUnit.MilitaryUnit;
import variables.Variables;

public abstract class SpecialUnit implements MilitaryUnit, Variables {
	private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;
	
	public SpecialUnit(int armor, int baseDamage) { // Segun el enunciado, armor deberia ser siempre 0 en estas unidades
		super();
		this.armor = armor;
		this.baseDamage = baseDamage;
		this.initialArmor = armor;
		this.experience = 0;
	}
	
	// SETTERS Y GETTERS

	public int getBaseDamage() {
		return baseDamage;
	}
	
	
	
	// METODOS
	public void takeDamage(int receivedDamage) {
		this.armor = this.armor - receivedDamage;
	}

	public int getActualArmor() {
		return this.armor;
	}

	public void resetArmor() {
		this.armor = initialArmor;
	}

	public void setExperience(int n) {
		this.experience = n;
	}

	public int getExperience() {
		return experience;
	}
	
	
	
	
	
}
