/**
 * ============================================================
 *  Student.java
 *  Represents a single student with a name and a numeric grade.
 *
 *  OOP concepts used:
 *    - Encapsulation  : all fields are private
 *    - Constructors   : parameterised constructor to initialise fields
 *    - Getters/Setters: controlled access to private fields
 * ============================================================
 */
public class Student {

    // ── Private fields ─────────────────────────────────────────
    private String name;   // Student's full name
    private double grade;  // Numeric grade (0.0 – 100.0)

    // ── Constructor ────────────────────────────────────────────
    /**
     * Creates a new Student with the given name and grade.
     *
     * @param name  the student's name (must not be blank)
     * @param grade a numeric score between 0 and 100
     */
    public Student(String name, double grade) {
        this.name  = name;
        this.grade = grade;
    }

    // ── Getters ────────────────────────────────────────────────
    /** Returns the student's name. */
    public String getName() {
        return name;
    }

    /** Returns the student's numeric grade. */
    public double getGrade() {
        return grade;
    }

    // ── Setters ────────────────────────────────────────────────
    /**
     * Updates the student's name.
     *
     * @param name new name (must not be blank)
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Updates the student's grade.
     *
     * @param grade new grade (0 – 100)
     */
    public void setGrade(double grade) {
        this.grade = grade;
    }

    // ── Grade letter ───────────────────────────────────────────
    /**
     * Converts the numeric grade to a letter grade.
     *
     * Scale:
     *   90 – 100  →  A  (Excellent)
     *   80 –  89  →  B  (Good)
     *   70 –  79  →  C  (Average)
     *   60 –  69  →  D  (Below Average)
     *    0 –  59  →  F  (Failing)
     *
     * @return single letter grade as a String
     */
    public String getLetterGrade() {
        if (grade >= 90) return "A";
        if (grade >= 80) return "B";
        if (grade >= 70) return "C";
        if (grade >= 60) return "D";
        return "F";
    }

    /**
     * Returns a short remark based on the letter grade.
     *
     * @return descriptive remark string
     */
    public String getRemark() {
        switch (getLetterGrade()) {
            case "A": return "Excellent";
            case "B": return "Good";
            case "C": return "Average";
            case "D": return "Below Average";
            default:  return "Failing";
        }
    }

    // ── toString ───────────────────────────────────────────────
    /**
     * Returns a formatted, single-line summary of this student.
     * Used when printing a list of all students.
     */
    @Override
    public String toString() {
        return String.format("%-25s | Grade: %6.2f | Letter: %s | Remark: %s",
                name, grade, getLetterGrade(), getRemark());
    }
}
