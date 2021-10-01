package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

public class labirintoPuzzleRed extends JFrame implements ActionListener {
	private Jogador jogador;
	private Timer timer;
	JPanel panel;

	boolean morreu = false;
	String palavra = ""; 
	
	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	// X, Y, LARGURA, ALTURA DE TODOS OS COMPONENTES
	int xFonte = 245, yFonte = 65, alturaFonte = 111, larguraFonte = 108, xLava = 100, yLava = 100, larguraLava = 84, alturaLava = 84,
			xLava2 = 100, yLava2 = 100, larguraLava2 = 84, alturaLava2 = 84;

	// JLABELs DOS COMPONENTES DA TELA
	JLabel lbFonte;

	ImageIcon imgFundo;
	JLabel lbFundo;

	// IMG E LABELs DE COLISAO
	ImageIcon imgLava;
	JLabel lbLava, lbLava2;

	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;
	
	String textoMorte = "<html> <center>Você morreu!</center><br> Para recomeçar <center>aperte R</center> </html>";
	JLabel dialogoMorte;

	// LOCAL AONDE ELE ESTA
	int localTerreno = 0;

	public labirintoPuzzleRed() {
		setLayout(null);
		setFocusable(true);

		panel = new JPanel(null) {
			public void paint(Graphics g) {
				super.paint(g);
				Graphics desenha = (Graphics2D) g;
				desenha.drawImage(jogador.getImgPixelRed(), jogador.getxR(), jogador.getyR(), 32, 32, this);
				desenha.drawImage(jogador.getImgPixelGreen(), jogador.getxG(), jogador.getyG(), 32, 32, this);
				desenha.drawImage(jogador.getImgPixelBlue(), jogador.getxB(), jogador.getyB(), 32, 32, this);
				desenha.drawImage(jogador.getImgPlayer(), jogador.getX(), jogador.getY(), jogador.getLargura(),
						jogador.getAltura(), this);

			}
		};
		panel.setBounds(0, 0, 600, 310);

		jogador = new Jogador();
		jogador.carregar();
		jogador.setX(270);
		jogador.setY(260);
		movimentacaoPet();
		
		
		dialogoMorte = new JLabel();
		dialogoMorte.setBounds(100, 38, 100, 60);
		dialogoMorte.setFont(new Font("Pixel Operator 8", Font.PLAIN, 8));
		dialogoMorte.setForeground(Color.WHITE);
		dialogoMorte.setVisible(false);
		panel.add(dialogoMorte);
		
		
		// IMAGENs DO BALAO DE DIALOGO
		imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
		lbBalaoDialog = new JLabel(imgBalaoDialog);
		lbBalaoDialog.setBounds(50, 70, 130, 60);
		lbBalaoDialog.setVisible(false);
		panel.add(lbBalaoDialog);

		ImageIcon fonte = new ImageIcon("res2/imgLabirintoPuzzleRed/Fonte.png");
		lbFonte = new JLabel(fonte);
		lbFonte.setBounds(xFonte, yFonte, larguraFonte, alturaFonte);
		panel.add(lbFonte);

		imgLava = new ImageIcon("res2/imgLabirintoPuzzleRed/ChaoLava01.gif");
		lbLava = new JLabel(imgLava);
		lbLava.setBounds(xLava, yLava, larguraLava, alturaLava);
		lbLava.setVisible(false);
		panel.add(lbLava);
		
		imgLava = new ImageIcon("res2/imgLabirintoPuzzleRed/ChaoLava01.gif");
		lbLava2 = new JLabel(imgLava);
		lbLava2.setBounds(xLava2, yLava2, larguraLava2, alturaLava2);
		lbLava2.setVisible(false);
		panel.add(lbLava2);

		// IMG FUNDO
		imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/001.png");
		lbFundo = new JLabel(imgFundo);
		lbFundo.setBounds(0, 0, 600, 310);
		panel.add(lbFundo);

		ImageIcon lava1 = new ImageIcon("res2/imgLabirintoPuzzleRed/ChaoLava01.gif");
//		lava = lava1.getImage();

		addKeyListener(new Teclado());

		timer = new Timer(6, this);
		timer.start();

		add(panel);

		setIconImage(imgLogo.getImage());
		setTitle("OnePixel - Part2");
		setSize(larguraFrame, alturaFrame);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// SALA 1
		if (localTerreno == 0) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/001.png");
			lbFundo.setIcon(imgFundo);
			lbFonte.setVisible(true);
			colisao(xFonte, yFonte, larguraFonte, alturaFonte);
			colisao(0, 0, 193, 24);
			colisao(407, 0, 193, 24);
			colisao(0, 195, 193, 110);
			colisao(407, 195, 193, 110);



			// VAI SALA 2 - ESQUERDA
			if (jogador.getX() <= -15 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				localTerreno = 1;
				lbFonte.setVisible(false);
				jogador.setX(550);

				// VAI SALA 6 - DIREITA
			} else if (jogador.getX() >= 575 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				localTerreno = 4;
				lbFonte.setVisible(false);
				jogador.setX(10);

				// VAI SALA 3 - CIMA
			} else if (jogador.getY() <= -20 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				localTerreno = 5;
				lbFonte.setVisible(false);
				jogador.setY(255);

				// VOLTA SALA PRINCIPAL - BAIXO
			} else if (jogador.getY() >= 280 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				jogo2SalaPrinc salaPrincipal = new jogo2SalaPrinc();
				salaPrincipal.salaPrinc = false;
				salaPrincipal.salaPorta1 = true;
				timer.stop();
				setVisible(false);

			}

		}

		// SALA 2
		if (localTerreno == 1) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/002.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 193, 24);
			colisao(407, 0, 193, 24);
			colisao(0, 195, 600, 110);

			// VOLTA SALA 1
			if (jogador.getX() >= 575 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				localTerreno = 0;
				lbFonte.setVisible(true);
				jogador.setX(10);

				// VAI SALA 3 - CIMA
			} else if (jogador.getY() <= -20 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				localTerreno = 2;
				jogador.setY(255);

				// VAI SALA 4 - ESQUERDA
			} else if (jogador.getX() <= -15 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				localTerreno = 3;
				jogador.setX(550);
			}
		}

		// SALA 3
		if (localTerreno == 2) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/003.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 600, 40);
			colisao(0, 0, 50, 310);
			colisao(550, 0, 50, 310);
			colisao(0, 226, 193, 90);
			colisao(407, 226, 193, 90);

			// VOLTA SALA 2 - BAIXO
			if (jogador.getY() >= 280 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				localTerreno = 1;
				lbFonte.setVisible(false);
				jogador.setY(0);

			}
		}

		// SALA 4
		if (localTerreno == 3) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/004.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 600, 24);
			colisao(0, 24, 425, 86);
			colisao(0, 0, 62, 310);
			colisao(0, 195, 600, 110);
			
			xLava = 70; yLava = 115;
			lbLava.setBounds(xLava, yLava, larguraLava, alturaLava);
			lbLava.setVisible(true);
			
			xLava2 = 450; yLava2 = 55;
			lbLava2.setBounds(xLava2, yLava2, larguraLava2, alturaLava2);
			lbLava2.setVisible(true);
			
			if (checkColisao(xLava, yLava, larguraLava, alturaLava) != null) {
				jogador.setAndar(false);
				if(jogador.isCorrendo()) {
					jogador.setX(xLava + 25);
					jogador.setY(yLava + 25);					
				}
				jogador.setCaminhoImg("res/imgGuri/Guri05.gif");
				jogador.carregar();
				if(morreu == false) {
					new balaoDialogFadeOut().start();
					new dialogoDeMorte().start();
					morreu = true;
				}
			}
			if (checkColisao(xLava2, yLava2, larguraLava2, alturaLava2) != null) {
				jogador.setAndar(false);
				if(jogador.isCorrendo()) {
					jogador.setX(xLava2 + 25);
					jogador.setY(yLava2 + 25);					
				}
				jogador.setCaminhoImg("res/imgGuri/Guri05.gif");
				jogador.carregar();
				if(morreu == false) {
					new balaoDialogFadeOut().start();
					new dialogoDeMorte().start();
					morreu = true;
				}
			}

			// VOLTA SALA 2 - DIREITA
			if (jogador.getX() >= 575 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				localTerreno = 1;
				jogador.setX(10);
				lbLava.setVisible(false);
				lbLava2.setVisible(false);
			}
		}

		// SALA 5
		if (localTerreno == 4) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/005.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 600, 24);
			colisao(175, 24, 425, 86);
			colisao(538, 0, 62, 310);
			colisao(0, 197, 600, 110);

			// VOLTA SALA 1 - ESQUERDA
			if (jogador.getX() <= -15 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				localTerreno = 0;
				jogador.setX(550);
			}
		}

		// SALA 6
		if (localTerreno == 5) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/006.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 193, 310);
			colisao(406, 0, 194, 76);
			colisao(406, 238, 194, 76);

			// VOLTA SALA 1 - BAIXO
			if (jogador.getY() >= 280 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				localTerreno = 0;
				jogador.setY(0);

				// VAI SALA 7 - CIMA
			} else if (jogador.getY() <= -20 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				localTerreno = 6;
				jogador.setY(255);
				
				// VAI SALA BOSS
			} else if (jogador.getX() >= 575 && jogador.getY() >= -10 && jogador.getY() <= 165) {
				new bossMorcego();
				timer.stop();
				setVisible(false);
			}

		}

		// SALA 7
		if (localTerreno == 6) {
			imgFundo = new ImageIcon("res2/imgLabirintoPuzzleRed/007.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 134, 310);
			colisao(465, 0, 135, 310);
			colisao(0, 0, 600, 76);

			colisao(0, 0, 193, 110);
			colisao(407, 0, 193, 110);
			colisao(0, 227, 193, 85);
			colisao(407, 227, 193, 85);

			// VOLTA SALA 6 - BAIXO
			if (jogador.getY() >= 280 && jogador.getX() >= 165 && jogador.getX() <= 385) {
				localTerreno = 5;
				jogador.setY(0);
			}
		}

		movimentacaoPet();
		jogador.atualizar();
		repaint();
		

	}

	public void colisao(int xB, int yB, int larguraB, int alturaB) {

		int aX = jogador.getX();
		int aY = jogador.getY();
		int ladoDireitoA = aX + jogador.getLargura() - 12;
		int ladoEsquerdoA = aX + 12;
		int ladoBaixoA = aY + jogador.getAltura() - 5;
		int ladoCimaA = aY + 35;

		int bX = xB;
		int bY = yB;
		int ladoDireitoB = bX + larguraB;
		int ladoEsquerdoB = bX;
		int ladoBaixoB = bY + alturaB;
		int ladoCimaB = bY;

		if (ladoDireitoA >= ladoEsquerdoB && ladoDireitoA < ladoEsquerdoB + 2 && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setX(jogador.getX() - 2);
		} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setX(jogador.getX() + 2);
		} else if (ladoDireitoA >= ladoEsquerdoB && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoBaixoA <= ladoBaixoB) {
			jogador.setY(jogador.getY() - 2);
		} else if (ladoEsquerdoA <= ladoDireitoB && ladoDireitoA >= ladoEsquerdoB && ladoCimaA >= ladoBaixoB - 2
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setY(jogador.getY() + 2);
		}

		if (jogador.isCorrendo()) {
			if (ladoDireitoA >= ladoEsquerdoB - 2 && ladoDireitoA < ladoEsquerdoB + 2 && ladoBaixoA >= ladoCimaB
					&& ladoCimaA <= ladoBaixoB) {
				jogador.setX(jogador.getX() - 2);
			} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB + 2 && ladoBaixoA >= ladoCimaB
					&& ladoCimaA <= ladoBaixoB) {
				jogador.setX(jogador.getX() + 2);
			} else if (ladoDireitoA >= ladoEsquerdoB && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB - 2
					&& ladoBaixoA <= ladoBaixoB + 2) {
				jogador.setY(jogador.getY() - 2);
			} else if (ladoEsquerdoA <= ladoDireitoB && ladoDireitoA >= ladoEsquerdoB && ladoCimaA >= ladoBaixoB - 2
					&& ladoCimaA <= ladoBaixoB + 2) {
				jogador.setY(jogador.getY() + 4);
			}
		}

	}

	public String checkColisao(int xB, int yB, int larguraB, int alturaB) {
		int aX = jogador.getX();
		int aY = jogador.getY();
		int ladoDireitoA = aX + jogador.getLargura() - 12;
		int ladoEsquerdoA = aX + 12;
		int ladoBaixoA = aY + jogador.getAltura() - 2;
		int ladoCimaA = aY + 35;

		int bX = xB;
		int bY = yB;
		int ladoDireitoB = bX + larguraB;
		int ladoEsquerdoB = bX;
		int ladoBaixoB = bY + alturaB;
		int ladoCimaB = bY;

// COLISAO COM O ITEM B
		// COLISAO LADO DIREITO DO GURI
		if (ladoDireitoA >= ladoEsquerdoB && ladoDireitoA < ladoEsquerdoB + 2 && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			return "esquerda";

			// COLISAO LADO ESQUERDO DO GURI
		} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			return "direita";

			// COLISAO LADO DE BAIXO DO GURI
		} else if (ladoDireitoA >= ladoEsquerdoB && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoBaixoA <= ladoBaixoB - 70) {
			return "cima";

			// COLISAO LADO DE CIMA DO GURI
		} else if (ladoEsquerdoA <= ladoDireitoB && ladoDireitoA >= ladoEsquerdoB && ladoCimaA >= ladoBaixoB - 4
				&& ladoCimaA <= ladoBaixoB) {
			return "baixo";

			// COLISAO DO GURI DENTRO DO OBJETO
		} else if (ladoDireitoA >= ladoEsquerdoB + 10 && ladoEsquerdoA <= ladoDireitoB - 10 && ladoBaixoA >= ladoCimaB
				&& ladoBaixoA <= ladoBaixoB) {
			return "dentro";

		}
		return null;
	}

	public void TextEffect(String DialogoBox, JLabel lbDialogo, int z, int milesimos) {
		try {
			char letra = DialogoBox.charAt(z);
			palavra = palavra + letra;
			if (z >= 6) {
				lbDialogo.setVisible(true);
			} else {
				lbDialogo.setVisible(false);
			}
			lbDialogo.setText(palavra);
			Thread.sleep(milesimos);
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}

	public void movimentacaoPet() {
		if (jogador.isCima()) {
			// PIXEL VERMELHO
			jogador.setyR(jogador.getY() + 25);
			jogador.setxR(jogador.getX() - 15);

			// PIXEL VERDE
			jogador.setyG(jogador.getY() + 30);
			jogador.setxG(jogador.getX() + 6);

			// PIXEL AZUL
			jogador.setyB(jogador.getY() + 25);
			jogador.setxB(jogador.getX() + 28);

		} else if (jogador.isBaixo()) {
			// PIXEL VERMELHO
			jogador.setyR(jogador.getY() - 20);
			jogador.setxR(jogador.getX() + 30);

			// PIXEL VERDE
			jogador.setyG(jogador.getY() - 25);
			jogador.setxG(jogador.getX() + 8);

			// PIXEL AZUL
			jogador.setyB(jogador.getY() - 20);
			jogador.setxB(jogador.getX() - 15);

		} else if (jogador.isDireita()) {
			// PIXEL VERMELHO
			jogador.setxR(jogador.getX() - 18);
			jogador.setyR(jogador.getY() - 10);

			// PIXEL VERDE
			jogador.setxG(jogador.getX() - 25);
			jogador.setyG(jogador.getY() + 5);

			// PIXEL AZUL
			jogador.setxB(jogador.getX() - 18);
			jogador.setyB(jogador.getY() + 20);

		} else if (jogador.isEsquerda()) {
			// PIXEL VERMELHO
			jogador.setxR(jogador.getX() + 30);
			jogador.setyR(jogador.getY() + 20);

			// PIXEL VERDE
			jogador.setxG(jogador.getX() + 38);
			jogador.setyG(jogador.getY() + 5);

			// PIXEL AZUL
			jogador.setxB(jogador.getX() + 30);
			jogador.setyB(jogador.getY() - 10);

		}
	}

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode() == 82 && morreu) {
				timer.stop();
				setVisible(false);
				jogo2SalaPrinc salaPrincipal = new jogo2SalaPrinc();
				salaPrincipal.salaPrinc = false;
				salaPrincipal.salaPorta1 = true;
			}
			
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}

			if (jogador.isAndar()) {
				jogador.keyPressed(e);
			}
		}

		public void keyReleased(KeyEvent e) {
			jogador.keyReleased(e);
		}
	}

	private class balaoDialogFadeIn extends Thread {
		public void run() {
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeIn.gif");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lbBalaoDialog.setVisible(false);
			jogador.setAndar(true);
		}
	}
	
	private class balaoDialogFadeOut extends Thread {
		public void run() {
			lbBalaoDialog.setVisible(true);
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaStatic.png");
			lbBalaoDialog.setIcon(imgBalaoDialog);
		}
	}
	
	private class dialogoDeMorte extends Thread {
		public void run() {
			lbBalaoDialog.setBounds(jogador.getX(), jogador.getY() - 60, 130, 60);
			dialogoMorte.setBounds(jogador.getX() + 20, jogador.getY() - 64, 100, 60);
			jogador.setCaminhoImg("res/imgGuri/Guri05.gif");
			jogador.carregar();
			dialogoMorte.setVisible(true);
			palavra = "";
			for(int i = 0; i < textoMorte.length(); i++) {
				TextEffect(textoMorte, dialogoMorte, i, 55);				
			}
		}
	}
	
	public static void main(String args[]) {
		new labirintoPuzzleRed();
	}
}
