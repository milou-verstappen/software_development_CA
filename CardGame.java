import java.util.ArrayList;
import java.util.List;

import Cards.Cards;
import java.io.File;                  
import java.io.FileNotFoundException; 
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
                String card = myReader.nextLine();

                if (int card <= 0 || int card > numPlayers) {
                    System.out.println("Invalid card denomination: " + card);
                    pack.clear();
                    return false;
                }

                pack.add(new Cards(card));
            }
        }

        // Catch the FileNotFoundException and print an error message
        catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        // Check if pack size is valid
        if pack.size() != 8*numPlayers {
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
        scanner.close();
    }

        System.out.println("Enter pack textfile: ");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.next();
        boolean packbool = false;
        packbool = readPack(filename);
        while (!scanner.hasNext() | !packbool) {
            System.out.println("Invalid input. Please enter a valid filename.");
            scanner.next(); // Clear the invalid input
            packbool = readPack(filename);
        }
        scanner.close();
        
    }


}