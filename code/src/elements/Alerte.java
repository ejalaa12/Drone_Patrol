package elements;

import organisation.Environnement;

public class Alerte {

	private int[] position;
	private int etat;
	/* 1=Alerte
	 * 2=AlerteMission
	 * 3=AlerteRecherche
	 * 4=AlerteBateauSauvetage
	 * 5=AlerteBateauSauvetageMission
	 * 6=AlerteSauvee
	 * 7=AlerteMorte
	 */
	private int temps;
	private int Tdecouvert = 0;
	private int Tsauve = 0;
	private int Tmort = 0;
	private Alerte next;
	private Drone dr;
	private Naufrage n;

	public Alerte(int[] position){
		this.position = position;
		this.etat = 1;
		this.temps = Environnement.temps;
		this.next = null;
		this.dr = null;
		this.n = null;
	}

	public Alerte(Drone dr){
		this.position = dr.getPosition();
		this.etat = 4;
		this.temps = Environnement.temps;
		this.next = null;
		this.dr = dr;
		this.n = null;
	}

	public void suivis(Naufrage n, Drone dr){
		this.etat = 4;
		this.n = n;
		this.dr = dr;
		this.position = dr.getPosition();
	}

	public int[] getPosition() {
		return position;
	}

	public void setPosition(int[] position) {
		this.position = position;
	}

	public int getEtat() {
		return etat;
	}

	public void setEtat(int etat) {
		this.etat = etat;
	}

	public int getTemps() {
		return Environnement.temps-this.temps;
	}

	public int getVraiTemps() {
		return this.temps;
	}
	
	public void setTemps(int temps) {
		this.temps = temps;
	}

	public Alerte getNext() {
		return this.next;
	}

	public void setNext(Alerte next) {
		this.next = next;
	}

	public Drone getDr() {
		return dr;
	}

	public void setDr(Drone dr) {
		this.dr = dr;
	}

	public Naufrage getN() {
		return n;
	}

	public void setN(Naufrage n) {
		this.n = n;
	}

	public int getTdecouvert() {
		return Tdecouvert;
	}

	public void setTdecouvert(int tdecouvert) {
		Tdecouvert = tdecouvert;
	}

	public int getTsauve() {
		return Tsauve;
	}

	public void setTsauve(int tsauve) {
		Tsauve = tsauve;
	}

	public int getTmort() {
		return Tmort;
	}

	public void setTmort(int tmort) {
		Tmort = tmort;
	}
}
