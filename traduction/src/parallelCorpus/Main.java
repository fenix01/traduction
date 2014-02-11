package parallelCorpus;

import java.io.IOException;
import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		/*
		 * a[i] => il y a un lien d'alignement entre le mot source[i] et
		 * cible[a[i]]
		 * 
		 * Cooccurence cible,source: Compte c = cooc.get[cible] int cpt =
		 * c.get[source]
		 */
		//parcours le corpus de phrases et génère un alignement aléatoire
		BiCorpus bi = new BiCorpus(src, dest);
		bi.parseFile();
		for (int i = 0; i < bi.getCorpus().size(); i++) {
			BiPhrase bp = bi.getCorpus().get(i);

			for (int j = 0; j < bi.getAlignements().get(i).getAlign().length; j++) {

				String src_word = bp.getArraysrc()[j];
				String dest_word = Cooccurence.NULL;

				int dest_idx = bi.getAlignements().get(i).getAlign()[j];
				if (dest_idx > -1) {
					dest_word = bp.getArraydest()[dest_idx];
				}
				Compte c = bi.getCooccurence().addCompte(dest_word);
				c.addWord(src_word);
			}
		}
		//permet de générer le fichier d'alignement suite au tirage aléatoire
		EvalAlignement eval = new EvalAlignement(bi, "./alea_align.txt");

		//génère 10 tirages multinomiaux sur le corpus
		MultinomialCorpus mtcorp = new MultinomialCorpus(bi, 10);
		mtcorp.compute();
		//permet de générer le fichier d'alignement suite aux tirages multinomiaux
		EvalAlignement eval2 = new EvalAlignement(bi, "./my_alignments.txt");

		//Exemple : affiche le compte du mot cible teacher
		Compte the2 = bi.getCooccurence().getCompte("teacher");
		for (Map.Entry<String, Integer> el : the2.getCompte_().entrySet()) {
			System.out.println(el.getKey() + " : " + el.getValue());
		}
		
		Runtime rt = Runtime.getRuntime();
		try {
			Process pr = rt.exec("perl ./aa_eval_align.pl ./reference_alignments.txt ./my_alignments.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
