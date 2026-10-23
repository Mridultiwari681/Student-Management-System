import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

class Student {
    int rollNo;
    String name;
    String course;
    double marks;

    Student(int rollNo, String name, String course, double marks) {
        this.rollNo = rollNo;
        this.name = name;
        this.course = course;
        this.marks = marks;
    }

    void display() {
        System.out.println("Roll No: " + rollNo);
        System.out.println("Name: " + name);
        System.out.println("Course: " + course);
        System.out.println("Marks: " + marks);
        System.out.println("-------------------------");
    }
}

public class StudentManagementSystem {

    static ArrayList<Student> students = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);
    static final String FILE_NAME = "students.txt";

    static Student findStudent(int rollNo) {
        for (Student s : students) {
            if (s.rollNo == rollNo) {
                return s;
            }
        }
        return null;
    }

    // Save student records to a file
    static void saveStudents() {
        try (PrintWriter writer =
                 new PrintWriter(new FileWriter(FILE_NAME))) {

            for (Student s : students) {
                writer.println(
                    s.rollNo + "\t" +
                    s.name + "\t" +
                    s.course + "\t" +
                    s.marks
                );
            }

        } catch (IOException e) {
            System.out.println("Error saving records: " + e.getMessage());
        }
    }

    // Load student records from a file
    static void loadStudents() {
        File file = new File(FILE_NAME);

        if (!file.exists()) {
            return;
        }

        try (BufferedReader reader =
                 new BufferedReader(new FileReader(file))) {

            String line;

            while ((line = reader.readLine()) != null) {
                String[] data = line.split("\t", 4);

                if (data.length == 4) {
                    try {
                        int rollNo = Integer.parseInt(data[0]);
                        double marks = Double.parseDouble(data[3]);

                        if (findStudent(rollNo) == null) {
                            students.add(new Student(
                                rollNo,
                                data[1],
                                data[2],
                                marks
                            ));
                        }

                    } catch (NumberFormatException e) {
                        System.out.println("Invalid record skipped.");
                    }
                }
            }

        } catch (IOException e) {
            System.out.println("Error loading records: " + e.getMessage());
        }
    }

    // Add a student
    static void addStudent() {
        System.out.print("Enter Roll Number: ");
        int roll = sc.nextInt();
        sc.nextLine();

        if (findStudent(roll) != null) {
            System.out.println("Roll number already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();

        System.out.print("Enter Course: ");
        String course = sc.nextLine();

        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();

        students.add(new Student(roll, name, course, marks));
        saveStudents();

        System.out.println("Student added successfully!");
    }

    // View all students
    static void viewStudents() {
        if (students.isEmpty()) {
            System.out.println("No student records found.");
            return;
        }

        for (Student s : students) {
            s.display();
        }
    }

    // Search a student
    static void searchStudent() {
        System.out.print("Enter Roll Number to search: ");
        int roll = sc.nextInt();

        Student s = findStudent(roll);

        if (s != null) {
            s.display();
        } else {
            System.out.println("Student not found!");
        }
    }

    // Update a student
    static void updateStudent() {
        System.out.print("Enter Roll Number to update: ");
        int roll = sc.nextInt();
        sc.nextLine();

        Student s = findStudent(roll);

        if (s == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter New Name: ");
        s.name = sc.nextLine();

        System.out.print("Enter New Course: ");
        s.course = sc.nextLine();

        System.out.print("Enter New Marks: ");
        s.marks = sc.nextDouble();

        saveStudents();

        System.out.println("Student updated successfully!");
    }

    // Delete a student
    static void deleteStudent() {
        System.out.print("Enter Roll Number to delete: ");
        int roll = sc.nextInt();

        Student s = findStudent(roll);

        if (s != null) {
            students.remove(s);
            saveStudents();

            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }
    }

    public static void main(String[] args) {
        loadStudents();

        System.out.println(
            "Loaded " + students.size() + " student record(s)."
        );

        int choice;

        do {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View Students");
            System.out.println("3. Search Student");
            System.out.println("4. Update Student");
            System.out.println("5. Delete Student");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;

                case 2:
                    viewStudents();
                    break;

                case 3:
                    searchStudent();
                    break;

                case 4:
                    updateStudent();
                    break;

                case 5:
                    deleteStudent();
                    break;

                case 0:
                    System.out.println("Thank you for using the system!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
            }

        } while (choice != 0);

        sc.close();
    }
}
