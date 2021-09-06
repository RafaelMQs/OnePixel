import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class teste extends JFrame {
	JButton a;

	public teste(){
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 677, 260); // tamanho do JFrame
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null); // tela fica centralizado

	}

	public static void main(String args[]){
		new teste();

	}
}
