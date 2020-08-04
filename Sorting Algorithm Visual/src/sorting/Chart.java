package sorting;

import java.io.File;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class Chart extends StackPane {
	private final int STAGE_WIDTH; // 640
	private final int STAGE_HEIGHT; // 480

	private double[] numArr_DEF;
	private double[] heightArr_DEF;
	private double[] numArr;
	private double[] heightArr;
	private Rectangle[] recs;

	private Text text;
	private int numComparisons;

	private String soundPath = "C:\\Users\\Nika_Kcin\\OneDrive - University of Georgia\\Eclipse files\\menuBeep.mp3";
	private Media media = new Media(new File(soundPath).toURI().toString());
	private MediaPlayer mediaPlayer = new MediaPlayer(media);

	public Chart(int[] arr, int stage_width, int stage_height) {
		super();

		HBox scene = new HBox();
		numComparisons = 0;
		text = new Text("   Number of comparisons: " + numComparisons);
		text.setFill(Color.WHITE);
		text.setTextAlignment(TextAlignment.LEFT);
		STAGE_WIDTH = stage_width;
		STAGE_HEIGHT = stage_height;

		BackgroundFill bgFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
		Background bg = new Background(bgFill);
		scene.setBackground(bg);
		scene.setAlignment(Pos.BOTTOM_LEFT);

		// sets up default arrays
		numArr_DEF = new double[arr.length];
		heightArr_DEF = new double[arr.length];

		// adds elements to local array and creates rectangles for each elem
		numArr = new double[arr.length];
		heightArr = new double[arr.length];
		recs = new Rectangle[arr.length];

		for (int i = 0; i < arr.length; i++) {

			// converts integer to decimal i.e. 999->.999 500->.500 1->.1
			int recWidth = STAGE_WIDTH / recs.length;
			double heightD = intToDec(arr[i]); // decimal value of height
			System.out.print(arr[i] + ", " + heightD);
			System.out.println();
			double recHeight = (STAGE_HEIGHT * heightD); // decimal value of height scaled to 480

			numArr_DEF[i] = heightD;
			numArr[i] = heightD;
			heightArr_DEF[i] = recHeight;
			heightArr[i] = recHeight;

			recs[i] = new Rectangle(recWidth, recHeight); // width, height
			recs[i].setStyle("-fx-fill: white; -fx-stroke: grey; -fx-stroke-width: 1;");
			scene.getChildren().add(recs[i]);
		} // copies elements into numArr

		this.getChildren().addAll(scene, text);
		this.setAlignment(Pos.TOP_LEFT);

	} // default constructor

	private double intToDec(int num) {
		double dec = num;
		String height = Integer.toString(num);
		String heightD = ".";
		if (num < 10) {
			dec = Double.parseDouble(heightD + "0" + num); // converts number to .0#
		} else {
			while (height.length() > 0) {
				int takeOff = num % 10;
				heightD = heightD.substring(0, 1) + takeOff + heightD.substring(1);
				height = height.substring(0, height.length() - 1);
				if (height.length() > 0) {
					num = Integer.parseInt(height);
				} // if
			} // while
			dec = Double.parseDouble(heightD);

		} // else
		return dec;
	} // intToDec

	private void reColor() {
		// playMedia();
		delay();
		// recolors rectangles to correct color and sets to correct height
		for (int i = 0; i < numArr.length; i++) {
			recs[i].setHeight(heightArr[i]);
			recs[i].setStyle("-fx-fill: white; -fx-stroke: grey; -fx-stroke-width: 1;");
		} // recolors rectangles height

	} // recolors rectangle

	private void compColor(int index) {
		recs[index].setStyle("-fx-fill: red; -fx-stroke: grey; -fx-stroke-width: 1;");
	} // changes rectangle to red for comparison

	private void updateComparisons() {

		numComparisons++;
		try {
			text.setText("   Number of comparisons: " + numComparisons);
		} catch (Exception e) {
			System.out.println("ERROR: Updating number of comparisons!");
		}

	} // update comparisons

	private void playMedia() {
		mediaPlayer.seek(Duration.ZERO);
		mediaPlayer.play();

	} // plays media

	// SELECTION SORT()
	// ---------------------------------------------------------------

	public void selectionSort() {

		int endIndex = numArr.length;

		for (int i = 0; i < endIndex; i++) {
			swap(i, minIndex(i, endIndex));

		} // performs the swaps for selection sort

	} // selection sort

	private int minIndex(int start, int end) {
		int min = start;
		for (int i = start + 1; i < end; i++) {
			updateComparisons();
			if (numArr[i] < numArr[min]) {
				min = i;
			} // makes min value current if less
		} // finds lowest number

		return min;
	} // selection sort - minIndex

	private void swap(int current, int min) {
		compColor(current);
		compColor(min);

		double temp = numArr[current];
		numArr[current] = numArr[min];
		numArr[min] = temp;

		double tempH = heightArr[current];
		heightArr[current] = heightArr[min];
		heightArr[min] = tempH;

		reColor();

	} // selection sort - swap

	// INSERTION SORT()
	// ---------------------------------------------------------------

	public void insertionSort() {
		// sorts data
		for (int i = 1; i < numArr.length; i++) {
			double num = numArr[i];
			double hei = heightArr[i];
			int j = i - 1;
			compColor(j + 1);

			while (j >= 0 && numArr[j] > num) {
				updateComparisons();
				numArr[j + 1] = numArr[j];
				heightArr[j + 1] = heightArr[j];
				j--;

				// reColor();
			} // while

			numArr[j + 1] = num;
			heightArr[j + 1] = hei;

			reColor();

		} // for

	} // insertion sort

	// MERGE SORT() ---------------------------------------------------------------

	public void mergeSort() {

		merge_sort(0, numArr.length - 1, numArr.length);

	} // mergeSort

	private void merge_sort(int first, int last, int size) {
		if (first < last) { // base case
			int middle = (first + last) / 2;
			merge_sort(first, middle, size);
			merge_sort(middle + 1, last, size);

			merge(first, middle, middle + 1, last, size);
		} // runs merge sort

	} // merge Sort - merge_sort

	private void merge(int leftFirst, int leftLast, int rightFirst, int rightLast, int size) {
		double[] temp_numArr = new double[size];
		double[] temp_heightArr = new double[size];
		int index = leftFirst;
		int saveFirst = leftFirst;

		while ((leftFirst <= leftLast) && (rightFirst <= rightLast)) { // compares values
			updateComparisons();
			compColor(leftFirst);
			compColor(rightFirst);

			if (numArr[leftFirst] < numArr[rightFirst]) {
				temp_numArr[index] = numArr[leftFirst];
				temp_heightArr[index] = heightArr[leftFirst];
				leftFirst++;
			} else {
				temp_numArr[index] = numArr[rightFirst];
				temp_heightArr[index] = heightArr[rightFirst];
				rightFirst++;
			}
			index++;
			reColor();
		} // while

		while (leftFirst <= leftLast) {
			updateComparisons();
			compColor(index);
			compColor(leftFirst);

			temp_numArr[index] = numArr[leftFirst];
			temp_heightArr[index] = heightArr[leftFirst];
			leftFirst++;
			index++;
			reColor();
		} // left

		while (rightFirst <= rightLast) {
			updateComparisons();
			compColor(index);
			compColor(rightFirst);

			temp_numArr[index] = numArr[rightFirst];
			temp_heightArr[index] = heightArr[rightFirst];
			rightFirst++;
			index++;
			reColor();
		} // right

		for (index = saveFirst; index <= rightLast; index++) {
			updateComparisons();
			compColor(index);

			numArr[index] = temp_numArr[index];
			heightArr[index] = temp_heightArr[index];
			reColor();
		} // copies values from temp array

	} // merge sort - merge

	// HEAP SORT() ---------------------------------------------------------------

	public void heapSort() {

		// converts array into a heap
		for (int i = (numArr.length / 2) - 1; i >= 0; i--) {
			reHeapDown(i, numArr.length - 1);
		} // for

		// sorts the array
		for (int i = numArr.length - 1; i >= 1; i--) {
			swap(i, 0); // uses selection sort swap
			reHeapDown(0, i - 1);
		} // for

	} // heap sort

	private void reHeapDown(int root, int bottom) {
		int maxChild;
		int rightChild;
		int leftChild;

		leftChild = root * 2 + 1;
		rightChild = root * 2 + 2;

		// checks position of child
		if (leftChild <= bottom) {
			updateComparisons();
			if (leftChild == bottom) {
				maxChild = leftChild;
			} else if (numArr[leftChild] < numArr[rightChild] || numArr[leftChild] == numArr[rightChild]) {
				maxChild = rightChild;
			} else {
				maxChild = leftChild;
			} // else

			// swaps elements and calls recursively
			if (numArr[root] < numArr[maxChild]) {
				swap(root, maxChild);
				reHeapDown(maxChild, bottom);
			} // if

		} // if

	} // heap sort - reheapDown

	// QUICK SORT() ---------------------------------------------------------------

	public void quickSort() {
		quick_sort(0, numArr.length - 1);
	} // quick sort

	private void quick_sort(int first, int last) {
		int splitIndex;
		if (first < last) {
			// splitting using random split point
			splitIndex = splitRand(first, last);
			quick_sort(first, splitIndex - 1);
			quick_sort(splitIndex + 1, last);
		} // recursive call to quick_sort

	} // quick sort - quick_sort

	private int splitRand(int first, int last) {
		// creates random split in bounds
		int splt = (int) (first + (Math.random() % (last - first)));

		swap(first, splt); // swaps elements

		return split(first, last);

	} // quick sort - splitRand

	private int split(int first, int last) {
		int index = first + 1;
		int split = first;

		for (int i = first; i <= last; i++) {
			updateComparisons();
			if (numArr[i] < numArr[split]) {
				swap(index, i);
				index++;
			} // performs swap

		} // compares values inserting them to target position

		// swaps value at index with index obtained
		swap(first, index - 1);

		return index - 1;

	} // quick sort - split

	public void reset() {

		// resets list to original list
		for (int i = 0; i < numArr.length; i++) {
			numArr[i] = numArr_DEF[i];
			heightArr[i] = heightArr_DEF[i];
		} // for

		// delay to show sorted list for 3 seconds before revert to original
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// displays list as original
		reColor();
		numComparisons = 0;
		text.setText("   Number of comparisons: " + numComparisons);

	} // resets chart

	private void delay() {
		try {
			Thread.sleep(50);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // delay for visual
	} // delay

	public void print() {
		playMedia();
		System.out.print("Chart data: ");
		for (int i = 0; i < numArr.length; i++) {
			if (i == numArr.length - 1) {
				System.out.print(numArr[i]);
			} else {
				System.out.print(numArr[i] + ", ");
			}
		} // prints array

		System.out.println();
		System.out.println();

		System.out.print("Heights data: ");
		for (int i = 0; i < numArr.length; i++) {
			if (i == numArr.length - 1) {
				System.out.print(heightArr[i]);
			} else {
				System.out.print(heightArr[i] + ", ");
			}
		} // prints array

		System.out.println("Differences > 5");
		for (int i = 1; i < heightArr.length; i++) {
			if (heightArr[i] - heightArr[i - 1] > 6) {
				System.out.println(i - 1 + ", " + i);
				System.out.println(heightArr[i - 1] + ", " + heightArr[i]);
			}
		}

		System.out.println();
		System.out.println();

	} // print

} // Chart
