package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.sql.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import javax.swing.table.*;

import BancoDeDados.*;

public class telaInicial extends JFrame {
	onePixelDAO dao;
	public PreparedStatement statement;
	public ResultSet resultado;
	private Clip SoundTrack;

//	LARGURA E ALTURA DO FRAME
	int larguraFrame = 600;
	int alturaFrame = 310;

//	TABELA	
	private JScrollPane scrollTable;
	private JTable table;

//	IMGs DO FUNDO E DA LOGO
	ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	ImageIcon imgFundo = new ImageIcon("res2/imgTelaInicial/NewBackgroundTela.gif");
	private JLabel gifFundo;

// IMGs DOS BTNs DA TELA DE INICIO
	ImageIcon imgBtnSair = new ImageIcon("./res2/imgTelaInicial/Sair-NoSelected.png");
	ImageIcon imgBtnJogar = new ImageIcon("./res2/imgTelaInicial/Jogar-NoSelected.png");
	ImageIcon imgBtnCarregar = new ImageIcon("./res2/imgTelaInicial/Carregar-NoSelected.png");
	ImageIcon imgBtnInstrucao = new ImageIcon("./res2/imgTelaInicial/Instrucoes-NoSelected.png");
	ImageIcon imgBtnConfirmar = new ImageIcon("./res2/imgTelaInicial/Confirmar-NoSelected.png");
	ImageIcon imgBtnVoltar = new ImageIcon ("./res2/imgTelaInicial/Voltar-NoSelected.png");
	// BTNs DA TELA DE INICO
	private JButton btnSair, btnJogar, btnCarregar, btnInstrucao, btnConfirmUsu, btnVoltar;

// VAR PARA VER SE ELE ESTA NA TELA DE INSTRUCAO OU NAO
	boolean verificInstrucao = false, verificOutraTela = false;

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
		comandosInstrucao = new JLabel(
				"<html> <center> <h1> COMANDOS: </h1> </center> <br> <p> SETAS: Andar || SHIFT: Corre"
				+ "<br><br> ESC: Fechar Game <br><br>"
						+ "ESPAÇO: Pula/Completa dialogo <br><br>" + "ENTER: Confirma ações <br><br>"
						+ " Z: Volta para o menu anterior </p> </html>");
		comandosInstrucao.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		comandosInstrucao.setForeground(Color.white);
		comandosInstrucao.setBounds(130, -55, 380, 400);
		comandosInstrucao.setVisible(false);
		add(comandosInstrucao);

// COMPONENTES DA TELA JOGAR
		// TF DA TELA JOGAR
		nomeUsu = new JTextField();
		nomeUsu.setBounds(55, 165, 173, 23);
		nomeUsu.setToolTipText("Nome de Usuario");
		nomeUsu.setBorder(null);
		nomeUsu.setVisible(false);
		add(nomeUsu);

		// BTN CONFIRMAR JOGAR
		btnConfirmUsu = new JButton(imgBtnConfirmar);
		btnConfirmUsu.setBounds(55, 201, 173, 23);
		btnConfirmUsu.setBorder(null);
		btnConfirmUsu.setContentAreaFilled(false);
		btnConfirmUsu.setVisible(false);
		add(btnConfirmUsu);

		// BTN VOLTATR (JOGAR)
		btnVoltar = new JButton(imgBtnVoltar);
		btnVoltar.setBounds(55, 234, 173, 23);
		btnVoltar.setBorder(null);
		btnVoltar.setContentAreaFilled(false);
		btnVoltar.setVisible(false);
		add(btnVoltar);

// COMPONENTES/TABELA DA TELA CARREGAR		
		// SCROLL
		scrollTable = new JScrollPane();
		scrollTable.setBounds(20, 150, 250, 100);
		scrollTable.setVisible(false);
		add(scrollTable);

// DEFININDO FUNDO
		gifFundo = new JLabel(imgFundo);
		gifFundo.setBounds(0, 0, 600, 310);
		add(gifFundo);

		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}
		
		File file = new File("SoundTrackInicial.wav");
		AudioInputStream audioStream;
		try {
			audioStream = AudioSystem.getAudioInputStream(file);
			SoundTrack = AudioSystem.getClip();
			SoundTrack.open(audioStream);
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		FloatControl gainControl = (FloatControl) SoundTrack.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-18.0f); 
		SoundTrack.start();
		
	}

	public void eventos() {
		// ADICIONA EVENTOS AO BOTAO SAIR
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {dao.bd.c.close();} catch (SQLException e1) {e1.printStackTrace();}
				System.exit(0);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try { audio("BtnHoverSong", -5, 0, 0); } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
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
				verificOutraTela = true;
				nomeUsu.setVisible(true);
				btnConfirmUsu.setVisible(true);
				btnVoltar.setVisible(true);
				btnVoltar.setBounds(55, 234, 173, 23);

				btnConfirmUsu.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						if (nomeUsu.getText().equals("")) {
							dao.pixel.setName("Guri");
							dao.pixel.setGenero("M");
							dao.pixel.setCheckpoint("0");
							dao.atualizar(1);
							setVisible(false);
							new prologo().setVisible(true);
							SoundTrack.stop();
						} else {
							dao.pixel.setName(nomeUsu.getText());
							dao.pixel.setGenero("M");
							dao.pixel.setCheckpoint("0");
							dao.atualizar(1);
							dao.buscar();
							setVisible(false);
							new prologo().setVisible(true);
							SoundTrack.stop();
						}
					}

					@Override
					public void mouseEntered(MouseEvent e) {
						try { audio("BtnHoverSong", -5, 0, 0); } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
							e1.printStackTrace();
						}
						imgBtnConfirmar = new ImageIcon("res2/imgTelaInicial/Confirmar-Selected.png");
						btnConfirmUsu.setIcon(imgBtnConfirmar);
					}

					@Override
					public void mouseExited(MouseEvent e) {
						imgBtnConfirmar = new ImageIcon("res2/imgTelaInicial/Confirmar-NoSelected.png");
						btnConfirmUsu.setIcon(imgBtnConfirmar);
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try { audio("BtnHoverSong", -5, 0, 0); } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
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
				btnJogar.setVisible(false);
				btnCarregar.setVisible(false);
				btnInstrucao.setVisible(false);

				btnVoltar.setBounds(55, 260, 173, 23);
				btnVoltar.setVisible(true);

				verificOutraTela = true;
				executarTabela();
				scrollTable.setVisible(true);

				table.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						String nomeUsu = String.valueOf(table.getValueAt(table.getSelectedRow(), 0));
						dao.pixel.setName(nomeUsu);
						System.out.println(dao.pixel.getName());
						dao.buscar();
						new controleFase();
						SoundTrack.stop();
						setVisible(false);
					}
				});
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try { audio("BtnHoverSong", -5, 0, 0); } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
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
				try { audio("BtnHoverSong", -5, 0, 0); } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
				imgBtnInstrucao = new ImageIcon("res2/imgTelaInicial/Instrucoes-Selected.png");
				btnInstrucao.setIcon(imgBtnInstrucao);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				imgBtnInstrucao = new ImageIcon("res2/imgTelaInicial/Instrucoes-NoSelected.png");
				btnInstrucao.setIcon(imgBtnInstrucao);
			}
		});

		btnVoltar.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				btnJogar.setVisible(true);
				btnCarregar.setVisible(true);
				btnInstrucao.setVisible(true);
				verificOutraTela = false;
				scrollTable.setVisible(false);
				nomeUsu.setVisible(false);
				btnConfirmUsu.setVisible(false);
				btnVoltar.setVisible(false);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				try { audio("BtnHoverSong", -8, 0, 0); } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e1) {
					e1.printStackTrace();
				}
				imgBtnVoltar = new ImageIcon("res2/imgTelaInicial/Voltar-Selected.png");
				btnVoltar.setIcon(imgBtnVoltar);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				imgBtnVoltar = new ImageIcon("res2/imgTelaInicial/Voltar-NoSelected.png");
				btnVoltar.setIcon(imgBtnVoltar);
			}
		});

	}
	
	public void audio(String nome, float volume, int loop, int action)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		File file = new File("./" + nome + ".wav");
		AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
		Clip clip = AudioSystem.getClip();
		clip.open(audioStream);

		FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
		gainControl.setValue(-volume); // Reduce volume by 10 decibels.
		clip.loop(loop);
		if (action == 0) {
			clip.start();
		} else {
			clip.stop();
		}
		
	}
	
	public void executarTabela() {
		try {
			String sql = "SELECT user_name, checkpoint FROM user";
			statement = dao.bd.c.prepareStatement(sql);
			resultado = statement.executeQuery();
			DefaultTableModel tableModel = new DefaultTableModel(new String[] { "Usuario", "Fase" }, 0) {
				public boolean isCellEditable(int row, int col) {
					return false;
				}
			};
			int qtdeColunas = resultado.getMetaData().getColumnCount();
			for (int indice = 1; indice <= qtdeColunas; indice++) {
//				tableModel.addColumn(resultado.getMetaData().getColumnName(indice));
			}
			table = new JTable(tableModel);
			DefaultTableModel dtm = (DefaultTableModel) table.getModel();

			while (resultado.next()) {
				try {
					String[] dados = new String[qtdeColunas];
					for (int i = 1; i <= qtdeColunas; i++) {
						dados[i - 1] = resultado.getString(i);
					}
					dtm.addRow(dados);
					System.out.print("");
				} catch (SQLException erro) {
					System.out.println(erro);
				}
				scrollTable.setViewportView(table);
			}

			resultado.close();
			statement.close();
		} catch (SQLException erro) {
			System.out.println("ERRO");
		}
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
					try {dao.bd.c.close();} catch (SQLException e1) {e1.printStackTrace();}
					System.exit(0);
				}
				if (e.getKeyCode() == KeyEvent.VK_Z && verificInstrucao == true) {
					new closeTelaIntroducao().start();
				} else if (e.getKeyCode() == KeyEvent.VK_Z && verificOutraTela == true) {
					btnJogar.setVisible(true);
					btnCarregar.setVisible(true);
					btnInstrucao.setVisible(true);
					nomeUsu.setVisible(false);
					scrollTable.setVisible(false);
					btnConfirmUsu.setVisible(false);
					btnVoltar.setVisible(false);
					verificOutraTela = false;
				}
			}
		});

	}

	public static void main(String args[]) {
		new telaInicial();
	}

	public class openTelaIntroducao extends Thread {
		public void run() {
			imgFundo = new ImageIcon("res2/imgTelaInicial/01FadeInInstru.gif");
			gifFundo.setIcon(imgFundo);
			try {Thread.sleep(2100);} catch (InterruptedException e1) {e1.printStackTrace();}
			imgFundo = new ImageIcon("res2/imgTelaInicial/01FadeInStatic.png");
			gifFundo.setIcon(imgFundo);
			comandosInstrucao.setVisible(true);
//			imgFundo = new ImageIcon("res2/imgTelaInicial/01FadeInInstru.gif");
			verificInstrucao = true;
		}
	}

	public class closeTelaIntroducao extends Thread {
		public void run() {
			comandosInstrucao.setVisible(false);
			imgFundo = new ImageIcon("res2/imgTelaInicial/02FadeOutInstru.gif");
			gifFundo.setIcon(imgFundo);
			try {
				Thread.sleep(1800);
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
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
