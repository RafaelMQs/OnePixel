package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import BancoDeDados.*;

public class jogo2SalaPrinc extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	boolean trocarTela = true;

	JPanel panel;
	JLabel jogadorzin;
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
//		setDoubleBuffered(true);

// IMAGENs DAS PORTAS
		// PORTA COMUM
		
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
		if (salaPrinc) {
			portaAzul.setVisible(false);
			portaVermelha.setVisible(false);
			portaVerde.setVisible(false);

			portaComum1.setVisible(true);
			portaComum2.setVisible(true);
			portaComum3.setVisible(true);
			porao.setVisible(true);

			imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoSalaPrincipal.png");
			lbChaoFundo.setIcon(imgChaoFundo);

			// COLISÃO COM A BORDA
			checkColisaoBorda(larguraFrame - 30, 30, alturaFrame - 75, 60);

			// COLISÃO COM O PORAO
			String retorno = checkColisao(xPorao1, yPorao1, larguraPorao1, alturaPorao1);
			if (retorno == "dentro" && jogador.getTecla() == 10) {
				jogador.setTecla(0);
				if (dao.pixel.getPixelR() == 1 && dao.pixel.getPixelG() == 1 && dao.pixel.getPixelB() == 1) {
					System.out.println("Entrando");
					imgPorao = new ImageIcon("res2/imgPortas/porao01.png");
					porao.setIcon(imgPorao);
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

			if (jogador.getY() >= 185 && jogador.getX() >= 260 && jogador.getX() <= 295) {
				salaPorta1 = false;
				salaPrinc = true;
				jogador.setX(95);
				jogador.setY(80);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
			}

			if (trocarTela) {
				setVisible(false);
				trocarTela = false;
			}

			// ENTROU NA SEGUNDA PORTA
		} else if (salaPorta2) {
			portaComum1.setVisible(false);
			portaComum2.setVisible(false);
			portaComum3.setVisible(false);
			porao.setVisible(false);

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

			portaAzul.setVisible(true);

			checkColisaoBorda(larguraFrame - 150, 150, alturaFrame - 75, 60);

			imgChaoFundo = new ImageIcon("res2/imgSalaPrincipal/chaoCheckpoint.png");
			lbChaoFundo.setIcon(imgChaoFundo);

			if (jogador.getY() >= 185 && jogador.getX() >= 260 && jogador.getX() <= 295) {
				salaPorta3 = false;
				salaPrinc = true;
				jogador.setX(464);
				jogador.setY(80);
				jogador.setyB(jogador.getY() + 25);
				jogador.setxB(jogador.getX());
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

	public void movimentacaoPet() {
		if(jogador.isCima() ) {
			// PIXEL VERMELHO
			jogador.setyR(jogador.getY() + 25);
			jogador.setxR(jogador.getX() - 15);
			
			// PIXEL VERDE
			jogador.setyG(jogador.getY() + 30);
			jogador.setxG(jogador.getX() + 6);
			
			// PIXEL AZUL
			jogador.setyB(jogador.getY() + 25);
			jogador.setxB(jogador.getX() + 28);
			
		}else if(jogador.isBaixo()) {
			// PIXEL VERMELHO
			jogador.setyR(jogador.getY() - 20);
			jogador.setxR(jogador.getX() + 30);

			// PIXEL VERDE
			jogador.setyG(jogador.getY() - 25);
			jogador.setxG(jogador.getX() + 8);
			
			// PIXEL AZUL			
			jogador.setyB(jogador.getY() - 20);
			jogador.setxB(jogador.getX() - 15);
			
		}else if(jogador.isDireita()) {
			// PIXEL VERMELHO
			jogador.setxR(jogador.getX() - 18);	 
			jogador.setyR(jogador.getY() - 10);
					
			// PIXEL VERDE
			jogador.setxG(jogador.getX() - 25);	 
			jogador.setyG(jogador.getY() + 5);
			
			// PIXEL AZUL
			jogador.setxB(jogador.getX() - 18);	 
			jogador.setyB(jogador.getY() + 20);
			
		}else if(jogador.isEsquerda()) {
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
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				try {
					dao.bd.c.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
			jogador.keyPressed(e);
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

				setLocation(originalX, originalY);

			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	public static void main(String args[]) {
		new jogo2SalaPrinc();
	}
}
