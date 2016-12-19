package organisationG;

import java.awt.Dimension;

public class Dimensionnement {
	/**
	 * Cette Classe dimensionne tout les composants graphiques en fonction de la taille de la fenetre et de la carte.
	 */

	private int largeurCarte;
	private int hauteurCarte;
	private int largeurFenetre;
	private int hauteurFenetre;

	private int tailleHexagone;
	private int largeurHexagone;
	private int hauteurHexagone;

	private int tailleDrone;
	private int tailleBateau;
	private int tailleNaufrage;

	private int horizSpace;
	private int vertSpace;

	public Dimensionnement(Dimension DimCarte, Dimension DimFenetre){
		this.largeurCarte = (int) DimCarte.getWidth();
		this.hauteurCarte = (int) DimCarte.getHeight();
		this.largeurFenetre = (int) DimFenetre.getWidth();
		this.hauteurFenetre = (int) DimFenetre.getHeight();

		dimensionnementHexagones();
		espacement();
		tailleDrone = tailleHexagone -2;
		tailleBateau = tailleHexagone -2;
		tailleNaufrage = tailleHexagone -2;
	}

	public void dimensionnementHexagones(){
		/*
		 * Cette méthode dimensionne les hexagones en fonction des dimensions de la fenetre et de la taille de la carte
		 */
		if (largeurCarte%2 ==0){
			tailleHexagone = (int) Math.min(Math.floor(2*largeurFenetre/(3*largeurCarte+1)), Math.floor(hauteurFenetre/(Math.sqrt(3)*(hauteurCarte+0.5))) );
		}
		else{
			tailleHexagone = (int) Math.min(Math.floor(largeurFenetre/(1.5*largeurCarte+0.5)), Math.floor(hauteurFenetre/(Math.sqrt(3)*(hauteurCarte+0.5))));
		}
		largeurHexagone = tailleHexagone*2;
		hauteurHexagone = (int) Math.round(Math.sqrt(3)*largeurHexagone/2);
	}

	public int[] pixToHex(int x, int y){
		int colonne = (int) Math.round( (x-tailleHexagone)/(horizSpace) +1);
		int ligne;
		if (colonne%2==0)
			ligne = (int) Math.floor(y/hauteurHexagone)+1;
		else
			ligne = (int) Math.floor(y/hauteurHexagone + 0.5);
		int[] res = {ligne, colonne};
		return res;
	}

	public double distance(int[] a, int[] b){
		return Math.sqrt(  Math.pow(a[0]-b[0],2) + Math.pow(a[1]-b[1],2) )   ;
	}

	public int[] pixToHex2(int x, int y){
		int[] posSouris = {x,y};
		double min = 1000;
		int[] posMin = {0,0};
		for (int i=0;i<positionCentres().length;i++)
			for (int j=0;j<positionCentres()[0].length;j++){
				double d = distance(positionCentres()[i][j],posSouris);
				if (d<min){
					posMin = new int[] {i+1,j+1};
					min = d;
				}
			}
		return posMin;
	}

	public int[] deduirePositionGraphique(int[] pos){
		int[] coord = new int[2];
		int ligne = pos[0]; int colonne = pos[1];
		int x,y;
		x = (int) Math.round(tailleHexagone + horizSpace*(colonne-1));
		if (colonne%2==0){
			y = (int) Math.round(hauteurHexagone + (ligne-1)*vertSpace);
		} else{
			y = (int) Math.round(hauteurHexagone/2 + (ligne-1)*vertSpace);
		}
		coord[0] = x;
		coord[1] = y;
		return coord;
	}

	public void espacement(){
		/* 
		 * Cette méthode crée l'espacement entre 2 cases hexagonales adjacentes
		 */
		horizSpace = (int) Math.round(0.75*largeurHexagone);
		vertSpace = hauteurHexagone;
	}

	public int[][][] positionCentres(){
		int [][][] tableau = new int[hauteurCarte][largeurCarte][2];
		for (int i=0;i<hauteurCarte;i++){
			for (int j=0;j<largeurCarte;j++){
				int x,y;
				if (j%2==0){
					x = tailleHexagone + horizSpace*j;
					y = (int) Math.round(hauteurHexagone/2 + i*vertSpace);
				} else{
					x = (int) Math.round(tailleHexagone + horizSpace*j);
					y = (int) Math.round(hauteurHexagone + i*vertSpace);
				}
				tableau[i][j][0] = x;
				tableau[i][j][1] = y;
			}
		}
		return tableau;
	}

	public int getLargeurFenetre(){
		return largeurFenetre;
	}

	public int getHauteurFenetre(){
		return hauteurFenetre;
	}

	public int getLargeurCarte(){
		return largeurCarte;
	}

	public int getHauteurCarte(){
		return hauteurCarte;
	}

	public int getTailleHexagone(){
		return tailleHexagone;
	}

	public int getLargeurHexagone(){
		return largeurHexagone;
	}

	public int getHauteurHexagone(){
		return hauteurHexagone;
	}

	public int getHoriz(){
		return horizSpace;
	}

	public int getVert(){
		return vertSpace;
	}

	public int getTailleDrone(){
		return tailleDrone;
	}

	public int getTailleBateau(){
		return tailleBateau;
	}

	public int getTailleNaufrage(){
		return tailleNaufrage;
	}



}
