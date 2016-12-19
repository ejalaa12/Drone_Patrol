package elementsG;

import java.awt.Color;
import java.awt.Graphics;

import organisation.Environnement;

import elements.CaseMeteo;
import elements.Element;

public class CaseMeteoG extends Element{

	private int taille;
	private int alpha;
	private double angle = 2*Math.PI/6;
//	private CaseMeteo maCase;
	private int alpha_apres;
	private int alpha_avant;

	public CaseMeteoG(int[] position, Environnement e, CaseMeteo cm){
		super(position, e);
//		this.maCase = cm;
		this.etat = cm.getEtat();
		this.position = position;
		this.positionGraphique = e.getDim().deduirePositionGraphique(this.position);
		this.taille = e.getDim().getTailleDrone();
		this.alpha = 0;
	}

	public void affiche(Graphics g){
		int[] x = new int[6];
		for (int i=0;i<6;i++){
			x[i] = (int) Math.floor(positionGraphique[0] + taille*Math.cos(angle*i));
		}
		int[] y = new int[6];
		for (int i=0;i<6;i++){
			y[i] = (int) Math.ceil(positionGraphique[1] + taille*Math.sin(angle*i));
		}
		try{
			g.setColor(new Color(0,0,0,alpha));
		} catch (IllegalArgumentException ex){
		}
		g.fillPolygon(x, y, 6);
	}

	public synchronized void modifier(int etat){
		this.alpha_avant = deduireAlpha(this.etat);
		this.alpha_apres = deduireAlpha(etat);

		Thread moving = new Thread() {
			public void run(){
				try {
					super.run();
					while (alpha!=alpha_apres){
						if(alpha_avant-alpha_apres<=0)
							alpha++;
						else
							alpha--;
						if (alpha>255){
							alpha = 255;
							break;}
						if (alpha<0){
							alpha = 0;
							break;}
						sleep(20);
						e.getFen().getPanel_meteo().repaint();
					}
					sleep(20);
					e.getFen().getPanel_meteo().repaint();
				} catch (InterruptedException ex){

				}
			}
		};
		moving.start();
	}
	
	public int deduireAlpha(int etat){
		switch(etat){
		case 1:
			return 0;
		case 2:
			return 90;
		case 3:
			return 140;
		case 4:
			return 255;
		default:
			return 0;
		}
	}

}
