package organisation;

import java.util.Arrays;

import elements.Alerte;
import elements.Drone;

public class Utile {

	public int[] trouverPort(int[][] tab){
		int max=0;
		int[] port= new int[2];
		for (int i=0;i<tab.length;i++){
			if(tab[i][1]>max){
				port=tab[i];
			}
		}
		return port;
	}

	public int trouverTailleCote(int[][] tab, int y){
		int max=0;
		for (int i=0;i<tab.length;i++){
			if(tab[i][1]>max && tab[i][0]==y){
				max=tab[i][0];
			}
		}
		return max;
	}

	public String getNom(int i){
		String[] nom = {"a", "z", "e", "r", "t", "y", "u", "i", "o", "p", "q", "s", "d", "f", "g"};
		return nom[i];
	}

	public boolean sortieCarte(int[] position, Environnement e){
		if(position[0]<2)
			return true;
		if(position[0]>e.getHauteurTotalCarte()-1)
			return true;
		if(position[1]>e.getLargeurTotalCarte())
			return true;
		if(position[1]<1)
			return true;
		return false;
	}

	public boolean sortieZonePatrouille(int[] position, Environnement e){
		if(position[0]<e.getBordureCarteHauteur())
			return true;
		if(position[0]>e.getHauteurCarte())
			return true;
		if(position[1]>e.getLargeurCarte())
			return true;
		if(position[1]<e.getLargeurCote())
			return true;
		return false;
	}

	public boolean dansZoneAlerte(int[] position, Environnement e, Drone dr){
		for(Alerte a=e.getListeAlertes().getFirst();a!=e.getListeAlertes().getLast();a=a.getNext()){
			for(int[] b : dr.parcoursDroneRecherche(a.getPosition())){
				if(Arrays.equals(position,b))
					return true;
			}
		}
		return false;
	}

	public boolean dansZoneAlerteSeule(int[] position, Environnement e, int[] alerte, Drone dr){
		for(int[] b : dr.parcoursDroneRecherche(alerte)){
			if(Arrays.equals(position,b))
				return true;
		}
		return false;
	}

	public boolean dansZoneAlerteSeule(int[] position, Environnement e, int[] alerte){
		Drone dr = new Drone("fake", null, 0, e, 0); 
		for(int[] b : dr.parcoursDroneRecherche(alerte)){
			if(Arrays.equals(position,b))
				return true;
		}
		return false;
	}

	public boolean naufrageTrouveDansZoneAlerteSeule(Environnement e, int[] alerte, Drone dr){
		for(Alerte a=e.getListeAlertes().getFirst();a.getNext()!=null;a=a.getNext()){
			for(int[] b : dr.parcoursDroneRecherche(alerte)){
				if(a.getN()!=null && Arrays.equals(a.getN().getPosition(),b))
					return true;
			}
		}
		return false;
	}

	public static void main(String arg[]){
	}
}