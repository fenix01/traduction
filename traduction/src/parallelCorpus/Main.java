package parallelCorpus;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		BiCorpus bi_fr_en = new BiCorpus(src, dest);
		BiCorpus bi_en_fr = new BiCorpus(dest, src);
		bi_fr_en.parseFile();
		bi_en_fr.parseFile();
		long start = System.currentTimeMillis();
		Accumulation acc_fr_en = new Accumulation(bi_fr_en,70,50);
		Accumulation acc_en_fr = new Accumulation(bi_en_fr,70,50);
		acc_fr_en.compute();
		acc_en_fr.compute();
		Symmetrize sym= new Symmetrize(acc_fr_en.getAccAlignments(), acc_en_fr.getAccAlignments());
		sym.compute();
		EvalAlignement eval = new EvalAlignement();
		eval.writeSym(sym.getSymAlignments(), "my_alignments2.txt");
		long end = System.currentTimeMillis();
		float time = ((float) (end-start)) / 1000f;
		System.out.println(time);
	}

}
