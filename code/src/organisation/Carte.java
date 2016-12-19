package organisation;

import organisationG.ConfigurationEnvironnement;

public class Carte {
	
	public static void main(String[] args) throws CloneNotSupportedException{
		ConfigurationEnvironnement config = new ConfigurationEnvironnement();
		config.setVisible(true);
		Environnement e = new Environnement(config.getLargeur(),config.getHauteur(),config.getCote(),
				config.getNbDrones(),config. getEssenceDrone(),config.getNbCercleRechercheDrone(),
				config.getNbKitSurvie(),config.getNbBateauSauvetage(),config.getEssenceBateauSauvetage(),
				config.getCapaciteNaufrageBateauSauvetage(),config.getNbBateauMax(),config.getEssenceBateau(),
				config.getNaufrageCarteMax(),config.getVieNaufrage(),config.getProbaDetectionBeau(),
				config.getProbaDetectionNuageux(),config.getProbaDetectePluie(),config.getProbaDetecteBrumeux(),
				config.getMalusDetection(),config.getMeteo());
		for(int i=0;i<100000;i++){
			System.out.println(i);
			e.Suivant();
		}
	}
}