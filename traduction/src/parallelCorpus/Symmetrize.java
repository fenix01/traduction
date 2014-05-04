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
			ArrayList<Couple> symAl = grow(src_cib.get(i),
					cib_src.get(i));
			symAlignments.add(symAl);
		}
	}
	
	private boolean coupleExists(Couple cp,ArrayList<Couple> couples){
		boolean found = false;
		int i = 0;
		while (!found && i < couples.size()){
			Couple cur_cp = couples.get(i);
			if (cp.getSrc() == cur_cp.getSrc() && cp.getCib() == cur_cp.getCib())
				found = true;
			i++;
		}
		return found;
	}
	
	private boolean rowCovered(int row, int col, int[][] matrix){
		// on regarde sur la ligne
		boolean valid = true;
		int i = 0;
		while (valid && i < matrix.length) {
			if (i != row && matrix[i][col] > 0)
				valid = false;
			i++;
		}
		return valid;		
	}
	
	private boolean colCovered(int row,int col, int[][] matrix){
		// on regarde la colonne
		boolean valid = true;
		int i = 0;
		while (valid && i < matrix[row].length) {
			if (i != col && matrix[row][i] > 0)
				valid = false;
			i++;
		}
		return valid;
	}
	
	private ArrayList<Couple> union(Alignement src_cib,
			Alignement cib_src) {
		int length_src = src_cib.getAlign().length;
		int length_cib = cib_src.getAlign().length;
		ArrayList<Couple> union = new ArrayList<>();
		
		for (int i = 0; i < length_src ; i++){
				Couple couple = new Couple(i, src_cib.getAlign()[i]);	
				if (!coupleExists(couple, union))
					union.add(couple);
		}
		for (int j = 0; j < length_cib; j++) {
				Couple couple = new Couple(cib_src.getAlign()[j],j);
				if (!coupleExists(couple, union))
					union.add(couple);
		}
		return union;
	}
	
	private ArrayList<Couple> neighboor(){
		ArrayList<Couple> neighboor = new ArrayList<>();
		neighboor.add(new Couple(-1, 0));
		neighboor.add(new Couple(0, -1));
		neighboor.add(new Couple(1, 0));
		neighboor.add(new Couple(0, 1));
		neighboor.add(new Couple(-1, -1));
		neighboor.add(new Couple(-1, 1));
		neighboor.add(new Couple(1, -1));
		neighboor.add(new Couple(1, 1));
		return neighboor;
	}
	
	private ArrayList<Couple> grow(Alignement src_cib,
			Alignement cib_src) {
		int length_src = src_cib.getAlign().length;
		int length_cib = cib_src.getAlign().length;

		ArrayList<Couple> intersection = intersection(src_cib,cib_src);
		ArrayList<Couple> union = union(src_cib,cib_src);
		
		ArrayList<Couple> grow = new ArrayList<>();
		grow.addAll(intersection);

		int[][] matrix = new int[length_src][length_cib];
		
		for (int i = 0; i < intersection.size(); i++){
			Couple cp_int = intersection.get(i);
			matrix[cp_int.getSrc()][cp_int.getCib()] = 1;
		}
		
		boolean changed = true;
		while (changed){
			changed = false;
			for (int i = 0; i < length_src; i++) {
				for (int j = 0; j < length_cib; j++){
					Couple cp_ = new Couple(i, j);
					if (matrix[i][j] != 0){
						for (Couple cp : neighboor()){
							int v_row = cp_.getSrc()+cp.getSrc();
							int v_col = cp_.getCib()+cp.getCib();
							
							//on vérifie que le point existe
							if (v_row >= 0 && v_row < length_src && v_col >= 0 && v_col < length_cib){
								int val = matrix[v_row][v_col];
								Couple cp_union = new Couple(v_row, v_col);
								
								if (val == 0 && coupleExists(cp_union, union)){
									if (!rowCovered(v_row, v_col, matrix) || (!colCovered(v_row, v_col, matrix))){
										matrix[v_row][v_col] = 1;
										changed = true;
										grow.add(cp_union);	
									}
									
								}
							}
						}
					}
				}
			}
		}	
		return grow;	
	}

	private ArrayList<Couple> intersection(Alignement src_cib,
			Alignement cib_src) {

		int length_src = src_cib.getAlign().length;

		ArrayList<Couple> intersect = new ArrayList<>();

		for (int i = 0; i < length_src; i++) {

			int cib = src_cib.getAlign()[i];

			if (cib != -1 && cib_src.getAlign()[cib] == i) {
				// intersection, on sauvegarde le lien
				Couple couple = new Couple(i, cib);
				intersect.add(couple);
			}
		}
		return intersect;

	}

	private void print(int[][] tab) {
		for (int[] line : tab) {
			Arrays.toString(line);
		}
		System.out.println("###################");

	}

}
