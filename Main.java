import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * ============================================================
 *  Main.java
 *  Entry point of the Student Grade Tracker application.
 *
 *  Responsibilities:
 *    - Display a menu-driven console interface
 *    - Read and validate user input
 *    - Delegate all data operations to GradeTracker
 *
 *  Flow:
 *    main() → showMenu() loop → handleChoice() → GradeTracker
 * ============================================================
 */
public class Main {

    // ── Shared objects ─────────────────────────────────────────
    // One Scanner for the entire run; closing it too early
    // would also close System.in and break further input.
    private static final Scanner scanner     = new Scanner(System.in);
    private static final GradeTracker tracker = new GradeTracker();

    // ── Entry point ────────────────────────────────────────────
    public static void main(String[] args) {
        printBanner();

        boolean running = true;

        while (running) {
            printMenu();
            int choice = readMenuChoice();      // safe integer read
            running = handleChoice(choice);     // returns false on exit
        }

        // Cleanup
        scanner.close();
        System.out.println("\n  Thank you for using Student Grade Tracker. Goodbye!\n");
    }

    // ── Menu display ───────────────────────────────────────────

    /** Prints the welcome banner once at startup. */
    private static void printBanner() {
        System.out.println();
        System.out.println("  ╔══════════════════════════════════════════╗");
        System.out.println("  ║      STUDENT GRADE TRACKER  v1.0        ║");
        System.out.println("  ║    Track • Analyse • Improve Grades     ║");
        System.out.println("  ╚══════════════════════════════════════════╝");
        System.out.println();
    }

    /** Prints the main menu options before each user prompt. */
    private static void printMenu() {
        System.out.println("\n  ┌─────────────────────────────────────┐");
        System.out.println("  │              MAIN MENU              │");
        System.out.println("  ├─────────────────────────────────────┤");
        System.out.println("  │  1. Add a Student                   │");
        System.out.println("  │  2. View All Students               │");
        System.out.println("  │  3. View Statistics                 │");
        System.out.println("  │  4. View Summary Report             │");
        System.out.println("  │  5. Search Student by Name          │");
        System.out.println("  │  6. View Ranked List                │");
        System.out.println("  │  7. Exit                            │");
        System.out.println("  └─────────────────────────────────────┘");
        System.out.print("  Enter your choice (1-7): ");
    }

    // ── Input handling ─────────────────────────────────────────

    /**
     * Reads the user's menu choice safely.
     * If the user types a non-integer, returns -1 so the
     * switch-default branch can display an error message.
     *
     * @return integer choice, or -1 on invalid input
     */
    private static int readMenuChoice() {
        try {
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume leftover newline
            return choice;
        } catch (InputMismatchException e) {
            scanner.nextLine(); // discard bad input
            return -1;          // signals invalid input
        }
    }

    /**
     * Routes the user's menu choice to the correct action.
     *
     * @param choice integer from 1–7
     * @return true  → keep the menu loop running
     *         false → exit the application
     */
    private static boolean handleChoice(int choice) {
        System.out.println(); // blank line for readability

        switch (choice) {

            case 1:
                addStudent();
                break;

            case 2:
                System.out.println("  ── All Students ──────────────────────────────────────────────────────");
                tracker.displayAllStudents();
                break;

            case 3:
                displayStatistics();
                break;

            case 4:
                tracker.displaySummaryReport();
                break;

            case 5:
                searchStudent();
                break;

            case 6:
                tracker.displayRankedList();
                break;

            case 7:
                return false; // exit signal

            default:
                System.out.println("  [!] Invalid choice. Please enter a number between 1 and 7.");
        }

        return true; // keep running
    }

    // ── Feature handlers ───────────────────────────────────────

    /**
     * Prompts the user for a student name and grade,
     * validates both inputs, then delegates to GradeTracker.
     *
     * Validation rules:
     *   - Name  : must not be blank
     *   - Grade : must be a number between 0.0 and 100.0
     */
    private static void addStudent() {
        System.out.println("  ── Add New Student ───────────────────────────────────────────────────");

        // ── Read and validate name ─────────────────────────────
        String name = "";
        while (name.trim().isEmpty()) {
            System.out.print("  Enter student name : ");
            name = scanner.nextLine();
            if (name.trim().isEmpty()) {
                System.out.println("  [!] Name cannot be empty. Please try again.");
            }
        }

        // ── Read and validate grade ────────────────────────────
        double grade = -1;
        while (grade < 0 || grade > 100) {
            System.out.print("  Enter grade (0 – 100) : ");
            try {
                grade = Double.parseDouble(scanner.nextLine().trim());
                if (grade < 0 || grade > 100) {
                    System.out.println("  [!] Grade must be between 0 and 100.");
                }
            } catch (NumberFormatException e) {
                // User typed something that isn't a number
                System.out.println("  [!] Invalid input. Please enter a numeric value (e.g. 85.5).");
                grade = -1; // reset so the loop continues
            }
        }

        tracker.addStudent(name.trim(), grade);
    }

    /**
     * Displays class statistics: average, highest, and lowest.
     * Shows a friendly message when no students have been added.
     */
    private static void displayStatistics() {
        if (tracker.getStudentCount() == 0) {
            System.out.println("  [!] No students found. Please add students first.");
            return;
        }

        System.out.println("  ── Class Statistics ──────────────────────────────────────────────────");
        System.out.println("  " + "─".repeat(56));
        System.out.printf("  Total Students  : %d%n", tracker.getStudentCount());
        System.out.printf("  Average Score   : %.2f%n", tracker.calculateAverage());

        // Highest
        var top = tracker.getTopStudent();
        System.out.printf("  Highest Score   : %.2f  (by %s)%n",
                top.getGrade(), top.getName());

        // Lowest
        var bottom = tracker.getBottomStudent();
        System.out.printf("  Lowest Score    : %.2f  (by %s)%n",
                bottom.getGrade(), bottom.getName());

        System.out.println("  " + "─".repeat(56));
    }

    /**
     * Prompts the user for a search keyword and calls
     * GradeTracker.searchStudent() to display matches.
     */
    private static void searchStudent() {
        System.out.println("  ── Search Student ────────────────────────────────────────────────────");
        System.out.print("  Enter name or keyword to search : ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("  [!] Search keyword cannot be empty.");
            return;
        }

        tracker.searchStudent(keyword);
    }
}
