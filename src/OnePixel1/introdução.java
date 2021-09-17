package OnePixel1;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.security.Key;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class introdução extends JFrame {
	private JLabel imgFundo0, imgFundo1, guriPretoBrancoAndando, guriPretoBranParado,GuriParadoColorido, GuriAndandoColorido, 
	GuriComTochaColorido, balaoOpen, balaoFixo, balaoClose, zeroGif, Op1NoSelected, Op1Selected, Op2NoSelected, Op2Selected;
	
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
			"<html>Zero: Eu me chamo Zero, sou o servidor do mundo virtual. Você está na nuvem da rede de internet. Por algum motivo houve um bug no sistema e todos os pixels se apagaram.",
			"<html>Zero: Você é a única pessoa que encontrei nessa escuridão. Aproveitando o momento, te escolho para uma missão extremamente importante. Você terá que encontrar os 3 pixels e reacendê-los com esta tocha mágica, estou com um pouco de preguiça pra isso.",
			"<html>Zero: Agora sobre os seus pais... Eu não irei dizer nada, então tire suas próprias conclusões nessa jornada. Agora você tem duas opções: <br> <br> <center>1° MORRER AGORA ou 2° IR PARA A MISSÃO",
			// CASO ELE ECOLHA MORRER
			"<html>Zero: ???????????????????????????????",
			"<html>Zero: Na verdade um egoísta pode sim salvar o mundo, basta deixar de ser um egoísta.",
			"<html>Zero: Use essa missão de pretexto para tornar-se altruísta. Você não pode consertar os seus erros, mas pode nunca mais cometê-los.",
			"<html>Zero: Como você foi o único que encontrei irei permitir você trocar de opção. Por favor, dê seu melhor.",
			"<html>Zero: Basta seguir o seu coração"}; // Texto do Guri

	private JLabel dialogoDoZero; // Diálogo do Guri
	private int opcaoSimulada = 0, opcaoReal = 0;
	private String palavra = "";
	private boolean pularDialog = false, fazerEscolha = false;

	private ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	
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

		// GIF DO FUNDO
		imgFundo0 = new JLabel(new ImageIcon("res/imgIntro/01-BackgroundWhiteGIF.gif"));
		imgFundo0.setBounds(0, 0, 600, 310);
		imgFundo0.setVisible(false);
		add(imgFundo0);

		zeroGif = new JLabel(new ImageIcon("res/imgZero/ZeroServer01.gif"));
		zeroGif.setBounds(400, 220, 64, 64);
		zeroGif.setVisible(false);
		add(zeroGif);

		// GIT DO GURI PRETO BRANCO
		guriPretoBrancoAndando = new JLabel(new ImageIcon("res/imgGuri/GuriPretoBrancoAndando.gif"));
		guriPretoBrancoAndando.setBounds(-10, 160, 64, 64);
		guriPretoBrancoAndando.setVisible(false);
		add(guriPretoBrancoAndando);

		guriPretoBranParado = new JLabel(new ImageIcon("res/imgGuri/GuriParadoPretoBranco.gif"));
		guriPretoBranParado.setBounds(120, 220, 64, 64);
		guriPretoBranParado.setVisible(false);
		add(guriPretoBranParado);
		
		// GIF DO GURI COLORIDO
		
		GuriParadoColorido = new JLabel(new ImageIcon("res/imgGuri/Guri01ParadoColorido.gif"));
		GuriParadoColorido.setBounds(120, 220, 64, 64);
		GuriParadoColorido.setVisible(false);
		add(GuriParadoColorido);
		
		GuriAndandoColorido = new JLabel(new ImageIcon("res/imgGuri/Guri02AndandoColorido.gif"));
		GuriAndandoColorido.setBounds(-10, 160, 64, 64);
		GuriAndandoColorido.setVisible(false);
		add(GuriAndandoColorido);
		
		GuriComTochaColorido = new JLabel(new ImageIcon("res/imgGuri/Guri06PegandoTocha.gif"));
		GuriComTochaColorido.setBounds(120, 210, 65, 75);
		GuriComTochaColorido.setVisible(false);
		add(GuriComTochaColorido);

		// BALÂO DE FALA
		balaoOpen = new JLabel(new ImageIcon("res/imgBalao/BalãoOpen.gif"));
		balaoOpen.setBounds(0, 0, 600, 310);
		balaoOpen.setVisible(false);
		add(balaoOpen);

		balaoFixo = new JLabel(new ImageIcon("res/imgBalao/BalaoFixo.png"));
		balaoFixo.setBounds(0, 0, 600, 310);
		balaoFixo.setVisible(false);
		add(balaoFixo);
		
		balaoClose = new JLabel(new ImageIcon("res/imgBalao/BalaoClose.gif"));
		balaoClose.setBounds(0, 0, 600, 310);
		balaoClose.setVisible(false);
		add(balaoClose);

		// IMAGEM DE FUNDO
		imgFundo1 = new JLabel(new ImageIcon("res/imgIntro/01-BackgroundWhite.png"));
		imgFundo1.setBounds(0, 0, 600, 310);
		imgFundo1.setVisible(false);
		add(imgFundo1);

	}

	public void Frame() {
		setIconImage(imgLogo.getImage());
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
				try {
					audio("guriPegandoTochaAudio", 18, 0, 0);
				} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
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
						Thread.sleep(8);
					}
					
					setVisible(false);
					new jogo().setVisible(true);
					
					
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
						Thread.sleep(8);
					}
		
					setVisible(false);
					new jogo().setVisible(true);
				}

//				opcaoS.setVisible(true);
//				opcaoN.setVisible(true);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (UnsupportedAudioFileException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (LineUnavailableException e) {
				// TODO Auto-generated catch block
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

	public void TextEffect(String DialogoBox, JLabel lbDialogo, int z, int milesimos) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
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
			TextEffectAudio();
		} catch (InterruptedException ex) {
			System.out.println(ex);
		}
	}
	
	public void TextEffectAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
		int random = (int) (Math.random() *3);
		switch (random) {
		case 0: audio("guriFalando", 25, 0, 0);
		case 1: audio("guriFalando1", 25, 0, 0);
		case 2:  audio("guriFalando2", 25, 0, 0);
		}
	}
	
	public void audio(String nome, float volume, int loop, int action)
			throws UnsupportedAudioFileException, IOException, LineUnavailableException {

		File file = new File(nome + ".wav");
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


}
