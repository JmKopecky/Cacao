package dev.prognitio.cacao;

public class Course {

    public String courseName;
    public int semester;
    public double GPA;
    public double grade;

    public Course(String courseName, int semester, double GPA, double grade) {
        this.courseName = courseName;
        this.semester = semester;
        this.GPA = GPA;
        this.grade = grade;
    }

    public String toString() {
        return courseName + " | " + semester + " | " + GPA + " | " + grade;
    }
}
