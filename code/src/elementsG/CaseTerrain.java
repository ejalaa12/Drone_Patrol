package elementsG;


import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import javax.imageio.ImageIO;
import organisation.Environnement;


public class CaseTerrain{

	private int taille;
	private int[] positionGraphique;
	private int[] position;
	private Environnement e;
	private static Image terre;
	static{ 
		try {
			terre = ImageIO.read(new File("TerreG.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	private static Image mer;
	static{ 
		try {
			mer = ImageIO.read(new File("MerG.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	private static Image port;
	static{ 
		try {
			port = ImageIO.read(new File("PortG.png"));
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	
	public CaseTerrain(int[] position, Environnement e) {
		this.e = e;
		this.position = position;
		positionGraphique = e.getDim().deduirePositionGraphique(position);
		taille = e.getDim().getTailleHexagone()*2;
		
	}
	
	public void affiche(Graphics g){
		if (Arrays.equals(position, e.getPort())){
			g.drawImage(port, positionGraphique[0]-taille/2, positionGraphique[1]-taille/2, taille, taille, e.getFen().getPanel_meteo());
		}
		else if(position[1]<=e.getLargeurCote()){
			g.drawImage(terre, positionGraphique[0]-taille/2, positionGraphique[1]-taille/2, taille, taille, e.getFen().getPanel_meteo());
		}
		else{
			g.drawImage(mer, positionGraphique[0]-taille/2, positionGraphique[1]-taille/2, taille, taille, e.getFen().getPanel_meteo());
		}
		
		
	}
}
