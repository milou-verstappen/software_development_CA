package software_development_CA;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

// runnable class for each player
public class Player implements Runnable{
    private final int num;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private List<Cards> hand = new ArrayList<>();
    private Cards lastDraw;
    private Cards lastDiscard;
    volatile static boolean hasWon = false;
    private final String filename;
    private static int winner;

    //constructor
    public Player(int num, CardDeck drawDeck, CardDeck discardDeck) {
        this.num = num;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
        StringBuilder filename_builder = new StringBuilder();
        filename_builder.append("player").append(this.num).append("_output.txt");
        this.filename = filename_builder.toString();
    }

    // initialize hand with 4 cards and write it to a file
    public void initialHand(Cards card1,Cards card2,Cards card3,Cards card4) {
        this.hand.add(card1);
        this.hand.add(card2);
        this.hand.add(card3);
        this.hand.add(card4);
        StringBuilder output = new StringBuilder();
        output.append("Initial hand:");
        output.append(this.hand.get(0).getDenomination()).append(" ");
        output.append(this.hand.get(1).getDenomination()).append(" ");
        output.append(this.hand.get(2).getDenomination()).append(" ");
        output.append(this.hand.get(3).getDenomination()).append("\n");
        
        try (PrintWriter file_write = new PrintWriter(filename)) {
            file_write.println(output);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    public static boolean getHasWon() {
        return hasWon;
    }

    //synchronize draw and discard to avoid race conditions
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
        output.append("PLayer ").append(this.num);
        output.append(" discards a ").append(this.lastDiscard.getDenomination()).append(" to deck ").append(this.discardDeck.getDeckNum()).append("\n");
        output.append("Player ").append(this.num).append(" current hand is ");
        output.append(this.hand.get(0).getDenomination()).append(" ");
        output.append(this.hand.get(1).getDenomination()).append(" ");
        output.append(this.hand.get(2).getDenomination()).append(" ");
        output.append(this.hand.get(3).getDenomination()).append("\n");
        try (PrintWriter file_write = new PrintWriter(new FileWriter(filename,true))) {
            file_write.println(output);
        }
        catch (IOException e) {
            System.out.println(e);
        }
    }

    @Override
    public void run() {
        boolean checkWon = false;
        while (!hasWon){
            int lastcard = this.hand.get(0).getDenomination();
            for (Cards card : this.hand) {
                if (card.getDenomination() == lastcard) {
                    checkWon = true;
                    lastcard = card.getDenomination();
                }
                else {
                    checkWon = false;
                    break;
                }
            }
            if (checkWon) {
                hasWon = true;
                winner = this.num;
                System.out.println("Player " + this.num + " wins");
                break;
            }
            playerTurn();
            writeOutput();
        }
        //final output
        StringBuilder output = new StringBuilder();
        if (winner == this.num){
            output.append("Player ").append(this.num).append(" wins\n");
            
        }
        else {
            output.append("Player ").append(winner).append(" has informed Player ").append(this.num).append(" that they have won\n");
        }
        output.append("Player ").append(this.num).append(" exits\n");
        output.append("Player ").append(this.num).append(" hand:");
        output.append(this.hand.get(0).getDenomination()).append(" ");
        output.append(this.hand.get(1).getDenomination()).append(" ");
        output.append(this.hand.get(2).getDenomination()).append(" ");
        output.append(this.hand.get(3).getDenomination());
        try (PrintWriter file_write = new PrintWriter(new FileWriter(filename,true))) {
            file_write.println(output);
        }
        catch (IOException e) {
            System.out.println(e);
        }
        
    }
}