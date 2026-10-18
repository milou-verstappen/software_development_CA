package software_development_CA;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int num;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private List<Cards> hand = new ArrayList<>();
    private Cards lastDraw;
    private Cards lastDiscard;
    volatile boolean hasWon = false;
    private final String filename;

    public Player(int num, CardDeck drawDeck, CardDeck discardDeck) {
        this.num = num;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
        StringBuilder filename_builder = new StringBuilder();
        filename_builder.append("player").append(this.num).append("_output.txt");
        this.filename = filename_builder.toString();
    }

    public void playerTurn() {
        synchronized (this.drawDeck) {
            synchronized (this.discardDeck) {
                //draw
                this.hand.add(drawDeck.popDeck());
                this.lastDraw = this.hand.get(this.hand.size() - 1);

                //discard
                for (int i = 0; i < this.hand.size(); i++) {
                    if (this.hand.get(i).getDenomination() != this.num) {
                        this.lastDiscard = this.hand.get(i);
                        this.hand.remove(i);
                        this.discardDeck.addDeck(this.lastDiscard);
                        break;
            }
        }
            }
        }
    }

    private void writeOutput() {
        StringBuilder output = new StringBuilder();
        output.append("Player ").append(this.num);
        output.append(" draws ").append(this.lastDraw.getDenomination());
        output.append(" from deck ").append(this.drawDeck.getDeckNum()).append("\n");
        //more lines to the string 
        output.append("PLayer").append(this.num);
        output.append("discards a").append(this.lastDiscard).append("to deck").append(this.discardDeck.getDeckNum());
        output.append("Player ").append(this.num).append("current hand is ").append(this.hand.get(0).getDenomination()).append(this.hand.get(1).getDenomination()).append(this.hand.get(2).getDenomination()).append(this.hand.get(3).getDenomination());
        
        try (PrintWriter file_write = new PrintWriter(filename)) {
            file_write.println(output);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

}