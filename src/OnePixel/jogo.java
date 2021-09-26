package OnePixel;

import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import BancoDeDados.*;

public class jogo extends JFrame {

	private onePixelDAO dao;
	
	private JLabel gifFundoRed, pngFundoRed, pngFundoFloresta, pngFundoCidadeGrande, guriColoridoAndando,
			guriColoridoParado, guriAcendendoPixel, guriColoridoCorrendo, guriColoridoMorrendo, Op1NoSelected,
			Op1Selected, Op2NoSelected, Op2Selected, YesNoSelected, YesSelected, NoNoSelected, NoSelected, balaoOpen,
			balaoFixo, balaoClose, hotBar00, hotBar01, hotBar02, hotBar03, pixelRed, pixelGreen, pixelBlue,
			pixelApagado, imgFinal1, imgFinal2, imgFinal3, imgFinal4, imgFinal5, imgFinal, zeroGif;

	private String[] TextoDoGuri = {
			"<html>Guri: Estou caminhando bem devagar.<br> <br><center> Será que: 1° Devo Correr ou 2° Não correr",
			// ESCOLHER NÃO CORRER
			"<html>Guri: Caraca!, achei um pixel. Isso até que foi bem fácil, mas qual pixel será esse?",
			"<html>Guri: Coletei um pixel vermelho. Até o cenário mudou. Pelo visto estou bem perto de encontrar os outros, melhor continuar caminhando com um passo de cada vez.",
			"<html>Guri: Agora devo escolher uma outra direção para ir, estou sentindo algo bom para o sul. <br> <br> <center> 1° Ir para o sul ou 2° Não ir para o sul",
			// ESCOLHER NAO IR PARA O SUL
			"<html>Guri caminha ao norte e se depara com uma cidade abandonada.<br><br>Guri: Essa cidade é de dar medo.<br> <br> <center> 1° Ir embora ou 2° Não ir embora",
			// ESCOLHER NAO IR EMBORA
			"<html>Guri: Independente de minha escolha... É melhor entrar, sinto que estou sendo forçado a entrar.<br><br>Guri: Há uma casa com a porta aberta logo ali em frente.<br><center> 1° Ir até lá ou 2° Não ir até lá",
			// ESCOLHER NAO IR ATÉ A CASA
			"<html>Guri: Caraca, que coincidência, no final desta cidade está mais um pixel!",
			"<html>Guri: Uau, ele é verde! Estou um passo mais perto de recuperar todas as cores e de me encontrar com os meus pais!",
			"<html>Guri: Esses pixels estão muito fáceis de serem encontrados, isso está meio estranho... É melhor eu não pensar nisso. Logo ali na frente há uma floresta. <br><br><center> 1° Entrar na floresta ou 2° Não Entrar ",
			// ESCOLHER NAO ENTRAR NA FLORESTA
			"<html>Guri: Okay, eu não posso ficar parado aqui. Logo ao lado há uma Cidade Grande.<br><br><center> 1° Entrar na Cidade Grande ou 2° Não Entrar",
			// ESCOLHER ENTRAR NA CIDADE GRANDE
			"<html>Guri: Independente de minha escolha... É melhor entrar, sinto que estou sendo forçado a entrar.<br><br>Guri: Que cidade meio estranha, mas Okay. Opa, parece que tem um pixel azul no cassino.<br><br><center> 1° Entrar ou 2° Não Entrar",
			"<html>Guri: Independente de minha escolha... É bom eu ir para o cassino, tem um pixel lá! xD",
			"<html>Guri: Pixel aparenta estar preso em uma dessas maquinas da sorte.<br><br><center> 1° Tentar a Sorte ou 2° Roubar o Pixel",
			// ESCOLHER TENTAR A SORTE E CONSEGUIR
			"<html>Guri: Eba, consegui. Este é o ultimo pixel, agora poderei ver minha família.",
			// ESCOLHER TENTAR A SORTE E ERRAR
			"<html>Guri: Droga, perdi. Acho que vou ter que roubar, ainda bem que não tem ninguém olhando.",
			// ESCOLHER ROUBAR
			"<html>Guri: Acho que vou ter que roubar, ainda bem que não tem ninguém olhando, então consigo fazer isso sem pensar que é algo errado.",
			// DIALOGO FINAL
			"<html>Guri: Finalmente consegui completar essa missão, que foi bem estranha e fácil por sinal. Agora poderei ver minha família.",
			"<html>Guri: Oshi, porque eu estou aqui, cadê minha familia, cadê tudo?" };// Texto do Guri

	private JLabel dialogoDoGuri; // Diálogo do Guri

	private String[] TextoDoZero = {
			"<html>Zero: Você achou mesmo que essa era a missão verdadeira, tipo... sério? Você conseguia reviver cara, eu basicamente facilitei e até mesmo manipulei algumas escolhas suas para você chegar até aqui. Isso foi muito fácil, era apenas um teste",
			"<html>Zero: Agora que você já sabe tem duas escolhas: <br><br><center>1° Permanecer na matrix ou 2° Enfrentar a realidade",
			"<html> EM BREVE... <br><br><center> ONE PIXEL 2",
			"<html>Zero: Você achou mesmo que essa era a missão verdadeira, tipo... serio? Você conseguia reviver cara. Isso foi muito facil, eu basicamente facilitei e até mesmo manipulei algumas escolhas suas para você chegar até aqui, tirando a parte que você roubou um pixel, mas irei relever, era apenas um teste para ver se era você mesmo o escolhido" }; // Texto
																																																																																											// do
																																																																																											// Guri

	private JLabel dialogoDoZero; // Diálogo do Guri

	private String[] TextoDoConsole = {
			"<html>Guri correu tão rapido que bateu no pixel com muita força. <br><br><center>Você MORREU! <br><br><center> 1° Recomeçar ou 2° Sair",
			"<html>Guri ficou andando sem rumo por muito tempo.<br><br><center>VOCÊ MORREU DE CANSAÇO! <br><br><center> 1° Recomeçar ou 2° Sair",
			"<html>Guri entrou na casa, porem, nao era uma casa e sim um poço artesiano em formato de casa.<br><br><center>VOCÊ MORREU DE QUEDA! <br><br><center> 1° Recomeçar ou 2° Sair ",
			"<html>Guri entrou na floresta, mas não estava preparado para o ambiente.<br><br><center> VOCÊ MORREU DE MEDO ( LITERALMENTE ) <br><br><center> 1° Recomeçar ou 2° Sair" }; // Mensagens
																																														// do
																																														// jogo
																																														// em
																																														// si

	private JLabel dialogoDoConsole;

	private ImageIcon imgLogo = new ImageIcon("res/IconGame.png");
	private int opcaoSimulada = 0, opcaoReal = 0;
	private String palavra = "", op = "";
	private boolean pularDialog = false, fazerEscolha = false, podePular = false;

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
		dialogoDoGuri.setBounds(40, 25, 580, 250);
		dialogoDoGuri.setVisible(false);
		add(dialogoDoGuri);

		dialogoDoZero = new JLabel();
		dialogoDoZero.setForeground(Color.WHITE);
		dialogoDoZero.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		dialogoDoZero.setBounds(40, -55, 500, 350);
		dialogoDoZero.setVisible(false);
		add(dialogoDoZero);

		dialogoDoConsole = new JLabel();
		dialogoDoConsole.setForeground(Color.WHITE);
		dialogoDoConsole.setFont(new Font("Pixel Operator 8", Font.PLAIN, 12));
		dialogoDoConsole.setBounds(40, 50, 500, 100);
		dialogoDoConsole.setVisible(false);
		add(dialogoDoConsole);

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

		// GURI COLORIDO CORRENDO
		guriColoridoCorrendo = new JLabel(new ImageIcon("res/imgGuri/Guri03CorrendoColorido.gif"));
		guriColoridoCorrendo.setBounds(120, 225, 65, 75);
		guriColoridoCorrendo.setVisible(false);
		add(guriColoridoCorrendo);

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

		// GURI COLORIDO MORRENDO
		guriColoridoMorrendo = new JLabel(new ImageIcon("res/imgGuri/Guri05.gif"));
		guriColoridoMorrendo.setBounds(120, 225, 65, 75);
		guriColoridoMorrendo.setVisible(false);
		add(guriColoridoMorrendo);

		// CRIANDO LABEL DO ZERO

		// ZERO GIF
		zeroGif = new JLabel(new ImageIcon("res/imgZero/ZeroServer01.gif"));
		zeroGif.setBounds(400, 220, 64, 64);
		zeroGif.setVisible(false);
		add(zeroGif);

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

		gifFundoRed = new JLabel(new ImageIcon("res/imgJogo/02-backgroundRedGIF.gif"));
		gifFundoRed.setBounds(0, 0, 600, 310);
		gifFundoRed.setVisible(false);
		add(gifFundoRed);

		pngFundoRed = new JLabel(new ImageIcon("res/imgJogo/02-backgroundRed.png"));
		pngFundoRed.setBounds(0, 0, 600, 310);
		pngFundoRed.setVisible(false);
		add(pngFundoRed);

		pngFundoFloresta = new JLabel(new ImageIcon("res/imgJogo/03-BackgroundFloresta.jpg"));
		pngFundoFloresta.setBounds(0, 0, 600, 310);
		pngFundoFloresta.setVisible(false);
		add(pngFundoFloresta);

		pngFundoCidadeGrande = new JLabel(new ImageIcon("res/imgJogo/04-BackgroundCidadeGrande.jpg"));
		pngFundoCidadeGrande.setBounds(0, 0, 600, 310);
		pngFundoCidadeGrande.setVisible(false);
		add(pngFundoCidadeGrande);

		// IMAGENS DE FINAL
		imgFinal1 = new JLabel(new ImageIcon("res/imgFinal/End01.png"));
		imgFinal1.setBounds(0, 0, 600, 310);
		imgFinal1.setVisible(false);
		add(imgFinal1);

		imgFinal2 = new JLabel(new ImageIcon("res/imgFinal/End02.png"));
		imgFinal2.setBounds(0, 0, 600, 310);
		imgFinal2.setVisible(false);
		add(imgFinal2);

		imgFinal3 = new JLabel(new ImageIcon("res/imgFinal/End03.png"));
		imgFinal3.setBounds(0, 0, 600, 310);
		imgFinal3.setVisible(false);
		add(imgFinal3);

		imgFinal4 = new JLabel(new ImageIcon("res/imgFinal/End04.png"));
		imgFinal4.setBounds(0, 0, 600, 310);
		imgFinal4.setVisible(false);
		add(imgFinal4);

		imgFinal5 = new JLabel(new ImageIcon("res/imgFinal/End05.png"));
		imgFinal5.setBounds(0, 0, 600, 310);
		imgFinal5.setVisible(false);
		add(imgFinal5);

		imgFinal = new JLabel(new ImageIcon("res/imgFinal/EndBackground.png"));
		imgFinal.setBounds(0, 0, 600, 310);
		imgFinal.setVisible(false);
		add(imgFinal);
		

		dao = new onePixelDAO();
		if (!dao.bd.connection()) { // verificação da conexão com o bd.
			JOptionPane.showMessageDialog(null, "Falha na conexão!");
			System.exit(0);
		}

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
				openDialogBox();
				addKeyListener(this);

				dialogoDoGuri.setBounds(40, 50, 500, 100);
				// DIALOGO DO GURI 0
				podePular = true;
				for (int z = 0; z < TextoDoGuri[0].length(); z++) {
					if (!pularDialog) {
						TextEffect(TextoDoGuri[0], dialogoDoGuri, z, 55);
					} else {
						dialogoDoGuri.setVisible(true);
						dialogoDoGuri.setText(TextoDoGuri[0]);
						pularDialog = false;
						break;
					}
				}
				podePular = false;
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

					guriColoridoAndando.setVisible(false);
					guriColoridoCorrendo.setVisible(true);
					audio("guriCorrendoAudio", 5, 0, 0);

					for (int i = 600; i > 135; i--) {
						pixelApagado.setBounds(i, 235, 64, 64);
						Thread.sleep(1);
					}
					pixelApagado.setVisible(false);

					guriColoridoCorrendo.setVisible(false);
					guriColoridoMorrendo.setVisible(true);
					audio("guriMorrendoAudio", 12, 0, 0);
					dialogoDoGuri.setVisible(false);
					dialogoDoConsole.setVisible(true);

					palavra = "";
					// DIALOGO DO CONSOLE 0
					podePular = true;
					for (int z = 0; z < TextoDoConsole[0].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoConsole[0], dialogoDoConsole, z, 55);
						} else {
							dialogoDoConsole.setVisible(true);
							dialogoDoConsole.setText(TextoDoConsole[0]);
							pularDialog = false;
							break;
						}
					}
					podePular = false;

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
					opcaoReal = 0;
					fazerEscolha = true;
					op = "1";
					// ESPERA O USUARIO ESCOLHER
					while (opcaoReal == 0) {
						System.out.print("Esperando Resposta");
					}
					fazerEscolha = false;

					// DEIXA AS OPÇÕES INVISIVEIS
					Op1NoSelected.setVisible(false);
					Op1Selected.setVisible(false);
					Op2NoSelected.setVisible(false);
					Op2Selected.setVisible(false);

					// RECOMEÇAR
					if (opcaoReal == 1) {
						setVisible(false);
						new jogo();

						// SAIR
					} else if (opcaoReal == 2) {
						System.exit(0);
					}

					// NÃO CORRER
				} else if (opcaoReal == 2) {
					// FECHANDO BALÃO DE DIALOGO
					closeDialogBox();

					for (int i = 600; i > 220; i--) {
						pixelApagado.setBounds(i, 235, 64, 64);
						Thread.sleep(12);
					}

					guriColoridoAndando.setVisible(false);
					guriColoridoParado.setVisible(true);

					// ABRINDO BALÃO DE DIALOGO
					openDialogBox();

					palavra = "";
					dialogoDoGuri.setBounds(40, 80, 500, 100);
					dialogoDoGuri.setVisible(true);

					// DIALOGO DO GURI 1
					podePular = true;
					for (int z = 0; z < TextoDoGuri[1].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[1], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoDoGuri[1]);
							pularDialog = false;
							break;
						}
					}
					podePular = false;
					
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
					audio("guriPegandoPixelAudio", 12, 0, 0);
					pixelRed.setVisible(false);

					Thread.sleep(2000);
					pngFundoRed.setVisible(true);
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
					podePular = true;
					for (int z = 0; z < TextoDoGuri[2].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[2], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoDoGuri[2]);
							pularDialog = false;
							break;
						}
					}
					podePular = false;

					Thread.sleep(2500);
					dialogoDoGuri.setBounds(40, 50, 500, 100);
					palavra = "";
					// DIALOGO DO GURI 3
					podePular = true;
					for (int z = 0; z < TextoDoGuri[3].length(); z++) {
						if (!pularDialog) {
							TextEffect(TextoDoGuri[3], dialogoDoGuri, z, 55);
						} else {
							dialogoDoGuri.setVisible(true);
							dialogoDoGuri.setText(TextoDoGuri[3]);
							pularDialog = false;
							break;
						}
					}
					podePular = false;

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
					if (opcaoReal == 1) {
						// FECHANDO BALÃO DE DIALOGO
						closeDialogBox();

						dialogoDoGuri.setVisible(false);
						guriColoridoAndando.setVisible(true);
						guriColoridoParado.setVisible(false);
						pngFundoRed.setVisible(false);
						gifFundoRed.setVisible(true);

						Thread.sleep(10000);
						guriColoridoAndando.setVisible(false);
						guriColoridoMorrendo.setVisible(true);
						audio("guriMorrendoAudio", 12, 0, 0);
						pngFundoRed.setVisible(true);
						gifFundoRed.setVisible(false);

						balaoOpen.setVisible(true);
						Thread.sleep(1800);
						balaoOpen.setVisible(false);
						balaoFixo.setVisible(true);

						palavra = "";
						// DIALOGO DO CONSOLE 1
						podePular = true;
						for (int z = 0; z < TextoDoConsole[1].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoDoConsole[1], dialogoDoConsole, z, 55);
							} else {
								dialogoDoConsole.setVisible(true);
								dialogoDoConsole.setText(TextoDoConsole[1]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;

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
						opcaoReal = 0;
						fazerEscolha = true;
						op = "1";
						// ESPERA O USUARIO ESCOLHER
						while (opcaoReal == 0) {
							System.out.print("Esperando Resposta");
						}
						fazerEscolha = false;

						// DEIXA AS OPÇÕES INVISIVEIS
						Op1NoSelected.setVisible(false);
						Op1Selected.setVisible(false);
						Op2NoSelected.setVisible(false);
						Op2Selected.setVisible(false);

						// RECOMEÇAR
						if (opcaoReal == 1) {
							setVisible(false);
							new jogo();

							// SAIR
						} else if (opcaoReal == 2) {
							System.exit(0);
						}

						// NÃO IR PARA O SUL
					} else if (opcaoReal == 2) {
						// FECHANDO BALÃO DE DIALOGO
						closeDialogBox();

						guriColoridoParado.setVisible(false);
						guriColoridoAndando.setVisible(true);
						pngFundoRed.setVisible(false);
						gifFundoRed.setVisible(true);

						Thread.sleep(2800);
						// ABRINDO BALÃO DE DIALOGO
						openDialogBox();

						guriColoridoParado.setVisible(true);
						guriColoridoAndando.setVisible(false);
						pngFundoRed.setVisible(true);
						gifFundoRed.setVisible(false);

						dialogoDoGuri.setBounds(40, 50, 500, 100);
						palavra = "";
						// DIALOGO DO GURI 4
						podePular = true;
						for (int z = 0; z < TextoDoGuri[4].length(); z++) {
							if (!pularDialog) {
								TextEffect(TextoDoGuri[4], dialogoDoGuri, z, 55);
							} else {
								dialogoDoGuri.setVisible(true);
								dialogoDoGuri.setText(TextoDoGuri[4]);
								pularDialog = false;
								break;
							}
						}
						podePular = false;

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

						// NÃO IR EMBORA
						if (opcaoReal == 1 || opcaoReal == 2) {
							palavra = "";
							dialogoDoGuri.setBounds(40, 70, 500, 100);
							// DIALOGO DO GURI 5
							podePular = true;
							for (int z = 0; z < TextoDoGuri[5].length(); z++) {
								if (!pularDialog) {
									TextEffect(TextoDoGuri[5], dialogoDoGuri, z, 55);
								} else {
									dialogoDoGuri.setVisible(true);
									dialogoDoGuri.setText(TextoDoGuri[5]);
									pularDialog = false;
									break;
								}
							}
							podePular = false;
							
							// MOSTRA OS BOTÕES
							YesNoSelected.setBounds(150, 165, 100, 30);
							YesNoSelected.setVisible(true);
							YesSelected.setBounds(150, 165, 100, 30);
							YesSelected.setVisible(false);

							NoNoSelected.setBounds(350, 165, 100, 30);
							NoNoSelected.setVisible(true);
							NoSelected.setBounds(350, 165, 100, 30);
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
							if (opcaoReal == 1) {
								dialogoDoGuri.setVisible(false);
								guriColoridoParado.setVisible(false);
								guriColoridoAndando.setVisible(true);

								Thread.sleep(3500);
								guriColoridoAndando.setVisible(false);
								guriColoridoMorrendo.setVisible(true);
								audio("guriMorrendoAudio", 12, 0, 0);
								dialogoDoGuri.setVisible(false);
								dialogoDoConsole.setVisible(true);

								palavra = "";
								// DIALOGO DO CONSOLE 2
								podePular = true;
								for (int z = 0; z < TextoDoConsole[2].length(); z++) {
									if (!pularDialog) {
										TextEffect(TextoDoConsole[2], dialogoDoConsole, z, 55);
									} else {
										dialogoDoConsole.setVisible(true);
										dialogoDoConsole.setText(TextoDoConsole[2]);
										pularDialog = false;
										break;
									}
								}
								podePular = false;

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
								opcaoReal = 0;
								fazerEscolha = true;
								op = "1";
								// ESPERA O USUARIO ESCOLHER
								while (opcaoReal == 0) {
									System.out.print("Esperando Resposta");
								}
								fazerEscolha = false;

								// DEIXA AS OPÇÕES INVISIVEIS
								Op1NoSelected.setVisible(false);
								Op1Selected.setVisible(false);
								Op2NoSelected.setVisible(false);
								Op2Selected.setVisible(false);

								// RECOMEÇAR
								if (opcaoReal == 1) {
									setVisible(false);
									new jogo();

									// SAIR
								} else if (opcaoReal == 2) {
									System.exit(0);
								}

								// NÃO IR ATÉ LÁ
							} else if (opcaoReal == 2) {
								dialogoDoGuri.setBounds(40, 80, 500, 100);
								palavra = "";
								// DIALOGO DO GURI 6
								podePular = true;
								for (int z = 0; z < TextoDoGuri[6].length(); z++) {
									if (!pularDialog) {
										TextEffect(TextoDoGuri[6], dialogoDoGuri, z, 55);
									} else {
										dialogoDoGuri.setVisible(true);
										dialogoDoGuri.setText(TextoDoGuri[6]);
										pularDialog = false;
										break;
									}
								}
								podePular = false;

								// FECHANDO BALÃO DE DIALOGO
								closeDialogBox();

								guriColoridoParado.setVisible(false);
								guriColoridoAndando.setVisible(true);
								pngFundoRed.setVisible(false);
								gifFundoRed.setVisible(true);

								pixelApagado.setBounds(600, 235, 64, 64);
								pixelApagado.setVisible(true);
								for (int i = 600; i > 220; i--) {
									pixelApagado.setBounds(i, 235, 64, 64);
									Thread.sleep(12);
								}
								guriColoridoAndando.setVisible(false);
								guriColoridoParado.setVisible(true);

								pngFundoRed.setVisible(true);
								gifFundoRed.setVisible(false);

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
								audio("guriPegandoPixelAudio", 12, 0, 0);
								pixelGreen.setVisible(false);

								Thread.sleep(2000);
								guriAcendendoPixel.setVisible(false);
								guriColoridoAndando.setVisible(true);

								for (int i = 210; i > 120; i--) {
									guriColoridoAndando.setBounds(i, 235, 64, 64);
									Thread.sleep(10);
								}

								guriColoridoAndando.setVisible(false);
								guriColoridoParado.setVisible(true);

								// ABRINDO BALÃO DE DIALOGO
								openDialogBox();

								guriColoridoParado.setVisible(true);
								guriColoridoAndando.setVisible(false);

								dialogoDoGuri.setBounds(40, 80, 500, 100);
								palavra = "";
								// DIALOGO DO GURI 7
								podePular = true;
								for (int z = 0; z < TextoDoGuri[7].length(); z++) {
									if (!pularDialog) {
										TextEffect(TextoDoGuri[7], dialogoDoGuri, z, 55);
									} else {
										dialogoDoGuri.setVisible(true);
										dialogoDoGuri.setText(TextoDoGuri[7]);
										pularDialog = false;
										break;
									}
								}
								podePular = false;

								dialogoDoGuri.setBounds(40, 50, 500, 100);
								palavra = "";
								// DIALOGO DO GURI 8
								Thread.sleep(2500);
								podePular = true;
								for (int z = 0; z < TextoDoGuri[8].length(); z++) {
									if (!pularDialog) {
										TextEffect(TextoDoGuri[8], dialogoDoGuri, z, 55);
									} else {
										dialogoDoGuri.setVisible(true);
										dialogoDoGuri.setText(TextoDoGuri[8]);
										pularDialog = false;
										break;
									}
								}
								podePular = false;

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

								// ENTRAR NA FLORESTA
								if (opcaoReal == 1) {
									// FECHANDO BALÃO DE DIALOGO
									closeDialogBox();

									guriColoridoParado.setVisible(false);
									guriColoridoAndando.setVisible(true);

									pngFundoFloresta.setBounds(600, 0, 600, 310);
									pngFundoFloresta.setVisible(true);
									pngFundoRed.setVisible(true);
									for (int i = 0; i > -600; i--) {
										pngFundoRed.setBounds(i, 0, 600, 310);
										pngFundoFloresta.setBounds(i + 600, 0, 600, 310);
										Thread.sleep(8);
									}
									pngFundoRed.setVisible(false);

									// ABRINDO BALÃO DE DIALOGO
									openDialogBox();

									Thread.sleep(2000);
									guriColoridoAndando.setVisible(false);
									guriColoridoMorrendo.setVisible(true);
									audio("guriMorrendoAudio", 12, 0, 0);
									dialogoDoGuri.setVisible(false);
									dialogoDoConsole.setVisible(true);

									palavra = "";
									// DIALOGO DO CONSOLE 3
									podePular = true;
									for (int z = 0; z < TextoDoConsole[3].length(); z++) {
										if (!pularDialog) {
											TextEffect(TextoDoConsole[3], dialogoDoConsole, z, 55);
										} else {
											dialogoDoConsole.setVisible(true);
											dialogoDoConsole.setText(TextoDoConsole[3]);
											pularDialog = false;
											break;
										}
									}
									podePular = false;

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
									opcaoReal = 0;
									fazerEscolha = true;
									op = "1";
									// ESPERA O USUARIO ESCOLHER
									while (opcaoReal == 0) {
										System.out.print("Esperando Resposta");
									}
									fazerEscolha = false;

									// DEIXA AS OPÇÕES INVISIVEIS
									Op1NoSelected.setVisible(false);
									Op1Selected.setVisible(false);
									Op2NoSelected.setVisible(false);
									Op2Selected.setVisible(false);

									// RECOMEÇAR
									if (opcaoReal == 1) {
										setVisible(false);
										new jogo();

										// SAIR
									} else if (opcaoReal == 2) {
										System.exit(0);
									}

									// NÃO ENTRAR NA FLORESTA
								} else if (opcaoReal == 2) {

									palavra = "";
									// DIALOGO DO GURI 9
									podePular = true;
									for (int z = 0; z < TextoDoGuri[9].length(); z++) {
										if (!pularDialog) {
											TextEffect(TextoDoGuri[9], dialogoDoGuri, z, 55);
										} else {
											dialogoDoGuri.setVisible(true);
											dialogoDoGuri.setText(TextoDoGuri[9]);
											pularDialog = false;
											break;
										}
									}
									podePular = false;

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

									// ENTRAR NA CIDADE GRANDE
									if (opcaoReal == 1 || opcaoReal == 2) {
										// FECHANDO BALÃO DE DIALOGO
										closeDialogBox();

										guriColoridoParado.setVisible(false);
										guriColoridoAndando.setVisible(true);

										pngFundoCidadeGrande.setBounds(600, 0, 600, 310);
										pngFundoCidadeGrande.setVisible(true);
										pngFundoRed.setVisible(true);
										for (int i = 0; i > -600; i--) {
											pngFundoRed.setBounds(i, 0, 600, 310);
											pngFundoCidadeGrande.setBounds(i + 600, 0, 600, 310);
											Thread.sleep(8);
										}
										pngFundoRed.setVisible(false);

										guriColoridoAndando.setVisible(false);
										guriColoridoParado.setVisible(true);

										// ABRINDO BALÃO DE DIALOGO
										openDialogBox();

										dialogoDoGuri.setBounds(40, 55, 500, 100);
										palavra = "";
										// DIALOGO DO GURI 10
										podePular = true;
										for (int z = 0; z < TextoDoGuri[10].length(); z++) {
											if (!pularDialog) {
												TextEffect(TextoDoGuri[10], dialogoDoGuri, z, 55);
											} else {
												dialogoDoGuri.setVisible(true);
												dialogoDoGuri.setText(TextoDoGuri[10]);
												pularDialog = false;
												break;
											}
										}
										podePular = false;

										// MOSTRA OS BOTÕES
										YesNoSelected.setBounds(150, 165, 100, 30);
										YesNoSelected.setVisible(true);
										YesSelected.setBounds(150, 165, 100, 30);
										YesSelected.setVisible(false);

										NoNoSelected.setBounds(350, 165, 100, 30);
										NoNoSelected.setVisible(true);
										NoSelected.setBounds(350, 165, 100, 30);
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

										// ENTRAR NO CASSINO
										if (opcaoReal == 1 || opcaoReal == 2) {
											// DIALOGO DO GURI 11
											podePular = true;
											palavra = "";
											for (int z = 0; z < TextoDoGuri[11].length(); z++) {
												if (!pularDialog) {
													TextEffect(TextoDoGuri[11], dialogoDoGuri, z, 55);
												} else {
													dialogoDoGuri.setVisible(true);
													dialogoDoGuri.setText(TextoDoGuri[11]);
													pularDialog = false;
													break;
												}
											}
											podePular = false;

											Thread.sleep(2500);
											// DIALOGO DO GURI 12
											podePular = true;
											palavra = "";
											for (int z = 0; z < TextoDoGuri[12].length(); z++) {
												if (!pularDialog) {
													TextEffect(TextoDoGuri[12], dialogoDoGuri, z, 55);
												} else {
													dialogoDoGuri.setVisible(true);
													dialogoDoGuri.setText(TextoDoGuri[12]);
													pularDialog = false;
													break;
												}
											}
											podePular = false;

											// MOSTRA OS BOTÕES
											Op1NoSelected.setBounds(150, 155, 100, 30);
											Op1NoSelected.setVisible(true);
											Op1Selected.setBounds(150, 155, 100, 30);
											Op1Selected.setVisible(false);

											Op2NoSelected.setBounds(350, 155, 100, 30);
											Op2NoSelected.setVisible(true);
											Op2Selected.setBounds(350, 155, 100, 30);
											Op2Selected.setVisible(false);

											// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
											opcaoReal = 0;
											fazerEscolha = true;
											op = "1";
											// ESPERA O USUARIO ESCOLHER
											while (opcaoReal == 0) {
												System.out.print("Esperando Resposta");
											}
											fazerEscolha = false;

											// DEIXA AS OPÇÕES INVISIVEIS
											Op1NoSelected.setVisible(false);
											Op1Selected.setVisible(false);
											Op2NoSelected.setVisible(false);
											Op2Selected.setVisible(false);

											// ESCOLHER SORTE
											if (opcaoReal == 1) {
												int random = (int) (Math.random() * 2);

												if (random > 0) {

													// DIALOGO DO GURI 13
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoGuri[13].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoGuri[13], dialogoDoGuri, z, 55);
														} else {
															dialogoDoGuri.setVisible(true);
															dialogoDoGuri.setText(TextoDoGuri[13]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													// GURI PEGANDO O PIXEL
													pixelApagado.setBounds(220, 235, 64, 64);
													pixelApagado.setVisible(true);
													Thread.sleep(1500);
													pixelApagado.setVisible(false);
													pixelBlue.setBounds(220, 235, 64, 64);
													pixelBlue.setVisible(true);
													Thread.sleep(2400);
													guriColoridoParado.setVisible(false);
													guriColoridoAndando.setVisible(true);
													for (int i = 120; i < 210; i++) {
														guriColoridoAndando.setBounds(i, 235, 64, 64);
														Thread.sleep(10);
													}
													hotBar02.setVisible(false);
													hotBar03.setVisible(true);
													guriColoridoAndando.setVisible(false);
													guriAcendendoPixel.setBounds(210, 225, 65, 75);
													guriAcendendoPixel.setVisible(true);
													audio("guriPegandoPixelAudio", 12, 0, 0);
													pixelBlue.setVisible(false);

													Thread.sleep(2000);
													guriAcendendoPixel.setVisible(false);
													guriColoridoAndando.setVisible(true);

													for (int i = 210; i > 120; i--) {
														guriColoridoAndando.setBounds(i, 235, 64, 64);
														Thread.sleep(10);
													}

													guriColoridoAndando.setVisible(false);
													guriColoridoParado.setVisible(true);

													// DIALOGO DO GURI 16
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoGuri[16].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoGuri[16], dialogoDoGuri, z, 55);
														} else {
															dialogoDoGuri.setVisible(true);
															dialogoDoGuri.setText(TextoDoGuri[16]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													Thread.sleep(1000);
													balaoFixo.setVisible(false);
													dialogoDoGuri.setVisible(false);
													balaoClose.setVisible(true);

													Thread.sleep(1500);
													balaoClose.setVisible(false);
													Thread.sleep(1000);
													guriColoridoParado.setVisible(false);
													pngFundoCidadeGrande.setVisible(false);
													hotBar03.setVisible(false);

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
													imgFinal5.setVisible(false);
													for (int i = 20; i < 255; i++) {
														getContentPane().setBackground(new Color(i, i, i));
														Thread.sleep(12);
													}

													balaoOpen.setVisible(true);
													Thread.sleep(1800);
													balaoOpen.setVisible(false);
													balaoFixo.setVisible(true);

													guriColoridoParado.setVisible(true);
													zeroGif.setVisible(true);

													// DIALOGO DO GURI 17
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoGuri[17].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoGuri[17], dialogoDoGuri, z, 55);
														} else {
															dialogoDoGuri.setVisible(true);
															dialogoDoGuri.setText(TextoDoGuri[17]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													Thread.sleep(2000);
													// DIALOGO DO ZERO 0
													dialogoDoGuri.setVisible(false);
													dialogoDoZero.setVisible(true);
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoZero[0].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoZero[0], dialogoDoZero, z, 55);
														} else {
															dialogoDoZero.setVisible(true);
															dialogoDoZero.setText(TextoDoZero[0]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													Thread.sleep(2000);
													// DIALOGO DO ZERO 1
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoZero[1].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoZero[1], dialogoDoZero, z, 55);
														} else {
															dialogoDoZero.setVisible(true);
															dialogoDoZero.setText(TextoDoZero[1]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													// MOSTRA OS BOTÕES
													Op1NoSelected.setBounds(150, 155, 100, 30);
													Op1NoSelected.setVisible(true);
													Op1Selected.setBounds(150, 155, 100, 30);
													Op1Selected.setVisible(false);

													Op2NoSelected.setBounds(350, 155, 100, 30);
													Op2NoSelected.setVisible(true);
													Op2Selected.setBounds(350, 155, 100, 30);
													Op2Selected.setVisible(false);

													// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
													opcaoReal = 0;
													fazerEscolha = true;
													op = "1";
													// ESPERA O USUARIO ESCOLHER
													while (opcaoReal == 0) {
														System.out.print("Esperando Resposta");
													}
													fazerEscolha = false;

													// DEIXA AS OPÇÕES INVISIVEIS
													Op1NoSelected.setVisible(false);
													Op1Selected.setVisible(false);
													Op2NoSelected.setVisible(false);
													Op2Selected.setVisible(false);

													if (opcaoReal == 1) {
														Thread.sleep(1000);
														balaoFixo.setVisible(false);
														dialogoDoGuri.setVisible(false);
														balaoClose.setVisible(true);

														Thread.sleep(1500);
														balaoClose.setVisible(false);
														Thread.sleep(1000);
														guriColoridoParado.setVisible(false);
														zeroGif.setVisible(false);

														imgFinal.setVisible(true);

													} else if (opcaoReal == 2) {
														// DIALOGO DO ZERO 2
														setVisible(false);
														pixelGetSet.setUpdateCheck("1");
														dao.atualizar(2);
														dao.buscar();
														new controleFase();
													}

												} else {
													// DIALOGO DO GURI 14
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoGuri[14].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoGuri[14], dialogoDoGuri, z, 55);
														} else {
															dialogoDoGuri.setVisible(true);
															dialogoDoGuri.setText(TextoDoGuri[14]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													// GURI PEGANDO O PIXEL
													pixelApagado.setBounds(220, 235, 64, 64);
													pixelApagado.setVisible(true);
													Thread.sleep(1500);
													pixelApagado.setVisible(false);
													pixelBlue.setBounds(220, 235, 64, 64);
													pixelBlue.setVisible(true);
													Thread.sleep(2400);
													guriColoridoParado.setVisible(false);
													guriColoridoAndando.setVisible(true);
													for (int i = 120; i < 210; i++) {
														guriColoridoAndando.setBounds(i, 235, 64, 64);
														Thread.sleep(10);
													}
													hotBar02.setVisible(false);
													hotBar03.setVisible(true);
													guriColoridoAndando.setVisible(false);
													guriAcendendoPixel.setBounds(210, 225, 65, 75);
													guriAcendendoPixel.setVisible(true);
													audio("guriPegandoPixelAudio", 12, 0, 0);
													pixelBlue.setVisible(false);

													Thread.sleep(2000);
													guriAcendendoPixel.setVisible(false);
													guriColoridoAndando.setVisible(true);

													for (int i = 210; i > 120; i--) {
														guriColoridoAndando.setBounds(i, 235, 64, 64);
														Thread.sleep(10);
													}

													guriColoridoAndando.setVisible(false);
													guriColoridoParado.setVisible(true);

													// DIALOGO DO GURI 16
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoGuri[16].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoGuri[16], dialogoDoGuri, z, 55);
														} else {
															dialogoDoGuri.setVisible(true);
															dialogoDoGuri.setText(TextoDoGuri[16]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													Thread.sleep(1000);
													balaoFixo.setVisible(false);
													dialogoDoGuri.setVisible(false);
													balaoClose.setVisible(true);

													Thread.sleep(1500);
													balaoClose.setVisible(false);
													Thread.sleep(1000);
													guriColoridoParado.setVisible(false);
													pngFundoCidadeGrande.setVisible(false);
													hotBar03.setVisible(false);

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
													imgFinal5.setVisible(false);
													for (int i = 20; i < 255; i++) {
														getContentPane().setBackground(new Color(i, i, i));
														Thread.sleep(12);
													}

													balaoOpen.setVisible(true);
													Thread.sleep(1800);
													balaoOpen.setVisible(false);
													balaoFixo.setVisible(true);

													guriColoridoParado.setVisible(true);
													zeroGif.setVisible(true);

													// DIALOGO DO GURI 17
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoGuri[17].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoGuri[17], dialogoDoGuri, z, 55);
														} else {
															dialogoDoGuri.setVisible(true);
															dialogoDoGuri.setText(TextoDoGuri[17]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													Thread.sleep(2000);
													// DIALOGO DO ZERO 3
													dialogoDoGuri.setVisible(false);
													dialogoDoZero.setVisible(true);
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoZero[3].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoZero[3], dialogoDoZero, z, 55);
														} else {
															dialogoDoZero.setVisible(true);
															dialogoDoZero.setText(TextoDoZero[3]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													Thread.sleep(2000);
													// DIALOGO DO ZERO 1
													podePular = true;
													palavra = "";
													for (int z = 0; z < TextoDoZero[1].length(); z++) {
														if (!pularDialog) {
															TextEffect(TextoDoZero[1], dialogoDoZero, z, 55);
														} else {
															dialogoDoZero.setVisible(true);
															dialogoDoZero.setText(TextoDoZero[1]);
															pularDialog = false;
															break;
														}
													}
													podePular = false;

													// MOSTRA OS BOTÕES
													Op1NoSelected.setBounds(150, 155, 100, 30);
													Op1NoSelected.setVisible(true);
													Op1Selected.setBounds(150, 155, 100, 30);
													Op1Selected.setVisible(false);

													Op2NoSelected.setBounds(350, 155, 100, 30);
													Op2NoSelected.setVisible(true);
													Op2Selected.setBounds(350, 155, 100, 30);
													Op2Selected.setVisible(false);

													// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
													opcaoReal = 0;
													fazerEscolha = true;
													op = "1";
													// ESPERA O USUARIO ESCOLHER
													while (opcaoReal == 0) {
														System.out.print("Esperando Resposta");
													}
													fazerEscolha = false;

													// DEIXA AS OPÇÕES INVISIVEIS
													Op1NoSelected.setVisible(false);
													Op1Selected.setVisible(false);
													Op2NoSelected.setVisible(false);
													Op2Selected.setVisible(false);

													if (opcaoReal == 1) {
														Thread.sleep(1000);
														balaoFixo.setVisible(false);
														dialogoDoGuri.setVisible(false);
														balaoClose.setVisible(true);

														Thread.sleep(1500);
														balaoClose.setVisible(false);
														Thread.sleep(1000);
														guriColoridoParado.setVisible(false);
														zeroGif.setVisible(false);

														imgFinal.setVisible(true);

													} else if (opcaoReal == 2) {
														// DIALOGO DO ZERO 2
														setVisible(false);
														pixelGetSet.setUpdateCheck("1");
														dao.atualizar(2);
														dao.buscar();
														new controleFase();
													}
												}

												// ESCOLHER ROUBAR
											} else if (opcaoReal == 2) {
												// DIALOGO DO GURI 15
												podePular = true;
												palavra = "";
												for (int z = 0; z < TextoDoGuri[15].length(); z++) {
													if (!pularDialog) {
														TextEffect(TextoDoGuri[15], dialogoDoGuri, z, 55);
													} else {
														dialogoDoGuri.setVisible(true);
														dialogoDoGuri.setText(TextoDoGuri[15]);
														pularDialog = false;
														break;
													}
												}
												podePular = false;

												// GURI PEGANDO O PIXEL
												pixelApagado.setBounds(220, 235, 64, 64);
												pixelApagado.setVisible(true);
												Thread.sleep(1500);
												pixelApagado.setVisible(false);
												pixelBlue.setBounds(220, 235, 64, 64);
												pixelBlue.setVisible(true);
												Thread.sleep(2400);
												guriColoridoParado.setVisible(false);
												guriColoridoAndando.setVisible(true);
												for (int i = 120; i < 210; i++) {
													guriColoridoAndando.setBounds(i, 235, 64, 64);
													Thread.sleep(10);
												}
												hotBar02.setVisible(false);
												hotBar03.setVisible(true);
												guriColoridoAndando.setVisible(false);
												guriAcendendoPixel.setBounds(210, 225, 65, 75);
												guriAcendendoPixel.setVisible(true);
												audio("guriPegandoPixelAudio", 12, 0, 0);
												pixelBlue.setVisible(false);

												Thread.sleep(2000);
												guriAcendendoPixel.setVisible(false);
												guriColoridoAndando.setVisible(true);

												for (int i = 210; i > 120; i--) {
													guriColoridoAndando.setBounds(i, 235, 64, 64);
													Thread.sleep(10);
												}

												guriColoridoAndando.setVisible(false);
												guriColoridoParado.setVisible(true);

												// DIALOGO DO GURI 16
												podePular = true;
												palavra = "";
												for (int z = 0; z < TextoDoGuri[16].length(); z++) {
													if (!pularDialog) {
														TextEffect(TextoDoGuri[16], dialogoDoGuri, z, 55);
													} else {
														dialogoDoGuri.setVisible(true);
														dialogoDoGuri.setText(TextoDoGuri[16]);
														pularDialog = false;
														break;
													}
												}
												podePular = false;

												Thread.sleep(1000);
												balaoFixo.setVisible(false);
												dialogoDoGuri.setVisible(false);
												balaoClose.setVisible(true);

												Thread.sleep(1500);
												balaoClose.setVisible(false);
												Thread.sleep(1000);
												guriColoridoParado.setVisible(false);
												pngFundoCidadeGrande.setVisible(false);
												hotBar03.setVisible(false);

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
												imgFinal5.setVisible(false);
												for (int i = 20; i < 255; i++) {
													getContentPane().setBackground(new Color(i, i, i));
													Thread.sleep(12);
												}

												balaoOpen.setVisible(true);
												Thread.sleep(1800);
												balaoOpen.setVisible(false);
												balaoFixo.setVisible(true);

												guriColoridoParado.setVisible(true);
												zeroGif.setVisible(true);

												// DIALOGO DO GURI 17
												podePular = true;
												palavra = "";
												for (int z = 0; z < TextoDoGuri[17].length(); z++) {
													if (!pularDialog) {
														TextEffect(TextoDoGuri[17], dialogoDoGuri, z, 55);
													} else {
														dialogoDoGuri.setVisible(true);
														dialogoDoGuri.setText(TextoDoGuri[17]);
														pularDialog = false;
														break;
													}
												}
												podePular = false;

												Thread.sleep(2000);
												// DIALOGO DO ZERO 3
												dialogoDoGuri.setVisible(false);
												dialogoDoZero.setVisible(true);
												podePular = true;
												palavra = "";
												for (int z = 0; z < TextoDoZero[3].length(); z++) {
													if (!pularDialog) {
														TextEffect(TextoDoZero[3], dialogoDoZero, z, 55);
													} else {
														dialogoDoZero.setVisible(true);
														dialogoDoZero.setText(TextoDoZero[3]);
														pularDialog = false;
														break;
													}
												}
												podePular = false;

												Thread.sleep(2000);
												// DIALOGO DO ZERO 1
												podePular = true;
												palavra = "";
												for (int z = 0; z < TextoDoZero[1].length(); z++) {
													if (!pularDialog) {
														TextEffect(TextoDoZero[1], dialogoDoZero, z, 55);
													} else {
														dialogoDoZero.setVisible(true);
														dialogoDoZero.setText(TextoDoZero[1]);
														pularDialog = false;
														break;
													}
												}
												podePular = false;

												// MOSTRA OS BOTÕES
												Op1NoSelected.setBounds(150, 155, 100, 30);
												Op1NoSelected.setVisible(true);
												Op1Selected.setBounds(150, 155, 100, 30);
												Op1Selected.setVisible(false);

												Op2NoSelected.setBounds(350, 155, 100, 30);
												Op2NoSelected.setVisible(true);
												Op2Selected.setBounds(350, 155, 100, 30);
												Op2Selected.setVisible(false);

												// PERMITE O USUARIO FAZER AS ESCOLHAS NAS SETINHAS
												opcaoReal = 0;
												fazerEscolha = true;
												op = "1";
												// ESPERA O USUARIO ESCOLHER
												while (opcaoReal == 0) {
													System.out.print("Esperando Resposta");
												}
												fazerEscolha = false;

												// DEIXA AS OPÇÕES INVISIVEIS
												Op1NoSelected.setVisible(false);
												Op1Selected.setVisible(false);
												Op2NoSelected.setVisible(false);
												Op2Selected.setVisible(false);

												if (opcaoReal == 1) {
													Thread.sleep(1000);
													balaoFixo.setVisible(false);
													dialogoDoGuri.setVisible(false);
													balaoClose.setVisible(true);

													Thread.sleep(1500);
													balaoClose.setVisible(false);
													Thread.sleep(1000);
													guriColoridoParado.setVisible(false);
													zeroGif.setVisible(false);

													imgFinal.setVisible(true);

												} else if (opcaoReal == 2) {
													// DIALOGO DO ZERO 2
													setVisible(false);
													pixelGetSet.setUpdateCheck("1");
													dao.atualizar(2);
													dao.buscar();
													new controleFase();
												}

											}
										}

									}
								}

							}
						}

					}

				}

			} catch (InterruptedException ex) {
			} catch (UnsupportedAudioFileException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (LineUnavailableException e) {
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
				if (podePular) {
					pularDialog = true;					
				}else {
					pularDialog = false;
				}
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

	// SOM
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
	
	public void openDialogBox() {
		try {
			balaoOpen.setVisible(true);
			Thread.sleep(1800);
			balaoOpen.setVisible(false);
			balaoFixo.setVisible(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void closeDialogBox() {
		try {
			Thread.sleep(500);
			balaoFixo.setVisible(false);
			dialogoDoGuri.setVisible(false);
			balaoClose.setVisible(true);
			Thread.sleep(1500);
			balaoClose.setVisible(false);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
