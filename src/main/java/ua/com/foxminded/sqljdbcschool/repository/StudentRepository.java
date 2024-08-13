package ua.com.foxminded.sqljdbcschool.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.entity.Student;

import java.util.List;



@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByFirstName(String firstName);
    List<Student> findByFirstName(String firstName);
    Student findByStudentId(long studentId);

}