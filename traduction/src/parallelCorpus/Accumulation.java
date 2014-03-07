package parallelCorpus;

import java.util.ArrayList;

public class Accumulation {

	private BiCorpus bi;
	private int accu;
	private int multinomial;
	private ArrayList<ArrayList<Alignement>> acc;

	// permet d'accumuler x structures d'alignements
	private void accAlignments() {
		for (int i = 0; i < accu; i++) {
			copyAlignements();
		}
	}

	private void copyAlignements() {
		// réinitialise le corpus pour repartir chaque fois sur une base
		// identique
		bi.reset();

		// remplit la structure cooccurence
		bi.fillCooccurence();
		// génère 10 tirages multinomiaux sur le corpus
		MultinomialCorpus mtcorp = new MultinomialCorpus(bi, multinomial);
		mtcorp.compute();
		ArrayList<Alignement> copy = new ArrayList<Alignement>();
		for (Alignement al : bi.getAlignements()) {
			try {
				copy.add((Alignement) al.clone());
			} catch (CloneNotSupportedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		acc.add(copy);
	}

	// max d'une array
	private static int findMax(int[] array) {
		int indexOfMax = 0;
		for (int i = 1; i < array.length; i++) {
			if (array[i] > array[indexOfMax]) {
				indexOfMax = i;
			}
		}
		return indexOfMax;
	}

	private void BurnIn() {
		ArrayList<Alignement> burnedAlign = new ArrayList<>();
		// parcours tous les alignements
		for (int i = 0; i < acc.get(0).size(); i++) {
			// alignement courant
			Alignement al = acc.get(0).get(i);

			// alignement brûlé
			Alignement burnedAl = new Alignement(al.getAlign().length,
					al.getCible());
			burnedAlign.add(burnedAl);

			// parcours tous les mots sources de l'alignement courant
			for (int j = 0; j < al.getAlign().length; j++) {
				// les indices représentent les mots cibles
				// la valeur sera représentée par la fréquence d'apparition
				// la dernière case du tableau correspond au mot nul -1
				int[] compte = new int[al.getCible() + 1];
				// parcours l'ensemble des structures d'alignements pour
				// l'alignement correspondant
				// au mot source correspondant.
				// Permet de récupérer tous les mots cibles auquels sont alignés
				// le mot source
				for (ArrayList<Alignement> struct_align : acc) {
					int cibl = struct_align.get(i).getAlign()[j];
					if (cibl == -1)
						compte[al.getCible()] += 1;
					else
						compte[cibl] += 1;
				}
				// on récupère la valeur max du tableau
				// = le mot cible le plus fréquent avec le mot source
				int cibl = findMax(compte);
				if (cibl == al.getCible())
					burnedAl.getAlign()[j] = -1;
				else
					burnedAl.getAlign()[j] = cibl;
			}
		}
		if (burnedAlign.size() == acc.get(0).size())
			System.out.println("ok");
		EvalAlignement eval2 = new EvalAlignement(burnedAlign, "./my_alignments.txt");
	}
	
	public void compute(){
		accAlignments();
		BurnIn();
	}

	// constructeur qui prend en entrée un bicorpus et le nombre d'accumulation
	// à effectuer
	Accumulation(BiCorpus bi, int accu, int multinomial) {
		this.bi = bi;
		this.accu = accu;
		this.multinomial = multinomial;
		this.acc = new ArrayList<>(accu);
	}
}
