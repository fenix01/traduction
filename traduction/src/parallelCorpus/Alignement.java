package parallelCorpus;

import java.util.Random;

public class Alignement implements Cloneable {

	private int cible;
	private int[] align;// réprésente le tableau d'alignement. En indice est
						// représenté les mots sources et en valeur le lien
						// d'alignement avec le mot cible

	// Constructeur qui prend un tableau de mot source et de mot cible en entrée
	// et génère aléatoirement les liens d'alignements
	Alignement(String[] src, String[] dest) {
		this.align = new int[src.length];
		this.cible = dest.length;
	}
	
	// Constructeur qui prend la taille de la source et de la cible en entrée
	Alignement(int src, int dest) {
		this.align = new int[src];
		this.cible = dest;
	}	

	public int[] getAlign() {
		return align;
	}
	
	public int getCible(){
		return cible;
	}
	
	//effectue un tirage aléatoire sur le tableau d'alignement
	public void randomAlignment(){
		Random rd = new Random();
		for (int i = 0; i < align.length; i++) {
			int x = rd.nextInt(cible) - 1;
			align[i] = x;
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		 return super.clone();
		 }

}
