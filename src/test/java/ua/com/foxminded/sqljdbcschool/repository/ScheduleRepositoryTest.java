package ua.com.foxminded.sqljdbcschool.repository;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;
import ua.com.foxminded.sqljdbcschool.entity.*;

import java.util.List;

import static org.junit.Assert.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ScheduleRepositoryTest {

    @Autowired
    ScheduleRepository scheduleRepository;

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    StudentRepository studentRepository;
    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql", "/sql/insert_courses.sql"})
    void save_shouldReturnCorrectScheduleCount_whenExecutesInsertQuery(){
        long studentId = 1;
        long courseId = 1;

        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        assertNotNull(course);
        assertNotNull(student);

        Schedule schedule = new Schedule(student, course);

        scheduleRepository.save(schedule);

        long count = scheduleRepository.count();
        assertEquals(1, count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql", "/sql/insert_courses.sql"})
    void delete_shouldReturnCorrectScheduleCount_whenExecutesDeleteQuery(){
        long studentId = 1;
        long courseId = 1;

        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        assertNotNull(course);
        assertNotNull(student);


        Schedule schedule = new Schedule(student, course);

        scheduleRepository.save(schedule);

        scheduleRepository.delete(schedule);

        long count = scheduleRepository.count();
        assertEquals(0, count);
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql", "/sql/insert_courses.sql"})
    void existsByCourseAndStudent_shouldReturnFalse_whenScheduleDoesntExist(){
        long studentId = 1;
        long courseId = 1;

        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        assertNotNull(course);
        assertNotNull(student);

        assertFalse(scheduleRepository.existsByCourseAndStudent(course, student));
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql", "/sql/insert_courses.sql"})
    void existsByCourseAndStudent_shouldReturnTrue_whenScheduleExists(){
        long studentId = 1;
        long courseId = 1;

        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        assertNotNull(course);
        assertNotNull(student);

        Schedule expectedSchedule = new Schedule(student, course);


        scheduleRepository.save(expectedSchedule);

        assertTrue(scheduleRepository.existsByCourseAndStudent(course, student));
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql", "/sql/insert_courses.sql"})
    void findByCourseAndStudent_shouldReturnCorrectSchedule_whenScheduleExists(){
        long studentId = 1;
        long courseId = 1;

        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        assertNotNull(course);
        assertNotNull(student);

        Schedule actualSchedule = new Schedule(student, course);

        scheduleRepository.save(actualSchedule);

        Schedule expectedSchedule = scheduleRepository.findByCourseAndStudent(course, student);

        assertTrue(actualSchedule.equals(expectedSchedule));
    }

    @Test
    @Sql(scripts = {"/sql/drop_data.sql", "/sql/insert_groups.sql", "/sql/insert_students.sql", "/sql/insert_courses.sql"})
    void findByCourseAndStudent_shouldReturnNull_whenScheduleDoesntExist(){
        long studentId = 1;
        long courseId = 1;

        Student student = studentRepository.findById(studentId).get();
        Course course = courseRepository.findById(courseId).get();

        assertNotNull(course);
        assertNotNull(student);

        Schedule expectedSchedule = scheduleRepository.findByCourseAndStudent(course, student);

        assertNull(expectedSchedule);
    }

}
