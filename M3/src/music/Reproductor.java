package music;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.BufferedInputStream;
import java.io.InputStream;

public class Reproductor {

    private Clip clip;

    public void iniciarMusica(String nombreArchivo) {
        try {
        	// 1. Cargar el archivo desde los recursos del proyecto (classpath)
        	InputStream is = getClass().getResourceAsStream("/" + nombreArchivo);
        	if (is == null) {
        	    System.err.println("Error: File not found -> " + nombreArchivo);
        	    return;
        	}

        	// 2. Preparar el flujo de audio con un búfer para soportar marcas de lectura
        	InputStream buffer = new BufferedInputStream(is);
        	AudioInputStream stream = AudioSystem.getAudioInputStream(buffer);

        	// 3. Configurar y reproducir el audio en un bucle infinito
        	clip = AudioSystem.getClip();
        	clip.open(stream);
        	clip.loop(Clip.LOOP_CONTINUOUSLY); // Se repite continuamente
        	clip.start();                      // Inicia la reproducción asíncrona

        	//System.out.println("Audio started successfully: " + nombreArchivo);

        } catch (Exception e) {
            System.err.println("Error: Failed to play audio. Reason: " + e.getMessage());
        }
    }

    public void detenerMusica() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            System.out.println("Audio playback stopped.");
        }
    }
}
