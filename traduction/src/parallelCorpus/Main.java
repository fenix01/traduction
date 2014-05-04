package parallelCorpus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Main {
	
	private final static int lines = 10000;

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String src = "./train.fr";
		String dest = "./train.en";

		BiCorpus bi_fr_en = new BiCorpus(src, dest,lines);
		BiCorpus bi_en_fr = new BiCorpus(dest, src, lines);
		bi_fr_en.parseFile();
		bi_en_fr.parseFile();
		long start = System.currentTimeMillis();
		
		//ibm1
		Accumulation acc_fr_en = new Accumulation(bi_fr_en,150,50,true);
		Accumulation acc_en_fr = new Accumulation(bi_en_fr,150,50,true);
		acc_fr_en.compute();
		acc_en_fr.compute();
			
		bi_en_fr.setAlignements(acc_en_fr.getAccAlignments());	
		
		bi_fr_en.setAlignements(acc_fr_en.getAccAlignments());
		
		Symmetrize sym= new Symmetrize(acc_fr_en.getAccAlignments(), acc_en_fr.getAccAlignments());
		sym.compute();
		//EvalAlignement eval = new EvalAlignement(acc_fr_en.getAccAlignments(),"my_alignments2.txt");
		EvalAlignement eval = new EvalAlignement();
		eval.writeSym(sym.getSymAlignments(), "my_alignments2.txt");
		long end = System.currentTimeMillis();
		float time = ((float) (end-start)) / 1000f;
		System.out.println(time);
		
		try {
			Process process = Runtime
					.getRuntime()
					.exec("perl aa_eval_align.pl reference_alignments.txt my_alignments2.txt");
			process.waitFor();
			if (process.exitValue() == 0) {
				try {
					BufferedReader in = new BufferedReader(
							new InputStreamReader(process.getInputStream()));
					String line = null;
					while ((line = in.readLine()) != null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				System.out.println("Command Failure");
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e.toString());
		}
		System.out.println(Arrays.toString(Ibm2.distorsion));
		System.out.println(Ibm2.count_distorsion());
	}

}
