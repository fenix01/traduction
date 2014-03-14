package parallelCorpus;

import java.util.Random;

public class Alignement implements Cloneable {
	
	private static Random rd = new Random();
	private int lengthCibl;
	private int[] align;// représente le tableau d'alignement. En indice est
						// représenté les mots sources et en valeur le lien
						// d'alignement avec le mot cible

	// Constructeur qui prend un tableau de mot source et de mot cible en entrée
	// et génère aléatoirement les liens d'alignements
	Alignement(String[] src, String[] dest) {
		this.align = new int[src.length];
		this.lengthCibl = dest.length;
		randomAlignment();
	}
	
	// Constructeur qui prend la taille de la source et de la cible en entrée
	Alignement(int src, int dest) {
		this.align = new int[src];
		this.lengthCibl = dest;
	}	
	
	public void print(){
		for (int al : align){
			System.out.print(al+" ");
		}
	}

	public int[] getAlign() {
		return align;
	}
	
	public int getLengthCibl(){
		return lengthCibl;
	}
	
	//génère aléatoirement le tableau d'alignement
	public void randomAlignment(){
		for (int i = 0; i < align.length; i++) {
			int x = rd.nextInt(lengthCibl + 1) - 1;
			align[i] = x;
		}
	}
	
	public Alignement clone() throws CloneNotSupportedException {
		Alignement copy = (Alignement) super.clone();
		copy.align = new int[align.length];
		System.arraycopy(align, 0, copy.align, 0 , align.length);
		return copy;
		 }

}
