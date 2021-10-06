package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Line2D;
import java.sql.SQLException;
import java.util.Random;

import javax.swing.*;
import javax.swing.border.*;

import BancoDeDados.*;

public class bossFinal extends JFrame {
	private Jogador jogador;
	private onePixelDAO dao;
	JPanel panel, panelPokemonTotal, panelAtkDef,finalDoJogo;

	// LOGO
	static ImageIcon imgLogo = new ImageIcon("res/IconGame.png");

	// LARGURA E ALTURA DO FRAME
	static int larguraFrame = 600;
	static int alturaFrame = 310;

	boolean pularDialog = false, podePular = false;
	String palavra = "";

	// ZEZIN
	ImageIcon imgZezin;
	JLabel lbZezin;
	private int xZezin = 500, yZezin = 130, larguraZezin = 45, alturaZezin = 45;

	// BALAO DE DIALOGO PEQUENO
	ImageIcon imgBalaoDialog;
	JLabel lbBalaoDialog;

	String textoDialogo[] = { "<html>Guri: Consegui derrotar todos os guaridões",
			"<html>Zezin: Parabens, agora me de esses pixels",
			"<html>Guri: Eu ainda estou com receio de te entregar isso",
			"<html>Zezin: EU TE AJUDEI A SALVAR SEU JOGO, AGORA ANDE LOGO E ME ENTREGUE ISSO!",
			"<html>Guri: Eu sei... mas minha missão era pegar os pixels, e nao entrega-los pra você",
			"<html>Zezin: SE VOCÊ NAO ME ENTREGAR AGORA, PEGAREI A FORÇA", "<html>Guri: TENTE SE FOR CAPAZ!" };
	JLabel lbDialogo;
	
	JLabel  imgFinal1, imgFinal2, imgFinal3, imgFinal4, imgFinal5, imgFinal;

	// CONTEUDOS DA BATALHA FINAL
	JProgressBar vidaGuri, vidaZezin;
	int porcemHpGuri, porcemHpZezin;
	JButton ataqueGuri, defesaGuri;
	JLabel escolhaAtkOuDef, descConsole;

	// BTNs ATAQUES
	JButton atkNormal, atkForte,atkPixelRGB;

	// BTNs DEFESA
	JButton defNormal;

	// DANOS
	int danoAtkNormal = 50, danoAtkNormalZ = 52, danoAtkForte = 52, danoCritico = 50, danoAtkPixelRGB = 200;
    boolean ataqueForte = false, ataquePixelRGB = false;
	// VIDA
	int hpGuri = 590, hpZezin = 615;
	int escudoHpGuri = 0, escudoHpZezin = 0;
	
	int chance = 0;
	boolean podeClickar = true;
	boolean atacou = false, defendeu = false, perdeu = false, ganhou = false;

	public bossFinal() {
		componentes();
		eventos();

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
		panel.setVisible(true);
		add(panel);

		imgZezin = new ImageIcon("res2/imgZezin/Zezin_1.gif");
		lbZezin = new JLabel(imgZezin);
		lbZezin.setBounds(xZezin, yZezin, larguraZezin, alturaZezin);
		panel.add(lbZezin);

		// LABELs DO DIALOGO DO GURI
		lbDialogo = new JLabel();
		lbDialogo.setBounds(395, 45, 120, 100);
		lbDialogo.setForeground(Color.WHITE);
		lbDialogo.setFont(new Font("Pixel Operator 8", Font.PLAIN, 8));
		lbDialogo.setVisible(false);
		panel.add(lbDialogo);

		// IMAGENs DO BALAO DE DIALOGO
		imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
		lbBalaoDialog = new JLabel(imgBalaoDialog);
		lbBalaoDialog.setBounds(385, 70, 130, 60);
		lbBalaoDialog.setVisible(false);
		panel.add(lbBalaoDialog);

		// PAINEL DO JOGO
		panelPokemonTotal = new JPanel(null);
		panelPokemonTotal.setBounds(0, 0, 600, 310);
		panelPokemonTotal.setVisible(false);
		add(panelPokemonTotal);
		
		finalDoJogo = new JPanel(null);
		finalDoJogo.setBounds(0, 0, 600, 310);
		finalDoJogo.setVisible(false);
		add(finalDoJogo);

		// CONTEUDOS DA BATALHA
		vidaGuri = new JProgressBar();
		vidaGuri.setBounds(10, 10, 200, 20);
		vidaGuri.setStringPainted(true);
		vidaGuri.setString("HP:"+hpGuri);
		vidaGuri.setMaximum(hpGuri);
		vidaGuri.setValue(hpGuri);
		vidaGuri.setFont(new Font("Pixel Operator 8", Font.BOLD, 12));
//		vidaGuri.setForeground(Color.red);
		vidaGuri.setBorder(null);
		panelPokemonTotal.add(vidaGuri);

		vidaZezin = new JProgressBar();
		vidaZezin.setBounds(390, 10, 200, 20);
		vidaZezin.setStringPainted(true);
		vidaZezin.setString("HP:"+hpZezin);
		vidaZezin.setMaximum(hpZezin);
		vidaZezin.setValue(hpZezin);
		vidaZezin.setFont(new Font("Pixel Operator 8", Font.BOLD, 12));
//		vidaZezin.setForeground(Color.red);
		vidaZezin.setBorder(null);
		panelPokemonTotal.add(vidaZezin);
		
		imgFinal = new JLabel(new ImageIcon("res2/imgFinal/EndBackground.png"));
		imgFinal.setBounds(0,0,600,310);
		imgFinal.setVisible(false);
		finalDoJogo.add(imgFinal);
		
		imgFinal1 = new JLabel(new ImageIcon("res2/imgFinal/End01.png"));
		imgFinal1.setBounds(0,0,600,310);
		imgFinal1.setVisible(false);
		finalDoJogo.add(imgFinal1);
		
		imgFinal2 = new JLabel(new ImageIcon("res2/imgFinal/End02.png"));
		imgFinal2.setBounds(0,0,600,310);
		imgFinal2.setVisible(false);
		finalDoJogo.add(imgFinal2);
		
		imgFinal3 = new JLabel(new ImageIcon("res2/imgFinal/End03.png"));
		imgFinal3.setBounds(0,0,600,310);
		imgFinal3.setVisible(false);
		finalDoJogo.add(imgFinal3);
		
		imgFinal4 = new JLabel(new ImageIcon("res2/imgFinal/End04.png"));
		imgFinal4.setBounds(0,0,600,310);
		imgFinal4.setVisible(false);
		finalDoJogo.add(imgFinal4);
		
		imgFinal5 = new JLabel(new ImageIcon("res2/imgFinal/End05.png"));
		imgFinal5.setBounds(0,0,600,310);
		imgFinal5.setVisible(false);
		finalDoJogo.add(imgFinal5);

		ataqueGuri = new JButton("Ataque");
		ataqueGuri.setBounds(10, 35, 135, 35);
		ataqueGuri.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		ataqueGuri.setFocusable(false);
		panelPokemonTotal.add(ataqueGuri);

		defesaGuri = new JButton("Defesa");
		defesaGuri.setBounds(10, 80, 120, 35);
		defesaGuri.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		defesaGuri.setFocusable(false);
		panelPokemonTotal.add(defesaGuri);

		// PAINEL DE ESCOLHER OS TIPOS DE ATAQUES OU DEFESAS
		panelAtkDef = new JPanel(null) {
			public void paint(Graphics g) {
				super.paint(g);
				Graphics2D g2 = (Graphics2D) g;
				g2.setStroke(new BasicStroke(2));
				Line2D divisaoLateral = new Line2D.Float(200, 45, 200, 110);
				Line2D divisaoHorizontal = new Line2D.Float(0, 45, 590, 45);
				g2.draw(divisaoLateral);
				g2.draw(divisaoHorizontal);
			}
		};
		panelAtkDef.setBounds(5, 195, 590, 110);
//		panelAtkDef.setBackground(Color.BLACK);
		Border lineBorder = BorderFactory.createLineBorder(Color.black);
		panelAtkDef.setBorder(BorderFactory.createTitledBorder(lineBorder, "", TitledBorder.CENTER, TitledBorder.TOP,
				new Font("Arial", Font.BOLD, 18), Color.black));
		panelPokemonTotal.add(panelAtkDef);

		descConsole = new JLabel("Console: Esperando sua escolha");
		descConsole.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		descConsole.setBounds(10, -26, 590, 100);
		descConsole.setVisible(true);
		panelAtkDef.add(descConsole);

		escolhaAtkOuDef = new JLabel();
		escolhaAtkOuDef.setText("");
		escolhaAtkOuDef.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		escolhaAtkOuDef.setBounds(60, 26, 200, 100);
		escolhaAtkOuDef.setVisible(true);
		panelAtkDef.add(escolhaAtkOuDef);

		atkNormal = new JButton("ATK Normal");
		atkNormal.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		atkNormal.setBounds(240, 52, 150, 25);
		atkNormal.setVisible(false);
		atkNormal.setFocusable(false);
		panelAtkDef.add(atkNormal);
		
		atkForte = new JButton("ATK forte");
		atkForte.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		atkForte.setBounds(400, 52, 150, 25);
		atkForte.setVisible(false);
		atkForte.setFocusable(false);
		panelAtkDef.add(atkForte);
		
		atkPixelRGB = new JButton("ATK Pixel RGB");
		atkPixelRGB.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		atkPixelRGB.setBounds(240, 80, 150, 25);
		atkPixelRGB.setVisible(false);
		atkPixelRGB.setFocusable(false);
		panelAtkDef.add(atkPixelRGB);

		defNormal = new JButton("DEF Normal");
		defNormal.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		defNormal.setBounds(240, 52, 150, 25);
		defNormal.setVisible(false);
		defNormal.setFocusable(false);
		panelAtkDef.add(defNormal);

		// CARREGAR OS KEYLISTENERS DA CLASS JOGADOR
		addKeyListener(new Teclado());
		// CARREGAR CLASS JOGADOR
		jogador = new Jogador();
		jogador.carregar();

		jogador.setX(-10);
		jogador.setCaminhoImg("res2/imgPlayer/guriDireita000.png");
		jogador.carregar();
		repaint();

		// INICIANDO BANCO DE DADOS
		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}
		
		new movimentacaoInicial().start();
	}

	public void eventos() {
//		new movimentacaoInicial().start();
		ataqueGuri.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				defNormal.setVisible(false);
				escolhaAtkOuDef.setVisible(true);
				escolhaAtkOuDef.setText("> ATAQUE");
				atkNormal.setVisible(true);
				atkForte.setVisible(true);
				atkPixelRGB.setVisible(true);
			};
		});

		defesaGuri.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				atkPixelRGB.setVisible(false);
				atkNormal.setVisible(false);
				atkForte.setVisible(false);
				escolhaAtkOuDef.setVisible(true);
				escolhaAtkOuDef.setText("> DEFESA");
				defNormal.setVisible(true);
			}
		});

		// ATAQUE NORMAL
		atkNormal.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!perdeu || !ganhou) {

					if (podeClickar) {
						atacou = true;
						new danoAtaque().start();
					}
				}
			};

			public void mouseEntered(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("ATK Normal: Ataque Simples");
				}
			};
			

			public void mouseExited(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("Console: Esperando sua escolha");
				}
			};

		});
		
		// ATAQUE FORTE
		atkForte.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!perdeu || !ganhou) {

					if (podeClickar) {
						atacou = true;
						ataqueForte = true;		
						chance = 2 + (int) (Math.random() * 6);
						new danoAtaque().start();
					}
				}
			};

			public void mouseEntered(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("ATK Forte: Ataque forte");
				}
			};

			public void mouseExited(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("Console: Esperando sua escolha");
				}
			};

		});
		
		// ATAQUE PIXEL RGB
		atkPixelRGB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!perdeu || !ganhou) {

					if (podeClickar) {
						atacou = true;
						ataquePixelRGB = true;
						
						new danoAtaque().start();
					}
				}
			};

			public void mouseEntered(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("ATK Pixel: Uni os três pixels");
				}
			};
			
			public void mouseExited(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("Console: Esperando sua escolha");
				}
			};

		});
		// DEFESA NORMAL
		defNormal.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!perdeu || !ganhou) {

					if (podeClickar) {
						defendeu = true;
						new danoAtaque().start();
					}
				}
			};

			public void mouseEntered(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("DEF Normal: Defesa Simples");
				}
			};

			public void mouseExited(MouseEvent e) {
				if (!perdeu || !ganhou) {

					descConsole.setText("Console: Esperando sua escolha");

				}
			};

		});
	}

	private class danoAtaque extends Thread {
		public void run() {
			podeClickar = false;
			if (atacou) {
				int sobreVida = hpZezin + escudoHpZezin;
				System.out.println("\nSobreVida: " + sobreVida);
				System.out.println("Entrou :"+ataqueForte );
				if(ataqueForte) {
					if(chance > 4) {
						danoAtkForte += danoCritico;
						hpZezin = sobreVida - danoAtkForte;
						System.out.println("Dano do ATK do forte"+ danoAtkForte);
						descConsole.setText("Console: Ataque Forte do Guri realizado com sucesso!");

					}else {
						hpZezin = sobreVida - danoAtkForte;
						descConsole.setText("Console: Ataque Forte do Guri falhou!");	
						System.out.println("Dano do ATK do forte que flopou"+ danoAtkForte);

					}
				}else if(ataquePixelRGB) {
					atkPixelRGB.setEnabled(false);
					hpZezin = sobreVida - danoAtkPixelRGB;
					System.out.println("Dano do ATK do forte"+ danoAtkForte);
					descConsole.setText("Console: Ataque Forte do Guri realizado com sucesso!");
					
				}else {
				    hpZezin = sobreVida - danoAtkNormal;
				    descConsole.setText("Console: Ataque do Guri realizado com sucesso!");
				}
				System.out.println("HpZezin tomando dano: " + hpZezin);
				for (int i = vidaZezin.getValue(); i >= hpZezin; i -= 2) {
					vidaZezin.setValue(i);
					try {
						sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				System.out.println("Barra de vida: " + vidaZezin.getValue());
				escudoHpZezin = 0;
				atacou = false;
			} else if (defendeu) {
				escudoHpGuri = 35;
				descConsole.setText("Console: Guri ativou seu escudo!");
			}

			if (hpZezin > 0) {
				try {
					sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				new danoRecebido().start();
			} else {
				descConsole.setText("Console: Zezin perdeu!");
				ganhou = true;
				//new Tremer().start();
				panelPokemonTotal.removeAll();
				panelPokemonTotal.setVisible(false);
				finalDoJogo.setVisible(true);
				new finalDoJogo().start();
			}
			
			vidaGuri.setString("HP:"+hpGuri);
			vidaZezin.setString("HP:"+hpZezin);
			ataquePixelRGB = false;
			danoAtkForte = 52;
			ataqueForte = false;

		}
	}
	
	private class finalDoJogo extends Thread{
		public void run() {
            try {
				imgFinal1.setVisible(true);
				Thread.sleep(2000);
				imgFinal1.setVisible(false);
				imgFinal2.setVisible(true);
				Thread.sleep(2000);
				imgFinal2.setVisible(false);
				imgFinal3.setVisible(true);
				Thread.sleep(2000);
				imgFinal3.setVisible(false);
				imgFinal4.setVisible(true);
				Thread.sleep(2000);
				imgFinal4.setVisible(false);
				imgFinal5.setVisible(true);
				Thread.sleep(2000);
				imgFinal.setVisible(false);
				imgFinal.setVisible(true);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}

	private class danoRecebido extends Thread {
		public void run() {
			int random = (int) (Math.random() * 5) + 1;
			int sobreVida = hpGuri + escudoHpGuri;
			if (random <= 4) {
				System.out.println("\nSobreVida: " + sobreVida);
				hpGuri = sobreVida - danoAtkNormalZ;
				System.out.println("HpGuri tomando dano: " + hpGuri);
				descConsole.setText("Console: Ataque do Zezin realizado com sucesso!");
				for (int i = vidaGuri.getValue(); i >= hpGuri; i -= 2) {
					vidaGuri.setValue(i);
					try {
						sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				escudoHpGuri = 0;
			} else {
				escudoHpZezin = 35;
				descConsole.setText("Console: Zezin ativou seu escudo!");
			}

			System.out.println("Barra de vida: " + vidaGuri.getValue());
			defendeu = false;
			podeClickar = true;

			if (hpGuri < 0) {
				descConsole.setText("Console: Guri perdeu!");
				perdeu = true;
				new Tremer().start();
				try { sleep(1500); } catch (InterruptedException e) { e.printStackTrace(); }
				descConsole.setText("Console: Guri sera deletado!");
				try { sleep(2000); } catch (InterruptedException e) { e.printStackTrace(); }
				dao.atualizarInventario(3);
				dao.atualizar(3);
				try { sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
				System.exit(0);
				
			}
			
			vidaGuri.setString("HP:"+hpGuri);
			vidaZezin.setString("HP:"+hpZezin);
		}
	}

	public void TextEffect(String DialogoBox, JLabel lbDialogo, int z, int milesimos) {
		try {
			char letra = DialogoBox.charAt(z);
			palavra = palavra + letra;
			if (z >= 6) {
				lbDialogo.setVisible(true);
			} else {
				lbDialogo.setVisible(false);
			}
			lbDialogo.setText(palavra);
			Thread.sleep(milesimos);
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}

	public void movimentacaoPet() {

		// PIXEL VERMELHO
		jogador.setxR(jogador.getX() - 18);
		jogador.setyR(jogador.getY() - 10);

		// PIXEL VERDE
		jogador.setxG(jogador.getX() - 25);
		jogador.setyG(jogador.getY() + 5);

		// PIXEL AZUL
		jogador.setxB(jogador.getX() - 18);
		jogador.setyB(jogador.getY() + 20);
	}

	private class Tremer extends Thread {
		public void run() {
			try {
				long sleepTime = 30;
				int originalX = getLocation().x;
				int originalY = getLocation().y;

				for (int i = 0; i <= 22; i++) {
					setLocation(originalX + 2, originalY);
					Thread.sleep(sleepTime);
					setLocation(originalX + 2, originalY + 2);
					Thread.sleep(sleepTime);
					setLocation(originalX, originalY + 2);
					Thread.sleep(sleepTime);
					setLocation(originalX, originalY);
					Thread.sleep(sleepTime);
					setLocation(originalX - 2, originalY);
					Thread.sleep(sleepTime);
					setLocation(originalX - 2, originalY - 2);
					Thread.sleep(sleepTime);
					setLocation(originalX, originalY - 2);
					Thread.sleep(sleepTime);
				}
				interrupt();
				setLocation(originalX, originalY);

			} catch (Exception ex) {
				System.out.println(ex.toString());
			}
		}
	}

	private class movimentacaoInicial extends Thread {
		public void run() {
			while (jogador.getX() <= 350) {
				jogador.setX(jogador.getX() + 4);
				jogador.setCaminhoImg("./res2/imgPlayer/guriDireita001.png");
				movimentacaoPet();
				jogador.carregar();
				jogador.atualizar();
				repaint();
				try {
					sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				jogador.setCaminhoImg("./res2/imgPlayer/guriDireita002.png");
				movimentacaoPet();
				jogador.atualizar();
				jogador.carregar();
				repaint();
				try {
					sleep(25);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			jogador.setCaminhoImg("./res2/imgPlayer/guriDireita000.png");
			movimentacaoPet();
			jogador.atualizar();
			jogador.carregar();
			repaint();
			try {
				new balaoDialogFadeOut().start();
				podePular = true;
				palavra = "";
				for (int z = 0; z < textoDialogo[0].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[0], lbDialogo, z, 55);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[0]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
				sleep(2000);
				podePular = true;
				palavra = "";
				for (int z = 0; z < textoDialogo[1].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[1], lbDialogo, z, 55);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[1]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
				sleep(2000);
				podePular = true;
				palavra = "";
				for (int z = 0; z < textoDialogo[2].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[2], lbDialogo, z, 55);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[2]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
				sleep(2000);
//				podePular = true;
				palavra = "";
				new Tremer().start();
				for (int z = 0; z < textoDialogo[3].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[3], lbDialogo, z, 55);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[3]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
				sleep(2000);
				podePular = true;
				palavra = "";
				for (int z = 0; z < textoDialogo[4].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[4], lbDialogo, z, 55);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[4]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
				sleep(2000);
//				podePular = true;
				palavra = "";
				new Tremer().start();
				for (int z = 0; z < textoDialogo[5].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[5], lbDialogo, z, 75);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[5]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
				sleep(2000);
//				podePular = true;
				palavra = "";
				new Tremer().start();
				for (int z = 0; z < textoDialogo[6].length(); z++) {
					if (!pularDialog) {
						TextEffect(textoDialogo[6], lbDialogo, z, 120);
					} else {
						lbDialogo.setVisible(true);
						lbDialogo.setText(textoDialogo[6]);
						pularDialog = false;
						break;
					}
				}

				sleep(2000);
				podePular = false;
				new balaoDialogFadeIn().start();
				sleep(1000);
				panel.setVisible(false);
				panel.removeAll();
				panelPokemonTotal.setVisible(true);
				stop();
			} catch (InterruptedException ex) {

			}
		}
	}

	private class balaoDialogFadeIn extends Thread {
		public void run() {
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeIn.gif");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			lbDialogo.setVisible(false);
			lbBalaoDialog.setVisible(false);
			jogador.setAndar(true);
			stop();
		}
	}

	private class balaoDialogFadeOut extends Thread {
		public void run() {
			lbBalaoDialog.setVisible(true);
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaFadeOut.gif");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			try {
				sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			imgBalaoDialog = new ImageIcon("res2/imgBalaoDialog/BalaoFalaStatic.png");
			lbBalaoDialog.setIcon(imgBalaoDialog);
			lbDialogo.setVisible(true);
			stop();
		}
	}

	private class Teclado extends KeyAdapter {
		public void keyPressed(KeyEvent e) {
			if (e.getKeyCode() == KeyEvent.VK_SPACE) {
				if (podePular) {
					pularDialog = true;
				} else {
					pularDialog = false;
				}
			}
			if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
				try {
					dao.bd.c.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				System.exit(0);
			}
		}
	}

	public static void main(String args[]) {
		new bossFinal();
	}
}
