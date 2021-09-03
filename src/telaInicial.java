import java.awt.*;
import java.awt.event.*;
import java.security.Key;

import javax.swing.*;

public class telaInicial extends JFrame implements MouseListener, KeyListener {
	private JLabel fundoLogo;
	private JButton iniciarJogo, fecharJogo;

	public telaInicial() {
		Componentes();
		Eventos();
		Tela();
	}

	public void Tela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 250); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		setVisible(false);
		getContentPane().setBackground(Color.BLACK);
		// getContentPane().add(fundoLogo);
	}

	public void Componentes() {
		setLayout(null);
		addKeyListener(this);
		// INICIO - Definir Fundo
		// fundoLogo = new JLabel(new ImageIcon("res//logoONEpixel.png"));
		// fundoLogo.setBounds(0, 0, 600, 250);
		// FIM - Definir Fundo

		// INICIO - Definir Botões
		iniciarJogo = new JButton("INICIAR JOGO");
		iniciarJogo.setBounds(200, 100, 200, 30);
		iniciarJogo.addMouseListener(this); // Adicionando um evento de mouse ( para algum hover talvez )
		iniciarJogo.setFocusable(false);
		add(iniciarJogo);

		fecharJogo = new JButton("FECHAR JOGO");
		fecharJogo.setBounds(200, 150, 200, 30);
		fecharJogo.addMouseListener(this); // Adicionando um evento de mouse ( para algum hover talvez )
		fecharJogo.setFocusable(false);
		add(fecharJogo);
		// FIM - Definir Botões

	}

	public void Eventos() {
		// Botão de Fechar o Jogo
		fecharJogo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Pergunta se ele tem certeza de que deseja fechar o jogo
				int resposta = JOptionPane.showConfirmDialog(null, "Tem Certeza ?", "Deseja Sair?",
						JOptionPane.YES_NO_OPTION);

				if (resposta == JOptionPane.YES_OPTION) {
					System.exit(0);
				} else {
					return;
				}
			}
		});
	}

	public static void main(String args[]) {
		new telaInicial();
	}

	

	// Criando evento no mouse ( Caso queira adicionar um Hover no botão por exemplo)
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}
	// Aumenta os botões quando o mouse estiver em cima
	@Override
	public void mouseEntered(MouseEvent e) {
		if (e.getSource() == iniciarJogo) {
			iniciarJogo.setBounds(iniciarJogo.getX() - 6, iniciarJogo.getY() - 6, iniciarJogo.getWidth() + 12, iniciarJogo.getHeight() + 12);
		} else if (e.getSource() == fecharJogo) {
			fecharJogo.setBounds(fecharJogo.getX() - 6, fecharJogo.getY() - 6, fecharJogo.getWidth() + 12, fecharJogo.getHeight() + 12);
		}
	}

	// Diminui os botões quando o mouse sair
	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == iniciarJogo) {
			iniciarJogo.setBounds(iniciarJogo.getX() + 6, iniciarJogo.getY() + 6, iniciarJogo.getWidth() - 12, iniciarJogo.getHeight() - 12);
		} else if (e.getSource() == fecharJogo) {
			fecharJogo.setBounds(fecharJogo.getX() + 6, fecharJogo.getY() + 6, fecharJogo.getWidth() - 12, fecharJogo.getHeight() - 12);
		}
	}

	
	// Adicionando evento no teclado
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// Fecha o jogo com o ESC
		if (e.getKeyChar() == KeyEvent.VK_ESCAPE) {
			System.exit(0);
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
