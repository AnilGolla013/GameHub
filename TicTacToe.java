import java.util.*;

public class TicTacToe {

    private char[] board;
    private Scanner scanner;

    public TicTacToe() {
        this.scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║        ❌ TIC TAC TOE ⭕           ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Choose mode:");
        System.out.println("  1. Two Players (Human vs Human)");
        System.out.println("  2. vs CPU (Easy)");
        System.out.println("  3. vs CPU (Hard - Unbeatable AI)");
        System.out.print("Select (1-3): ");

        int mode = getValidInput(1, 3);

        int playerXWins = 0, playerOWins = 0, draws = 0;
        int gamesToPlay = 3;

        for (int game = 1; game <= gamesToPlay; game++) {
            System.out.println("\n═══ Game " + game + " of " + gamesToPlay + " ═══");
            initBoard();
            char result = playGame(mode);

            if (result == 'X') { playerXWins++; System.out.println("🏆 X wins this game!"); }
            else if (result == 'O') { playerOWins++; System.out.println("🏆 O wins this game!"); }
            else { draws++; System.out.println("🤝 It's a draw!"); }

            System.out.println("Score → X: " + playerXWins + "  O: " + playerOWins + "  Draws: " + draws);
        }

        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║           SERIES RESULT          ║");
        System.out.println("╠══════════════════════════════════╣");
        System.out.printf("║  X: %-2d | O: %-2d | Draws: %-2d       ║%n", playerXWins, playerOWins, draws);
        System.out.println("╚══════════════════════════════════╝");

        if (playerXWins > playerOWins) System.out.println("🥇 X wins the series!");
        else if (playerOWins > playerXWins) System.out.println("🥇 O wins the series!");
        else System.out.println("🤝 Series ends in a tie!");
    }

    private void initBoard() {
        board = new char[9];
        for (int i = 0; i < 9; i++) board[i] = (char) ('1' + i);
    }

    private char playGame(int mode) {
        char currentPlayer = 'X';

        while (true) {
            printBoard();
            int move;

            if (currentPlayer == 'O' && mode == 2) {
                move = getCpuMoveEasy();
                System.out.println("CPU plays: " + (move + 1));
            } else if (currentPlayer == 'O' && mode == 3) {
                move = getBestMove();
                System.out.println("CPU plays: " + (move + 1));
            } else {
                System.out.print("Player " + currentPlayer + ", enter position (1-9): ");
                move = getValidInput(1, 9) - 1;
                while (board[move] == 'X' || board[move] == 'O') {
                    System.out.print("Cell taken! Choose another (1-9): ");
                    move = getValidInput(1, 9) - 1;
                }
            }

            board[move] = currentPlayer;

            if (checkWin(currentPlayer)) { printBoard(); return currentPlayer; }
            if (isBoardFull()) { printBoard(); return 'D'; }

            currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
        }
    }

    private void printBoard() {
        System.out.println("\n " + board[0] + " │ " + board[1] + " │ " + board[2]);
        System.out.println("───┼───┼───");
        System.out.println(" " + board[3] + " │ " + board[4] + " │ " + board[5]);
        System.out.println("───┼───┼───");
        System.out.println(" " + board[6] + " │ " + board[7] + " │ " + board[8] + "\n");
    }

    private boolean checkWin(char p) {
        int[][] wins = {{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
        for (int[] w : wins) if (board[w[0]]==p && board[w[1]]==p && board[w[2]]==p) return true;
        return false;
    }

    private boolean isBoardFull() {
        for (char c : board) if (c != 'X' && c != 'O') return false;
        return true;
    }

    private int getCpuMoveEasy() {
        Random r = new Random();
        List<Integer> available = new ArrayList<>();
        for (int i = 0; i < 9; i++) if (board[i] != 'X' && board[i] != 'O') available.add(i);
        return available.get(r.nextInt(available.size()));
    }

    // Minimax AI for unbeatable hard mode
    private int getBestMove() {
        int bestScore = Integer.MIN_VALUE, bestMove = -1;
        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                char temp = board[i]; board[i] = 'O';
                int score = minimax(false, 0);
                board[i] = temp;
                if (score > bestScore) { bestScore = score; bestMove = i; }
            }
        }
        return bestMove;
    }

    private int minimax(boolean isMaximizing, int depth) {
        if (checkWin('O')) return 10 - depth;
        if (checkWin('X')) return depth - 10;
        if (isBoardFull()) return 0;

        int best = isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int i = 0; i < 9; i++) {
            if (board[i] != 'X' && board[i] != 'O') {
                char temp = board[i];
                board[i] = isMaximizing ? 'O' : 'X';
                int score = minimax(!isMaximizing, depth + 1);
                board[i] = temp;
                best = isMaximizing ? Math.max(best, score) : Math.min(best, score);
            }
        }
        return best;
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
