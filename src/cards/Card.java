package cards;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Card extends ImageView {
	private boolean isVisible = false;
	private int cardNum;
	private Image cardFront;
	private static Image cardBack;
	
	public Card(int cardNum) {
		super();
		this.cardNum = cardNum;
		cardFront = new Image("CardImages/" + String.valueOf(this.cardNum) + ".png");
		cardBack = new Image("CardImages/back.png");
		super.setImage(getCardImage());
	}
	public boolean isFaceUp() {
		return isVisible;
	}
	public void setFaceUp(boolean isVisible) {
		this.isVisible = isVisible;
	}
	public int getCardNum() {
		return cardNum;
	}
	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	public Image getCardImage() {
		if(isVisible) {
			return cardFront;
		}
		return cardBack;
	}
	public void refreshImage() {
		super.setImage(getCardImage());
	}
	public void toggleVisible() {
		if(isVisible) {
			isVisible = false;
		} else {
			isVisible = true;
		}
		refreshImage();
	}
	public static List<Card> getCards(int number) {
		List<Card> cards = new ArrayList<>();
		List<Integer> deck = new ArrayList<>();
		for(int i = 1; i <= 54; i++) {
			deck.add(i);
		}
		Collections.shuffle(deck);
		for(int i = 0; i < number; i++) {
			cards.add(new Card(deck.get(i)));
		}
		return cards;
	}
}
