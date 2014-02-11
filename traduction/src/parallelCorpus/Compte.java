package parallelCorpus;

import java.util.HashMap;

public class Compte {
	private int ncount = 0; // ncount représente la fréquence totale d'un mot
							// cible par rapports à tous les mots sources du
							// compte
	private HashMap<String, Integer> compte_;

	public HashMap<String, Integer> getCompte_() {
		return compte_;
	}

	public Compte() {
		compte_ = new HashMap<String, Integer>();
	}

	// permet de mettre à jour un mot source sans l'ajouter dans les comptes
	// s'il n'existe pas
	public void updateWord(String word) {
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			compte_.put(word, old + 1);
			ncount += 1;
		}
	}

	// ajoute un mot source dans les comptes et s'il existe déjà le met à jour
	// en incrémentant sa fréquence
	public void addWord(String word) {
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			compte_.put(word, old + 1);
			ncount += 1;
		} else {
			compte_.put(word, 1);
			ncount = 1;
		}
	}

	// public void addWord(String word,int val){
	// if (compte_.containsKey(word)) {
	// int old = compte_.get(word);
	// compte_.put(word, old+val);
	// ncount += val;
	// }
	// else {
	// compte_.put(word, val);
	// ncount = val;
	// }
	// }

	public int getNcount() {
		return ncount;
	}

	// supprime complètement un mot source des comptes
	public void deleteWord(String word) {
		if (compte_.containsKey(word)) {
			compte_.remove(word);
		}
	}

	// retire un mot source des comptes.
	// Si le mot source existe décrémente sa fréquence de 1
	public void removeWord(String word) {
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			old = (old <= 1) ? 0 : old - 1;
			compte_.put(word, old);
			ncount = (ncount <= 1) ? 0 : ncount - 1;
		}
	}

	// public void removeWord(String word, int val){
	// if (compte_.containsKey(word)) {
	// int old = compte_.get(word);
	// old = (old-val <= 0) ? 0 : old-val;
	// compte_.put(word, old);
	// ncount = (ncount-val <= 0) ? 0 : ncount-val;
	// }
	// }
}
