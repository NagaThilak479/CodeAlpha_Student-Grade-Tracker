import java.util.ArrayList;

/**
 * ============================================================
 *  GradeTracker.java
 *  Manages the list of students and all grade-related
 *  calculations (average, highest, lowest, summary).
 *
 *  Separation of concerns: business logic lives here,
 *  user interaction lives in Main.java.
 * ============================================================
 */
public class GradeTracker {

    // ── Internal storage ───────────────────────────────────────
    // ArrayList grows automatically; no fixed size needed.
    private ArrayList<Student> students;

    // ── Constructor ────────────────────────────────────────────
    /** Initialises an empty student list. */
    public GradeTracker() {
        students = new ArrayList<>();
    }

    // ── CRUD operations ────────────────────────────────────────

    /**
     * Adds a new student to the tracker.
     *
     * @param name  student name
     * @param grade numeric grade (0 – 100)
     */
    public void addStudent(String name, double grade) {
        students.add(new Student(name, grade));
        System.out.println("\n  ✔  Student \"" + name + "\" added successfully!");
    }

    /**
     * Returns a copy of the student list so callers can't
     * accidentally mutate the internal list.
     *
     * @return ArrayList of Student objects
     */
    public ArrayList<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    /** Returns the total number of students currently tracked. */
    public int getStudentCount() {
        return students.size();
    }

    // ── Statistical calculations ───────────────────────────────

    /**
     * Calculates the average grade of all students.
     *
     * @return average as a double, or 0 if no students exist
     */
    public double calculateAverage() {
        if (students.isEmpty()) return 0;

        double total = 0;
        for (Student s : students) {
            total += s.getGrade();
        }
        return total / students.size();
    }

    /**
     * Finds the student with the highest grade.
     *
     * @return Student with highest grade, or null if list is empty
     */
    public Student getTopStudent() {
        if (students.isEmpty()) return null;

        Student top = students.get(0);
        for (Student s : students) {
            if (s.getGrade() > top.getGrade()) {
                top = s;
            }
        }
        return top;
    }

    /**
     * Finds the student with the lowest grade.
     *
     * @return Student with lowest grade, or null if list is empty
     */
    public Student getBottomStudent() {
        if (students.isEmpty()) return null;

        Student bottom = students.get(0);
        for (Student s : students) {
            if (s.getGrade() < bottom.getGrade()) {
                bottom = s;
            }
        }
        return bottom;
    }

    // ── Display helpers ────────────────────────────────────────

    /**
     * Prints a neatly formatted table of all students.
     * Warns the user if no students have been added yet.
     */
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n  [!] No students found. Please add students first.\n");
            return;
        }

        printDivider('=', 72);
        System.out.printf("  %-4s %-25s %-10s %-10s %-12s%n",
                "#", "Name", "Grade", "Letter", "Remark");
        printDivider('-', 72);

        for (int i = 0; i < students.size(); i++) {
            Student s = students.get(i);
            System.out.printf("  %-4d %-25s %-10.2f %-10s %-12s%n",
                    (i + 1), s.getName(), s.getGrade(),
                    s.getLetterGrade(), s.getRemark());
        }

        printDivider('=', 72);
    }

    /**
     * Prints a statistics summary: count, average, top and
     * bottom students, and a grade-distribution breakdown.
     */
    public void displaySummaryReport() {
        if (students.isEmpty()) {
            System.out.println("\n  [!] No data to summarise. Please add students first.\n");
            return;
        }

        // ── Counts per letter grade ────────────────────────────
        int countA = 0, countB = 0, countC = 0, countD = 0, countF = 0;
        for (Student s : students) {
            switch (s.getLetterGrade()) {
                case "A": countA++; break;
                case "B": countB++; break;
                case "C": countC++; break;
                case "D": countD++; break;
                default:  countF++; break;
            }
        }

        Student top    = getTopStudent();
        Student bottom = getBottomStudent();
        double  avg    = calculateAverage();

        // ── Print the report ───────────────────────────────────
        printDivider('=', 60);
        System.out.println("             SUMMARY REPORT");
        printDivider('=', 60);
        System.out.printf("  Total Students  : %d%n", students.size());
        System.out.printf("  Average Score   : %.2f%n", avg);
        System.out.printf("  Class Letter    : %s%n", gradeLetterFromScore(avg));
        printDivider('-', 60);
        System.out.printf("  Highest Score   : %.2f  (%s)%n",
                top.getGrade(), top.getName());
        System.out.printf("  Lowest Score    : %.2f  (%s)%n",
                bottom.getGrade(), bottom.getName());
        printDivider('-', 60);
        System.out.println("  Grade Distribution:");
        System.out.printf("    A (90-100)  : %d student(s)%n", countA);
        System.out.printf("    B (80-89)   : %d student(s)%n", countB);
        System.out.printf("    C (70-79)   : %d student(s)%n", countC);
        System.out.printf("    D (60-69)   : %d student(s)%n", countD);
        System.out.printf("    F (0-59)    : %d student(s)%n", countF);
        printDivider('=', 60);
    }

    /**
     * Searches for students whose name contains the given
     * keyword (case-insensitive) and prints the matches.
     *
     * @param keyword text to search for in student names
     */
    public void searchStudent(String keyword) {
        if (students.isEmpty()) {
            System.out.println("\n  [!] No students to search.\n");
            return;
        }

        ArrayList<Student> results = new ArrayList<>();
        for (Student s : students) {
            if (s.getName().toLowerCase().contains(keyword.toLowerCase())) {
                results.add(s);
            }
        }

        if (results.isEmpty()) {
            System.out.println("\n  [!] No student found matching \"" + keyword + "\".\n");
        } else {
            System.out.println("\n  Search results for \"" + keyword + "\":");
            printDivider('-', 72);
            for (Student s : results) {
                System.out.println("  " + s);
            }
            printDivider('-', 72);
        }
    }

    /**
     * Sorts students by grade (highest first) and prints
     * the ranked list. The original list order is unchanged.
     */
    public void displayRankedList() {
        if (students.isEmpty()) {
            System.out.println("\n  [!] No students to rank.\n");
            return;
        }

        // Copy so we don't mutate the original list
        ArrayList<Student> sorted = new ArrayList<>(students);

        // Bubble sort (easy to read for beginners)
        for (int i = 0; i < sorted.size() - 1; i++) {
            for (int j = 0; j < sorted.size() - 1 - i; j++) {
                if (sorted.get(j).getGrade() < sorted.get(j + 1).getGrade()) {
                    Student temp = sorted.get(j);
                    sorted.set(j, sorted.get(j + 1));
                    sorted.set(j + 1, temp);
                }
            }
        }

        System.out.println("\n  Students Ranked by Score (Highest → Lowest):");
        printDivider('-', 72);
        for (int i = 0; i < sorted.size(); i++) {
            Student s = sorted.get(i);
            System.out.printf("  Rank %-3d | %s%n", (i + 1), s);
        }
        printDivider('-', 72);
    }

    // ── Private utility helpers ────────────────────────────────

    /** Converts a numeric score to a letter grade string. */
    private String gradeLetterFromScore(double score) {
        if (score >= 90) return "A";
        if (score >= 80) return "B";
        if (score >= 70) return "C";
        if (score >= 60) return "D";
        return "F";
    }

    /** Prints a horizontal divider made of repeated characters. */
    private void printDivider(char ch, int length) {
        System.out.println("  " + String.valueOf(ch).repeat(length));
    }
}
