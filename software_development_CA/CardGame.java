package software_development_CA;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CardGame {
    private static int numPlayers;
    private static List<Cards> pack = new ArrayList<>();

    // Method to read the pack of cards from a file
    private static boolean readPack(String filename) {

        File file = new File(filename);

        // Check if the file exists
        if (!file.exists()) {
            System.out.println("File does not exist");
            return false;
        }

        // Read the file and add each card to the pack
        try (Scanner myReader = new Scanner(file)) {
            while (myReader.hasNextLine()) {
                int card = Integer.parseInt(myReader.nextLine()); //parseInt can produce an error to,account for that

                if (card <= 0 || card > numPlayers) {
                    System.out.println("Invalid card denomination: " + card);
                    pack.clear();
                    return false;
                }

                pack.add(new Cards(card));
            }
        }

        // Catch the FileNotFoundException and print an error message
        catch (FileNotFoundException e) {
            //more should be added here
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Check if pack size is valid
        if (pack.size() != 8*numPlayers) {
            System.out.println("Invalid number of cards in the pack. Expected " + (8*numPlayers) + " but found " + pack.size());
            pack.clear();
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        System.out.println("Enter the number of players: ");
        Scanner scanner = new Scanner(System.in);
        while (!scanner.hasNextInt()) {
            System.out.println("Invalid input. Please enter an integer.");
            scanner.next(); // Clear the invalid input
        }
        numPlayers = scanner.nextInt();
        
        scanner.nextLine();

        System.out.println("Enter pack textfile: ");
        
        String filename = scanner.nextLine();
        boolean packbool = false;
        packbool = readPack(filename);
        while (!packbool) {
            System.out.println("Invalid input. Please enter a valid filename.");
            scanner.nextLine(); // Clear the invalid input
            packbool = readPack(filename);
        }
        scanner.close();

        List<CardDeck> decks = new ArrayList<>();
        List<Player> players = new ArrayList<>();
        for (int i =1; i <= numPlayers; i++) {
            decks.add(new CardDeck(i));
        }
        int packInd = 0;
        for (int i=1; i <= numPlayers;i++) {
            if (i==numPlayers) {
                players.add(new Player(i, decks.get(i-1), decks.get(0)));
                players.get(i-1).initialHand(pack.get(packInd),pack.get(packInd+1),pack.get(packInd+2),pack.get(packInd+3));
                packInd+=4;
            }
            else {
                players.add(new Player(i, decks.get(i-1), decks.get(i)));
                players.get(i-1).initialHand(pack.get(packInd),pack.get(packInd+1),pack.get(packInd+2),pack.get(packInd+3));
                packInd += 4;
        }
        }
        
        int deckSelection = 0;
        while (packInd < pack.size()){
            decks.get(deckSelection%numPlayers).addDeck(pack.get(packInd));
            deckSelection++;
            packInd++;
        }

        List<Thread> playerThreads = new ArrayList<>();
        for (Player player : players) {
            Thread playerThread = new Thread(player);
            playerThreads.add(playerThread);
            playerThread.start();
        }    
        
        while (!Player.getHasWon()) {
                ;
            }

        for (Thread playerThread : playerThreads) {
            try {
                playerThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        for (CardDeck deck : decks) {
           deck.finalOutput();
        }

    }
}