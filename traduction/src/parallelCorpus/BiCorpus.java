package parallelCorpus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BiCorpus {
	private String sourcefile;
	private String destfile;
	
	private ArrayList<BiPhrase> corpus;
	private ArrayList<Alignement> alignements;
	private Cooccurence cooccurence;
	
	public BiCorpus(String sourcefile, String destfile){
		this.sourcefile = sourcefile;
		this.destfile = destfile;
		this.corpus = new ArrayList<BiPhrase>();
		this.alignements = new ArrayList<Alignement>();
		this.cooccurence = new Cooccurence();
	}
	
	public ArrayList<Alignement> getAlignements() {
		return alignements;
	}

	public ArrayList<BiPhrase> getCorpus() {
		return corpus;
	}

	public void parseFile(){
		BufferedReader rdsource;
		BufferedReader rddest;
		int countsource = 0;
		int countdest = 0;
		try {
			rdsource = new BufferedReader(new FileReader(sourcefile));
			rddest = new BufferedReader(new FileReader(destfile));
			while (rdsource.readLine() != null) countsource++;
			rdsource.close();
			
			while (rddest.readLine() != null) countdest++;
			rddest.close();
			
			rdsource = new BufferedReader(new FileReader(sourcefile));
			rddest = new BufferedReader(new FileReader(destfile));
			
			if (countsource == countdest){
				//on ins�re les donn�es dans des arrayslists distincts
				String srcline,destline = "";
				while ((srcline = rdsource.readLine()) != null && (destline = rddest.readLine()) != null){
					BiPhrase bp = new BiPhrase(srcline.toLowerCase(),destline.toLowerCase());
					Alignement al = new Alignement(bp.getArraysrc(), bp.getArraydest());
					corpus.add(bp);
					alignements.add(al);
				}
				rdsource.close();
				rddest.close();
				
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Cooccurence getCooccurence() {
		return cooccurence;
	}

}
