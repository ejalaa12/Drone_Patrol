package organisation;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;

import organisationG.Dimensionnement;
import organisationG.Fenetre;
import elements.*;
import elementsG.AlerteG;

public class Environnement{

	//Attributs
	public static int temps = 0;
	public static int tempstat = 0;

	private int largeurTotalCarte;//Attention la valeur doit etre superieur a 9
	private int hauteurTotalCarte;//Attention la valeur doit etre superieur a 9
	private int largeurCote;// toujours plus que deux fois le nbCercleRechercheDrone (recherche losange)
	private int nbCercleRechercheDrone;
	private int bordureCarteHauteur;// toujours plus que le nbCercleRechercheDrone+1 (recherche losange)
	private int bordureCarteLargeur;// toujours plus que deux fois le nbCercleRechercheDrone+1 (recherche losange)
	private int hauteurCarte;
	private int largeurCarte;
	private int moitieCarte;{
		if(hauteurCarte%2==0)
			moitieCarte = hauteurTotalCarte/2;
		else
			moitieCarte = (hauteurTotalCarte+1)/2;
	}
	private int[] port = new int[2];{
		port[0] = moitieCarte;
		port[1] = largeurCote+1;
	}
	private int probabiliteNaufrageBateau = 20; // si �gale z�ro, passe en automatique(chance en fonction de son trajet)
	private int vie = 10;
	private int capaciteMaxBateauSauvetage = 5;
	private int nbKitSurvie = 3;
	private int nbBateauParTour = 10;
	private int nbDrones = 4;
	private int pleinEssenceDrone = 1000;
	private int pleinEssenceBateauSauvetage = 1000;
	private int pleinEssenceBateau = 100;
	private int probaDetectionBeau = 100;
	private int probaDetectionNuageux = 70;
	private int probaDetectionPluie = 40;
	private int probaDetectionBrume = 10;
	private int probaNaufrageBeau = 1;
	private int probaNaufrageNuageux = 10;
	private int probaNaufragePluie = 40;
	private int probaNaufrageBrume = 70;
	private int malusDetection = 10; // pour les cases � cot�(et non pas pour celles juste en dessous)
	private int bonusVieKitSurvie = 10;
	private int meteo = 5; // valeur entre 0 et 10 (1=tr�s beau, 10=tr�s moche)
	private LinkedList<Drone> drones = new LinkedList<Drone>();
	private LinkedList<Bateau> bateaux = new LinkedList<Bateau>();
	private LinkedList<BateauSauvetage> bateauxSauvetages = new LinkedList<BateauSauvetage>();
	private LinkedList<Naufrage> naufrages = new LinkedList<Naufrage>();
	private ArrayList<Naufrage> naufragesSauves = new ArrayList<Naufrage>(); // liste des naufrages sauves
	private ArrayList<Naufrage> naufragesSauvesCote = new ArrayList<Naufrage>(); // liste des naufrages sauves par la cote
	private ArrayList<Naufrage> naufragesMorts = new ArrayList<Naufrage>(); // liste des naufrages mort
	private ArrayList<Naufrage> naufragesSauvesBateau = new ArrayList<Naufrage>(); // liste des naufrages sauve par un bateau qui passait
	private ArrayList<CaseMeteo> casesMeteo = new ArrayList<CaseMeteo>(); // liste des cases m�t�o
	private int sensVent = 0; // int de 0 a 5;
	private Utile u = new Utile();
	private int compteurBateau = 0;
	private int compteurNaufrage = 0;
	private int nbMaxBateauCarte = 8;
	private int nbMaxNaufrageCarte = 2;
	private int probabiliteEnvoiAlerte = 100;
	private LinkedList<Element> tous = new LinkedList<Element>();
	private int nbBateauSauvetage = 5;
	private int probabiliteDetecteNaufrageBateau = 100;
	private Fenetre fen;
	private Dimension dimFen = new Dimension(912,644);
	private Dimensionnement dim = new Dimensionnement(new Dimension (largeurTotalCarte,hauteurTotalCarte), dimFen);
	private boolean estGraphique = false;
	private ArrayList<AlerteG> alertesg = new ArrayList<AlerteG>();
	private int nbPassagersMaxBateau = 1;
	private ListeAlerte listeAlertes = new ListeAlerte();
	private double[] stats = new double[3];

	public Environnement(int largeurTotalCarte, int hauteurTotalCarte,  int largeurCote,
			int nbDrones, int pleinEssenceDrone, int nbCercleRechercheDrone, int nbKitSurvie,
			int nbBateauSauvetage, int pleinEssenceBateauSauvetage, int capaciteMaxBateauSauvetage,
			int nbMaxBateauCarte, int pleinEssenceBateau,
			int nbMaxNaufrageCarte, int vie,
			int probaDetectionBeau, int probaDetectionNuageux, int probaDetectionPluie, int probaDetectionBrume,
			int malusDetection,int meteo){
		this.hauteurTotalCarte = hauteurTotalCarte;
		this.largeurTotalCarte = largeurTotalCarte;
		this.nbCercleRechercheDrone = nbCercleRechercheDrone;
		this.bordureCarteHauteur = nbCercleRechercheDrone+1;// toujours plus que le nbCercleRechercheDrone+1 (recherche losange)
		this.bordureCarteLargeur = nbCercleRechercheDrone*2+1;
		this.hauteurCarte = hauteurTotalCarte-2*bordureCarteHauteur;
		this.largeurCarte = largeurTotalCarte-bordureCarteLargeur;
		this.largeurCote = largeurCote;
		if(hauteurCarte%2==0)
			moitieCarte = hauteurTotalCarte/2;
		else
			moitieCarte = (hauteurTotalCarte+1)/2;
		port = new int[2];{
			port[0] = moitieCarte;
			port[1] = largeurCote+1;
		}
		this.vie = vie;
		this.nbKitSurvie = nbKitSurvie;
		this.nbDrones = nbDrones;
		this.pleinEssenceDrone = pleinEssenceDrone;
		this.pleinEssenceBateauSauvetage = pleinEssenceBateauSauvetage;
		this.pleinEssenceBateau = pleinEssenceBateau;
		this.probaDetectionBeau = probaDetectionBeau;
		this.probaDetectionNuageux = probaDetectionNuageux;
		this.probaDetectionPluie = probaDetectionPluie;
		this.probaDetectionBrume = probaDetectionBrume;
		this.malusDetection = malusDetection;
		this.meteo = meteo;
		this.nbMaxBateauCarte = nbMaxBateauCarte;
		this.nbMaxNaufrageCarte = nbMaxNaufrageCarte;
		this.nbBateauSauvetage = nbBateauSauvetage;
		Environnement.tempstat = 0;
		temps = 0;
	}

	public Environnement(int largeurTotalCarte, int hauteurTotalCarte,  int largeurCote,
			int nbDrones, int pleinEssenceDrone, int nbCercleRechercheDrone, int nbKitSurvie,
			int nbBateauSauvetage, int pleinEssenceBateauSauvetage, int capaciteMaxBateauSauvetage,
			int nbMaxBateauCarte, int pleinEssenceBateau,
			int nbMaxNaufrageCarte, int vie,
			int probaDetectionBeau, int probaDetectionNuageux, int probaDetectionPluie, int probaDetectionBrume,
			int malusDetection,int meteo, Fenetre fenetre) {
		this(largeurTotalCarte, hauteurTotalCarte, largeurCote,
				nbDrones, pleinEssenceDrone, nbCercleRechercheDrone, nbKitSurvie,
				nbBateauSauvetage, pleinEssenceBateauSauvetage, capaciteMaxBateauSauvetage,
				nbMaxBateauCarte, pleinEssenceBateau,
				nbMaxNaufrageCarte, vie,
				probaDetectionBeau, probaDetectionNuageux, probaDetectionPluie, probaDetectionBrume,
				malusDetection, meteo);
		this.fen = fenetre;
		this.estGraphique = true;
		dimFen = new Dimension(912,644);
		dim = new Dimensionnement(new Dimension (largeurTotalCarte, hauteurTotalCarte), dimFen);
		Environnement.temps = 0;
	}

	// ==========================================================ZONE DE SIMULATION TOUR PAR TOUR===============================================================

	public void Suivant(){
		if(temps==0){
			creerMeteo();
			creerDrone();
			creerBateauSauvetage();
		}
		else{
			if(temps%32==0)
				trenteDeuxMinutes();
			if(temps%16==0)
				seizeMinutes();
			if(temps%8==0)
				huitMinutes();
			if(temps%4==0)
				quatreMinutes();
			if(temps%2==0)
				deuxMinutes();
			uneMinute();
		}
		temps++;
		statistiques();
	}

	public void uneMinute(){
		changerEtatDrone();
		changeEtatBateauSauvetage();
		deplacementDrone();
	}

	public void deuxMinutes(){
		deplacementBateauSauvetage();
	}

	public void quatreMinutes(){
		deplacementBateau();
	}

	public void huitMinutes(){
		changerEtatMeteo();
		deplacementNaufrage();
		creerNaufrage();
		creerBateau();
	}

	public void seizeMinutes(){
		changerEtatMeteoVent();
	}

	public void trenteDeuxMinutes(){
		changerSensVent();
	}

	// ============================================================FIN DE LA ZONE DE SIMULATION================================================================= 
	public void creerMeteo(){
		for(int i=0;i<hauteurTotalCarte;i++){
			for(int j=0;j<largeurTotalCarte;j++){
				int[] a = {i+1,j+1};
				CaseMeteo cm = new CaseMeteo(a, this);
				casesMeteo.add(cm);
			}
		}
	}

	public void creerDrone(){
		for(int i=0;i<nbDrones;i++){
			Drone dr = new Drone("DR-"+Integer.toString(i+1), port.clone(), pleinEssenceDrone, this, i+1);
			drones.add(dr);
		}
	}

	public void creerBateauSauvetage(){
		for(int i=0;i<nbBateauSauvetage;i++){
			BateauSauvetage bs = new BateauSauvetage("BateauSauvetage-"+Integer.toString(i+1), port.clone(), pleinEssenceBateauSauvetage, this);
			bateauxSauvetages.add(bs);
		}
	}

	public void creerBateau(){
		if(bateaux.size()<nbMaxBateauCarte){
			compteurBateau+=1;
			int[] depart = pointDepart();
			int[] arrive = pointArrive(depart);
			int a = (int)(Math.random()*(nbPassagersMaxBateau))+1;
			Bateau b = new Bateau("Bateau-"+Integer.toString(compteurBateau), depart, this.pleinEssenceBateau, this, arrive, a);
			bateaux.add(b);
		}
	}

	public void creerNaufrage(){
		ArrayList<Bateau> liste1 = new ArrayList<Bateau>();
		for(Bateau b : bateaux){
			if(b.testNaufrage() && naufrages.size()<nbMaxNaufrageCarte){
				for(int i=0;i<b.getNbPassager();i++){
					compteurNaufrage+=1;
					Naufrage n = new Naufrage("Naufrage-"+Integer.toString(compteurNaufrage), b.getPosition().clone(), vie, this);
					naufrages.add(n);
				}
				int a = (int)(Math.random()*100);
				if(a<probabiliteEnvoiAlerte && !u.sortieZonePatrouille(b.getPosition(), this)){ // L'alerte n'est envoy� que d�pendement de la probabilit� d'envoi
					Alerte alerte = new Alerte(b.getPosition().clone());
					listeAlertes.add(alerte);
					if(this.getEstGraphique()){
						AlerteG alg = new AlerteG(dim.deduirePositionGraphique(b.getPosition().clone()), getFen().getPanel_meteo(), dim);
						if (alertesg.size()==10){
							alertesg.remove(0);
						}
						alertesg.add(alg);
						alg.alerter();
					}
				}
				liste1.add(b);
			}
		}
		bateaux.removeAll(liste1);
	}	

	public void changerEtatDrone(){
		for(Drone dr : drones){
			dr.definirEtat();
		}
	}

	public void changeEtatBateauSauvetage(){
		for(BateauSauvetage bs : bateauxSauvetages){
			bs.definirEtat();
		}
	}

	public void changerEtatMeteo(){
		for(CaseMeteo cm : this.casesMeteo)
			cm.definirEtat();
		for(CaseMeteo cm : this.casesMeteo){
			cm.setEtat(cm.getEtatTer());
			cm.setEtatBis(cm.getEtatQua());
		}
	}

	public void changerEtatMeteoVent(){
		for(CaseMeteo cm : this.casesMeteo)
			cm.vent();
		for(CaseMeteo cm : this.casesMeteo){
			cm.setEtat(cm.getEtatTer());
			cm.setEtatBis(cm.getEtatQua());
		}
	}

	public void changerSensVent(){
		sensVent = (int)(Math.random()*6);
	}

	private void supprimerBateau(){
		ArrayList<Bateau> liste1 = new ArrayList<Bateau>();
		for(Bateau b : bateaux){
			if(b.testArrive())// On supprime le bateau quand il arrive � son point d'arriv�e
				liste1.add(b);
		}
		bateaux.removeAll(liste1);
	}

	private void supprimerNaufrage(){
		ArrayList<Naufrage> liste1 = new ArrayList<Naufrage>();
		for(Naufrage n : naufrages){
			if(n.getEssence() == 0){ // S'il est mort
				liste1.add(n);
				naufragesMorts.add(n);
			}
			else if(n.getPosition()[1]<=this.largeurCote){ // S'il a rejoint la cote
				liste1.add(n);
				naufragesSauvesCote.add(n);
			}
			else{
				for(BateauSauvetage bs : bateauxSauvetages){
					if(Arrays.equals(n.getPosition(), bs.getPosition())){ // S'il a �t� sauv� par un bateau de sauvetage
						liste1.add(n);
						naufragesSauves.add(n);
						bs.ajoutNaufrageBord(n);
						for(Alerte a1 : this.getListeAlertes().getAlertesTous()){
							if(u.dansZoneAlerteSeule(n.getPosition(), this, a1.getPosition())){
								a1.setEtat(6);
							}
						}
					}
				}
				for(Bateau b : bateaux){
					int a = (int)(Math.random()*100);
					if(Arrays.equals(n.getPosition(), b.getPosition()) && a<this.probabiliteDetecteNaufrageBateau){ // S'il a �t� sauv� par un bateau qui passait
						liste1.add(n);
						naufragesSauvesBateau.add(n);
						if(!this.getListeAlertes().isEmpty()){
							for(Alerte a2 : this.getListeAlertes().getAlertesTous()){
								if(u.dansZoneAlerteSeule(n.getPosition(), this, a2.getPosition())){
									a2.setEtat(6);
									break;
								}
							}
						}
					}
				}
			}
		}
		naufrages.removeAll(liste1);
	}

	public void deplacementDrone(){
		for(Drone dr : drones){
			if(dr.getN()==null || !Arrays.equals(dr.getN().getPosition(),dr.getPosition()))
				dr.deplacer();
		}
	}

	public void deplacementBateauSauvetage(){
		for(BateauSauvetage b : bateauxSauvetages){
			b.deplacer();
			supprimerNaufrage();
		}
	}

	public void deplacementBateau(){
		ArrayList<Bateau> liste1 = new ArrayList<Bateau>();
		for(Bateau b : bateaux){
			b.deplacer();
			if(u.sortieCarte(b.getPosition(), this)) // Au cas o� il sort de la carte
				liste1.add(b);
		}
		bateaux.removeAll(liste1);
		supprimerBateau();
		supprimerNaufrage();
	}

	public void deplacementNaufrage(){
		for(Drone dr : drones){
			if(dr.getEtat()==4 && !Arrays.equals(dr.getN().getPosition(),dr.getPosition()))
				dr.deplacer();
		}
		ArrayList<Naufrage> liste1 = new ArrayList<Naufrage>();
		for(Naufrage n : naufrages){
			n.deplacer();
			if(u.sortieCarte(n.getPosition(), this)) // Au cas o� il sort de la carte
				liste1.add(n);
		}
		naufrages.removeAll(liste1);
		supprimerNaufrage();
	}

	private void statistiques(){
		for(Alerte a : this.listeAlertes.getAlertesTous()){
			if((a.getEtat()==4 && a.getTdecouvert()==0) || (a.getEtat()==5 && a.getTdecouvert()==0))
				a.setTdecouvert(temps);
			if(a.getEtat()==6 && a.getTsauve()==0)
				a.setTsauve(temps);
			if(a.getEtat()==7 && a.getTmort()==0){
				a.setTmort(temps);
			}
		}
		int temp1 = 0;
		int temp2 = 0;
		int temp3 = 0;
		int i = 0;
		int j = 0;
		int k = 0;
		for(Alerte a : this.listeAlertes.getAlertesTous()){
			if(a.getTsauve()!=0){
				i++;
				temp1+=(a.getTsauve()-a.getVraiTemps());
			}
			if(a.getTmort()!=0){
				j++;
				if(a.getTmort()-a.getVraiTemps()<80)
					temp2+=(a.getTmort()-a.getVraiTemps());
			}
			if(a.getTdecouvert()!=0){
				k++;
				temp3+=(a.getTdecouvert()-a.getVraiTemps());
			}
		}
		if(i!=0) 
			stats[0] = (double)(temp1/i);
		if(j!=0) 
			stats[1] = (double)(temp2/j);
		if(k!=0)
			stats[2] = (double)(temp3/k);
	}

	/**
	 * Cr�e un point de d�part al�atoirement sur un des c�t�s (!= de la cote) de la carte ou le port. <br>
	 * Ce point de d�part servira pour y faire appara�tre des bateaux. 
	 * @return	les coordonn�es du point de d�part
	 */
	private int[] pointDepart(){
		int x;
		int y;
		int a =(int) (Math.random()*4);
		if(a==0){
			x = 1;
			y =(int) Math.round(Math.random()*(largeurTotalCarte-largeurCote-1)+largeurCote+1);
		}

		else if (a==1){
			x = hauteurTotalCarte;
			y =(int) Math.round(Math.random()*(largeurTotalCarte-largeurCote-1)+largeurCote+1);
		}

		else if (a==2){
			x = port[0];
			y = port[1];
		}

		else {
			x =(int) Math.round(Math.random()*(hauteurTotalCarte-1)+1);
			y = largeurTotalCarte;
		}

		int[] depart = {x,y};
		return depart;
	}

	/**
	 * Cr�e un point d'arriv�e al�atoirement sur un des c�t�s en fonction du point de depart(!= de la cote) de la carte ou le port <br>
	 * Ce point d'arriv�e servira � donner une destination aux nouveaux bateaux. 
	 * @param depart
	 * @return les coordonn�es du point d'arriv�e
	 */
	private int[] pointArrive(int [] depart){
		int x;
		int y;
		//		int a =(int)(Math.random()*1000);

		if(depart[0]==port[0] && depart[1]==port[1]){
			int[] b = pointDepart();
			x = b[0];
			y = b[1];
		}

		//		else if(a<1){
		//			x = port[0];
		//			y = port[1];
		//		}

		else {
			if(depart[0]==1){
				x = hauteurTotalCarte;
				y =(int) Math.round(Math.random()*(largeurTotalCarte-largeurCote-1)+largeurCote+1);
			}
			else if(depart[0]==hauteurTotalCarte){
				x=1;
				y =(int) Math.round(Math.random()*(largeurTotalCarte-largeurCote-1)+largeurCote+1);
			}
			else if(depart[1]==largeurTotalCarte){
				int a =(int)(Math.random()+1);
				if(a==0){
					x = hauteurTotalCarte;
					y =(int) Math.round(Math.random()*(largeurTotalCarte-largeurCote-1)+largeurCote+1);
				}
				else{
					x=1;
					y =(int) Math.round(Math.random()*(largeurTotalCarte-largeurCote-1)+largeurCote+1);
				}

			}
			else {
				x = port[0];
				y = port[1];
			}
		}
		int[] arrive = {x,y};
		return arrive;
	}

	public LinkedList<Element> getTous(){
		tous.addAll(drones);
		tous.addAll(bateaux);
		tous.addAll(bateauxSauvetages);
		tous.addAll(naufrages);
		return tous;
	}

	//===============================================ZONE DESSIN====================================================
	public void dessinMeteo(Graphics g){
		for(CaseMeteo cm : this.casesMeteo)
			cm.getCmg().affiche(g);
	}

	public void dessinDrones(Graphics g){
		for(Drone dr : this.drones)
			dr.getDg().affiche(g);
	}

	public void dessinBateaux(Graphics g){
		for(Bateau b : this.bateaux)
			b.getBg().affiche(g);
	}

	public void dessinBateauxSauvetage(Graphics g){
		for(BateauSauvetage bs : this.bateauxSauvetages){
			bs.getBsg().affiche(g);
		}
	}

	public void dessinNaufrages(Graphics g){
		for(Naufrage n : this.naufrages){
			n.getNg().affiche(g);
		}
	}

	public void dessinAlertes(Graphics g){
		for (AlerteG alg : this.alertesg){
			alg.affiche(g);
		}
	}
	//===============================================FIN  DESSIN====================================================

	//Get et Set
	public int getHauteurTotalCarte() {
		return hauteurTotalCarte;
	}

	public void setHauteurTotalCarte(int hauteurTotalCarte) {
		this.hauteurTotalCarte = hauteurTotalCarte;
	}

	public int getLargeurTotalCarte() {
		return largeurTotalCarte;
	}

	public void setLargeurTotalCarte(int largeurTotalCarte) {
		this.largeurTotalCarte = largeurTotalCarte;
	}

	public int getNbCercleRechercheDrone() {
		return nbCercleRechercheDrone;
	}

	public void setNbCercleRechercheDrone(int nbCercleRechercheDrone) {
		this.nbCercleRechercheDrone = nbCercleRechercheDrone;
	}

	public int getBordureCarteHauteur() {
		return bordureCarteHauteur;
	}

	public void setBordureCarteHauteur(int bordureCarteHauteur) {
		this.bordureCarteHauteur = bordureCarteHauteur;
	}

	public int getBordureCarteLargeur() {
		return bordureCarteLargeur;
	}

	public void setBordureCarteLargeur(int bordureCarteLargeur) {
		this.bordureCarteLargeur = bordureCarteLargeur;
	}

	public int getHauteurCarte() {
		return hauteurCarte;
	}

	public void setHauteurCarte(int hauteurCarte) {
		this.hauteurCarte = hauteurCarte;
	}

	public int getLargeurCarte() {
		return largeurCarte;
	}

	public void setLargeurCarte(int largeurCarte) {
		this.largeurCarte = largeurCarte;
	}

	public int getLargeurCote() {
		return largeurCote;
	}

	public void setLargeurCote(int largeurCote) {
		this.largeurCote = largeurCote;
	}

	public int getMoitieCarte() {
		return moitieCarte;
	}

	public void setMoitieCarte(int moitieCarte) {
		this.moitieCarte = moitieCarte;
	}

	public int[] getPort() {
		return port;
	}

	public void setPort(int[] port) {
		this.port = port;
	}

	public int getProbabiliteNaufrageBateau() {
		return probabiliteNaufrageBateau;
	}

	public void setProbabiliteNaufrageBateau(int probabiliteNaufrageBateau) {
		this.probabiliteNaufrageBateau = probabiliteNaufrageBateau;
	}

	public int getVie() {
		return vie;
	}

	public void setVie(int vie) {
		this.vie = vie;
	}

	public int getNbBateauParTour() {
		return nbBateauParTour;
	}

	public void setNbBateauParTour(int nbBateauParTour) {
		this.nbBateauParTour = nbBateauParTour;
	}

	public int getNbDrones() {
		return nbDrones;
	}

	public void setNbDrones(int nbDrones) {
		this.nbDrones = nbDrones;
	}

	public int getPleinEssenceDrone() {
		return pleinEssenceDrone;
	}

	public void setPleinEssenceDrone(int pleinEssenceDrone) {
		this.pleinEssenceDrone = pleinEssenceDrone;
	}

	public int getPleinEssenceBateauSauvetage() {
		return pleinEssenceBateauSauvetage;
	}

	public void setPleinEssenceBateauSauvetage(int pleinEssenceBateauSauvetage) {
		this.pleinEssenceBateauSauvetage = pleinEssenceBateauSauvetage;
	}

	public int getPleinEssenceBateau() {
		return pleinEssenceBateau;
	}

	public void setPleinEssenceBateau(int pleinEssenceBateau) {
		this.pleinEssenceBateau = pleinEssenceBateau;
	}

	public int getProbaDetectionBeau() {
		return probaDetectionBeau;
	}

	public void setProbaDetectionBeau(int probaDetectionBeau) {
		this.probaDetectionBeau = probaDetectionBeau;
	}

	public int getProbaDetectionNuageux() {
		return probaDetectionNuageux;
	}

	public void setProbaDetectionNuageux(int probaDetectionNuageux) {
		this.probaDetectionNuageux = probaDetectionNuageux;
	}

	public int getProbaDetectionPluie() {
		return probaDetectionPluie;
	}

	public void setProbaDetectionPluie(int probaDetectionPluie) {
		this.probaDetectionPluie = probaDetectionPluie;
	}

	public int getProbaDetectionBrume() {
		return probaDetectionBrume;
	}

	public void setProbaDetectionBrume(int probaDetectionBrume) {
		this.probaDetectionBrume = probaDetectionBrume;
	}

	public int getProbaNaufrageBeau() {
		return probaNaufrageBeau;
	}

	public void setProbaNaufrageBeau(int probaNaufrageBeau) {
		this.probaNaufrageBeau = probaNaufrageBeau;
	}

	public int getProbaNaufrageNuageux() {
		return probaNaufrageNuageux;
	}

	public void setProbaNaufrageNuageux(int probaNaufrageNuageux) {
		this.probaNaufrageNuageux = probaNaufrageNuageux;
	}

	public int getProbaNaufragePluie() {
		return probaNaufragePluie;
	}

	public void setProbaNaufragePluie(int probaNaufragePluie) {
		this.probaNaufragePluie = probaNaufragePluie;
	}

	public int getProbaNaufrageBrume() {
		return probaNaufrageBrume;
	}

	public void setProbaNaufrageBrume(int probaNaufrageBrume) {
		this.probaNaufrageBrume = probaNaufrageBrume;
	}

	public int getMalusDetection() {
		return malusDetection;
	}

	public void setMalusDetection(int malusDetection) {
		this.malusDetection = malusDetection;
	}

	public int getBonusVieKitSurvie() {
		return bonusVieKitSurvie;
	}

	public void setBonusVieKitSurvie(int bonusVieKitSurvie) {
		this.bonusVieKitSurvie = bonusVieKitSurvie;
	}

	public int getMeteo() {
		return meteo;
	}

	public void setMeteo(int meteo) {
		this.meteo = meteo;
	}

	public LinkedList<Drone> getDrones() {
		return drones;
	}

	public void setDrones(LinkedList<Drone> drones) {
		this.drones = drones;
	}

	public LinkedList<Bateau> getBateaux() {
		return bateaux;
	}

	public void setBateaux(LinkedList<Bateau> bateaux) {
		this.bateaux = bateaux;
	}

	public LinkedList<BateauSauvetage> getBateauxSauvetages() {
		return bateauxSauvetages;
	}

	public void setBateauxSauvetages(LinkedList<BateauSauvetage> bateauxSauvetages) {
		this.bateauxSauvetages = bateauxSauvetages;
	}

	public LinkedList<Naufrage> getNaufrages() {
		return naufrages;
	}

	public void setNaufrages(LinkedList<Naufrage> naufrages) {
		this.naufrages = naufrages;
	}

	public ArrayList<Naufrage> getNaufragesSauves() {
		return naufragesSauves;
	}

	public void setNaufragesSauves(ArrayList<Naufrage> naufragesSauves) {
		this.naufragesSauves = naufragesSauves;
	}

	public ArrayList<Naufrage> getNaufragesSauvesCote() {
		return naufragesSauvesCote;
	}

	public void setNaufragesSauvesCote(ArrayList<Naufrage> naufragesSauvesCote) {
		this.naufragesSauvesCote = naufragesSauvesCote;
	}

	public ArrayList<Naufrage> getNaufragesMorts() {
		return naufragesMorts;
	}

	public void setNaufragesMorts(ArrayList<Naufrage> naufragesMorts) {
		this.naufragesMorts = naufragesMorts;
	}

	public ArrayList<Naufrage> getNaufragesSauvesBateau() {
		return naufragesSauvesBateau;
	}

	public void setNaufragesSauvesBateau(ArrayList<Naufrage> naufragesSauvesBateau) {
		this.naufragesSauvesBateau = naufragesSauvesBateau;
	}

	public ArrayList<CaseMeteo> getCasesMeteo() {
		return casesMeteo;
	}

	public void setCasesMeteo(ArrayList<CaseMeteo> casesMeteo) {
		this.casesMeteo = casesMeteo;
	}

	public int getSensVent() {
		return sensVent;
	}

	public void setSensVent(int sensVent) {
		this.sensVent = sensVent;
	}

	public Utile getU() {
		return u;
	}

	public void setU(Utile u) {
		this.u = u;
	}

	public int getCompteurBateau() {
		return compteurBateau;
	}

	public void setCompteurBateau(int compteurBateau) {
		this.compteurBateau = compteurBateau;
	}

	public int getCompteurNaufrage() {
		return compteurNaufrage;
	}

	public void setCompteurNaufrage(int compteurNaufrage) {
		this.compteurNaufrage = compteurNaufrage;
	}

	public int getNbMaxBateauCarte() {
		return nbMaxBateauCarte;
	}

	public void setNbMaxBateauCarte(int nbMaxBateauCarte) {
		this.nbMaxBateauCarte = nbMaxBateauCarte;
	}

	public int getNbMaxNaufrageCarte() {
		return nbMaxNaufrageCarte;
	}

	public void setNbMaxNaufrageCarte(int nbMaxNaufrageCarte) {
		this.nbMaxNaufrageCarte = nbMaxNaufrageCarte;
	}

	public int getProbabiliteEnvoiAlerte() {
		return probabiliteEnvoiAlerte;
	}

	public void setProbabiliteEnvoiAlerte(int probabiliteEnvoiAlerte) {
		this.probabiliteEnvoiAlerte = probabiliteEnvoiAlerte;
	}

	public int getNbBateauSauvetage() {
		return nbBateauSauvetage;
	}

	public void setNbBateauSauvetage(int nbBateauSauvetage) {
		this.nbBateauSauvetage = nbBateauSauvetage;
	}

	public int getProbabiliteDetecteNaufrageBateau() {
		return probabiliteDetecteNaufrageBateau;
	}

	public void setProbabiliteDetecteNaufrageBateau(
			int probabiliteDetecteNaufrageBateau) {
		this.probabiliteDetecteNaufrageBateau = probabiliteDetecteNaufrageBateau;
	}

	public void setTous(LinkedList<Element> tous) {
		this.tous = tous;
	}

	public Fenetre getFen() {
		return fen;
	}

	public void setFen(Fenetre fen) {
		this.fen = fen;
	}

	public Dimension getDimFen() {
		return dimFen;
	}

	public void setDimFen(Dimension dimFen) {
		this.dimFen = dimFen;
	}

	public Dimensionnement getDim() {
		return dim;
	}

	public void setDim(Dimensionnement dim) {
		this.dim = dim;
	}

	public boolean getEstGraphique() {
		return estGraphique;
	}

	public void setEstGraphique(boolean estGraphique) {
		this.estGraphique = estGraphique;
	}

	public int getNbKitSurvie() {
		return nbKitSurvie;
	}

	public void setNbKitSurvie(int nbKitSurvie) {
		this.nbKitSurvie = nbKitSurvie;
	}

	public int getCapaciteMaxBateauSauvetage() {
		return capaciteMaxBateauSauvetage;
	}

	public void setCapaciteMaxBateauSauvetage(int capaciteMaxBateauSauvetage) {
		this.capaciteMaxBateauSauvetage = capaciteMaxBateauSauvetage;
	}

	public ArrayList<AlerteG> getAlertesg() {
		return alertesg;
	}

	public void setAlertesg(ArrayList<AlerteG> alertesg) {
		this.alertesg = alertesg;
	}

	public int getNbPassagersMaxBateau() {
		return nbPassagersMaxBateau;
	}

	public void setNbPassagersMaxBateau(int nbPassagersMaxBateau) {
		this.nbPassagersMaxBateau = nbPassagersMaxBateau;
	}

	public ListeAlerte getListeAlertes() {
		return listeAlertes;
	}

	public void setListeAlertes(ListeAlerte listeAlertes) {
		this.listeAlertes = listeAlertes;
	}

	public double[] getStats() {
		return stats;
	}

	public void setStats(double[] stats) {
		this.stats = stats;
	}
}