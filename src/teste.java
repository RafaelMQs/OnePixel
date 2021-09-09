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
		
		img = new JLabel(new ImageIcon("prologo//000.gif"));
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

/*
 * 
 * import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.event.*;

/* hb Warning!!! Cada imagem estar em um JPanel
 * add(NomeDoJPanel) 

public class prologo extends JFrame implements KeyListener {
	private JPanel[] panel = new JPanel[11];
	private JLabel[] tela = new JLabel[11];

	private JLabel[] lbTexto = new JLabel[10];
	private String[] texto = { "<html> Era mais um dia normal no Mundo Virtual. </html>",
			"Pelo menos, era o que Guri achava...",
			"Ele estava discutindo com os seus pais, eles queriam que ele fizesse a faxina da casa.",
			"Guri odiava faxina, Então sem pensar duas vezes ele disse:",
			"“Se eu fosse a única pessoa nesse mundo tudo seria diferente”",
			"Após o último som de sua voz, tudo começou a tremer e o que estava ao seu redor foi consumido pela...",
			"ESCURIDÃO",
			"Guri, ficou 15 horas naquele breu. Ainda afobado na escuridão ele descobriu algo que o fez chorar. Ele percebeu que ele não aproveitou nada em sua vida, pois era apenas um egoísta.",
			"Enquanto Guri estava em total desespero... Ele ouve a seguinte frase:",
			"Você sabe quantas cores há no mundo virtual? Você consegue acendê-las?" };
	private int contTela0 = 0, contString = 0;
	private String palavra;
	private boolean resposta = false;

	public prologo() {
		// TODO Auto-generated constructor stub
		inicializarComponentes();
		frame();
		//new Temporizador().start();
	}

	public void inicializarComponentes() {
		addKeyListener(this);
		setLayout(null);
		for (int i = 0; i < panel.length; i++) {
			panel[i] = new JPanel(null);
			panel[i].setBackground(Color.WHITE);
			panel[i].setBounds(0, 0, 600, 310);
			panel[i].setVisible(false);
			add(panel[i]);
			tela[i] = new JLabel();
			tela[i].setBounds(0, 0, 600, 310);
		}

//		imagem(tela[0], "000", "gif");
//		panel[0].add(tela[0]);
//		panel[0].setVisible(true);

		for (int i = 0; i < lbTexto.length; i++) {
			lbTexto[i] = new JLabel(texto[i]);
			lbTexto[i].setForeground(Color.black);
			lbTexto[i].setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		}
		
		lbTexto[0].setBounds(50, 200, 500, 100);
		add(lbTexto[0]);
		

		palavra = "";
		for (int z = 0; z < texto[0].length(); z++) {
			try {
				Thread.sleep(200);
				char letra = texto[0].charAt(z);
				palavra = palavra + letra;
				lbTexto[0].setText(palavra);
			} catch (InterruptedException ex) {
			}
		}

	}
	
	public void frame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setBounds(0, 0, 600, 310); // tamanho do JFrame
		setUndecorated(true);
		setVisible(true);
		setLocationRelativeTo(null); // tela fica centralizado
    
	}

	public void imagem(JLabel nomeDaJLabel, String nomeDaImagem, String tipoDaImagem) {
		nomeDaJLabel.setIcon(new ImageIcon("prologo//" + nomeDaImagem + "." + tipoDaImagem + ""));
	}

	public static void main(String[] args) {
		new prologo();
	}

	public void TextEffect(String DialogoBox[], JLabel lbDialogo, int i, int z) {
		try {
			char letra = DialogoBox[i].charAt(z);
			palavra = palavra + letra;
			lbDialogo.setText(palavra);
			Thread.sleep(200);
		} catch (InterruptedException ex) {
			System.out.println("Errou");
		}
	}

	/*
	public class Temporizador extends Thread {
		public void run() {
			while (contTela0 < 100) {
				try {
					sleep(50);
					contTela0 += 1;
					// Quando chegar 100%, esta função chamarar outra tela.
					if (contTela0 >= 76) {
						panel[0].setVisible(false);
						add(panel[1]);
						panel[1].setVisible(true);
					}
				} catch (InterruptedException e) {
					e.printStackTrace();

				}
			}
		}
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

}
*/
