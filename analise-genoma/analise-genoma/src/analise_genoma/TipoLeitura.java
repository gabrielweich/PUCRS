package analise_genoma;

public enum TipoLeitura {
	LEITURA_531("Leitura 531"),
	LEITURA_532("Leitura 532"),
	LEITURA_533("Leitura 533"),
	LEITURA_351("Leitura 351"),
	LEITURA_352("Leitura 352"),
	LEITURA_353("Leitura 353"),
	LEITURA_CORRETA("Correta");
	
	private final String name;
	
	TipoLeitura(String nome) {
        this.name = nome;
    }
	
	@Override
    public String toString() {
        return name;
    }
}
