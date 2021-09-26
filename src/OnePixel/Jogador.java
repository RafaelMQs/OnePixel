package OnePixel;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

public class Jogador {
	private int x, y, xB, yB;
	private int directX, directY;
	private Image imgPlayer, imgPet;
	private ImageIcon jogadorzinho, petzinho;
	private int largura, altura;
	private String caminhoImg = "./res2/imgPlayer/guriCima000.png";

	private boolean cima = false, baixo = false, direita = false, esquerda = false, correndo = false;
	int tecla = 0;
	
	public Jogador() {
		x = 276;
		y = 125;
		
		xB = x;
		yB = y;
		
		new Temporizador().start();
	}

	public void carregar() {
		jogadorzinho = new ImageIcon(caminhoImg);
		imgPlayer = jogadorzinho.getImage();
		
		petzinho = new ImageIcon("res2/imgPlayer/gato.png");
		imgPet = petzinho.getImage();
		
		altura = 45;
		largura = 45;
	}

	public void atualizar() {
		x += directX;
		y += directY;
		
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
					yB = y + 25;
					xB = x;
					carregar();
					caminhoImg = "res2/imgPlayer/guriCima001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					yB = y + 35;
					caminhoImg = "res2/imgPlayer/guriCima002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				} else if (baixo == true) {
					yB = y - 15;
					xB = x;
					caminhoImg = "res2/imgPlayer/guriBaixo001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					yB = y - 25;
					caminhoImg = "res2/imgPlayer/guriBaixo002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				} else if (direita == true) {
					yB = y;
					xB = x - 15;
					caminhoImg = "res2/imgPlayer/guriDireita001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					xB = x - 25;
					caminhoImg = "res2/imgPlayer/guriDireita002.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
				} else if (esquerda == true) {
					yB = y;
					xB = x +30;
					caminhoImg = "res2/imgPlayer/guriEsquerda001.png";
					carregar();
					try {sleep(180);} catch (InterruptedException e) {e.printStackTrace();}
					xB = x + 40;
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
	
	public int getXB() {
		return xB;
	}

	public int getYB() {
		return yB;
	}

	public void setX(int X) {
		this.x = X;
	}

	public void setY(int Y) {
		this.y = Y;
	}

	public void setXB(int X) {
		this.xB = X;
	}

	public void setYB(int Y) {
		this.yB = Y;
	}
	
	public Image getImgPlayer() {
		return imgPlayer;
	}
	
	public Image getImgPet() {
		return imgPet;
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

}
