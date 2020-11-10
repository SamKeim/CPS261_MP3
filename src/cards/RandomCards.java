package cards;

import java.util.List;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class RandomCards extends Application {
	
	TextField numberInput;
	Label status;
	List<Card> cards;
	Button deal;
	BorderPane primary;
	FlowPane cardsBox;
	Scene scene;

	@Override
	public void start(Stage primaryStage) {
		// setup main view
		primary = new BorderPane();

		addControls();
		dealCards(4);
		
		scene = new Scene(primary, 400, 400);
		primaryStage.setTitle("Random Cards");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	public void dealCards(int numOfCards) {

		if(cardsBox == null) {
			// cardsBox is null when program is first run,
			// creates a new box and ties it to the primary view
			cardsBox = new FlowPane();
			primary.setCenter(cardsBox);
			BorderPane.setAlignment(cardsBox, Pos.CENTER);
			cardsBox.setAlignment(Pos.CENTER);
			
		} else {
			cardsBox.getChildren().clear();
			cards.clear();	
		}
		
		// get four random cards from the Card class
		cards = Card.getCards(numOfCards);
		
		for(Card card : cards) {
			card.setOnMouseClicked(e -> card.toggleVisible());
			cardsBox.getChildren().add(card);
		}
	}
	
	// add controls to the borderpane
	public void addControls() {
		
		GridPane controlsBox = new GridPane();
		EventHandler<ActionEvent> dealCards = (e -> {
			if(isValid()) {
				dealCards(Integer.parseInt(numberInput.getText()));
			}
		});
		
		// setup number input
		numberInput = new TextField("4");
		numberInput.setOnAction(dealCards);
		status = new Label("");
		
		// setup deal buton
		deal = new Button("Deal Cards");
		deal.setOnAction(dealCards);
		
		// add all to controlsBox
		controlsBox.add(numberInput, 0, 0);
		controlsBox.add(deal, 1, 0);
		controlsBox.add(status, 0, 1, 2, 1);		
		
		// add to Primary
		primary.setBottom(controlsBox);
		// This center property isn't working, and I'm out of time
		BorderPane.setAlignment(controlsBox, Pos.TOP_CENTER);
		BorderPane.setMargin(controlsBox, new Insets(10));
	}
	
	public boolean isValid() {
		try {
			int test = Integer.parseInt(numberInput.getText());
			if(test < 1 || test > 54) {
				throw new IllegalArgumentException();
			}
			status.setText("");
			return true;
		} catch (NumberFormatException e) {
			status.setText("Please enter a numeric value.");
			return false;
		} catch (IllegalArgumentException e) {
			status.setText("Please enter a value between 1-54.");
			return false;
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
