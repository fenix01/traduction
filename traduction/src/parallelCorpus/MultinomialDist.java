package parallelCorpus;

public class MultinomialDist {

	private boolean ibm2;	
	// petite constante pour la probabilité
	private final double a = 0.003;

	private Cooccurence co;
	private int src_idx;
	private int cib_idx;
	private String src_word;
	private String align_word;

	private String[] src;
	private String[] cib;
	
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
		int taille = cib.length + 1;
		distribution = new double[taille];
		for (int i = 0; i < taille; i++) {

			Compte compte;
			if (i == taille - 1)
				compte = co.getCompte(Cooccurence.NULL);
			else
				compte = co.getCompte(cib[i]);

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
			else if (!ibm2)
				distribution[i] = (freq_src_dest + a) / (freq_dest + a * V);
			else {
				//calcul de la distorsion, IBM 2
				double p_distorsion = 0;
				double p0 = (double)1 / (src.length + 1);
				if (i == taille - 1){
					p_distorsion = p0;
				}
				else {
					int moy = Math.round((src_idx+1)*cib.length/src.length);
					int idx = (i+1) - moy;
					idx = (idx > 3) ? 3 : (idx < -3) ? -3 : idx;
					idx += 3;//pour sélectionner une case entre 0 et 6
					p_distorsion = (Ibm2.distorsion[idx]+1)/(Ibm2.count_distorsion()+7);
				}
				distribution[i] = p_distorsion*(freq_src_dest + a) / (freq_dest + a * V);
			}
			
		}
	}

	// méthode à appeler pour effectuer le tirage multinomial sur le mot source
	// par rapport éà la phrase cible
	public void compute() {
		removeLink();
		constructDist();
		normalize();
		//print();
		int new_index = sample();
		// modifie le lien d'alignement suite au tirage et incrémente la
		// fréquence d'alignement avec
		// le nouveau mot cible
		
		//if (ibm2) genIbm2(new_index);

		String dest_word;
		if (new_index == distribution.length - 1) {
			dest_word = Cooccurence.NULL;
			al.getAlign()[src_idx] = -1;
		} else {
			dest_word = cib[new_index];
			al.getAlign()[src_idx] = new_index;
		}
		co.getCompte(dest_word).addWord(src_word);
	}

	// constructeur qui permet d'effectuer un tirage multinomial entre un mot
	// source et une liste de mots cible
	public MultinomialDist(int src_idx, String[] src,String[] cib, Alignement al,
			Cooccurence co, boolean ibm2) {
		this.ibm2 = ibm2;
		this.co = co;
		this.src = src;
		this.src_idx = src_idx;
		this.cib = cib;
		this.cib_idx = al.getAlign()[src_idx];
		this.al = al;
		int alignement = al.getAlign()[src_idx];
		this.src_word = src[src_idx];
		this.align_word = (alignement == -1) ? Cooccurence.NULL : cib[alignement];
	}

}
