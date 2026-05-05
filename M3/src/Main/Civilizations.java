package Main;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Civilizations extends JFrame{

	PanelJuego panel_juego;
	public static void main(String[] args) {
		new Civilizations();
	}
	public Civilizations(){
		setBounds(200,200,600,400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("Civilizations");
		panel_juego = new PanelJuego();
		add(panel_juego);
		panel_juego.setBackground(Color.black);
		setVisible(true);
	}

}

class PanelJuego extends JPanel{
	private Fondo fondo;
	
    public PanelJuego() {
        fondo = new Fondo();
        setFocusable(true);
        
    }
	
    protected void paintComponent(Graphics arg0) {
        super.paintComponent(arg0);
        Graphics2D g2d = (Graphics2D) arg0;
        g2d.drawImage(fondo.getImagen().getScaledInstance(getWidth(), getHeight(), Image.SCALE_SMOOTH), 0, 0, this);
        g2d.setColor(new Color(128,64,0));
    }
}

class Fondo{
	private BufferedImage imagen;
	
	public Fondo() {
		try {
            imagen = ImageIO.read(new File("./M3/src/Main/img/fondo_ciudad.png"));
        } catch (IOException e) {
            System.out.println("Problema Cargando Imagen");
        }
	}

	public BufferedImage getImagen() {
		return imagen;
	}
	
}