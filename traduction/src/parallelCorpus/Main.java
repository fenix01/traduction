package parallelCorpus;

import java.sql.Time;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		BiCorpus bi = new BiCorpus(src, dest);
		bi.parseFile();
		long start = System.currentTimeMillis();
		Accumulation acc = new Accumulation(bi,100,50);
		acc.compute();
		long end = System.currentTimeMillis();
		float time = ((float) (end-start)) / 1000f;
		System.out.println(time);
	}

}
