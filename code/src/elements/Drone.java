package elements;

import java.util.ArrayList;
import java.util.Arrays;

import organisation.Environnement;
import organisation.Utile;

import elementsG.AlerteG;
import elementsG.DroneG;

public class Drone extends Element{

	//Attributs
	/* 1=DronePatrouille 
	 * 2=DroneDebutMission
	 * 3=DroneRecherche
	 * 4=DroneSuiveur
	 * 5=DroneCalibrage
	 * 6=DroneRetourEssence
	 * 7=DroneDetruit*/
	private Naufrage n;
	private int nbKitSurvie = 3;
	private Alerte alerte;
	private int[] positionPatrouille;
	private Utile u = new Utile();
	private DroneG dg;

	//Constructeur
	public Drone(String nom, int[] position, int essence, Environnement e, int numero){
		super(nom, position, essence, e);
		this.etat = 5;
		this.numero = numero;
		this.nbKitSurvie = e.getNbKitSurvie();
		if(position!=null){
			definirPositionPatrouille();
			if(e.getEstGraphique()){
				this.dg = new DroneG(this, e.getFen().getPanel_drone(), e.getDim());
				dg.affiche(e.getFen().getPanel_drone().getGraphics());
			}
		}
	}

	//Methodes Publics
	public void presentation(){
		super.presentation();
		switch(etat){
		case 1: // DronePatrouille
			System.out.println("Je suis en mode Patrouille.");
			break;
		case 2: // DroneDebutMission
			System.out.println("Je suis en Début de mission. J'ai recu une alerte en: "+Arrays.toString(this.alerte.getPosition()));
			break;
		case 3: // DroneRecherche
			System.out.println("Je suis en mode Recherche. Je cherche des naufragés autour du lieu du naufrage: "+Arrays.toString(this.alerte.getPosition()));
			break;
		case 4: // DroneSuiveur
			System.out.println("J'ai trouvé le naufragé. Je suis en mode Suiveur.");
			break;
		case 5: // DroneCalibre
			System.out.println("Je suis en Recalibrage vers "+Arrays.toString(this.positionPatrouille));
			break;
		case 6: // DroneRetourEssence
			System.out.println("Je n'est plus d'essence, je rentre au port");
			break;
		case 7: // DroneDetruit
			System.out.println("Je me suis craché en "+Arrays.toString(this.position));
			break;
		default: 
			System.out.println("Cet état n'existe pas!");
		}
	}

	public void deplacer(){
		switch(etat){
		case 1: // DronePatrouille
			this.position = positionSuivanteDronePatrouille(this.position);
			this.setEssence(getEssence()-1);
			break;
		case 2: // DroneDebutMission
			this.position = positionSuivanteObjet(this.position, this.alerte.getPosition());
			this.setEssence(getEssence()-1);
			break;
		case 3: // DroneRecherche
			this.position = positionSuivanteDroneRecherche(this.position, this.alerte.getPosition());
			this.setEssence(getEssence()-1);
			break;
		case 4: // DroneSuiveur
			if(this.n!=null){
				this.position = positionSuivanteObjet(this.position, n.getPosition());
			}
			else
				this.position = positionSuivanteObjet(this.position, positionPatrouille);
			this.setEssence(getEssence()-1);
			break;
		case 5: // DroneCalibrage
			this.position = positionSuivanteObjet(this.position, positionPatrouille);
			this.setEssence(getEssence()-1);
			break;
		case 6: // DroneRetourEssence
			this.position = positionSuivanteObjet(this.position, e.getPort());
			this.setEssence(getEssence()-1);
			break;
		case 7:
			break;
		default: 
			System.out.println("Cet état n'existe pas!");
		}
		if(e.getEstGraphique())
			dg.deplacer();
	}

	public void definirEtat(){
		testDetectionNaufrageMeteo(); // differents cas
		testDroneCalibre(); // si oui passe à DronePatrouille
		testArrivePort(); // si oui passe à DronePatrouille et fait le plein d'essence
		testNaufrageMortOuSauveOuTrouve(); // si oui passe à DroneCalibrage
		testRecoitAlerte(); // si oui passe à DroneDebutMission
		testArriveAlerte(); //si oui passe à DroneRecherche
		testEssence(); // s'il manque de l'essence il va faire le plein sans detour
		definirPositionPatrouille();
	}

	public ArrayList<int[]> parcoursDroneRecherche(int[] alerte){
		ArrayList<int[]> tab = new ArrayList<int[]>();
		int[] position = alerte.clone();
		while(position[0]!=alerte[0]-e.getNbCercleRechercheDrone() || position[1]!=alerte[1]){
			tab.add(position.clone());
			position = positionSuivanteDroneRecherche(position, alerte);
		}
		tab.add(position.clone());
		return tab;
	}

	//Methodes Privates
	private void testDetectionNaufrageMeteo(){
		int[][] voisins;
		if(position[1]%2==0){
			voisins = new int[][]{ {1 ,1 , 0 ,-1 ,-1 ,0 ,0} , 	//colonne
					{1 ,0 ,-1 , 0 , 1 ,1 ,0} };		//ligne
		}
		else{
			voisins = new int[][]{ {1 , 1 , 0 ,-1 ,-1 ,0 ,0} , 	//colonne
					{0 ,-1 ,-1 ,-1 , 0 ,1 ,0} };	//ligne
		}
		for (int i=0;i<7;i++){
			int col = position[1]+voisins[0][i]; int lig = position[0]+voisins[1][i];
			int[] verification = {lig,col};
			for (Naufrage n : e.getNaufrages()){
				if (Arrays.equals(n.getPosition(), verification) && !e.getListeAlertes().contains(n)){
					for(CaseMeteo cm : e.getCasesMeteo()){
						if (Arrays.equals(cm.getPosition(), verification)){
							int a = 0;
							if(i!=6)
								a = e.getMalusDetection();
							int b = (int)(Math.random()*100);
							switch(cm.getEtat()){
							case 1:
								if(b<(e.getProbaDetectionBeau()-a))
									testDetectionNaufrage(n);
								break;
							case 2:
								if(b<(e.getProbaDetectionNuageux()-a))
									testDetectionNaufrage(n);
								break;
							case 3:
								if(b<(e.getProbaDetectionPluie()-a))
									testDetectionNaufrage(n);
								break;
							case 4:
								if(b<(e.getProbaDetectionBrume()-a))
									testDetectionNaufrage(n);
								break;
							default:
								System.out.println("Probleme dans Drone");
								break;
							}
						}
					}
				}
			}
		}
	}

	private void testDroneCalibre(){
		if(Arrays.equals(this.position, this.positionPatrouille) && this.etat == 5)
			setEtat(1);
	}

	private void testArrivePort(){
		if(Arrays.equals(this.position, e.getPort())){
			this.nbKitSurvie = 3;
			this.essence = e.getPleinEssenceDrone();
		}
		if(this.etat == 6)
			setEtat(5);
	}

	private void testNaufrageMortOuSauveOuTrouve(){
		if(this.etat == 2 && this.alerte.getEtat()!=2){
			setEtat(5);
			this.alerte = null;
			this.n = null;
		}
		if(this.etat == 3 && this.alerte.getEtat()!=3){
			setEtat(5);
			this.alerte = null;
			this.n = null;
		}
		if(this.etat == 4 && this.alerte.getEtat()!=4 && this.alerte.getEtat()!=5){
			setEtat(5);
			this.alerte = null;
			this.n = null;
		}
		if(this.n!=null && (u.sortieCarte(this.n.getPosition(), this.e) || n.getPosition()[1]<=e.getLargeurCote())){
			this.alerte.setEtat(7);
			this.n = null;
		}
	}

	private void testRecoitAlerte(){
		if(!e.getListeAlertes().getAlertes().isEmpty() && (this.etat==1 || this.etat==5)){
			int a = 0;
			int b = 0;
			for(Drone dr : e.getDrones()){
				if(dr.getEtat()==1 || dr.getEtat()==5){
					b+=1;
					if(this.parcoursObjet(this.getPosition(), e.getListeAlertes().getFirstAlerte().getPosition()).size()<dr.parcoursObjet(dr.getPosition(), e.getListeAlertes().getFirstAlerte().getPosition()).size())
						a+=1;
				}
			}
			if(a == b-1){
				this.alerte = e.getListeAlertes().getFirstAlerte();
				this.alerte.setEtat(2);
				setEtat(2);
			}
		}
	}

	private void testArriveAlerte(){
		if(this.alerte!=null && Arrays.equals(this.position, this.alerte.getPosition()) && this.etat==2){
			this.alerte.setEtat(3);
			setEtat(3);
		}
	}

	private void testEssence(){
		if(this.essence==0){
			setEtat(7);
			return;
		}
		if(parcoursObjet(this.position, e.getPort()).size()>this.essence-5 && this.etat!=6){
			switch(this.etat){
			case 1: // DronePatrouille
				setEtat(6);
				break;
			case 2: // DroneDebutMission
				setEtat(6);
				this.alerte.setEtat(1);
				break;
			case 3: // DroneRecherche
				setEtat(6);
				this.alerte.setEtat(1);
				break;
			case 4: // DroneSuiveur
				setEtat(6);
				this.alerte.setEtat(1);				
				this.n = null;
				break;
			case 5: // DroneCalibrage
				setEtat(6);
				break;
			default:
				System.out.println("probleme dans drone test non Retour");
			}
		}
	}

	private void definirPositionPatrouille(){
		if(this.positionPatrouille==null){
			if(e.getNbDrones()==1)
				this.positionPatrouille = e.getPort().clone();
			else{
				ArrayList<int[]> parcours = parcoursDronePatrouille();
				int a = parcours.size();
				int b = Math.round(a/e.getNbDrones());
				this.positionPatrouille = parcours.get(this.numero*b-1);
			}
		}
		else{
			this.positionPatrouille = positionSuivanteDronePatrouille(this.positionPatrouille);
		}
	}

	private void testDetectionNaufrage(Naufrage n){
		switch(this.etat){
		case 1: // DronePatrouille
			setEtat(4); // passe à DroneSuiveur
			this.n = n;
			if(this.nbKitSurvie!=0){
				n.setEssence(n.getEssence()+e.getBonusVieKitSurvie());
				n.setKitSurvie(true);
				this.nbKitSurvie-=1;
			}
			for(Alerte a : e.getListeAlertes().getAlertesTous()){
				if(u.dansZoneAlerteSeule(n.getPosition(), e, a.getPosition(), this)){
					a.suivis(n, this);
					this.alerte = a;
					return;
				}
			}
			Alerte alerte1 = new Alerte(this);
			e.getListeAlertes().add(alerte1);
			this.alerte = alerte1;
			break;
		case 2: // DroneDebutMission
			setEtat(4); // passe à DroneSuiveur
			this.n = n;
			if(this.nbKitSurvie!=0){
				n.setEssence(n.getEssence()+e.getBonusVieKitSurvie());
				n.setKitSurvie(true);
				this.nbKitSurvie-=1;
			}
			for(Alerte a : e.getListeAlertes().getAlertesTous()){
				if(u.dansZoneAlerteSeule(n.getPosition(), e, a.getPosition(), this)){
					if(a == this.alerte){
						this.alerte.suivis(n, this);
						return;
					}
					else{
						this.alerte.setEtat(1);
						this.alerte = a;
						a.suivis(n, this);
						return;
					}
				}
			}
			Alerte Alerte2 = new Alerte(this);
			e.getListeAlertes().add(Alerte2);
			this.alerte = Alerte2;
			break;
		case 3: // DroneRecherche
			setEtat(4); // passe à DroneSuiveur
			this.n = n;
			if(this.nbKitSurvie!=0){
				n.setEssence(n.getEssence()+e.getBonusVieKitSurvie());
				n.setKitSurvie(true);
				this.nbKitSurvie-=1;
			}
			this.alerte.suivis(n, this);
			break;
		case 4: // DroneSuiveur
			if(this.n != n && !Arrays.equals(n.getPosition(),this.getPosition()) && !u.sortieZonePatrouille(n.getPosition(), e) && !u.dansZoneAlerte(n.getPosition(), e, this)){ // parce-que sinon le drone sort de la carte, on suppose qu'il ne peut pas avoir deux alerte dans la même zone de recherche d'une alerte
				Alerte Alerte3 = new Alerte(n.getPosition().clone());
				e.getListeAlertes().add(Alerte3);
				if(e.getEstGraphique()){
					AlerteG alg = new AlerteG(e.getDim().deduirePositionGraphique(n.getPosition().clone()), e.getFen().getPanel_meteo(), e.getDim());
					if (e.getAlertesg().size()==10){
						e.getAlertesg().remove(0);
					}
					e.getAlertesg().add(alg);
					alg.alerter();
				}
			}
			break;
		case 5: // DroneCalibrage
			setEtat(4); // passe à DroneSuiveur
			this.n = n;
			if(this.nbKitSurvie!=0){
				n.setEssence(n.getEssence()+e.getBonusVieKitSurvie());
				n.setKitSurvie(true);
				this.nbKitSurvie-=1;
			}
			for(Alerte a : e.getListeAlertes().getAlertesTous()){
				if(u.dansZoneAlerteSeule(n.getPosition(), e, a.getPosition(), this)){
					a.suivis(n, this);
					this.alerte = a;
					return;
				}
			}
			Alerte alerte4 = new Alerte(this);
			e.getListeAlertes().add(alerte4);
			this.alerte = alerte4;
			break;
		case 6: // DroneRetourEssence
			if(this.n != n && !Arrays.equals(n.getPosition(),this.getPosition()) && !u.sortieZonePatrouille(n.getPosition(), e) && !u.dansZoneAlerte(n.getPosition(), e, this)){ // parce-que sinon le drone sort de la carte, on suppose qu'il ne peut pas avoir deux alerte dans la même zone de recherche d'une alerte
				Alerte Alerte5 = new Alerte(n.getPosition().clone());
				e.getListeAlertes().add(Alerte5);
				if(e.getEstGraphique()){
					AlerteG alg = new AlerteG(e.getDim().deduirePositionGraphique(n.getPosition().clone()), e.getFen().getPanel_meteo(), e.getDim());
					if (e.getAlertesg().size()==10){
						e.getAlertesg().remove(0);
					}
					e.getAlertesg().add(alg);
					alg.alerter();
				}
			}
			break;
		default: 
			System.out.println("Probleme dans test detection naufrage 'drone'");
		};
	}

	private int[] positionSuivanteDronePatrouille(int[] position){

		if(position[0]==e.getPort()[0] && position[1]==e.getPort()[1]){//port

			if(e.getPort()[1]%2==0){
				position[0] = position[0]+1;
				position[1] = position[1]+1;
			}
			else
				position[1] = position[1]+1;
		}
		else if((position[1]-e.getPort()[1])%4==1 && position[1]<e.getLargeurCarte()-1){//phase descendante

			if(position[1]%2==0 && position[0]+2>e.getHauteurCarte()+e.getBordureCarteHauteur()){
				position[0] = position[0]+1;
				position[1] = position[1]+1;
			}
			else if(position[0]+1>e.getHauteurCarte()+e.getBordureCarteHauteur())
				position[1] = position[1]+1;
			else if(position[0]+1==e.getMoitieCarte() && position[1]-1!=e.getPort()[1]){

				if(position[1]%2==0){
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}
				else
					position[1] = position[1]-1;
			}
			else if(position[0]==e.getPort()[0] && position[1]-1==e.getPort()[1] && position[1]%2==1)
				position[1] = position[1]-1;
			else if(position[0]+1==e.getPort()[0] && position[1]-1==e.getPort()[1] && position[1]%2==0){
				position[0] = position[0]+1;
				position[1] = position[1]-1;
			}
			else
				position[0] = position[0]+1;
		}
		else if((position[1]-e.getPort()[1])%4==3 && position[1]<e.getLargeurCarte()-1){//phase montante

			if(position[1]%2==1 && position[0]-2<1+e.getBordureCarteHauteur()){
				position[0] = position[0]-1;
				position[1] = position[1]-1;
			}
			else if(position[0]-1<1+e.getBordureCarteHauteur())
				position[1] = position[1]-1;
			else if(position[0]-1==e.getMoitieCarte()){

				if(position[1]%2==1){
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
				else
					position[1] = position[1]+1;
			}
			else
				position[0] = position[0]-1;
		}
		else if(position[1]!=e.getPort()[1] && (position[1]-e.getPort()[1])%4==2 && position[1]<e.getLargeurCarte()-1){//virage exterieur

			if(position[0]==e.getHauteurCarte()+e.getBordureCarteHauteur()){

				if(position[1]%2==0)
					position[1] = position[1]+1;
				else{
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
			}
			else{

				if(position[1]%2==1)
					position[1] = position[1]-1;
				else{
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}

			}
		}
		else if((position[1]-e.getPort()[1])%4==0 && position[1]<e.getLargeurCarte()-1){//virage interieur

			if(position[1]%2==0){

				if(position[0]==e.getMoitieCarte()){
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				else
					position[1] = position[1]-1;
			}
			else{

				if(position[0]==e.getMoitieCarte()){
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
				else
					position[1] = position[1]+1;
			}
		}
		else{//2 derniere colonne

			if(position[1]%2==0 && position[1]==e.getLargeurCarte()-1){

				if(position[1]==e.getLargeurCarte()-1 && (position[1]-e.getPort()[1])%4==2){//entree virage exterieur debut

					if(position[0]==1+e.getBordureCarteHauteur()){
						position[0] = position[0]+1;
						position[1] = position[1]-1;
					}
					else
						position[1] = position[1]+1;
				}
				else if(position[1]==e.getLargeurCarte() && (position[1]-e.getPort()[1])%4==3){//entree virage exterieur fin

					if(position[0]==2+e.getBordureCarteHauteur()){
						position[0] = position[0]-1;
						position[1] = position[1]-1;
					}
					else
						position[0] = position[0]-1;
				}
				else if(position[1]==e.getLargeurCarte()-1 && (position[1]-e.getPort()[1])%4==3){//entree phase montante debut/fin

					if(position[0]==1+e.getBordureCarteHauteur())
						position[1] = position[1]-1;
					else
						position[0] = position[0]-1;
				}
				else if(position[1]==e.getLargeurCarte()-1 && ((position[1]-e.getPort()[1])%4==1 || position[1]==e.getPort()[1])){//entree phase descendante/virage interieur debut

					if(position[0]+1==e.getMoitieCarte() && (position[1]-e.getPort()[1])%4==1){
						position[0] = position[0]+1;
						position[1] = position[1]-1;
					}
					else if(position[0]+1==e.getMoitieCarte() && (position[1]-e.getPort()[1])%4==0)
						position[1] = position[1]-1;
					else if(position[0]+1==e.getHauteurCarte()+e.getBordureCarteHauteur())
						position[1] = position[1]+1;
					else
						position[0] = position[0]+1;
				}
				else if(position[1]==e.getLargeurCarte() && (position[1]==e.getPort()[1]+2 || position[1]==e.getPort()[1]+1)){//entree phase descendante/virage interieur fin

					if(position[0]==2+e.getBordureCarteHauteur()){
						position[0] = position[0]-1;
						position[1] = position[1]-1;
					}
					else
						position[0] = position[0]-1;
				}
			}
			else{

				if(position[1]==e.getLargeurCarte()-1 && (position[1]-e.getPort()[1])%4==2){//entree virage exterieur debut

					if(position[0]==1+e.getBordureCarteHauteur())
						position[1] = position[1]-1;
					else{
						position[0] = position[0]-1;
						position[1] = position[1]+1;
					}
				}
				else if(position[1]==e.getLargeurCarte() && (position[1]-e.getPort()[1])%4==3){//entree virage exterieur fin

					if(position[0]==1+e.getBordureCarteHauteur())
						position[1] = position[1]-1;
					else
						position[0] = position[0]-1;
				}
				else if(position[1]==e.getLargeurCarte()-1 && (position[1]-e.getPort()[1])%4==3){//entree phase montante debut/fin

					if(position[0]==2+e.getBordureCarteHauteur()){
						position[0] = position[0]-1;
						position[1] = position[1]-1;
					}	
					else
						position[0] = position[0]-1;
				}
				else if(position[1]==e.getLargeurCarte()-1 && ((position[1]-e.getPort()[1])%4==1 || (position[1]-e.getPort()[1])%4==0)){//entree phase descendante/virage interieur debut

					if(position[0]+1==e.getMoitieCarte() && (position[1]-e.getPort()[1])%4==1)
						position[1] = position[1]-1;
					else if(position[0]+1==e.getMoitieCarte() && (position[1]-e.getPort()[1])%4==0){
						position[0] = position[0]-1;
						position[1] = position[1]-1;
					}
					else if(position[0]+1==e.getHauteurCarte()+e.getBordureCarteHauteur())
						position[1] = position[1]+1;
					else
						position[0] = position[0]+1;
				}
				else if(position[1]==e.getLargeurCarte() && ((position[1]-e.getPort()[1])%4==2 || (position[1]-e.getPort()[1])%4==1)){//entree phase descendante/virage interieur fin

					if(position[0]==2+e.getBordureCarteHauteur())
						position[1] = position[1]-1;
					else
						position[0] = position[0]-1;
				}
			}
		}
		return position;
	}

	private int[] positionSuivanteDroneRecherche(int[] position, int[] alerte){
		if(position[0]==alerte[0]-e.getNbCercleRechercheDrone() && position[1]==alerte[1]){
			setEtat(5);
			this.position = positionSuivanteObjet(this.position, positionPatrouille);
			this.alerte.setEtat(7);
		}
		else if(alerte[1]%2==0){

			if(position[0]==alerte[0] && position[1]==alerte[1])
				position[1] = position[1]-1;
			else if(position[1]<alerte[1] && position[0]<=alerte[0]){//haut gauche

				if(position[0]==alerte[0] && position[1]%2==0){
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				else
					if(position[1]%2==0){
						position[0] = position[0]+1;
						position[1] = position[1]-1;
					}
					else{
						position[1] = position[1]-1;
					}
			}
			else if(position[1]<alerte[1] && position[0]>alerte[0]){//bas gauche

				if(position[1]%2==0){
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				else
					position[1] = position[1]+1;
			}
			else if(position[1]>=alerte[1] && position[0]>alerte[0]){//bas droit

				if(position[1]%2==0)
					position[1] = position[1]+1;
				else{
					position[0] = position[0]-1;
					position[1] = position[1]+1;
				}
			}
			else if(position[1]>=alerte[1] && position[0]<=alerte[0]){//haut droit

				if(position[1]%2==0)
					position[1] = position[1]-1;
				else{
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
			}
		}
		else if(alerte[1]%2==1){

			if(position[0]==alerte[0] && position[1]==alerte[1]){
				position[0] = position[0]-1;
				position[1] = position[1]-1;
			}
			else if(position[1]<alerte[1] && position[0]<alerte[0]){//haut gauche

				if(position[1]%2==0){
					position[0] = position[0]+1;
					position[1] = position[1]-1;
				}
				else{
					position[1] = position[1]-1;
				}
			}
			else if(position[1]<alerte[1] && position[0]>=alerte[0]){//bas gauche

				if(position[1]%2==0){
					position[0] = position[0]+1;
					position[1] = position[1]+1;
				}
				else
					position[1] = position[1]+1;
			}
			else if(position[1]>=alerte[1] && position[0]>=alerte[0]){//bas droit

				if(position[0]==alerte[0]){
					if(position[1]%2==0)
						position[1] = position[1]+1;
					else{
						position[0] = position[0]-1;
						position[1] = position[1]-1;
					}
				}
				else
					if(position[1]%2==1){
						position[0] = position[0]-1;
						position[1] = position[1]+1;
					}
					else
						position[1] = position[1]+1;
			}
			else if(position[1]>=alerte[1] && position[0]<alerte[0]){//haut droit

				if(position[1]%2==0)
					position[1] = position[1]-1;
				else{
					position[0] = position[0]-1;
					position[1] = position[1]-1;
				}
			}
		}
		else{
			presentation();
			System.out.println("j'ai pas bougé!!!!!!!!!!!!!!!!!!!!!!!!");
		}
		return position;
	}

	private ArrayList<int[]> parcoursDronePatrouille(){
		ArrayList<int[]> tab = new ArrayList<int[]>();
		int[] position = e.getPort().clone();
		position = positionSuivanteDronePatrouille(position);
		while(!Arrays.equals(position,e.getPort())){
			tab.add(position.clone());
			position = positionSuivanteDronePatrouille(position);
		}
		tab.add(position.clone());
		return tab;
	}

	//Gets et Sets
	public Naufrage getN() {
		return n;
	}

	public void setN(Naufrage n) {
		this.n = n;
	}

	public int getNbKitSurvie() {
		return nbKitSurvie;
	}

	public void setNbKitSurvie(int nbKitSurvie) {
		this.nbKitSurvie = nbKitSurvie;
	}

	public Alerte getAlerte() {
		return alerte;
	}

	public void setAlerte(Alerte alerte) {
		this.alerte = alerte;
	}

	public int[] getPositionPatrouille() {
		return positionPatrouille;
	}

	public void setPositionPatrouille(int[] positionPatrouille) {
		this.positionPatrouille = positionPatrouille;
	}

	public Utile getU() {
		return u;
	}

	public void setU(Utile u) {
		this.u = u;
	}

	public DroneG getDg() {
		return dg;
	}

	public void setDg(DroneG dg) {
		this.dg = dg;
	}

}
