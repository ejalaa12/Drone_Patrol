package elementsG;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

import organisationG.Dimensionnement;

import elements.Naufrage;

public class NaufrageG{

	private JPanel pan;
	private int taille;
//	private static Image img = Toolkit.getDefaultToolkit().createImage("/ressources/img/s32/yacht.png"); //celle ci ne semble pas marcher
	private static Image img2;
	static{ 
		try {
			img2 = ImageIO.read(new File("noye.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	private Dimensionnement d;
	private int px,py;
	private boolean finDep=false;
	private Naufrage monNaufrage;
	
	public NaufrageG(Naufrage monNaufrage, JPanel pan, Dimensionnement d) {
		this.monNaufrage = monNaufrage;
		this.pan = pan;
		this.d =d;
		monNaufrage.setPositionGraphique(d.deduirePositionGraphique(monNaufrage.getPosition())); 
		taille = d.getTailleNaufrage();
//		pan.setOpaque(false);
	}
	
	public void affiche(Graphics g){
		g.drawImage(img2, monNaufrage.getPositionGraphique()[0]-taille/2, monNaufrage.getPositionGraphique()[1]-taille/2, taille, taille, this.pan);
		
		
	}
	
	public void presentation(){
		System.out.print("ma position graphique est: ");
		System.out.println(Arrays.toString(monNaufrage.getPositionGraphique()));
	}
	
	public synchronized void deplacer(){
		int[] pos_avant = monNaufrage.getPositionGraphique().clone();
		int[] pos_apres = d.deduirePositionGraphique(monNaufrage.getPosition().clone());
		px = (int) ((pos_apres[0] - pos_avant[0])/10);
		py = (int) ((pos_apres[1] - pos_avant[1])/10);
		
		Thread moving = new Thread() {
			public void run(){
				try {
					super.run();
					do{
						monNaufrage.ajoutPositionGraphiqueX(px); 
						monNaufrage.ajoutPositionGraphiqueY(py);
						sleep(160);
						pan.repaint();
					}while (Math.abs(monNaufrage.getPositionGraphique()[0]-d.deduirePositionGraphique(monNaufrage.getPosition())[0])>taille/2 || Math.abs(monNaufrage.getPositionGraphique()[1]-d.deduirePositionGraphique(monNaufrage.getPosition())[1])>taille/2);
					monNaufrage.setPositionGraphique(d.deduirePositionGraphique(monNaufrage.getPosition()));
					sleep(160);
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
	
	public static void main(String[] args){
		
	}
}
