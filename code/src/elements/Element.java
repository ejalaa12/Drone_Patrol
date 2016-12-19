package elements;

import java.util.ArrayList;
import java.util.Arrays;

import organisation.Environnement;

public abstract class Element {

	//Attributs
	protected String nom;
	protected int[] position;
	protected int essence;
	protected int etat;
	protected int numero;
	protected Environnement e;
	protected int[] positionGraphique;

	//Constructeurs
	public Element(int[] position, Environnement e) {
		this.position = position;
		this.e = e;
	}

	public Element(String nom, int[] position, int essence, Environnement e) {
		this.nom = nom;
		this.position = position;
		this.essence = essence;
		this.e = e;
	}

	//Methods Publics
	public void ajoutPositionGraphiqueX(int x){
		this.positionGraphique[0]+=x;
	}

	public void ajoutPositionGraphiqueY(int y){
		this.positionGraphique[1]+=y;
	}
	
	//Methodes Protecteds
	protected void presentation(){
		System.out.println("Nom : "+nom+" ; Position : "+Arrays.toString(position)+" ; PositionG : "+Arrays.toString(positionGraphique));
	}

	protected int[] positionSuivanteObjet(int[] position, int[] arrive){
		// colonne paire (décalée vers le bas):
		if(position[1]%2==0){
			// arrivée au dessus:
			if(position[0]>arrive[0]){
				// arrivée colonne juste a gauche:
				if(position[1]-1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0)
						position[1] = position[1]-1;
//					else
//						position[0] = position[0]-1;
				}
				// arrivée colonne juste a droite:
				else if(position[1]+1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0)
						position[1] = position[1]+1;
//					else
//						position[0] = position[0]-1;
				}
				// arrivée un peu plus à gauche:
				else if(position[1]-1>arrive[1])
					position[1] = position[1]-1;
				// arrivée un peu plus à droite:
				else if (position[1]+1<arrive[1])
					position[1] = position[1]+1;
				// dernier cas arrivée dans la même colonne
				else
					position[0] = position[0]-1;
			}
			// arrivée en dessous:
			else if(position[0]<arrive[0]){
				// arrivée dans la colonne juste à gauche
				if(position[1]-1==arrive[1]){
					int a = (int)Math.round(Math.random());
					if(a==0){
						position[0] = position[0]+1;
						position[1] = position[1]-1;
					}
//					else
//						position[0] = position[0]+1;
				}
				// arrivée dans la colonne juste à droite
				else if(position[1]+1==arrive[1]){
					int a = (int)Math.round(Math.random());
					if(a==0){
						position[0] = position[0]+1;
						position[1] = position[1]+1;
					}	
//					else
//						position[0] = position[0]+1;
				}
				// arrivée un peu plus à gauche
				else if(position[1]-1>arrive[1]){
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}
				// arrivée un peu plus à droite
				else if (position[1]+1<arrive[1]){
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				// dernier cas, même colonne
				else
					position[0] = position[0]+1;
			}
			// dernier cas si l'arrivée n'est ni en dessous ni au dessus donc sur la même ligne
			else {
				if(position[1]+1==arrive[1]) //juste à droite
					position[1] = position[1]+1; // !!! Il y a peut être une faute .?? il faut incrémenter de un ici (je l'ai fait)
				else if(position[1]-1==arrive[1])// juste à gauche
					position[1] = position[1]-1;
				else if(position[1]-1>arrive[1]){ // plus à gauche
					position[0] = position[0]; //+(int)Math.round(Math.random())       inutile non vu que si c'est juste à cote il(nico : je crois que c'est bon)
					position[1] = position[1]-1;							  // y aura une chance qu'on fasse un tour de plus
				}
				else if(position[1]+1<arrive[1]){ // plus à droite
					position[0] = position[0]; // +(int)Math.round(Math.random())même remarque (nico : je crois que c'est bon)
					position[1] = position[1]+1;
				}
				else
					position[0] = position[0];
			}	
		}
		// colonne impaire
		else{
			// arrivée au dessus strictement
			if(position[0]>arrive[0]){
				// arrivée colonne de gauche
				if(position[1]-1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0){
						position[0] = position[0]-1;
						position[1] = position[1]-1;
//					}
//					else
//						position[0] = position[0]-1;
				}
				// arrivée colonne de droite
				else if(position[1]+1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0){
						position[0] = position[0]-1;
						position[1] = position[1]+1;
//					}	
//					else
//						position[0] = position[0]-1;
				}
				// arrivée un peu plus à gauche
				else if(position[1]-1>arrive[1]){
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
				// arrivée un peu plus à droite
				else if(position[1]+1<arrive[1]){
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
				// arrivée dans la même colonne
				else
					position[0] = position[0]-1;
			}
			// arrivée en dessous strictement
			else if(position[0]<arrive[0]){
				// arrivée colonne de gauche
				if(position[1]-1==arrive[1]){
					int a = (int)Math.round(Math.random());
					if(a==0){
						position[1] = position[1]-1;
					}
					else
						position[0] = position[0]+1;
				}
				// arrivée colonne de droite
				else if(position[1]+1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0){
//						position[0] = position[0]+1;
//					}	
//					else
						position[1] = position[1]+1;
				}
				// arrivée plus à gauche
				else if(position[1]-1>arrive[1])
					position[1] = position[1]-1;
				// arrivée plus à droite
				else if (position[1]+1<arrive[1])
					position[1] = position[1]+1;
				// arrivée même colonne
				else
					position[0] = position[0]+1;
			}
			// arrivée même ligne
			else {
				// arrivée colonne juste à droite
				if(position[1]+1==arrive[1])
					position[1] = position[1]+1;  // même erreur que la ligne 108 il y avait un +1 manquant
				// arrivée colonne juste à gauche
				else if(position[1]-1==arrive[1])
					position[1] = position[1]-1;
				// arrivée plus à gauche
				else if(position[1]-1>arrive[1]){
					position[0] = position[0];	// -(int)Math.round(Math.random())même remarque qu'avant sur le tour de plus
					position[1] = position[1]-1;
				}
				// arrivée plus à droite
				else if (position[1]+1<arrive[1]){
					position[0] = position[0];	//-(int)Math.round(Math.random()) même remarque
					position[1] = position[1]+1;
				}
				// même case
				else
					position[0] = position[0];
			}
		}
		return position;
	}

	protected ArrayList<int[]> parcoursObjet(int[] depart, int[] arrive){
		ArrayList<int[]> tab = new ArrayList<int[]>();
		int[] position = depart.clone();
		while(!Arrays.equals(position,arrive)){
			tab.add(position.clone());
			position = positionSuivanteObjet(position, arrive);
		}
		tab.add(position.clone());
		return tab;
	}

	//Get et Set
	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public int getEssence() {
		return essence;
	}

	public void setEssence(int essence) {
		this.essence = essence;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Environnement getE() {
		return e;
	}

	public void setE(Environnement e) {
		this.e = e;
	}

	public int[] getPositionGraphique() {
		return positionGraphique;
	}

	public void setPositionGraphique(int[] positionGraphique) {
		this.positionGraphique = positionGraphique;
	}
}