package Main;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
class PanelJuego extends JPanel{
	private BufferedImage fondo_juego;
	
    public PanelJuego() {
    	setLayout(new BorderLayout());
    	
        try {
            fondo_juego = ImageIO.read(new File("./M3/src/Main/img/fondo_ciudad.png"));
        } catch (IOException e) {
            System.out.println("No se pudo cargar la imagen: " + e.getMessage());
        }
    	
        setFocusable(true);
        
    }
    
    protected void paintComponent(Graphics g2d) {
        super.paintComponent(g2d);
        g2d.drawImage(fondo_juego.getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
    }
	
}
