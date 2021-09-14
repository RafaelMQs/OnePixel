import java.awt.*;
import java.awt.event.*;
import java.security.Key;

import javax.swing.*;

public class introdução extends JFrame implements KeyListener {
	private JLabel imgFundo0, imgFundo1, guriPretoBrancoAndando,guriPretoBranParado, balaoOpen,balaoFixo,opcaoS, opcaoN;
	private String[] dialogoDoGuri = {}; // diálogo do Guri
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

		// GIF DO FUNDOa
		imgFundo0 = new JLabel(new ImageIcon("res/imgIntro/01-BackgroundWhiteGIF.gif"));
		imgFundo0.setBounds(0, 0, 600, 310);
		imgFundo0.setVisible(false);
		add(imgFundo0);
		
		
		//GIT DO GURI PRETO BRANCO
		guriPretoBrancoAndando = new JLabel(new ImageIcon("res/Guri img/GuriPretoBrancoAndando.gif"));
		guriPretoBrancoAndando.setBounds(-10,160,64,64);
		guriPretoBrancoAndando.setVisible(false);
		add(guriPretoBrancoAndando);
		
		
		guriPretoBranParado = new JLabel(new ImageIcon("res/Guri img/GuriParadoPretoBranco.gif"));
		guriPretoBranParado.setBounds(120,220,64,64);
		guriPretoBranParado.setVisible(false);
		add(guriPretoBranParado);
		
		// BALÂO DE FALA 
		balaoOpen = new JLabel(new ImageIcon("res/balao de fala/balãoOpen.gif"));
		balaoOpen.setBounds(0,0,600,310);
		balaoOpen.setVisible(false);
		add(balaoOpen);
		
		balaoFixo = new JLabel(new ImageIcon("res/balao de fala/BalaoFixo.png"));
		balaoFixo.setBounds(0,0,600,310);
		balaoFixo.setVisible(false);
		add(balaoFixo);

		// TESTANDO
		opcaoS = new JLabel("SIM");
		opcaoS.setBounds(100, 100, 100, 100);
		opcaoS.setVisible(false);
		//add(opcaoS);
		// TESTANDO
		opcaoN = new JLabel("NÃO");
		opcaoN.setBounds(100, 0, 100, 100);
		opcaoN.setVisible(false);
		//add(opcaoN);

		// IMAGEM DE FUNDO
		imgFundo1 = new JLabel(new ImageIcon("res/imgIntro/01-BackgroundWhite.png"));
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
				Thread.sleep(3000);
				
				// FAZENDO O GURI ANDAR
				for (int i=0; i < 120;i++) {
					guriPretoBrancoAndando.setVisible(true);
					guriPretoBrancoAndando.setBounds(i,220,64,64);
					Thread.sleep(20);
				}
				guriPretoBrancoAndando.setVisible(false);
				guriPretoBranParado.setVisible(true);
				Thread.sleep(1000);
				
				
				//Abrindo o balão de fala
				balaoOpen.setVisible(true);
				Thread.sleep(2000);
				balaoOpen.setVisible(false);
				balaoFixo.setVisible(true);
				
//				opcaoS.setVisible(true);
//				opcaoN.setVisible(true);
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
