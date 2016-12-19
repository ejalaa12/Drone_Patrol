package elements;

import java.util.Arrays;

import organisation.Environnement;

import elementsG.CaseMeteoG;

public class CaseMeteo extends Element {

	//Attributs
	/* 1=beau
	 * 2=nuageux
	 * 3=pluie
	 * 4=brumeux
	 */
	private int etatBis;
	private int etatTer;
	private int etatQua;
	private CaseMeteo debut;
	private int nbVoisinBeau;
	private int nbVoisinNuageux;
	private int nbVoisinPluie;
	private int nbVoisinBrumeux;
	private CaseMeteoG cmg;

	//Constructeur
	public CaseMeteo(int[] position, Environnement e){
		super(position, e);
		this.etat = 1;
		this.etatBis = 0;
		this.etatTer = 1;
		this.debut = null;
		if(e.getEstGraphique()){
			cmg = new CaseMeteoG(this.position, this.e, this);
			cmg.affiche(e.getFen().getPanel_meteo().getGraphics());
		}

	}

	//Methodes Publics
	public void definirEtat(){
		compterMeteoVoisin();
		int a = (int)(Math.random()*100);
		if(a<1){
			int b = (int)(Math.random()*11);
			if(b<e.getMeteo())
				this.etatTer=2;
			else
				this.etatTer=1;
			this.etatQua = 1;
		}
		else if(this.etatBis==1)
			this.etatQua = 0;
		else if(this.debut!=null)
			this.etatTer = this.debut.getEtat();
		else if(nbVoisinBrumeux>=4)
			this.etatTer = 4;
		else if(nbVoisinPluie>=4)
			this.etatTer = 4;
		else if(nbVoisinNuageux>=4)
			this.etatTer = 3;
		else if(nbVoisinBeau>=4)
			this.etatTer = 1;
		else if(nbVoisinBeau==0 && this.etat==1)
			this.etatTer = 2;
		else if(nbVoisinBeau==0 && nbVoisinNuageux==0 && this.etat==2)
			this.etatTer = 3;
	}

	public void vent(){
		switch(e.getSensVent()){
		case 0:// haut-droite
			for(CaseMeteo cs : e.getCasesMeteo()){
				if(cs.getPosition()[0] == this.position[0] && cs.getPosition()[1] == this.position[1]-1 && this.position[1]%2 == 1){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
				else if(cs.getPosition()[0] == this.position[0]+1 && cs.getPosition()[1] == this.position[1]-1 && this.position[1]%2 == 0){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
			}
			break;
		case 1:// bas-droite
			for(CaseMeteo cs : e.getCasesMeteo()){
				if(cs.getPosition()[0] == this.position[0]-1 && cs.getPosition()[1] == this.position[1]-1 && this.position[1]%2 == 1){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
				else if(cs.getPosition()[0] == this.position[0] && cs.getPosition()[1] == this.position[1]-1 && this.position[1]%2 == 0){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
			}
			break; 
		case 2:// bas
			for(CaseMeteo cs : e.getCasesMeteo()){
				if(cs.getPosition()[0] == this.position[0]-1 && cs.getPosition()[1] == this.position[1]){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
			}
			break;
		case 3:// bas-gauche
			for(CaseMeteo cs : e.getCasesMeteo()){
				if(cs.getPosition()[0] == this.position[0]-1 && cs.getPosition()[1] == this.position[1]+1 && this.position[1]%2 == 1){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
				else if(cs.getPosition()[0] == this.position[0] && cs.getPosition()[1] == this.position[1]+1 && this.position[1]%2 == 0){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
			}
			break;
		case 4:// haut-gauche
			for(CaseMeteo cs : e.getCasesMeteo()){
				if(cs.getPosition()[0] == this.position[0] && cs.getPosition()[1] == this.position[1]+1 && this.position[1]%2 == 1){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
				else if(cs.getPosition()[0] == this.position[0]+1 && cs.getPosition()[1] == this.position[1]+1 && this.position[1]%2 == 0){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
			}
			break;
		case 5:// haut
			for(CaseMeteo cs : e.getCasesMeteo()){
				if(cs.getPosition()[0] == this.position[0]+1 && cs.getPosition()[1] == this.position[1]){
					this.etatTer = cs.getEtat();
					this.etatQua = cs.getEtatBis();
					break;
				}
			}
			break;
		default:
			System.out.println("Ce sens du vent n'existe pas");
		}
	}

	//Methodes Publics
	private void compterMeteoVoisin(){
		clearAllnbVoisin();
		this.debut=null;
		int[][] voisins;
		if(this.position[1]%2==0){
			voisins = new int[][]{ {1 ,1 , 0 ,-1 ,-1 , 0} , 	//colonne
					{0 ,1 , 1 , 1 , 0 ,-1} };		//ligne
		}
		else{
			voisins = new int[][]{ {1 , 1 , 0 ,-1 ,-1 ,0} , 	//colonne
					{0 ,-1 ,-1 ,-1 , 0 ,1} };	//ligne

		}
		for (int i=0;i<6;i++){
			int col = this.position[1]+voisins[0][i]; 
			int lig = this.position[0]+voisins[1][i];
			int[] verification = {lig,col};
			for (CaseMeteo m : e.getCasesMeteo()){
				if (Arrays.equals(m.getPosition(), verification)){
					if(m.getEtatBis()==1)
						this.debut=m;
					else{
						switch(m.getEtat()){
						case 1: // Beau
							this.nbVoisinBeau+=1;
							break;
						case 2: // Nuageux
							this.nbVoisinNuageux+=1;
							break;
						case 3: // Pluie
							this.nbVoisinPluie+=1;
							break;
						case 4: // Brumeux
							this.nbVoisinBrumeux+=1;
							break;
						default: 
							System.out.println("Probleme dans CaseMeteo");
						};
					}
				}
			}
		}
		if(this.nbVoisinBeau+this.nbVoisinNuageux+this.nbVoisinPluie+this.nbVoisinBrumeux!=6)
			this.nbVoisinBeau+=(6-this.nbVoisinBeau+this.nbVoisinNuageux+this.nbVoisinPluie+this.nbVoisinBrumeux);
	}

	private void clearAllnbVoisin(){
		this.nbVoisinBeau = 0;
		this.nbVoisinNuageux = 0;
		this.nbVoisinPluie = 0;
		this.nbVoisinBrumeux =0;
	}

	//Gets et Sets
	public void setEtat(int i){
		if(e.getEstGraphique()){
			if (i!=this.cmg.getEtat()){
				this.cmg.modifier(i);
			}			
			this.cmg.setEtat(i);
		}
		super.setEtat(i);
	}
	public int getEtatBis() {
		return etatBis;
	}

	public void setEtatBis(int etatBis) {
		this.etatBis = etatBis;
	}

	public int getEtatTer() {
		return etatTer;
	}

	public void setEtatTer(int etatTer) {
		this.etatTer = etatTer;
	}

	public int getEtatQua() {
		return etatQua;
	}

	public void setEtatQua(int etatQua) {
		this.etatQua = etatQua;
	}

	public CaseMeteo getDebut() {
		return debut;
	}

	public void setDebut(CaseMeteo debut) {
		this.debut = debut;
	}

	public int getNbVoisinBeau() {
		return nbVoisinBeau;
	}

	public void setNbVoisinBeau(int nbVoisinBeau) {
		this.nbVoisinBeau = nbVoisinBeau;
	}

	public int getNbVoisinNuageux() {
		return nbVoisinNuageux;
	}

	public void setNbVoisinNuageux(int nbVoisinNuageux) {
		this.nbVoisinNuageux = nbVoisinNuageux;
	}

	public int getNbVoisinPluie() {
		return nbVoisinPluie;
	}

	public void setNbVoisinPluie(int nbVoisinPluie) {
		this.nbVoisinPluie = nbVoisinPluie;
	}

	public int getNbVoisinBrumeux() {
		return nbVoisinBrumeux;
	}

	public void setNbVoisinBrumeux(int nbVoisinBrumeux) {
		this.nbVoisinBrumeux = nbVoisinBrumeux;
	}

	public CaseMeteoG getCmg() {
		return cmg;
	}

	public void setCmg(CaseMeteoG cmg) {
		this.cmg = cmg;
	}
}