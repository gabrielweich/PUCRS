import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class App {

    public static void main(String[] args) {
        long start = System.currentTimeMillis();

        Plano plano = new Plano(50000);
        start(plano);

        long end = System.currentTimeMillis();
        plano.printCores();
        System.out.println("Millis:  " + (double) (end - start));

    }


    public static void start(Plano plano) {
        BufferedReader reader = null;
        ArrayList<Retangulo> listaRetangulos;

        try {
            File file = new File("src/teste100000");
            reader = new BufferedReader(new FileReader(file));
            listaRetangulos = new ArrayList<>(Integer.parseInt(reader.readLine()));

            String line;

            while ((line = reader.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line);
                listaRetangulos.add(new Retangulo(Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), Integer.parseInt(tokenizer.nextToken()), tokenizer.nextToken()));
            }

            for (int i = listaRetangulos.size() - 1; i >= 0; i--) {
                plano.addNodo(listaRetangulos.get(i));
            }


        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

