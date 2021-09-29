package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;
import BancoDeDados.*;

public class bossMorcego extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	JPanel panel;

	boolean podePular = false, pularDialog = false;
	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// IMG FUNDO
	ImageIcon imgFundo;
	JLabel lbFundo;
	
	// TIMER
	Timer timer;

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
				grafico.drawImage(jogador.getImgPlayer(), jogador.getX(), jogador.getY(), jogador.getLargura(),
						jogador.getAltura(), this);
				grafico.drawImage(jogador.getImgPixelRed(), jogador.getxR(), jogador.getyR(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelGreen(), jogador.getxG(), jogador.getyG(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelBlue(), jogador.getxB(), jogador.getyB(), 32, 32, this);
				grafico.dispose();
			}
		};
		panel.setBounds(0, 0, 600, 310);
		panel.setDoubleBuffered(true);
		add(panel);

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
		// TIMER
		timer = new Timer(5, this);
		timer.start();

		// INICIANDO BANCO DE DADOS
		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkColisaoBorda(larguraFrame- 42, 40, alturaFrame - 36, 0);
		
		movimentacaoPet();
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

	public static void main(String args[]) {
		new bossMorcego();
	}
	
	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
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
			if(jogador.isAndar()) {
				jogador.keyPressed(e);				
			}
		}

		public void keyReleased(KeyEvent e) {
			jogador.keyReleased(e);
		}
	}

}
