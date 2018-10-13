public class Cores {

    public long verdeClaro = 0, vermelho = 0, azulClaro = 0, amarelo = 0, verdeEscuro = 0,
            marrom = 0, azulEscuro = 0, cinza = 0, dourado = 0, violeta = 0, preto = 0, laranja = 0;


    @Override
    public String toString() {
        return "Cores{" +
                "verdeClaro=" + verdeClaro +
                ", vermelho=" + vermelho +
                ", azulClaro=" + azulClaro +
                ", amarelo=" + amarelo +
                ", verdeEscuro=" + verdeEscuro +
                ", marrom=" + marrom +
                ", azulEscuro=" + azulEscuro +
                ", cinza=" + cinza +
                ", dourado=" + dourado +
                ", violeta=" + violeta +
                ", preto=" + preto +
                ", laranja=" + laranja +
                '}';
    }

    public void calc(String color, int add) {
        switch (color) {
            case "verde-claro":
                verdeClaro += add;
                break;
            case "vermelho":
                vermelho += add;
                break;
            case "azul-claro":
                azulClaro += add;
                break;
            case "amarelo":
                amarelo += add;
                break;
            case "verde-escuro":
                verdeEscuro += add;
                break;
            case "marrom":
                marrom += add;
                break;
            case "azul-escuro":
                azulEscuro += add;
                break;
            case "cinza":
                cinza += add;
                break;
            case "dourado":
                dourado += add;
                break;
            case "violeta":
                violeta += add;
                break;
            case "preto":
                preto += add;
                break;
            case "laranja":
                laranja += add;
                break;
        }
    }

    public long getTotal() {
        return verdeClaro + vermelho + azulClaro + amarelo + verdeEscuro + marrom + azulEscuro + cinza + dourado + violeta + preto + laranja;
    }
}

