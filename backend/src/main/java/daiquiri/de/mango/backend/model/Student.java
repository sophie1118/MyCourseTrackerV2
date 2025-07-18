package daiquiri.de.mango.backend.model;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Entity
@Table (name = "students")
public class Student {

    @Id
    private String carnet;


    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private String email;

    @ManyToMany
    @JoinTable (
            name = "student_course",
            joinColumns = @JoinColumn (name = "student_id"),
            inverseJoinColumns = @JoinColumn (name = "course_code")
    )
     private List <Course> courses;

    public Student() {
    }

    public Student(String carnet, String name, String email) {
        this.carnet = carnet.trim();
        this.name = name.trim();
        this.email = email.trim();
    }


    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email.trim();
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
