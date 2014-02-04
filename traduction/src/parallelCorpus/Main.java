package parallelCorpus;

import java.util.Map;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.en";
		String dest = "./train.fr";

		
		
		/* 
		 *  a[i] => il y a un lien d'alignement entre le mot source[i] et cible[a[i]] 
		 * 
		 * Cooccurence cible,source:
		 * 	Compte c = cooc.get[cible]
		 * 	int cpt = c.get[source]
		 * 
		 * */
		BiCorpus bi = new BiCorpus(src,dest);
		bi.parseFile();
		for (int i = 0; i<bi.getCorpus().size();i++){
			BiPhrase bp = bi.getCorpus().get(i);
			for (int j=0;j<bi.getAlignements().get(i).getAlign().length;j++){
				String src_word = bp.getArraysrc()[j];
				String dest_word = Cooccurence.NULL;
				int dest_idx = bi.getAlignements().get(i).getAlign()[j];
				if (dest_idx > -1){
					dest_word = bp.getArraydest()[dest_idx];
				}
				Compte c = bi.getCooccurence().addCompte(dest_word);
				c.addWord(src_word);
			}
		}
		MultinomialCorpus mtcorp = new MultinomialCorpus(bi, 10);
		mtcorp.compute();
		
//		Compte the = bi.getCooccurence().getCompte("le");
//		for (Map.Entry<String,Integer> el : the.getCompte_().entrySet()){
//			System.out.println(el.getKey()+" : "+el.getValue());
//		}
//				for (Alignement al : bi.getAlignements()){
//					for (int i=0;i<al.getAlign().length;i++){
//						System.out.print(al.getAlign()[i]+" ");;
//					}
//					System.out.println("");
//				}

	}

}
