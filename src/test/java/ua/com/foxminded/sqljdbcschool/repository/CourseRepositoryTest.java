package ua.com.foxminded.sqljdbcschool.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.sqljdbcschool.entity.Course;
import ua.com.foxminded.sqljdbcschool.repository.CourseRepository;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CourseRepositoryTest {

    @Autowired
    private CourseRepository courseRepository;

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void save_shouldReturnCorrectCourseCount_whenExecutesInsertQuery(){
        Course course = new Course("PE", "Physical education");
        courseRepository.save(course);
        long count = courseRepository.findAll().size();
        assertEquals(11, count);
    }
    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void deleteById_shouldReturnCorrectCourseCount_whenExecutesDeleteQuery(){
        long courseId = 1;
        courseRepository.deleteById(courseId);
        long count = courseRepository.findAll().size();
        assertEquals(9, count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void existsById_shouldReturnTrue_whenCourseIdExists(){
        long courseId = 1;
        boolean isCourseNameExists = courseRepository.existsById(courseId);
        assertTrue(isCourseNameExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void existsById_shouldReturnFalse_whenCourseIdDoesntExist(){
        long courseId = 100;
        boolean isCourseNameExists = courseRepository.existsById(courseId);
        assertFalse(isCourseNameExists);
    }
    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void existsByCourseName_shouldReturnTrue_whenCourseNameExists(){
        String courseName = "Mathematics";
        boolean isCourseNameExists = courseRepository.existsByCourseName(courseName);
        assertTrue(isCourseNameExists);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void existsByCourseName_shouldReturnFalse_whenCourseNameDoesntExist(){
        String courseName = "Test subject";
        boolean isCourseNameExists = courseRepository.existsByCourseName(courseName);
        assertFalse(isCourseNameExists);
    }
    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_courses.sql"})
    void findAll_shouldReturnCorrectCourseCount_whenExecutesSelectQuery(){
        List<Course> courseList = courseRepository.findAll();
        long count = courseList.size();
        assertEquals(10, count);
    }

    @Test
    @Sql({"/sql/drop_data.sql","/sql/insert_courses.sql"})
    void deleteAll_shouldReturnCorrectCourseCount_whenExecutesDeleteQuery(){
        courseRepository.deleteAll();
        long count = courseRepository.findAll().size();
        assertEquals(0, count);
    }
}
