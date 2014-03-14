package parallelCorpus;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		BiCorpus bi = new BiCorpus(src, dest);
		bi.parseFile();
		Accumulation acc = new Accumulation(bi,100,50);
		acc.compute();
	}

}
