package parallelCorpus;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		BiCorpus bi = new BiCorpus(src, dest);
		bi.parseFile();
		Accumulation acc = new Accumulation(bi,10,20);
		acc.compute();
		
//		bi.fillCooccurence();
//		MultinomialCorpus mtcorp = new MultinomialCorpus(bi, 20);
//		mtcorp.compute();
//		new EvalAlignement(bi.getAlignements(), "./my_alignments2.txt");
//		MultinomialCorpus mtcorp2 = new MultinomialCorpus(bi, 20);
//		mtcorp2.compute();
//		new EvalAlignement(bi.getAlignements(), "./my_alignments3.txt");
//		
	}

}
