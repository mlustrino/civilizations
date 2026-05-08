package Main;
import java.util.ArrayList;
import java.util.Arrays;

import Attack.*;
import battle.*;
import defenseUnit.*;
import militaryUnit.*;
import specialUnit.*;
import variables.*;


public class pruebas_eliminar_a_futuro {

	public static void main(String[] args) {
		Cannon c1 = new Cannon(20,5);
		Cannon c2 = new Cannon();
		ArrowTower at1 = new ArrowTower(20,5);
		
		ArrayList<MilitaryUnit> civilizationArmy = new ArrayList<>();
		civilizationArmy.add(new Swordsman(2, 1));
		civilizationArmy.add(new Swordsman(2, 1));
		civilizationArmy.add(new Spearman(2, 1));
		civilizationArmy.add(new ArrowTower(2, 1));
		civilizationArmy.add(new Cannon(2, 1));

		ArrayList<MilitaryUnit> enemyArmy = new ArrayList<>();
		enemyArmy.add(new Swordsman());
		enemyArmy.add(new Swordsman());
		enemyArmy.add(new Spearman());
		enemyArmy.add(new Cannon());
		
		Battle b1 = new Battle(civilizationArmy, enemyArmy);
		System.out.println(civilizationArmy.size());
		
		System.out.println(b1.remainderPercentageFleet(civilizationArmy));
		civilizationArmy.remove(1);
		
		System.out.println("Costes del ejercito");
		int [] costes = b1.fleetResourceCost(civilizationArmy);
		for (int i = 0; i < costes.length;i++) {
			System.out.println(costes[i]);
		}
		b1.initInitialArmies();
		//System.out.println(Arrays.toString(civilizationArmy));
		
		System.out.println(Arrays.deepToString(b1.getInitialArmies())); // Para leer arrays sin bucles
		System.out.println(b1.getCivilizationArmy().size());

	}

}
