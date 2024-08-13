package ua.com.foxminded.sqljdbcschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Schedule;
import ua.com.foxminded.sqljdbcschool.entity.ScheduleId;
import ua.com.foxminded.sqljdbcschool.entity.Student;

import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, ScheduleId> {

    boolean existsByCourseAndStudent(Course course, Student student);
    Schedule findByCourseAndStudent(Course course, Student student);


}
