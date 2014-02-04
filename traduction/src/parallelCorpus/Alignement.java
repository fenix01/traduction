package parallelCorpus;

import java.util.Random;

public class Alignement {
	
	private int[] align;
	
	Alignement(String[] src,String[] dest){
		this.align = new int[src.length];
		Random rd = new Random();
		for (int i=0;i<align.length;i++){
			int x = rd.nextInt(dest.length) - 1;
			align[i]=x;
		}
	}

	public int[] getAlign() {
		return align;
	}

}
