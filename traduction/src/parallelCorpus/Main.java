package parallelCorpus;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		BiCorpus bi = new BiCorpus(src, dest);
		bi.parseFile();
		
		Accumulation acc = new Accumulation(bi, 1,10);
		acc.compute();
//		
//		//permet de générer le fichier d'alignement suite au tirage aléatoire
//		EvalAlignement eval = new EvalAlignement(bi, "./alea_align.txt");
//
//		//génère 10 tirages multinomiaux sur le corpus
//		MultinomialCorpus mtcorp = new MultinomialCorpus(bi, 10);
//		mtcorp.compute();
		//permet de générer le fichier d'alignement suite aux tirages multinomiaux

//		//Exemple : affiche le compte du mot cible teacher
//		Compte the2 = bi.getCooccurence().getCompte("teacher");
//		for (Map.Entry<String, Integer> el : the2.getCompte_().entrySet()) {
//			System.out.println(el.getKey() + " : " + el.getValue());
//		}
//		
//		Runtime rt = Runtime.getRuntime();
//		try {
//			Process pr = rt.exec("perl ./aa_eval_align.pl ./reference_alignments.txt ./my_alignments.txt");
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}

}
