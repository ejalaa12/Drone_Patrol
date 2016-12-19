package elementsG;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import organisationG.Dimensionnement;

import elements.BateauSauvetage;

public class BateauSauvetageG{

	private JPanel pan;
	private int taille;
	private static Image img2;
	static{ 
		try {
			img2 = ImageIO.read(new File("canot.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	private Dimensionnement d;
	private int px,py;
	private boolean finDep=false;
	private BateauSauvetage monBateau;
	private int[] pos_avant, pos_apres;
	
	public BateauSauvetageG(BateauSauvetage monBateau, JPanel pan, Dimensionnement d) {
		this.monBateau = monBateau;
		this.pan = pan;
		this.d =d;
		monBateau.setPositionGraphique(d.deduirePositionGraphique(monBateau.getPosition())); 
		taille = d.getTailleBateau();
//		pan.setOpaque(false);
	}
	
	public void affiche(Graphics g){
		g.drawImage(img2, monBateau.getPositionGraphique()[0]-taille/2, monBateau.getPositionGraphique()[1]-taille/2, taille, taille, this.pan);
		
		
	}
	
	public void presentation(){
		monBateau.presentation();
		System.out.print("ma position graphique est: ");
		System.out.println(Arrays.toString(monBateau.getPositionGraphique()));
	}
	
	public synchronized void deplacer(){
		pos_avant = monBateau.getPositionGraphique().clone();
		pos_apres = d.deduirePositionGraphique(monBateau.getPosition().clone());
		px = (int) ((pos_apres[0] - pos_avant[0])/10);
		py = (int) ((pos_apres[1] - pos_avant[1])/10);
		
		Thread moving = new Thread() {
			public void run(){
				try {
					super.run();
					while(!depasse(pos_avant, monBateau.getPositionGraphique(), pos_apres, taille) && d.distance(pos_apres, monBateau.getPositionGraphique())>taille/3 ){
						monBateau.ajoutPositionGraphiqueX(px); 
						monBateau.ajoutPositionGraphiqueY(py); 
						sleep(40);
						pan.repaint();

					}					
					monBateau.setPositionGraphique(d.deduirePositionGraphique(monBateau.getPosition()));
					sleep(40);
					pan.repaint();
					finDep=true;
				} catch (InterruptedException ex){
					
				}
			}
		};
		moving.start();
		
		
	}
	
	public boolean depasse(int[] depart, int[] position, int[] arrivee, int taille){
		int direction;
		if (arrivee[0]>depart[0]){
			if (arrivee[1]>depart[1]) direction = 0; // direction: bas droite
			else if (arrivee[1]<depart[1]) direction = 1; //direction: haut droite
			else direction = 2; //direction: droite toute!
		}
		else if (arrivee[0]<depart[0]){
			if (arrivee[1]>depart[1]) direction = 3; // direction: bas gauche
			else if (arrivee[1]<depart[1]) direction =4; // direction: haut gauche
			else direction = 5; //direction: gauche toute!
		}
		else
			if (arrivee[1]>depart[1]) direction = 6; // direction: bas toute
			else if (arrivee[1]<depart[1]) direction = 7; // direction: haut toute
			else direction = 8; //direction: pas bouger !
		if (d.distance(arrivee, position)>d.getTailleHexagone()+30) direction = 9;
		switch (direction){
		case 0: // direction: bas droite
			if (position[0]<arrivee[0] || position[1]<arrivee[1]) return false;
			else return true;
		case 1: //direction: haut droite
			if (position[0]<arrivee[0] || position[1]>arrivee[1]) return false;
			else return true;
		case 2: //direction: droite toute!
			if (position[0]<arrivee[0]) return false;
			else return true;
		case 3: // direction: bas gauche
			if (position[0]>arrivee[0] || position[1]<arrivee[1]) return false;
			else return true;
		case 4: // direction: haut gauche
			if (position[0]>arrivee[0] || position[1]>arrivee[1]) return false;
			else return true;
		case 5: //direction: gauche toute!
			if (position[0]>arrivee[0]) return false;
			else return true;
		case 6: // direction: bas toute
			if (position[1]<arrivee[1]) return false;
			else return true;
		case 7: // direction: haut toute
			if (position[1]>arrivee[1]) return false;
			else return true;
		case 8:
			return true;
		case 9:
			return true;
		default:
			return true;

		}
	}
	public boolean getFini(){
		return finDep;
	}
	
	public static void main(String[] args){
		
	}
}
