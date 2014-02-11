package parallelCorpus;

import java.util.HashMap;

public class Compte {
	private int ncount = 0; // ncount repr�sente la fr�quence totale d'un mot
							// cible par rapports � tous les mots sources du
							// compte
	private HashMap<String, Integer> compte_;

	public HashMap<String, Integer> getCompte_() {
		return compte_;
	}

	public Compte() {
		compte_ = new HashMap<String, Integer>();
	}

	// permet de mettre � jour un mot source sans l'ajouter dans les comptes
	// s'il n'existe pas
	public void updateWord(String word) {
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			compte_.put(word, old + 1);
			ncount += 1;
		}
	}

	// ajoute un mot source dans les comptes et s'il existe d�j� le met � jour
	// en incr�mentant sa fr�quence
	public void addWord(String word) {
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			compte_.put(word, old + 1);
			ncount += 1;
		} else {
			compte_.put(word, 1);
			ncount += 1;
		}
	}

	public int getNcount() {
		return ncount;
	}

	// supprime compl�tement un mot source des comptes
	public void deleteWord(String word) {
		if (compte_.containsKey(word)) {
			compte_.remove(word);
		}
	}

	// retire un mot source des comptes.
	// Si le mot source existe d�cr�mente sa fr�quence de 1
	public void removeWord(String word) {
		if (compte_.containsKey(word)) {
			int old = compte_.get(word);
			if (old == 1){
				deleteWord(word);
			}
			else {
				old = old - 1;
				compte_.put(word, old);
			}
			ncount = (ncount <= 1) ? 0 : ncount - 1;
		}
	}
}
