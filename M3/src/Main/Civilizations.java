package Main;

import civilizations.*;
import exceptions.ResourceException;
import variables.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
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
import javax.swing.border.Border;

public class Civilizations extends JFrame{

    private PanelInicio panel_inicio;
    private PanelJuego panel_juego;
    
	public static void main(String[] args) {
		new Civilizations();
	}
	public Civilizations(){
		setBounds(200,100,800,600);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Civilizations");
		
		panel_inicio = new PanelInicio(this);
		panel_juego = new PanelJuego();
		add(panel_inicio);
		setVisible(true);
	}
	
	public void mostrarJuego() {
	    remove(panel_inicio);
	    add(panel_juego);
	    revalidate(); // El revalidate nos sirve para que el JFrame recalcule el layout, de la misma manera que repaint sirve para decirle que vuelva a pintar
	    repaint();
	}

}
class PanelInicio extends JPanel {
	private BufferedImage fondo_inicio;
	
    public PanelInicio(Civilizations ventana) {
    	setLayout(new BorderLayout());
    	
        try {
            fondo_inicio = ImageIO.read(new File("./M3/src/Main/img/civilization_portada.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
        
        JPanel panelBotones = new JPanel();
        panelBotones.setLayout(new GridLayout(2, 1));
        
        JButton button_inicio_start = new JButton("Start New Game");
        JButton button_inicio_exit = new JButton("Exit");
        
        button_inicio_start.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				ventana.mostrarJuego();
				
			}
		});
        
        button_inicio_exit.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
				// Hace que acabe el programa
				
			}
		});
        
        panelBotones.add(button_inicio_start);
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
    private JLabel comida, madera, hierro, mana; 
    private JLabel lvl_tech_attack, lvl_tech_defense; 
    private JLabel coste_tech_attack, coste_tech_defense;
    private JLabel label_food_gen, label_wood_gen, label_iron_gen, label_mana_gen;
    private JLabel cant_farm, cant_carpentry, cant_smithy, cant_magictower, cant_church;
    private JTextField cantidad_textfield;
    private Timer timer;
	
	
    public PanelJuego() {
    	setLayout(new BorderLayout());
    	civilizacion = new Civilization(3000,3000,3000,3000);
        try {
            fondo_juego = ImageIO.read(new File("./M3/src/Main/img/fondo_ciudad.png"));
            BufferedImage imgmadera = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            BufferedImage imgcomida = ImageIO.read(new File("./M3/src/Main/img/bread.png"));
            BufferedImage imghierro = ImageIO.read(new File("./M3/src/Main/img/iron.png"));
            BufferedImage imgmana = ImageIO.read(new File("./M3/src/Main/img/mana.png"));
            BufferedImage imgtechatt = ImageIO.read(new File("./M3/src/Main/img/tec_att.png"));
            BufferedImage imgtechdef = ImageIO.read(new File("./M3/src/Main/img/tec_def.png"));
            
            
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
        
        //boton_upgrade_tech_attack.addActionListener(new ActionListener() {
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
        coste_tech_attack = new JLabel("Food: " + UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST + " Wood: " + UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST + " Iron: " + UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST);
        JButton boton_upgrade_tech_defense = new JButton("Upgrade defense tech");
        coste_tech_defense = new JLabel("Food: " + UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST + " Wood: " + UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST + " Iron: " + UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST);
        
        //button_inicio_start.addActionListener(new ActionListener() {
        boton_upgrade_tech_attack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		        if (civilizacion.getFood() >= calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST) &&
		                civilizacion.getWood() >= calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST) &&
		                civilizacion.getIron() >= calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST)) {
		        	
					civilizacion.setFood(civilizacion.getFood()-calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST));
					civilizacion.setWood(civilizacion.getWood()-calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST));
					civilizacion.setIron(civilizacion.getIron()-calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST));
					civilizacion.setTechnologyAttack(civilizacion.getTechnologyAttack()+1);
					lvl_tech_attack.setText("Level: " + civilizacion.getTechnologyAttack());
					actualizarRecursos();
				}
				
			}
		});
        
        boton_upgrade_tech_defense.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
		        if (civilizacion.getFood() >= calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST) &&
		                civilizacion.getWood() >= calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST) &&
		                civilizacion.getIron() >= calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST)) {
		        	
					civilizacion.setFood(civilizacion.getFood()-calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST));
					civilizacion.setWood(civilizacion.getWood()-calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST));
					civilizacion.setIron(civilizacion.getIron()-calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST));
					civilizacion.setTechnologyDefense(civilizacion.getTechnologyDefense()+1);
					lvl_tech_attack.setText("Level: " + civilizacion.getTechnologyDefense());
					actualizarRecursos();
				}
				
			}
		});
        
        
        //JLabel upgrade_attack = new JLabel("Cost:\nFood: 2000\nWood: 3000\nIron: 4000");
        
        
        
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
        
        JPanel panel_creacion_tropas = new JPanel();
        panel_creacion_tropas.setLayout(new GridLayout(4,1));
        panel_creacion_tropas.setOpaque(false);
        
        // *********************TROPAS DE ATAQUE****************************
        JPanel panel_ofensivas = new JPanel();
        panel_ofensivas.setLayout(new GridLayout(5, 1)); 
        panel_ofensivas.setOpaque(false);
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
					civilizacion.newCannon(cantidad);
					actualizarRecursos();
					//System.out.println(Arrays.deepToString(civilizacion.getArmy()));
				} catch (ResourceException e1) {
		            JOptionPane.showMessageDialog(null, 
		                    "Not enough resources to build a Cannon!\n" + e1, 
		                    "Error", 
		                    JOptionPane.ERROR_MESSAGE);
				}
				
			}
		});
        //boton_upgrade_tech_defense.addActionListener(new ActionListener() {
        
        
        
        
        panel_ofensivas.add(ataque);
        panel_ofensivas.add(button_cannon);
        panel_ofensivas.add(button_crossbow);
        panel_ofensivas.add(button_spearman);
        panel_ofensivas.add(button_swordsman);
        
     // *********************TROPAS DE DEFENSA****************************
        JPanel panel_defensivas = new JPanel();
        panel_defensivas.setLayout(new GridLayout(4, 1)); 
        panel_defensivas.setOpaque(false);
        JLabel defensa = new JLabel("Tropas defensivas");
        defensa.setHorizontalAlignment(JLabel.CENTER);
        defensa.setForeground(Color.WHITE);
        
        JButton button_arrowtower = new JButton("Arrow Tower");
        JButton button_catapult = new JButton("Catapult");
        JButton button_rocket = new JButton("Rocket Launcher Tower");
        
        panel_defensivas.add(defensa);
        panel_defensivas.add(button_arrowtower);
        panel_defensivas.add(button_catapult);
        panel_defensivas.add(button_rocket);

     // *********************TROPAS ESPECIALES****************************
        JPanel panel_especiales = new JPanel();
        panel_especiales.setLayout(new GridLayout(3, 1)); 
        panel_especiales.setOpaque(false);
        JLabel especiales = new JLabel("Tropas especiales");
        especiales.setHorizontalAlignment(JLabel.CENTER);
        especiales.setForeground(Color.WHITE);
        
        JButton button_magician = new JButton("Magician");
        JButton button_priest = new JButton("Priest");
        
        panel_especiales.add(especiales);
        panel_especiales.add(button_magician);
        panel_especiales.add(button_priest);
        
        JPanel crear = new JPanel();
        JLabel cantidad_tropas = new JLabel("Cantidad: ");
        cantidad_textfield = new JTextField("1");
        crear.add(cantidad_tropas);
        crear.add(cantidad_textfield);

        panel_creacion_tropas.add(panel_ofensivas);
        panel_creacion_tropas.add(panel_defensivas);
        panel_creacion_tropas.add(panel_especiales);
        panel_creacion_tropas.add(crear);

        add(panel_creacion_tropas, BorderLayout.WEST);
        
        startTimer();
        
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
        
        coste_tech_attack.setText(
                "Food: " + calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_FOOD_COST) +
                " Wood: " + calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_WOOD_COST) +
                " Iron: " + calcularCosteAtaque(UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_ATTACK_TECHNOLOGY_IRON_COST));
        
        coste_tech_defense.setText(
                "Food: " + calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_FOOD_COST) +
                " Wood: " + calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_WOOD_COST) +
                " Iron: " + calcularCosteDefensa(UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST, UPGRADE_PLUS_DEFENSE_TECHNOLOGY_IRON_COST));
        
        lvl_tech_attack.setText("Att Level: " + civilizacion.getTechnologyAttack());
        lvl_tech_defense.setText("Def Level: " + civilizacion.getTechnologyDefense());
    }
	
}
