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

	public Symmetrize(ArrayList<Alignement> src_cib, ArrayList<Alignement> cib_src){
		this.src_cib = src_cib;
		this.cib_src = cib_src;
		this.symAlignments = new ArrayList<>();
	}
	
	public void compute(){
		for (int i = 0; i < src_cib.size() ; i++){
			ArrayList<Couple> symAl = intersection(src_cib.get(i), cib_src.get(i));
			symAlignments.add(symAl);
		}
	}
	
	private ArrayList<Couple> intersection(Alignement src_cib, Alignement cib_src){
		
		int length_src = src_cib.getAlign().length;
		int length_cib = cib_src.getAlign().length;
		
		ArrayList<Couple> intersect = new ArrayList<>();
		
		int[][] matrix = new int[length_src][length_cib];
		
		for (int i = 0 ; i < matrix.length ; i++){
			
			int cib = src_cib.getAlign()[i];
			
			if (cib != -1 && cib_src.getAlign()[cib] != i)
			{
				matrix[i][cib] = 1;
			}
			
			if (cib != -1 && cib_src.getAlign()[cib] == i)
			{
				//intersection, on sauvegarde le lien
				Couple couple = new Couple(i, cib);
				intersect.add(couple);
				
				matrix[i][cib] = 2;
			}
		}
		for (int j = 0; j < length_cib ; j++){
			int cib = cib_src.getAlign()[j];
			if (cib != -1 && src_cib.getAlign()[cib] != j)
				matrix[cib][j] = 1;
		}
		return intersect;
		
	}
	
	private void print(int[][] tab){
		for (int[] line : tab){
			Arrays.toString(line);
		}
		System.out.println("###################");
		
	}

}
