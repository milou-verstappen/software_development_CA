package software_development_CA;
import java.util.LinkedList;
import java.util.Queue;


public class CardDeck {
    private Queue<Cards> deck = new LinkedList<Cards>();
    final private int deckNum;

    public CardDeck(int deckNum) {
        this.deckNum = deckNum;
    }

    public int getDeckNum() {
        return this.deckNum;
    }

    public Queue<Cards> getDeck() {
        return this.deck;
    }

    public void addDeck(Cards card) {
        synchronized (card) {
            this.deck.add(card);
        }
    }

    public Cards popDeck() {
        synchronized (this.deck) {
            return this.deck.remove();
        }
    }

}
