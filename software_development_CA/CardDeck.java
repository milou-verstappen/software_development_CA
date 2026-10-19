package software_development_CA;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Queue;


public class CardDeck {
    // initalize, deck is a queue to ensure FIFO
    private Queue<Cards> deck = new LinkedList<Cards>();
    final private int deckNum;
    final private String filename;

    //constructor
    public CardDeck(int deckNum) {
        this.deckNum = deckNum;
        StringBuilder deck_file_builder = new StringBuilder();
        deck_file_builder.append("deck").append(deckNum).append("_output.txt");   
        this.filename= deck_file_builder.toString();
    }

    // getter methods
    public int getDeckNum() {
        return this.deckNum;
    }

    public Queue<Cards> getDeck() {
        return this.deck;
    }

    public void addDeck(Cards card) {
        synchronized (this.deck) {
            this.deck.add(card);
        }
    }

    public Cards popDeck() {
        synchronized (this.deck) {
            return this.deck.remove();
        }
    }

    // method to print the contents of the deck to a file
    public void finalOutput() {
        StringBuilder output = new StringBuilder();
        StringBuilder initial_text = new StringBuilder();
        initial_text.append("deck ").append(this.deckNum).append(" contents: ");
        try (PrintWriter file_write = new PrintWriter(filename)) {
            file_write.println(initial_text);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        for (Cards card : this.deck) {
            output.append(card.getDenomination()).append(" ");
        }
        try (PrintWriter file_write = new PrintWriter(new FileWriter(filename,true))) {
            file_write.println(output);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }
}
