import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class teste extends JFrame implements KeyListener {
	
	String Dialogo1[] = { "Oi Testando", "Teste 2", "Michel Legal"};
	JLabel Dialogo, img;
	boolean resposta = true;
	
	String palavra = "";

	public teste() {
		Frame();
		Componentes();
		Eventos();
	}

	public void Componentes() {
		addKeyListener(this);
		Dialogo = new JLabel();
		Dialogo.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		add(Dialogo);
		
		img = new JLabel(new ImageIcon("res/prologo//000.gif"));
		add(img);

		for (int i = 0; i < Dialogo1.length; i++){
			palavra = "";
			for (int z = 0; z < Dialogo1[i].length(); z++) {
				if (resposta) {
					TextEffect(Dialogo1, Dialogo, i, z);
				}else {
					z = Dialogo1[i].length();
					resposta = true;
				}
			}
		}
	}

	public void Eventos() {

	}

	public void Frame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 310); // tamanho do JFrame
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null); // tela fica centralizado

	}

	public static void main(String args[]) {
		new teste();

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == KeyEvent.VK_SPACE) {
			resposta = false;
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}
	
	public void TextEffect (String DialogoBox[], JLabel lbDialogo, int i, int z) {
		try {
			char letra = DialogoBox[i].charAt(z);
			palavra = palavra + letra;
			lbDialogo.setText(palavra);
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			System.out.println("Errou");
		}
	}
}

