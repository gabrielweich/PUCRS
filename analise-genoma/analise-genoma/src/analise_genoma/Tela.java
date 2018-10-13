package analise_genoma;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.event.EventHandler;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Tela extends Application {
	private Label lblLocus;
	private Label lblBegin;
	private Label lblEnd;
	private Label lblSequencia;
	
	private List<Gene> listaGenes;
	private List<String> listaLeituras;
	private Map<String, Runnable> mapaLeituras;
	
	private Desktop desktop = Desktop.getDesktop();
	public static final ObservableList genes = FXCollections.observableArrayList();
	public static ObservableList leituras = FXCollections.observableArrayList();
	
	private ListView lvListaGenes;
	private ListView lvLeituraBases;
	private ListView lvLeituraCodons;
	private ListView lvLeituraAminoacidos;
	
	private ListView lvListaLeituras;
	
	private static ObservableList leituraBases = FXCollections.observableArrayList();
	private static ObservableList leituraCodons = FXCollections.observableArrayList();
	private static ObservableList leituraAminoacidos = FXCollections.observableArrayList();
	
	private Stage primaryStage;
	private Canvas canvas;
	private GraphicsContext gc;
	private static final int imglarg = 300;
	private static final int imgAlt = 300;
	private GridPane gridGrafo;
	
	public Gene geneSelecionado;

	@Override
	public void start(Stage primaryStage) {
		GridPane gridMain = new GridPane();
			GridPane gridTitle = new GridPane();
			GridPane gridInfo = new GridPane();
				GridPane gridGeneSelect = new GridPane();
				GridPane gridGeneLeituras = new GridPane();
				GridPane gridGeneInfo = new GridPane();
			gridGrafo = new GridPane();

				gridMain.setPrefSize(720, 720);
				gridGeneSelect.setPrefWidth(300);
				gridGeneSelect.setVgap(10);
				gridGeneLeituras.setPrefWidth(300);
				gridGeneLeituras.setVgap(10);
				gridMain.setPadding(new Insets(25,25,25,25));
				
		lblLocus = new Label("Locus do gene: ");
		lblBegin = new Label("Início: ");
		lblEnd = new Label("Fim: ");
		lblSequencia = new Label("Sequência: ");
		
		Label lblLeituraBases = new Label("Bases: ");
		Label lblLeituraCodons = new Label("Códons: ");
		Label lblLeituraAminoacidos = new Label("Aminoácidos: ");
		
		listaGenes = new ArrayList<Gene>(200);
		
		lvListaGenes = new ListView();
		lvListaGenes.setPrefSize(90, 250);
		lvListaGenes.setEditable(true);
		
		lvListaLeituras = new ListView();
		lvListaLeituras.setPrefSize(90, 250);
		lvListaLeituras.setEditable(true);
		
		lvLeituraBases = new ListView();
		lvLeituraBases.setOrientation(Orientation.HORIZONTAL);
		lvLeituraBases.setPrefSize(500, 40);
		
		lvLeituraCodons = new ListView();
		lvLeituraCodons.setOrientation(Orientation.HORIZONTAL);
		lvLeituraCodons.setPrefSize(500, 40);
		
		lvLeituraAminoacidos = new ListView();
		lvLeituraAminoacidos.setOrientation(Orientation.HORIZONTAL);
		lvLeituraAminoacidos.setPrefSize(500, 40);
		
		
		
		Label lblTitleLocusList = new Label("Lista de genes");
		lblTitleLocusList.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		lblTitleLocusList.setPadding(new Insets(10,0,10,0));
		
		Label lblTitleLeiturasList = new Label("Realizar leitura");
		lblTitleLeiturasList.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		lblTitleLeiturasList.setPadding(new Insets(10,0,10,0));
		
		Label lblTitleGeneInfo = new Label("Dados");
		lblTitleGeneInfo.setFont(Font.font("Tahoma", FontWeight.NORMAL, 15));
		lblTitleGeneInfo.setPadding(new Insets(10,0,10,0));

		StackPane pnListaGenes = new StackPane();
		pnListaGenes.getChildren().add(lvListaGenes);
		
		StackPane pnListaLeituras = new StackPane();
		pnListaLeituras.getChildren().add(lvListaLeituras);
		
		
		StackPane pnLeituraBases = new StackPane();
		pnLeituraBases.getChildren().add(lvLeituraBases);
		
		StackPane pnLeituraCodons = new StackPane();
		pnLeituraCodons.getChildren().add(lvLeituraCodons);
		
		StackPane pnLeituraAminoacidos = new StackPane();
		pnLeituraAminoacidos.getChildren().add(lvLeituraAminoacidos);

		
		Label lblTitle = new Label("Análise de Genoma");
		lblTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		gridTitle.add(lblTitle, 0, 0);

		gridTitle.setAlignment(Pos.CENTER);
		gridTitle.prefWidthProperty().bind(gridMain.widthProperty());

		final FileChooser fileChooser = new FileChooser();

		final Button openButton = new Button("Abrir arquivo");

		openButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(final ActionEvent e) {
				configureFileChooser(fileChooser);
				File file = fileChooser.showOpenDialog(primaryStage);
				if (file != null) {
					openFile(file);
				}
			}
		});
		

		gridGeneSelect.add(lblTitleLocusList, 0, 0);
		gridGeneSelect.add(pnListaGenes, 0, 1);
		gridGeneSelect.add(openButton, 0, 2);
		
		gridGeneLeituras.add(lblTitleLeiturasList, 0, 0);
		gridGeneLeituras.add(pnListaLeituras, 0, 1);
		
		gridGeneInfo.add(lblTitleGeneInfo, 0, 0);
		
		gridGeneInfo.add(lblLocus, 0, 1);
		gridGeneInfo.add(lblBegin, 0, 2);
		gridGeneInfo.add(lblEnd, 0, 3);
		gridGeneInfo.add(lblSequencia, 0, 4);
		
		gridGeneInfo.add(lblLeituraBases, 0, 5);
		gridGeneInfo.add(pnLeituraBases, 0, 6);
		
		gridGeneInfo.add(lblLeituraCodons, 0, 7);
		gridGeneInfo.add(pnLeituraCodons, 0, 8);
		
		gridGeneInfo.add(lblLeituraAminoacidos, 0, 9);
		gridGeneInfo.add(pnLeituraAminoacidos, 0, 10);
		
		gridGeneInfo.setPadding(new Insets(0,0,0,10));
		gridGeneInfo.setVgap(5);
		
		gridMain.setVgap(10);
		gridMain.setHgap(10);
		
		lvListaGenes.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	gridGrafo.getChildren().clear();
		    	int index = lvListaGenes.getSelectionModel().getSelectedIndex();
		    	atualizaInfo(index);
		    }
		});
		
		canvas = new Canvas(imglarg, imgAlt);
		gc = canvas.getGraphicsContext2D();
		gridGrafo.add(canvas, 0, 0);
		gridGrafo.setAlignment(Pos.CENTER);
		
		
		gridMain.add(gridTitle, 0, 0);
		gridMain.add(gridInfo, 0, 1);
		gridMain.add(gridGrafo, 0, 2);
		
		gridInfo.add(gridGeneSelect, 0, 0);
		gridInfo.add(gridGeneLeituras, 1, 0);
		gridInfo.add(gridGeneInfo, 2, 0);
		

		VBox root = new VBox();
		root.setAlignment(Pos.CENTER);
		root.getChildren().add(gridMain);

		Scene scene = new Scene(root);
		primaryStage.setTitle("Gesiel - Análise de genes");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		mapaLeituras = new LinkedHashMap<String, Runnable>();
		

		
		lvListaLeituras.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {
		    @Override
		    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
		    	if(geneSelecionado != null) {
		    		mapaLeituras.get(newValue).run();
		    	}
		    }
		});
	}

	public static void main(String[] args) {
		launch(args);
	}

	private void openFile(File file) {
		LerArquivo lf = LerArquivo.getInstance();
		try {
			lf.inicializa(file);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		this.carregarGenes();
		criaLeituras();
	}

	private static void configureFileChooser(final FileChooser fileChooser) {
		fileChooser.setTitle("Abrir arquivo");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
	}
	
	private void carregarGenes() {
		LerArquivo lf = LerArquivo.getInstance();
		genes.clear();
		listaGenes.clear();
		
		lf.getListaGenes().forEach(gene -> {listaGenes.add(gene);genes.add(gene.getLocus());});
		
		lvListaGenes.setItems(genes);
	}

	public void atualizaInfo(int index){
		geneSelecionado = listaGenes.get(index);
		lblLocus.setText("Locus do gene: " + geneSelecionado.getLocus());
    	lblBegin.setText("Início: " + geneSelecionado.getBegin());
    	lblEnd.setText("Fim: " + geneSelecionado.getEnd());
    	List<Character> sequencia = geneSelecionado.getSequenciaBases();
    	
    	leituraBases.clear();
    	leituraCodons.clear();
    	leituraAminoacidos.clear();
    	
    	
    	leituraBases.addAll(geneSelecionado.getSequenciaBases());
    	
    	lvLeituraBases.setItems(leituraBases);
    	lvLeituraCodons.setItems(leituraCodons);
    	lvLeituraAminoacidos.setItems(leituraAminoacidos);
    	
    	canvas = new Canvas(imglarg, imgAlt);
		gc = canvas.getGraphicsContext2D();
    	drawSequence(leituraBases);
    	gridGrafo.add(canvas, 0, 0);
    
	}
	
	public void criaLeituras() {
		mapaLeituras.put(TipoLeitura.LEITURA_531.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeitura(TipoLeitura.LEITURA_531)));
		mapaLeituras.put(TipoLeitura.LEITURA_532.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeitura(TipoLeitura.LEITURA_532)));
		mapaLeituras.put(TipoLeitura.LEITURA_533.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeitura(TipoLeitura.LEITURA_533)));
		mapaLeituras.put(TipoLeitura.LEITURA_351.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeitura(TipoLeitura.LEITURA_351)));
		mapaLeituras.put(TipoLeitura.LEITURA_352.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeitura(TipoLeitura.LEITURA_352)));
		mapaLeituras.put(TipoLeitura.LEITURA_353.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeitura(TipoLeitura.LEITURA_353)));
		mapaLeituras.put(TipoLeitura.LEITURA_CORRETA.toString(), () -> realizaLeitura(() -> geneSelecionado.gerarLeituraCorreta()));
		
		List<String> listaLeituras = new ArrayList<String>(mapaLeituras.keySet());
		
		leituras = FXCollections.observableArrayList(listaLeituras);
		
		lvListaLeituras.setItems(leituras);
		
		
	}
	
	public <T> void realizaLeitura(LeituraSupplier supplier) {
		Leitura leitura = supplier.ler();
		leituraCodons.clear();
		leituraAminoacidos.clear();
		
    	leituraCodons.addAll(leitura.getCodons());
    	leituraAminoacidos.addAll(leitura.getAminoacidos());
    	
    	lvLeituraCodons.setItems(leituraCodons);
    	lvLeituraAminoacidos.setItems(leituraAminoacidos);
    	
    	lblSequencia.setText("Sequência: " + leitura.getIdentificacao());
	}
	
	
	private Color getNucleotideColor(Character n) {
		switch (n) {
		case 'A':
			return Color.BLUE;
		case 'C':
			return Color.YELLOW;
		case 'T':
			return Color.GREEN;
		case 'G':
			return Color.RED;
		default:
			return Color.BLACK;
		}
	}

	// x = r cos(t) y = r sin(t)
	private void drawSequence(List<Character> nucl) {
		

		int cx = imglarg / 2;
		int cy = imgAlt / 2;
		int raioInterno = 50;
		int raioExterno = 130;
		gc.setLineWidth(1);
		int i = 0;
		for (double ang = 0; ang < Math.PI * 2; ang += (Math.PI * 2) / nucl.size(), i++) {
			if (i < nucl.size()) {
				double x1 = raioInterno * Math.cos(ang);
				double y1 = raioInterno * Math.sin(ang);
				double x2 = raioExterno * Math.cos(ang);
				double y2 = raioExterno * Math.sin(ang);
				gc.setStroke(getNucleotideColor(nucl.get(i)));
				gc.strokeLine((int) (cx + x1), (int) (cy + y1), (int) (cx + x2), (int) (cy + y2));
			}
		}
	}
}