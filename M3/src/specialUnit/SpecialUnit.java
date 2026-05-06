package specialUnit;

import militaryUnit.MilitaryUnit;
import variables.Variables;

abstract class SpecialUnit implements MilitaryUnit, Variables {
	private int armor;
	private int initialArmor;
	private int baseDamage;
	private int experience;
	
	public SpecialUnit(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.baseDamage = baseDamage;
		this.initialArmor = armor;
		this.experience = 0;
	}
	
	
	
	
	
}
