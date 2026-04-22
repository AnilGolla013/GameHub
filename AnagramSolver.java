import java.util.*;

public class AnagramSolver {
    private static final String[] WORD_LIST = {
        "listen", "silent", "enlist", "tinsel", "inlets",
        "hello", "world", "java", "games", "coding",
        "triangle", "integral", "relating", "alerting",
        "master", "stream", "tamers", "remast",
        "python", "garden", "danger", "gander",
        "stone", "notes", "tones", "onset",
        "earth", "heart", "hater", "rathe",
        "below", "elbow", "bowel",
        "night", "thing", "tiger", "gripe",
        "plate", "leapt", "petal", "pleat",
        "dusty", "study", "musty", "rusty",
        "angel", "angle", "glean", "lange"
    };

    private Scanner scanner;
    private int score;
    private int totalRounds;

    public AnagramSolver() {
        this.scanner = new Scanner(System.in);
        this.score = 0;
        this.totalRounds = 5;
    }

    public void play() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║       🔤 ANAGRAM SOLVER 🔤        ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Unscramble the letters to find the word!");
        System.out.println("You have 3 attempts per round.\n");

        Random random = new Random();
        Set<Integer> usedIndices = new HashSet<>();

        for (int round = 1; round <= totalRounds; round++) {
            System.out.println("── Round " + round + " of " + totalRounds + " ──");

            int idx;
            do { idx = random.nextInt(WORD_LIST.length); } while (usedIndices.contains(idx));
            usedIndices.add(idx);

            String word = WORD_LIST[idx];
            String scrambled = scrambleWord(word);

            System.out.println("Scrambled: " + scrambled.toUpperCase());

            boolean solved = false;
            for (int attempt = 1; attempt <= 3; attempt++) {
                System.out.print("Attempt " + attempt + "/3 → ");
                String guess = scanner.nextLine().trim().toLowerCase();

                if (guess.equals(word)) {
                    int points = (4 - attempt) * 10;
                    score += points;
                    System.out.println("✅ Correct! +" + points + " points (Score: " + score + ")\n");
                    solved = true;
                    break;
                } else if (isAnagram(guess, word)) {
                    System.out.println("⚡ Valid anagram, but not the target word. Try again!");
                } else {
                    System.out.println("❌ Wrong! Try again.");
                }
            }

            if (!solved) {
                System.out.println("💡 The word was: " + word.toUpperCase() + "\n");
            }
        }

        System.out.println("╔══════════════════════════════════╗");
        System.out.printf("║  Final Score: %-5d / 150         ║%n", score);
        System.out.println("╚══════════════════════════════════╝");

        if (score >= 120) System.out.println("🏆 Outstanding! You're a wordsmith!");
        else if (score >= 80) System.out.println("🥈 Great job! Well done!");
        else if (score >= 40) System.out.println("🥉 Good effort! Keep practicing!");
        else System.out.println("📚 Keep playing to improve!");
    }

    private String scrambleWord(String word) {
        List<Character> chars = new ArrayList<>();
        for (char c : word.toCharArray()) chars.add(c);
        String scrambled;
        do {
            Collections.shuffle(chars);
            StringBuilder sb = new StringBuilder();
            for (char c : chars) sb.append(c);
            scrambled = sb.toString();
        } while (scrambled.equals(word));
        return scrambled;
    }

    private boolean isAnagram(String a, String b) {
        if (a.length() != b.length()) return false;
        char[] ca = a.toCharArray(), cb = b.toCharArray();
        Arrays.sort(ca); Arrays.sort(cb);
        return Arrays.equals(ca, cb);
    }
}
