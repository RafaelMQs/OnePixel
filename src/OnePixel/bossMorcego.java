package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import BancoDeDados.*;
import BossMorcego.*;

public class bossMorcego extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	JPanel panel;

	private inimigo inimigo;
	private tiroDoInimigo tiro;

	boolean podePular = false, pularDialog = false, morto = false, iniciou = true, intangivel = false, derrotou = false;
	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;
	int xPXRed = 400, yPXRed = 100, largPXRed = 60, alturaPXRed = 60;

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// IMG FUNDO
	ImageIcon imgFundo;
	JLabel lbFundo;

	// TIMER
	Timer timer;

	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;

	String textoMorte = "<html> <center>Você morreu!</center><br> Para recomeçar <center>aperte R</center> </html>";
	JLabel dialogoMorte;
	
	// IMGs DO PIXEL RED
	ImageIcon imgRedPixel;
	JLabel redPixel;

	String palavra = "";
	int tempoInimigo = 0;

	public bossMorcego() {
		componentes();

		setIconImage(imgLogo.getImage());
		setBackground(Color.red);
		setTitle("OnePixel - Part2");
		setSize(larguraFrame, alturaFrame);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);
	}

	public void componentes() {
		setLayout(null);
		setFocusable(true);

		// DEFININDO JPANEL PADRAO
		panel = new JPanel(null) {
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D grafico = (Graphics2D) g;
				grafico.drawImage(jogador.getImgPixelRed(), jogador.getxR(), jogador.getyR(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelGreen(), jogador.getxG(), jogador.getyG(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelBlue(), jogador.getxB(), jogador.getyB(), 32, 32, this);
				grafico.drawImage(inimigo.getImgInimigo(), inimigo.getX(), inimigo.getY(), inimigo.getLargura(),
						inimigo.getAltura(), this);
				if (tiro.getStopTiro() != 1) {
					grafico.drawImage(tiro.getImgTiro(), tiro.getXtiro(), tiro.getYtiro(), 54, 50, this);
					grafico.drawImage(tiro.getImgTiro(), tiro.getXtiro(), tiro.getYtiro() + 25, 54, 50, this);

				}
				grafico.drawImage(jogador.getImgPlayer(), jogador.getX(), jogador.getY(), jogador.getLargura(),
						jogador.getAltura(), this);
				grafico.dispose();
			}
		};
		panel.setFocusable(true);
		panel.setDoubleBuffered(true);
		panel.setBounds(0, 0, 600, 310);
		add(panel);

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
		
		// IMAGENs DO PIXEL VERMELHO
		imgRedPixel = new ImageIcon("res2/imgPixels/ChamaPixelVermelho.gif");
		redPixel = new JLabel(imgRedPixel);

		redPixel.setBounds(xPXRed, yPXRed, largPXRed, alturaPXRed);
		redPixel.setVisible(false);
		panel.add(redPixel);

		// CRIANDO IMG FUNDO
		imgFundo = new ImageIcon("res2/imgCenarioBoss1/BossCenary.png");
		lbFundo = new JLabel(imgFundo);
		lbFundo.setBounds(0, 0, 600, 310);
		panel.add(lbFundo);

		// CARREGAR OS KEYLISTENERS DA CLASS JOGADOR
		addKeyListener(new Teclado());
		// CARREGAR CLASS JOGADOR
		jogador = new Jogador();
		jogador.carregar();
		jogador.setX(35);

		inimigo = new inimigo();
		inimigo.carregar();

		tiro = new tiroDoInimigo();
		tiro.carregarComponents();

//		tiro = new tiroDoInimigo();
//		tiro.carregarComponents();

		// INICIANDO BANCO DE DADOS
		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}

		new Temporizador().start();
		
		// TIMER
		timer = new Timer(6, this);
		timer.start();

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkColisaoBorda(larguraFrame - 160, 40, alturaFrame - 36, 0);

		if (colisaoTiro()) {
			if(!intangivel) {
				if (!morto) {
					System.out.println("Morreu");
					tiro.setStopTiro(1);
					inimigo.setParar(true);
					morto = true;
					jogador.setAndar(false);
					new dialogoDeMorte().start();
				}
			}
		}

		if (morto) {
			iniciou = false;
			jogador.setCaminhoImg("res/imgGuri/Guri05.gif");
			jogador.carregar();
		}
		
		if(derrotou) {
			// COLISAO COM Pixel
			String colisaoPixel = colisaoPixelRed(xPXRed, yPXRed, largPXRed, alturaPXRed);
			if(colisaoPixel != "longe") {
				redPixel.setVisible(false);
				try { Thread.sleep(1000); } catch (InterruptedException e1) { e1.printStackTrace(); }
				dao.pixel.setPixelR(1);
				dao.pixel.setCheckpoint("2");
				timer.stop();
				setVisible(false);
				jogo2SalaPrinc salaPrincipal = new jogo2SalaPrinc();
				salaPrincipal.salaPrinc = false;
				salaPrincipal.salaPorta1 = true;
			}
		}
		
		

		movimentacaoPet();
		jogador.atualizar();
		inimigo.atualizar();
		tiro.atualizar();
		repaint();
	}

	public void checkColisaoBorda(int xLarguraFrameP, int xLarguraFrameN, int yAlturaFrameP, int yAlturaFrameN) {
		// COLISAO GURI LADO DIREITO
		if (jogador.getX() + jogador.getLargura() >= xLarguraFrameP) {
			if (jogador.isCorrendo()) {
				jogador.setX(jogador.getX() - 4);
			} else {
				jogador.setX(jogador.getX() - 2);
			}

			// COLISAO GURI LADO ESQUERDO
		} else if (jogador.getX() <= xLarguraFrameN) {
			if (jogador.isCorrendo()) {
				jogador.setX(jogador.getX() + 4);
			} else {
				jogador.setX(jogador.getX() + 2);
			}
			// COLISAO GURI CIMA
		} else if (jogador.getY() <= yAlturaFrameN) {
			if (jogador.isCorrendo()) {
				jogador.setY(jogador.getY() + 4);
			} else {
				jogador.setY(jogador.getY() + 2);
			}
			// COLISAO GURI BAIXO
		} else if (jogador.getY() + jogador.getLargura() >= yAlturaFrameP) {
			if (jogador.isCorrendo()) {
				jogador.setY(jogador.getY() - 4);
			} else {
				jogador.setY(jogador.getY() - 2);
			}
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

	public boolean colisaoTiro() {
		int aX = jogador.getX();
		int aY = jogador.getY();
		int ladoDireitoA = aX + jogador.getLargura() - 12;
		int ladoEsquerdoA = aX + 12;
		int ladoBaixoA = aY + jogador.getAltura() - 2;
		int ladoCimaA = aY + 35;

		int tiroX = tiro.getXtiro();
		int tiroY = tiro.getYtiro();
		int ladoDireitoTiro = tiroX + 15;
		int ladoEsquerdoTiro = tiroX;
		int ladoBaixoTiro = tiroY + 50;
		int ladoCimaTiro = tiroY + 10;

		boolean colidiu = false;
		// TIRO

		if (ladoDireitoA >= ladoEsquerdoTiro && ladoDireitoA < ladoEsquerdoTiro + 2 && ladoBaixoA >= ladoCimaTiro
				&& ladoCimaA <= ladoBaixoTiro) {
			colidiu = true;
		} else if (ladoEsquerdoA >= ladoDireitoTiro - 2 && ladoEsquerdoA <= ladoDireitoTiro
				&& ladoBaixoA >= ladoCimaTiro && ladoCimaA <= ladoBaixoTiro) {
			colidiu = true;
		} else if (ladoDireitoA >= ladoEsquerdoTiro && ladoEsquerdoA <= ladoDireitoTiro
				&& ladoBaixoA >= ladoCimaTiro - 8 && ladoBaixoA <= ladoBaixoTiro) {
			colidiu = true;
		} else if (ladoEsquerdoA <= ladoDireitoTiro && ladoDireitoA >= ladoEsquerdoTiro
				&& ladoCimaA >= ladoBaixoTiro - 2 && ladoCimaA <= ladoBaixoTiro + 35) {
			colidiu = true;
		}

		return colidiu;

	}

	public String colisaoPixelRed(int xB, int yB, int larguraB, int alturaB) {
		int aX = jogador.getX();
		int aY = jogador.getY();
		int ladoDireitoA = aX + jogador.getLargura() - 12;
		int ladoEsquerdoA = aX + 12;
		int ladoBaixoA = aY + jogador.getAltura() - 2;
		int ladoCimaA = aY + 35;

		int bX = xB;
		int bY = yB;
		int ladoDireitoB = bX + larguraB;
		int ladoEsquerdoB = bX + 15;
		int ladoBaixoB = bY + alturaB - 2;
		int ladoCimaB = bY + 25;

		String colidiu = "longe";

		if (ladoDireitoA >= ladoEsquerdoB && ladoDireitoA < ladoEsquerdoB + 2 && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setX(jogador.getX() - 2);
			colidiu = "true";
		} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setX(jogador.getX() + 2);
			colidiu = "true";
		} else if (ladoDireitoA >= ladoEsquerdoB && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoBaixoA <= ladoBaixoB) {
			jogador.setY(jogador.getY() - 2);
			colidiu = "true";
		} else if (ladoEsquerdoA <= ladoDireitoB && ladoDireitoA >= ladoEsquerdoB && ladoCimaA >= ladoBaixoB - 2
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setY(jogador.getY() + 2);
			colidiu = "true";

		} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB + 4 && ladoBaixoA >= ladoCimaB + 4
				&& ladoCimaA <= ladoBaixoB + 4) {
			colidiu = "pertoD";
		}

		if (jogador.isCorrendo()) {
			if (ladoDireitoA >= ladoEsquerdoB && ladoDireitoA < ladoEsquerdoB + 2 && ladoBaixoA >= ladoCimaB
					&& ladoCimaA <= ladoBaixoB) {
				jogador.setX(jogador.getX() - 4);
				colidiu = "true";
			} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
					&& ladoCimaA <= ladoBaixoB) {
				jogador.setX(jogador.getX() + 4);
				colidiu = "true";
			} else if (ladoDireitoA >= ladoEsquerdoB && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
					&& ladoBaixoA <= ladoBaixoB) {
				jogador.setY(jogador.getY() - 4);
				colidiu = "true";
			} else if (ladoEsquerdoA <= ladoDireitoB && ladoDireitoA >= ladoEsquerdoB && ladoCimaA >= ladoBaixoB - 2
					&& ladoCimaA <= ladoBaixoB) {
				jogador.setY(jogador.getY() + 4);
				colidiu = "true";

			}
		}

		return colidiu;
	}

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 82 && morto) {
				timer.stop();
				setVisible(false);
				jogo2SalaPrinc salaPrincipal = new jogo2SalaPrinc();
				salaPrincipal.salaPrinc = false;
				salaPrincipal.salaPorta1 = true;
			}

			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (podePular) {
					pularDialog = true;
				} else {
					pularDialog = false;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				try {
					dao.bd.c.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
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

	private class Temporizador extends Thread {
		public void run() {
			while (tempoInimigo < 40) {
				if (iniciou) {
					try {
						sleep(100);
						tempoInimigo += 1;
						System.out.println(tempoInimigo);
						if (tempoInimigo >= 20 && tempoInimigo <= 40) {
							tiro.setVelocidade(18);
							System.out.println("18");
						}if(tempoInimigo >= 41 && tempoInimigo <= 60) {
							tiro.setVelocidade(16);
							System.out.println("16");
						}if(tempoInimigo >= 61 && tempoInimigo <= 80) {
							tiro.setVelocidade(14);
							System.out.println("14");
						}if(tempoInimigo >= 81 && tempoInimigo <= 100) {
							tiro.setVelocidade(12);
							System.out.println("12");
						}if(tempoInimigo >= 101 && tempoInimigo <= 120) {
							tiro.setVelocidade(10);
							System.out.println("10");
						}
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				} else {
					interrupt();
					break;
				}
			}
			
			if(!morto) {
				intangivel = true;
				System.out.println("GANHOU");
				tiro.setStopTiro(1);
				inimigo.setParar(true);
				
				inimigo.setCaminhoImg("res2/imgCenarioBoss1/BossV2.gif");
				inimigo.carregar();
				
				redPixel.setVisible(true);
				derrotou = true;
				
			}

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
			new balaoDialogFadeOut().start();
			dialogoMorte.setVisible(true);
			palavra = "";
			for (int i = 0; i < textoMorte.length(); i++) {
				TextEffect(textoMorte, dialogoMorte, i, 55);
			}
		}
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

	public static void main(String args[]) {
		new bossMorcego();
	}
}
