package elementsG;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import organisationG.Dimensionnement;
import elements.Drone;

public class DroneG{

	private JPanel pan;
	private int taille;
	//	private static Image img = Toolkit.getDefaultToolkit().createImage("/ressources/img/s32/yacht.png"); //celle ci ne semble pas marcher
	
	private static Image img; //celle ci ne semble pas marcher
	static{ 
		try {
			img = ImageIO.read(new File( DroneG.class.getResource("/ressources/img/drone.png").toURI() ));
		} catch (IOException e){
			e.printStackTrace();
		} catch (URISyntaxException ui) {
			ui.printStackTrace();
		}
	}
	private static Image img2;
	static{ 
		try {
			img2 = ImageIO.read(new File( DroneG.class.getResource("/ressources/img/drone2.png").toURI() ));
		} catch (IOException e){
			e.printStackTrace();
		} catch (URISyntaxException ui) {
			ui.printStackTrace();
		}
	}
	private static Image img3; //celle ci ne semble pas marcher
	static{ 
		try {
			img3 = ImageIO.read(new File( DroneG.class.getResource("/ressources/img/drone3.png").toURI() ));
		} catch (IOException e){
			e.printStackTrace();
		} catch (URISyntaxException ui) {
			ui.printStackTrace();
		}
	}
	private Dimensionnement d;
	private int px,py;
	private boolean finDep=false;
	private Drone monDrone;
	private int a;
	private int[] pos_avant, pos_apres;
	private Image imgChoisie;

	public DroneG(Drone monDrone, JPanel pan, Dimensionnement d) {
		this.monDrone = monDrone;
		this.pan = pan;
		this.d =d;
		monDrone.setPositionGraphique(d.deduirePositionGraphique(monDrone.getPosition())); 
		taille = d.getTailleDrone();
		int a = (int) (Math.random()*3);
		if (a==0)
			this.imgChoisie = img;
		else if (a==1) this.imgChoisie = img2;
		else this.imgChoisie = img3;
		//		pan.setOpaque(false);
	}

	public void affiche(Graphics g){
		g.drawImage(imgChoisie, monDrone.getPositionGraphique()[0]-taille/2, monDrone.getPositionGraphique()[1]-taille/2, taille, taille, this.pan);


	}

	public void presentation(){
		monDrone.presentation();
		System.out.print("ma position graphique est: ");
		System.out.println(Arrays.toString(monDrone.getPositionGraphique()));
	}

	public synchronized void deplacer(){
		pos_avant = monDrone.getPositionGraphique().clone();
		pos_apres = d.deduirePositionGraphique(monDrone.getPosition().clone());
		px = (int) ((pos_apres[0] - pos_avant[0])/10);
		py = (int) ((pos_apres[1] - pos_avant[1])/10);
		a = 20;
		if(monDrone.getEtat() == 4)
			a = 160;

		Thread moving = new Thread() {
			public void run(){
				try {
					super.run();
					while(!depasse(pos_avant, monDrone.getPositionGraphique(), pos_apres, taille) && d.distance(pos_apres, monDrone.getPositionGraphique())>taille/3 ){
						monDrone.ajoutPositionGraphiqueX(px); 
						monDrone.ajoutPositionGraphiqueY(py);
						sleep(a);
						pan.repaint();
					}
					monDrone.setPositionGraphique(d.deduirePositionGraphique(monDrone.getPosition()));
					sleep(a);
					pan.repaint();
					finDep=true;
				} catch (InterruptedException ex){
				}
			}
		};
		moving.start();


	} 

	public boolean getFini(){
		return finDep;
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
		// cas ou le drone est trop loin
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

	public static void main(String[] args){
	}
}
