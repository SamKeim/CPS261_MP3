package hockeyStats;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class BarChart extends Application {

	GridPane primary;
	Integer maxScore;
	Map<String, Integer> scoreBoard;
	int i;
	Label title = new Label();
	Scene scene;
	
	@Override
	public void start(Stage primaryStage) {
		// read scoreboard map from file
		setupScoreboard();
		
		// setup chart pane for application
		primary = new GridPane();
		primary.setPadding(new Insets(5));
		ColumnConstraints column = new ColumnConstraints(100);
		primary.getColumnConstraints().add(column);
		
		// Add title information
		setupTitle();
		primaryStage.setTitle(title.getText());
				
		// Add labels from Map to Gridpane
		setupLabels();
		
		// Add bars from Map to Gridpane
		setupBars();

		scene = new Scene(primary, 400, 170);
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	
	public void setupTitle() {
		// set styling for bar chart title
		title.setStyle("-fx-font-weight: bold; -fx-font-size: 15pt ; -fx-padding: 0 5 0 0;");
		primary.add(title, 0, 0, 2, 1);
	}
	
	public void setupLabels() {
		// set row counter to zero
		i = 1;
		// iterate through map
		for(Map.Entry<String, Integer> entry : scoreBoard.entrySet()) {	
			Label label = new Label(entry.getKey());
			label.setStyle("-fx-font-weight: bold; -fx-padding: 0 5 0 0;");
			primary.add(label, 0, i++);
		}

	}

	public void setupBars() {
		// set row counter to zero
		i = 1;
		// iterate through map
		for(Map.Entry<String, Integer> entry : scoreBoard.entrySet()) {
			Rectangle bar = new Rectangle(100, 15);
			// create style of bar
			bar.setStroke(Color.BLACK);
			bar.setFill(new Color(0.0, 0.5, (double)i / 10, 0.99));
			
			// bind to window to resize
			bar.widthProperty().bind(primary.widthProperty().subtract(110).multiply((double)entry.getValue() / maxScore));
			
			// add number label
			Label label = new Label(String.valueOf(entry.getValue()));
			label.setPadding(new Insets(0,0,0,5));
			label.setFont(Font.font("", FontWeight.BOLD, FontPosture.ITALIC, 10));

			// add to gridpane
			primary.add(bar, 1, i);
			primary.add(label, 1, i++);
			GridPane.setHalignment(label, HPos.LEFT);

		}
	}
	
	public void setupScoreboard() {
		// read in scores from txt file
		Scanner fileIn = null;
		scoreBoard = new LinkedHashMap<>();
		try {
			fileIn = new Scanner(new FileInputStream("src/mp3_hockey_stats.txt"));
			// I modified the original text file slightly, so that the
			// first line of the file describes the data in the file.
			// The scanner reads this in and saves it as the title for the window
			// and the title for the chart.
			title.setText(fileIn.nextLine());
			fileIn.useDelimiter("[,\\n\\r]");
			while(fileIn.hasNext()) {
				scoreBoard.put(fileIn.next(), fileIn.nextInt());
				fileIn.next(); // Clear scanner
			}
		} catch (FileNotFoundException e) {
			scoreBoard.put("Error: File Not Found", 0);
		} finally {
			if(fileIn != null) {
				fileIn.close();
			}
		}
		getHighScore();
	}
	
	public void getHighScore() {
		// get high score from board, used in calculating bar size
		maxScore = 0;
		for(Map.Entry<String, Integer> entry : scoreBoard.entrySet()) {
			if (entry.getValue() >= maxScore)
				maxScore = entry.getValue();
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
