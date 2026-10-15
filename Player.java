import CardDeck.CardDeck;
import java.util.ArrayList;
import java.util.List;

public class Player {
    private final int num;
    private final CardDeck drawDeck;
    private final CardDeck discardDeck;
    private static List<Cards> hand = new ArrayList<>();
    volatile boolean hasWon = false;


    public Player(int num, CardDeck drawDeck, CardDeck discardDeck) {
        this.num = num;
        this.drawDeck = drawDeck;
        this.discardDeck = discardDeck;
    }

    private static void chooseCard() {
        for (int i = 0; i < hand.size(); i++) {
            if (hand.get(i).getDenomination() != num) {
                //implement lock
                hand.get(i);
                hand.remove(i);
                break;
            }
        }
    }

    public static void drawCard(CardDeck drawDeck) {
                synchronized (drawDeck) {
                    hand.add(drawDeck.popDeck());
                }
            }

    

}