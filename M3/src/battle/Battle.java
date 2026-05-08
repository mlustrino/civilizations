package battle;
import java.util.ArrayList;

import javax.xml.catalog.Catalog;

import Attack.*;
import defenseUnit.*;
import militaryUnit.MilitaryUnit;
import specialUnit.Magician;
import specialUnit.Priest;


public class Battle {
	private ArrayList<MilitaryUnit> civilizationArmy;
	private ArrayList<MilitaryUnit> enemyArmy;
	private ArrayList<MilitaryUnit> [][] armies;
	private String battleDevelopment;
	private int [][] initialCostFleet;
	private int initialNumberUnitsCivilization, initialNumberUnitsEnemy;
	private int [] wasteWoodIron;
	private int enemyDrops, civilizationDrops;
	private int [][] resourcesLooses;
	private int [][] initialArmies;
	private int [] actualNumberUnitsCivilization, actualNumberUnitsEnemy;
	
	public Battle(ArrayList<MilitaryUnit> civilizationArmy, ArrayList<MilitaryUnit> enemyArmy) {
		super();
		this.civilizationArmy = civilizationArmy;
		this.enemyArmy = enemyArmy;
		
	    // INICIALIZAR ARMIES
		this.armies = new ArrayList[2][9];
		for (int i = 0; i < 2; i++) {
		    for (int j = 0; j < 9; j++) {
		        this.armies[i][j] = new ArrayList<MilitaryUnit>();
		    }
		}
		
		for (MilitaryUnit unit : civilizationArmy) { // Este for es para anadir las unidades de nuestra civilizacion en el arrayList de armies
			if (unit instanceof Swordsman) { 
				armies[0][0].add(unit); // armies de 0 es nuestro ejercito y armies de 1 es el ejercito enemigo
			}
			else if (unit instanceof Spearman) {
				armies[0][1].add(unit);
			}
			else if (unit instanceof Crosswob) {
				armies[0][2].add(unit);
			}
			else if (unit instanceof Cannon) {
				armies[0][3].add(unit);
			}
			else if (unit instanceof ArrowTower) {
				armies[0][4].add(unit);
			}
			else if (unit instanceof Catapult) {
				armies[0][5].add(unit);
			}
			else if (unit instanceof RocketLauncherTower) {
				armies[0][6].add(unit);
			}
			else if (unit instanceof Magician) {
				armies[0][7].add(unit);
			}
			else if (unit instanceof Priest) {
				armies[0][8].add(unit);
			}
		}
		
		for (MilitaryUnit unit : enemyArmy) { // En este for no comprobamos el resto de clases ya que el ejercito enemigo solamente puede tener estas tropas
			if (unit instanceof Swordsman) { 
				armies[1][0].add(unit); 
			}
			else if (unit instanceof Spearman) {
				armies[1][1].add(unit);
			}
			else if (unit instanceof Crosswob) {
				armies[1][2].add(unit);
			}
			else if (unit instanceof Cannon) {
				armies[1][3].add(unit);
			}
		}
			
		this.battleDevelopment = "";
		
		this.initialCostFleet = new int [2][3];
		for (MilitaryUnit unit : civilizationArmy) {
			initialCostFleet[0][0] += unit.getFoodCost(); //Comida
			initialCostFleet[0][1] += unit.getWoodCost(); //Madera
			initialCostFleet[0][2] += unit.getIronCost(); //Hierro
		}
		
		for (MilitaryUnit unit : enemyArmy) {
			initialCostFleet[1][0] += unit.getFoodCost(); //Comida
			initialCostFleet[1][1] += unit.getWoodCost(); //Madera
			initialCostFleet[1][2] += unit.getIronCost(); //Hierro
		}
		
		this.initialNumberUnitsCivilization = getCivilizationArmy().size();
		this.initialNumberUnitsEnemy = getEnemyArmy().size();
		
		this.wasteWoodIron = new int [2];
		
		this.enemyDrops = 0;
		this.civilizationDrops = 0;
		
		this.resourcesLooses = new int [2][4];

		
		this.actualNumberUnitsCivilization = new int[9];
		this.actualNumberUnitsEnemy = new int[4];
	}
	
	
	
	//Setters y getters
	public ArrayList<MilitaryUnit> getCivilizationArmy() {
		return civilizationArmy;
	}
	public ArrayList<MilitaryUnit> getEnemyArmy() {
		return enemyArmy;
	}
	
	public int getInitialNumberUnitsCivilization() {
		return initialNumberUnitsCivilization;
	}

	public int getInitialNumberUnitsEnemy() {
		return initialNumberUnitsEnemy;
	}
	
	public ArrayList<MilitaryUnit>[][] getArmies() {
		return armies;
	}
	
	public int[][] getInitialArmies() {
		return initialArmies;
	}

	// Metodos de la clase Battle
	public String getBattleReport(int battles) {
		return "Has librado un total de " + battles;
	}
	
	public String getBattleDevelopment() {
		return battleDevelopment;
	}
	
	public void initInitialArmies() { // Esto nos da la cantidad de tropas que tenemos de cada tipo en cada ejercito initialArmies [0] es nuestro ejercito y de [1] es el enemigo
		initialArmies = new int [2][9];
		for (int i = 0; i < 9; i++) {
			initialArmies[0][i] = armies[0][i].size();
			initialArmies[1][i] = armies[1][i].size();
		}
 	}
	
	public void updateResourcesLooses() {
		resourcesLooses = new int [2][4];
	}
	
	public int[] fleetResourceCost(ArrayList<MilitaryUnit> army) { // Devolvemos un array con los costes del ejercito
		int[] costes = new int[3];
		for (int i = 0; i < army.size();i++) {
			costes[0] += army.get(i).getFoodCost(); // Comida, Madera, Hierro
			costes[1] += army.get(i).getWoodCost();
			costes[2] += army.get(i).getIronCost();
		}
		return costes;
	}
	
	public int initialFleetNumber(ArrayList<MilitaryUnit> army) { // Nos devuelve un array en el que en cada posicion te dice la cantidad de cada unidad que hay en el ejercito
		return army.size();
	}
	
	public float remainderPercentageFleet(ArrayList<MilitaryUnit> army) { // Este metodo nos devuelve el porcentaje restante de nuestro ejercito respecto al inicial
		float porcentaje;
		if (army == civilizationArmy) { 
			porcentaje = army.size() * 100 / initialNumberUnitsCivilization;
		} else {
			porcentaje = army.size() * 100 / initialNumberUnitsEnemy;
		}
		return porcentaje;
	}
	
	public int getGroupDefender(ArrayList<MilitaryUnit> army) {
		return 0;
	}
	
	public int getCivilizationGroupAttacker() {
		
		return 0;
	}
	
	public int getEnemyGroupAttacker() {
		
		return 0;
	}
	
	public void resetArmyArmor() {
		for (int i = 0; i < civilizationArmy.size();i++) {
			civilizationArmy.get(i).resetArmor();
		}
	}
	
	
	
	
	
}





















