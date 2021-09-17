package OnePixel1;


import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;


public class teladeCarregamento extends JFrame {
	
	private JLabel fundo,person;
	private int load = 0;
	private ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	
	public teladeCarregamento() {
		initComponentes();
		new Temporizador().start();
	}
	
	public void initComponentes() {
		setIconImage(imgLogo.getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0,0,677,260); // tamanho do JFrame
		setUndecorated(true);
		setLocationRelativeTo(null); // tela fica centralizado
		
		person = new JLabel(new ImageIcon("res/imgGuri/Guri01ParadoColorido.gif"));
		person.setBounds(305,165,64,64);
		add(person);
		
		fundo = new JLabel(new ImageIcon("res/LoadingBackgroundv2.png"));
		fundo.setBounds(0,0,677,260);
		add(fundo);
		
	}

	public static void main(String[] args) {
		new teladeCarregamento().setVisible(true);
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
        				new telaInicial().setVisible(true);
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