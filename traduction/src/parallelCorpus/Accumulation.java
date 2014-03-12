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
		// g�n�re 10 tirages multinomiaux sur le corpus
		MultinomialCorpus mtcorp = new MultinomialCorpus(bi, 5);
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
	
	private void print(){
		for (int i = 0; i < acc.get(0).size(); i++) {
			// alignement courant
			Alignement al = acc.get(0).get(i);
			System.out.println("---------------------------------------");
			al.print();
			for (int j = 1; j < acc.size();j++){
				Alignement al2 = acc.get(j).get(i);
				if (al == al2) 
				{
					System.out.println("error");
					System.exit(11);
				}
				al2.print();
			}
		}
	}

	private void maximizeFrequencies() {
		ArrayList<Alignement> optimalAlign = new ArrayList<>();
		// parcours tous les alignements
		for (int i = 0; i < acc.get(0).size(); i++) {
			// alignement courant
			Alignement al = acc.get(0).get(i);

			// alignement br�l�
			Alignement optimalAl = new Alignement(al.getAlign().length,
					al.getLengthCibl());
			optimalAlign.add(optimalAl);

			// parcours tous les mots sources de l'alignement courant
			for (int j = 0; j < al.getAlign().length; j++) {
				// les indices repr�sentent les mots cibles
				// la valeur sera repr�sent�e par la fr�quence d'apparition
				// la derni�re case du tableau correspond au mot nul -1
				int[] compte = new int[al.getLengthCibl() + 1];
				// parcours l'ensemble des structures d'alignements pour
				// l'alignement correspondant
				// au mot source correspondant.
				// Permet de r�cup�rer tous les mots cibles auquels sont align�s
				// le mot source
				for (ArrayList<Alignement> struct_align : acc) {
					int cibl = struct_align.get(i).getAlign()[j];
					if (cibl == -1)
						compte[al.getLengthCibl()] += 1;
					else
						compte[cibl] += 1;
				}
				// on r�cup�re la valeur max du tableau
				// = le mot cible le plus fr�quent avec le mot source
				int cibl = findMax(compte);
				if (cibl == al.getLengthCibl())
					optimalAl.getAlign()[j] = -1;
				else
					optimalAl.getAlign()[j] = cibl;
			}
		}
		if (optimalAlign.size() == acc.get(0).size())
			System.out.println("ok");
		EvalAlignement eval2 = new EvalAlignement(optimalAlign, "./my_alignments2.txt");
	}
	
	public void compute(){
		burnIn();
		accAlignments();
		//print();
		maximizeFrequencies();
	}

	private void burnIn() {
		bi.reset();
		bi.fillCooccurence();
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

	// constructeur qui prend en entr�e un bicorpus et le nombre d'accumulation
	// � effectuer
	Accumulation(BiCorpus bi, int accu, int multinomial) {
		this.bi = bi;
		this.accu = accu;
		this.multinomial = multinomial;
		this.acc = new ArrayList<>(accu);
	}
}
