import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class pósprologo extends JFrame{

	public pósprologo() {
		new Iniciar().start();
		Componentes();
		Eventos();
		Frame();
	}
	
	public void Componentes() {
		setLayout(null);
	}
	
	public void Eventos() {
		
	}
	
	public void Frame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 310); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		setVisible(true);
	}
	
	public static void main (String args[]) {
		new pósprologo();
	}
	
	public class Iniciar extends Thread{
		public void run() {
			for(int i = 0; i < 255; i++) {
				try {
					getContentPane().setBackground(new Color(i, i, i));
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
