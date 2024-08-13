package ua.com.foxminded.sqljdbcschool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@ToString
@Table(name = "students")
@NoArgsConstructor
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "student_id")
    private long studentId;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
    @JoinTable(
            name = "schedule",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "course_id")
    )
    private List<Course> courses = new LinkedList<>();

    public Student(Group group, String firstName, String lastName) {
        this.group = group;
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
