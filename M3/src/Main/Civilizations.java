package Main;

import civilizations.*;
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

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
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
	
	
    public PanelJuego() {
    	setLayout(new BorderLayout());
    	civilizacion = new Civilization(3000,3000,3000,3000);
        try {
            fondo_juego = ImageIO.read(new File("./M3/src/Main/img/fondo_ciudad.png"));
            BufferedImage imgmadera = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            BufferedImage imgcomida = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            BufferedImage imghierro = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            BufferedImage imgmana = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            BufferedImage imgtechatt = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            BufferedImage imgtechdef = ImageIO.read(new File("./M3/src/Main/img/wood.png"));
            
            
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
        panel_tech_upgrades.setLayout(new GridLayout(2,1));
        
        
        JButton boton_upgrade_tech_attack = new JButton("Upgrade attack tech");
        JButton boton_upgrade_tech_defense = new JButton("Upgrade defense tech");
        
        //button_inicio_start.addActionListener(new ActionListener() {
        boton_upgrade_tech_attack.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (civilizacion.getFood() >= UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST && civilizacion.getWood() >= UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST && civilizacion.getIron() >= UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST) {
					civilizacion.setTechnologyAttack(civilizacion.getTechnologyAttack()+1);
					lvl_tech_attack.setText("Level: " + civilizacion.getTechnologyAttack());
					civilizacion.setFood(civilizacion.getFood()-UPGRADE_BASE_ATTACK_TECHNOLOGY_FOOD_COST);
					civilizacion.setWood(civilizacion.getWood()-UPGRADE_BASE_ATTACK_TECHNOLOGY_WOOD_COST);
					civilizacion.setIron(civilizacion.getIron()-UPGRADE_BASE_ATTACK_TECHNOLOGY_IRON_COST);
					actualizarRecursos();
				}
				
			}
		});
        
        boton_upgrade_tech_defense.addActionListener(new ActionListener() {
			
			public void actionPerformed(ActionEvent e) {
				if (civilizacion.getFood() >= UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST && civilizacion.getWood() >= UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST && civilizacion.getIron() >= UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST) {
					civilizacion.setTechnologyDefense(civilizacion.getTechnologyDefense()+1);
					lvl_tech_attack.setText("Level: " + civilizacion.getTechnologyDefense());
					civilizacion.setFood(civilizacion.getFood()-UPGRADE_BASE_DEFENSE_TECHNOLOGY_FOOD_COST);
					civilizacion.setWood(civilizacion.getWood()-UPGRADE_BASE_DEFENSE_TECHNOLOGY_WOOD_COST);
					civilizacion.setIron(civilizacion.getIron()-UPGRADE_BASE_DEFENSE_TECHNOLOGY_IRON_COST);
					actualizarRecursos();
				}
				
			}
		});
        
        
        //JLabel upgrade_attack = new JLabel("Cost:\nFood: 2000\nWood: 3000\nIron: 4000");
        
        
        
        panel_tech_upgrades.add(boton_upgrade_tech_attack);
        panel_tech_upgrades.add(boton_upgrade_tech_defense);
        
        panel_tech.add(panel_tech_upgrades, BorderLayout.NORTH);
        
        JPanel panel_invisible = new JPanel();
        panel_invisible.setLayout(new GridLayout(1,1));
        panel_invisible.setOpaque(false);
        
        panel_tech.add(panel_invisible, BorderLayout.CENTER);
        
        JPanel panel_tech_info = new JPanel();
        panel_tech_info.setLayout(new GridLayout(4,1));
        
        JLabel label_food_gen = new JLabel("You make 2000 food/min");
        JLabel label_wood_gen = new JLabel("You make 3000 wood/min");
        JLabel label_iron_gen = new JLabel("You make 4000 iron/min");
        JLabel label_mana_gen = new JLabel("You make 5000 mana/min");
        
        panel_tech_info.add(label_food_gen);
        panel_tech_info.add(label_wood_gen);
        panel_tech_info.add(label_iron_gen);
        panel_tech_info.add(label_mana_gen);
        
        panel_tech_info.setBackground(Color.ORANGE);
        
        panel_tech.add(panel_tech_info, BorderLayout.SOUTH);
        
        add(panel_tech,BorderLayout.EAST);
        
        setFocusable(true);
        
    }
    
    protected void paintComponent(Graphics g2d) {
        super.paintComponent(g2d);
        g2d.drawImage(fondo_juego.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        
        
    }
    
    public void actualizarRecursos() {
        comida.setText("Food: " + civilizacion.getFood());
        madera.setText("Wood: " + civilizacion.getWood());
        hierro.setText("Iron: " + civilizacion.getIron());
        mana.setText("Mana: " + civilizacion.getMana());
        lvl_tech_attack.setText("Att Level: " + civilizacion.getTechnologyAttack());
        lvl_tech_defense.setText("Def Level: " + civilizacion.getTechnologyDefense());
    }
	
}
