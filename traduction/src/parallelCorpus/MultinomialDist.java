package parallelCorpus;

import java.util.Random;

public class MultinomialDist {

	// petite constante pour la probabilité
	private final double a = 0.05;

	private Cooccurence co;
	private int src_idx;
	private String src_word;
	private String align_word;
	private BiPhrase bi;
	private Alignement al;
	private double[] distribution;

	// On n'utilise plus la classe Random, Math.random est suffisant
	// private Random rd;

	// affiche le tableau de probabilité
	private void print() {
		System.out.println("tableau de probas");
		for (double prob : distribution) {
			System.out.print(prob + " ");
		}
		System.out.println();
	}

	// effectue le tirage multinomial et renvoie l'indice correspondant
	private int sample() {
		// double target = rd.nextDouble();
		double target = Math.random();
		double cumProb = 0;
		for (int i = 0; i < distribution.length; i++) {
			cumProb += distribution[i];
			if (target < cumProb) {
				return i;
			}
		}
		return (distribution.length - 1); // this shouldn't ever be executed
	}

	// permet de normaliser le tableau de probabilités pour que la somme soit
	// égale é 1
	private void normalize() {
		double sum = 0;
		for (int i = 0; i < distribution.length; i++) {
			sum += distribution[i];
		}
		// System.out.println("======================");
		for (int i = 0; i < distribution.length; i++) {
			distribution[i] = distribution[i] / sum;
			// System.out.print(distribution[i]+" ");
		}
	}

	// retire le lien entre le mot source et le mot de destination.
	// le compte est retiré
	private void removeLink() {
		Compte acc = co.getCompte(align_word);
		acc.removeWord(src_word);
	}

	// initialise le tableau de probabilités
	private void constructDist() {
		int taille = bi.getArraydest().length + 1;
		distribution = new double[taille];
		for (int i = 0; i < taille; i++) {

			Compte compte;
			if (i == taille - 1)
				compte = co.getCompte(Cooccurence.NULL);
			else
				compte = co.getCompte(bi.getArraydest()[i]);

			// nombre de mots sources différents alignées avec le mot
			// destination
			int V = compte.getCompte_().size();

			int freq_src_dest = 0, freq_dest = 0;

			// récupére la fréquence du mot cible aligné avec le mot source
			if (compte.getCompte_().containsKey(src_word)) {
				freq_src_dest = compte.getCompte_().get(src_word);
			} else
				freq_src_dest = 0;

			// récupére la fréquence
			freq_dest = compte.getNcount();
			if (a * V == 0 && freq_dest == 0)
				distribution[i] = 1;
			else
				distribution[i] = (freq_src_dest + a) / (freq_dest + a * V);

		}
	}

	// méthode à appeler pour effectuer le tirage multinomial sur le mot source
	// par rapport éà la phrase cible
	public void compute() {
		removeLink();
		constructDist();
		normalize();
		int new_index = sample();
		// modifie le lien d'alignement suite au tirage et incrémente la
		// fréquence d'alignement avec
		// le nouveau mot cible

		String dest_word;
		if (new_index == distribution.length - 1) {
			dest_word = Cooccurence.NULL;
			al.getAlign()[src_idx] = -1;
		} else {
			dest_word = bi.getArraydest()[new_index];
			al.getAlign()[src_idx] = new_index;
		}
		co.getCompte(dest_word).addWord(src_word);
	}

	// constructeur qui permet d'effectuer un tirage multinomial entre un mot
	// source et une liste de mots cible
	public MultinomialDist(int src_idx, BiPhrase bi, Alignement al,
			Cooccurence co) {
		this.co = co;
		this.src_idx = src_idx;
		this.src_word = bi.getArraysrc()[src_idx];
		this.bi = bi;
		this.al = al;
		int alignement = al.getAlign()[src_idx];
		this.align_word = (alignement == -1) ? Cooccurence.NULL : bi
				.getArraydest()[alignement];
	}

}
