package civilizations;

import java.util.ArrayList;
import militaryUnit.*;
import specialUnit.Magician;
import specialUnit.Priest;
import variables.Variables;
import defenseUnit.*;
import Attack.*;
import exceptions.*;

public class Civilization implements Variables {
	    
    public static final int UNIT_ARRAY_LENGTH = 9;
    
	// Technology
	private int technologyDefense ;
	private int technologyAttack ;
	
	// Recursos
	private int wood;
	private int iron;
	private int food;
	private int mana;
	
	// Construcciones
	private int magicTower;
	private int church;
	private int farm;
	private int smithy;
	private int carpentry;
	
	// Battle counter
	private int battles;
	
	// ArrayList Armas
	private ArrayList<MilitaryUnit>[] army;
	
		
	private int upgradeDefenseTechnologyWoodCost; 
	private int upgradeAttackTechnologyWoodCost;
	private int upgradeDefenseTechnologyIronCost; 
	private int upgradeAttackTechnologyIronCost;    

	/*NOUS CAMPS**/
	private String name;
	private int civilization_id;


	public Civilization(int wood, int iron, int food, int mana) {
		super();
		this.wood = wood;
		this.iron = iron;
		this.food = food;
		this.mana = mana;
		initArrayArmy();
		recalcularTechnologyCosts();
	}
	
	public Civilization() {
		this(0,0,0,0);
	}
	
	/*
	 * Inicializamos arrayList de armas
	 * */
	private void initArrayArmy() {
		army = new ArrayList[UNIT_ARRAY_LENGTH];

		for(int i = 0; i < army.length; i++) {
		    army[i] = new ArrayList<MilitaryUnit>();
		}
	}

	/*
	 * 
	 * Método que ejecutaremos cada vez que el nivel de tecnología de defensa o ataque suba.
	 * 
	 * Requeriments: 
	 * Tenemos que tener en cuenta, que cada vez que subimos un nivel de tecnología, la siguiente actualización será un porcentaje establecido más caro.
	 * 
	 * **/
	private void recalcularTechnologyCosts() {
		upgradeDefenseTechnologyWoodCost = 
				UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST + technologyDefense * UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST;
		upgradeDefenseTechnologyIronCost = 
				UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST + technologyDefense * UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST;
		upgradeAttackTechnologyWoodCost =
				UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST + technologyAttack * UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST;
		upgradeAttackTechnologyIronCost = 
				UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST + technologyAttack * UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST;
			
	}	
	

	/**
	 * CREACIÓN EDIFICIOS
	 * 
	 * Con estos métodos podemos crear una nueva iglesia o una nueva torre mágica.
	 * En caso de no tener recursos suficientes, lanzaremos una excepción del tipo ResourceException, 
	 * que comentaremos más adelante.
	 * */
	public void newChurch() throws ResourceException{
		if (food < FOOD_COST_CHURCH || wood < WOOD_COST_CHURCH || iron < IRON_COST_CHURCH) {
			throw new ResourceException("Church", FOOD_COST_CHURCH, WOOD_COST_CHURCH, IRON_COST_CHURCH, food, wood, iron);
		}
		food -= FOOD_COST_CHURCH;
		wood -= WOOD_COST_CHURCH;
		iron -= IRON_COST_CHURCH;
		church++;		
	}
	
	public void newMagicTower() throws ResourceException{
		if (food < FOOD_COST_MAGICTOWER || wood < WOOD_COST_MAGICTOWER || iron < IRON_COST_MAGICTOWER) {
			throw new ResourceException("Magic tower",  FOOD_COST_MAGICTOWER, WOOD_COST_MAGICTOWER, IRON_COST_MAGICTOWER, food, wood, iron);

			
		}		
		food -= FOOD_COST_MAGICTOWER;
		wood -= WOOD_COST_MAGICTOWER;
		iron -= IRON_COST_MAGICTOWER;
		magicTower++;		
	}
		
	public void newFarm() throws ResourceException{
		if (food < FOOD_COST_FARM || wood < WOOD_COST_FARM || iron < IRON_COST_FARM) {
			throw new ResourceException("food", FOOD_COST_FARM, WOOD_COST_FARM, IRON_COST_FARM, food, wood, iron);
		}		
		food -= FOOD_COST_FARM;
		wood -= WOOD_COST_FARM;
		iron -= IRON_COST_FARM;
		farm++;		
	}
	public void newCarpentry() throws ResourceException{
		if (food < FOOD_COST_CARPENTRY || wood < WOOD_COST_CARPENTRY || iron < IRON_COST_CARPENTRY) {
			throw new ResourceException("carpentry",  FOOD_COST_CARPENTRY, WOOD_COST_CARPENTRY, IRON_COST_CARPENTRY, food, wood, iron);
		}		
		food -= FOOD_COST_CARPENTRY;
		wood -= WOOD_COST_CARPENTRY;
		iron -= IRON_COST_CARPENTRY;
		carpentry++;		
	}
	public void newSmithy()  throws ResourceException{
		if (food < FOOD_COST_SMITHY || wood < WOOD_COST_SMITHY || iron < IRON_COST_SMITHY) {
			throw new ResourceException("smithy",  FOOD_COST_SMITHY, WOOD_COST_SMITHY, IRON_COST_SMITHY, food, wood, iron);
		}		
		food -= FOOD_COST_SMITHY;
		wood -= WOOD_COST_SMITHY;
		iron -= IRON_COST_SMITHY;
		smithy++;		
	}
	
	
	/**
	 * Con estos métodos, pretendemos actualizar nuestras tecnologías de ataque/defensa, 
	 * pero antes tendremos que comprobar que tenemos recursos suficientes para actualizar dicha tecnología. 
	 * En caso de no tener recursos suficientes, lanzaremos una excepción del tipo ResourceException, que comentaremos más adelante.
	 * Tenemos que tener en cuenta, que cada vez que subimos un nivel de tecnología, la siguiente actualización será un porcentaje establecido más caro.
	 * 
	 * Por ejemplo, si pasar del nivel 1 de defensa al nivel 2 de defensa costase 100 de Hierro, y el porcentaje establecido de incremento de precio fuese un 10%, 
	 * pasar del nivel 2 al nivel 3 nos costaría 110 de hierro. 
	 * Estos valores estaría previamente indicados en las características upgradeDefenseTechnologyIronCost, upgradeAttackTechnologyIronCost, upgradeDefenseTechnologyWoodCost, upgradeAttackTechnologyWoodCost = 0;

	 * 
	 * 1 _ Controlar que tenemos recursos suficientes - ResourceException
	 * 2 _ Actualizar los valores de recursos, recurso - costoRecurso
	 * 3 _ Aumentar de nivel incrementa un 10% para el siguiente nivel. 
	 * * Formula: baseCost + currentLevel * plusCost 
	 * */
	public void upgradeTechnologyDefense() throws ResourceException {
		if (wood < upgradeDefenseTechnologyWoodCost || iron < upgradeDefenseTechnologyWoodCost) {
//			throw new ResourceException("Defense Technology",technologyDefense,technologyDefense+1, upgradeDefenseTechnologyWoodCost, upgradeDefenseTechnologyWoodCost, wood, iron);
			throw new ResourceException(String.format("Defense Technology cannot be upgraded from %d to %d."
					+ "Wood=%d, Iron=%d are required.\n"
					+ "Remaining resources: Wood=%d, Iron=%d.", technologyDefense, technologyDefense+1, upgradeDefenseTechnologyWoodCost, upgradeDefenseTechnologyIronCost, wood, iron));
		}
		
		wood -= upgradeDefenseTechnologyWoodCost;
		iron -= upgradeDefenseTechnologyIronCost;
		technologyDefense++;
		recalcularTechnologyCosts();
		
	}

	public void upgradeTechnologyAttack() throws ResourceException{
		if (wood < upgradeAttackTechnologyWoodCost || iron < upgradeAttackTechnologyIronCost) {
			throw new ResourceException(String.format("Attack Technology cannot be upgraded from %d to %d."
					+ "Wood=%d, Iron=%d are required. \n"
					+ "Remaining resources: Wood=%d, Iron=%d.", technologyDefense, technologyDefense+1, upgradeDefenseTechnologyWoodCost, upgradeDefenseTechnologyIronCost, wood, iron));
		}
		
		wood -= upgradeAttackTechnologyWoodCost;
		iron -= upgradeAttackTechnologyIronCost;
		technologyAttack++;
		recalcularTechnologyCosts();
		
	}
	
    /**
     * Returns how many units of this type we can afford with current resources.
     * Considers only non-zero costs to avoid division-by-zero.
     * 
     * Devuelve cuántas unidades de este tipo podemos costear con los recursos actuales.
     * Considera únicamente los costos distintos de cero para evitar la división por cero.
     */
    private int availableUnits(int foodCost, int woodCost, int ironCost, int manaCost) {
        int max = Integer.MAX_VALUE;
        if (foodCost > 0) max = Math.min(max, food / foodCost);
        if (woodCost > 0) max = Math.min(max, wood / woodCost);
        if (ironCost > 0) max = Math.min(max, iron / ironCost);
        if (manaCost > 0) max = Math.min(max, mana / manaCost);
        return (max == Integer.MAX_VALUE) ? Integer.MAX_VALUE : max;
    }
    
    
	/*
	 * Estos métodos servirán para añadir nuevas unidades militares a nuestro ejército army mencionado anteriormente.
	 * Estos métodos reciben un entero n que indica el número de unidades que queremos añadir, si no tenemos suficientes recursos para añadirlas unidades que queremos, 
	 * lanzará una excepción del tipo ResourceException indicando el mensaje informativo. 
	 * 
	 * Pero se añadirán todas las unidades posibles que permitan nuestros recursos.
	 * Es decir, si queremos añadir 10 swordsman, y sólo tenemos recursos para añadir 5, se lanzará una excepción del tipo ResourceException, pero se añadirán los 5 
	 * swordsman que podemos generar y se nos mostrará también un mensaje informativo indicando el número de swordsman que se han añadido.
	 * 
	 * En el caso de crear magos, si no tenemos al menos una torre mágica, lanzaremos una excepción del tipo BuildingException.
	 * En el casod de crear sacerdotes, si no tenemos al menos una iglesia, lanzaremos una excepción del tipo BuildingException.
	 * 
	 * 
	 * */
	public void createUnits(int n, int idx_army, String unitName, int foodCost, int woodCost, int ironCost, int manaCost) throws ResourceException {
		int available =  availableUnits(foodCost, woodCost,ironCost, manaCost);
		int toAdd = Math.min(n, available);
		
		for (int i = 0; i < toAdd; i++) {

			switch (idx_army) {
				case IDX_UNIT_SWORDSMAN :
					army[IDX_UNIT_SWORDSMAN].add(new Swordsman(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_SPEARMAN :
					army[IDX_UNIT_SPEARMAN].add(new Spearman(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_CROSSBOW :
					army[IDX_UNIT_CROSSBOW].add(new Crosswob(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_CANNON :
					army[IDX_UNIT_CANNON].add(new Cannon(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_ARROWTOWER :
					army[IDX_UNIT_ARROWTOWER].add(new ArrowTower(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_CATAPULT :
					army[IDX_UNIT_CATAPULT].add(new Catapult(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_ROCKETLAUNCHER :
					army[IDX_UNIT_ROCKETLAUNCHER].add(new RocketLauncherTower(technologyDefense,technologyAttack));
					break;
	
				case IDX_UNIT_MAGICIAN :
					army[IDX_UNIT_MAGICIAN].add(new Magician(0,technologyAttack));
					break;
	
				case IDX_UNIT_PRIEST :
//					army[IDX_UNIT_PRIEST].add(new Priest(technologyDefense,technologyAttack));
					army[IDX_UNIT_PRIEST].add(new Priest(0,0));
					break;
			}
			
			food -= foodCost;
			wood -= woodCost;
			iron -= ironCost;
			mana -= manaCost;
		}
		if (toAdd < n) {
			throw new ResourceException(String.format("There are not resources enough to create %d %s.\n"
					+ "Unit requested: %d\n"
					+ "Created: %d\n"
					+ "Remaining resources: Food=%d, Wood=%d, Iron=%d, Mana=%d.", n, unitName, n, toAdd, food, wood, iron, mana));
		}
	}
	public void newSwordsman(int n) throws ResourceException {
		createUnits(n, IDX_UNIT_SWORDSMAN, "Swordman", FOOD_COST_SWORDSMAN, WOOD_COST_SWORDSMAN,IRON_COST_SWORDSMAN, MANA_COST_SWORDSMAN);
	}
	
	public void newSpearman(int n) throws ResourceException {
		createUnits(n, IDX_UNIT_SPEARMAN, "Spearman", FOOD_COST_SPEARMAN, WOOD_COST_SPEARMAN,IRON_COST_SPEARMAN, MANA_COST_SPEARMAN);	
	}
	public void newCrossbow(int n) throws ResourceException {
		createUnits(n, IDX_UNIT_CROSSBOW, "Crosswob",FOOD_COST_CROSSBOW, WOOD_COST_CROSSBOW,IRON_COST_CROSSBOW, MANA_COST_CROSSBOW);
	}
	public void newCannon(int n) throws ResourceException {
		createUnits(n, IDX_UNIT_CANNON, "Cannon",FOOD_COST_CANNON, WOOD_COST_CANNON,IRON_COST_CANNON, MANA_COST_CANNON);
	}
	public void newArrowTower(int n) throws ResourceException {
		createUnits(n, IDX_UNIT_ARROWTOWER, "Arrow tower",FOOD_COST_ARROWTOWER, WOOD_COST_ARROWTOWER,IRON_COST_ARROWTOWER, MANA_COST_ARROWTOWER);
	}
	public void newCatapult(int n) throws ResourceException {
		createUnits(n, IDX_UNIT_CATAPULT, "Catapult", FOOD_COST_CATAPULT, WOOD_COST_CATAPULT,IRON_COST_CATAPULT, MANA_COST_CATAPULT);
	}
	public void newRocketLauncher(int n)  throws ResourceException {
		createUnits(n, IDX_UNIT_ROCKETLAUNCHER, "Rocket launcher", FOOD_COST_ROCKETLAUNCHERTOWER, WOOD_COST_ROCKETLAUNCHERTOWER,IRON_COST_ROCKETLAUNCHERTOWER, MANA_COST_ROCKETLAUNCHERTOWER);
	}
	public void newMagician(int n) throws BuildingException, ResourceException {
		if (magicTower < 1) {
			throw new BuildingException(String.format("Recruiting a magician is not allowed; you must have at least one magician tower built."));
		}
		createUnits(n, IDX_UNIT_MAGICIAN, "Magician", FOOD_COST_MAGICIAN, WOOD_COST_MAGICIAN,IRON_COST_MAGICIAN, MANA_COST_MAGICIAN);
	}
	public void newPriest(int n) throws BuildingException, ResourceException {
		if (church < 1) {
			throw new BuildingException(String.format("Recruiting a priest is not allowed; you must have at least one church already established."));
		}
		
		int currentPriests =  army[IDX_UNIT_PRIEST].size();
		int availableSlots = church - currentPriests;
		if (availableSlots <= 0) {
			throw new BuildingException(String.format("There are %d church(s) and there are not church free.\n "
					+ "Build more churches to recruit additional priests.", church));
		}
		

        int requested = Math.min(n, availableSlots);
        
        String extraInfo = (requested < n) 
        		? String.format("(limited to %d by church capacity; built more church for add one more priest)", requested) :"";
        
		createUnits(n, IDX_UNIT_PRIEST, "Priest " + extraInfo, FOOD_COST_PRIEST, WOOD_COST_PRIEST,IRON_COST_PRIEST, MANA_COST_PRIEST);
	}
	
	
	

	
    // =========================================================================
    // GENERACIÓN DE RECURSOS ( Llamada desde TimerTask)
    // =========================================================================

    /**
     * Generar recursos para cada secuencia de tiempo (one minute of game time).
     * Base values are taken from {@link Variables}; buildings add a bonus.
     */
    public void generateResources() {
        food += CIVILIZATION_FOOD_GENERATED + (farm      * CIVILIZATION_FOOD_GENERATED_PER_FARM);
        wood += CIVILIZATION_WOOD_GENERATED + (carpentry * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY);
        iron += CIVILIZATION_IRON_GENERATED + (smithy    * CIVILIZATION_IRON_GENERATED_PER_SMITHY);
        mana += magicTower * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;
    }
	
    
    
    

    // =========================================================================
    // STATS DISPLAY
    // =========================================================================

    /**
     * Prints a formatted snapshot of the civilization's current state to stdout.
     * Mirrors the sample output shown in the project document.
     *	Este método nos servirá para mostrar una visión del estado de nuestro planeta por consola, 
	 * una posible salida cuando llamamos a este método podría ser:
     */
    public void printStats() {
        System.out.println();
        System.out.println("***************************CIVILIZATION STATS***************************");
        System.out.println("--------------------------------------------------TECHNOLOGY----------------------------------------");
        System.out.printf("  %-10s %-10s%n", "Attack", "Defense");
        System.out.printf("  %-10d %-10d%n", technologyAttack, technologyDefense);

        System.out.println("---------------------------------------------------BUILDINGS----------------------------------------");
        System.out.printf("  %-8s %-8s %-12s %-14s %-10s%n",
                "Farm", "Smithy", "Carpentry", "Magic Tower", "Church");
        System.out.printf("  %-8d %-8d %-12d %-14d %-10d%n",
                farm, smithy, carpentry, magicTower, church);

        System.out.println("----------------------------------------------------DEFENSES----------------------------------------");
        System.out.printf("  %-14s %-12s %-22s%n",
                "Arrow Tower", "Catapult", "Rocket Launcher");
        System.out.printf("  %-14d %-12d %-22d%n",
                army[IDX_UNIT_ARROWTOWER].size(), army[IDX_UNIT_CATAPULT].size(), army[IDX_UNIT_ROCKETLAUNCHER].size());

        System.out.println("------------------------------------------------ATTACK UNITS----------------------------------------");
        System.out.printf("  %-12s %-12s %-12s %-10s%n",
                "Swordsman", "Spearman", "Crossbow", "Cannon");
        System.out.printf("  %-12d %-12d %-12d %-10d%n",
                army[IDX_UNIT_SWORDSMAN].size(), army[IDX_UNIT_SPEARMAN].size(),
                army[IDX_UNIT_CROSSBOW].size(), army[IDX_UNIT_CANNON].size());

        System.out.println("----------------------------------------------SPECIAL UNITS----------------------------------------");
        System.out.printf("  %-12s %-12s%n", "Magician", "Priest");
        System.out.printf("  %-12d %-12d%n",
                army[IDX_UNIT_MAGICIAN].size(), army[IDX_UNIT_PRIEST].size());

        System.out.println("---------------------------------------------------RESOURCES----------------------------------------");
        System.out.printf("  %-10s %-10s %-10s %-10s%n", "Food", "Wood", "Iron", "Mana");
        System.out.printf("  %-10d %-10d %-10d %-10d%n", food, wood, iron, mana);

        System.out.println("----------------------------------------GENERATION RESOURCES----------------------------------------");
        int genFood = CIVILIZATION_FOOD_GENERATED + (farm      * CIVILIZATION_FOOD_GENERATED_PER_FARM);
        int genWood = CIVILIZATION_WOOD_GENERATED + (carpentry * CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY);
        int genIron = CIVILIZATION_IRON_GENERATED + (smithy    * CIVILIZATION_IRON_GENERATED_PER_SMITHY);
        int genMana = magicTower * CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER;
        System.out.printf("  %-10s %-10s %-10s %-10s%n", "Food", "Wood", "Iron", "Mana");
        System.out.printf("  %-10d %-10d %-10d %-10d%n", genFood, genWood, genIron, genMana);
        System.out.println();
    }

    // =========================================================================
    // GETTERS & SETTERS
    // =========================================================================

    public int getTechnologyDefense()    { return technologyDefense; }
    public int getTechnologyAttack()     { return technologyAttack; }
    public int getWood()                 { return wood; }
    public int getIron()                 { return iron; }
    public int getFood()                 { return food; }
    public int getMana()                 { return mana; }
    public int getMagicTower()           { return magicTower; }
    public int getChurch()               { return church; }
    public int getFarm()                 { return farm; }
    public int getSmithy()               { return smithy; }
    public int getCarpentry()            { return carpentry; }
    public int getBattles()              { return battles; }
    public String getName()              { return name; }
    public int getCivilization_id() 	 { return civilization_id; }

	

    public ArrayList<MilitaryUnit>[] getArmy() { return army; }

    public void setTechnologyDefense(int v) { this.technologyDefense = v; recalcularTechnologyCosts(); }
    public void setTechnologyAttack(int v)  { this.technologyAttack  = v; recalcularTechnologyCosts(); }
    public void setWood(int wood)           { this.wood    = wood; }
    public void setIron(int v)              { this.iron    = v; }
    public void setFood(int v)              { this.food    = v; }
    public void setMana(int v)              { this.mana    = v; }
    public void setMagicTower(int v)        { this.magicTower  = v; }
    public void setChurch(int v)            { this.church      = v; }
    public void setFarm(int v)              { this.farm        = v; }
    public void setSmithy(int v)            { this.smithy      = v; }
    public void setCarpentry(int v)         { this.carpentry   = v; }
    public void setBattles(int v)           { this.battles     = v; }
    public void setName(String n)           { this.name		   = n; }
    public void setCivilization_id(int civilization_id) { this.civilization_id = civilization_id;}
    public void incrementBattles()          { this.battles++;       }

    public int[] getNextDefenseTechCost() {
        return new int[]{ upgradeDefenseTechnologyWoodCost, upgradeAttackTechnologyIronCost};
    }

    public int[] getNextAttackTechCost() {
        return new int[]{ upgradeAttackTechnologyWoodCost, upgradeAttackTechnologyIronCost };
    }
	
}
