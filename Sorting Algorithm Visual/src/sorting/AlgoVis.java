package sorting;


//file reading
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;


import javafx.application.*;
import javafx.geometry.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.*;
import javafx.scene.control.*; 

//chart
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class AlgoVis extends Application {
	
	public final int STAGE_HEIGHT = 480;
	public final int STAGE_WIDTH = 640;
	
	private Scene scene;
	private Stage stage;
	private VBox root;
	
	
	//chart manipulation
//	CategoryAxis Xaxis = new CategoryAxis();
//	NumberAxis Yaxis = new  NumberAxis();
//	BarChart<String, Number> chart = new BarChart(Xaxis, Yaxis);
//	
//	XYChart.Series bars = new XYChart.Series();
//	XYChart.Data[] dataArr; // = new XYChart.Data[numArr.length];
	
	Chart chart;
	
	private int[] numArr;
	
	
	@Override
	public void start(Stage stage) {
		//main vertical container
		root = new VBox();
		BackgroundFill bgFill = new BackgroundFill(Color.DARKSLATEGRAY, CornerRadii.EMPTY, Insets.EMPTY);
		Background bg = new Background(bgFill);
		root.setBackground(bg);
		parseFile();
	
		
		//chart initialization
		chart = new Chart(numArr, STAGE_WIDTH, STAGE_HEIGHT);
		chart.print();
		
		
		//buttons and button horizontal container
		Button btnSel = new Button("Selection Sort");
		btnSel.setStyle("-fx-background-color: DARKGRAY");
		btnSel.setOnAction(e -> { 
			Runnable r = () -> {
					selectionSort();	
			};
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.start();
			//selectionSort();
		}); //runs selection sort

		Button btnIns = new Button("Insertion Sort");
		btnIns.setStyle("-fx-background-color: DARKGRAY");
		btnIns.setOnAction(e -> {
			Runnable r = () -> {
				insertionSort();
			};
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.start();
		});
		
		Button btnMer = new Button("Merge Sort");
		btnMer.setStyle("-fx-background-color: DARKGRAY");
		btnMer.setOnAction(e -> {
			Runnable r = () -> {
				mergeSort();
			};
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.start();
		}); //runs merge sort
		
		Button btnHeap = new Button("Heap Sort");
		btnHeap.setStyle("-fx-background-color: DARKGRAY");
		btnHeap.setOnAction(e -> {
			Runnable r = () -> {
				heapSort();
			};
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.start();
		});
		Button btnQrp = new Button("Quick Sort Random Pivot");
		btnQrp.setStyle("-fx-background-color: DARKGRAY");
		btnQrp.setOnAction(e -> {
			Runnable r = () -> {
				quickSort();
			};
			Thread t = new Thread(r);
			t.setDaemon(true);
			t.start();
		});
		
		
		HBox btnBox = new HBox(btnSel, btnIns, btnMer, btnHeap, btnQrp);
		
		btnBox.setSpacing(10);
		btnBox.setPadding(new Insets(10));
		btnBox.setAlignment(Pos.CENTER);
		

		//adds to container
		root.getChildren().addAll(btnBox, chart);
		
		
		
		
		
		//sets stage
		this.scene = new Scene(root);
		stage.setResizable(false);
//		stage.setMaxWidth(STAGE_WIDTH);
//		stage.setMaxHeight(STAGE_HEIGHT);
//		stage.setMinWidth(STAGE_WIDTH);
//		stage.setMinHeight(STAGE_HEIGHT);
		
		
		
		stage.setTitle("Algorithm Visualizer");
		stage.setScene(scene);
		stage.sizeToScene();
		this.stage = stage;
		this.stage.show();
		
	} //start

	/**
	 * Parses text file to fill array with input
	 */
	private void parseFile() {
		String line = "";
		try {
			//reads file text into line var
			//10 random ints in src
			//File file = new File("C:\\Users\\Nika_Kcin\\OneDrive - University of Georgia\\Eclipse files\\Projects\\UGA\\Sorting Algorithm Visual\\src\\sorting\\random.txt");
			//100 random ints in onedrive
			File file = new File("C:\\Users\\Nika_Kcin\\OneDrive - University of Georgia\\Eclipse files\\100_random.txt");
			BufferedReader br = new BufferedReader(new FileReader(file));

			
			line = br.readLine();
			br.close();
			
			
		
			
			//adds line to array
			line = line.trim();
			String linecp = line;
			int numElements = 0;
			int ind;
			
			//finds number of elements;
			while ((ind = linecp.indexOf(' ')) != -1) {
				numElements++;
				linecp = linecp.substring(ind + 1);
				linecp.trim();
			} //finds number of elements
			if (linecp.length() > 0) {
				numElements++;
			} //accounts for last element
			
			//adds elements to array
			numArr = new int[numElements];
			for (int i = 0; i < numElements; i++) {
				int space = line.indexOf(' ');
				if (space > 0) {
					numArr[i] = Integer.parseInt(line.substring(0, space));
					line = line.substring(space + 1);
					line.trim();
				} else {
					numArr[i] = Integer.parseInt(line);
				} //last element
			} //for
			
		
		} catch(IOException e) {
			System.out.println("ERROR: IO Exception!");
			System.exit(1);
		}
		
		
		
	} //parseFile
	
	
	public void selectionSort() {
		chart.selectionSort();
		System.out.println("Sorted Data:");
		chart.print();
		chart.reset();
	} //selectionSort
	
	public void insertionSort() {
		chart.insertionSort();
		System.out.println("Sorted Data:");
		chart.print();
		chart.reset();	
	} //insertion Sort
	
	private void mergeSort() {
		chart.mergeSort();
		System.out.println("Sorted Data:");
		chart.print();
		chart.reset();
	} //mergeSort
	
	private void heapSort() {
		chart.heapSort();
		System.out.println("Sorted Data:");
		chart.print();
		chart.reset();
	} //heap sort
	
	private void quickSort() {
		chart.quickSort();
		System.out.println("Sorted Data:");
		chart.print();
		chart.reset();
	} //quickSort
	

	
	
	
} //algoVis
