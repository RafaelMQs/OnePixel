import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class telaInicial extends JFrame {
	private JLabel fundoLogo;
	private JButton iniciarJogo, fecharJogo;
	
	
	public telaInicial() {
		Componentes();
		Eventos();
		Tela();
	}
	
	public void Tela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600,250); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		setVisible(true);
		getContentPane().setBackground(Color.BLACK);
		//getContentPane().add(fundoLogo);
	}
	
	public void Componentes() {
		setLayout(null);
		// INICIO - Definir Fundo
		//fundoLogo = new JLabel(new ImageIcon("res//logoONEpixel.png"));
		//fundoLogo.setBounds(0, 0, 600, 250);
		// FIM - Definir Fundo
		
		// INICIO - Definir Botões
		iniciarJogo = new JButton("INICIAR JOGO");
		iniciarJogo.setBounds(200, 100, 200, 30);
		iniciarJogo.setFocusable(false);
		add(iniciarJogo);
		
		fecharJogo = new JButton("FECHAR JOGO");
		fecharJogo.setBounds(200, 150, 200, 30);
		fecharJogo.setFocusable(false);
		add(fecharJogo);
		// FIM - Definir Botões
		
	}
	
	public void Eventos() {
		// Botão de Fechar o Jogo
		fecharJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pergunta se ele tem certeza de que deseja fechar o jogo
				int resposta = JOptionPane.showConfirmDialog(null, "Tem Certeza ?", "Deseja Sair?", JOptionPane.YES_NO_OPTION);
		
				if (resposta == JOptionPane.YES_OPTION) {
					System.exit(0);
				}else {
					return;
				}
			}
		});
		
		// Adicionando evento no teclado
		addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				// Fecha o jogo com o ESC
				if(e.getKeyChar() == KeyEvent.VK_ESCAPE) {
					System.exit(0);
				}
				
			}
		});
	}
	
	

	
	
	
	public static void main (String args[]) {
		new telaInicial();
	}
}
