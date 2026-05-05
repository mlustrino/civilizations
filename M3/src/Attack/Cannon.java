package Attack;

public class Cannon extends attackUnit{
	
	private Cannon(int armor,int baseDamage) {
		super(armor,baseDamage);
	}
	
	private Cannon() {
		super(8000,700);
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
