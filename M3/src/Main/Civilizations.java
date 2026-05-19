package Main;

import dao.BattleLogDAO;
import dao.BattleStatsDAO;
import dao.CivilizationDAO;
import dao.impl.BattleLogDAOImpl;
import dao.impl.BattleStatsDAOImpl;
import dao.impl.CivilizationDAOImpl;
import database.DBConnection;

import exceptions.BuildingException;
import exceptions.ResourceException;
import militaryUnit.MilitaryUnit;
import civilizations.*;
import variables.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import Attack.Cannon;
import Attack.Crosswob;
import Attack.Spearman;
import Attack.Swordsman;
import battle.Battle;

public class Civilizations extends JFrame {

    private PanelInicio panel_inicio;
    private PanelJuego panel_juego;
    private PanelCreacionTropas panel_creacion_tropas;
    private BufferedImage icono_juego;
    
	public static void main(String[] args) {
		new Civilizations();
	}
	public Civilizations(){
		try {
			icono_juego = ImageIO.read(new File("./src/Main/img/logo_civilizations.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		//setBounds(200,100,800,600);
		setBounds(0,0,800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Civilizations");
		setIconImage(icono_juego);
		System.out.println("hola");
		
		DBConnection.getInstance();
		panel_inicio = new PanelInicio(this);
		//panel_juego = new PanelJuego(this);
		add(panel_inicio);
		setVisible(true);
	}
	
	public void mostrarJuego(String nombreCivilizacion) {
	    Civilization civ = new Civilization(3000, 3000, 3000, 3000);
	    civ.setName(nombreCivilizacion);
	    new CivilizationDAOImpl().insertCivilization(civ);
	    iniciarJuego(civ);
	}
	public void mostrarJuego() {
	    remove(panel_inicio);
	    add(panel_juego);
	    revalidate(); // El revalidate nos sirve para que el JFrame recalcule el layout, de la misma manera que repaint sirve para decirle que vuelva a pintar
	    repaint();
	}
	
	public void mostrarJuegoExistente(int civilizationId) {
	    Civilization civ = new CivilizationDAOImpl().loadCivilization(civilizationId);
	    if (civ != null) iniciarJuego(civ);
	}

	private void iniciarJuego(Civilization civ) {
	    panel_juego = new PanelJuego(this, civ);
	    remove(panel_inicio);
	    add(panel_juego);
	    revalidate();
	    repaint();
	}
	
	public void abrirVentanaTropas(Civilization civilizacion, PanelJuego panelJuego) {
		new Frame_unidades(civilizacion, panelJuego);
	}

}

class Frame_unidades extends JFrame {
    private PanelCreacionTropas panel_creacion_tropas;
    
    public Frame_unidades(Civilization civilizacion, PanelJuego panel_juego) {
		setTitle("Creacion de Tropas");
        setBounds(800, 0, 750, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        add(new PanelCreacionTropas(civilizacion, panel_juego));
        
        setVisible(true);
	}
}
class PanelInicio extends JPanel {
	private BufferedImage fondo_inicio;
	
    public PanelInicio(Civilizations ventana) {
    	setLayout(new BorderLayout());
    	
        try {
            fondo_inicio = ImageIO.read(new File("./src/Main/img/civilization_portada.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(3, 1));
        
        JButton button_inicio_start = new JButton("Start New Game");
        JButton button_inicio_load = new JButton("Load Game");
        JButton button_inicio_exit = new JButton("Exit");
        
        button_inicio_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				String nombre = JOptionPane.showInputDialog(
				    ventana,
				    "Enter your civilization name:",
				    "New Game",
				    JOptionPane.QUESTION_MESSAGE);
				if (nombre != null && !nombre.trim().isEmpty()) {
				    ventana.mostrarJuego(nombre.trim());
				}
			}
		});

        button_inicio_load.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                ArrayList<Civilization> lista = new CivilizationDAOImpl().getAllCivilizations();
                if (lista.isEmpty()) {
                    JOptionPane.showMessageDialog(ventana,
                        "No saved civilizations found.",
                        "Load Game", JOptionPane.INFORMATION_MESSAGE);
                    return;
                }
                String[] opciones = new String[lista.size()];
                for (int i = 0; i < lista.size(); i++) {
                    Civilization c = lista.get(i);
                    opciones[i] = c.getName() + " [ID: " + c.getCivilization_id() + "]";
                }
                String seleccion = (String) JOptionPane.showInputDialog(
                    ventana,
                    "Select a civilization to load:",
                    "Load Game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    opciones,
                    opciones[0]);
                if (seleccion != null) {
                    for (int i = 0; i < opciones.length; i++) {
                        if (opciones[i].equals(seleccion)) {
                            ventana.mostrarJuegoExistente(lista.get(i).getCivilization_id());
                            break;
                        }
                    }
                }
            }
        });
        
        button_inicio_exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				// Hace que acabe el programa
				
				/*
				 * Gurdar todo lo que esté abierto.
				 * */
				
			}
		});
        
        panelBotones.add(button_inicio_start);
        panelBotones.add(button_inicio_load);
        panelBotones.add(button_inicio_exit);
        
        add(panelBotones, BorderLayout.SOUTH);
    }
    
    protected void paintComponent(Graphics g2d) {
        super.paintComponent(g2d);
        g2d.drawImage(fondo_inicio.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
    }
}
class PanelJuego extends JPanel implements Variables {
	private BufferedImage fondo_juego;
	private ImageIcon icono_madera, icono_comida, icono_hierro, icono_mana, icono_tech_att, icono_tech_def;
    private Civilization civilizacion;  
    private CivilizationDAO civilDao;        
    private JLabel comida, madera, hierro, mana; 
    private JLabel lvl_tech_attack, lvl_tech_defense; 
    private JLabel coste_tech_attack, coste_tech_defense;
    private JLabel label_food_gen, label_wood_gen, label_iron_gen, label_mana_gen;
    private JLabel cant_farm, cant_carpentry, cant_smithy, cant_magictower, cant_church;
    private JLabel enemy_swordsman, enemy_spearman, enemy_crossbow, enemy_cannon;
    private JLabel civilization_swordsman, civilization_spearman, civilization_crossbow, civilization_cannon;
    private JLabel civilization_arrowtower, civilization_catapult, civilization_rocketlauncher;
    private JLabel civilization_magician, civilization_priest;
    private Timer timer, timer_batalla;
	
	
//    public PanelJuego(Civilizations ventana) {
    public PanelJuego(Civilizations ventana, Civilization civilizacion) {
    	
    	
    	this.civilizacion = civilizacion;
    	civilDao = new CivilizationDAOImpl();
    	setLayout(new BorderLayout());
    	
        try {
        	// Imagenes que usaremos en la interficie grafica
            fondo_juego = ImageIO.read(new File("./src/Main/img/fondo_ciudad.png"));
            
            BufferedImage imgmadera = ImageIO.read(new File("./src/Main/img/wood.png"));
            BufferedImage imgcomida = ImageIO.read(new File("./src/Main/img/bread.png"));
            BufferedImage imghierro = ImageIO.read(new File("./src/Main/img/iron.png"));
            BufferedImage imgmana = ImageIO.read(new File("./src/Main/img/mana.png"));
            BufferedImage imgtechatt = ImageIO.read(new File("./src/Main/img/tec_att.png"));
            BufferedImage imgtechdef = ImageIO.read(new File("./src/Main/img/tec_def.png"));
            
            
            icono_madera = new ImageIcon(imgmadera.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            icono_comida = new ImageIcon(imgcomida.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            icono_hierro = new ImageIcon(imghierro.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            icono_mana = new ImageIcon(imgmana.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            icono_tech_att = new ImageIcon(imgtechatt.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
            icono_tech_def = new ImageIcon(imgtechdef.getScaledInstance(30, 30, Image.SCALE_SMOOTH));
                        
            
            
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
        
        // Panel de los recursos (madera, hierro, etc.)
        JPanel panel_recursos = new JPanel();
        panel_recursos.setLayout(new GridLayout(1,5));
        
        comida = new JLabel("Food: " + civilizacion.getFood(), icono_comida, JLabel.LEFT);
        madera = new JLabel("Wood: " + civilizacion.getWood(), icono_madera, JLabel.LEFT);
        hierro = new JLabel("Iron: " + civilizacion.getIron(), icono_hierro, JLabel.LEFT);
        mana = new JLabel("Mana: " + civilizacion.getMana(), icono_mana, JLabel.LEFT);
        lvl_tech_attack = new JLabel("Att Level: " + civilizacion.getTechnologyAttack(), icono_tech_att, JLabel.LEFT);
        lvl_tech_defense = new JLabel("Def Level: " + civilizacion.getTechnologyDefense(), icono_tech_def, JLabel.LEFT);
        
        comida.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        madera.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        hierro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        mana.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lvl_tech_attack.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        lvl_tech_defense.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        
        panel_recursos.add(comida);
        panel_recursos.add(madera);
        panel_recursos.add(hierro);
        panel_recursos.add(mana);
        panel_recursos.add(lvl_tech_attack);
        panel_recursos.add(lvl_tech_defense);
        
        panel_recursos.setOpaque(false);
        
        add(panel_recursos,BorderLayout.NORTH);
        
        // Construir, crear unidades y subir de nivel de tecnologia
        JPanel panel_construccion = new JPanel();
        panel_construccion.setLayout(new GridLayout(1,5));
        
        JButton cons_granja = new JButton("Build a farm");
        JButton cons_carpinteria = new JButton("Build a carpentry");
        JButton cons_herreria = new JButton("Build a smithy");
        JButton cons_torre_magica = new JButton("Build a magic tower");
        JButton cons_iglesia = new JButton("Build a church");
        
        cons_granja.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.newFarm();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Farm!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        cons_carpinteria.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.newCarpentry();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Carpentry!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        cons_herreria.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.newSmithy();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Smithy!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        cons_torre_magica.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.newMagicTower();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Magic Tower!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        cons_iglesia.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.newChurch();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Church!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        
        
        
        Dimension size_button = new Dimension(150,50);
        panel_construccion.setPreferredSize(size_button);
        
        panel_construccion.add(cons_granja);
        panel_construccion.add(cons_carpinteria);
        panel_construccion.add(cons_herreria);
        panel_construccion.add(cons_torre_magica);
        panel_construccion.add(cons_iglesia);
        
        add(panel_construccion,BorderLayout.SOUTH);
        
        // Panel tecnologias
        JPanel panel_tech = new JPanel();
        panel_tech.setLayout(new BorderLayout());
        panel_tech.setOpaque(false);
        
        // Panel de arriba
        JPanel panel_tech_upgrades = new JPanel();
        panel_tech_upgrades.setLayout(new GridLayout(4,1));
        panel_tech_upgrades.setOpaque(false);
        
        
        JButton boton_upgrade_tech_attack = new JButton("Upgrade attack tech");
        coste_tech_attack = new JLabel("Wood: " + UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST + " Iron: " + UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST);
        JButton boton_upgrade_tech_defense = new JButton("Upgrade defense tech");
        coste_tech_defense = new JLabel("Wood: " + UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST + " Iron: " + UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST);
        
        //button_inicio_start.addActionListener(new ActionListener() {
        boton_upgrade_tech_attack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.upgradeTechnologyAttack();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
                    "Not enough resources to upgrade Technology Attack!\n" + e1, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
        
        boton_upgrade_tech_defense.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					civilizacion.upgradeTechnologyDefense();
					actualizarRecursos();
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
                    "Not enough resources to upgrade Technology Defense!\n" + e1, 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
				}
				
				
			}
		});
        
        
        
        
        panel_tech_upgrades.add(boton_upgrade_tech_attack);
        panel_tech_upgrades.add(coste_tech_attack);
        panel_tech_upgrades.add(boton_upgrade_tech_defense);
        panel_tech_upgrades.add(coste_tech_defense);
        
        panel_tech.add(panel_tech_upgrades, BorderLayout.NORTH);
        
        
        JPanel panel_cantidad_edificios = new JPanel();
        panel_cantidad_edificios.setLayout(new GridLayout(5,1));
        
        cant_farm = new JLabel("Farm: " + civilizacion.getFarm());
        cant_carpentry = new JLabel("Carpentry: " + civilizacion.getCarpentry());
        cant_smithy = new JLabel("Smithy: " + civilizacion.getSmithy());
        cant_magictower = new JLabel("Magic Tower: " + civilizacion.getMagicTower());
        cant_church = new JLabel("Church: " + civilizacion.getChurch());
        
        panel_cantidad_edificios.add(cant_farm);
        panel_cantidad_edificios.add(cant_carpentry);
        panel_cantidad_edificios.add(cant_smithy);
        panel_cantidad_edificios.add(cant_magictower);
        panel_cantidad_edificios.add(cant_church);
        
        panel_tech.add(panel_cantidad_edificios, BorderLayout.CENTER);
        
        JPanel panel_tech_info = new JPanel();
        panel_tech_info.setLayout(new GridLayout(4,1));
        
        label_food_gen = new JLabel("You make " + (CIVILIZATION_FOOD_GENERATED + CIVILIZATION_FOOD_GENERATED_PER_FARM*civilizacion.getFarm()) + " food/min");
        label_wood_gen = new JLabel("You make "+ (CIVILIZATION_WOOD_GENERATED + CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY*civilizacion.getCarpentry()) + " wood/min");
        label_iron_gen = new JLabel("You make " + (CIVILIZATION_IRON_GENERATED + CIVILIZATION_IRON_GENERATED_PER_SMITHY*civilizacion.getSmithy()) + " iron/min");
        label_mana_gen = new JLabel("You make " + (CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER*civilizacion.getMagicTower())+  " mana/min");
        
        
        panel_tech_info.add(label_food_gen);
        panel_tech_info.add(label_wood_gen);
        panel_tech_info.add(label_iron_gen);
        panel_tech_info.add(label_mana_gen);
        
        panel_tech_info.setBackground(Color.ORANGE);
        
        panel_tech.add(panel_tech_info, BorderLayout.SOUTH);
        
        add(panel_tech,BorderLayout.EAST);
        
        enemy_swordsman = new JLabel("Swordsman: ");
        enemy_spearman =  new JLabel("Spearman: ");
        enemy_crossbow = new JLabel("Crossbow: ");
        enemy_cannon = new JLabel("Cannon: ");
        
        civilization_swordsman = new JLabel("Swordsman: " + civilizacion.getArmy()[0].size());
        civilization_spearman = new JLabel("Spearman: " + civilizacion.getArmy()[1].size());
        civilization_crossbow = new JLabel("Crossbow: " + civilizacion.getArmy()[2].size());
        civilization_cannon = new JLabel("Cannon: " + civilizacion.getArmy()[3].size());
        civilization_arrowtower = new JLabel("Arrow Tower: " + civilizacion.getArmy()[4].size());
        civilization_catapult = new JLabel("Catapult: " + civilizacion.getArmy()[5].size());
        civilization_rocketlauncher = new JLabel("Rocket Tower: " + civilizacion.getArmy()[6].size());
        civilization_magician = new JLabel("Magician: " + civilizacion.getArmy()[7].size());
        civilization_priest = new JLabel("Priest: " + civilizacion.getArmy()[8].size());
        
        
        JPanel panel_info_tropas = new JPanel();
        panel_info_tropas.setLayout(new GridLayout(16,1));
        
        JButton boton_crear_tropas = new JButton("Crear Tropas"); 
        boton_crear_tropas.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	ventana.abrirVentanaTropas(civilizacion, PanelJuego.this);
            }
        });
        
        panel_info_tropas.add(new JLabel("Tropas enemigas")); //AQUI
        panel_info_tropas.add(enemy_swordsman);
        panel_info_tropas.add(enemy_spearman);
        panel_info_tropas.add(enemy_crossbow);
        panel_info_tropas.add(enemy_cannon);
        
        panel_info_tropas.add(new JLabel("Tus tropas"));
        panel_info_tropas.add(civilization_swordsman);
        panel_info_tropas.add(civilization_spearman);
        panel_info_tropas.add(civilization_crossbow);
        panel_info_tropas.add(civilization_cannon);
        panel_info_tropas.add(civilization_arrowtower);
        panel_info_tropas.add(civilization_catapult);
        panel_info_tropas.add(civilization_rocketlauncher);
        panel_info_tropas.add(civilization_magician);
        panel_info_tropas.add(civilization_priest);
        
        
        
        panel_info_tropas.add(boton_crear_tropas);
        add(panel_info_tropas, BorderLayout.WEST);
        //add(boton_crear_tropas, BorderLayout.WEST);
        
        
       
        
        startTimer();
        battleStarts();
        
        
        setFocusable(true);
        
    }
    
    private void startTimer() { // Timer para aumentar nuestros recursos cada x tiempo
    	timer = new Timer();
    	TimerTask task_recursos = new TimerTask() {
			
			public void run() {
				civilizacion.setFood(civilizacion.getFood() + (CIVILIZATION_FOOD_GENERATED + CIVILIZATION_FOOD_GENERATED_PER_FARM*civilizacion.getFarm()));
				civilizacion.setWood(civilizacion.getWood() + (CIVILIZATION_WOOD_GENERATED + CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY*civilizacion.getCarpentry()));
				civilizacion.setIron(civilizacion.getIron() + (CIVILIZATION_IRON_GENERATED + CIVILIZATION_IRON_GENERATED_PER_SMITHY*civilizacion.getSmithy()));
				civilizacion.setMana(civilizacion.getMana() + (CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER*civilizacion.getMagicTower()));
				actualizarRecursos();
			}
		};
		//timer.schedule(task_recursos, 60000, 60000); // cada 60 segundos
		timer.schedule(task_recursos, 1000, 1000);
    }
    
    
    private void battleStarts() { // Metodo para empezar la batalla
    	timer_batalla = new Timer();
    	TimerTask task_batalla = new TimerTask() {
			
			public void run() {				
				ArrayList<MilitaryUnit> enemyArmy = createEnemyArmy();
				
				ArrayList<MilitaryUnit> civilizationArmy = new ArrayList<>();
				for (int i = 0; i < civilizacion.getArmy().length; i++) {
				    for (int j = 0; j < civilizacion.getArmy()[i].size(); j++) {
				        civilizationArmy.add(civilizacion.getArmy()[i].get(j));
				    }
				}
				Battle batalla = new Battle(civilizationArmy, enemyArmy);
				new Frame_batalla(civilizacion, batalla);
				
			}
		};
		timer_batalla.schedule(task_batalla, 30000, 30000); // Cada 30 segundos
    }
    
    private ArrayList<MilitaryUnit> createEnemyArmy() { 
    	ArrayList<MilitaryUnit> enemyArmy = new ArrayList<>();
    	
        int ironAvailable = IRON_BASE_ENEMY_ARMY + (civilizacion.getBattles() * ENEMY_FLEET_INCREASE * IRON_BASE_ENEMY_ARMY / 100);
        int woodAvailable = WOOD_BASE_ENEMY_ARMY + (civilizacion.getBattles() * ENEMY_FLEET_INCREASE * WOOD_BASE_ENEMY_ARMY / 100);
        int foodAvailable = FOOD_BASE_ENEMY_ARMY + (civilizacion.getBattles() * ENEMY_FLEET_INCREASE * FOOD_BASE_ENEMY_ARMY / 100);
        
        while (ironAvailable >= IRON_COST_SWORDSMAN && woodAvailable >= WOOD_COST_SWORDSMAN && foodAvailable >= FOOD_COST_SWORDSMAN) {
        	
        	int num_random = (int) (Math.random()*100);
        	        	
        	if(num_random <= 35) { // Crea Swordsman
                if (foodAvailable >= FOOD_COST_SWORDSMAN && woodAvailable >= WOOD_COST_SWORDSMAN && ironAvailable >= IRON_COST_SWORDSMAN) {
                	
            		enemyArmy.add(new Swordsman());
            		
            		foodAvailable -= FOOD_COST_SWORDSMAN;
            		woodAvailable -= WOOD_COST_SWORDSMAN;
            		ironAvailable -= IRON_COST_SWORDSMAN;
                } 
        	
        	} else if (num_random <= 60) { // Crea spearman
        		if (foodAvailable >= FOOD_COST_SPEARMAN && woodAvailable >= WOOD_COST_SPEARMAN && ironAvailable >= IRON_COST_SPEARMAN) {
            		enemyArmy.add(new Spearman());
            		foodAvailable -= FOOD_COST_SPEARMAN;
            		woodAvailable -= WOOD_COST_SPEARMAN;
            		ironAvailable -= IRON_COST_SPEARMAN;
        		} else {
            		enemyArmy.add(new Swordsman());
            		
            		foodAvailable -= FOOD_COST_SWORDSMAN;
            		woodAvailable -= WOOD_COST_SWORDSMAN;
            		ironAvailable -= IRON_COST_SWORDSMAN;
        		}
        	} else if (num_random <= 80) { // Crea Crossbow
        		if (foodAvailable >= FOOD_COST_CROSSBOW && woodAvailable >= WOOD_COST_CROSSBOW && ironAvailable >= IRON_COST_CROSSBOW) {
            		enemyArmy.add(new Crosswob());
            		foodAvailable -= FOOD_COST_CROSSBOW;
            		woodAvailable -= WOOD_COST_CROSSBOW;
            		ironAvailable -= IRON_COST_CROSSBOW;
        		} else {
            		enemyArmy.add(new Swordsman());
            		
            		foodAvailable -= FOOD_COST_SWORDSMAN;
            		woodAvailable -= WOOD_COST_SWORDSMAN;
            		ironAvailable -= IRON_COST_SWORDSMAN;
        		}
        	} else if (num_random <= 100) { // Crea cannon
        		if (foodAvailable >= FOOD_COST_CANNON && woodAvailable >= WOOD_COST_CANNON && ironAvailable >= IRON_COST_CANNON) {
            		enemyArmy.add(new Cannon());
            		foodAvailable -= FOOD_COST_CANNON;
            		woodAvailable -= WOOD_COST_CANNON;
            		ironAvailable -= IRON_COST_CANNON;
        		} else {
            		enemyArmy.add(new Swordsman());
            		
            		foodAvailable -= FOOD_COST_SWORDSMAN;
            		woodAvailable -= WOOD_COST_SWORDSMAN;
            		ironAvailable -= IRON_COST_SWORDSMAN;
        		}
        	}
        	
        }
        
        return enemyArmy;
    	
    }
    
    protected void paintComponent(Graphics g2d) { 
        super.paintComponent(g2d);
        g2d.drawImage(fondo_juego.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        
        
    }
    
    public int calcularCosteAtaque(int coste_base, int suma_porcentaje) {
    	return coste_base + (civilizacion.getTechnologyAttack() * suma_porcentaje) * coste_base / 100;
    }
    
    public int calcularCosteDefensa(int coste_base, int suma_porcentaje) {
    	return coste_base + (civilizacion.getTechnologyDefense() * suma_porcentaje) * coste_base / 100;
    }
    
    
    public void actualizarRecursos() {
        comida.setText("Food: " + civilizacion.getFood());
        madera.setText("Wood: " + civilizacion.getWood());
        hierro.setText("Iron: " + civilizacion.getIron());
        mana.setText("Mana: " + civilizacion.getMana());
        
        label_food_gen.setText("You make " + (CIVILIZATION_FOOD_GENERATED + CIVILIZATION_FOOD_GENERATED_PER_FARM*civilizacion.getFarm()) + " food/min");
        label_wood_gen.setText("You make "+ (CIVILIZATION_WOOD_GENERATED + CIVILIZATION_WOOD_GENERATED_PER_CARPENTRY*civilizacion.getCarpentry()) + " wood/min");
        label_iron_gen.setText("You make " + (CIVILIZATION_IRON_GENERATED + CIVILIZATION_IRON_GENERATED_PER_SMITHY*civilizacion.getSmithy()) + " iron/min");
        label_mana_gen.setText("You make " + (CIVILIZATION_MANA_GENERATED_PER_MAGIC_TOWER*civilizacion.getMagicTower())+  " mana/min");
        
        cant_farm.setText("Farm: " + civilizacion.getFarm());
        cant_carpentry.setText("Carpentry: " + civilizacion.getCarpentry());
        cant_smithy.setText("Smithy: " + civilizacion.getSmithy());
        cant_magictower.setText("Magic Tower: " + civilizacion.getMagicTower());
        cant_church.setText("Church: " + civilizacion.getChurch());
        
        civilization_swordsman.setText("Swordsmans: " + civilizacion.getArmy()[0].size());
        civilization_spearman.setText("Spearman: " + civilizacion.getArmy()[1].size());
        civilization_crossbow.setText("Crossbow: " + civilizacion.getArmy()[2].size());
        civilization_cannon.setText("Cannon: " + civilizacion.getArmy()[3].size());
        civilization_arrowtower.setText("Arrow Tower: " + civilizacion.getArmy()[4].size());
        civilization_catapult.setText("Catapult: " + civilizacion.getArmy()[5].size());
        civilization_rocketlauncher.setText("Rocket Tower: " + civilizacion.getArmy()[6].size());
        civilization_magician.setText("Magician: " + civilizacion.getArmy()[7].size());
        civilization_priest.setText("Priest: " + civilizacion.getArmy()[8].size());
        
        
        coste_tech_attack.setText(
                "Wood: " + calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST) +
                " Iron: " + calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST));
        
        coste_tech_defense.setText(
                "Wood: " + calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST) +
                " Iron: " + calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST));
        
        lvl_tech_attack.setText("Att Level: " + civilizacion.getTechnologyAttack());
        lvl_tech_defense.setText("Def Level: " + civilizacion.getTechnologyDefense());
        
        civilDao.updateCivilization(civilizacion);
    }
	
}

class PanelCreacionTropas extends JPanel implements Variables {
	private ImageIcon icono_cannon, icono_crossbow, icono_spearman, icono_swordsman, icono_arrowtower, icono_catapult ,icono_rocketlaunchertower, icono_magician, icono_priest;
    private JTextField cantidad_textfield;
    private Civilization civilizacion;
    private PanelJuego paneljuego;
	
    public PanelCreacionTropas(Civilization civilizacion, PanelJuego paneljuego) {
    	this.civilizacion = civilizacion;
        this.paneljuego = paneljuego;
        setLayout(new BorderLayout());
        
        try {
			BufferedImage imgcannon = ImageIO.read(new File("./src/Main/img/Cannon.png"));
	        BufferedImage imgcrossbow = ImageIO.read(new File("./src/Main/img/Crossbow.png"));
	        BufferedImage imgspearman = ImageIO.read(new File("./src/Main/img/Spearman.png"));
	        BufferedImage imgswordsman = ImageIO.read(new File("./src/Main/img/Swordsman.png"));
	        BufferedImage imgarrowtower = ImageIO.read(new File("./src/Main/img/ArrowTower.png"));
	        BufferedImage imgcatapult = ImageIO.read(new File("./src/Main/img/Cattapult.png"));
	        BufferedImage imgrocket = ImageIO.read(new File("./src/Main/img/RocketLauncherTower.png"));
	        BufferedImage imgmagician = ImageIO.read(new File("./src/Main/img/Mage.png"));
	        BufferedImage imgpriest = ImageIO.read(new File("./src/Main/img/Priest.png"));
	        
            icono_cannon = new ImageIcon(imgcannon.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_crossbow = new ImageIcon(imgcrossbow.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_spearman = new ImageIcon(imgspearman.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_swordsman = new ImageIcon(imgswordsman.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_arrowtower = new ImageIcon(imgarrowtower.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_catapult = new ImageIcon(imgcatapult.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_rocketlaunchertower = new ImageIcon(imgrocket.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_magician = new ImageIcon(imgmagician.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            icono_priest = new ImageIcon(imgpriest.getScaledInstance(60, 60, Image.SCALE_SMOOTH));
            
            
		} catch (IOException e) {
			e.printStackTrace();
		}
        
        JPanel panel_creacion_tropas = new JPanel();
        panel_creacion_tropas.setLayout(new GridLayout(1,3,15,0)); // 15 es la cantida de pixeles que se dejan entre columnas, 0 seria entre filas
        panel_creacion_tropas.setOpaque(false);
        
        // *********************TROPAS DE ATAQUE****************************
        JPanel panel_ofensivas = new JPanel();
        panel_ofensivas.setLayout(new GridLayout(9, 1,0,5)); 
        panel_ofensivas.setOpaque(false);
        panel_ofensivas.setPreferredSize(new Dimension(200,500));
        
        JLabel ataque = new JLabel("Tropas ofensivas");
        ataque.setHorizontalAlignment(JLabel.CENTER);
        ataque.setForeground(Color.WHITE);
        
        JButton button_cannon = new JButton("Cannon");
        JButton button_crossbow = new JButton("Crossbow");
        JButton button_spearman = new JButton("Spearman");
        JButton button_swordsman = new JButton("Swordsman");
        
        button_cannon.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());	
					
					int totalFood = cantidad * FOOD_COST_CANNON;
		            int totalWood = cantidad * WOOD_COST_CANNON;
		            int totalIron = cantidad * IRON_COST_CANNON;
		            int totalMana = cantidad * MANA_COST_CANNON;
					
					civilizacion.newCannon(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Cannons\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
		            
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Cannon!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        button_crossbow.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_CROSSBOW;
		            int totalWood = cantidad * WOOD_COST_CROSSBOW;
		            int totalIron = cantidad * IRON_COST_CROSSBOW;
		            int totalMana = cantidad * MANA_COST_CROSSBOW;
		            
					civilizacion.newCrossbow(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Crossbows\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
					
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Crossbow!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        button_spearman.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_SPEARMAN;
		            int totalWood = cantidad * WOOD_COST_SPEARMAN;
		            int totalIron = cantidad * IRON_COST_SPEARMAN;
		            int totalMana = cantidad * MANA_COST_SPEARMAN;
		            
					civilizacion.newSpearman(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Spearman\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Spearman!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        button_swordsman.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_SWORDSMAN;
		            int totalWood = cantidad * WOOD_COST_SWORDSMAN;
		            int totalIron = cantidad * IRON_COST_SWORDSMAN;
		            int totalMana = cantidad * MANA_COST_SWORDSMAN;
		            
					civilizacion.newSwordsman(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Swordsman\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Swordsman!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        panel_ofensivas.add(ataque);
        panel_ofensivas.add(new JLabel(icono_cannon));
        panel_ofensivas.add(button_cannon);
        panel_ofensivas.add(new JLabel(icono_crossbow));
        panel_ofensivas.add(button_crossbow);
        panel_ofensivas.add(new JLabel(icono_spearman));
        panel_ofensivas.add(button_spearman);
        panel_ofensivas.add(new JLabel(icono_swordsman));
        panel_ofensivas.add(button_swordsman);
        
     // *********************TROPAS DE DEFENSA****************************
        JPanel panel_defensivas = new JPanel();
        panel_defensivas.setLayout(new GridLayout(9, 1,0,5)); 
        panel_defensivas.setOpaque(false);
        panel_defensivas.setPreferredSize(new Dimension(200,500));
        
        JLabel defensa = new JLabel("Tropas defensivas");
        defensa.setHorizontalAlignment(JLabel.CENTER);
        defensa.setForeground(Color.WHITE);
        
        JButton button_arrowtower = new JButton("Arrow Tower");
        JButton button_catapult = new JButton("Catapult");
        JButton button_rocket = new JButton("Rocket Launcher Tower");
        
        button_arrowtower.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_ARROWTOWER;
		            int totalWood = cantidad * WOOD_COST_ARROWTOWER;
		            int totalIron = cantidad * IRON_COST_ARROWTOWER;
		            int totalMana = cantidad * MANA_COST_ARROWTOWER;
		            
					civilizacion.newArrowTower(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Arrow Tower\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
		            
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Arrow Tower!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        button_catapult.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_CATAPULT;
		            int totalWood = cantidad * WOOD_COST_CATAPULT;
		            int totalIron = cantidad * IRON_COST_CATAPULT;
		            int totalMana = cantidad * MANA_COST_CATAPULT;
		            
					civilizacion.newCatapult(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Catapult\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Catapult!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        button_rocket.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_ROCKETLAUNCHERTOWER;
		            int totalWood = cantidad * WOOD_COST_ROCKETLAUNCHERTOWER;
		            int totalIron = cantidad * IRON_COST_ROCKETLAUNCHERTOWER;
		            int totalMana = cantidad * MANA_COST_ROCKETLAUNCHERTOWER;
		            
					civilizacion.newRocketLauncher(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Rocket Launcher Tower\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Rocket Launcher Tower!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        panel_defensivas.add(defensa);
        panel_defensivas.add(new JLabel(icono_arrowtower));
        panel_defensivas.add(button_arrowtower);
        panel_defensivas.add(new JLabel(icono_catapult));
        panel_defensivas.add(button_catapult);
        panel_defensivas.add(new JLabel(icono_rocketlaunchertower));
        panel_defensivas.add(button_rocket);

     // *********************TROPAS ESPECIALES****************************
        JPanel panel_especiales = new JPanel();
        panel_especiales.setLayout(new GridLayout(9, 1,0,5)); 
        panel_especiales.setOpaque(false);
        panel_especiales.setPreferredSize(new Dimension(200,500));
        
        JLabel especiales = new JLabel("Tropas especiales");
        especiales.setHorizontalAlignment(JLabel.CENTER);
        especiales.setForeground(Color.WHITE);
        
        JButton button_magician = new JButton("Magician");
        JButton button_priest = new JButton("Priest");
        
        button_magician.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_MAGICIAN;
		            int totalWood = cantidad * WOOD_COST_MAGICIAN;
		            int totalIron = cantidad * IRON_COST_MAGICIAN;
		            int totalMana = cantidad * MANA_COST_MAGICIAN;
		            
					civilizacion.newMagician(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Magician\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
				} catch (BuildingException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "You need to build a Magic Tower first!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Magician!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        button_priest.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				try {
					int cantidad = Integer.parseInt(cantidad_textfield.getText());
					
					int totalFood = cantidad * FOOD_COST_PRIEST;
		            int totalWood = cantidad * WOOD_COST_PRIEST;
		            int totalIron = cantidad * IRON_COST_PRIEST;
		            int totalMana = cantidad * MANA_COST_PRIEST;
		            
					civilizacion.newPriest(cantidad);
					paneljuego.actualizarRecursos();
					
		            JOptionPane.showMessageDialog(null, 
		                    "Created units: "+ cantidad + " Priest\nResources you used:\nFood: " + totalFood + " Wood: " + totalWood + " Iron: " + totalIron + " Mana: " + totalMana, 
		                    "Unidad creada", 
		                    JOptionPane.INFORMATION_MESSAGE);
				} catch (BuildingException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "You need to build a church first!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Priest!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        
        panel_especiales.add(especiales);
        panel_especiales.add(new JLabel(icono_magician));
        panel_especiales.add(button_magician);
        panel_especiales.add(new JLabel(icono_priest));
        panel_especiales.add(button_priest);
        
        JPanel crear = new JPanel();
        JLabel cantidad_tropas = new JLabel("Cantidad: ");
        cantidad_textfield = new JTextField("1", 5); // El 5 es para limitar el ancho
        crear.add(cantidad_tropas);
        crear.add(cantidad_textfield);
        

        panel_creacion_tropas.add(panel_ofensivas);
        panel_creacion_tropas.add(panel_defensivas);
        panel_creacion_tropas.add(panel_especiales);

        add(panel_creacion_tropas, BorderLayout.CENTER);
        add(crear, BorderLayout.SOUTH);
        
        
	}
	
	
}

class Frame_batalla extends JFrame {
    private PanelBatalla panel_batalla;
    private Battle batalla;
    
    public Frame_batalla(Civilization civilizacion, Battle batalla) {
    	this.batalla = batalla;
    	
    	setTitle("¡ALERTA: Batalla Inminente!");
        setBounds(400, 200, 800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        this.panel_batalla = new PanelBatalla(civilizacion, batalla);
        add(this.panel_batalla);
        
        setVisible(true);
	}
	
	
    
    
}

class PanelBatalla extends JPanel implements Variables {
	private BufferedImage fondo_batalla;
	private JLabel porcentaje_ejercito_civilization, porcentaje_ejercito_enemigo;
	private Battle batalla;
	
	
	public PanelBatalla(Civilization civilizacion, Battle batalla) {
		this.batalla = batalla;
		setLayout(new BorderLayout());
		try {
			fondo_batalla = ImageIO.read(new File("./src/Main/img/fondo_batalla.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		JPanel panel_unidades_civilization = new JPanel();
		JPanel panel_unidades_enemigas = new JPanel();

		panel_unidades_civilization.setLayout(new GridLayout(1,1));
		panel_unidades_enemigas.setLayout(new GridLayout(1,1));

		porcentaje_ejercito_civilization = new JLabel("Porcentaje restante aliado = ");
		porcentaje_ejercito_enemigo = new JLabel("Porcentaje restante enemigo = ");

		panel_unidades_civilization.add(porcentaje_ejercito_civilization);
		panel_unidades_enemigas.add(porcentaje_ejercito_enemigo);

		add(panel_unidades_civilization, BorderLayout.WEST);
		add(panel_unidades_enemigas, BorderLayout.EAST);

		BattleStatsDAO  battleStatsDao = new BattleStatsDAOImpl();
		BattleLogDAO    battleLogDao   = new BattleLogDAOImpl();
		CivilizationDAO civilDao       = new CivilizationDAOImpl();

		int civId     = civilizacion.getCivilization_id();
		int numBattle = civilizacion.getBattles() + 1;

		String[] tiposAtaque   = {"Swordsman", "Spearman", "Crossbow", "Cannon"};
		String[] tiposDefensa  = {"ArrowTower", "Catapult", "RocketLauncherTower"};
		String[] tiposEspecial = {"Magician", "Priest"};

		// ======== ANTES DE LA BATALLA ========
		battleStatsDao.insertBattleStats(civId, numBattle, 0, 0);

		for (int i = 0; i < tiposAtaque.length; i++)
			battleStatsDao.insertStatsAttakCivilization(civId, numBattle,
				tiposAtaque[i], batalla.getArmies()[0][i].size(), 0);
		for (int i = 0; i < tiposDefensa.length; i++)
			battleStatsDao.insertStatsDefenseCivilization(civId, numBattle,
				tiposDefensa[i], batalla.getArmies()[0][4+i].size(), 0);
		for (int i = 0; i < tiposEspecial.length; i++)
			battleStatsDao.insertStatsSpecialCivilization(civId, numBattle,
				tiposEspecial[i], batalla.getArmies()[0][7+i].size(), 0);
		for (int i = 0; i < tiposAtaque.length; i++)
			battleStatsDao.insertStatsEnemiAttak(civId, numBattle,
				tiposAtaque[i], batalla.getArmies()[1][i].size(), 0);

		// ======== LA BATALLA ========
		batalla.startBattle();

		// ======== DESPUÉS DE LA BATALLA ========
		for (int i = 0; i < tiposAtaque.length; i++) {
			int drops = batalla.getInitialArmies()[0][i] - batalla.getArmies()[0][i].size();
			battleStatsDao.updateDropsAttakCivilization(civId, numBattle, tiposAtaque[i], drops);
		}
		for (int i = 0; i < tiposDefensa.length; i++) {
			int drops = batalla.getInitialArmies()[0][4+i] - batalla.getArmies()[0][4+i].size();
			battleStatsDao.updateDropsDefenseCivilization(civId, numBattle, tiposDefensa[i], drops);
		}
		for (int i = 0; i < tiposEspecial.length; i++) {
			int drops = batalla.getInitialArmies()[0][7+i] - batalla.getArmies()[0][7+i].size();
			battleStatsDao.updateDropsSpecialCivilization(civId, numBattle, tiposEspecial[i], drops);
		}
		for (int i = 0; i < tiposAtaque.length; i++) {
			int drops = batalla.getInitialArmies()[1][i] - batalla.getArmies()[1][i].size();
			battleStatsDao.updateDropsEnemyAttak(civId, numBattle, tiposAtaque[i], drops);
		}

		int woodAdquired = batalla.getWasteWoodIron()[0];
		int ironAdquired = batalla.getWasteWoodIron()[1];
		battleStatsDao.updateBattleStats(civId, numBattle, woodAdquired, ironAdquired);
		battleLogDao.insert(civId, numBattle, batalla.getBattleDevelopment());

		civilizacion.setBattles(numBattle);
		civilizacion.setWood(civilizacion.getWood() + woodAdquired);
		civilizacion.setIron(civilizacion.getIron() + ironAdquired);
		civilDao.updateCivilization(civilizacion);
	}
	
	
    public void actualizarEstadisticas() {
    	
    }
	
    protected void paintComponent(Graphics g2d) { 
        super.paintComponent(g2d);
        g2d.drawImage(fondo_batalla.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        
        
    }
}
