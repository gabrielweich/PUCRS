package analise_genoma;

import java.util.List;

public class Leitura {	
	private List<String> codons;
	private List<String> aminoacidos;
	private TipoLeitura identificacao;
	
	public Leitura(TipoLeitura identificacao, List<String> codons, List<String> aminoacidos) {
		this.identificacao = identificacao;
		this.codons = codons;
		this.aminoacidos = aminoacidos;
	}
	
	public List<String> getCodons() {
		return codons;
	}

	public List<String> getAminoacidos() {
		return aminoacidos;
	}
	
	public TipoLeitura getIdentificacao() {
		return identificacao;
	}
	
}
