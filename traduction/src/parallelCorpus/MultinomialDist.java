package parallelCorpus;

import java.util.Random;

public class MultinomialDist {

	// petite constante pour la probabilit�
	private final double a = 0.05;

	private Cooccurence co;
	private int src_idx;
	private String src_word;
	private String align_word;
	private BiPhrase bi;
	private Alignement al;
	private double[] distribution;

	//On n'utilise plus la classe Random, Math.random est suffisant
	//private Random rd;

	// affiche le tableau de probabilit�
	private void print() {
		System.out.println("tableau de probas");
		for (double prob : distribution) {
			System.out.print(prob + " ");
		}
		System.out.println();
	}
	
	
	// effectue le tirage multinomial et renvoie l'indice correspondant
	private int sample() {
		//double target = rd.nextDouble();
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

	// permet de normaliser le tableau de probabilit�s pour que la somme soit
	// �gale � 1
	private void normalize() {
		double sum = 0;
		for (int i = 0; i < distribution.length; i++) {
			sum += distribution[i];
		}
		for (int i = 0; i < distribution.length; i++) {
			distribution[i] = distribution[i] / sum;
		}
	}

//	// retire le lien entre le mot source et le mot de destination.
//	// la fr�quence du compte du mot align� au mot source est d�cr�ment� de 1
//	private void removeLink() {
//		Compte acc = co.getCompte(align_word);
//		acc.removeWord(src_word);
//	}

	// ajoute un lien entre le mot source et les mots destinations
	// la fr�quence des comptes des mots destinations associ�s au mot source est
	// incr�ment� de 1
	private void addLinks() {
		for (String dest_word : bi.getArraydest()) {
			Compte acc = co.getCompte(dest_word);
			if (!align_word.equals(dest_word)) {
				acc.addWord(src_word);
			}
		}
	}

//	// trie le tableau de probabilit� par ordre d�croissant
//	// n�cessite d'avoir trier auparavant le tableau par ordre croissant
//	// NB: n'am�liore pas du tout le r�sultat de l'AER
//	private void descendSort(double[] tab) {
//		for (int i = 0; i < tab.length / 2; i++) {
//			double temp = tab[i];
//			tab[i] = tab[tab.length - 1 - i];
//			tab[tab.length - 1 - i] = temp;
//		}
//	}

	// initialise le tableau de probabilit�s
	private void constructDist() {
		distribution = new double[bi.getArraydest().length];
		for (int i = 0; i < bi.getArraydest().length; i++) {

			// nombre de mots sources diff�rents align�es avec le mot
			// destination
			int V = co.getCompte(bi.getArraydest()[i]).getCompte_().size();

			int freq_src_dest = 0, freq_dest = 0;

			// r�cup�re la fr�quence du mot cible align� avec le mot source
			if (co.getCompte(bi.getArraydest()[i]).getCompte_()
					.containsKey(src_word)) {
				freq_src_dest = co.getCompte(bi.getArraydest()[i]).getCompte_()
						.get(src_word);
			} else
				freq_src_dest = 0;

			// r�cup�re la fr�quence
			freq_dest = co.getCompte(bi.getArraydest()[i]).getNcount();
			
			distribution[i] = (freq_src_dest + a) / (freq_dest + a * V);
			
		}
	}

	//m�thode � appeler pour effectuer le tirage multinomial sur le mot source
	//par rapport � la phrase cible
	public void compute() {
		//removeLink();
		addLinks();
		constructDist();
		normalize();
		//Arrays.sort(distribution);
		// descendSort(distribution);
		int new_index = sample();
		//modifie le lien d'alignement suite au tirage et incr�mente la fr�quence d'alignement avec
		//le nouveau mot cible
		String dest_word = (new_index == -1) ? Cooccurence.NULL : bi
				.getArraydest()[new_index];
		al.getAlign()[src_idx] = new_index;
		co.getCompte(dest_word).addWord(src_word);	
	}

	// constructeur qui permet d'effectuer un tirage multinomial entre un mot
	// source et une liste de mots cible
	public MultinomialDist(int src_idx, BiPhrase bi, Alignement al,
			Cooccurence co) {
//		this.rd = new Random(System.currentTimeMillis());
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
