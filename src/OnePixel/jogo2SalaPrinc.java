package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import BancoDeDados.*;

public class jogo2SalaPrinc extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	JPanel panel;
	boolean ganhouPPPT = false, pularDialog = false, podePular = false, liberaEnter = true, firstDialog = false,
			entrou = false, dentro = true;
	String palavra = "";

	private int xPorao1 = 270, yPorao1 = 170, larguraPorao1 = 60, alturaPorao1 = 60, xPortaComum1 = 85,
			yPortaComum1 = 25, larguraPortasComuns = 60, alturaPortasComuns = 78, xPortaComum2 = 270, yPortaComum2 = 25,
			xPortaComum3 = 455, yPortaComum3 = 25, xPortaColorida = 270, yPortaColorida = 25, larguraPortaColorida = 60,
			alturaPortaColoria = 78;
	private Timer timer;
	private ImageIcon imgPorao, imgChaoFundo;

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	// JLABELs DAS IMGs FUNDO
	JLabel lbFundo, lbChaoFundo;

	// ZEZIN
	ImageIcon imgZezin;
	JLabel lbZezin;
	private int xZezin = 35, yZezin = 130, larguraZezin = 45, alturaZezin = 45;

	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;

	// JLABELs DIALOGO
	String[] TextoInicial = { "<html>Guri: Quem é você? Zezin: Eu sou o zezin. Ajudo o zero daqui de dentro</html>",
			"<html>Zezin: Este que vos fala é apenas um de meus clones</html>",
			"<html>Zezin: Aqui dentro está os guardiões dos pixels </html>",
			"<html>Zezin: Estava tudo certo, mas eles foram corrompidos</html>",
			"<html>Zezin: E dessa forma acabaram corrompendo os pixels </html>",
			"<html>Zezin: Sua missão é derrotar eles, pegar os pixels de volta e entregar para mim </html>",
			"<html>Guri: Okay, mas porque você mesmo não pega??</html>",
			"<html>Zezin: Eu nao tenho poder o suficiente, mas isso nao vem ao caso",
			"<html>Guri: Okay, mas porque eu devo entregar eles pra você?",
			"<html>Zezin: Apenas faça o que eu mando, garoto!</html>",
			"<html>Zezin: Tera um clone meu em cada sala, basta chegar nele e apertar enter",
			"<html>Zezin: Ele salvara o seus itens para sempre",
			"<html>Zezin: Estarei embaixo daquele porao, apenas entre quando tiver todos os pixels",
			"<html>Zezin: ANDE! Entre logo em uma porta!" };

	String[] TextoUpdate = { "<html>Zezin: Jogo salvo com sucesso!</html>",
			"<html> Zezin: Parabens! Você pegou o pixel vermelho" };
	JLabel dialogoDoGuri;

	// JLABELs DAS IMGs PORTAS
	JLabel portaComum1, portaComum2, portaComum3, porao, portaVermelha, portaVerde, portaAzul;

	// BOOLEAN PRA VEFICAR SE ELE ESTA NA SALA PRIC OU NAS OUTRAS SALAS
	boolean salaPrinc = true, salaPorta1 = false, salaPorta2 = false, salaPorta3 = false;

	public jogo2SalaPrinc() {
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
		add(panel);

// LABELs DO DIALOGO DO GURI
		dialogoDoGuri = new JLabel();
		dialogoDoGuri.setBounds(58, 45, 120, 100);
		dialogoDoGuri.setForeground(Color.WHITE);
		dialogoDoGuri.setFont(new Font("Pixel Operator 8", Font.PLAIN, 8));
		dialogoDoGuri.setVisible(false);
		panel.add(dialogoDoGuri);

// IMAGENs DO BALAO DE DIALOGO
		imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
		lbBalaoDialog = new JLabel(imgBalaoDialog);
		lbBalaoDialog.setBounds(50, 70, 130, 60);
		lbBalaoDialog.setVisible(false);
		panel.add(lbBalaoDialog);

// IMAGENs DAS PORTAS
		// PORTA COMUM
		ImageIcon imgPortaComum = new ImageIcon("res2/imgPortas/000PortaComum.png");
		portaComum1 = new JLabel(imgPortaComum);
		portaComum1.setBounds(xPortaComum1, yPortaComum1, larguraPortasComuns, alturaPortasComuns);
		panel.add(portaComum1);

		portaComum2 = new JLabel(imgPortaComum);
		portaComum2.setBounds(xPortaComum2, yPortaComum2, larguraPortasComuns, alturaPortasComuns);
		panel.add(portaComum2);

		portaComum3 = new JLabel(imgPortaComum);
		portaComum3.setBounds(xPortaComum3, yPortaComum3, larguraPortasComuns, alturaPortasComuns);
		panel.add(portaComum3);

		// PORTA VERMELHA
		ImageIcon imgPortaVermelha = new ImageIcon("res2/imgPortas/000Vermelho.png");
		portaVermelha = new JLabel(imgPortaVermelha);
		portaVermelha.setBounds(xPortaColorida, yPortaColorida, larguraPortaColorida, alturaPortaColoria);
		portaVermelha.setVisible(false);
		panel.add(portaVermelha);

		// PORTA VERDE
		ImageIcon imgPortaVerde = new ImageIcon("res2/imgPortas/000Verde.png");
		portaVerde = new JLabel(imgPortaVerde);
		portaVerde.setBounds(xPortaColorida, yPortaColorida, larguraPortaColorida, alturaPortaColoria);
		portaVerde.setVisible(false);
		panel.add(portaVerde);

		// PORTA AZUL
		ImageIcon imgPortaAzul = new ImageIcon("res2/imgPortas/000Azul.png");
		portaAzul = new JLabel(imgPortaAzul);
		portaAzul.setBounds(xPortaColorida, yPortaColorida, larguraPortaColorida, alturaPortaColoria);
		portaAzul.setVisible(false);
		panel.add(portaAzul);

		// PORAO
		imgPorao = new ImageIcon("res2/imgPortas/porao00.png");
		porao = new JLabel(imgPorao);
		porao.setBounds(xPorao1, yPorao1, larguraPorao1, alturaPorao1);
		panel.add(porao);

// IMAGENs DO ZEZIN
		imgZezin = new ImageIcon("res2/imgZezin/Zezin_2.gif");
		lbZezin = new JLabel(imgZezin);
		lbZezin.setBounds(xZezin, yZezin, larguraZezin, alturaZezin);
		panel.add(lbZezin);

// IMAGENs DE FUNDO
		// CHAO
		imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoSalaPrincipal.png");
		lbChaoFundo = new JLabel(imgChaoFundo);
		lbChaoFundo.setBounds(0, 0, 600, 310);
		panel.add(lbChaoFundo);
		// FUNDO
		ImageIcon imgFundo = new ImageIcon("res2/imgSalaPrincipal/background.png");
		lbFundo = new JLabel(imgFundo);
		lbFundo.setBounds(0, 0, 600, 310);
		panel.add(lbFundo);

// CARREGAR OS KEYLISTENERS DA CLASS JOGADOR
		addKeyListener(new Teclado());
// CARREGAR CLASS JOGADOR
		jogador = new Jogador();
		jogador.carregar();
// TIMER

// INICIANDO BANCO DE DADOS
		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}

		timer = new Timer(5, this);
		timer.start();

	}

	boolean jogando = false;

	public void actionPerformed(ActionEvent e) {

		if (salaPrinc) {
			xZezin = 35;
			yZezin = 130;
			lbZezin.setBounds(xZezin, yZezin, larguraZezin, alturaZezin);
			portaAzul.setVisible(false);
			portaVermelha.setVisible(false);
			portaVerde.setVisible(false);

			lbZezin.setVisible(true);
			portaComum1.setVisible(true);
			portaComum2.setVisible(true);
			portaComum3.setVisible(true);
			porao.setVisible(true);

			imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoSalaPrincipal.png");
			lbChaoFundo.setIcon(imgChaoFundo);

			// COLISAO COM A BORDA
			checkColisaoBorda(larguraFrame - 30, 30, alturaFrame - 75, 60);

			// COLISAO COM ZEZIN
			String colisaoZezin = colisaoZezin(xZezin, yZezin, larguraZezin, alturaZezin);
			if (colisaoZezin == "pertoD" && jogador.getTecla() == 10) {
				jogador.setTecla(0);
				if (liberaEnter) {
					new balaoDialogFadeOut().start();
					new dialogoInicial().start();
					liberaEnter = false;
				}
			}
			if (colisaoZezin != "pertoD") {
			}
			// COLISAO COM O PORAO
			String retorno = checkColisao(xPorao1, yPorao1, larguraPorao1, alturaPorao1);
			if (retorno == "dentro" && jogador.getTecla() == 10) {
				jogador.setTecla(0);
				if (dao.pixel.getPixelR() == 1 && dao.pixel.getPixelG() == 1 && dao.pixel.getPixelB() == 1) {

					String retornoInput = JOptionPane.showInputDialog(null, "Pedra, Papel ou Tesoura");
					System.out.println(retornoInput);
					int random = (int) (Math.random() * 9) + 1;

					// SORTEIA PEDRA, PAPEL OU TESOURA
					String pedraPapelOuTesoura = "";
					if (random > 0 && random < 4) {
						pedraPapelOuTesoura = "pedra";
					} else if (random > 3 && random < 7) {
						pedraPapelOuTesoura = "papel";
					} else if (random > 6 && random < 10) {
						pedraPapelOuTesoura = "tesoura";
					}	// CASO ELE ESCOLHA PEDRA
					if (retornoInput.toLowerCase().equals("pedra") && pedraPapelOuTesoura == "pedra") {
						System.out.println("Pedra com Pedra - EMPATE");
					} else if (retornoInput.toLowerCase().equals("pedra") && pedraPapelOuTesoura == "papel") {
						System.out.println("Pedra com Papel - DERROTA");
					} else if (retornoInput.toLowerCase().equals("pedra") && pedraPapelOuTesoura == "tesoura") {
						System.out.println("Pedra com Tesoura - VITORIA");
						ganhouPPPT = true;

						// CASO ELE ESCOLHA PAPEL
					} else if (retornoInput.toLowerCase().equals("papel") && pedraPapelOuTesoura == "pedra") {
						System.out.println("Papel com Pedra - VITORIA");
						ganhouPPPT = true;
					} else if (retornoInput.toLowerCase().equals("papel") && pedraPapelOuTesoura == "papel") {
						System.out.println("Papel com Papel - EMPATE");
					} else if (retornoInput.toLowerCase().equals("papel") && pedraPapelOuTesoura == "tesoura") {
						System.out.println("Papel com Tesoura - DERROTA");

						// CASO ELE ESCOLHA TESOURA
					} else if (retornoInput.toLowerCase().equals("tesoura") && pedraPapelOuTesoura == "pedra") {
						System.out.println("Tesoura com Pedra - DERROTA");
					} else if (retornoInput.toLowerCase().equals("tesoura") && pedraPapelOuTesoura == "papel") {
						System.out.println("Tesoura com Papel - VITORIA");
						ganhouPPPT = true;
					} else if (retornoInput.toLowerCase().equals("tesoura") && pedraPapelOuTesoura == "tesoura") {
						System.out.println("Tesoura com Tesoura - EMPATE");
					}

					if (ganhouPPPT) {
						System.out.println("Entrando");
						imgPorao = new ImageIcon("res2/imgPortas/porao01.png");
						porao.setIcon(imgPorao);
					} else {
						JOptionPane.showMessageDialog(this, "Você Perdeu!", "Pedra, Papel ou Tesoura", 0);
					}
					System.out.println(pedraPapelOuTesoura);

				

				} else {
					Tremer tremer = new Tremer();
					tremer.start();
					JOptionPane.showMessageDialog(this, "Colete os outros PIXELs!", "Busque pixels", 0);
				}
			}

			// COLISAO PORTA 1
			retorno = checkColisao(xPortaComum1, yPortaComum1, larguraPortasComuns, alturaPortasComuns);
			if (retorno != null) {
				System.out.println("Porta 1");
				salaPrinc = false;
				salaPorta1 = true;
				jogador.setX(278);
				jogador.setY(180);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
			}
			// COLISAO PORTA 2
			retorno = checkColisao(xPortaComum2, yPortaComum2, larguraPortasComuns, alturaPortasComuns);
			if (retorno != null) {
				System.out.println("Porta 2");
				salaPrinc = false;
				salaPorta2 = true;
				jogador.setX(278);
				jogador.setY(180);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
			}
			// COLISAO PORTA 3
			retorno = checkColisao(xPortaComum3, yPortaComum3, larguraPortasComuns, alturaPortasComuns);
			if (retorno != null) {
				System.out.println("Porta 3");
				salaPrinc = false;
				salaPorta3 = true;
				jogador.setX(278);
				jogador.setY(180);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
			}

			// ENTROU NA PRIMEIRA PORTA
		} else if (salaPorta1) {
			portaComum1.setVisible(false);
			portaComum2.setVisible(false);
			portaComum3.setVisible(false);
			porao.setVisible(false);
			portaVerde.setVisible(false);
			portaAzul.setVisible(false);

			portaVermelha.setVisible(true);

			checkColisaoBorda(larguraFrame - 150, 150, alturaFrame - 75, 60);

			imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoCheckpoint.png");
			lbChaoFundo.setIcon(imgChaoFundo);
			xZezin = 160;
			yZezin = 120;
			lbZezin.setBounds(xZezin, yZezin, larguraZezin, alturaZezin);

			// COLISAO COM ZEZIN
			String colisaoZezin = colisaoZezin(xZezin, yZezin, larguraZezin, alturaZezin);
			if (colisaoZezin == "pertoD" && jogador.getTecla() == 10) {
				jogador.setTecla(0);
				if (liberaEnter) {
					new balaoDialogFadeOut().start();
					new dialogoUpdate().start();
					liberaEnter = false;
				}
			}

			if (jogador.getY() >= 185 && jogador.getX() >= 260 && jogador.getX() <= 295) {
				salaPorta1 = false;
				salaPrinc = true;
				jogador.setX(95);
				jogador.setY(80);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
			}

			if (dao.pixel.getPixelR() == 1) {
				portaVermelha.setVisible(false);
			} else {
				portaVermelha.setVisible(true);
				// COLISAO COM A PORTA VERMELHA
				String colisaoPortaColorida = checkColisao(xPortaColorida, yPortaColorida, larguraPortaColorida,
						alturaPortaColoria);
				if (colisaoPortaColorida != null && entrou == false) {
					entrou = true;
					labirintoPuzzleRed labirinto = new labirintoPuzzleRed();
					labirinto.setVisible(true);
					timer.stop();
					setVisible(false);
				}
			}
			// ENTROU NA SEGUNDA PORTA
		} else if (salaPorta2) {
			portaComum1.setVisible(false);
			portaComum2.setVisible(false);
			portaComum3.setVisible(false);
			porao.setVisible(false);
			lbZezin.setVisible(false);
			portaVerde.setVisible(true);

			checkColisaoBorda(larguraFrame - 150, 150, alturaFrame - 75, 60);

			imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoCheckpoint.png");
			lbChaoFundo.setIcon(imgChaoFundo);

			if (jogador.getY() >= 185 && jogador.getX() >= 260 && jogador.getX() <= 295) {
				salaPorta2 = false;
				salaPrinc = true;
				jogador.setX(278);
				jogador.setY(80);
				jogador.setyB(jogador.getY() + 25);
				jogador.setyB(jogador.getX());
			}

			// ENTROU NA TERCEIRA PORTA
		} else if (salaPorta3) {
			portaComum1.setVisible(false);
			portaComum2.setVisible(false);
			portaComum3.setVisible(false);
			porao.setVisible(false);
			lbZezin.setVisible(false);
			portaAzul.setVisible(true);

			checkColisaoBorda(larguraFrame - 150, 150, alturaFrame - 75, 60);

			imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoCheckpoint.png");
			lbChaoFundo.setIcon(imgChaoFundo);
			
			xZezin = 160;
			yZezin = 120;
			lbZezin.setBounds(xZezin, yZezin, larguraZezin, alturaZezin);
			
			// COLISAO COM ZEZIN
			String colisaoZezin = colisaoZezin(xZezin, yZezin, larguraZezin, alturaZezin);
			if (colisaoZezin == "pertoD" && jogador.getTecla() == 10) {
				jogador.setTecla(0);
				if (liberaEnter) {
					new balaoDialogFadeOut().start();
					new dialogoUpdate().start();
					liberaEnter = false;
				}
			}

			if (jogador.getY() >= 185 && jogador.getX() >= 260 && jogador.getX() <= 295) {
				salaPorta3 = false;
				salaPrinc = true;
				jogador.setX(464);
				jogador.setY(80);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
			}
			
			if (dao.pixel.getPixelB() == 1) {
				portaAzul.setVisible(false);
				lbZezin.setVisible(true);
			} else {
				portaAzul.setVisible(true);
				// COLISAO COM A PORTA Azul
				String colisaoPortaColorida = checkColisao(xPortaColorida, yPortaColorida, larguraPortaColorida,
						alturaPortaColoria);
				if (colisaoPortaColorida != null && entrou == false) {
					entrou = true;
					salaPuzzleBlueBoss salaDaNeve = new salaPuzzleBlueBoss();
					salaDaNeve.setVisible(true);
					timer.stop();
					setVisible(false);
				}
			}
		}

		movimentacaoPet();
		jogador.atualizar();
		repaint();
	}

	public void print() {
		System.out.println("ID: " + dao.pixel.getId());
		System.out.println("NOME: " + dao.pixel.getName());
		System.out.println("GENERO: " + dao.pixel.getGenero());
		System.out.println("FASE:" + dao.pixel.getCheckpoint());
		System.out.println("PIXEL RED: " + dao.pixel.getPixelR());
		System.out.println("PIXEL GREEN: " + dao.pixel.getPixelG());
		System.out.println("PIXEL BLUE: " + dao.pixel.getPixelB());
		System.out.println("ALIADO 1: " + dao.pixel.getAliado1());
		System.out.println("ALIADO 2: " + dao.pixel.getAliado2());
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
				&& ladoBaixoA <= ladoBaixoB - 40) {
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

	public String colisaoZezin(int xB, int yB, int larguraB, int alturaB) {
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
		int ladoBaixoB = bY + alturaB - 2;
		int ladoCimaB = bY + 6;

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

		} else if (ladoEsquerdoA >= ladoDireitoB - 2 && ladoEsquerdoA <= ladoDireitoB + 4 && ladoBaixoA >= ladoCimaB
				&& ladoCimaA <= ladoBaixoB) {
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

	public void close() {
		WindowEvent winClose = new WindowEvent(this, WindowEvent.WINDOW_CLOSING);
		Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(winClose);
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

			if (!jogando) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				jogando = true;
			}
			if (jogador.isAndar()) {
				jogador.keyPressed(e);
			}
		}

		public void keyReleased(KeyEvent e) {
			jogador.keyReleased(e);
		}
	}

	private class Tremer extends Thread {
		public void run() {
			try {
				long sleepTime = 20;
				int originalX = getLocation().x;
				int originalY = getLocation().y;

				for (int i = 0; i <= 2; i++) {
					setLocation(originalX + 2, originalY);
					Thread.sleep(sleepTime);
					setLocation(originalX + 2, originalY + 2);
					Thread.sleep(sleepTime);
					setLocation(originalX, originalY + 2);
					Thread.sleep(sleepTime);
					setLocation(originalX, originalY);
					Thread.sleep(sleepTime);
					setLocation(originalX - 2, originalY);
					Thread.sleep(sleepTime);
					setLocation(originalX - 2, originalY - 2);
					Thread.sleep(sleepTime);
					setLocation(originalX, originalY - 2);
					Thread.sleep(sleepTime);
				}
				interrupt();
				setLocation(originalX, originalY);

			} catch (Exception ex) {
				System.out.println(ex.toString());
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
			dialogoDoGuri.setVisible(false);
			jogador.setAndar(true);
		}
	}

	private class dialogoInicial extends Thread {
		public void run() {
			jogador.setAndar(false);
			podePular = true;
			palavra = "";
			try {
				jogador.setX(70);
				jogador.setY(130);
				System.out.println(dao.pixel.getCheckpoint());
				if (dao.pixel.getCheckpoint().equals("1")) {
					if (!firstDialog) {
						for (int z = 0; z < TextoInicial[0].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[0], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[0]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[1].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[1], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[1]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[2].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[2], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[2]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[3].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[3], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[3]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[4].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[4], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[4]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[5].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[5], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[5]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(3000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[6].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[6], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[6]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(3000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[7].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[7], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[7]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;
						sleep(3000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[8].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[8], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[8]);
								pularDialog = false;
								break;
							}
						}

						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[9].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[9], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[9]);
								pularDialog = false;
								break;
							}
						}

						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[10].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[10], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[10]);
								pularDialog = false;
								break;
							}
						}

						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[11].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[11], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[11]);
								pularDialog = false;
								break;
							}
						}

						podePular = false;
						sleep(2000);
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[12].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[12], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[12]);
								pularDialog = false;
								break;
							}
						}

						sleep(2000);
						podePular = false;
						liberaEnter = true;
						firstDialog = true;
						new balaoDialogFadeIn().start();

					} else if (firstDialog) {
						podePular = true;
						palavra = "";
						for (int z = 0; z < TextoInicial[13].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoInicial[13], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoInicial[13]);
								pularDialog = false;
								break;
							}
						}
						sleep(2000);
						podePular = false;
						liberaEnter = true;
						new balaoDialogFadeIn().start();
					}

				}else {
					podePular = true;
					palavra = "";
					for (int z = 0; z < TextoInicial[13].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoInicial[13], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoInicial[13]);
							pularDialog = false;
							break;
						}
					}
					sleep(2000);
					podePular = false;
					liberaEnter = true;
					new balaoDialogFadeIn().start();
				}
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	private class dialogoUpdate extends Thread {
		public void run() {
			jogador.setAndar(false);
			podePular = true;
			palavra = "";
			try {
				if (dao.pixel.getPixelR() == 1 && salaPorta1 == true || dao.pixel.getPixelB() == 1 && salaPorta3 == true) {
					jogador.setX(195);
					jogador.setY(120);
					System.out.println(dao.pixel.getCheckpoint());
					dao.atualizarInventario(2);
					dao.atualizar(2);
					dao.buscar();
					dao.pixel.getCheckpoint();
//					if(dao.pixel.getCheckpoint().equals("1") || dao.pixel.getCheckpoint().equals("2") || dao.pixel.getCheckpoint().equals("3")) {
					for (int z = 0; z < TextoUpdate[1].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoUpdate[1], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoUpdate[1]);
							pularDialog = false;
							break;
						}
					}
					podePular = false;
					sleep(2000);
					podePular = true;
					palavra = "";
					for (int z = 0; z < TextoUpdate[0].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoUpdate[0], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoUpdate[0]);
							pularDialog = false;
							break;
						}
					}
					podePular = false;
					sleep(2000);
					liberaEnter = true;
					new balaoDialogFadeIn().start();
				} else {
					jogador.setX(195);
					jogador.setY(120);
					System.out.println(dao.pixel.getCheckpoint());
					dao.atualizarInventario(2);
					dao.atualizar(2);
					dao.buscar();
					dao.pixel.getCheckpoint();
//				if(dao.pixel.getCheckpoint().equals("1") || dao.pixel.getCheckpoint().equals("2") || dao.pixel.getCheckpoint().equals("3")) {
					for (int z = 0; z < TextoUpdate[0].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoUpdate[0], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoUpdate[0]);
							pularDialog = false;
							break;
						}
					}
					sleep(2000);
					podePular = false;
					liberaEnter = true;
					new balaoDialogFadeIn().start();
//				}
				}
			} catch (InterruptedException ex) {
				System.out.println(ex);
			}
		}
	}

	public static void main(String args[]) {
		new jogo2SalaPrinc();
	}
}
