package OnePixel;

import javax.swing.*;
import BancoDeDados.onePixelDAO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.Iterator;


public class salaPuzzleBlueBoss extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	private Timer timer;
	private JPanel panel;
	
	// FALA DO SAPO
	
	private String[] falaDoSapo = {"<html>SAPO: Então você quer me desafiar...?",
			"<html>SAPO: Vem pro x1"};
	
	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	
	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	boolean pularDialog = false, podePular = false ,morreu = false;;
	String palavra = "";
	
	// FUNDO DA TELA (CENÁRIO) IMG - JLABEL
	ImageIcon imgFundo;
	JLabel lbFundo;
	
	//SAPO - BOSS
	
	ImageIcon imgSapo;
	Image sapo;
	JLabel dialogoDoSapo;
	private int sapoX = 242, sapoY = 30;
	
	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;
	
	// LOCAL AONDE ELE ESTA
	int localTerreno = 0;
			
	//Exibir cena em uma unica vez
	boolean fazerUmaVez = true;

	
	
	
	
	
	public salaPuzzleBlueBoss() {
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
				if(localTerreno == 1) {
					grafico.drawImage(sapo,sapoX,sapoY,111,130,this);
				}
				grafico.drawImage(jogador.getImgPixelRed(), jogador.getxR(), jogador.getyR(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelGreen(), jogador.getxG(), jogador.getyG(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelBlue(), jogador.getxB(), jogador.getyB(), 32, 32, this);
				grafico.dispose();
			}
		};
		panel.setBounds(0, 0, 600, 310);
		add(panel);
		
		//INICIANDO JOGADOR
		jogador = new Jogador();
		jogador.carregar();
		jogador.setX(278);
		jogador.setY(140);
		movimentacaoPet();
		


		
		dialogoDoSapo = new JLabel();
		dialogoDoSapo.setForeground(Color.BLACK);
		dialogoDoSapo.setFont(new Font("Pixel Operator 8", Font.PLAIN, 10));
		dialogoDoSapo.setVisible(false);
		panel.add(dialogoDoSapo);
		
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
		

		
		
		timer = new Timer(6, this);
		timer.start();
		
		addKeyListener(new Teclado());


		
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// SAPO - BOSS
		imgSapo = new ImageIcon("res2/imgPuzzleBlueBoss/sapoNormal.gif");
		sapo = imgSapo.getImage();
		
		if( localTerreno == 0) { 		
			// IMG FUNDO
			imgFundo = new ImageIcon("res2/imgPuzzleBlueBoss/CenarioAzul1.png");
			lbFundo.setIcon(imgFundo);
			colisao(0,0,230,310);  // Barreira direita
			colisao(365,0,230,310);// Barreira esquerda
			colisao(0,195,600,20); // Barreira de baixo
		}else {
			// IMG FUNDO
			imgFundo = new ImageIcon("res2/imgPuzzleBlueBoss/CenarioAzul2.png");
			lbFundo.setIcon(imgFundo);
			colisao(0,0,600,80);    // Barreira de cima
			colisao(0,0,100,310);   // Barreira esquerda
			colisao(485,0,100,310); // Barreira direita
			
			//Barreira esquerda baixo
			colisao(100,260,100,80);
			colisao(200,290,30,20);
			
			//Barreira direita baixo
			colisao(400,260,100,80);
			colisao(360,290,40,20);
			
			//Sapo - Boss
			colisao(0,0,600,250);
			
			if(checkColisao(0,0,600,260) != null && fazerUmaVez != false ) {
				fazerUmaVez = false;
				jogador.setAndar(false);
				dialogoDoSapo.setBounds(115, 40, 130, 50);
				lbBalaoDialog.setBounds(110, 40, 130, 60);
			
                new dialogo_Movimento_Do_sapo().start();
                new balaoDialogFadeOut().start();
                

               
			}
			
		}
		
		// VOLTA SALA 1 - BAIXO
		if (jogador.getY() >= 280 && jogador.getX() >= 165 && jogador.getX() <= 385) {
			localTerreno = 0;
			jogador.setY(0);

			// VAI SALA 7 - CIMA
		} else if (jogador.getY() <= -20 && jogador.getX() >= 165 && jogador.getX() <= 385) {
			localTerreno = 1;
			jogador.setY(255);
		}
		
		movimentacaoPet();
	    jogador.atualizar();
	    repaint();
		
	}
	
	private class dialogo_Movimento_Do_sapo extends Thread {
		public void run() {
			try {
				for(int z = 0 ; z < falaDoSapo[0].length();z++) {
					TextEffect(falaDoSapo[0],dialogoDoSapo,z,55);
				}
				sleep(3000);
				palavra = "";
				for(int z = 0 ; z < falaDoSapo[1].length();z++) {
					TextEffect(falaDoSapo[1],dialogoDoSapo,z,55);
				}
				sleep(3000);
				palavra = "";
				dialogoDoSapo.setVisible(false);
				new balaoDialogFadeIn().start();
				sleep(2000);
				//jogador.setAndar(false);
				int i = 0;
				while( i < 10) {
					jogador.setY(jogador.getY()+i);
					sleep(60);
					i++;
				}

				

				
			} catch (Exception e) {
				// TODO: handle exception
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
	
	// FECHA O BALÃO
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
	
	// ABRIR O BALÃO
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
	
	public static void main(String args[]) {
		new salaPuzzleBlueBoss();
	}
	 

}
