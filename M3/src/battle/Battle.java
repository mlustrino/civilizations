
package battle;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JOptionPane;

import Attack.*;
import civilizations.Civilization;
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
	private String informe;
	private int [][] initialCostFleet;
	private int initialNumberUnitsCivilization, initialNumberUnitsEnemy;
	private int [] wasteWoodIron;
	private int enemyDrops, civilizationDrops;
	private int [][] resourcesLooses;
	private int [][] initialArmies;
	private int [] actualNumberUnitsCivilization, actualNumberUnitsEnemy;
	
	private Civilization civilizacion;
	public Battle(ArrayList<MilitaryUnit> civilizationArmy, ArrayList<MilitaryUnit> enemyArmy, Civilization civilizacion) {
		super();
		this.civilizationArmy = civilizationArmy;
		this.enemyArmy = enemyArmy;
		this.civilizacion = civilizacion;
		
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
			else if (unit instanceof Crossbow) {
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
			else if (unit instanceof Crossbow) {
				armies[1][2].add(unit);
			}
			else if (unit instanceof Cannon) {
				armies[1][3].add(unit);
			}
		}
			
		this.battleDevelopment = "";
		this.informe = "";
		
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

	public String getInforme() {
		return informe;
	}

	public void initInitialArmies() { //Es para inicializar el Array de initialArmies 
		initialArmies = new int [2][9];
		for (int i = 0; i < 9; i++) {
			initialArmies[0][i] = armies[0][i].size();
		}
		for (int i = 0; i < 4; i++) {
			initialArmies[1][i] = armies[1][i].size();
		}
 	}

	public int[] getWasteWoodIron() {
		return wasteWoodIron;
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
			} else {
				initialArmies[1][i] = armies[1][i].size();
			}
		}
	}
	
	public float remainderPercentageFleet(ArrayList<MilitaryUnit> [] army) { // Este metodo nos devuelve el porcentaje restante de nuestro ejercito respecto al inicial
	    int army_total = 0;
	    
	    for (int i = 0; i < army.length; i++) {
	        army_total += army[i].size();
	    }
	    
	    if (initialNumberUnitsCivilization == 0) { // En el caso de que no haya tropas, sera el 0%
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
		for (int i = 0; i < armies[0].length;i++) {
			for (MilitaryUnit unit : armies[0][i]) {
				unit.resetArmor();
			}
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
	
	public void incrementExperience() {
		for (int i = 0; i < armies[0].length;i++) {
			for (MilitaryUnit unit : armies[0][i]) {
				unit.setExperience(unit.getExperience() + 1);
			}
		}
	}
	
	public void sanctifyUnits() {
		if (armies[0][8].size() >= 1) {
			for (int i = 0; i < armies[0].length;i++) {
				for (MilitaryUnit unit : armies[0][i]) {
					unit.setSanctified(true);
				}
			}
		}
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
    	int numTurno = 0;
    	informe = "";
    	
    	battleDevelopment = "Battle Number: " + civilizacion.getBattles() + "\n";
    	resetArmyArmor();
    	
    	while (remainderPercentageFleet(armies[0]) > 20 && remainderPercentageFleet(armies[1]) > 20) {
	    	MilitaryUnit unidad_atacante, unidad_defensora;
	    	int ejercito_atacante, ejercito_defensor;
	        int grupo_atacante, grupo_defensor;
	        
	    	
	    	// **********************BLOQUE PARA DECIDIR QUIEN ATACA Y QUIEN DEFIENDE DE CADA GRUPO**************************************
	    	sanctifyUnits();
	    	
	    	if (turno == 0) {
	            ejercito_atacante = 0;
	            ejercito_defensor = 1;
	    		
	            grupo_atacante = getCivilizationGroupAttacker(); 
	            grupo_defensor = getGroupDefender(enemyArmy);
	            
	            if (armies[ejercito_atacante][grupo_atacante].size() == 0 || armies[ejercito_defensor][grupo_defensor].size() == 0) {
	            	continue;
	            }
	            
		    	battleDevelopment += "*".repeat(30) + "CHANGE ATTACKER" + "*".repeat(30) + "\n";
	            
	            int idAtacante = (int)(Math.random() * armies[ejercito_atacante][grupo_atacante].size());
	            int idDefensor = (int)(Math.random() * armies[ejercito_defensor][grupo_defensor].size());
	    		
	    		unidad_atacante = armies[ejercito_atacante][grupo_atacante].get(idAtacante);
	    		unidad_defensora = armies[ejercito_defensor][grupo_defensor].get(idDefensor);
	    		
	    		battleDevelopment += "Attacks Civilization: " + unidad_atacante.getClass().getSimpleName() + " attacks " + unidad_defensora.getClass().getSimpleName() + "\n";

	    	} else {
	            ejercito_atacante = 1;
	            ejercito_defensor = 0;
	    		
	            grupo_atacante = getEnemyGroupAttacker(); 
	            grupo_defensor = getGroupDefender(civilizationArmy);
	            
	            if (armies[ejercito_atacante][grupo_atacante].size() == 0 || armies[ejercito_defensor][grupo_defensor].size() == 0) {
	            	continue;
	            }
	            
		    	battleDevelopment += "*".repeat(30) + "CHANGE ATTACKER" + "*".repeat(30) + "\n";
	            
	            int idAtacante = (int)(Math.random() * armies[ejercito_atacante][grupo_atacante].size());
	            int idDefensor = (int)(Math.random() * armies[ejercito_defensor][grupo_defensor].size());
	            
	    		unidad_atacante = armies[ejercito_atacante][grupo_atacante].get(idAtacante);
	    		unidad_defensora = armies[ejercito_defensor][grupo_defensor].get(idDefensor);
	    		
	    		battleDevelopment += "Attacks Enemy army: " + unidad_atacante.getClass().getSimpleName() + " attacks " + unidad_defensora.getClass().getSimpleName() + "\n";
	    		
	    	}
	    	
	    	
	    	
	    	//***********************BLOQUE DE DAÑO ENTRE UNIDADES**************************************************************
	    	unidad_defensora.takeDamage(unidad_atacante.attack());
	    	battleDevelopment += unidad_atacante.getClass().getSimpleName() + " generates the damage = " + unidad_atacante.attack() + "\n";
	    	battleDevelopment += unidad_defensora.getClass().getSimpleName() + " stays with armor = " + unidad_defensora.getActualArmor() + "\n";
	    	if (unidad_defensora.getActualArmor() <= 0) { // Caso de que la unidad muera
	    		// *****************************BLOQUE DE RESIDUOS*********************************
	    		battleDevelopment += unidad_defensora.getClass().getSimpleName() + " gets eliminated" + "\n";
	    		if ((int)(Math.random() * 100) < unidad_defensora.getChanceGeneratingWaste()) {
	    			residuos_madera += unidad_defensora.getWoodCost()*(PERCENTATGE_WASTE*0.1);
	    			residuos_hierro += unidad_defensora.getIronCost()*(PERCENTATGE_WASTE*0.1);
	    		}
	    		armies[ejercito_defensor][grupo_defensor].remove(unidad_defensora);
	    		
	            if (ejercito_defensor == 0) {
	                civilizationDrops++;
	            } else {
	                enemyDrops++;
	            }
	            
	    	} else { // Caso de que la unidad viva
	    		if (unidad_atacante.getChanceAttackAgain() >= (int) (Math.random()*100)) { // Probabilidad de volver a atacar 
	    			unidad_defensora.takeDamage(unidad_atacante.attack());
	    			battleDevelopment += "Second Attack!\n" + unidad_atacante.getClass().getSimpleName() + " generates the damage = " + unidad_atacante.attack() + "\n";
	    			battleDevelopment += unidad_defensora.getClass().getSimpleName() + " stays with armor = " + unidad_defensora.getActualArmor() + "\n";
		    		if (unidad_defensora.getActualArmor() <= 0) {
		    			if ((int)(Math.random() * 100) < unidad_defensora.getChanceGeneratingWaste()) {
		    				residuos_madera += (unidad_defensora.getWoodCost() * PERCENTATGE_WASTE) / 100;
		    				residuos_hierro += (unidad_defensora.getIronCost() * PERCENTATGE_WASTE) / 100;
			    		}
	                    armies[ejercito_defensor][grupo_defensor].remove(unidad_defensora);
	                    
	                    if (ejercito_defensor == 0) {
	                        civilizationDrops++;
	                    } else {
	                        enemyDrops++;
	                    }
		    		}
	    		}
	    		
	    	}
	    	
	        actualNumberUnitsCivilization();
	        actualNumberUnitsEnemy();
	        
	        int[] costeFinalCiv = new int[3];
	        int[] costeFinalEnemy = new int[3];
	        
	        for (int i = 0; i < 9; i++) {
	            for (MilitaryUnit unit : armies[0][i]) {
	                costeFinalCiv[0] += unit.getFoodCost();
	                costeFinalCiv[1] += unit.getWoodCost();
	                costeFinalCiv[2] += unit.getIronCost();
	            }
	        }
	        
	        for (int i = 0; i < 4; i++) {
	            for (MilitaryUnit unit : armies[1][i]) {
	                costeFinalEnemy[0] += unit.getFoodCost();
	                costeFinalEnemy[1] += unit.getWoodCost();
	                costeFinalEnemy[2] += unit.getIronCost();
	            }
	        }
	        
	        resourcesLooses[0][0] = initialCostFleet[0][0] - costeFinalCiv[0];  
	        resourcesLooses[0][1] = initialCostFleet[0][1] - costeFinalCiv[1];  
	        resourcesLooses[0][2] = initialCostFleet[0][2] - costeFinalCiv[2];  
	        resourcesLooses[0][3] = resourcesLooses[0][2] + resourcesLooses[0][1]/5 + resourcesLooses[0][0]/10; 
	        
	        resourcesLooses[1][0] = initialCostFleet[1][0] - costeFinalEnemy[0];
	        resourcesLooses[1][1] = initialCostFleet[1][1] - costeFinalEnemy[1];
	        resourcesLooses[1][2] = initialCostFleet[1][2] - costeFinalEnemy[2];
	        resourcesLooses[1][3] = resourcesLooses[1][2] + resourcesLooses[1][1]/5 + resourcesLooses[1][0]/10;
	        
	    	
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
                    JOptionPane.WARNING_MESSAGE);
    	    civilizacion.setWood(civilizacion.getWood() + wasteWoodIron[0]);
    	    civilizacion.setIron(civilizacion.getIron() + wasteWoodIron[1]);
    	    incrementExperience();
    	    
	    } else {
            JOptionPane.showMessageDialog(null, 
                    "Ha ganado el ejercito enemigo", 
                    "Loser", 
                    JOptionPane.WARNING_MESSAGE);
	    }
    	
    	civilizacion.incrementBattles();
    	
    	for (int i = 0; i < 9; i++) {
    	    civilizacion.getArmy()[i].clear();
    	    civilizacion.getArmy()[i].addAll(armies[0][i]);
    	}
    	
    	informe += "BATTLE NUMBER: "+ civilizacion.getBattles() + "\nBATTLE STATISTICS\n\n";
    	informe += String.format("%-25s %10s %10s    %-25s %10s %10s","Civilization Army", "Units", "Drops", "Enemy Army", "Units", "Drops") + "\n";
    	informe += String.format("%-25s %10s %10s    %-25s %10s %10s","Swordsman", initialArmies[0][0], initialArmies[0][0]-actualNumberUnitsCivilization[0], "Swordsman", initialArmies[1][0], initialArmies[1][0]-actualNumberUnitsEnemy[0]) + "\n";
    	informe += String.format("%-25s %10s %10s    %-25s %10s %10s","Spearman", initialArmies[0][1], initialArmies[0][1]-actualNumberUnitsCivilization[1], "Spearman", initialArmies[1][1], initialArmies[1][1]-actualNumberUnitsEnemy[1]) + "\n";
    	informe += String.format("%-25s %10s %10s    %-25s %10s %10s","Crossbow", initialArmies[0][2], initialArmies[0][2]-actualNumberUnitsCivilization[2], "Crossbow", initialArmies[1][2], initialArmies[1][2]-actualNumberUnitsEnemy[2]) + "\n";
    	informe += String.format("%-25s %10s %10s    %-25s %10s %10s","Cannon", initialArmies[0][3], initialArmies[0][3]-actualNumberUnitsCivilization[3], "Cannon", initialArmies[1][3], initialArmies[1][3]-actualNumberUnitsEnemy[3]) + "\n";
    	informe += String.format("%-25s %10s %10s","Arrow Tower", initialArmies[0][4], initialArmies[0][4]-actualNumberUnitsCivilization[4]) + "\n";
    	informe += String.format("%-25s %10s %10s","Catapult", initialArmies[0][5], initialArmies[0][5]-actualNumberUnitsCivilization[5]) + "\n";
    	informe += String.format("%-25s %10s %10s","Rocket Launcher Tower", initialArmies[0][6], initialArmies[0][6]-actualNumberUnitsCivilization[6]) + "\n";
    	informe += String.format("%-25s %10s %10s","Magician", initialArmies[0][7], initialArmies[0][7]-actualNumberUnitsCivilization[7]) + "\n";
    	informe += String.format("%-25s %10s %10s","Priest", initialArmies[0][8], initialArmies[0][8]-actualNumberUnitsCivilization[8]) + "\n" + "*".repeat(85) + "\n";
    	
    	informe += String.format("%-30s %-30s","Cost Army Civilization","Cost Enemy Army") + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Food:",initialCostFleet[0][0],"Food:",initialCostFleet[1][0]) + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Wood:",initialCostFleet[0][1],"Wood:",initialCostFleet[1][1]) + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Iron:",initialCostFleet[0][2],"Iron:",initialCostFleet[1][2]) + "\n" + "*".repeat(85) + "\n";
    	
    	informe += String.format("%-30s %-30s","Looses Army Civilization","Looses Enemy Army") + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Food:",resourcesLooses[0][0],"Food:",resourcesLooses[1][0]) + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Wood:",resourcesLooses[0][1],"Wood:",resourcesLooses[1][1]) + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Iron:",resourcesLooses[0][2],"Iron:",resourcesLooses[1][2]) + "\n" + "*".repeat(85) + "\n";
    	
    	informe += String.format("%-30s","Waste Generated: " ) + "\n";
    	informe += String.format("%-15s %-15s %-15s %-15s","Wood:",residuos_madera,"Iron:",residuos_hierro) + "\n" + "*".repeat(85) + "\n";
    	
    	
    	
    	//System.out.println(informe);
    	
//    	System.out.println("View Battle Development? (S/n)");
//    	String opc = sc.nextLine();
//    	
//    	if (opc.toLowerCase().equals("s")) {
//        	System.out.println(battleDevelopment);
//    	}
	}
	

	
}