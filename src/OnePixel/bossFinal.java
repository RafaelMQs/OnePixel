package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import BancoDeDados.*;

public class bossFinal extends JFrame {
	private Jogador jogador;
	private onePixelDAO dao;
	JPanel panel;

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	boolean pularDialog = false, podePular = false;
	String palavra = "";

	// ZEZIN
	ImageIcon imgZezin;
	JLabel lbZezin;
	private int xZezin = 500, yZezin = 130, larguraZezin = 45, alturaZezin = 45;

	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;

	JLabel lbDialogo;

	public bossFinal() {
		componentes();
		eventos();

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
		add(panel);

		imgZezin = new ImageIcon("res2/imgZezin/Zezin_1.gif");
		lbZezin = new JLabel(imgZezin);
		lbZezin.setBounds(xZezin, yZezin, larguraZezin, alturaZezin);
		panel.add(lbZezin);

		// IMAGENs DO BALAO DE DIALOGO
		imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
		lbBalaoDialog = new JLabel(imgBalaoDialog);
		lbBalaoDialog.setBounds(385, 70, 130, 60);
		lbBalaoDialog.setVisible(false);
		panel.add(lbBalaoDialog);

		// CARREGAR OS KEYLISTENERS DA CLASS JOGADOR
		addKeyListener(new Teclado());
		// CARREGAR CLASS JOGADOR
		jogador = new Jogador();
		jogador.carregar();

		jogador.setX(10);
		jogador.setCaminhoImg("res2/imgPlayer/guriDireita000.png");
		jogador.carregar();
		repaint();

		// INICIANDO BANCO DE DADOS
		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}
	}

	public void eventos() {
		new movimentacaoInicial().start();
	}

	private class movimentacaoInicial extends Thread {
		public void run() {
			while (jogador.getX() <= 350) {
				jogador.setX(jogador.getX() + 4);
				jogador.setCaminhoImg("./res2/imgPlayer/guriDireita001.png");
				jogador.carregar();
				repaint();
				try {
					sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				jogador.setCaminhoImg("./res2/imgPlayer/guriDireita002.png");
				jogador.carregar();
				repaint();
				try {
					sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			jogador.setCaminhoImg("./res2/imgPlayer/guriDireita000.png");
			jogador.carregar();
			repaint();
			new balaoDialogFadeOut().start();
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

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				panel.setVisible(false);
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
		}
	}

	public static void main(String args[]) {
		new bossFinal();
	}
}
