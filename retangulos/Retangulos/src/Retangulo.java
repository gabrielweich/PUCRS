public class Retangulo {
    private int x1, y1, x2, y2;
    private String cor;
    public Retangulo(int x1, int  y1, int x2, int y2, String cor){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.cor = cor;
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public String getCor() {
        return cor;
    }
}
