package software_development_CA;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.util.List;

public class testCardGame {
    @BeforeEach
    public void setUp() throws Exception{
        Player.hasWon = false;
        Field packField = CardGame.class.getDeclaredField("pack");
        packField.setAccessible(true);
        List<?> pack = (List<?>) packField.get(null);
        pack.clear();
    }

    @AfterEach
    public void tearDown() {
        for (int i=1; i <= 4;i++) {
            try {
                new File("player"+i+"_output.txt").delete();
                new File("deck"+i+"_output.txt").delete();    
            } catch (Exception e) {
            }
            
        }
    }

    @Test
    public void testDeckFIFO() {
        CardDeck deck = new CardDeck(1);
        Cards card1 = new Cards(1);
        Cards card2 = new Cards(2);
        deck.addDeck(card1);
        deck.addDeck(card2);
        assertEquals(1,deck.popDeck().getDenomination());
        assertEquals(2,deck.popDeck().getDenomination());
    }

    @Test
    public void testDeckFinalOutput() {
        CardDeck deck = new CardDeck(1);
        Cards card1 = new Cards(1);
        Cards card2 = new Cards(2);
        deck.finalOutput();
        File outputFile = new File("deck1_output.txt");
        assertTrue(outputFile.exists());
        String fileContent = Files.readString(outputFile.toPath());
        assertTrue(fileContent.contains("1 2"));
    }

    

}