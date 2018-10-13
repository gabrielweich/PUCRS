package analise_genoma;

import java.util.HashMap;
import java.util.Map;

public class TabelaAminoacidos {
	private static TabelaAminoacidos este = null;
	private Map<String, String> TabelaAminoacidos;
	
	private TabelaAminoacidos() {
		TabelaAminoacidos = new HashMap<String, String>();
        populateTabelaAminoacidos();
	}

	public static TabelaAminoacidos getInstance() {
		if (este == null) {
			este = new TabelaAminoacidos();
		}
		return este;
	}
	
	public String getAminoacid(String codon){
        return TabelaAminoacidos.get(codon);
	}
	
	private void populateTabelaAminoacidos() {
		TabelaAminoacidos.put("TTT", "F");// Phe
		TabelaAminoacidos.put("TTC", "F");

		TabelaAminoacidos.put("TTA", "L");// Leu
		TabelaAminoacidos.put("TTG", "L");

		TabelaAminoacidos.put("CTT", "L");// Leu
		TabelaAminoacidos.put("CTC", "L");
		TabelaAminoacidos.put("CTA", "L");
		TabelaAminoacidos.put("CTG", "L");

		TabelaAminoacidos.put("ATT", "I"); // Ile
		TabelaAminoacidos.put("ATC", "I");
		TabelaAminoacidos.put("ATA", "I");

		TabelaAminoacidos.put("ATG", "Met");

		TabelaAminoacidos.put("GTT", "V");// Val
		TabelaAminoacidos.put("GTC", "V");
		TabelaAminoacidos.put("GTA", "V");
		TabelaAminoacidos.put("GTG", "V");

		TabelaAminoacidos.put("TCT", "S"); // Ser
		TabelaAminoacidos.put("TCC", "S");
		TabelaAminoacidos.put("TCA", "S");
		TabelaAminoacidos.put("TCG", "S");

		TabelaAminoacidos.put("CCT", "P"); // Pro
		TabelaAminoacidos.put("CCC", "P");
		TabelaAminoacidos.put("CCA", "P");
		TabelaAminoacidos.put("CCG", "P");

		TabelaAminoacidos.put("ACT", "T"); // Thr
		TabelaAminoacidos.put("ACC", "T");
		TabelaAminoacidos.put("ACA", "T");
		TabelaAminoacidos.put("ACG", "T");

		TabelaAminoacidos.put("GCT", "A"); // Ala
		TabelaAminoacidos.put("GCC", "A");
		TabelaAminoacidos.put("GCA", "A");
		TabelaAminoacidos.put("GCG", "A");

		TabelaAminoacidos.put("TAT", "Y"); // Tyr
		TabelaAminoacidos.put("TAC", "Y");

		TabelaAminoacidos.put("TAA", "Stop");
		TabelaAminoacidos.put("TAG", "Stop");

		TabelaAminoacidos.put("CAT", "H"); // His
		TabelaAminoacidos.put("CAC", "H");

		TabelaAminoacidos.put("CAA", "Q"); // Gln
		TabelaAminoacidos.put("CAG", "Q");

		TabelaAminoacidos.put("AAT", "N");// Asn
		TabelaAminoacidos.put("AAC", "N");

		TabelaAminoacidos.put("AAA", "K");// Lys
		TabelaAminoacidos.put("AAG", "K");

		TabelaAminoacidos.put("GAT", "D");// Asp
		TabelaAminoacidos.put("GAC", "D");

		TabelaAminoacidos.put("GAA", "E"); // Glu
		TabelaAminoacidos.put("GAG", "E");

		TabelaAminoacidos.put("TGT", "C"); // Cys
		TabelaAminoacidos.put("TGC", "C");

		TabelaAminoacidos.put("TGA", "Stop");

		TabelaAminoacidos.put("TGG", "W"); // Trp

		TabelaAminoacidos.put("CGT", "R"); // Arg
		TabelaAminoacidos.put("CGC", "R");
		TabelaAminoacidos.put("CGA", "R");
		TabelaAminoacidos.put("CGG", "R");

		TabelaAminoacidos.put("AGT", "S"); // Ser
		TabelaAminoacidos.put("AGC", "S");

		TabelaAminoacidos.put("AGA", "R"); // Arg
		TabelaAminoacidos.put("AGG", "R");

		TabelaAminoacidos.put("GGT", "G"); // Gly
		TabelaAminoacidos.put("GGC", "G");
		TabelaAminoacidos.put("GGA", "G");
		TabelaAminoacidos.put("GGG", "G");
	}
}