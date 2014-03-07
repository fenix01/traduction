package parallelCorpus;

import java.util.HashMap;

public class Cooccurence {
	
	public static final String NULL = "NULL";
	
	/*
	 * a[i] => il y a un lien d'alignement entre le mot source[i] et
	 * cible[a[i]]
	 * 
	 * Cooccurence cible,source: Compte c = cooc.get[cible] int cpt =
	 * c.get[source]
	 */
	//parcours le corpus de phrases et génère un alignement aléatoire
	
	private HashMap<String, Compte> cooccurence_;
	
	public Cooccurence(){
		cooccurence_ = new HashMap<String, Compte>();
	}
	
	public Compte addCompte(String word){
		Compte c;
		if (!cooccurence_.containsKey(word)){
			c = new Compte();
			cooccurence_.put(word, c);			
		}
		else {
			c = cooccurence_.get(word);
		}
		return c;	
	}
	
	public Compte getCompte(String word){
		return addCompte(word);
	}
	
	//permet de remettre à zéro la structure cooccurence
	public void reset(){
		cooccurence_.clear();
	}
}
