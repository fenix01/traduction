package parallelCorpus;

import java.util.HashMap;

public class Cooccurence {
	
	public static final String NULL = "NULL";
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
		return cooccurence_.get(word);
	}
}
