import java.awt.*;
import java.awt.event.*;
import java.security.Key;

import javax.swing.*;

public class introdução extends JFrame {
	private JLabel imgFundo0, imgFundo1, guriPretoBrancoAndando, guriPretoBranParado,GuriParadoColorido, GuriAndandoColorido,GuriComTochaColorido, balaoOpen, balaoFixo, balaoClose, opcaoS,
			opcaoN, zeroGif, Op1NoSelected, Op1Selected, Op2NoSelected, Op2Selected;
	
	private String[] TextoDoGuri = { "<html>Guri: Quem é você? O que Aconteceu? Onde Estou?",
			"<html>Guri: Por que só eu estou aqui? Cadê meus pais?",
			// CASO ELE ESCOLHA MORRER
			"<html>Guri: Eu escolho morrer!",
			"<html>Guri: Eu sou um péssimo filho. Eu sou apenas um egoísta. Um egoísta não pode salvar o mundo..",
			"<html>Guri: !?",
			"<html>Guri: He he... Pensando bem... Posso mudar de opção?",
			"<html>Guri: Certo, mas onde encontrarei estes pixels?"}; // Texto do Guri
	private JLabel dialogoDoGuri; // Diálogo do Guri
	
	private String[] TextoDoZero = {
			"<html>Zero: Eu me chamo Zero, sou o servidor do mundo virtual. Você está na nuvem da rede de internet. Por algum motivo houve um bug no sistema e todos os pixels se apagaram",
			"<html>Zero: Você é a única pessoa que encontrei nessa escuridão. Aproveitando o momento, te escolho para uma missão super importante. Você tera que encontrar os 3 pixels e reacende-los com esta tocha magica, estou com um pouco de preguiça pra isso",
			"<html>Zero: Agora sobre os seus pais... Eu não irei dizer nada, então tire suas próprias conclusões durante sua jornada. Agora você tem duas opções: <br> <br> <center>1° IR PARA A MISSÃO ou 2° MORRER AGORA",
			// CASO ELE ECOLHA MORRER
			"<html>Zero: ???????????????????????????????",
			"<html>Zero: Na verdade um egoísta pode sim salvar o mundo, basta deixar de ser um egoísta",
			"<html>Zero: Use essa missão de pretesto para tornar-se altruísta. Você não pode consertar os seus erros, mas pode nunca mais comete-los",
			"<html>Zero: Como você foi o unico que encontrei irei permitir você trocar de opção. Por favor, de seu melhor",
			"<html>Zero: Basta seguir o seu coração"}; // Texto do Guri

	private JLabel dialogoDoZero; // Diálogo do Guri
	private int opcaoSimulada = 0, opcaoReal = 0;
	private String palavra = "";
	private boolean pularDialog = false, fazerEscolha = false;

	public introdução() {
		new Eventos().start();
		Componentes();
		Frame();
	}

	public void Componentes() {
		setLayout(null);

		// CRIANDO AS LABELS DE DIALOGO
		dialogoDoGuri = new JLabel();
		dialogoDoGuri.setForeground(Color.WHITE);
		dialogoDoGuri.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		dialogoDoGuri.setBounds(40, 25, 500, 100);
		dialogoDoGuri.setVisible(false);
		add(dialogoDoGuri);

		dialogoDoZero = new JLabel();
		dialogoDoZero.setForeground(Color.WHITE);
		dialogoDoZero.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		dialogoDoZero.setBounds(40, 85, 500, 100);
		dialogoDoZero.setVisible(false);
		add(dialogoDoZero);

		// CRIANDO OS BOTÕES DE ESCOLHA
		Op1NoSelected = new JLabel(new ImageIcon("res/imgIntro/YesButton-NoSelected.png"));
		Op1NoSelected.setVisible(false);
		add(Op1NoSelected);

		Op1Selected = new JLabel(new ImageIcon("res/imgIntro/YesButton-Selected.png"));
		Op1Selected.setVisible(false);
		add(Op1Selected);

		Op2NoSelected = new JLabel(new ImageIcon("res/imgIntro/NoButton-NoSelected.png"));
		Op2NoSelected.setVisible(false);
		add(Op2NoSelected);

		Op2Selected = new JLabel(new ImageIcon("res/imgIntro/NoButton-Selected.png"));
		Op2Selected.setVisible(false);
		add(Op2Selected);

		// GIF DO FUNDO
		imgFundo0 = new JLabel(new ImageIcon("res/imgIntro/01-BackgroundWhiteGIF.gif"));
		imgFundo0.setBounds(0, 0, 600, 310);
		imgFundo0.setVisible(false);
		add(imgFundo0);

		zeroGif = new JLabel(new ImageIcon("res/imgIntro/ZeroServer01.gif"));
		zeroGif.setBounds(400, 220, 64, 64);
		zeroGif.setVisible(false);
		add(zeroGif);

		// GIT DO GURI PRETO BRANCO
		guriPretoBrancoAndando = new JLabel(new ImageIcon("res/imgIntro/GuriPretoBrancoAndando.gif"));
		guriPretoBrancoAndando.setBounds(-10, 160, 64, 64);
		guriPretoBrancoAndando.setVisible(false);
		add(guriPretoBrancoAndando);

		guriPretoBranParado = new JLabel(new ImageIcon("res/imgIntro/GuriParadoPretoBranco.gif"));
		guriPretoBranParado.setBounds(120, 220, 64, 64);
		guriPretoBranParado.setVisible(false);
		add(guriPretoBranParado);
		
		// GIF DO GURI COLORIDO
		
		GuriParadoColorido = new JLabel(new ImageIcon("res/Guri01.gif"));
		GuriParadoColorido.setBounds(120, 220, 64, 64);
		GuriParadoColorido.setVisible(false);
		add(GuriParadoColorido);
		
		GuriAndandoColorido = new JLabel(new ImageIcon("res/imgIntro/Guri02AndandoColorido.gif"));
		GuriAndandoColorido.setBounds(-10, 160, 64, 64);
		GuriAndandoColorido.setVisible(false);
		add(GuriAndandoColorido);
		
		GuriComTochaColorido = new JLabel(new ImageIcon("res/imgIntro/Guri06PegandoTocha.gif"));
		GuriComTochaColorido.setBounds(120, 210, 65, 75);
		GuriComTochaColorido.setVisible(false);
		add(GuriComTochaColorido);

		// BALÂO DE FALA
		balaoOpen = new JLabel(new ImageIcon("res/imgIntro/BalãoOpen.gif"));
		balaoOpen.setBounds(0, 0, 600, 310);
		balaoOpen.setVisible(false);
		add(balaoOpen);

		balaoFixo = new JLabel(new ImageIcon("res/imgIntro/BalaoFixo.png"));
		balaoFixo.setBounds(0, 0, 600, 310);
		balaoFixo.setVisible(false);
		add(balaoFixo);
		
		balaoClose = new JLabel(new ImageIcon("res/imgIntro/BalaoClose.gif"));
		balaoClose.setBounds(0, 0, 600, 310);
		balaoClose.setVisible(false);
		add(balaoClose);

		// TESTANDO
		opcaoS = new JLabel("SIM");
		opcaoS.setBounds(100, 100, 100, 100);
		opcaoS.setVisible(false);
		// add(opcaoS);
		// TESTANDO
		opcaoN = new JLabel("NÃO");
		opcaoN.setBounds(100, 0, 100, 100);
		opcaoN.setVisible(false);
		// add(opcaoN);

		// IMAGEM DE FUNDO
		imgFundo1 = new JLabel(new ImageIcon("res/imgIntro/01-BackgroundWhite.png"));
		imgFundo1.setBounds(0, 0, 600, 310);
		imgFundo1.setVisible(false);
		add(imgFundo1);

	}

	public void Frame() {
		setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		setSize(600, 310); // Tamanho do JFrame
		setUndecorated(true); // Desativa a Decoração
		setLocationRelativeTo(null); // Tela Centralizada
		setVisible(true);
	}

	public static void main(String args[]) {
		new introdução();
	}

	public class Eventos extends Thread implements KeyListener {
		public void run() {
			try {
				for (int i = 0; i < 255; i++) {
					getContentPane().setBackground(new Color(i, i, i));
					Thread.sleep(8);
				}
				Thread.sleep(180);
				imgFundo0.setVisible(true);
				Thread.sleep(2000);
				imgFundo0.setVisible(false);
				imgFundo1.setVisible(true);
				Thread.sleep(3000);

				zeroGif.setVisible(true);
				// FAZENDO O GURI ANDAR
				for (int i = -10; i < 120; i++) {
					guriPretoBrancoAndando.setVisible(true);
					guriPretoBrancoAndando.setBounds(i, 220, 64, 64);
					Thread.sleep(10);
				}
				guriPretoBrancoAndando.setVisible(false);
				guriPretoBranParado.setVisible(true);
				Thread.sleep(1000);

				// Abrindo o balão de fala
				balaoOpen.setVisible(true);
				Thread.sleep(1800);
				balaoOpen.setVisible(false);
				balaoFixo.setVisible(true);
				addKeyListener(this);

				// TODO O DIALOGO DOS DOIS
				dialogoDoGuri.setVisible(true);

				// FOR QUE CAMINHA AS LETRAS DO VETOR
				for (int z = 0; z < TextoDoGuri[0].length(); z++) {
					if (!pularDialog) {
						// FUNÇÃO QUE GUARDA LETRA POR LETRA EM UMA VARIAVEL E VAI MOSTRANDO
						TextEffect(TextoDoGuri[0], dialogoDoGuri, z, 60);
					} else {
						// CASO ELE APERTE ESPAÇO ELE ACABA O FOR E COMPLETA A FRASE
						dialogoDoGuri.setText(TextoDoGuri[0]);
						pularDialog = false;
						break;
					}
				}
				
				// DIALOGO ZERO
				palavra = "";
				dialogoDoZero.setVisible(true);
				for (int z = 0; z < TextoDoZero[0].length(); z++) {
					if (!pularDialog) {
						TextEffect(TextoDoZero[0], dialogoDoZero, z, 40);
					} else {
						dialogoDoZero.setText(TextoDoZero[0]);
						pularDialog = false;
						break;
					}
				}

				// DIALOGO GURI
				Thread.sleep(3000);
				dialogoDoZero.setVisible(false);
				dialogoDoGuri.setVisible(true);
				palavra = "";
				for (int z = 0; z < TextoDoGuri[1].length(); z++) {
					if (!pularDialog) {
						TextEffect(TextoDoGuri[1], dialogoDoGuri, z, 60);
					} else {
						dialogoDoGuri.setText(TextoDoGuri[1]);
						pularDialog = false;
						break;
					}
				}

				// DIALOGO ZERO
				palavra = "";
				dialogoDoZero.setVisible(true);
				for (int z = 0; z < TextoDoZero[1].length(); z++) {
					if (!pularDialog) {
						TextEffect(TextoDoZero[1], dialogoDoZero, z, 40);
					} else {
						dialogoDoZero.setText(TextoDoZero[1]);
						pularDialog = false;
						break;
					}
				}
				guriPretoBranParado.setVisible(false);
				GuriComTochaColorido.setVisible(true);
				Thread.sleep(4500);
				GuriComTochaColorido.setVisible(false);
				GuriParadoColorido.setVisible(true);

				// DIALOGO ZERO
				Thread.sleep(3000);
				dialogoDoZero.setBounds(40, 60, 500, 100);
				dialogoDoGuri.setVisible(false);
				palavra = "";
				dialogoDoZero.setVisible(true);
				for (int z = 0; z < TextoDoZero[2].length(); z++) {
					if (!pularDialog) {
						TextEffect(TextoDoZero[2], dialogoDoZero, z, 40);
					} else {
						dialogoDoZero.setText(TextoDoZero[2]);
						pularDialog = false;
						break;
					}
				}
				
				// MOSTRA OS BOTÕES
				Op1NoSelected.setBounds(150, 150, 100, 30);
				Op1NoSelected.setVisible(true);
				Op1Selected.setBounds(150, 150, 100, 30);
				Op1Selected.setVisible(false);

				Op2NoSelected.setBounds(350, 150, 100, 30);
				Op2NoSelected.setVisible(true);
				Op2Selected.setBounds(350, 150, 100, 30);
				Op2Selected.setVisible(false);

				// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
				fazerEscolha = true;
				// ESPERA O USUARIO ESCOLHER
				while (opcaoReal == 0) {
					System.out.print("Esperando Resposta");
				}

				// DEIXA AS OPÇÕES INVISIVEIS
				Op1NoSelected.setVisible(false);
				Op1Selected.setVisible(false);
				Op2NoSelected.setVisible(false);
				Op2Selected.setVisible(false);
				// DESATIVA A PERMISSÃO DE FAZER ESCOLHAS
				fazerEscolha = false;

				// ESCOLHER MORRER
				if (opcaoReal == 1) {
					
					// DIALOGO GURI
					dialogoDoZero.setVisible(false);
					dialogoDoGuri.setVisible(true);
					palavra = "";
					for (int z = 0; z < TextoDoGuri[2].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[2], dialogoDoGuri, z, 60);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[2]);
							pularDialog = false;
							break;
						}
					}

					// DIALOGO ZERO
					dialogoDoZero.setBounds(40, 85, 500, 100);
					palavra = "";
					dialogoDoZero.setVisible(true);
					for (int z = 0; z < TextoDoZero[3].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoZero[3], dialogoDoZero, z, 55);
						} else {
							dialogoDoZero.setText(TextoDoZero[3]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO GURI
					Thread.sleep(2000);
					dialogoDoZero.setVisible(false);
					dialogoDoGuri.setVisible(true);
					palavra = "";
					for (int z = 0; z < TextoDoGuri[3].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[3], dialogoDoGuri, z, 40);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[3]);
							pularDialog = false;
							break;
						}
					}

					// DIALOGO ZERO
					dialogoDoZero.setBounds(40, 85, 500, 100);
					palavra = "";
					dialogoDoZero.setVisible(true);
					for (int z = 0; z < TextoDoZero[4].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoZero[4], dialogoDoZero, z, 55);
						} else {
							dialogoDoZero.setText(TextoDoZero[4]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO GURI
					Thread.sleep(2000);
					dialogoDoZero.setVisible(false);
					dialogoDoGuri.setVisible(true);
					palavra = "";
					for (int z = 0; z < TextoDoGuri[4].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[4], dialogoDoGuri, z, 40);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[4]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO ZERO
					dialogoDoZero.setBounds(40, 85, 500, 100);
					palavra = "";
					dialogoDoZero.setVisible(true);
					for (int z = 0; z < TextoDoZero[5].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoZero[5], dialogoDoZero, z, 55);
						} else {
							dialogoDoZero.setText(TextoDoZero[5]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO GURI
					Thread.sleep(3000);
					dialogoDoZero.setVisible(false);
					dialogoDoGuri.setVisible(true);
					palavra = "";
					for (int z = 0; z < TextoDoGuri[5].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[5], dialogoDoGuri, z, 40);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[5]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO ZERO
					dialogoDoZero.setBounds(40, 85, 500, 100);
					palavra = "";
					dialogoDoZero.setVisible(true);
					for (int z = 0; z < TextoDoZero[6].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoZero[6], dialogoDoZero, z, 55);
						} else {
							dialogoDoZero.setText(TextoDoZero[6]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO GURI
					Thread.sleep(3000);
					dialogoDoZero.setVisible(false);
					dialogoDoGuri.setVisible(true);
					palavra = "";
					for (int z = 0; z < TextoDoGuri[6].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[6], dialogoDoGuri, z, 40);
						} else {
							dialogoDoGuri.setText(TextoDoGuri[6]);
							pularDialog = false;
							break;
						}
					}
					
					// DIALOGO ZERO
					dialogoDoZero.setBounds(40, 85, 500, 100);
					palavra = "";
					dialogoDoZero.setVisible(true);
					for (int z = 0; z < TextoDoZero[7].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoZero[7], dialogoDoZero, z, 55);
						} else {
							dialogoDoZero.setText(TextoDoZero[7]);
							pularDialog = false;
							break;
						}
					}
					
					// TROCANDO DE TELA
					Thread.sleep(2200);
					balaoFixo.setVisible(false);
					dialogoDoGuri.setVisible(false);
					dialogoDoZero.setVisible(false);
					balaoClose.setVisible(true);
					
					Thread.sleep(1500);
					balaoClose.setVisible(false);
					Thread.sleep(1000);
					GuriParadoColorido.setVisible(false);
					zeroGif.setVisible(false);
					imgFundo1.setVisible(false);
					
					for (int i = 255; i > 0 ; i--) {
						getContentPane().setBackground(new Color(i, i, i));
						Thread.sleep(12);
					}
					
					
					// ESCOLHER IR PARA A MISSÃO
				} else if (opcaoReal == 2) {
					balaoFixo.setVisible(false);
					dialogoDoGuri.setVisible(false);
					dialogoDoZero.setVisible(false);
					balaoClose.setVisible(true);
					
					Thread.sleep(1500);
					balaoClose.setVisible(false);
					Thread.sleep(1000);
					GuriParadoColorido.setVisible(false);
					zeroGif.setVisible(false);
					imgFundo1.setVisible(false);
					
					for (int i = 255; i > 0 ; i--) {
						getContentPane().setBackground(new Color(i, i, i));
						Thread.sleep(12);
					}
				}

//				opcaoS.setVisible(true);
//				opcaoN.setVisible(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
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
