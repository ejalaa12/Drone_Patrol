package elements;

import java.util.ArrayList;
import java.util.Arrays;

import organisation.Environnement;

import elementsG.BateauSauvetageG;


public class BateauSauvetage extends Element {

	//Attributs
	/* 1=BateauSauvetageAttente
	 * 2=BateauSauvetageDebutMision
	 * 3=BateauSauvetageFinMission
	 * 4=BateauSauvetageRetourEssence
	 * 5=BateauSauvetageDetruit*/
	private ArrayList<Naufrage> naufragesBord = new ArrayList<Naufrage>();
	private BateauSauvetageG bsg;
	private Alerte alerte;

	//Constructeur
	public BateauSauvetage(String nom, int[] position, int essence, Environnement e){
		super(nom, position, essence, e);
		this.etat = 1;
		if(e.getEstGraphique()){
			this.bsg = new BateauSauvetageG(this, e.getFen().getPanel_bat(), e.getDim());
			bsg.affiche(e.getFen().getPanel_bat().getGraphics());
		}
	}

	//Methodes Publics
	public void presentation(){
		super.presentation();
		switch(etat){
		case 1: // BateauSauvetageAttente
			System.out.println("Je suis en Attente au port.");
			break;
		case 2: // BateauSauvetageDebutMision
			System.out.println("Je suis en Début de mission. Je vais vers une alerte en: "+Arrays.toString(this.alerte.getPosition())+" j'ai "+Integer.toString(this.naufragesBord.size())+" naufragé(s) à bord");
			break;
		case 3: // BateauSauvetageFinMission
			System.out.println("J'ai trouvé le naufragé, je retourne au port avec "+Integer.toString(this.naufragesBord.size())+" naufragé(s)");
			break;
		case 4: // BateauSauvetageRetourEssence
			System.out.println("Je n'ai plus d'essence, je retourne au port");
			break;
		case 5: // BateauSauvetageDetruit
			System.out.println("Je coulé en "+Arrays.toString(this.position));
			break;
		default: 
			System.out.println("Cet état n'existe pas!");
		}
	}
	
	public void deplacer(){
		switch(etat){
		case 1: // BateauSauvetageAttente
			break;
		case 2: // BateauSauvetageDebutMission
			this.position = positionSuivanteObjet(this.position, this.alerte.getDr().getPosition());
			this.setEssence(this.getEssence()-1);
			break;
		case 3: // BateauSauvetageFinMission
			this.position = positionSuivanteObjet(this.position, e.getPort());
			this.setEssence(this.getEssence()-1);
			break;
		case 4: // BateauSauvetageRetourEssence
			this.position = positionSuivanteObjet(this.position, e.getPort());
			this.setEssence(this.getEssence()-1);
			break;
		case 5: // BateauSauvetageDetruit
			break;
		default: 
			System.out.println("Cet état n'existe pas!");
		}
		if(e.getEstGraphique())
			bsg.deplacer();
	}

	public void definirEtat(){
		testRecoitAlerte(); // si oui passe à BateauSauvetageDebutMission
		testArrivePort();
		testEssence();
	}

	public void ajoutNaufrageBord(Naufrage n){
		this.naufragesBord.add(n);
	}
	
	//Methodes Privates
	private void testRecoitAlerte(){
		if(this.etat==1){
				if(e.getListeAlertes().containAlerteBateauSauvetage()){
					this.alerte = e.getListeAlertes().getFirstAlerteBateauSauvetage();
					this.alerte.setEtat(5);
					setEtat(2);
				}
		}
		if(this.etat==2 && this.alerte.getEtat()==6){
			setEtat(3);
			this.alerte.setEtat(6);
			this.alerte = null;
		}
	}

	private void testArrivePort(){
		if(Arrays.equals(this.position, this.e.getPort()) && this.etat!=2){
			setEtat(1);
			this.essence = e.getPleinEssenceBateauSauvetage();
			this.naufragesBord.clear();
		}
	}

	private void testEssence(){
		if(this.essence==0){
			setEtat(6);
			return;
		}
		if(parcoursObject(this.position, e.getPort()).size()>this.essence-5 && this.etat!=4){
			switch(this.etat){
			case 2: // BateauSauvetageDebutMision
				setEtat(4);
				this.alerte.setEtat(4);
				break;
			case 3: // BateauSauvetageFinMission
				setEtat(4);
				break;
			default:
				System.out.println("probleme dans drone test non Retour");
			}
		}
	}

	private ArrayList<int[]> parcoursObject(int[] depart, int[] arrive){
		ArrayList<int[]> tab = new ArrayList<int[]>();
		int[] position = depart.clone();
		while(!Arrays.equals(position,arrive)){
			tab.add(position.clone());
			position = super.positionSuivanteObjet(position, arrive);
		}
		tab.add(position.clone());
		return tab;
	}

	//Gets et Sets
	public ArrayList<Naufrage> getNaufragesBord() {
		return naufragesBord;
	}

	public void setNaufragesBord(ArrayList<Naufrage> naufragesBord) {
		this.naufragesBord = naufragesBord;
	}

	public BateauSauvetageG getBsg() {
		return bsg;
	}

	public void setBsg(BateauSauvetageG bsg) {
		this.bsg = bsg;
	}

	public Alerte getAlerte() {
		return alerte;
	}

	public void setAlerte(Alerte alerte) {
		this.alerte = alerte;
	}
}