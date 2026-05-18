
package battle;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JOptionPane;
import javax.xml.catalog.Catalog;

import Attack.*;
import defenseUnit.*;
import variables.*;
import militaryUnit.MilitaryUnit;
import specialUnit.Magician;
import specialUnit.Priest;




public class Battle implements Variables {
	
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
		
		for (MilitaryUnit unit : civilizationArmy) { // Este for es para aÃ±adir las unidades de nuestra civilizacion en el arrayList de armies
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
		return "You have waged a total of " + battles;
	}
	
	public String getBattleDevelopment() {
		return battleDevelopment;
	}
	
	public void initInitialArmies() { //Es para inicializar el Array de initialArmies 
		initialArmies = new int [2][9];
		/*for (int i = 0; i < 9; i++) {
			initialArmies[0][i] = armies[0][i].size();
			initialArmies[1][i] = armies[1][i].size();
		}*/
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
	
	public void initialFleetNumber(ArrayList<MilitaryUnit> army) { // Pone la cantidad que hay de cada tipo de tropa de cada ejercito
		
		for (int i = 0; i < army.size(); i++) {
			
			if (army.size() == 9) {
				initialArmies[0][i] = armies[0][i].size();
			}else {
				initialArmies[1][i] = armies[1][i].size();
			}
		}
	}
	
	public float remainderPercentageFleet(ArrayList<MilitaryUnit> [] army) { // Este metodo nos devuelve el porcentaje restante de nuestro ejercito respecto al inicial
	    int army_total = 0;
	    
	    for (int i = 0; i < army.length; i++) {
	        army_total += army[i].size();
	    }
	    
	    if (army.length == 0) { // En el caso de que no haya tropas, sera el 0%
	    	return 0;
	    }
	    
	    if (army == armies[0]) {
	        return army_total * 100 / initialNumberUnitsCivilization;
	    } else {
	        return army_total * 100 / initialNumberUnitsEnemy;
	    }
		
	}
	
	public int getGroupDefender(ArrayList<MilitaryUnit> army) {
		int num_unit;
		int sel_unit;
		
		if(army == civilizationArmy) {
			sel_unit = (int) (Math.random()*9);
		}else {
			sel_unit = (int) (Math.random()*4);
		}
		
		num_unit = sel_unit;
		
		return num_unit;
	}
//	public int getGroupDefender(ArrayList<MilitaryUnit>[] armyGroups) {
//	    int total = 0;
//	    for (int i = 0; i < armyGroups.length; i++) {
//	        total += armyGroups[i].size();
//	    }
//	    if (total == 0) return 0;
//	    
//	    int random = (int)(Math.random() * total);
//	    int acumulado = 0;
//	    for (int i = 0; i < armyGroups.length; i++) {
//	        acumulado += armyGroups[i].size();
//	        if (random < acumulado) return i;
//	    }
//	    return 0;
//	}
	
    public int getCivilizationGroupAttacker() {
    	
    	int total_unit = 0;
        for (int i = 0; i < armies[0].length; i++) {
            total_unit += armies[0][i].size(); 
        }
        if (total_unit == 0) {
        	return 0;
        }
        
        int sel_unit = (int) (Math.random() * total_unit);
        
        int acumulado = 0;
        for (int i = 0; i < 9; i++) {
            acumulado += armies[0][i].size();
            if (sel_unit < acumulado) {
                return i;
            }
        }
        
        for (int i = 0; i < 9; i++) {
            if (armies[0][i].size() > 0) {
            	return i;
            }
        }
        return 0;

    }

    public int getEnemyGroupAttacker() {
    	
    	int total_unit = 0;
        for (int i = 0; i < armies[1].length; i++) {
            total_unit += armies[1][i].size(); 
        }
        if (total_unit == 0) return 0;
        
        int sel_unit = (int) (Math.random() * total_unit);
        
        int acumulado = 0;
        for (int i = 0; i < 4; i++) {
            acumulado += armies[1][i].size();
            if (sel_unit < acumulado) {
                return i;
            }
        }

        for (int i = 0; i < 4; i++) {
            if (armies[1][i].size() > 0) return i;
        }
        return 0;
    }
	
	public void resetArmyArmor() {
		for (int i = 0; i < civilizationArmy.size();i++) {
			civilizationArmy.get(i).resetArmor();
		}
	}
	
	public int [] actualNumberUnitsCivilization() {
		for (int i = 0; i < actualNumberUnitsCivilization.length;i++) {
			actualNumberUnitsCivilization[i] = armies[0][i].size();
		}
		return actualNumberUnitsCivilization;
	}
	
	public int [] actualNumberUnitsEnemy() {
		for (int i = 0; i < actualNumberUnitsEnemy.length;i++) {
			actualNumberUnitsEnemy[i] = armies[1][i].size();
		}
		return actualNumberUnitsEnemy;
	}
	
	
	public int startArmy() { // Para saber quien empieza 
		if (Math.random() < 0.5) {
			return 0;
		}
		else {
			return 1;
		}
	}
	
	public void startBattle() {
		initInitialArmies();
    	int turno = startArmy(); // Un 0 es que empieza la civilizacion, un 1 es que empiezan los enemigos
    	int residuos_madera = 0;
    	int residuos_hierro = 0;
    	
    	while (remainderPercentageFleet(armies[0]) > 20 && remainderPercentageFleet(armies[1]) > 20) {
	    	int atacante, defensor;
	    	MilitaryUnit unidad_atacante, unidad_defensora;
	    	int ejercito_atacante, ejercito_defensor;
	        int grupo_atacante, grupo_defensor;
	    	
	    	// **********************BLOQUE PARA DECIDIR QUIEN ATACA Y QUIEN DEFIENDE DE CADA GRUPO**************************************
	    	System.out.println("Nuestro ejercito: " + remainderPercentageFleet(armies[0]));
	    	System.out.println("Ejercito enemigo: "+ remainderPercentageFleet(armies[1]));
	    	if (turno == 0) {
	            ejercito_atacante = 0;
	            ejercito_defensor = 1;
	    		
	            grupo_atacante = getCivilizationGroupAttacker(); 
	            grupo_defensor = getGroupDefender(enemyArmy);
	            
	            if (armies[ejercito_atacante][grupo_atacante].size() == 0 || armies[ejercito_defensor][grupo_defensor].size() == 0) {
	            	continue;
	            }
	            
	            int idAtacante = (int)(Math.random() * armies[ejercito_atacante][grupo_atacante].size());
	            int idDefensor = (int)(Math.random() * armies[ejercito_defensor][grupo_defensor].size());
	    		
	    		unidad_atacante = armies[ejercito_atacante][grupo_atacante].get(idAtacante);
	    		unidad_defensora = armies[ejercito_defensor][grupo_defensor].get(idDefensor);

	    	} else {
	            ejercito_atacante = 1;
	            ejercito_defensor = 0;
	    		
	            grupo_atacante = getEnemyGroupAttacker(); 
	            grupo_defensor = getGroupDefender(civilizationArmy);
	            
	            if (armies[ejercito_atacante][grupo_atacante].size() == 0 || armies[ejercito_defensor][grupo_defensor].size() == 0) {
	            	continue;
	            }
	            
	            int idAtacante = (int)(Math.random() * armies[ejercito_atacante][grupo_atacante].size());
	            int idDefensor = (int)(Math.random() * armies[ejercito_defensor][grupo_defensor].size());
	            
	    		unidad_atacante = armies[ejercito_atacante][grupo_atacante].get(idAtacante);
	    		unidad_defensora = armies[ejercito_defensor][grupo_defensor].get(idDefensor);
	    		
	    	}
	    	
	    	//***********************BLOQUE DE DAÑO ENTRE UNIDADES**************************************************************
	    	unidad_defensora.takeDamage(unidad_atacante.attack());
	    	
	    	if (unidad_defensora.getActualArmor() <= 0) { // Caso de que la unidad muera
	    		// *****************************BLOQUE DE RESIDUOS*********************************
	    		if ((int)(Math.random() * 100) < unidad_defensora.getChanceGeneratingWaste()) {
	    			residuos_madera += unidad_defensora.getWoodCost()*(PERCENTATGE_WASTE*0.1);
	    			residuos_hierro += unidad_defensora.getIronCost()*(PERCENTATGE_WASTE*0.1);
	    		}
	    		armies[ejercito_defensor][grupo_defensor].remove(unidad_defensora);
	    	} else { // Caso de que la unidad viva
	    		if (unidad_atacante.getChanceAttackAgain() >= (int) (Math.random()*100)) { // Probabilidad de volver a atacar 
	    			unidad_defensora.takeDamage(unidad_atacante.attack());
	    			
		    		if (unidad_defensora.getActualArmor() <= 0) {
		    			if ((int)(Math.random() * 100) < unidad_defensora.getChanceGeneratingWaste()) {
			    			residuos_madera += unidad_defensora.getWoodCost()*(PERCENTATGE_WASTE*0.1);
			    			residuos_hierro += unidad_defensora.getIronCost()*(PERCENTATGE_WASTE*0.1);
			    		}
	                    armies[ejercito_defensor][grupo_defensor].remove(unidad_defensora);

		    		}
	    		}
	    		
	    	}
	    	
	        wasteWoodIron[0] = residuos_madera;
	        wasteWoodIron[1] = residuos_hierro;	
	        
	        
	        if (turno == 1) {
	        	turno = 0;
	        } else {
	        	turno = 1;
	        }
	    	
	    	
	    }
	    
    	if (remainderPercentageFleet(armies[0]) > remainderPercentageFleet(armies[1])) {
            JOptionPane.showMessageDialog(null, 
                    "Ha ganado el ejercito de la civilization", 
                    "Winner", 
                    JOptionPane.INFORMATION_MESSAGE);
	    } else {
            JOptionPane.showMessageDialog(null, 
                    "Ha ganado el ejercito enemigo", 
                    "Loser", 
                    JOptionPane.INFORMATION_MESSAGE);
	    }
	}
	

	
}