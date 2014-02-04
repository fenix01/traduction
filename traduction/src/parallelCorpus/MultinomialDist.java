package parallelCorpus;

public class MultinomialDist {
	
	private Cooccurence co;
	private String src_word;
	private String align_word;
	private String[] dest_words;
	private double[] distribution;
	
	public int sample(){
		double alea = Math.random();	//un nombre aleatoire entre 0 et 1
		double proba = 0;
		for(int i = 0; i < distribution.length; i++){
			proba += distribution[i];	//la somme des proba doit etre egale a 1
			if(alea < proba){
				return i;
			}
		}
		return -1;
	}
	
	// retire le lien entre le mot source et le mot de destination.
	// la fréquence du compte du mot aligné au mot source est décrémenté de 1
	private void removeLink(){
		Compte acc = co.getCompte(align_word);
		acc.removeWord(src_word);
	}
	
	// ajoute un lien entre le mot source et les mots destinations
	// la fréquence des comptes des mots destinations associés au mot source est incrémenté de 1
	private void addLinks(){
		Compte acc = co.getCompte(align_word);
		for (String src_word : acc.getCompte_().keySet()){
			if (!src_word.equals(this.src_word)){
				acc.removeWord(src_word);
			}
		}
	}
	
	private void constructDist(){
		distribution = new double[dest_words.length];
		for (String word : dest_words){
			int freq = 0;
			freq = co.getCompte(src_word).getCompte_().get(word);
		}
	}
	
	public MultinomialDist(String src_word,String[] dest_words,int alignement,Cooccurence co){
		this.co = co;
		this.src_word = src_word;
		this.dest_words = dest_words;
		this.align_word = dest_words[alignement];
	}

}
