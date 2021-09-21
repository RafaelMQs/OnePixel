package com.kaua.windows.panels;

import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.*;

public class PrologueScreen extends JPanel implements KeyListener {
	private static final long serialVersionUID = 1L;
	
	private JPanel[] panel = new JPanel[11];
	private JLabel[] tela = new JLabel[11];
	private String[] Dialogo = { "<html>Era mais um dia normal no Mundo Virtual.",
			"<html>Pelo menos, era o que Guri achava...",
			"<html>Ele estava discutindo com os seus pais, eles queriam que ele fizesse a faxina da casa.",
			"<html>Guri odiava faxina, Então sem pensar duas vezes ele disse:",
			"<html>“Se eu fosse a única pessoa nesse mundo tudo seria diferente”",
			"<html>Após o último som de sua voz, tudo começou a tremer e o que estava ao seu redor foi consumido pela...",
			"<html>ESCURIDÃO",
			"<html>Guri, ficou 15 horas naquele breu. Ainda afobado na escuridão ele descobriu algo que o fez chorar. Ele percebeu que ele não aproveitou nada em sua vida, pois era apenas um egoísta.",
			"<html>Enquanto Guri estava em total desespero... Ele ouve a seguinte frase:",
			"<html>Você sabe quantas cores há no mundo virtual? Você consegue acendê-las?" };
	private JLabel[] texto = new JLabel[Dialogo.length];
	private String palavra = "";
	private boolean pulouTudo = false;

	public PrologueScreen() {
		init();
		new Temporizador().start();

	}

	public void init() {
		setLayout(null);
		addKeyListener(this);

		for (int i = 0; i < texto.length; i++) {
			texto[i] = new JLabel();
			texto[i].setForeground(Color.WHITE);
			texto[i].setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
			texto[i].setBounds(100, 200, 500, 100);
		}

		for (int i = 0; i < 11; i++) {
			panel[i] = new JPanel(null);
			panel[i].setBackground(Color.BLACK);
			panel[i].setBounds(0, 0, 600, 310);
			panel[i].setVisible(false);
			add(panel[i]);
			tela[i] = new JLabel();
			tela[i].setBounds(0, 0, 600, 310);
		}

		/* Panel 0 | TELA 0 | GIF | Duração de 3 segundos */

		imagem(tela[0], "000", "gif");
		panel[0].add(tela[0]);
		panel[0].setVisible(true);
		add(panel[0]);

		/* Panel 1 | TELA 1 | PNG | Duração ?????? */

		imagem(tela[1], "001", "png");
		panel[1].add(texto[0]);
		panel[1].add(tela[1]);

		/* Panel 2 | TELA 2 | PNG | Duração ?????? */

		imagem(tela[2], "002", "png");
		panel[2].add(texto[1]);
		panel[2].add(tela[2]);

		/* Panel 3 | TELA 3 | PNG | Duração ?????? */

		imagem(tela[3], "003", "png");
		panel[3].add(texto[2]);
		panel[3].add(tela[3]);

		/* Panel 4 | TELA 4 | PNG | Duração ?????? */

		imagem(tela[4], "004", "png");
		panel[4].add(texto[3]);
		panel[4].add(tela[4]);

		/* Panel 5 | TELA 5 | PNG | Duração ?????? */

		imagem(tela[5], "005", "png");
		panel[5].add(texto[4]);
		panel[5].add(tela[5]);

		/* Panel 6 | TELA 6 | PNG | Duração ?????? */

		imagem(tela[6], "006", "png");
		panel[6].add(texto[5]);
		panel[6].add(tela[6]);

		/* Panel 7 | TELA 7 | PNG | Duração ?????? */

		imagem(tela[7], "007", "png");
		panel[7].add(texto[6]);
		panel[7].add(tela[7]);

		/* Panel 8 | TELA 8 | PNG | Duração ?????? */

		imagem(tela[8], "008", "png");
		panel[8].add(texto[7]);
		panel[8].add(tela[8]);

		/* Panel 9 | TELA 9 | PNG | Duração ?????? */

		imagem(tela[9], "009", "png");
		panel[9].add(texto[8]);
		panel[9].add(tela[9]);

		/* Panel 9 | TELA 9 | PNG | Duração ?????? */

		imagem(tela[10], "010", "png");
		panel[10].add(texto[9]);
		panel[10].add(tela[10]);

	}

	public void imagem(JLabel nomeDaJLabel, String nomeDaImagem, String tipoDaImagem) {
		nomeDaJLabel.setIcon(new ImageIcon("res/prologo//" + nomeDaImagem + "." + tipoDaImagem + ""));
	}

	public class Temporizador extends Thread implements KeyListener {
		public void run() {
			try {
				Thread.sleep(4300);
				panel[0].setVisible(false);
				panel[1].setVisible(true);
				for (int z = 0; z < Dialogo[0].length(); z++) {
					TextEffect(Dialogo[0], texto[0], z, 30, false);
				}
				Thread.sleep(1750);
				panel[1].setVisible(false);
				panel[2].setVisible(true);
				palavra = "";
				for (int z = 0; z < Dialogo[1].length(); z++) {
					TextEffect(Dialogo[1], texto[1], z, 30, false);
				}
				Thread.sleep(1800);
				panel[2].setVisible(false);
				panel[3].setVisible(true);
				palavra = "";
				for (int z = 0; z < Dialogo[2].length(); z++) {
					TextEffect(Dialogo[2], texto[2], z, 30, false);
				}
				Thread.sleep(1850);
				panel[3].setVisible(false);
				panel[4].setVisible(true);
				palavra = "";
				for (int z = 0; z < Dialogo[3].length(); z++) {
					TextEffect(Dialogo[3], texto[3], z, 30, false);
				}
				Thread.sleep(1800);
				panel[4].setVisible(false);
				panel[5].setVisible(true);
				texto[4].setBounds(15, 135, 580, 200);
				texto[4].setFont(new Font("Pixel Operator 8", Font.PLAIN, 20));
				palavra = "";
				for (int z = 0; z < Dialogo[4].length(); z++) {
					TextEffect(Dialogo[4], texto[4], z, 35, true);
				}
				Thread.sleep(2050);
				panel[5].setVisible(false);
				panel[6].setVisible(true);
				texto[5].setBounds(15, 50, 580, 200);
				texto[5].setFont(new Font("Pixel Operator 8", Font.PLAIN, 20));
				palavra = "";
				for (int z = 0; z < Dialogo[5].length(); z++) {
					TextEffect(Dialogo[5], texto[5], z, 40, false);
				}
				Thread.sleep(2200);
				panel[6].setVisible(false);
				panel[7].setVisible(true);
				texto[6].setBounds(210, 50, 490, 200);
				texto[6].setFont(new Font("Pixel Operator 8", Font.PLAIN, 25));
				palavra = "";
				for (int z = 0; z < Dialogo[6].length(); z++) {
					TextEffect(Dialogo[6], texto[6], z, 450, true);
				}
				Thread.sleep(2550);
				panel[7].setVisible(false);
				panel[8].setVisible(true);
				texto[7].setBounds(15, 50, 580, 200);
				texto[7].setFont(new Font("Pixel Operator 8", Font.PLAIN, 16));
				palavra = "";
				for (int z = 0; z < Dialogo[7].length(); z++) {
					TextEffect(Dialogo[7], texto[7], z, 30, false);
				}
				Thread.sleep(2400);
				panel[8].setVisible(false);
				panel[9].setVisible(true);
				texto[8].setBounds(15, 50, 580, 200);
				texto[8].setFont(new Font("Pixel Operator 8", Font.PLAIN, 16));
				palavra = "";
				for (int z = 0; z < Dialogo[8].length(); z++) {
					TextEffect(Dialogo[8], texto[8], z, 35, false);
				}
				Thread.sleep(2400);
				panel[9].setVisible(false);
				panel[10].setVisible(true);
				texto[9].setBounds(15, 50, 580, 200);
				texto[9].setFont(new Font("Pixel Operator 8", Font.PLAIN, 16));
				palavra = "";
				for (int z = 0; z < Dialogo[9].length(); z++) {
					TextEffect(Dialogo[9], texto[9], z, 60, false);
				}

				Thread.sleep(1000);
				if (!pulouTudo) {
					setVisible(false);
					new Introduction().setVisible(true);
					System.out.println("CHAMANDO A CORNA");
				}
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {

		}

		@Override
		public void keyReleased(KeyEvent e) {

		}
	}

	public static void main(String[] args) {
		new PrologueScreen().setVisible(true);
	}

	public void TextEffect(String DialogoBox, JLabel lbDialogo, int z, int milesimos, boolean Tremer) {
		try {
			if (!Tremer) {
				char letra = DialogoBox.charAt(z);
				palavra = palavra + letra;
				if (z >= 6) {
					lbDialogo.setVisible(true);
				} else {
					lbDialogo.setVisible(false);
				}
				lbDialogo.setText(palavra);
				Thread.sleep(milesimos);
			} else {
				int originalX = getLocation().x;
				int originalY = getLocation().y;

				for (int i = 0; i <= 2; i++) {
					setLocation(originalX + 5, originalY);
					setLocation(originalX + 5, originalY + 5);
					setLocation(originalX, originalY + 5);
					setLocation(originalX, originalY);
					setLocation(originalX - 5, originalY);
					setLocation(originalX - 5, originalY - 5);
					setLocation(originalX, originalY - 5);
				}
				setLocation(originalX, originalY);

				char letra = DialogoBox.charAt(z);
				palavra = palavra + letra;
				if (z >= 6) {
					lbDialogo.setVisible(true);
				} else {
					lbDialogo.setVisible(false);
				}
				lbDialogo.setText(palavra);
				Thread.sleep(milesimos);
			}

		} catch (InterruptedException ex) {
			System.out.println("Errou");
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			pulouTudo = true;
			setVisible(false);
			new Introduction().setVisible(true);
		} else if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
}

