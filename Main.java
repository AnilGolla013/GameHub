import java.util.*;

public class Main {

    interface Game {
        String getName();
        String getDescription();
        String getEmoji();
        void launch();
    }

    
    static class AnagramSolverGame implements Game {
        public String getName()        { return "Anagram Solver"; }
        public String getDescription() { return "Unscramble letters to find hidden words"; }
        public String getEmoji()       { return "🔤"; }
        public void launch()           { new AnagramSolver().play(); }
    }

    static class GKQuizGame implements Game {
        public String getName()        { return "GK Quiz"; }
        public String getDescription() { return "Test your General Knowledge across topics"; }
        public String getEmoji()       { return "🧠"; }
        public void launch()           { new GKQuiz().play(); }
    }

    static class GuessNumberGame implements Game {
        public String getName()        { return "Guess a Number"; }
        public String getDescription() { return "Find the secret number with binary search hints"; }
        public String getEmoji()       { return "🎯"; }
        public void launch()           { new GuessANumber().play(); }
    }

    static class HangmanGameWrapper implements Game {
        public String getName()        { return "Hangman"; }
        public String getDescription() { return "Save the man by guessing the hidden word"; }
        public String getEmoji()       { return "🪢"; }
        public void launch()           { new HangmanGame().play(); }
    }

    static class RPSGame implements Game {
        public String getName()        { return "Rock Paper Scissors"; }
        public String getDescription() { return "Classic hand game – best of 3, 5, or 7 rounds"; }
        public String getEmoji()       { return "🪨"; }
        public void launch()           { new RockPaperScissors().play(); }
    }

    static class TicTacToeGame implements Game {
        public String getName()        { return "Tic Tac Toe"; }
        public String getDescription() { return "Grid game with Easy/Hard (Minimax AI) CPU"; }
        public String getEmoji()       { return "❌"; }
        public void launch()           { new TicTacToe().play(); }
    }

    // ── Hub controller ─────────────────────────────────────────────
    private final List<Game> games;
    private final Scanner scanner;
    private final Map<String, Integer> playCount;

    public Main() {
        this.scanner = new Scanner(System.in);
        this.playCount = new LinkedHashMap<>();

        // Register all games (easily extendable)
        this.games = new ArrayList<>(Arrays.asList(
            new AnagramSolverGame(),
            new GKQuizGame(),
            new GuessNumberGame(),
            new HangmanGameWrapper(),
            new RPSGame(),
            new TicTacToeGame()
        ));

        for (Game g : games) playCount.put(g.getName(), 0);
    }

    public void run() {
        boolean running = true;

        while (running) {
            printMenu();
            System.out.print("Enter your choice: ");

            String input = scanner.nextLine().trim();

            if (input.equalsIgnoreCase("q") || input.equals("0")) {
                running = false;
            } else if (input.equalsIgnoreCase("s")) {
                printStats();
            } else {
                try {
                    int choice = Integer.parseInt(input);
                    if (choice >= 1 && choice <= games.size()) {
                        Game selected = games.get(choice - 1);
                        playCount.merge(selected.getName(), 1, Integer::sum);
                        selected.launch();
                        System.out.println("\nPress ENTER to return to the Hub...");
                        scanner.nextLine();
                    } else {
                        System.out.println("⚠️  Invalid choice. Enter 1–" + games.size() + ", S for stats, or 0 to quit.");
                    }
                } catch (NumberFormatException e) {
                    System.out.println("⚠️  Invalid input. Enter a number, S, or 0.");
                }
            }
        }

        printFarewell();
    }

    private void printMenu() {
        System.out.println("  ┌─────────────────────────────────────────────────┐");
        System.out.println("  │                      GAME MENU                  │");
        System.out.println("  ├────┬────────────────────────────────────────────┤");

        for (int i = 0; i < games.size(); i++) {
            Game g = games.get(i);
            int plays = playCount.get(g.getName());
            String playStr = plays > 0 ? "  [played " + plays + "x]" : "";
            System.out.printf("  │ %-2d │ %-39s│%n", i + 1, g.getName() + playStr);
        }

        System.out.println("  ├────┴────────────────────────────────────────────┤");
        System.out.println("  │  S  │  View Stats                             │");
        System.out.println("  │  0  │  Quit                                   │");
        System.out.println("  └─────────────────────────────────────────────────┘");
    }

    private void printStats() {
        System.out.println("\n  📊 SESSION STATS");
        System.out.println("  ─────────────────────────────────");
        int total = 0;
        for (Game g : games) {
            int count = playCount.get(g.getName());
            total += count;
            System.out.printf("  %-30s %d time(s)%n", g.getName(), count);
        }
        System.out.println("  ─────────────────────────────────");
        System.out.println("  Total games played: " + total);
        System.out.println();
    }

    private void printFarewell() {echo "# GameHub" >> README.md

        int total = playCount.values().stream().mapToInt(Integer::intValue).sum();
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════════════╗");
        System.out.println("  ║          Thanks for playing! Come back soon!     ║");
        System.out.printf( "  ║          You played %2d game(s) this session.     ║%n", total);
        System.out.println("  ╚══════════════════════════════════════════════════╝");
        System.out.println();
    }

    // ── Entry point ────────────────────────────────────────────────
    public static void main(String[] args) {
        new Main().run();
    }
}
