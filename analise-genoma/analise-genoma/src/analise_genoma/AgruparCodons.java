package analise_genoma;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.IntBinaryOperator;
import java.util.function.IntFunction;

public class AgruparCodons {
	private static List agrupaCodons(List<Character> listaBases, int posInicio, int posFim, int caminho, IntBinaryToBoolean verificaFinal) {
		List<String> listaCodons = new ArrayList<String>();
		String codon;
		for(int i = posInicio; verificaFinal.op(i, posFim); i = i + (3*caminho)) {
			codon = "";
			for(int j = i; verificaFinal.op(j, (i + (caminho*3))); j = j + caminho) {
				codon += listaBases.get(j).toString();
			}
			listaCodons.add(codon);
		}
		return listaCodons;
	}
	

	public static List leitura531(List<Character> listaBases){
		int posInicio = 0;
		int posFim = listaBases.size() - 2;
		int caminho = 1;
		IntBinaryToBoolean verificaFinal = (a, b) -> a < b;
		return agrupaCodons(listaBases, posInicio, posFim, caminho, verificaFinal);
	}
	
	public static List leitura532(List<Character> listaBases){
		int posInicio = 1;
		int posFim = listaBases.size() - 2;
		int caminho = 1;
		IntBinaryToBoolean verificaFinal = (a, b) -> a < b;
		return agrupaCodons(listaBases, posInicio, posFim, caminho, verificaFinal);
	}
	
	public static List leitura533(List<Character> listaBases){
		int posInicio = 2;
		int posFim = listaBases.size() - 2;
		int caminho = 1;
		IntBinaryToBoolean verificaFinal = (a, b) -> a < b;
		return agrupaCodons(listaBases, posInicio, posFim, caminho, verificaFinal);
	}
	
	public static List leitura351(List<Character> listaBases){
		int posInicio = listaBases.size() - 1;
		int posFim = 1;
		int caminho = -1;
		IntBinaryToBoolean verificaFinal = (a, b) -> a > b;
		return agrupaCodons(listaBases, posInicio, posFim, caminho, verificaFinal);
	}
	
	public static List leitura352(List<Character> listaBases){
		int posInicio = listaBases.size() - 2;
		int posFim = 1;
		int caminho = -1;
		IntBinaryToBoolean verificaFinal = (a, b) -> a > b;
		return agrupaCodons(listaBases, posInicio, posFim, caminho, verificaFinal);
	}
	
	public static List leitura353(List<Character> listaBases){
		int posInicio = listaBases.size() - 3;
		int posFim = 1;
		int caminho = -1;
		IntBinaryToBoolean verificaFinal = (a, b) -> a > b;
		return agrupaCodons(listaBases, posInicio, posFim, caminho, verificaFinal);
	}
	
	
	public static List leitura(TipoLeitura tipo, List<Character> listaBases) {
		switch(tipo) {
			case LEITURA_531: return leitura531(listaBases);
			case LEITURA_532: return leitura532(listaBases);
			case LEITURA_533: return leitura533(listaBases);
			case LEITURA_351: return leitura351(listaBases);
			case LEITURA_352: return leitura352(listaBases);
			case LEITURA_353: return leitura353(listaBases);
			default: return null;
		}
	}
	
}
