import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private ArrayList<Card> deck;
    private Values[] values = Values.values();
    private Suits[] suits = Suits.values();

    public Deck() {
        this.deck = new ArrayList<Card>();
    }

    public void createFullDeck() {
        // generate cards
        for(int i = 0; i < suits.length; i++) {
            Suits cardSuit = suits[i];
            for(int j = 0; j < values.length; j++) {
                this.addCard(new Card(cardSuit, values[j]));
            }
        }
    }

    public void shuffleDeck(){
        Collections.shuffle(deck);
    }

    public Card getCard(int i){
        return this.deck.get(i);
    }

    public void removeCard(int i){
        this.deck.remove(i);
    }

    public void addCard(Card addCard) {
        this.deck.add(addCard);
    }

    // Get the size of the deck
    public int deckSize() {
        return this.deck.size();
    }

    // Draws from the deck
    public void draw(Deck comingFrom) {
        this.deck.add(comingFrom.getCard(0));
        comingFrom.deck.remove(0);
    }

    // This will move cards back into the deck to continue playing
    public void moveAllToDeck(Deck moveTo) {
        for(int i = 0; i < deck.size(); i ++) {
            moveTo.deck.add(this.deck.get(i));
        }
        this.deck = new ArrayList<Card>();
    }

    @Override
    public String toString() {
        String response = "";
        for(Card card: deck) {
            response += card.toString() + ", ";
        }
        return response;
    }

    public String getFirstCard() {
        return deck.get(0).toString() + ", ";
    }

    public int getFirstCardValue() {
        return deck.get(0).getValue().value;
    }

    public int getValue() {
        int total = 0;
        for(Card card: deck) {
            if(card.getValue() != Values.ACE) {
                total += card.getValue().value;
            }
        }
        for(Card card: deck) {
            if(card.getValue() == Values.ACE) {
                if(card.getValue() == Values.ACE) {
                    if((total + 11) > 21) {
                        total += 1;
                    } else {
                        total += 11;
                    }
                }
            }
        }
        return total;
    }

}
