package elements;

import java.util.Arrays;

import organisation.Environnement;

import elementsG.BateauG;


public class Bateau extends Element {

	//Attributs
	private int[] arrive;
	private int nbPassager;
	private BateauG bg;

	//Constructeur
	public Bateau(String nom, int[] position, int essence, Environnement e, int[] arrive,  int nbPassager){
		super(nom, position, essence, e);
		this.arrive = arrive;
		this.nbPassager = nbPassager;
		if(e.getEstGraphique()){
			this.bg = new BateauG(this, e.getFen().getPanel_bat(), e.getDim());
			bg.affiche(e.getFen().getPanel_bat().getGraphics());
		}
	}

	//Methodes Publics
	public void presentation(){
		super.presentation();
		System.out.println("Je vais en : "+Arrays.toString(this.arrive));
	}

	public void deplacer(){
		this.position = positionSuivanteObjet(this.position, this.arrive);
		setEssence(getEssence()-1);
		if(e.getEstGraphique())
			bg.deplacer();
	}

	public boolean testNaufrage(){
		if(this.essence <= 0)//TODO
			return true;
		else{
			for(CaseMeteo cm : e.getCasesMeteo()){
				if(Arrays.equals(this.getPosition(), cm.getPosition())){
					int b = (int)(Math.random()*100);
					switch(cm.getEtat()){
					case 1:
						if(b<e.getProbaNaufrageBeau())
							return true;
						break;
					case 2:
						if(b<e.getProbaNaufrageNuageux())
							return true;
						break;
					case 3:
						if(b<e.getProbaNaufragePluie())
							return true;
						break;
					case 4:
						if(b<e.getProbaNaufrageBrume())
							return true;
						break;
					default:
						System.out.println("Probleme dans testNaufrage bateau");
						break;
					}
				}
			}
		}
		return false;
	}

	public boolean testArrive(){
		if(Arrays.equals(this.position, this.arrive)){
			return true;
		}
		return false;
	}

	//Gets et Sets
	public int[] getArrive() {
		return arrive;
	}

	public void setArrive(int[] arrive) {
		this.arrive = arrive;
	}

	public int getNbPassager() {
		return nbPassager;
	}

	public void setNbPassager(int nbPassager) {
		this.nbPassager = nbPassager;
	}

	public BateauG getBg() {
		return bg;
	}

	public void setBg(BateauG bg) {
		this.bg = bg;
	}
}

