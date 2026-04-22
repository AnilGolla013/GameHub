import java.util.*;

public class GuessANumber {

    private Scanner scanner;

    public GuessANumber() {
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║       🎯 GUESS THE NUMBER 🎯      ║");
        System.out.println("╚══════════════════════════════════╝");

        System.out.println("Choose difficulty:");
        System.out.println("  1. Easy   (1–50,  10 attempts)");
        System.out.println("  2. Medium (1–100,  7 attempts)");
        System.out.println("  3. Hard   (1–200,  5 attempts)");
        System.out.print("Select (1-3): ");

        int choice = getValidInput(1, 3);

        int maxNum, maxAttempts;
        String level;
        switch (choice) {
            case 1: maxNum = 50;  maxAttempts = 10; level = "Easy";   break;
            case 2: maxNum = 100; maxAttempts = 7;  level = "Medium"; break;
            default: maxNum = 200; maxAttempts = 5; level = "Hard";   break;
        }

        Random random = new Random();
        int totalScore = 0;
        int rounds = 3;

        for (int round = 1; round <= rounds; round++) {
            System.out.println("\n── Round " + round + " of " + rounds + " [" + level + "] ──");
            System.out.println("Guess a number between 1 and " + maxNum);
            System.out.println("You have " + maxAttempts + " attempts.\n");

            int secret = random.nextInt(maxNum) + 1;
            int roundScore = playRound(secret, maxNum, maxAttempts);
            totalScore += roundScore;
            System.out.println("Round score: +" + roundScore + " | Total: " + totalScore);
        }

        System.out.println("\n╔══════════════════════════════════╗");
        System.out.printf("║  Final Score: %-5d               ║%n", totalScore);
        System.out.println("╚══════════════════════════════════╝");

        if (totalScore >= 250) System.out.println("🏆 Master Guesser! Incredible!");
        else if (totalScore >= 150) System.out.println("🥈 Great instincts!");
        else if (totalScore >= 75) System.out.println("🥉 Decent guessing!");
        else System.out.println("🎲 Keep trying your luck!");
    }

    private int playRound(int secret, int maxNum, int maxAttempts) {
        int low = 1, high = maxNum;

        for (int attempt = 1; attempt <= maxAttempts; attempt++) {
            System.out.printf("Attempt %d/%d [Range: %d–%d]: ", attempt, maxAttempts, low, high);
            int guess = getValidInput(1, maxNum);

            if (guess == secret) {
                int points = (maxAttempts - attempt + 1) * 20;
                System.out.println("🎉 Correct! Found it in " + attempt + " attempt(s)! +" + points + " pts");
                return points;
            } else if (guess < secret) {
                low = Math.max(low, guess + 1);
                System.out.println("📈 Too LOW! Go higher.");
            } else {
                high = Math.min(high, guess - 1);
                System.out.println("📉 Too HIGH! Go lower.");
            }
        }

        System.out.println("😞 Out of attempts! The number was: " + secret);
        return 0;
    }

    private int getValidInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) return input;
                System.out.print("Enter between " + min + "–" + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid! Enter a number: ");
            }
        }
    }
}
