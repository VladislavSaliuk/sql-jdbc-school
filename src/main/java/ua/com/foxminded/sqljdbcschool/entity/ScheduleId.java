package ua.com.foxminded.sqljdbcschool.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class ScheduleId implements Serializable {
    private Long student;
    private Long course;
}
