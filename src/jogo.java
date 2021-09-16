import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class jogo extends JFrame {

	private JLabel gifFundo, pngFundo, guriColoridoAndando, guriColoridoParado, guriAcendendoPixel, Op1NoSelected, Op1Selected,
			Op2NoSelected, Op2Selected, YesNoSelected, YesSelected, NoNoSelected, NoSelected, balaoOpen, balaoFixo,
			balaoClose, hotBar00, hotBar01, hotBar02, hotBar03, pixelRed, pixelGreen, pixelBlue, pixelApagado;

	private String[] TextoDoGuri = {
			"<html>Guri: Estou caminhando bem devagar.<br> <br><center> Será que: 1° Devo Correr ou 2° Não correr",
			// ESCOLHER NÃO CORRER
			"<html>Guri: Caraca!, achei um pixel. Isso até que foi bem facil, mas qual pixel sera esse ?",
			"<html>Guri: Coletei um pixel vermelho. Até o cenario mudou. Pelo visto estou levemente perto de encontrar as outras, mas melhor ir um passo de cada vez",
			"<html>Guri:Agora devo escolher uma outra direção para ir, estou sentindo algo bom para o sul. <br> <br> <center> 1° Ir para o sul ou 2° Não ir para o sul",
			"<html>Guri caminha ao norte e se depara com uma cidade abandonada.<br><br>Guri: Essa cidade dá medo.<br> <br> <center> 1° Ir embora ou 2° Não ir embora",
			"<html>Guri entra na cidade. <br><br>Guri: Há uma casa com a porta aberta logo ali na frente.<br><br><center> 1° Ir até lá ou 2° Não ir até lá",
			"<html>Guri: Caraca, que coincidencia, no final desta cidade está mais um pixel!",
			"<html>Guri: Uau ele é verde! Estou um passo mais perto de conseguir recuperar todas as cores e de me encontrar com os meus pais!",
			"<>"};// Texto do Guri
	private JLabel dialogoDoGuri; // Diálogo do Guri

	private ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	private int opcaoSimulada = 0, opcaoReal = 0;
	private String palavra = "", op = "";
	private boolean pularDialog = false, fazerEscolha = false;

	public jogo() {
		Componentes();
		Frame();
		new Eventos().start();
	}

	public static void main(String args[]) {
		new jogo();
	}

	public void Componentes() {
		setLayout(null);

		// CRIANDO AS LABELS DE DIALOGO
		dialogoDoGuri = new JLabel();
		dialogoDoGuri.setForeground(Color.WHITE);
		dialogoDoGuri.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		dialogoDoGuri.setBounds(40, 25, 580, 100);
		dialogoDoGuri.setVisible(false);
		add(dialogoDoGuri);

		// CRIANDO A BARRA DE SLOTS DE RGB
		hotBar00 = new JLabel(new ImageIcon("res/imgHotBar/Slot00.png"));
		hotBar00.setBounds(0, 10, 116, 29);
		add(hotBar00);

		hotBar01 = new JLabel(new ImageIcon("res/imgHotBar/Slot01.png"));
		hotBar01.setVisible(false);
		hotBar01.setBounds(0, 10, 116, 29);
		add(hotBar01);

		hotBar02 = new JLabel(new ImageIcon("res/imgHotBar/Slot02.png"));
		hotBar02.setVisible(false);
		hotBar02.setBounds(0, 10, 116, 29);
		add(hotBar02);

		hotBar03 = new JLabel(new ImageIcon("res/imgHotBar/Slot03.png"));
		hotBar03.setVisible(false);
		hotBar03.setBounds(0, 10, 116, 29);
		add(hotBar03);

		// CRIANDO O LABEL DOS PIXEL

		// PIXEL APAGADO
		pixelApagado = new JLabel(new ImageIcon("res/imgPixels/PixelApagado.gif"));
		pixelApagado.setBounds(600, 235, 64, 64);
		add(pixelApagado);

		// PIXEL RED
		pixelRed = new JLabel(new ImageIcon("res/imgPixels/PixelR.gif"));
		pixelRed.setBounds(600, 235, 64, 64);
		add(pixelRed);

		// PIXEL GREEN
		pixelGreen = new JLabel(new ImageIcon("res/imgPixels/PixelG.gif"));
		pixelGreen.setBounds(600, 235, 64, 64);
		add(pixelGreen);

		// PIXEL BLUE
		pixelBlue = new JLabel(new ImageIcon("res/imgPixels/PixelB.gif"));
		pixelBlue.setBounds(600, 235, 64, 64);
		add(pixelBlue);

		// CRIANDO O LABEL DO GURI

		// GURI COLORIDO ANDANDO
		guriColoridoAndando = new JLabel(new ImageIcon("res/imgGuri/Guri02AndandoColorido.gif"));
		guriColoridoAndando.setBounds(-10, 235, 64, 64);
		guriColoridoAndando.setVisible(false);
		add(guriColoridoAndando);

		// GURI COLORIDO PARADO
		guriColoridoParado = new JLabel(new ImageIcon("res/imgGuri/Guri01ParadoColorido.gif"));
		guriColoridoParado.setBounds(120, 235, 64, 64);
		guriColoridoParado.setVisible(false);
		add(guriColoridoParado);

		// GURI COLORIDO ACENDENDO PIXEL
		guriAcendendoPixel = new JLabel(new ImageIcon("res/imgGuri/Guri07.gif"));
		guriAcendendoPixel.setBounds(120, 225, 65, 75);
		guriAcendendoPixel.setVisible(false);
		add(guriAcendendoPixel);

		// CRIANDO OS BOTÕES DE ESCOLHA
		Op1NoSelected = new JLabel(new ImageIcon("res/imgLBSelect/Option1Button-NoSelected.png"));
		Op1NoSelected.setVisible(false);
		add(Op1NoSelected);

		Op1Selected = new JLabel(new ImageIcon("res/imgLBSelect/Option1Button-Selected.png"));
		Op1Selected.setVisible(false);
		add(Op1Selected);

		Op2NoSelected = new JLabel(new ImageIcon("res/imgLBSelect/Option2Button-NoSelected.png"));
		Op2NoSelected.setVisible(false);
		add(Op2NoSelected);

		Op2Selected = new JLabel(new ImageIcon("res/imgLBSelect/Option2Button-Selected.png"));
		Op2Selected.setVisible(false);
		add(Op2Selected);

		// CRIANDO OS BOTÕES DE ESCOLHA
		YesNoSelected = new JLabel(new ImageIcon("res/imgLBSelect/YesButton-NoSelected.png"));
		YesNoSelected.setVisible(false);
		add(YesNoSelected);

		YesSelected = new JLabel(new ImageIcon("res/imgLBSelect/YesButton-Selected.png"));
		YesSelected.setVisible(false);
		add(YesSelected);

		NoNoSelected = new JLabel(new ImageIcon("res/imgLBSelect/NoButton-NoSelected.png"));
		NoNoSelected.setVisible(false);
		add(NoNoSelected);

		NoSelected = new JLabel(new ImageIcon("res/imgLBSelect/NoButton-Selected.png"));
		NoSelected.setVisible(false);
		add(NoSelected);

		// BALÂO DE FALA
		balaoOpen = new JLabel(new ImageIcon("res/imgBalao/BalãoOpen.gif"));
		balaoOpen.setBounds(0, 5, 600, 310);
		balaoOpen.setVisible(false);
		add(balaoOpen);

		balaoFixo = new JLabel(new ImageIcon("res/imgBalao/BalaoFixo.png"));
		balaoFixo.setBounds(0, 5, 600, 310);
		balaoFixo.setVisible(false);
		add(balaoFixo);

		balaoClose = new JLabel(new ImageIcon("res/imgBalao/BalaoClose.gif"));
		balaoClose.setBounds(0, 5, 600, 310);
		balaoClose.setVisible(false);
		add(balaoClose);

		// COLOCANDO A IMAGEM DE FUNDO
		
//		gifFundo = new JLabel(new ImageIcon("res/imgJogo/00-backgroundBlackV2.gif"));
//		gifFundo.setBounds(0, 0, 600, 310);
//		gifFundo.setVisible(true);
//		add(gifFundo);
	
		gifFundo = new JLabel(new ImageIcon("res/imgJogo/02-backgroundRedGIF.gif"));
		gifFundo.setBounds(0, 0, 600, 310);
		gifFundo.setVisible(false);
		add(gifFundo);
		

		pngFundo = new JLabel(new ImageIcon("res/imgJogo/02-backgroundRed.png"));
		pngFundo.setBounds(0, 0, 600, 310);
		pngFundo.setVisible(false);
		add(pngFundo);

	}

	public void Frame() {
		setIconImage(imgLogo.getImage());
		getContentPane().setBackground(new Color(0, 0, 0));
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 310); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		setVisible(true);
	}

	public class Eventos extends Thread implements KeyListener {
		public void run() {
			try {
				Thread.sleep(500);
				for (int i = 0; i < 20; i++) {
					getContentPane().setBackground(new Color(i, i, i));
					Thread.sleep(20);
				}

				guriColoridoAndando.setVisible(true);
				for (int i = -10; i < 120; i++) {
					guriColoridoAndando.setBounds(i, 235, 64, 64);
					Thread.sleep(10);
				}

				// ABRINDO BALÃO DE DIALOGO
				balaoOpen.setVisible(true);
				Thread.sleep(1800);
				balaoOpen.setVisible(false);
				balaoFixo.setVisible(true);
				addKeyListener(this);

				dialogoDoGuri.setBounds(40, 50, 500, 100);
				// DIALOGO DO GURI 0
				for (int z = 0; z < TextoDoGuri[0].length(); z++) {
					if (!pularDialog) {
						TextEffect(TextoDoGuri[0], dialogoDoGuri, z, 55);
					} else {
						dialogoDoGuri.setText(TextoDoGuri[0]);
						pularDialog = false;
						break;
					}
				}
				// MOSTRA OS BOTÕES
				YesNoSelected.setBounds(150, 135, 100, 30);
				YesNoSelected.setVisible(true);
				YesSelected.setBounds(150, 135, 100, 30);
				YesSelected.setVisible(false);

				NoNoSelected.setBounds(350, 135, 100, 30);
				NoNoSelected.setVisible(true);
				NoSelected.setBounds(350, 135, 100, 30);
				NoSelected.setVisible(false);

				// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
				fazerEscolha = true;
				op = "Yes";
				// ESPERA O USUARIO ESCOLHER
				while (opcaoReal == 0) {
					System.out.print("Esperando Resposta");
				}
				fazerEscolha = false;

				// DEIXA AS OPÇÕES INVISIVEIS
				YesNoSelected.setVisible(false);
				YesSelected.setVisible(false);
				NoNoSelected.setVisible(false);
				NoSelected.setVisible(false);

				// CORRER
				if (opcaoReal == 1) {

					// VAZIO

					// NÃO CORRER
				} else if (opcaoReal == 2) {
					// FECHANDO BALÃO DE DIALOGO
					Thread.sleep(500);
					balaoFixo.setVisible(false);
					dialogoDoGuri.setVisible(false);
					balaoClose.setVisible(true);
					Thread.sleep(1500);
					balaoClose.setVisible(false);

					for (int i = 600; i > 220; i--) {
						pixelApagado.setBounds(i, 235, 64, 64);
						Thread.sleep(12);
					}

					guriColoridoAndando.setVisible(false);
					guriColoridoParado.setVisible(true);

					// ABRINDO BALÃO DE DIALOGO
					balaoOpen.setVisible(true);
					Thread.sleep(1800);
					balaoOpen.setVisible(false);
					balaoFixo.setVisible(true);

					palavra = "";
					dialogoDoGuri.setBounds(40, 80, 500, 100);
					dialogoDoGuri.setVisible(true);

					// DIALOGO DO GURI 1
					for (int z = 0; z < TextoDoGuri[1].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[1], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[1]);
							pularDialog = false;
							break;
						}
					}
					
					// GURI PEGANDO O PIXEL
					pixelApagado.setVisible(false);
					pixelRed.setBounds(220, 235, 64, 64);
					pixelRed.setVisible(true);
					Thread.sleep(2400);
					guriColoridoParado.setVisible(false);
					guriColoridoAndando.setVisible(true);
					for (int i = 120; i < 210; i++) {
						guriColoridoAndando.setBounds(i, 235, 64, 64);
						Thread.sleep(10);
					}
					hotBar00.setVisible(false);
					hotBar01.setVisible(true);
					guriColoridoAndando.setVisible(false);
					guriAcendendoPixel.setBounds(210, 225, 65, 75);
					guriAcendendoPixel.setVisible(true);
					pixelRed.setVisible(false);					

					Thread.sleep(2000);
					pngFundo.setVisible(true);
					guriAcendendoPixel.setVisible(false);
					guriColoridoAndando.setVisible(true);

					for (int i = 210; i > 120; i--) {
						guriColoridoAndando.setBounds(i, 235, 64, 64);
						Thread.sleep(10);
					}

					guriColoridoAndando.setVisible(false);
					guriColoridoParado.setVisible(true);
					
					palavra = "";
					// DIALOGO DO GURI 2
					for (int z = 0; z < TextoDoGuri[2].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[2], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[2]);
							pularDialog = false;
							break;
						}
					}
					
					Thread.sleep(2500);
					dialogoDoGuri.setBounds(40, 50, 500, 100);
					palavra = "";
					// DIALOGO DO GURI 3
					for (int z = 0; z < TextoDoGuri[3].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[3], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[3]);
							pularDialog = false;
							break;
						}
					}
					
					// MOSTRA OS BOTÕES
					YesNoSelected.setBounds(150, 145, 100, 30);
					YesNoSelected.setVisible(true);
					YesSelected.setBounds(150, 145, 100, 30);
					YesSelected.setVisible(false);

					NoNoSelected.setBounds(350, 145, 100, 30);
					NoNoSelected.setVisible(true);
					NoSelected.setBounds(350, 145, 100, 30);
					NoSelected.setVisible(false);

					// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
					opcaoReal = 0;
					fazerEscolha = true;
					op = "Yes";
					// ESPERA O USUARIO ESCOLHER
					while (opcaoReal == 0) {
						System.out.print("Esperando Resposta");
					}
					fazerEscolha = false;

					// DEIXA AS OPÇÕES INVISIVEIS
					YesNoSelected.setVisible(false);
					YesSelected.setVisible(false);
					NoNoSelected.setVisible(false);
					NoSelected.setVisible(false);
					
					// IR PARA O SUL
					if( opcaoReal == 1) {
						
						// VAZIO
						
					// NÃO IR PARA O SUL	
					}else if ( opcaoReal == 2) {
						// FECHANDO BALÃO DE DIALOGO
						Thread.sleep(500);
						balaoFixo.setVisible(false);
						dialogoDoGuri.setVisible(false);
						balaoClose.setVisible(true);
						Thread.sleep(1500);
						balaoClose.setVisible(false);
						
						guriColoridoParado.setVisible(false);
						guriColoridoAndando.setVisible(true);
						pngFundo.setVisible(false);
						gifFundo.setVisible(true);
						
						Thread.sleep(2800);
						// ABRINDO BALÃO DE DIALOGO
						balaoOpen.setVisible(true);
						Thread.sleep(1800);
						balaoOpen.setVisible(false);
						balaoFixo.setVisible(true);
						
						guriColoridoParado.setVisible(true);
						guriColoridoAndando.setVisible(false);
						pngFundo.setVisible(true);
						gifFundo.setVisible(false);
						
						dialogoDoGuri.setBounds(40, 50, 500, 100);
						palavra = "";
						// DIALOGO DO GURI 4
						for (int z = 0; z < TextoDoGuri[4].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoDoGuri[4], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setText(TextoDoGuri[4]);
								pularDialog = false;
								break;
							}
						}
						
						// MOSTRA OS BOTÕES
						YesNoSelected.setBounds(150, 155, 100, 30);
						YesNoSelected.setVisible(true);
						YesSelected.setBounds(150, 155, 100, 30);
						YesSelected.setVisible(false);

						NoNoSelected.setBounds(350, 155, 100, 30);
						NoNoSelected.setVisible(true);
						NoSelected.setBounds(350, 155, 100, 30);
						NoSelected.setVisible(false);

						// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
						opcaoReal = 0;
						fazerEscolha = true;
						op = "Yes";
						// ESPERA O USUARIO ESCOLHER
						while (opcaoReal == 0) {
							System.out.print("Esperando Resposta");
						}
						fazerEscolha = false;

						// DEIXA AS OPÇÕES INVISIVEIS
						YesNoSelected.setVisible(false);
						YesSelected.setVisible(false);
						NoNoSelected.setVisible(false);
						NoSelected.setVisible(false);
						
						// IR EMBORA
						if( opcaoReal == 1) {
							
							// VAZIO
							
						// NÃO IR EMBORA
						}else if( opcaoReal == 2) {
							palavra = "";
							// DIALOGO DO GURI 5
							for (int z = 0; z < TextoDoGuri[5].length(); z++) {
								if (!pularDialog) {
									TextEffect(TextoDoGuri[5], dialogoDoGuri, z, 55);
								} else {
									dialogoDoGuri.setText(TextoDoGuri[5]);
									pularDialog = false;
									break;
								}
							}
							// MOSTRA OS BOTÕES
							YesNoSelected.setBounds(150, 155, 100, 30);
							YesNoSelected.setVisible(true);
							YesSelected.setBounds(150, 155, 100, 30);
							YesSelected.setVisible(false);

							NoNoSelected.setBounds(350, 155, 100, 30);
							NoNoSelected.setVisible(true);
							NoSelected.setBounds(350, 155, 100, 30);
							NoSelected.setVisible(false);

							// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
							opcaoReal = 0;
							fazerEscolha = true;
							op = "Yes";
							// ESPERA O USUARIO ESCOLHER
							while (opcaoReal == 0) {
								System.out.print("Esperando Resposta");
							}
							fazerEscolha = false;

							// DEIXA AS OPÇÕES INVISIVEIS
							YesNoSelected.setVisible(false);
							YesSelected.setVisible(false);
							NoNoSelected.setVisible(false);
							NoSelected.setVisible(false);
							
							// IR ATÉ LÁ
							if( opcaoReal == 1) {
								
								// VAZIO
								
							// NÃO IR ATÉ LÁ	
							}else if( opcaoReal == 2) {
								dialogoDoGuri.setBounds(40, 80, 500, 100);
								palavra = "";
								// DIALOGO DO GURI 6
								for (int z = 0; z < TextoDoGuri[6].length(); z++) {
									if (!pularDialog) {
										TextEffect(TextoDoGuri[6], dialogoDoGuri, z, 55);
									} else {
										dialogoDoGuri.setText(TextoDoGuri[6]);
										pularDialog = false;
										break;
									}
								}
								
								// FECHANDO BALÃO DE DIALOGO
								Thread.sleep(500);
								balaoFixo.setVisible(false);
								dialogoDoGuri.setVisible(false);
								balaoClose.setVisible(true);
								Thread.sleep(1500);
								balaoClose.setVisible(false);
								
								guriColoridoParado.setVisible(false);
								guriColoridoAndando.setVisible(true);
								pngFundo.setVisible(false);
								gifFundo.setVisible(true);
								
								pixelApagado.setBounds(600, 235, 64, 64);
								pixelApagado.setVisible(true);
								for (int i = 600; i > 220; i--) {
									pixelApagado.setBounds(i, 235, 64, 64);
									Thread.sleep(12);
								}
								guriColoridoAndando.setVisible(false);
								guriColoridoParado.setVisible(true);
								
								pngFundo.setVisible(true);
								gifFundo.setVisible(false);
								
								// GURI PEGANDO O PIXEL
								pixelApagado.setVisible(false);
								pixelGreen.setBounds(220, 235, 64, 64);
								pixelGreen.setVisible(true);
								Thread.sleep(2400);
								guriColoridoParado.setVisible(false);
								guriColoridoAndando.setVisible(true);
								for (int i = 120; i < 210; i++) {
									guriColoridoAndando.setBounds(i, 235, 64, 64);
									Thread.sleep(10);
								}
								hotBar01.setVisible(false);
								hotBar02.setVisible(true);
								guriColoridoAndando.setVisible(false);
								guriAcendendoPixel.setBounds(210, 225, 65, 75);
								guriAcendendoPixel.setVisible(true);
								pixelGreen.setVisible(false);					

								Thread.sleep(2000);
								pngFundo.setVisible(true);
								guriAcendendoPixel.setVisible(false);
								guriColoridoAndando.setVisible(true);

								for (int i = 210; i > 120; i--) {
									guriColoridoAndando.setBounds(i, 235, 64, 64);
									Thread.sleep(10);
								}

								guriColoridoAndando.setVisible(false);
								guriColoridoParado.setVisible(true);
								
								// ABRINDO BALÃO DE DIALOGO
								balaoOpen.setVisible(true);
								Thread.sleep(1800);
								balaoOpen.setVisible(false);
								balaoFixo.setVisible(true);
								
								guriColoridoParado.setVisible(true);
								guriColoridoAndando.setVisible(false);
								pngFundo.setVisible(true);
								gifFundo.setVisible(false);
			
								
							}
						}
						
					}
					
					
				}

			} catch (InterruptedException ex) {
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// ESQUERDA
			if (fazerEscolha) {
				if (e.getKeyCode() == KeyEvent.VK_LEFT) {
					opcaoSimulada = 1;
				}
				// DIREITA
				else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
					opcaoSimulada = 2;
				}

				if (op == "Yes") {
					if (opcaoSimulada == 1) {
						YesNoSelected.setVisible(false);
						YesSelected.setVisible(true);
						NoNoSelected.setVisible(true);
						NoSelected.setVisible(false);
					} else if (opcaoSimulada == 2) {
						YesNoSelected.setVisible(true);
						YesSelected.setVisible(false);
						NoNoSelected.setVisible(false);
						NoSelected.setVisible(true);
					}
				} else if (op == "1") {
					// MUDA O TEXTO CONFORME A OPÇÃO SELECIONADA
					if (opcaoSimulada == 1) {
						Op1NoSelected.setVisible(false);
						Op1Selected.setVisible(true);
						Op2NoSelected.setVisible(true);
						Op2Selected.setVisible(false);
					} else if (opcaoSimulada == 2) {
						Op1NoSelected.setVisible(true);
						Op1Selected.setVisible(false);
						Op2NoSelected.setVisible(false);
						Op2Selected.setVisible(true);
					}
				}
				// CONFIRMAR ESCOLHA
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					opcaoReal = opcaoSimulada;
					opcaoSimulada = 0;
				}

			} else {
				opcaoSimulada = 0;
			}

			if (e.getKeyChar() == KeyEvent.VK_SPACE) {
				pularDialog = true;
			}

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

}
