package BossMorcego;

import java.awt.Image;

import javax.swing.ImageIcon;

import OnePixel.Jogador;



public class tiroDoInimigo {
	private int xTiro, yTiro;
	private int stopTiro;
	private inimigo inimigo;
	private Image imgTiro;
	private ImageIcon iconTiro;
	private int largura, altura, velocidade = 20;	
	private String tiroImg = "res2/imgCenarioBoss1/tiro.png";

	public tiroDoInimigo() {

		new tiroReload().start();

	}
	
	public void carregarComponents() {

		iconTiro = new ImageIcon(tiroImg);
		imgTiro = iconTiro.getImage();
		inimigo = new inimigo();
		xTiro = inimigo.getX() ;
		yTiro = inimigo.getY();

	
	
				
	}
	
	public void atualizar() {
		xTiro = xTiro;
		yTiro = yTiro;
		

	}
	

	 public class tiroReload extends Thread {
	    	public void run() {
	    		try {
					sleep(500);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
	    		while (true) {
		    		if(stopTiro == 0) {
		    			for(int i = 0; i < 390; i++) {
			    			//System.out.println("Contagem"+i);
							try {
								xTiro -=10;
								sleep(velocidade);
								if(xTiro <= 20) {
								    xTiro = inimigo.getX();
								    yTiro = inimigo.getY() + 50;   
								}

							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}


			    		}
			    		xTiro = inimigo.getX();
			    		yTiro = inimigo.getY() + 50;
		    		}else {
		    			break;
 
		    		}
	    		}
	  	
	    	}

	    }

		
		public int getStopTiro() {
			return stopTiro;
		}

		public void setStopTiro(int stopTiro) {
			this.stopTiro = stopTiro;
		}
		
		public int getVelocidade() {
			return velocidade;
		}

		public void setVelocidade(int velocidade) {
			this.velocidade = velocidade;
		}

		public Image getImgTiro() {
			return imgTiro;
		}
	 
		public int getXtiro() {
			return xTiro;
		}
		
		public int getYtiro() {
			return yTiro;
		}
		
		public void setXtiro(int X) {
			this.xTiro = X;
		}

		public void setYtiro(int Y) {
			this.yTiro = Y;
		}
	
}
