package OnePixel;

import java.awt.Color;
import java.awt.Frame;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class telaMain extends JFrame {
	ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	jogo2SalaPrinc salaPrinc = new jogo2SalaPrinc();
	
	int larguraFrame = 600;
	int alturaFrame = 310;
	public telaMain() {
		setIconImage(imgLogo.getImage());
		salaPrinc.setVisible(true);
		add(salaPrinc);
		
		setBackground(Color.red);
		setTitle("OnePixel - Part2");
		setSize(larguraFrame, alturaFrame);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		setUndecorated(true);
		setVisible(true);	
	}
	
	public static void main (String args[]) {
		new telaMain();
	}
}
