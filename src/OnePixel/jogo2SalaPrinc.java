package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;

import javax.swing.*;

import BancoDeDados.*;

public class jogo2SalaPrinc extends JPanel implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	private int xPorao1 = 270, yPorao1 = 170, larguraPorao1 = 60, alturaPorao1 = 60;
	private Timer timer;
	private ImageIcon imgPorao;
	
	 // BOOLEAN DE COLISAO E DE PET
//	private boolean colidiu = false, mudarPet = true;

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

//	IMAGENs DE FUNDO
	// FUNDO
	Image fundo;
	// CHAO
	Image chaoSala;
	
// 	IMAGENs DAS PORTAS
	// PORAO
	Image porao;

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
		ImageIcon gifFundo = new ImageIcon("res2/imgSalaPrincipal/background.png");
		fundo = gifFundo.getImage();

//	IMAGENs DAS PORTAS
		// PORAO
		imgPorao = new ImageIcon("res2/imgPortas/porao00.png");
		porao = imgPorao.getImage();
		
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
		if(!dao.bd.connection()){ //verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null,"Falha na conexão!");
			System.exit(0);
		}	
	}

	public void eventos() {

	}

	public void paint(Graphics g) {
		Graphics2D grafico = (Graphics2D) g;
		grafico.drawImage(fundo, 0, 0, 600, 310, null);
		grafico.drawImage(chaoSala, 0, 0, 600, 310, null);
		grafico.drawImage(porao, xPorao1, yPorao1, larguraPorao1, alturaPorao1, null);
		grafico.drawImage(jogador.getImgPlayer(), jogador.getX(), jogador.getY(), jogador.getLargura(),
				jogador.getAltura(), this);
		grafico.drawImage(jogador.getImgPet(), jogador.getXB(), jogador.getYB(), 32, 32, this);
		grafico.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		checkColisaoBorda(larguraFrame - 30, 30, alturaFrame - 75, 60);

		String retorno = checkColisao(xPorao1, yPorao1, larguraPorao1, alturaPorao1);
		if (retorno == "dentro" && jogador.getTecla() == 10) {
			System.out.println("entrando");
			jogador.setTecla(0);
			System.out.println("ID: "+dao.pixel.getId());
    		System.out.println("NOME: "+dao.pixel.getName());
    		System.out.println("GENERO: "+dao.pixel.getGenero());
    		System.out.println("FASE:"+dao.pixel.getCheckpoint());
    		System.out.println("PIXEL RED: "+dao.pixel.getPixelR());
    		System.out.println("PIXEL GREEN: "+dao.pixel.getPixelG());
    		System.out.println("PIXEL BLUE: "+dao.pixel.getPixelB());
    		System.out.println("ALIADO 1: "+dao.pixel.getAliado1());
     		System.out.println("ALIADO 2: "+dao.pixel.getAliado2());
     		if(dao.pixel.getPixelR() == 1 && dao.pixel.getPixelG() == 1 && dao.pixel.getPixelB() == 1) {
     			imgPorao = new ImageIcon("res2/imgPortas/porao01.png");
     			porao = imgPorao.getImage();
     		}else{
     			JOptionPane.showMessageDialog(this, "Colete os outros PIXELS!");
     		}
		}
		
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
		}else if(ladoDireitoA >= ladoEsquerdoB + 10 && ladoEsquerdoA <= ladoDireitoB - 10 && ladoBaixoA >= ladoCimaB
				&& ladoBaixoA <= ladoBaixoB) {
			return "dentro";
			
		}
		return null;
	}

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				try {dao.bd.c.close();} catch (SQLException e1) {e1.printStackTrace();}
				System.exit(0);
			}
			jogador.keyPressed(e);
		}

		public void keyReleased(KeyEvent e) {
			jogador.keyReleased(e);
		}
	}
}

