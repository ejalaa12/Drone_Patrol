package elementsG;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;

import organisationG.Dimensionnement;

public class AlerteG {

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private int[] positionGraphique;
	private int taille;
	private int decalMax;
	private int decal;
	private JPanel pan;
	private Clip sound;

	public AlerteG(int[] positionGraphique, JPanel pan, Dimensionnement d){
		this.positionGraphique = positionGraphique;
		this.pan = pan;
		this.decalMax = 250;
		sound = loadClip("/ressources/son/splash1.wav");
	}

	public void affiche(Graphics g){
		g.setColor(Color.red);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setStroke(new BasicStroke(3));
		g2d.drawOval(positionGraphique[0]-decal/2,positionGraphique[1]-decal/2, taille+decal, taille+decal);
		//		for(int i=0; i<5; i++){
		//	          g.drawOval(positionGraphique[0]-decal+i,positionGraphique[1]-decal+i, taille-(i*2), taille-(i*2));
		//	     }
	}

	public void alerter(){
		taille = 0;
		decal = 3;
		playClip(sound);
		Thread growing = new Thread(){
			public void run(){
				try{
					super.run();
					while (decal<=decalMax){
						decal++;
						sleep(3);
						affiche(pan.getGraphics());
					}
					while (decal>=0){
						decal--;
						sleep(1);
						affiche(pan.getGraphics());
					}
					sleep(200);
					taille = 0;
					decal = 0;
				}catch (InterruptedException ex){

				}
			}
		};
		growing.start();
		tk.beep();
	}

	public Clip loadClip(String filename){
		Clip clip = null;
		try{
			AudioInputStream audio = AudioSystem.getAudioInputStream( getClass().getResource(filename));
			clip = AudioSystem.getClip();
			clip.open(audio);
			
			
		} catch (Exception e){
			e.printStackTrace();
		}
		return clip;
	}
	
	public void playClip( Clip clip){
		clip.stop();
		clip.start();
	}
	public static void main(String args[]){
	}
}
