package analise_genoma;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Gene {
	private String locus;
	private long begin;
	private long end;
	private ArrayList<Character> sequenciaBases;
	private Map<TipoLeitura, Leitura> mapaLeiturasRealizadas;

	public Gene(String locus, long begin, long end, ArrayList<Character> sequenciaBases) {
		this.locus = locus;
		this.begin = begin;
		this.end = end;
		this.sequenciaBases = sequenciaBases;
		mapaLeiturasRealizadas = new HashMap<TipoLeitura, Leitura>();
	}

	public String getLocus() {
		return locus;
	}

	public long getBegin() {
		return begin;
	}

	public long getEnd() {
		return end;
	}

	public List<Character> getSequenciaBases() {
		return sequenciaBases;
	}

	public Leitura gerarLeitura(TipoLeitura tipo) {
		Leitura leitura = mapaLeiturasRealizadas.get(tipo);
		if (leitura != null)
			return leitura;
		
		List<String> codons = AgruparCodons.leitura(tipo, this.sequenciaBases);
		List<String> aminoacidos = GerirAminoacidos.geraAminoacidos(codons);
		leitura = new Leitura(tipo, codons, aminoacidos);
		mapaLeiturasRealizadas.put(tipo, leitura);
		return leitura;
	}

	public Leitura gerarLeituraCorreta() {
		Leitura leitura = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_CORRETA);
		if (leitura != null)
			return leitura;

		List<Leitura> listaLeituras = new ArrayList<Leitura>();

		Leitura leitura531 = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_531);
		if (leitura531 == null)
			leitura531 = gerarLeitura(TipoLeitura.LEITURA_531);

		Leitura leitura532 = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_532);
		if (leitura532 == null)
			leitura532 = gerarLeitura(TipoLeitura.LEITURA_532);

		Leitura leitura533 = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_533);
		if (leitura533 == null)
			leitura533 = gerarLeitura(TipoLeitura.LEITURA_533);

		Leitura leitura351 = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_351);
		if (leitura351 == null)
			leitura351 = gerarLeitura(TipoLeitura.LEITURA_351);

		Leitura leitura352 = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_352);
		if (leitura352 == null)
			leitura352 = gerarLeitura(TipoLeitura.LEITURA_352);

		Leitura leitura353 = mapaLeiturasRealizadas.get(TipoLeitura.LEITURA_353);
		if (leitura353 == null)
			leitura353 = gerarLeitura(TipoLeitura.LEITURA_353);

		listaLeituras.add(leitura531);
		listaLeituras.add(leitura532);
		listaLeituras.add(leitura533);
		listaLeituras.add(leitura351);
		listaLeituras.add(leitura352);
		listaLeituras.add(leitura353);

		leitura = GerirAminoacidos.sequenciaCorretaAminoacidos(listaLeituras);
		mapaLeiturasRealizadas.put(TipoLeitura.LEITURA_CORRETA, leitura);
		return leitura;
	}

	@Override
	public String toString() {
		return "Gene [locus=" + locus + ", begin=" + begin + ", end=" + end + ", sequenciaBases=" + sequenciaBases
				+ "]";
	}
}
