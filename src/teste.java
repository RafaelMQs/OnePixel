import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class teste extends JFrame implements KeyListener {
	
	String Dialogo1[] = { "Oi Testando", "Teste 2", "Michel Legal"};
	JLabel Dialogo, img;
	boolean resposta = true;
	File file1 = new File("guriFalando1.wav");
	File file2 = new File("guriFalando1.wav");
	File file3 = new File("guriFalando1.wav");
	Clip clip;
	
	String palavra = "";

	public teste() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		Frame();
		Componentes();
		Eventos();
	}

	public void Componentes() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		addKeyListener(this);
		Dialogo = new JLabel();
		Dialogo.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		add(Dialogo);
		


		for (int i = 0; i < Dialogo1.length; i++){
			palavra = "";
			for (int z = 0; z < Dialogo1[i].length(); z++) {
				if (resposta) {
					TextEffect(Dialogo1, Dialogo, i, z);
				}else {
					z = Dialogo1[i].length();
					resposta = true;
				}
			}
		}
	}

	public void Eventos() {

	}

	public void Frame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 310); // tamanho do JFrame
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null); // tela fica centralizado

	}

	public static void main(String args[]) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		new teste();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			resposta = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void TextEffect (String DialogoBox[], JLabel lbDialogo, int i, int z) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		try {
			char letra = DialogoBox[i].charAt(z);
			palavra = palavra + letra;
			lbDialogo.setText(palavra);
			Thread.sleep(200);
			TextEffectAudio();
		} catch (InterruptedException ex) {
			System.out.println("Errou");
		}
	}
	
	public void TextEffectAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int random = (int) (Math.random() *3);
		switch (random) {
		case 0: audio("guriFalando", 20, 0, 0);
		case 1: audio("guriFalando1", 20, 0, 0);
		case 2:  audio("guriFalando2", 20, 0, 0);
		}
	}
	
	public void audio(String nome, float volume, int loop, int action)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		File file = new File(nome + ".wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);

		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-volume); // Reduce volume by 10 decibels.
		clip.loop(loop);
		if (action == 0) {
			clip.start();
		} else {
			clip.stop();
		}
		
	}
}

