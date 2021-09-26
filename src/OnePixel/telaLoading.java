package OnePixel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class telaLoading extends JFrame {
	private JLabel fundo, person;
	private int load = 0;
	private ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	public telaLoading() {
		initComponentes();
		new Temporizador().start();
	}

	public void initComponentes() {
		setIconImage(imgLogo.getImage());
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 310); // tamanho do JFrame
		setUndecorated(true);
		setLocationRelativeTo(null); // tela fica centralizado

		fundo = new JLabel(new ImageIcon("res2/imgTelaLoading/logoSquare.gif"));
		fundo.setBounds(0, 0, 600, 310);
		add(fundo);

	}

	public static void main(String[] args) {
		new telaLoading().setVisible(true);
	}

	public class Temporizador extends Thread {
		public void run() {
			while (load < 100) {
				try {
					sleep(100);
					load += 1;
					System.out.print("");
					// Quando chegar 100%, esta função chamarar outra tela.
					if (load >= 100) {
						setVisible(false);
						new telaInicial().setVisible(true);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}
}