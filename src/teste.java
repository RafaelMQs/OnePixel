import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

public class teste extends JFrame {
	JButton a;

	public teste() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 677, 260); // tamanho do JFrame
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null); // tela fica centralizado

		File file = new File("Selected Sound.wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);
		
		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-20.0f); // Reduce volume by 10 decibels.
		clip.start();
	}

	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new teste();

	}
}
