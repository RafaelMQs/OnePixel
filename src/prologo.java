import java.awt.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.*;

/* Warning!!! Cada imagem estar em um JPanel
 * add(NomeDoJPanel) */

public class prologo extends JFrame {
	
	private JPanel[] panel = new JPanel[11];
	private JLabel[] tela = new JLabel[11];
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
		
		
		for (int i = 0; i < 11;i++) {
			panel[i] = new JPanel();
			panel[i].setBackground(Color.BLACK);
			panel[i].setBounds(0,0,600,310);
			
			tela[i] = new JLabel();
			tela[i].setBounds(0,0,600,310);
		}
		
		/*Panel 0 | TELA 0 | GIF | Duração de 3 segundos  */
		
        imagem(tela[0], "000", "gif");
        panel[0].add(tela[0]);
        add(panel[0]);


		/*Panel 1 | TELA 1 | PNG | Duração ??????  */
        
        imagem(tela[1], "001", "png");
        panel[1].add(tela[1]);

     	/*Panel 2 | TELA 2 | PNG | Duração ??????  */
        
        imagem(tela[2], "002", "png");
        panel[2].add(tela[2]);
        

		/*Panel 3 | TELA 3 | PNG | Duração ??????  */
		
        imagem(tela[3], "003", "png");
        panel[3].add(tela[3]);
		
		/*Panel 4 | TELA 4 | PNG | Duração ??????  */
		
        imagem(tela[4], "004", "png");
        panel[4].add(tela[4]);
		
		/*Panel 5 | TELA 5 | PNG | Duração ??????  */
		
        imagem(tela[5], "005", "png");
        panel[5].add(tela[5]);
        
		/*Panel 6 | TELA 6 | PNG | Duração ??????  */
		
        imagem(tela[6], "006", "png");
        panel[6].add(tela[6]);
        
		/*Panel 7 | TELA 7 | PNG | Duração ??????  */
		
        imagem(tela[7], "007", "png");
        panel[7].add(tela[7]);
		
		/*Panel 8 | TELA 8 | PNG | Duração ??????  */
		
        imagem(tela[8], "008", "png");
        panel[8].add(tela[8]);
		
		/*Panel 8 | TELA 8 | PNG | Duração ??????  */
		
        imagem(tela[8], "008", "png");
        panel[8].add(tela[8]);
        
		/*Panel 9 | TELA 9 | PNG | Duração ??????  */
		
        imagem(tela[9], "009", "png");
        panel[9].add(tela[9]);
        
		/*Panel 9 | TELA 9 | PNG | Duração ??????  */
		
        imagem(tela[10], "010", "png");
        panel[10].add(tela[10]);		
	}
	
	public void imagem(JLabel nomeDaJLabel,String nomeDaImagem,String tipoDaImagem) {
		nomeDaJLabel.setIcon(new ImageIcon("prologo//"+nomeDaImagem+"."+tipoDaImagem+""));
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
        				panel[0].setVisible(false);
        				add(panel[1]);
        				panel[1].setVisible(true);
        			}
                } catch (InterruptedException e) {
                    e.printStackTrace();
                
                }
            }
        }
	}
}
