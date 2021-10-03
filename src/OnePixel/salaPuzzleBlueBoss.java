package OnePixel;

import javax.swing.*;
import BancoDeDados.onePixelDAO;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.*;
import java.sql.SQLException;


public class salaPuzzleBlueBoss extends JFrame implements ActionListener {
	private Jogador jogador;
	private onePixelDAO dao;
	private Timer timer;
	private JPanel panel;
	
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
	
	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;
	
	// LOCAL AONDE ELE ESTA
	int localTerreno = 0;
	
	
	
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
				grafico.drawImage(jogador.getImgPixelRed(), jogador.getxR(), jogador.getyR(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelGreen(), jogador.getxG(), jogador.getyG(), 32, 32, this);
				grafico.drawImage(jogador.getImgPixelBlue(), jogador.getxB(), jogador.getyB(), 32, 32, this);
				grafico.dispose();
			}
		};
		panel.setBounds(0, 0, 600, 310);
		add(panel);
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public static void main(String args[]) {
		new salaPuzzleBlueBoss();
	}
	 

}
