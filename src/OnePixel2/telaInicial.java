package OnePixel2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class telaInicial extends JFrame {

//	LARGURA E ALTURA DO FRAME
	int larguraFrame = 600;
	int alturaFrame = 310;
	
//	IMGs DO FUNDO E DA LOGO
	ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	ImageIcon imgFundo = new ImageIcon("res2/imgTelaInicial/NewBackgroundTela.gif");
	private JLabel gifFundo;

// IMGs DOS BTNs DA TELA DE INICIO
	ImageIcon imgBtnSair = new ImageIcon("./res2/imgTelaInicial/Sair-NoSelected.png");
	ImageIcon imgBtnJogar = new ImageIcon("./res2/imgTelaInicial/Jogar-NoSelected.png");
	ImageIcon imgBtnCarregar = new ImageIcon("./res2/imgTelaInicial/Carregar-NoSelected.png");
	ImageIcon imgBtnInstrucao = new ImageIcon("./res2/imgTelaInicial/Instrucoes-NoSelected.png");
	// BTNs DA TELA DE INICO
	private JButton btnSair, btnJogar, btnCarregar, btnInstrucao, btnConfirmUsu;

// VAR PARA VER SE ELE ESTA NA TELA DE INSTRUCAO OU NAO
	boolean verificInstrucao = false, verificJogar = false;
	
// LABELs DA TELA DE INSTRUCAO
	JLabel comandosInstrucao;
	
// TF DA TELA JOGAR
	JTextField nomeUsu;
		
	public telaInicial() {
		componentes();
		eventos();
		frame();
	}

	public void componentes() {
		setLayout(null);

// BOTÕES DA TELA INICIAL
		// DEFININDO BTNs SAIR
		btnSair = new JButton(imgBtnSair);
		btnSair.setBounds(516, 8, 75, 36);
		btnSair.setBorder(null);
		btnSair.setContentAreaFilled(false);
		btnSair.setFocusable(false);
		add(btnSair);
		// DEFININDO BTNs JOGAR
		btnJogar = new JButton(imgBtnJogar);
		btnJogar.setBounds(55, 165, 173, 23);
		btnJogar.setBorder(null);
		btnJogar.setContentAreaFilled(false);
		btnJogar.setFocusable(false);
		add(btnJogar);
		// DEFININDO BTNs CARREGAR
		btnCarregar = new JButton(imgBtnCarregar);
		btnCarregar.setBounds(55, 201, 173, 23);
		btnCarregar.setBorder(null);
		btnCarregar.setContentAreaFilled(false);
		btnCarregar.setFocusable(false);
		add(btnCarregar);
		// DEFININDO BTN INSTRUCOES
		btnInstrucao = new JButton(imgBtnInstrucao);
		btnInstrucao.setBounds(55, 234, 173, 23);
		btnInstrucao.setBorder(null);
		btnInstrucao.setContentAreaFilled(false);
		btnInstrucao.setFocusable(false);
		add(btnInstrucao);

// JLABELs DA TELA INSTRUCAO
		comandosInstrucao = new JLabel("<html> <center> <h1> COMANDOS: </h1> </center> <br> <p> SETAS: Andar || ESC: Fechar Game <br><br>"
				+ "ESPAÇO: Pula/Completa dialogo <br><br>"
				+ "ENTER: Confirma ações <br><br>"
				+ " Z: Volta para o menu anterior </p> </html>");
		comandosInstrucao.setFont(new Font("Pixel Operator 8", Font.PLAIN, 14));
		comandosInstrucao.setForeground(Color.black);
		comandosInstrucao.setBounds(125, -60, 380, 400);
		comandosInstrucao.setVisible(false);
		add(comandosInstrucao);

// COMPONENTES DA TELA JOGAR
		// TF DA TELA JOGAR
		nomeUsu = new JTextField();
		nomeUsu.setBounds(55, 165, 173, 23);
		nomeUsu.setVisible(false);
		add(nomeUsu);
		
		// BTN CONFIRMAR JOGAR
		btnConfirmUsu = new JButton("Confirmar");
		btnConfirmUsu.setBounds(55, 201, 173, 23);
		btnConfirmUsu.setVisible(false);
		add(btnConfirmUsu);
		
		// DEFININDO FUNDO
		gifFundo = new JLabel(imgFundo);
		gifFundo.setBounds(0, 0, 600, 310);
		add(gifFundo);
		
	}

	public void eventos() {
		// ADICIONA EVENTOS AO BOTAO SAIR
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				imgBtnSair = new ImageIcon("res2/imgTelaInicial/Sair-Selected.png");
				btnSair.setIcon(imgBtnSair);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				imgBtnSair = new ImageIcon("res2/imgTelaInicial/Sair-NoSelected.png");
				btnSair.setIcon(imgBtnSair);
			}
		});

		// ADICIONA EVENTO AO BOTAO JOGAR
		btnJogar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnJogar.setVisible(false);
				btnCarregar.setVisible(false);
				btnInstrucao.setVisible(false);
				verificJogar = true;
				nomeUsu.setVisible(true);
				btnConfirmUsu.setVisible(true);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				imgBtnJogar = new ImageIcon("res2/imgTelaInicial/Jogar-Selected.png");
				btnJogar.setIcon(imgBtnJogar);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				imgBtnJogar = new ImageIcon("res2/imgTelaInicial/Jogar-NoSelected.png");
				btnJogar.setIcon(imgBtnJogar);
			}
		});

		// ADICIONA EVENTO AO BOTAO CARREGAR
		btnCarregar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				imgBtnCarregar = new ImageIcon("res2/imgTelaInicial/Carregar-Selected.png");
				btnCarregar.setIcon(imgBtnCarregar);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				imgBtnCarregar = new ImageIcon("res2/imgTelaInicial/Carregar-NoSelected.png");
				btnCarregar.setIcon(imgBtnCarregar);
			}
		});

		// ADICIONA EVENTO AO BOTAO INSTRUCAO
		btnInstrucao.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnJogar.setVisible(false);
				btnCarregar.setVisible(false);
				btnInstrucao.setVisible(false);
				btnSair.setVisible(false);
				new openTelaIntroducao().start();
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				imgBtnInstrucao = new ImageIcon("res2/imgTelaInicial/Instrucoes-Selected.png");
				btnInstrucao.setIcon(imgBtnInstrucao);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				imgBtnInstrucao = new ImageIcon("res2/imgTelaInicial/Instrucoes-NoSelected.png");
				btnInstrucao.setIcon(imgBtnInstrucao);
			}
		});
	 
		
	}

	public void frame() {
		setIconImage(imgLogo.getImage());
		setTitle("OnePixel - Part2");
		setSize(larguraFrame, alturaFrame);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);

		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}if (e.getKeyCode() == KeyEvent.VK_Z && verificInstrucao == true) {
					 new closeTelaIntroducao().start();
				}else if (e.getKeyCode() == KeyEvent.VK_Z && verificJogar == true) {
					btnJogar.setVisible(true);
					btnCarregar.setVisible(true);
					btnInstrucao.setVisible(true);
					nomeUsu.setVisible(false);
					btnConfirmUsu.setVisible(false);
					verificJogar = false;
				}
			}
		});

	}

	public static void main(String args[]) {
		new telaInicial();
	}
	
	public class openTelaIntroducao extends Thread{
		public void run() {
			imgFundo = new ImageIcon("res2/imgTelaInicial/01FadeInInstru.gif");	
			gifFundo.setIcon(imgFundo);
			try {Thread.sleep(3000);} catch (InterruptedException e1) {e1.printStackTrace();}
			comandosInstrucao.setVisible(true);
//			imgFundo = new ImageIcon("res2/imgTelaInicial/01FadeInInstru.gif");
			verificInstrucao = true;
		}
	}
	
	public class closeTelaIntroducao extends Thread{
		public void run() {
			comandosInstrucao.setVisible(false);		
			imgFundo = new ImageIcon("res2/imgTelaInicial/02FadeOutInstru.gif");
			gifFundo.setIcon(imgFundo);
			try {Thread.sleep(1800);} catch (InterruptedException e1) {e1.printStackTrace();}
			verificInstrucao = false;
			imgFundo = new ImageIcon("res2/imgTelaInicial/NewBackgroundTela.gif");
			gifFundo.setIcon(imgFundo);
			btnJogar.setVisible(true);
			btnCarregar.setVisible(true);
			btnInstrucao.setVisible(true);
			btnSair.setVisible(true);
		}
	}

}