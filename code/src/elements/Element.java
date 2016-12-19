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
		// colonne paire (d�cal�e vers le bas):
		if(position[1]%2==0){
			// arriv�e au dessus:
			if(position[0]>arrive[0]){
				// arriv�e colonne juste a gauche:
				if(position[1]-1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0)
						position[1] = position[1]-1;
//					else
//						position[0] = position[0]-1;
				}
				// arriv�e colonne juste a droite:
				else if(position[1]+1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0)
						position[1] = position[1]+1;
//					else
//						position[0] = position[0]-1;
				}
				// arriv�e un peu plus � gauche:
				else if(position[1]-1>arrive[1])
					position[1] = position[1]-1;
				// arriv�e un peu plus � droite:
				else if (position[1]+1<arrive[1])
					position[1] = position[1]+1;
				// dernier cas arriv�e dans la m�me colonne
				else
					position[0] = position[0]-1;
			}
			// arriv�e en dessous:
			else if(position[0]<arrive[0]){
				// arriv�e dans la colonne juste � gauche
				if(position[1]-1==arrive[1]){
					int a = (int)Math.round(Math.random());
					if(a==0){
						position[0] = position[0]+1;
						position[1] = position[1]-1;
					}
//					else
//						position[0] = position[0]+1;
				}
				// arriv�e dans la colonne juste � droite
				else if(position[1]+1==arrive[1]){
					int a = (int)Math.round(Math.random());
					if(a==0){
						position[0] = position[0]+1;
						position[1] = position[1]+1;
					}	
//					else
//						position[0] = position[0]+1;
				}
				// arriv�e un peu plus � gauche
				else if(position[1]-1>arrive[1]){
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}
				// arriv�e un peu plus � droite
				else if (position[1]+1<arrive[1]){
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				// dernier cas, m�me colonne
				else
					position[0] = position[0]+1;
			}
			// dernier cas si l'arriv�e n'est ni en dessous ni au dessus donc sur la m�me ligne
			else {
				if(position[1]+1==arrive[1]) //juste � droite
					position[1] = position[1]+1; // !!! Il y a peut �tre une faute .?? il faut incr�menter de un ici (je l'ai fait)
				else if(position[1]-1==arrive[1])// juste � gauche
					position[1] = position[1]-1;
				else if(position[1]-1>arrive[1]){ // plus � gauche
					position[0] = position[0]; //+(int)Math.round(Math.random())       inutile non vu que si c'est juste � cote il(nico : je crois que c'est bon)
					position[1] = position[1]-1;							  // y aura une chance qu'on fasse un tour de plus
				}
				else if(position[1]+1<arrive[1]){ // plus � droite
					position[0] = position[0]; // +(int)Math.round(Math.random())m�me remarque (nico : je crois que c'est bon)
					position[1] = position[1]+1;
				}
				else
					position[0] = position[0];
			}	
		}
		// colonne impaire
		else{
			// arriv�e au dessus strictement
			if(position[0]>arrive[0]){
				// arriv�e colonne de gauche
				if(position[1]-1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0){
						position[0] = position[0]-1;
						position[1] = position[1]-1;
//					}
//					else
//						position[0] = position[0]-1;
				}
				// arriv�e colonne de droite
				else if(position[1]+1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0){
						position[0] = position[0]-1;
						position[1] = position[1]+1;
//					}	
//					else
//						position[0] = position[0]-1;
				}
				// arriv�e un peu plus � gauche
				else if(position[1]-1>arrive[1]){
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
				// arriv�e un peu plus � droite
				else if(position[1]+1<arrive[1]){
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
				// arriv�e dans la m�me colonne
				else
					position[0] = position[0]-1;
			}
			// arriv�e en dessous strictement
			else if(position[0]<arrive[0]){
				// arriv�e colonne de gauche
				if(position[1]-1==arrive[1]){
					int a = (int)Math.round(Math.random());
					if(a==0){
						position[1] = position[1]-1;
					}
					else
						position[0] = position[0]+1;
				}
				// arriv�e colonne de droite
				else if(position[1]+1==arrive[1]){
//					int a = (int)Math.round(Math.random());
//					if(a==0){
//						position[0] = position[0]+1;
//					}	
//					else
						position[1] = position[1]+1;
				}
				// arriv�e plus � gauche
				else if(position[1]-1>arrive[1])
					position[1] = position[1]-1;
				// arriv�e plus � droite
				else if (position[1]+1<arrive[1])
					position[1] = position[1]+1;
				// arriv�e m�me colonne
				else
					position[0] = position[0]+1;
			}
			// arriv�e m�me ligne
			else {
				// arriv�e colonne juste � droite
				if(position[1]+1==arrive[1])
					position[1] = position[1]+1;  // m�me erreur que la ligne 108 il y avait un +1 manquant
				// arriv�e colonne juste � gauche
				else if(position[1]-1==arrive[1])
					position[1] = position[1]-1;
				// arriv�e plus � gauche
				else if(position[1]-1>arrive[1]){
					position[0] = position[0];	// -(int)Math.round(Math.random())m�me remarque qu'avant sur le tour de plus
					position[1] = position[1]-1;
				}
				// arriv�e plus � droite
				else if (position[1]+1<arrive[1]){
					position[0] = position[0];	//-(int)Math.round(Math.random()) m�me remarque
					position[1] = position[1]+1;
				}
				// m�me case
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