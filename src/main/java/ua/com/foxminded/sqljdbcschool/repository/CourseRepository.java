package ua.com.foxminded.sqljdbcschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqljdbcschool.entity.Course;

@Repository
public interface CourseRepository extends JpaRepository<Course,Long> {
    boolean existsByCourseName(String courseName);

    Course findByCourseId(long courseId);
}
