package parallelCorpus;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EvalAlignement {

	// constructeur qui prend un BiCorpus en entrée et le nom d'un fichier.
	// Permet de générer le fichier d'alignement pour le script perl calculant
	// l'AER
	// NB: les mots sources alignés avec -1 ne sont pas pris en compte dans le
	// fichier
	public EvalAlignement(BiCorpus bi, String filename) {
		BufferedWriter alignFile = null;
		try {
			alignFile = new BufferedWriter(new FileWriter(filename));
			for (int i = 0; i < 447; i++) {
				Alignement al = bi.getAlignements().get(i);
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
