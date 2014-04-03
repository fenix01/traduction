package parallelCorpus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class EvalAlignement {
	
	public void writeSym(ArrayList<ArrayList<Couple>> symAlignments, String filename){
		BufferedWriter alignFile = null;
		try {
			alignFile = new BufferedWriter(new FileWriter(filename));
		for ( int i = 0; i < symAlignments.size() ; i++){
			ArrayList<Couple> symAl = symAlignments.get(i);
			for (Couple cp : symAl){
				if (cp.getCib() != -1) {
					alignFile.write((i + 1) + " " + (cp.getSrc()+1) + " "
							+ (cp.getCib()+1));
					alignFile.newLine();
				}
			}
		}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				alignFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public EvalAlignement(){
		
	}
	
	// constructeur qui prend un BiCorpus en entr�e et le nom d'un fichier.
	// Permet de g�n�rer le fichier d'alignement pour le script perl calculant
	// l'AER
	// NB: les mots sources align�s avec -1 ne sont pas pris en compte dans le
	// fichier
	public EvalAlignement(ArrayList<Alignement> struct_align, String filename) {
		BufferedWriter alignFile = null;
		try {
			alignFile = new BufferedWriter(new FileWriter(filename));
			for (int i = 0; i < struct_align.size(); i++) {
				Alignement al = struct_align.get(i);
				for (int j = 0; j < al.getAlign().length; j++) {
					if (al.getAlign()[j] != -1) {
						alignFile.write((i + 1) + " " + (j+1) + " "
								+ (al.getAlign()[j]+1));
						alignFile.newLine();
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				alignFile.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
