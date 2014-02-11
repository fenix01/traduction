package parallelCorpus;

public class MultinomialCorpus {
	
	private BiCorpus bp;
	private int computetime;
	
	//méthode à appeler pour effectue les tirages multinomiaux sur le corpus
	public void compute(){
		for (int i=0;i<computetime;i++){
			compute2();
		}
	}
	
	//permet d'effectuer x tirage sur le corpus
	//parcours l'ensemble des biphrases du corpus et pour que mot source effectue un tirage multinomial
	//en fonction de la phrase cible
	private void compute2(){
		for (int i=0 ; i < bp.getCorpus().size() ; i ++){
			BiPhrase bph = bp.getCorpus().get(i);
			Alignement al = bp.getAlignements().get(i);
			for ( int j = 0 ; j<bph.getArraysrc().length ; j++){
				MultinomialDist md = new MultinomialDist(j, bph, al , bp.getCooccurence());
				md.compute();
			}
		}
	}
	
	//constructeur qui prend en paramètre un BiCorpus et le nombre de tirage multinomial à effectuer sur le corpus
	public MultinomialCorpus(BiCorpus bp, int computetime){
		this.bp = bp;
		this.computetime = computetime;
	}

}
