import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.*;

/* Warning!!! Cada imagem estar em um JPanel
 * add(NomeDoJPanel) */

public class prologo extends JFrame {
	
	private JPanel panel0,panel1,panel2;
	private JLabel tela0,tela1,tela2;
	private int contTela0 = 0;
	
	public prologo() {
		// TODO Auto-generated constructor stub
		inicializarComponentes();
		new Temporizador().start();
	}
	
	public void inicializarComponentes() {
		setLayout(null);
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 310); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		
		/*Panel 0 | TELA 0 | GIF | Duração de 3 segundos  */
		
		panel0 = new JPanel();
		panel0.setBackground(Color.BLACK);
		panel0.setBounds(0,0,600,310);
		add(panel0);

		
		tela0 = new JLabel(new ImageIcon("prologo//000.gif"));
		tela0.setBounds(0,0,600,310);
		panel0.add(tela0);

		
		
		/*Panel 1 | TELA 1 | PNG | Duração ??????  */
	
		panel1 = new JPanel();
		panel1.setBackground(Color.BLACK);
		panel1.setBounds(0,0,600,310);
		
		tela1 = new JLabel(new ImageIcon("prologo//001.png"));
		tela1.setBounds(0,0,600,310);
		panel1.add(tela1);
		
		/*Panel 2 | TELA 2 | PNG | Duração ??????  */
		
		panel2 = new JPanel();
		panel2.setBackground(Color.BLACK);
		panel2.setBounds(0,0,600,310);
		
		tela2 = new JLabel(new ImageIcon("prologo//001.png"));
		tela2.setBounds(0,0,600,310);
		panel2.add(tela2);
		
		
		
	}
	
	
	public static void main(String[] args) {
		new prologo().setVisible(true);
	}
	
	public class Temporizador extends Thread{
        public void run(){
            while( contTela0 <100){
                try {
                	sleep(50);
                	contTela0 +=1;
                    // Quando chegar 100%, esta função chamarar outra tela.	
        			if(contTela0 >= 81) {
        				panel0.setVisible(false);
        				add(panel1);
        				panel1.setVisible(true);
        			}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                
                }
            }
        }
	}
}
