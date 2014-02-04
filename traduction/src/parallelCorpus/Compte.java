package parallelCorpus;

import java.util.HashMap;

public class Compte {
	private int ncount = 0;
	private HashMap<String, Integer> compte_;
	
	public void updateWord(String word,int univers){
		if (compte_.containsKey(word)) {
			
		}
	}
	
	public HashMap<String, Integer> getCompte_() {
		return compte_;
	}

	public Compte(){
		compte_ = new HashMap<String, Integer>();
	}
	
	public void addWord(String word){
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			compte_.put(word, old+1);
			ncount += old+1;
		}
		else {
			compte_.put(word, 1);
			ncount = 1;
		}
	}
	
	public void removeWord(String word){
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			compte_.put(word, old-1);
			ncount += old-1;
		}
	}
}
