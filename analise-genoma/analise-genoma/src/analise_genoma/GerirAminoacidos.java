package analise_genoma;

import java.util.List;
import java.util.stream.Collectors;

public class GerirAminoacidos {
	public static List<String> geraAminoacidos(List<String> lista_codons) {
		TabelaAminoacidos ta = TabelaAminoacidos.getInstance();

		List<String> listaAminoacidos =  lista_codons.stream()
				.map(codon -> ta.getAminoacid(codon)).collect(Collectors.toList());

		return listaAminoacidos;
	}

	public static Leitura sequenciaCorretaAminoacidos(List<Leitura> lista_leituras) {
		Leitura leitura_correta = null;
		int maior_distancia = 0;
		for (Leitura leitura : lista_leituras) {
			int distancia = -1;
			int estado = -1;
			for (String amino : leitura.getAminoacidos()) {
				distancia++;
				if (amino.equalsIgnoreCase("met") && estado == -1) {
					distancia = 0;
					estado = 0;
				}
				if (amino.equalsIgnoreCase("stop") && estado == 0) {
					if (distancia > maior_distancia) {
						
						leitura_correta = leitura;
					}
					distancia = 0;
					estado = -1;
				}
			}
		}
		return leitura_correta;

	}
}
