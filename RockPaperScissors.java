import java.util.*;

public class RockPaperScissors {

    private enum Move {
        ROCK("🪨 Rock"), PAPER("📄 Paper"), SCISSORS("✂️ Scissors");

        final String display;
        Move(String display) { this.display = display; }

        boolean beats(Move other) {
            return (this == ROCK && other == SCISSORS) ||
                   (this == PAPER && other == ROCK) ||
                   (this == SCISSORS && other == PAPER);
        }
    }

    private Scanner scanner;
    private int playerWins, cpuWins, draws;

    public RockPaperScissors() {
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║    🪨📄✂️  ROCK PAPER SCISSORS     ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Choose number of rounds:");
        System.out.println("  1. Best of 3   2. Best of 5   3. Best of 7");
        System.out.print("Select (1-3): ");

        int choice = getValidInput(1, 3);
        int totalRounds = choice == 1 ? 3 : choice == 2 ? 5 : 7;
        int winsNeeded = totalRounds / 2 + 1;

        System.out.println("\nBest of " + totalRounds + "! First to " + winsNeeded + " wins!\n");

        Random random = new Random();
        Move[] moves = Move.values();

        int round = 0;
        while (playerWins < winsNeeded && cpuWins < winsNeeded) {
            round++;
            System.out.println("── Round " + round + " ──  [You: " + playerWins + " | CPU: " + cpuWins + "]");
            System.out.println("  1. 🪨 Rock   2. 📄 Paper   3. ✂️  Scissors");
            System.out.print("Your move (1-3): ");

            int moveChoice = getValidInput(1, 3);
            Move playerMove = moves[moveChoice - 1];
            Move cpuMove = moves[random.nextInt(3)];

            System.out.println("You chose:  " + playerMove.display);
            System.out.println("CPU chose:  " + cpuMove.display);

            if (playerMove == cpuMove) {
                draws++;
                System.out.println("🤝 It's a DRAW!");
            } else if (playerMove.beats(cpuMove)) {
                playerWins++;
                System.out.println("🎉 You WIN this round!");
            } else {
                cpuWins++;
                System.out.println("😔 CPU wins this round!");
            }
            System.out.println();
        }

        System.out.println("╔══════════════════════════════════╗");
        System.out.println("║          MATCH RESULT            ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf("║  You: %-3d | CPU: %-3d | Draws: %-2d ║%n", playerWins, cpuWins, draws);
        System.out.println("╚══════════════════════════════════╝");

        if (playerWins > cpuWins) System.out.println("🏆 YOU WIN THE MATCH! Great game!");
        else if (cpuWins > playerWins) System.out.println("🤖 CPU wins the match. Better luck next time!");
        else System.out.println("🤝 It's an overall DRAW!");
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
