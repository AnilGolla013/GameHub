import java.util.*;

public class HangmanGame {

    private static final String[][] WORDS_BY_CATEGORY = {
        {"PROGRAMMING", "JAVA", "PYTHON", "ALGORITHM", "COMPILER", "VARIABLE", "FUNCTION", "RECURSION"},
        {"GEOGRAPHY",   "HIMALAYA", "AMAZON", "PACIFIC", "SAVANNA", "PLATEAU", "VOLCANO", "GLACIER"},
        {"ANIMALS",     "ELEPHANT", "GIRAFFE", "PENGUIN", "CROCODILE", "CHEETAH", "PLATYPUS", "WOLVERINE"},
        {"SPORTS",      "CRICKET", "BADMINTON", "WRESTLING", "ARCHERY", "SWIMMING", "GYMNASTICS", "FENCING"}
    };

    private static final String[] HANGMAN_STAGES = {
        """
          +---+
          |   |
              |
              |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
              |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
          |   |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|   |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|\\  |
              |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|\\  |
         /    |
              |
        =========""",
        """
          +---+
          |   |
          O   |
         /|\\  |
         / \\  |
              |
        ========="""
    };

    private Scanner scanner;

    public HangmanGame() {
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║        🪢  HANGMAN GAME  🪢        ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Choose a category:");
        for (int i = 0; i < WORDS_BY_CATEGORY.length; i++) {
            System.out.println("  " + (i + 1) + ". " + WORDS_BY_CATEGORY[i][0]);
        }
        System.out.print("Select (1-" + WORDS_BY_CATEGORY.length + "): ");
        int catChoice = getValidInput(1, WORDS_BY_CATEGORY.length) - 1;

        String[] category = WORDS_BY_CATEGORY[catChoice];
        String categoryName = category[0];
        Random random = new Random();
        String word = category[1 + random.nextInt(category.length - 1)];

        playRound(word, categoryName);
    }

    private void playRound(String word, String category) {
        Set<Character> guessed = new LinkedHashSet<>();
        int wrongGuesses = 0;
        int maxWrong = 6;
        boolean won = false;

        System.out.println("\nCategory: " + category);
        System.out.println("Word has " + word.length() + " letters. You have " + maxWrong + " lives.\n");

        while (wrongGuesses < maxWrong) {
            System.out.println(HANGMAN_STAGES[wrongGuesses]);
            System.out.println("\nWord: " + buildDisplay(word, guessed));
            System.out.println("Guessed: " + formatGuessed(guessed));
            System.out.println("Lives left: " + (maxWrong - wrongGuesses) + " / " + maxWrong);

            if (isWordSolved(word, guessed)) { won = true; break; }

            System.out.print("Guess a letter: ");
            char letter = getLetterInput();

            if (guessed.contains(letter)) {
                System.out.println("⚠️  Already guessed '" + letter + "'!");
                continue;
            }

            guessed.add(letter);

            if (word.indexOf(letter) >= 0) {
                System.out.println("✅ '" + letter + "' is in the word!");
            } else {
                wrongGuesses++;
                System.out.println("❌ '" + letter + "' is NOT in the word. (-1 life)");
            }
        }

        System.out.println(HANGMAN_STAGES[wrongGuesses]);

        if (won || isWordSolved(word, guessed)) {
            System.out.println("\n🎉 You saved the man! The word was: " + word);
        } else {
            System.out.println("\n💀 Game Over! The word was: " + word);
        }
    }

    private String buildDisplay(String word, Set<Character> guessed) {
        StringBuilder sb = new StringBuilder();
        for (char c : word.toCharArray()) {
            sb.append(guessed.contains(c) ? c : '_');
            sb.append(' ');
        }
        return sb.toString().trim();
    }

    private boolean isWordSolved(String word, Set<Character> guessed) {
        for (char c : word.toCharArray()) if (!guessed.contains(c)) return false;
        return true;
    }

    private String formatGuessed(Set<Character> guessed) {
        if (guessed.isEmpty()) return "(none)";
        StringBuilder sb = new StringBuilder();
        for (char c : guessed) sb.append(c).append(' ');
        return sb.toString().trim();
    }

    private char getLetterInput() {
        while (true) {
            String input = scanner.nextLine().trim().toUpperCase();
            if (input.length() == 1 && Character.isLetter(input.charAt(0))) return input.charAt(0);
            System.out.print("Enter a single letter: ");
        }
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
