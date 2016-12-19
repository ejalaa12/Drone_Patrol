package elements;

import organisation.Environnement;

import elementsG.NaufrageG;

public class Naufrage extends Element{

	//Attributs
	private NaufrageG ng;
	private boolean KitSurvie = false;

	//Constructeur
	public Naufrage(String nom, int[] position, int essence, Environnement e){
		super(nom, position, essence, e);
		if(e.getEstGraphique()){
			this.ng = new NaufrageG(this, e.getFen().getPanel_nauf(), e.getDim());
			ng.affiche(e.getFen().getPanel_nauf().getGraphics());
		}
	}

	//Methodes Publics
	public void deplacer(){
		this.position = positionSuivanteNaufrage(this.position);
		this.setEssence(this.getEssence()-1);
		if(e.getEstGraphique())
			ng.deplacer();
	}

	//Methode Privates
	private int[] positionSuivanteNaufrage(int[] position){
		int b = (int)(Math.random()*10);
		if(b<9){
			int a = e.getSensVent();

			if(position[1]%2==0){

				if(a==1){// bas-droite
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				else if(a==3){// bas-gauche
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}
				else if(a==2)// bas
					position[0] = position[0]+1;
				else if(a==5)// haut
					position[0] = position[0]-1;
				else if(a==0)// haut-droite 
					position[1] = position[1]+1;
				else if(a==4)// haut-gauche
					position[1] = position[1]-1;
				else{
				}
			}
			else{

				if(a==4){// haut-gauche
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
				else if(a==0){// haut-droite
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
				else if(a==2)// bas
					position[0] = position[0]+1;
				else if(a==5)// haut
					position[0] = position[0]-1;
				else if(a==1)// bas-droite
					position[1] = position[1]+1;
				else if(a==3)// bas-gauche
					position[1] = position[1]-1;
				else{
				}
			}
		}
		else{
			int c = (int)(Math.random()*6);
			if(position[1]%2==0){

				if(c==0){// bas-droite
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				else if(c==1){// bas-gauche
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}
				else if(c==2)// bas
					position[0] = position[0]+1;
				else if(c==3)// haut
					position[0] = position[0]-1;
				else if(c==4)// haut-droite 
					position[1] = position[1]+1;
				else if(c==5)// haut-gauche
					position[1] = position[1]-1;
				else{
				}
			}
			else{

				if(c==0){// haut-gauche
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
				else if(c==1){// haut-droite
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
				else if(c==2)// bas
					position[0] = position[0]+1;
				else if(c==3)// haut
					position[0] = position[0]-1;
				else if(c==4)// bas-droite
					position[1] = position[1]+1;
				else if(c==5)// bas-gauche
					position[1] = position[1]-1;
				else{
				}
			}
		}
		return position;
	}


	//Gets et Sets
	public NaufrageG getNg() {
		return ng;
	}

	public void setNg(NaufrageG ng) {
		this.ng = ng;
	}

	public boolean isKitSurvie() {
		return KitSurvie;
	}

	public void setKitSurvie(boolean kitSurvie) {
		KitSurvie = kitSurvie;
	}
}
