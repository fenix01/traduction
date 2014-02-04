package parallelCorpus;

public class MultinomialDist {
	
	//petite constante pour la probabilité
	private final double a = 0.01;
	
	private Cooccurence co;
	private String src_word;
	private String align_word;
	private String[] dest_words;
	private double[] distribution;
	
	private int sample(){
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
	
	//permet de normaliser le tableau de probabilités pour que la somme soit égale à 1
	private void normalize(){
		double sum = 0;
		for (int i = 0; i<distribution.length; i++){
			sum+=distribution[i];
		}
		for (int i = 0; i<distribution.length; i++){
			distribution[i] = distribution[i]/sum;
		}
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
		for (String dest_word : dest_words){
			Compte acc = co.getCompte(dest_word);
			if (!align_word.equals(dest_word)){
				acc.addWord(src_word);
			}
		}
	}
	
	//initialise le tableau de probabilités
	private void constructDist(){
		distribution = new double[dest_words.length+1];
		for ( int i = 0; i<dest_words.length ; i++){
			
			// nombre de mots sources différents alignées avec le mot destination
			System.out.println(dest_words[i]);
			int V = co.getCompte(dest_words[i]).getCompte_().size();
			
			int freq_src_dest = 0, freq_dest = 0;
			
			if (co.getCompte(dest_words[i]).getCompte_().containsKey(src_word)){
				freq_src_dest = co.getCompte(dest_words[i]).getCompte_().get(src_word);
			}
			else freq_src_dest = 0;
			
			freq_dest = co.getCompte(dest_words[i]).getNcount();
			
			distribution[i] = (freq_src_dest+a) / (freq_dest+a*V);
		}
	}
	
	public void compute() {
//		removeLink();
//		addLinks();
		constructDist();
		normalize();
		int new_index = sample();
		String dest_word = dest_words[new_index];
		Compte acc = co.getCompte(dest_word);
		acc.addWord(src_word);
	}
	
	//constructeur qui permet d'effectuer un tirage multinomial entre un mot source et une liste de mots cible
	public MultinomialDist(String src_word,String[] dest_words,int alignement,Cooccurence co){
		this.co = co;
		this.src_word = src_word;
		this.dest_words = dest_words;
		this.align_word = (alignement == -1) ? Cooccurence.NULL: dest_words[alignement];
	}

}
