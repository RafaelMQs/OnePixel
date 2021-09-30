package BossMorcego;

import java.awt.*;
import javax.swing.*;

public class inimigo {
	private int x, y, xTiro, yTiro;
	private int directX, directY;
	private Image imgInimigo;
	private ImageIcon iconInimigo;
	private int largura, altura;
	private String caminhoImg = "res2/imgCenarioBoss1/BossV1.gif";

	private boolean cima = false, baixo = false, direita = false, esquerda = false, parar = false;

	public inimigo() {
		// TODO Auto-generated constructor stub
		x = 450;
		y = 150;
		new temporizador().start();
	}

	public void carregar() {
		iconInimigo = new ImageIcon(caminhoImg);
		imgInimigo = iconInimigo.getImage();
		altura = 150;
		largura = 132;
	}

	public void atualizar() {
		x = x;
		y = y;

	}

	public class temporizador extends Thread {
		public void run() {
			while (true) {
				if (!parar) {
					try {
						int sleepRandom = (int) (Math.random() * 50);
						for (int i = 10; y <= 130; i++) {
							y += i;
							sleep(60);
						}
						sleep(1000);
						for (int i = -10; y >= -2; i--) {
							y += i;
							sleep(60);
						}
						sleep(1000);

						for (int i = -10; y <= 20; i--) {
							y -= i;
							sleep(60);
						}
						sleep(1000);

					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
	}

	public Image getImgInimigo() {
		return imgInimigo;
	}

	public int getLargura() {
		return largura;
	}

	public int getAltura() {
		return altura;
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

	public boolean isParar() {
		return parar;
	}

	public void setParar(boolean parar) {
		this.parar = parar;
	}
	
	

}
