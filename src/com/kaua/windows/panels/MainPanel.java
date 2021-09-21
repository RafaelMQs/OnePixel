package com.kaua.windows.panels;

import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

	private JLabel fundo, person;
	private int load = 0;
	
	public MainPanel() {
		init();
		new Temporizador().start();
	}

	public void init() {
		setLayout(null);
		
		person = new JLabel(new ImageIcon("res//Guri01.gif"));
		person.setBounds(305, 165, 64, 64);
		add(person);

		fundo = new JLabel(new ImageIcon("res//LoadingBackgroundv2.png"));
		fundo.setBounds(0, 0, 677, 260);
		add(fundo);
	}
	
	public class Temporizador extends Thread{
        public void run(){
            while( load <100){
                try {
                	sleep(50);
                    load +=1;
                    System.out.println(load);
                    // Quando chegar 100%, esta função chamarar outra tela.	
        			if(load >= 100) {
        				setVisible(false);
        				new InicialScreen().setVisible(true);
        			}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
        }
    }

}
