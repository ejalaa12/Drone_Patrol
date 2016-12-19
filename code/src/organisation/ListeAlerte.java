package organisation;

import java.util.ArrayList;

import elements.Alerte;
import elements.Naufrage;

public class ListeAlerte{

	private Alerte first;
	private Alerte last;
	private int size;

	public ListeAlerte(){
		this.first=null;
		this.last=null;
		this.size=0;
	}

	public void add(Alerte alerte) {
		if(this.first==null){
			this.first=alerte;
			this.last=first;
		}
		else{
			Alerte curent = this.first;
			while (curent.getNext() != null) {
				curent = curent.getNext();
			}
			curent.setNext(alerte);
			this.last = alerte;
		}
		this.size++;
	}

	public void remove(Alerte alerte){
		if(!this.isEmpty()){
			Alerte curent = this.first;
			while (curent.getNext() != alerte) {
				curent = curent.getNext();
			}
			if(alerte!=this.last)
				curent.setNext(alerte.getNext());
			else{
				this.last = curent;
				curent.setNext(null);
			}
			this.size--;
		}
	}

	public boolean isEmpty(){
		if(this.last==null)
			return true;
		return false;
	}

	public Alerte getFirstAlerte(){
		Alerte curent = this.first;
		while (curent.getEtat() != 1 && curent!=null) {
			curent = curent.getNext();
		}
		return curent;
	}

	public Alerte getFirstAlerteBateauSauvetage(){
		Alerte curent = this.first;
		while (curent.getEtat() != 4) {
			curent = curent.getNext();
		}
		return curent;
	}

	public boolean containAlerteBateauSauvetage(){
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==4)
				return true;
			curent = curent.getNext();
		}
		return false;
	}

	public boolean contains(Naufrage n){
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getN()==n)
				return true;
			curent = curent.getNext();
		}
		return false;
	}

	public int sizeAlertes(){
		int i = 0;
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==1)
				i++;
			curent = curent.getNext();
		}
		return i;
	}

	public int sizeAlertesMissions(){
		int i = 0;
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==2)
				i++;
			curent = curent.getNext();
		}
		return i;
	}

	public int sizeAlertesRecherches(){
		int i = 0;
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==3)
				i++;
			curent = curent.getNext();
		}
		return i;
	}

	public int sizeAlertesBateauxSauvetage(){
		int i = 0;
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==4)
				i++;
			curent = curent.getNext();
		}
		return i;
	}

	public int sizeAlertesBateauxSauvetageMissions(){
		int i = 0;
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==5)
				i++;
			curent = curent.getNext();
		}
		return i;
	}

	public ArrayList<Alerte> getAlertesTous(){
		ArrayList<Alerte> tous = new ArrayList<Alerte>();
		Alerte curent = this.first;
		while (curent!=null) {
			tous.add(curent);
			curent = curent.getNext();
		}
		return tous;
	}

	public ArrayList<Alerte> getAlertes(){
		ArrayList<Alerte> alertes = new ArrayList<Alerte>();
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==1)
				alertes.add(curent);
			curent = curent.getNext();
		}
		return alertes;
	}

	public ArrayList<Alerte> getAlertesMissions(){
		ArrayList<Alerte> Missions = new ArrayList<Alerte>();
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==2)
				Missions.add(curent);
			curent = curent.getNext();
		}
		return Missions;
	}

	public ArrayList<Alerte> getAlertesRecherches(){
		ArrayList<Alerte> alertesRecherches = new ArrayList<Alerte>();
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==3)
				alertesRecherches.add(curent);
			curent = curent.getNext();
		}
		return alertesRecherches;
	}

	public ArrayList<Alerte> getAlertesBateauxSauvetages(){
		ArrayList<Alerte> alertesBateauxSauvetages = new ArrayList<Alerte>();
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==4)
				alertesBateauxSauvetages.add(curent);
			curent = curent.getNext();
		}
		return alertesBateauxSauvetages;
	}

	public ArrayList<Alerte> getAlertesBateauxSauvetagesMissions(){
		ArrayList<Alerte> alertesBateauxSauvetagesMission = new ArrayList<Alerte>();
		Alerte curent = this.first;
		while (curent!=null) {
			if(curent.getEtat()==5)
				alertesBateauxSauvetagesMission.add(curent);
			curent = curent.getNext();
		}
		return alertesBateauxSauvetagesMission;
	}

	public Alerte get(int i){
		if(!isEmpty()){
			int j = 0;
			Alerte curent = this.first;
			while (curent!=null && j!=i) {
				j++;
				curent = curent.getNext();
			}
			return curent;
		}
		return null;
	}

	public Alerte getFirst() {
		return first;
	}

	public void setFirst(Alerte first) {
		this.first = first;
	}

	public Alerte getLast() {
		return last;
	}

	public void setLast(Alerte last) {
		this.last = last;
	}

	public int size() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}
}