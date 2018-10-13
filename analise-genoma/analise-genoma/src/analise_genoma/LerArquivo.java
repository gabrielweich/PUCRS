package analise_genoma;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LerArquivo {
	private static LerArquivo este = null; 
	private File sourceFile;
	private boolean loaded;
	private List<Gene> listaGenes;

	private void loadData() throws FileNotFoundException {
		if (sourceFile == null) return;
		
		Pattern pattern = Pattern.compile("(\\[(.*?)\\])");
		Pattern patloc = Pattern.compile("(\\[location=(\\d+)..(\\d+)\\])");
		Pattern patLocus = Pattern.compile("(\\[locus_tag=(.*?)\\])");

		Scanner data = new Scanner(sourceFile);

		String locus = "";
		long begin = -1;
		long end = -1;
		ArrayList<Character> sequence = new ArrayList<>(200);

		// Percorre o arquivo do genoma
		while (data.hasNextLine()) {
			String line = data.nextLine();
			// Se é uma linha de cabeçalho ...
			if (line.length() > 0 && line.charAt(0) == '>') {
				if (begin != -1) {
					// Se já tem um Gene para montar, monta e armazena
					Gene gene = new Gene(locus, begin, end, sequence);
					this.listaGenes.add(gene);
					
					locus = "";
					begin = -1;
					end = -1;
					sequence = new ArrayList<>(200);
				}
				Matcher matcher = pattern.matcher(line);
				// Procura pelos dados
				while (matcher.find()) {
					String token = matcher.group(1);
					Matcher matchLoc = patloc.matcher(token);
					if (matchLoc.matches()) {
						begin = Long.parseLong(matchLoc.group(2));
						end = Long.parseLong(matchLoc.group(3));
					} else {
						Matcher matchLocus = patLocus.matcher(token);
						if (matchLocus.matches()) {
							locus = matchLocus.group(2);
						}
					}
				}
			} else {
				// Se é uma linha de sequencia de gene ...
				for (int i = 0; i < line.length(); i++) {
					sequence.add(line.charAt(i));
				}
			}
		}
		data.close();
		loaded = true;
	}

	private LerArquivo(){
		loaded = false;
		sourceFile = null;
	}
	
	public static LerArquivo getInstance(){
		if (este == null){
			este = new LerArquivo();
		}
		return este;
	}
	
	public void inicializa(File sourceFile) throws FileNotFoundException {
		this.listaGenes = new ArrayList<Gene>();
		this.sourceFile = sourceFile;
		this.loadData();
		this.loaded = true;
	}
	
	public List<Gene> getListaGenes(){
		return this.listaGenes;
	}

	
}