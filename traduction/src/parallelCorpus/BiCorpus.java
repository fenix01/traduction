package parallelCorpus;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class BiCorpus {
	private String sourcefile;//nom du fichier source
	private String destfile;//nom du fichier cible
	
	private ArrayList<BiPhrase> corpus;
	private ArrayList<Alignement> alignements;
	private Cooccurence cooccurence;
	
	//permet de remettre à zéro le BiCorpus
	//mais conserve les BiPhrases
	public void reset(){
		//nettoie la structure cooccurence
		cooccurence.reset();
		//conserve la structure alignements mais effectue un tirage aléatoire dessus
		for (Alignement al : alignements){
			al.randomAlignment();
		}
	}
	
	//fonction permettant de remplir cooccurrence
	//un appel à parseFile est nécessaire avant tout appel
	public void fillCooccurence(){
		for (int i = 0; i < corpus.size(); i++) {
			BiPhrase bp = corpus.get(i);

			for (int j = 0; j < alignements.get(i).getAlign().length; j++) {

				String src_word = bp.getArraysrc()[j];
				String dest_word = Cooccurence.NULL;

				int dest_idx = alignements.get(i).getAlign()[j];
				if (dest_idx > -1) {
					dest_word = bp.getArraydest()[dest_idx];
				}
				Compte c = cooccurence.addCompte(dest_word);
				c.addWord(src_word);
			}
		}
	}
	
	
	//permet d'afficher une Biphrase et le tableau d'alignement
	public void print(BiPhrase bp, Alignement al){
		System.out.println("######################################");
		for ( String src : bp.getArraysrc()){
			System.out.print(src+" ");
		}
		System.out.println();
		for ( int align : al.getAlign()){
			System.out.print(align+" ");
		}		
		System.out.println();
		for ( String dest : bp.getArraydest()){
			System.out.print(dest+" ");
		}	
		System.out.println();
		System.out.println("######################################");		
	}
	
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
	
	public void setAlignements(ArrayList<Alignement> al) {
		alignements = al;
	}

	public ArrayList<BiPhrase> getCorpus() {
		return corpus;
	}
	
	//parcours le corpus et remplit les structures de données correspondantes
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
				//on insère les données dans des arrayslists distincts
				String srcline,destline = "";
				int cpt =0;
				while ((srcline = rdsource.readLine()) != null && (destline = rddest.readLine()) != null){
					BiPhrase bp = new BiPhrase(srcline.toLowerCase(),destline.toLowerCase());
					Alignement al = new Alignement(bp.getArraysrc(), bp.getArraydest());
					corpus.add(bp);
					alignements.add(al);
					cpt++;
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
