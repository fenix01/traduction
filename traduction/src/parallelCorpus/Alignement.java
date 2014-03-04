package parallelCorpus;

import java.util.Random;

public class Alignement implements Cloneable {

	private int lengthCibl;
	private int[] align;// r�pr�sente le tableau d'alignement. En indice est
						// repr�sent� les mots sources et en valeur le lien
						// d'alignement avec le mot cible

	// Constructeur qui prend un tableau de mot source et de mot cible en entr�e
	// et g�n�re al�atoirement les liens d'alignements
	Alignement(String[] src, String[] dest) {
		this.align = new int[src.length];
		this.lengthCibl = dest.length;
	}
	
	// Constructeur qui prend la taille de la source et de la cible en entr�e
	Alignement(int src, int dest) {
		this.align = new int[src];
		this.lengthCibl = dest;
	}	

	public int[] getAlign() {
		return align;
	}
	
	public int getLengthCibl(){
		return lengthCibl;
	}
	
	//effectue un tirage al�atoire sur le tableau d'alignement
	public void randomAlignment(){
		Random rd = new Random();
		for (int i = 0; i < align.length; i++) {
			int x = rd.nextInt(lengthCibl) - 1;
			align[i] = x;
		}
	}
	
	public Object clone() throws CloneNotSupportedException {
		 return super.clone();
		 }

}
