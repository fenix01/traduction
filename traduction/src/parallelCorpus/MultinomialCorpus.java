package parallelCorpus;

public class MultinomialCorpus {
	
	private BiCorpus bp;
	private int computetime;
	
	public void compute(){
		for (int i=0;i<computetime;i++){
			compute2();
		}
	}
	
	private void compute2(){
		for (int i=0 ; i < bp.getCorpus().size() ; i ++){
			BiPhrase bph = bp.getCorpus().get(i);
			Alignement al = bp.getAlignements().get(i);
			for ( int j = 0 ; j<bph.getArraysrc().length ; j++){
				bp.print(bph, al);
				MultinomialDist md = new MultinomialDist(bph.getArraysrc()[j], bph.getArraydest(), al.getAlign()[j] , bp.getCooccurence());
				md.compute();
			}
		}
	}
	
	public MultinomialCorpus(BiCorpus bp, int computetime){
		this.bp = bp;
		this.computetime = computetime;
	}

}
