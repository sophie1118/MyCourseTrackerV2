package daiquiri.de.mango.backend.model;


import jakarta.persistence.*;

import java.util.List;

@Entity
@Table (name = "courses")
public class Course {

    @Id
    private String code;

    @Column (nullable = false)
    private String name;

    @Column (nullable = false)
    private Integer credits;

    @ManyToMany
    @JoinTable(
            name = "course_prerequisites",
            joinColumns = @JoinColumn(name = "course_code"),
            inverseJoinColumns = @JoinColumn(name = "prerequisite_code")
    )
    private List<Course> prerequisites;

    public Course() {
    }


    public Course(String code, String name, Integer credits, List<Course> prerequisites) {
        this.code = code;
        this.name = name;
        this.credits = credits;
        this.prerequisites = prerequisites;
    }

    public List<Course> getPrerequisites() {
        return prerequisites;
    }

    public void setPrerequisites(List<Course> prerequisites) {
        this.prerequisites = prerequisites;
    }

    public Integer getCredits() {
        return credits;
    }

    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
