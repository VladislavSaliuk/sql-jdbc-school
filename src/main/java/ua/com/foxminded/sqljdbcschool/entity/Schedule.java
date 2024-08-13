package ua.com.foxminded.sqljdbcschool.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Entity
@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "schedule")
@IdClass(ScheduleId.class)
public class Schedule implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Id
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Schedule(Student student, Course course) {
        this.student = student;
        this.course = course;
    }
}
