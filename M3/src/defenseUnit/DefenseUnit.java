package defenseUnit;
import militaryUnit.*;
import variables.*;


public abstract class DefenseUnit implements MilitaryUnit, Variables {
	private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;
	private boolean sanctified;
	
	public DefenseUnit(int armor, int baseDamage) {
	    this.armor = armor;
	    this.initialArmor = armor;
	    this.baseDamage = baseDamage;
	    this.experience = 0;
	    this.sanctified = false;
	}
	

	// SETTERS Y GETTERS

	public int getBaseDamage() {
		return baseDamage;
	}
	
	public boolean isSanctified() {
		return sanctified;
	}

	public void setSanctified(boolean sanctified) {
		this.sanctified = sanctified;
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
