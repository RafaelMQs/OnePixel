import java.awt.*;
import java.awt.event.*;
import java.security.Key;

import javax.swing.*;

public class telaInicial extends JFrame implements MouseListener, KeyListener {
	private JLabel fundoLogo;
	private JButton iniciarNoSelected, fecharNoSelected, iniciarSelected, fecharSelected;

	public telaInicial() {
		Componentes();
		Eventos();
		Tela();
	}

	public void Tela() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 310); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		setVisible(false);
		getContentPane().add(fundoLogo);
	}

	public void Componentes() {
		setLayout(null);
		addKeyListener(this);
		
		// INICIO - Definir Fundo
		fundoLogo = new JLabel(new ImageIcon("TelaInicial//BkTelaPrinc.gif"));
		fundoLogo.setBounds(0, 0, 600, 310);
		// FIM - Definir Fundo

		// INICIO - Definir Botões
		
		// BOTÃO INICIAR NÃO SELECIONADO
		iniciarNoSelected = new JButton(new ImageIcon("TelaInicial//iniciar01-noSelect.png"));
		iniciarNoSelected.setBounds(200, 180, 200, 30);
		iniciarNoSelected.addMouseListener(this); // Adicionando um evento de mouse ( para algum hover talvez )
		iniciarNoSelected.setFocusable(false);
		iniciarNoSelected.setContentAreaFilled(false);
		iniciarNoSelected.setBorderPainted(false);
		add(iniciarNoSelected);

		// BOTÃO INICIAR SELECIONADO
		iniciarSelected = new JButton(new ImageIcon("TelaInicial//iniciar01-Select.png"));
		iniciarSelected.setBounds(200, 180, 200, 30);
		iniciarSelected.addMouseListener(this); // Adicionando um evento de mouse ( para algum hover talvez )
		iniciarSelected.setFocusable(false);
		iniciarSelected.setContentAreaFilled(false);
		iniciarSelected.setBorderPainted(false);
		iniciarSelected.setVisible(false);
		add(iniciarSelected);
		
		// BOTÃO FECHAR NÃO SELECIONADO
		fecharNoSelected = new JButton(new ImageIcon("TelaInicial//fechar01-noSelect.png"));
		fecharNoSelected.setBounds(200, 230, 200, 30);
		fecharNoSelected.addMouseListener(this); // Adicionando um evento de mouse ( para algum hover talvez )
		fecharNoSelected.setFocusable(false);
		fecharNoSelected.setContentAreaFilled(false);
		fecharNoSelected.setBorderPainted(false);
		add(fecharNoSelected);
		
		// BOTÃO FECHAR SELECIONADO
		fecharSelected = new JButton(new ImageIcon("TelaInicial//fechar01-Select.png"));
		fecharSelected.setBounds(200, 230, 200, 30);
		fecharSelected.addMouseListener(this); // Adicionando um evento de mouse ( para algum hover talvez )
		fecharSelected.setFocusable(false);
		fecharSelected.setContentAreaFilled(false);
		fecharSelected.setBorderPainted(false);
		fecharSelected.setVisible(false);
		add(fecharSelected);
		// FIM - Definir Botões

	}

	public void Eventos() {
		// Botão de Fechar o Jogo
		fecharSelected.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// Objeto para trocaro Yes e o No do JOptionPane
				Object[] options = {"Sim", "Não"};
				// Pergunta se ele tem certeza de que deseja fechar o jogo
				int resposta = JOptionPane.showOptionDialog(null, "Tem Certeza ?", "Deseja Sair?",
						JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE,null, options, options[0]);

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
		if (e.getSource() == iniciarNoSelected) {
			iniciarNoSelected.setVisible(false);
			iniciarSelected.setVisible(true);
		} else if (e.getSource() == fecharNoSelected) {
			fecharNoSelected.setVisible(false);
			fecharSelected.setVisible(true);
		}
	}

	// Diminui os botões quando o mouse sair
	@Override
	public void mouseExited(MouseEvent e) {
		if (e.getSource() == iniciarSelected) {
			iniciarNoSelected.setVisible(true);
			iniciarSelected.setVisible(false);
		} else if (e.getSource() == fecharSelected) {
			fecharNoSelected.setVisible(true);
			fecharSelected.setVisible(false);
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
