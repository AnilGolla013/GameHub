import java.util.*;

public class GKQuiz {

    private static class Question {
        String question;
        String[] options;
        int correctIndex;
        String category;

        Question(String question, String[] options, int correctIndex, String category) {
            this.question = question;
            this.options = options;
            this.correctIndex = correctIndex;
            this.category = category;
        }
    }

    private static final List<Question> QUESTION_BANK = new ArrayList<>(Arrays.asList(
        new Question("What is the capital of Australia?",
            new String[]{"Sydney", "Melbourne", "Canberra", "Brisbane"}, 2, "Geography"),
        new Question("Which planet is known as the Red Planet?",
            new String[]{"Venus", "Mars", "Jupiter", "Saturn"}, 1, "Science"),
        new Question("Who painted the Mona Lisa?",
            new String[]{"Michelangelo", "Van Gogh", "Leonardo da Vinci", "Picasso"}, 2, "Art"),
        new Question("What is the chemical symbol for Gold?",
            new String[]{"Gd", "Go", "Au", "Ag"}, 2, "Science"),
        new Question("In which year did World War II end?",
            new String[]{"1943", "1944", "1945", "1946"}, 2, "History"),
        new Question("Which is the largest ocean on Earth?",
            new String[]{"Atlantic", "Indian", "Arctic", "Pacific"}, 3, "Geography"),
        new Question("Who wrote 'Romeo and Juliet'?",
            new String[]{"Charles Dickens", "William Shakespeare", "Jane Austen", "Mark Twain"}, 1, "Literature"),
        new Question("What is the speed of light (approx)?",
            new String[]{"3×10⁸ m/s", "3×10⁶ m/s", "3×10⁴ m/s", "3×10¹⁰ m/s"}, 0, "Science"),
        new Question("Which country has the largest population?",
            new String[]{"USA", "China", "India", "Russia"}, 2, "Geography"),
        new Question("What is H₂O commonly known as?",
            new String[]{"Hydrogen", "Oxygen", "Water", "Salt"}, 2, "Science"),
        new Question("Which metal is liquid at room temperature?",
            new String[]{"Lead", "Mercury", "Silver", "Tin"}, 1, "Science"),
        new Question("The Great Wall of China was built primarily to protect against whom?",
            new String[]{"Romans", "Mongols", "Japanese", "Persians"}, 1, "History"),
        new Question("What is the longest river in the world?",
            new String[]{"Amazon", "Yangtze", "Mississippi", "Nile"}, 3, "Geography"),
        new Question("Which element has the atomic number 1?",
            new String[]{"Helium", "Hydrogen", "Oxygen", "Carbon"}, 1, "Science"),
        new Question("Who is known as the Father of Computers?",
            new String[]{"Alan Turing", "Charles Babbage", "Bill Gates", "John von Neumann"}, 1, "Technology")
    ));

    private Scanner scanner;
    private int score;
    private int totalQuestions;

    public GKQuiz() {
        this.scanner = new Scanner(System.in);
        this.score = 0;
        this.totalQuestions = 10;
    }

    public void play() {
        System.out.println("\n╔══════════════════════════════════╗");
        System.out.println("║       🧠 GENERAL KNOWLEDGE QUIZ   ║");
        System.out.println("╚══════════════════════════════════╝");
        System.out.println("Answer " + totalQuestions + " multiple choice questions.");
        System.out.println("Each correct answer = 10 points.\n");

        Collections.shuffle(QUESTION_BANK);
        int asked = Math.min(totalQuestions, QUESTION_BANK.size());

        for (int i = 0; i < asked; i++) {
            Question q = QUESTION_BANK.get(i);
            System.out.println("─────────────────────────────────");
            System.out.printf("Q%d [%s]: %s%n", i + 1, q.category, q.question);

            for (int j = 0; j < q.options.length; j++) {
                System.out.printf("  %d. %s%n", j + 1, q.options[j]);
            }

            System.out.print("Your answer (1-4): ");
            int answer = getValidInput(1, 4);

            if (answer - 1 == q.correctIndex) {
                score += 10;
                System.out.println("✅ Correct! (Score: " + score + ")");
            } else {
                System.out.println("❌ Wrong! Answer: " + q.options[q.correctIndex]);
            }
            System.out.println();
        }

        System.out.println("╔══════════════════════════════════╗");
        System.out.printf("║  Final Score: %-5d / %-5d        ║%n", score, asked * 10);
        System.out.println("╚══════════════════════════════════╝");

        double percent = (double) score / (asked * 10) * 100;
        if (percent == 100) System.out.println("🏆 Perfect Score! Genius!");
        else if (percent >= 80) System.out.println("🥈 Excellent! Very knowledgeable!");
        else if (percent >= 60) System.out.println("🥉 Good job! Above average!");
        else if (percent >= 40) System.out.println("📖 Keep learning, you'll get there!");
        else System.out.println("📚 Brush up on your general knowledge!");
    }

    private int getValidInput(int min, int max) {
        while (true) {
            try {
                int input = Integer.parseInt(scanner.nextLine().trim());
                if (input >= min && input <= max) return input;
                System.out.print("Enter a number between " + min + " and " + max + ": ");
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Enter a number: ");
            }
        }
    }
}
