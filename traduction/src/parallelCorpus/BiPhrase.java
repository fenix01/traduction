package parallelCorpus;

public class BiPhrase {
	
	private String[] arraysrc;
	private String[] arraydest;
	
	public String[] getArraysrc() {
		return arraysrc;
	}

	public String[] getArraydest() {
		return arraydest;
	}

	public BiPhrase(String src, String dest){
		String src2 = src.replaceAll(" [0-9,$!]+", "");
		String dest2 = dest.replaceAll(" [0-9,$!]+", "");
		arraysrc = src2.split(" ");
		arraydest = dest2.split(" ");
	}
	

}
