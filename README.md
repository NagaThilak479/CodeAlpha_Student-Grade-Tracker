📚 Student Grade Tracker — Java Console Application
A beginner-friendly, menu-driven Java project that lets you add students,
record their grades, and view statistics and reports — all from the terminal.
---
📁 Project Structure
```
StudentGradeTracker/
├── src/
│   ├── Student.java        ← Data model (OOP: fields, constructor, getters/setters)
│   ├── GradeTracker.java   ← Business logic (add, stats, search, sort)
│   └── Main.java           ← Entry point (menu, input validation, exception handling)
└── README.md
```
---
⚙️ How to Compile & Run
Option A — VS Code
Install the Extension Pack for Java (Microsoft) from the Extensions panel.
Open the `StudentGradeTracker` folder.
Open `src/Main.java`.
Click the ▶️ Run button that appears above `public static void main`.
Option B — IntelliJ IDEA
Open IntelliJ → File → New → Project from Existing Sources → select the folder.
Mark `src/` as Sources Root (right-click → Mark Directory As → Sources Root).
Right-click `Main.java` → Run 'Main.main()'.
Option C — Terminal (any OS)
```bash
# 1. Navigate into the src folder
cd StudentGradeTracker/src

# 2. Compile all three files at once
javac Student.java GradeTracker.java Main.java

# 3. Run the program (class with main is called Main)
java Main
```
> **Requires Java 11 or higher.** Check with `java -version`.
---
🖥️ Sample Input / Output
```
  ╔══════════════════════════════════════════╗
  ║      STUDENT GRADE TRACKER  v1.0        ║
  ║    Track • Analyse • Improve Grades     ║
  ╚══════════════════════════════════════════╝

  ┌─────────────────────────────────────┐
  │              MAIN MENU              │
  ├─────────────────────────────────────┤
  │  1. Add a Student                   │
  │  2. View All Students               │
  │  3. View Statistics                 │
  │  4. View Summary Report             │
  │  5. Search Student by Name          │
  │  6. View Ranked List                │
  │  7. Exit                            │
  └─────────────────────────────────────┘
  Enter your choice (1-7): 1

  ── Add New Student ─────────────────────────────────────
  Enter student name : Arjun Sharma
  Enter grade (0 – 100) : 92

  ✔  Student "Arjun Sharma" added successfully!

  Enter your choice (1-7): 1
  Enter student name : Priya Iyer
  Enter grade (0 – 100) : 78

  ✔  Student "Priya Iyer" added successfully!

  Enter your choice (1-7): 2

  ── All Students ─────────────────────────────────────────
    #    Name                      Grade      Letter     Remark
    ────────────────────────────────────────────────────────────
    1    Arjun Sharma              92.00      A          Excellent
    2    Priya Iyer                78.00      C          Average
    ════════════════════════════════════════════════════════════

  Enter your choice (1-7): 3

  ── Class Statistics ─────────────────────────────────────
    Total Students  : 2
    Average Score   : 85.00
    Highest Score   : 92.00  (by Arjun Sharma)
    Lowest Score    : 78.00  (by Priya Iyer)

  Enter your choice (1-7): 4

    ════════════════════════════════════════════════════
                    SUMMARY REPORT
    ════════════════════════════════════════════════════
      Total Students  : 2
      Average Score   : 85.00
      Class Letter    : B
    ────────────────────────────────────────────────────
      Highest Score   : 92.00  (Arjun Sharma)
      Lowest Score    : 78.00  (Priya Iyer)
    ────────────────────────────────────────────────────
      Grade Distribution:
        A (90-100)  : 1 student(s)
        B (80-89)   : 0 student(s)
        C (70-79)   : 1 student(s)
        D (60-69)   : 0 student(s)
        F (0-59)    : 0 student(s)
    ════════════════════════════════════════════════════
```
---
🔍 Step-by-Step Explanation
How the program works
Step	What happens
1	`main()` in `Main.java` prints the banner and enters a `while(true)` loop
2	Each iteration calls `printMenu()` to show the 7 options
3	`readMenuChoice()` reads an integer safely — any non-integer returns `-1`
4	`handleChoice(int)` routes to the correct action via a `switch` statement
5	Add Student validates name (not blank) and grade (0–100) before calling `tracker.addStudent()`
6	`GradeTracker` stores students in an `ArrayList<Student>`
7	Statistics iterate over the list to find average, max, and min
8	The summary report also counts how many students fall in each letter band
9	Choosing 7 returns `false` from `handleChoice`, breaking the loop
10	`Scanner.close()` is called once at the very end to free resources
Key OOP decisions
`Student` is a pure data class — it knows nothing about I/O.
`GradeTracker` contains all list logic — it knows nothing about the menu.
`Main` owns all user interaction — it never directly touches the ArrayList.
This separation makes each class easy to test, extend, or replace independently.
---
🧩 Input Validation & Exception Handling
Situation	How it's handled
User types letters instead of a number for the menu choice	`InputMismatchException` caught → returns `-1` → switch default prints error
Student name is blank or only spaces	`while` loop repeats until a non-empty name is entered
Grade is not a number (e.g. "abc")	`NumberFormatException` caught → grade reset to -1 → loop continues
Grade is out of range (< 0 or > 100)	Checked with an `if` inside the same loop → error message shown
No students added before viewing stats	Null/empty guard in every display method prints a friendly warning
---
✨ 3 Extra Features to Add Next
1. 🔁 Edit or Delete a Student
Allow the user to select a student by number and update their name/grade or
remove them entirely. This reinforces `ArrayList` manipulation methods like
`set()` and `remove()`.
2. 💾 Save & Load from a File
Use `FileWriter` / `BufferedReader` to write the student list to a `.txt` or
`.csv` file and load it back on startup. This introduces file I/O — a common
real-world requirement.
3. 📊 Per-Grade-Band Pass/Fail Breakdown
Add a configurable pass mark (e.g. 50 or 60) and display how many students
passed vs. failed, plus what percentage of the class each represents. Great
practice for percentage calculations and formatted output.
---
📝 Grade Scale Reference
Letter	Range	Remark
A	90 – 100	Excellent
B	80 – 89	Good
C	70 – 79	Average
D	60 – 69	Below Average
F	0 – 59	Failing
