import CardDeck.CardDeck;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int num;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private static List<Cards> hand = new ArrayList<>();
    private static Cards lastDraw;
    private static Cards lastDiscard;
    volatile boolean hasWon = false;


    public Player(int num, CardDeck drawDeck, CardDeck discardDeck) {
        this.num = num;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
    }

    private static void chooseCard() {
        for (int i = 0; i < this.hand.size(); i++) {
            if (this.hand.get(i).getDenomination() != this.num) {
                //implement lock
                this.lastDiscard = this.hand.get(i);
                this.hand.remove(i);
                break;
            }
        }
    }

    public static void drawCard(CardDeck drawDeck) {
                synchronized (drawDeck) {
                    this.hand.add(drawDeck.popDeck());
                    this.lastDraw = this.hand.get(this.hand.size() - 1);
                }
            }

    private static void writeOutput() {
        StringBuilder output = new StringBuilder();
        output.append("Player ").append(this.num);
        output.append(" draws ").append(this.lastDraw.getDenomination());
        output.append(" from deck ").append(this.drawDeck.getDeck().toString()); //give decks number
        System.out.println(output.toString().trim());
    }

}