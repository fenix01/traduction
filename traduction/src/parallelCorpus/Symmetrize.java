package parallelCorpus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Symmetrize {

	private ArrayList<Alignement> src_cib;
	private ArrayList<Alignement> cib_src;
	private ArrayList<ArrayList<Couple>> symAlignments;

	public ArrayList<ArrayList<Couple>> getSymAlignments() {
		return symAlignments;
	}

	public Symmetrize(ArrayList<Alignement> src_cib,
			ArrayList<Alignement> cib_src) {
		this.src_cib = src_cib;
		this.cib_src = cib_src;
		this.symAlignments = new ArrayList<>();
	}

	public void compute() {
		for (int i = 0; i < src_cib.size(); i++) {
			ArrayList<Couple> symAl = intersection(src_cib.get(i),
					cib_src.get(i));
			symAlignments.add(symAl);
		}
	}

	private ArrayList<Couple> refined(Alignement src_cib, Alignement cib_src,
			int[][] matrix) {
		ArrayList<Couple> refined = new ArrayList<>();
		for (int i = 0; i < src_cib.getAlign().length; i++) {
			int j = src_cib.getAlign()[i];
			if (j != -1)
			{
				// on regarde sur la ligne
				boolean valid = true;
				for (int k = 0; k < matrix.length; k++) {
					if (i != k && matrix[k][j] > 0) {
						valid = false;
						break;
					}
				}
				// on regarde la colonne
				for (int k = 0; k < matrix[i].length; k++) {
					if (j != k && matrix[i][k] > 0) {
						valid = false;
						break;
					}
				}
				// si aucun alignement sur la ligne et la colonne hormis celui-ci,
				// on l'ajoute
				if (valid) {
					Couple couple = new Couple(i, j);
					refined.add(couple);
				}
				matrix[i][j] = 1;
			}

		}
		for (int j = 0; j < cib_src.getAlign().length; j++) {
			int i = cib_src.getAlign()[j];
			
			if (i != -1){
				// on regarde sur la ligne
				boolean valid = true;
				for (int k = 0; k < matrix.length; k++) {
					if (i != k && matrix[k][j] > 0) {
						valid = false;
						break;
					}
				}
				// on regarde la colonne
				for (int k = 0; k < matrix[i].length; k++) {
					if (j != k && matrix[i][k] > 0) {
						valid = false;
						break;
					}
				}
				// si aucun alignement sur la ligne et la colonne hormis celui-ci,
				// on l'ajoute
				if (valid) {
					Couple couple = new Couple(i, j);
					refined.add(couple);
				}
				matrix[i][j] = 1;
			}
		}
		return refined;

	}

	private ArrayList<Couple> intersection(Alignement src_cib,
			Alignement cib_src) {

		int length_src = src_cib.getAlign().length;
		int length_cib = cib_src.getAlign().length;

		ArrayList<Couple> intersect = new ArrayList<>();

		int[][] matrix = new int[length_src][length_cib];

		for (int i = 0; i < matrix.length; i++) {

			int cib = src_cib.getAlign()[i];

			// if (cib != -1 && cib_src.getAlign()[cib] != i)
			// {
			// matrix[i][cib] = 1;
			// }

			if (cib != -1 && cib_src.getAlign()[cib] == i) {
				// intersection, on sauvegarde le lien
				Couple couple = new Couple(i, cib);
				intersect.add(couple);

				matrix[i][cib] = 2;
			}
		}
//		ArrayList<Couple> refinedR1 = refined(src_cib, cib_src, matrix);
//		intersect.addAll(refinedR1);
		// for (int j = 0; j < length_cib ; j++){
		// int cib = cib_src.getAlign()[j];
		// if (cib != -1 && src_cib.getAlign()[cib] != j)
		// matrix[cib][j] = 1;
		// }
		return intersect;

	}

	private void print(int[][] tab) {
		for (int[] line : tab) {
			Arrays.toString(line);
		}
		System.out.println("###################");

	}

}
