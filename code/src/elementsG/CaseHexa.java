package elementsG;

import java.awt.Color;
import java.awt.Graphics;

import organisation.Environnement;

import elements.Element;

public class CaseHexa extends Element{

	private int taille;
	private double angle = 2*Math.PI/6;
	private int[] x,y;
	
	
	public CaseHexa(int[] position, Environnement e) {
		super(position, e);
		this.positionGraphique = e.getDim().deduirePositionGraphique(position);
		taille = e.getDim().getTailleHexagone();
		creerXY();
	}
	
	public void affiche(Graphics g){
		g.setColor(Color.black);
		g.drawPolygon(x,y,6);

		// on affiche ici les coordonn�es de l'hexagone
//		String coord = Integer.toString(position[0])+","+Integer.toString(position[1]);
//		g.drawString(coord, this.positionGraphique[0]-10, this.positionGraphique[1]);
//		pan.repaint(positionGraphique[0]-taille/2, positionGraphique[1]-taille/2, taille, taille);
	}
	
	public void creerXY(){
		x = new int[6];
		for (int i=0;i<6;i++){
			x[i] = (int) Math.floor(positionGraphique[0] + taille*Math.cos(angle*i));
		}
		y = new int[6];
		for (int i=0;i<6;i++){
			y[i] = (int) Math.ceil(positionGraphique[1] + taille*Math.sin(angle*i));
		}
	}
	public int[] getX() {
		return x;
	}

	public void setX(int[] x) {
		this.x = x;
	}

	public int[] getY() {
		return y;
	}

	public void setY(int[] y) {
		this.y = y;
	}

	public boolean dansCase(int x, int y){
		// TODO verifier si la souris est dans un hexagone
		
		return false;
	}
}
