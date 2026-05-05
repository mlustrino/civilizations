package defenseUnit;
import militaryUnit.*;
import variables.*;


abstract class DefenseUnit implements MilitaryUnit, Variables {
	private int armor;
	private int initalArmor;
	private int baseDamage;
	private int experience;
	private boolean sanctified;
	
	public DefenseUnit(int armor, int baseDamage) {
		super();
		this.armor = armor;
		this.baseDamage = baseDamage;
	}
	
	
	
	
	
}
