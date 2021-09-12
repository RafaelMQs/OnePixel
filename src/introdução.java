import java.awt.*;
import java.awt.event.*;
import java.security.Key;

import javax.swing.*;

public class introdução extends JFrame implements KeyListener {
	private JLabel imgFundo0, imgFundo1, opcaoS, opcaoN;
	private int opcao = 0;
	
	public introdução() {
		new Iniciar().start();
		Componentes();
		Eventos();
		Frame();
	}

	public void Componentes() {
		setLayout(null);
		addKeyListener(this);

		// GIF DO FUNDO
		imgFundo0 = new JLabel(new ImageIcon("imgIntro/01-BackgroundWhiteGIF.gif"));
		imgFundo0.setBounds(0, 0, 600, 310);
		imgFundo0.setVisible(false);
		add(imgFundo0);

		// TESTANDO
		opcaoS = new JLabel("SIM");
		opcaoS.setBounds(100, 100, 100, 100);
		opcaoS.setVisible(false);
		add(opcaoS);
		// TESTANDO
		opcaoN = new JLabel("NÃO");
		opcaoN.setBounds(100, 0, 100, 100);
		opcaoN.setVisible(false);
		add(opcaoN);

		// IMAGEM DE FUNDO
		imgFundo1 = new JLabel(new ImageIcon("imgIntro/01-BackgroundWhite.png"));
		imgFundo1.setBounds(0, 0, 600, 310);
		imgFundo1.setVisible(false);
		add(imgFundo1);

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

	public static void main(String args[]) {
		new introdução();
	}

	public class Iniciar extends Thread {
		public void run() {
			try {
				for (int i = 0; i < 255; i++) {
					getContentPane().setBackground(new Color(i, i, i));
					Thread.sleep(8);
				}
				Thread.sleep(180);
				imgFundo0.setVisible(true);
				Thread.sleep(2000);
				imgFundo0.setVisible(false);
				imgFundo1.setVisible(true);
				opcaoS.setVisible(true);
				opcaoN.setVisible(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// ESQUERDA
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			opcao = 1;
		}
		// DIREITA
		else if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
			opcao = 0;
		}
		
		// MUDA O TEXTO CONFORME A OPÇÃO SELECIONADA
		if(opcao == 1) {
			opcaoS.setText(">SIM");
			opcaoN.setText("NÃO");
		}else {
			opcaoN.setText(">NÃO");
			opcaoS.setText("SIM");
		}
		
		//
		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
			if(opcao == 1) {
				System.out.println("SIM");
			}else {
				System.out.println("NAO");
			}
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}
