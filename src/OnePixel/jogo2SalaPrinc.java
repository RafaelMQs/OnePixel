package OnePixel;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class jogo2SalaPrinc extends JPanel implements ActionListener {
	private Jogador jogador;
	private int xP = 260, yP = 0, alturaP = 73, larguraP = 60;
	private Timer timer;

	// BOOLEAN DE COLISAO E DE PET
	private boolean colidiu = false, mudarPet = true;

	// LOGO
	ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	int larguraFrame = 600;
	int alturaFrame = 310;

//	IMAGENs DE FUNDO
	// FUNDO
	Image fundo;
	// CHAO
	Image chaoSala;

	public jogo2SalaPrinc() {
		componentes();
		eventos();
	}

	public void componentes() {
		setFocusable(true);
		setDoubleBuffered(true);

// IMAGENs DE FUNDO
		// CHAO
		ImageIcon imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoSalaPrincipal.png");
		chaoSala = imgChaoFundo.getImage();
		// FUNDO
		ImageIcon gifFundo = new ImageIcon("res2/imgSalaPrincipal/background.gif");
		fundo = gifFundo.getImage();

// CARREGAR OS KEYLISTENERS DA CLASS JOGADOR
		addKeyListener(new Teclado());
// CARREGAR CLASS JOGADOR
		jogador = new Jogador();
		jogador.carregar();
// TIMER
		timer = new Timer(5, this);
		timer.start();
	}

	public void eventos() {

	}

	public void paint(Graphics g) {
		Graphics2D grafico = (Graphics2D) g;
		grafico.drawImage(fundo, 0, 0, 600, 310, this);
		grafico.drawImage(chaoSala, 0, 0, 600, 310, null);
		grafico.drawImage(jogador.getImgPlayer(), jogador.getX(), jogador.getY(), jogador.getLargura(),
				jogador.getAltura(), this);
		grafico.drawImage(jogador.getImgPet(), jogador.getXB(), jogador.getYB(), 32, 32, this);
		grafico.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkColisaoBorda(larguraFrame - 30, 30, alturaFrame - 75, 60);

//		checkColisao();

		jogador.atualizar();
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

	public void checkColisao(int xB, int yB, int larguraB, int alturaB) {
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
			jogador.setX(jogador.getX() - 2);

			// COLISAO LADO ESQUERDO DO GURI
		} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setX(jogador.getX() + 2);

			// COLISAO LADO DE BAIXO DO GURI
		} else if (ladoDireitoA >= ladoEsquerdoB && ladoEsquerdoA <= ladoDireitoB && ladoBaixoA >= ladoCimaB
				&& ladoBaixoA <= ladoBaixoB) {
			jogador.setY(jogador.getY() - 2);

			// COLISAO LADO DE CIMA DO GURI
		} else if (ladoEsquerdoA <= ladoDireitoB && ladoDireitoA >= ladoEsquerdoB && ladoCimaA >= ladoBaixoB - 2
				&& ladoCimaA <= ladoBaixoB) {
			jogador.setY(jogador.getY() + 2);
		}

		if (jogador.isCima()) {
			jogador.setYB(jogador.getY() + 40);
			jogador.setXB(jogador.getX());

		} else if (jogador.isBaixo()) {
			jogador.setYB(jogador.getY() - 25);
			jogador.setXB(jogador.getX());

		} else if (jogador.isDireita()) {
			jogador.setXB(jogador.getX() - 30);
			jogador.setYB(jogador.getY());

		} else if (jogador.isEsquerda()) {
			jogador.setXB(jogador.getX() + 40);
			jogador.setYB(jogador.getY());
		}

	}

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				System.exit(0);
			}
			jogador.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			jogador.keyReleased(e);
		}
	}

}
