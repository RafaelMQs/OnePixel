package OnePixel;

import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;

import BancoDeDados.onePixelDAO;
import BancoDeDados.pixelGetSet;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.sql.SQLException;
import java.util.Iterator;

public class salaPuzzleBlueBoss extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	private Timer timer;
	private JPanel panel;

	// FALA DO SAPO

	private String[] falaDoSapo = { "<html>SAPO: Então você quer me desafiar...?", "<html>SAPO: Vem pro x1",
			"<html>SAPO: Você perdeu! Volte mais tarde" };

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	boolean pularDialog = false, podePular = false, morreu = false;
	String palavra = "";

	// FUNDO DA TELA (CENÁRIO) IMG - JLABEL
	ImageIcon imgFundo;
	JLabel lbFundo;

	// SAPO - BOSS
	ImageIcon imgSapo;
	Image sapo;
	JLabel dialogoDoSapo;
	String bravo = "";
	private int sapoX = 242, sapoY = 30;

	// IMGs DO PIXEL RED
	ImageIcon imgBluePixel;
	JLabel bluePixel;

	// Jokenpo

	ImageIcon imgTesouraEsquerda, imgPedraEsquerda, imgPapelEsquerda, imgTesouraDireita, imgPedraDireita,
			imgPapelDireita, imgRandomJokenpo;

	JLabel tesouraEsquerda, pedraEsquerda, papelEsquerda, tesouraDireita, pedraDireita, papelDireita, randomJokenpo;

	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;

	// LOCAL AONDE ELE ESTA
	int localTerreno = 0;

	// Exibir cena em uma unica vez
	boolean fazerUmaVez = true;

	// Barreira

	int reduzir = 0;
	// JOGO
	private String jokenpo = "", empate = "";
	int pontosGuri = 2;
	int pontosSapo = 0;
	boolean win = false, derrotou = false, update = false;

	public salaPuzzleBlueBoss() {
		componentes();
		eventosClick();
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
				if (localTerreno == 1) {
					grafico.drawImage(sapo, sapoX, sapoY, 111, 130, this);
				}
				grafico.drawImage(jogador.getImgPixelRed(), jogador.getxR(), jogador.getyR(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelGreen(), jogador.getxG(), jogador.getyG(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelBlue(), jogador.getxB(), jogador.getyB(), 32, 32, this);
				grafico.dispose();
			}
		};
		panel.setBounds(0, 0, 600, 310);
		add(panel);

		// INICIANDO JOGADOR
		jogador = new Jogador();
		jogador.carregar();
		jogador.setX(278);
		jogador.setY(140);
		movimentacaoPet();

		dialogoDoSapo = new JLabel();
		dialogoDoSapo.setForeground(Color.white);
		dialogoDoSapo.setFont(new Font("Pixel Operator 8", Font.PLAIN, 8));
		dialogoDoSapo.setVisible(false);
		panel.add(dialogoDoSapo);

		// JOKENPO - GURI
		imgPedraEsquerda = new ImageIcon("res2/imgPuzzleBlueBoss/Pedra1.png");
		pedraEsquerda = new JLabel(imgPedraEsquerda);
		pedraEsquerda.setVisible(false);
		panel.add(pedraEsquerda);

		imgTesouraEsquerda = new ImageIcon("res2/imgPuzzleBlueBoss/Tesoura2.png");
		tesouraEsquerda = new JLabel(imgTesouraEsquerda);
		tesouraEsquerda.setVisible(false);
		panel.add(tesouraEsquerda);

		imgPapelEsquerda = new ImageIcon("res2/imgPuzzleBlueBoss/Papel1.png");
		papelEsquerda = new JLabel(imgPapelEsquerda);
		papelEsquerda.setVisible(false);
		panel.add(papelEsquerda);

		imgPedraDireita = new ImageIcon("res2/imgPuzzleBlueBoss/Pedra2.png");
		pedraDireita = new JLabel(imgPedraDireita);
		pedraDireita.setVisible(false);
		panel.add(pedraDireita);

		imgTesouraDireita = new ImageIcon("res2/imgPuzzleBlueBoss/Tesoura2.png");
		tesouraDireita = new JLabel(imgTesouraDireita);
		tesouraDireita.setVisible(false);
		panel.add(tesouraDireita);

		imgPapelDireita = new ImageIcon("res2/imgPuzzleBlueBoss/Papel2.png");
		papelDireita = new JLabel(imgPapelDireita);
		papelDireita.setVisible(false);
		panel.add(papelDireita);

		imgRandomJokenpo = new ImageIcon("res2/imgPuzzleBlueBoss/sorteioJokenpo.gif");
		randomJokenpo = new JLabel(imgRandomJokenpo);
		randomJokenpo.setVisible(false);
		panel.add(randomJokenpo);

		// PIXEL AZUL
		imgBluePixel = new ImageIcon("res2/imgPixels/ChamaPixelAzul.gif");
		bluePixel = new JLabel(imgBluePixel);
		bluePixel.setVisible(false);
		panel.add(bluePixel);

		// IMAGENs DO BALAO DE DIALOGO
		imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
		lbBalaoDialog = new JLabel(imgBalaoDialog);
		lbBalaoDialog.setVisible(false);
		panel.add(lbBalaoDialog);

		// IMG FUNDO
		imgFundo = new ImageIcon("res2/imgPuzzleBlueBoss/CenarioAzul1.png");
		lbFundo = new JLabel(imgFundo);
		lbFundo.setBounds(0, 0, 600, 310);
		panel.add(lbFundo);

		// INICIANDO BANCO DE DADOS
		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}

		addKeyListener(new Teclado());

		timer = new Timer(6, this);
		timer.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// SAPO - BOSS
		// VOLTA SALA 1 - BAIXO
		if (jogador.getY() >= 280 && jogador.getX() >= 165 && jogador.getX() <= 385) {
			localTerreno = 0;
			jogador.setY(0);

			// VAI SALA 7 - CIMA
		} else if (jogador.getY() <= -20 && jogador.getX() >= 165 && jogador.getX() <= 385) {
			localTerreno = 1;
			jogador.setY(255);
		}

		imgSapo = new ImageIcon("res2/imgPuzzleBlueBoss/sapo" + bravo + ".gif");
		sapo = imgSapo.getImage();

		if (localTerreno == 0) {
			// IMG FUNDO
			imgFundo = new ImageIcon("res2/imgPuzzleBlueBoss/CenarioAzul1.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 230, 310); // Barreira direita
			colisao(365, 0, 230, 310);// Barreira esquerda
			colisao(0, 195, 600, 20); // Barreira de baixo
		} else {
			// IMG FUNDO
			imgFundo = new ImageIcon("res2/imgPuzzleBlueBoss/CenarioAzul2.png");
			lbFundo.setIcon(imgFundo);
			colisao(0, 0, 600, 80); // Barreira de cima
			colisao(0, 0, 100, 310); // Barreira esquerda
			colisao(485, 0, 100, 310); // Barreira direita

			// Barreira esquerda baixo
			colisao(100, 260, 100, 80);
			colisao(200, 290, 30, 20);

			// Barreira direita baixo
			colisao(400, 260, 100, 80);
			colisao(360, 290, 40, 20);

			// Sapo - Boss
			colisao(0, 0, 600, 250 - reduzir);

			if (checkColisao(0, 0, 600, 260) != null && fazerUmaVez != false) {
				fazerUmaVez = false;
				jogador.setAndar(false);
				dialogoDoSapo.setBounds(115, 40, 130, 50);
				lbBalaoDialog.setBounds(110, 40, 130, 60);

				new dialogo_Movimento_Do_sapo().start();
				new balaoDialogFadeOut().start();

			}

		}

		if (derrotou) {
			// COLISAO COM Pixel
			bluePixel.setBounds(sapoX + 30, sapoY + 160, 47, 38);
			bluePixel.setVisible(true);
			String colisaoPixel = checkColisao(sapoX + 30, sapoY + 160, 47, 38);
			if (colisaoPixel != null) {
				bluePixel.setVisible(false);
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				if (!update) {
					dao.pixel.setPixelB(1);
					int checkAtual = Integer.parseInt(dao.pixel.getCheckpoint()) + 1;
					pixelGetSet.setUpdateCheck(String.valueOf(checkAtual));
					update = true;
				}
				timer.stop();
				setVisible(false);
				jogo2SalaPrinc salaPrincipal = new jogo2SalaPrinc();
				salaPrincipal.salaPrinc = false;
				salaPrincipal.salaPorta3 = true;
			}
		}

		movimentacaoPet();
		jogador.atualizar();
		repaint();

	}

	private class dialogo_Movimento_Do_sapo extends Thread {
		public void run() {
			try {
				// Fala do sapo
				for (int z = 0; z < falaDoSapo[0].length(); z++) {
					TextEffect(falaDoSapo[0], dialogoDoSapo, z, 55);
				}
				sleep(2000);
				palavra = "";
				for (int z = 0; z < falaDoSapo[1].length(); z++) {
					TextEffect(falaDoSapo[1], dialogoDoSapo, z, 55);
				}
				sleep(2000);
				palavra = "";
				dialogoDoSapo.setVisible(false);
				new balaoDialogFadeIn().start();
				jogador.setAndar(false);
				sleep(600);

				// Depois da fala do Sapo. O guri andará para atrás
				int i = 0;
				while (i < 10) {
					jogador.setY(jogador.getY() + i);
					sleep(60);
					i++;
				}
				new balaoDialogFadeOutMediano().start();
				sleep(1700);
				pedraEsquerda.setBounds(160, 165, 47, 38);
				pedraEsquerda.setVisible(true);
				papelEsquerda.setBounds(135, 210, 47, 38);
				papelEsquerda.setVisible(true);
				tesouraEsquerda.setBounds(190, 210, 47, 38);
				tesouraEsquerda.setVisible(true);
				sleep(60);

				// JOGO
				while (pontosGuri < 3 && pontosSapo < 3) {
					randomJokenpo.setBounds(sapoX + 90, sapoY + 135, 47, 38);
					randomJokenpo.setVisible(true);
					papelDireita.setVisible(false);
					pedraDireita.setVisible(false);
					tesouraDireita.setVisible(false);

					while (jokenpo == "") {
						System.out.print("");
					}
					sleep(500);
					jogoJokenpo();
					sleep(1000);
					pedraEsquerda.setBounds(160, 165, 47, 38);
					papelEsquerda.setBounds(135, 210, 47, 38);
					tesouraEsquerda.setBounds(190, 210, 47, 38);
				}

				new balaoDialogFadeInMediano().start();
				jogador.setAndar(true);
				reduzir = 50; // reduzir o tamaho da barreira que não deixa o usuario chegar no sapo
				papelDireita.setVisible(false);
				pedraDireita.setVisible(false);
				tesouraDireita.setVisible(false);
				randomJokenpo.setVisible(false);

			} catch (Exception e) {
			}
		}
	}

	public void eventosClick() {
		pedraEsquerda.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pedraEsquerda.setVisible(false);
				papelEsquerda.setVisible(false);
				tesouraEsquerda.setVisible(false);
				pedraEsquerda.setBounds(330, 210, 47, 38);
				jokenpo = "pedra";
				System.out.println("Guri escolheu : " + jokenpo);
			}
		});

		papelEsquerda.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pedraEsquerda.setVisible(false);
				papelEsquerda.setVisible(false);
				tesouraEsquerda.setVisible(false);
				papelEsquerda.setBounds(330, 210, 47, 38);
				jokenpo = "papel";
				System.out.println("Guri escolheu : " + jokenpo);
			}
		});

		tesouraEsquerda.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				pedraEsquerda.setVisible(false);
				papelEsquerda.setVisible(false);
				tesouraEsquerda.setVisible(false);
				tesouraEsquerda.setBounds(330, 210, 47, 38);
				jokenpo = "tesoura";
				System.out.println("Guri escolheu : " + jokenpo);
			}
		});
	}

	public void jogoJokenpo() {
		int random = (int) (Math.random() * 9) + 1;
		if (jokenpo != "") {
			randomJokenpo.setVisible(false);
			// SORTEIA PEDRA, PAPEL OU TESOURA
			String pedraPapelOuTesoura = "";
			if (random > 0 && random < 4) {
				pedraPapelOuTesoura = "pedra";
				pedraDireita.setBounds(sapoX + 90, sapoY + 135, 47, 38);
				pedraDireita.setVisible(true);
				papelDireita.setVisible(false);
				tesouraDireita.setVisible(false);
			} else if (random > 3 && random < 7) {
				pedraPapelOuTesoura = "papel";
				papelDireita.setBounds(sapoX + 90, sapoY + 135, 47, 38);
				papelDireita.setVisible(true);
				pedraDireita.setVisible(false);
				tesouraDireita.setVisible(false);
			} else if (random > 6 && random < 10) {
				pedraPapelOuTesoura = "tesoura";
				tesouraDireita.setBounds(sapoX + 90, sapoY + 135, 47, 38);
				tesouraDireita.setVisible(true);
				papelDireita.setVisible(false);
				pedraDireita.setVisible(false);
			}
			System.out.println("Sapo escolheu : " + pedraPapelOuTesoura);

			// CASO ELE ESCOLHA PEDRA
			if (pedraPapelOuTesoura == "pedra" && jokenpo == "pedra") {
				System.out.println("Pedra com Pedra - EMPATE");
				empate = "empate";
				bravo = "";
			} else if (pedraPapelOuTesoura == "papel" && jokenpo == "pedra") {
				System.out.println("Pedra com Papel - DERROTA");
				win = false;
				empate = "";
				bravo = "";
			} else if (pedraPapelOuTesoura == "tesoura" && jokenpo == "pedra") {
				System.out.println("Pedra com Tesoura - VITORIA");
				win = true;
				bravo = "Bravo";
				empate = "";

				// CASO ELE ESCOLHA PAPEL
			} else if (jokenpo == "papel" && pedraPapelOuTesoura == "pedra") {
				System.out.println("Papel com Pedra - VITORIA");
				win = true;
				empate = "";
				bravo = "Bravo";
			} else if (jokenpo == "papel" && pedraPapelOuTesoura == "papel") {
				System.out.println("Papel com Papel - EMPATE");
				empate = "empate";
				bravo = "";
			} else if (jokenpo == "papel" && pedraPapelOuTesoura == "tesoura") {
				System.out.println("Papel com Tesoura - DERROTA");
				win = false;
				empate = "";
				bravo = "";

				// CASO ELE ESCOLHA TESOURA
			} else if (jokenpo == "tesoura" && pedraPapelOuTesoura == "pedra") {
				System.out.println("Tesoura com Pedra - DERROTA");
				win = false;
				empate = "";
				bravo = "";
			} else if (jokenpo == "tesoura" && pedraPapelOuTesoura == "papel") {
				System.out.println("Tesoura com Papel - VITORIA");
				win = true;
				empate = "";
				bravo = "Bravo";
			} else if (jokenpo == "tesoura" && pedraPapelOuTesoura == "tesoura") {
				System.out.println("Tesoura com Tesoura - EMPATE");
				empate = "empate";
				bravo = "";
			}

			if (win && empate != "empate") {
				pontosGuri += 1;
				pedraEsquerda.setVisible(true);
				papelEsquerda.setVisible(true);
				tesouraEsquerda.setVisible(true);
				if (pontosGuri == 3) {
					System.out.println("Ganhouuuu! " + pontosGuri);
					derrotou = true;
					pedraEsquerda.setVisible(false);
					papelEsquerda.setVisible(false);
					tesouraEsquerda.setVisible(false);
				}
			} else if (!win && empate != "empate") {
				pontosSapo += 1;
				pedraEsquerda.setVisible(true);
				papelEsquerda.setVisible(true);
				tesouraEsquerda.setVisible(true);
				if (pontosSapo == 3) {
					System.out.println("Perdeu! " + pontosSapo);
					pedraEsquerda.setVisible(false);
					papelEsquerda.setVisible(false);
					tesouraEsquerda.setVisible(false);
					new dialogoDerrota().start();
				}
			}
			if (pontosGuri < 3 && pontosSapo < 3) {
				jokenpo = "";
				System.out.println("Sapo pontos " + pontosSapo + " X " + pontosGuri + " Pontos guri \n");
				pedraEsquerda.setVisible(true);
				papelEsquerda.setVisible(true);
				tesouraEsquerda.setVisible(true);
			}
		}
	}

	public class dialogoDerrota extends Thread {
		public void run() {
			try {
				sleep(3000);

				lbBalaoDialog.setBounds(110, 40, 130, 60);
				new balaoDialogFadeOut().start();
				palavra = "";
				for (int z = 0; z < falaDoSapo[2].length(); z++) {
					TextEffect(falaDoSapo[2], dialogoDoSapo, z, 55);
				}
				sleep(2000);
				palavra = "";
				dialogoDoSapo.setVisible(false);
				new balaoDialogFadeIn().start();
				timer.stop();
				setVisible(false);
				new jogo2SalaPrinc();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	// COLISÂO COM A BARREIRA
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

	// CHECAR ONDE COLIDIU
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

	// FECHA O BALÃO PEQUENO
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
		}
	}

	// ABRIR O BALÃO PEQUENO
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

	// ABRIR O BALÃO MEDIO
	private class balaoDialogFadeOutMediano extends Thread {
		public void run() {
			lbBalaoDialog.setBounds(110, 130, 360, 186);
			lbBalaoDialog.setVisible(true);
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/medianoin.gif");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			try {
				sleep(1400);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/medianostatic.png");
			lbBalaoDialog.setIcon(imgBalaoDialog);
		}
	}

	private class balaoDialogFadeInMediano extends Thread {
		public void run() {
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/medianoout.gif");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lbBalaoDialog.setVisible(false);
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

	// TEXTO NO BALÃO
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

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == 82 && morreu) {
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

	public static void main(String args[]) {
		new salaPuzzleBlueBoss();
	}

}
