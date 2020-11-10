package conversion;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

// enum to handle switching between convert options
enum Type {
	DISTANCE, TEMPERATURE, WEIGHT;
}

public class ConversionController {
	@FXML
	private TextField textA;
	@FXML
	private TextField textB;
	@FXML
	private Button convert;
	@FXML
	private Button clear;
	@FXML
	private Label status;
	@FXML
	private Label labelA;
	@FXML
	private Label labelB;
	@FXML
	private RadioButton distance;
	@FXML
	private RadioButton temperature;
	@FXML
	private RadioButton weight;
	public Type conversionType = Type.DISTANCE;

	public void convert() {
		if (isValid(textA)) {
			convertAB();
			return;
		}
		if (isValid(textB)) {
			convertBA();
			return;
		}
	}

	public boolean isValid(TextField value) {
		try {
			if(value.getText().isBlank()) throw new IllegalArgumentException();
			Double.parseDouble(value.getText());
			status.setText(""); // Sets status to blank if data is valid
			return true;
		} catch (NumberFormatException e) {
			status.setText("Please enter a numerical value.");
			return false;
		} catch (IllegalArgumentException e) {
			status.setText("Please enter a value.");
			return false;
		}
	}

	// quick method to clear both input boxes and status bar
	public void clearAll() {
		clearTextA();
		clearTextB();
		status.setText("");
	}

	public void clearTextA() {
		textA.setText("");
	}

	public void clearTextB() {
		textB.setText("");
	}

	// When Miles and Kilometers radio button is selected,
	// this method is called to clear fields, update labels,
	// and set enum type
	public void distanceConvert() {
		labelA.setText("Miles:");
		labelB.setText("Kilometers:");
		conversionType = Type.DISTANCE;
		clearAll();
	}

	public void temperatureConvert() {
		labelA.setText("Celsius:");
		labelB.setText("Fahrenheit:");
		conversionType = Type.TEMPERATURE;
		clearAll();
	}

	public void weightConvert() {
		labelA.setText("Pounds:");
		labelB.setText("Kilograms:");
		conversionType = Type.WEIGHT;
		clearAll();
	}

	// converts field A to field B based on conversionType enum
	public void convertAB() {
		if(isValid(textA)) {
			switch (conversionType) {
			case DISTANCE:
				// mi to km
				double km = Double.parseDouble(textA.getText()) * 1.609344;
				textB.setText(String.format("%.2f", km));
				break;
			case TEMPERATURE:
				// f to c
				double c = (Double.parseDouble(textA.getText()) * 9 / 5) + 32;
				textB.setText(String.format("%.2f", c));
				break;
			case WEIGHT:
				// lb to kg
				double kg = Double.parseDouble(textA.getText()) * 0.45359237;
				textB.setText(String.format("%.2f", kg));
				break;
			}
		}
	}

	// converts field B to field A based on conversionType enum
	public void convertBA() {
		if(isValid(textB)) {
			switch (conversionType) {
			case DISTANCE:
				// km to mi
				double mi = Double.parseDouble(textB.getText()) / 1.609344;
				textA.setText(String.format("%.2f", mi));
				break;
			case TEMPERATURE:
				// c to f
				double f = (Double.parseDouble(textB.getText()) - 32) * 5 / 9;
				textA.setText(String.format("%.2f", f));
				break;
			case WEIGHT:
				// kg to lb
				double lb = Double.parseDouble(textB.getText()) / 0.45359237;
				textA.setText(String.format("%.2f", lb));
				break;
			}
		}
	}
}
