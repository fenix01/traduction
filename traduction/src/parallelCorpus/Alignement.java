package parallelCorpus;

import java.util.Random;

public class Alignement {

	private int[] align;// réprésente le tableau d'alignement. En indice est
						// représenté les mots sources et en valeur le lien
						// d'alignement avec le mot cible

	// Constructeur qui prend un tableau de mot source et de mot cible en entrée
	// et génère aléatoirement les liens d'alignements
	Alignement(String[] src, String[] dest) {
		this.align = new int[src.length];
		Random rd = new Random();
		for (int i = 0; i < align.length; i++) {
			int x = rd.nextInt(dest.length) - 1;
			align[i] = x;
		}
	}

	public int[] getAlign() {
		return align;
	}

}
