package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;
import BancoDeDados.*;

public class Jogador {
	onePixelDAO dao = new onePixelDAO();
	
	private int x, y, xR, yR, xG, yG, xB, yB;;
	private int directX, directY;
	private Image imgPlayer, imgPixelRed, imgPixelGreen, imgPixelBlue;
	private ImageIcon jogadorzinho, pixelRed, pixelGreen, pixelBlue;
	private int largura, altura;
	private String caminhoImg = "./res2/imgPlayer/guriCima000.png";

	private boolean cima = false, baixo = false, direita = false, esquerda = false, correndo = false;
	int tecla = 0;
	
	public Jogador() {
		x = 276;
		y = 125;
		
		xR = x - 15;
		yR = y + 25;
		
		xG = x + 6;
		yG = y + 30;
		
		xB = x + 28;
		yB = y + 25;
		
		new Temporizador().start();
	}

	public void carregar() {
		jogadorzinho = new ImageIcon(caminhoImg);
		imgPlayer = jogadorzinho.getImage();
		
		if(dao.pixel.getPixelR() == 1) {
			pixelRed = new ImageIcon("res2/imgPixels/ChamaPixelVermelho.gif");
			imgPixelRed = pixelRed.getImage();			
		}if (dao.pixel.getPixelG() == 1) {
			pixelGreen = new ImageIcon("res2/imgPixels/ChamaPixelVerde.gif");
			imgPixelGreen = pixelGreen.getImage();			
		}if (dao.pixel.getPixelB() == 1) {
			pixelBlue = new ImageIcon("res2/imgPixels/ChamaPixelAzul.gif");
			imgPixelBlue = pixelBlue.getImage();
		}
		
		altura = 45;
		largura = 45;
	}

	public void atualizar() {
		x += directX;
		y += directY;
		
		xR = xR;
		yR = yR;
		
		xG = xG;
		yG = yG;
		
		xB = xB;
		yB = yB;
 

	}

	public class timer extends Thread {

	}

	public void keyPressed(KeyEvent e) {
		tecla = e.getKeyCode();
		if (e.getKeyCode() == KeyEvent.VK_UP && direita == false && esquerda == false && baixo == false) {
			cima = true;
			directY = -2;
			caminhoImg = "res2/imgPlayer/guriCima001.png";
			if(e.isShiftDown()) {
				correndo = true;
				directY = -4;
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN && direita == false && esquerda == false && cima == false) {
			baixo = true;
			directY = 2;
			caminhoImg = "res2/imgPlayer/guriBaixo001.png";
			if(e.isShiftDown()) {
				correndo = true;
				directY = 4;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT && direita == false && cima == false && baixo == false) {
			esquerda = true;
			directX = -2;
			caminhoImg = "res2/imgPlayer/guriDireita001.png";
			if(e.isShiftDown()) {
				correndo = true;
				directX = -4;
			}

		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT && cima == false && esquerda == false && baixo == false) {
			direita = true;
			directX = 2;
			caminhoImg = "res2/imgPlayer/guriEsquerda001.png";
			if(e.isShiftDown()) {
				correndo = true;
				directX = 4;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			cima = false;
			correndo = false;
			directY = 0;
			caminhoImg = "res2/imgPlayer/guriCima000.png";
			carregar();
		}
		if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			baixo = false;
			correndo = false;
			directY = 0;
			caminhoImg = "res2/imgPlayer/guriBaixo000.png";
			carregar();

		}
		if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			esquerda = false;
			correndo = false;
			directX = 0;
			caminhoImg = "res2/imgPlayer/guriEsquerda000.png";
			carregar();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			direita = false;
			correndo = false;
			directX = 0;
			caminhoImg = "res2/imgPlayer/guriDireita000.png";
			carregar();
		}
	}

	public class Temporizador extends Thread {
		public void run() {
			while (true) {
				System.out.print("");
				if (cima == true) {
					carregar();
					caminhoImg = "res2/imgPlayer/guriCima001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					caminhoImg = "res2/imgPlayer/guriCima002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				} else if (baixo == true) {
					caminhoImg = "res2/imgPlayer/guriBaixo001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					caminhoImg = "res2/imgPlayer/guriBaixo002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				} else if (direita == true) {
					caminhoImg = "res2/imgPlayer/guriDireita001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					caminhoImg = "res2/imgPlayer/guriDireita002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				} else if (esquerda == true) {
					caminhoImg = "res2/imgPlayer/guriEsquerda001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					caminhoImg = "res2/imgPlayer/guriEsquerda002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				}

			}

		}
	}

	
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public void setX(int X) {
		this.x = X;
	}

	public void setY(int Y) {
		this.y = Y;
	}

	public int getxR() {
		return xR;
	}

	public void setxR(int xR) {
		this.xR = xR;
	}

	public int getyR() {
		return yR;
	}

	public void setyR(int yR) {
		this.yR = yR;
	}

	public int getxG() {
		return xG;
	}

	public void setxG(int xG) {
		this.xG = xG;
	}

	public int getyG() {
		return yG;
	}

	public void setyG(int yG) {
		this.yG = yG;
	}

	public int getxB() {
		return xB;
	}

	public void setxB(int xB) {
		this.xB = xB;
	}

	public int getyB() {
		return yB;
	}

	public void setyB(int yB) {
		this.yB = yB;
	}

	public Image getImgPixelRed() {
		return imgPixelRed;
	}

	public Image getImgPixelGreen() {
		return imgPixelGreen;
	}

	public Image getImgPixelBlue() {
		return imgPixelBlue;
	}
	
	public Image getImgPlayer() {
		return imgPlayer;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
	}

	public int getTecla() {
		return tecla;
	}

	public void setTecla(int tecla) {
		this.tecla = tecla;
	}

	public boolean isCima() {
		return cima;
	}

	public boolean isBaixo() {
		return baixo;
	}

	public boolean isDireita() {
		return direita;
	} 

	public boolean isEsquerda() {
		return esquerda;
	}
	
	public boolean isCorrendo() {
		return correndo;
	}

	public void setCima(boolean cima) {
		this.cima = cima;
	}

	public void setBaixo(boolean baixo) {
		this.baixo = baixo;
	}

	public void setDireita(boolean direita) {
		this.direita = direita;
	}

	public void setEsquerda(boolean esquerda) {
		this.esquerda = esquerda;
	}

}
